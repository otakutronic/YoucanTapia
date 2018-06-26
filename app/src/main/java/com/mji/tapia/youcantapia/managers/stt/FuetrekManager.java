package com.mji.tapia.youcantapia.managers.stt;

import android.content.Context;
import android.support.annotation.RawRes;
import android.util.Log;

import com.fuetrek.fsr.FSRService;
import com.fuetrek.fsr.FSRServiceEnum.BackendType;
import com.fuetrek.fsr.FSRServiceEnum.EventType;
import com.fuetrek.fsr.FSRServiceEnum.IOMode;
import com.fuetrek.fsr.FSRServiceEnum.LogLevel;
import com.fuetrek.fsr.FSRServiceEnum.Ret;
import com.fuetrek.fsr.FSRServiceEventListener;
import com.fuetrek.fsr.entity.AbortInfoEntity;
import com.fuetrek.fsr.entity.CodecAssignEntity;
import com.fuetrek.fsr.entity.ConstructEntity;
import com.fuetrek.fsr.entity.DtypeInfoEntity;
import com.fuetrek.fsr.entity.IOSourceEntity;
import com.fuetrek.fsr.entity.IndividualEntity;
import com.fuetrek.fsr.entity.ModelEntity;
import com.fuetrek.fsr.entity.RecognizeEntity;
import com.fuetrek.fsr.entity.ResultInfoEntity;
import com.fuetrek.fsr.entity.StartParamEntity;
import com.fuetrek.fsr.entity.VoiceControlEntity;
import com.fuetrek.fsr.exception.AbortException;
import com.fuetrek.fsr.exception.OperationException;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Sami on 5/17/2018.
 *
 */

public class FuetrekManager implements STTManager, FSRServiceEventListener {

    private Context context;

    private FSRService fsrService;

    private final static BackendType backendType = BackendType.E;

    private final static String backendURL = "asp.fuetrek.co.jp/FSSP/?group=mji-tapia";
    private final static String backendService_ = "mji-tapia";
    private final static int    backendPortNumber = 80;

    static private STTManager instance;
    static public STTManager getInstance(Context context) {
        if (instance == null) {
            instance = new FuetrekManager(context);
        }
        return instance;
    }


    private IndividualEntity individualEntity;
    private CodecAssignEntity codecAssignEntity;
    private ConstructEntity constructEntity;
    private ModelEntity modelEntity;

    private FuetrekSession currentSession;

    private CompletableSubject initSubject;
    private CompletableSubject cancelSubject;

    private BehaviorSubject<STTState> currentStateBehaviorSubject;

    private PublishSubject<Integer> levelPublishSubject;

    private boolean isInit;

    private FuetrekManager(Context context) {
        this.context = context;
        initSubject = CompletableSubject.create();
        currentStateBehaviorSubject = BehaviorSubject.create();
        currentStateBehaviorSubject.onNext(STTState.IDLE);
        currentStateBehaviorSubject.subscribe(sttState -> Log.e("FUETREK","GLOBAL STATE:" + sttState.name()));
        levelPublishSubject = PublishSubject.create();
        isInit = false;
    }


//    @Override
//    public Completable initOnline() {
//        return null;
//    }

    @Override
    public Completable initOffline(String modelPath) {
        if (!isInit) {
            isInit = true;
            cancelSubject = CompletableSubject.create();
            cancelSubject.onComplete();
            initIndividualEntity();
            initCodecAssignEntity();
            initConstructEntity();

            try {
                fsrService = new FSRService(backendType, this, constructEntity);


                final DtypeInfoEntity dtypeInfoEntity;
                dtypeInfoEntity = null;
                initModelEntity(modelPath);
                fsrService.connectSession(backendType, dtypeInfoEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return initSubject;
    }

//    @Override
//    public Completable initOffline(int modelId) {
//        return null;
//    }



    private void  initIndividualEntity() {
        individualEntity = new IndividualEntity();
        individualEntity.setApplication("SampleTypeE");
        individualEntity.setTerminalId("SIM Serial");
        individualEntity.setTerminalType("IMEI number");
    }

    private void  initCodecAssignEntity() {
        codecAssignEntity = new CodecAssignEntity();
        codecAssignEntity.setCodec("PCM");
        codecAssignEntity.setRecordSize(240);
    }

    private void  initConstructEntity() {
        constructEntity = new ConstructEntity();
        constructEntity.setIndividual(individualEntity);
        constructEntity.setCodecAssign(codecAssignEntity);
        constructEntity.setSpeechTime(5000);
        constructEntity.setRecognizeTime(5000);
        constructEntity.setLogLevel(LogLevel.Debug);
        constructEntity.setRapidMode(true);
    }

    private void  initModelEntity(String assetPath) {
        try {
            modelEntity = new ModelEntity();
            byte[] buffer;
            InputStream in;
            in = context.getAssets().open(assetPath);

            int total;
            total = in.available();
            buffer = new byte[total];
            in.read(buffer);
            in.close();

            modelEntity.setTotalSize(total);
            modelEntity.setDivideDataArea(buffer);
            modelEntity.setLast(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void  initModelEntity(@RawRes int modelId) {
        try {
            modelEntity = new ModelEntity();
            byte[] buffer;
            InputStream in;
            in = context.getResources().openRawResource(modelId);

            int total;
            total = in.available();
            buffer = new byte[total];
            in.read(buffer);
            in.close();

            modelEntity.setTotalSize(total);
            modelEntity.setDivideDataArea(buffer);
            modelEntity.setLast(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initIOSourceEntity() {
        IOSourceEntity ioSourceEntity = new IOSourceEntity();
        try {
            fsrService.setIOSource(IOMode.ModePcmMic, ioSourceEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Session createSession() {
        return new FuetrekSession();
    }

    @Override
    public Observable<STTState> getCurrentStateObserver() {
        return currentStateBehaviorSubject;
    }

    @Override
    public Observable<Integer> getMicrophoneVolumeObserver() {
        return levelPublishSubject;
    }

    @Override
    public void stopCurrent() {
        if (currentSession != null) {
            currentSession.cancel();
        }
    }

    private Disposable sessionStateDisposable;
    private void setCurrentSession(FuetrekSession session) {
        if (sessionStateDisposable != null && !sessionStateDisposable.isDisposed()) {
            sessionStateDisposable.dispose();
        }
        currentSession = session;
        sessionStateDisposable = session.getStateObserver().subscribe(currentStateBehaviorSubject::onNext);
    }

    @Override
    public void notifyEvent(Object appHandle, EventType eventType, BackendType backendType, Object eventData) {
        if(eventType != EventType.NotifyLevel) {
            Log.e("FUETREK", eventType.name());
        }
        switch (eventType) {
            case CompleteConnect:
                initIOSourceEntity();

                if (backendType == BackendType.E) {
                    try {
                        fsrService.setModelData(modelEntity);
                        if( eventData != Ret.RetOk ){
                            throw new Exception("failed connectSession.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    initSubject.onComplete();
                }
                break;
            case CompleteStart:
//                if (currentSession != null) {
//                    currentSession.stateBehaviorSubject.onNext(STTState.LISTENING);
//                }
                break;
            case CompleteSetModel:
                modelEntity.setDivideDataArea(null);
                initSubject.onComplete();
                break;
            case NotifyEndRecognition:
                if (currentSession != null) {
                    currentSession.stateBehaviorSubject.onNext(STTState.DONE);
                    try {
                        RecognizeEntity recognizeEntity = fsrService.getSessionResultStatus(backendType);
                        final List<String> resultList = new ArrayList<>();
                        for(int i = 0; i < recognizeEntity.getCount(); i++){
                            ResultInfoEntity info = fsrService.getSessionResult(backendType, i + 1);
                            Log.e("FUETREK",info.getText());
                            resultList.add(info.getText());
                        }

                        currentSession.resultSingleSubject.onSuccess(resultList);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case NotifyLevel:
                levelPublishSubject.onNext((Integer) eventData);
                break;
            case NotifyAutoStopSilence:
                if (currentSession != null) {
                    currentSession.stateBehaviorSubject.onNext(STTState.PROCESSING);
                }
                break;
            case NotifyAutoStart:
                if (currentSession != null) {
                    currentSession.stateBehaviorSubject.onNext(STTState.RECORDING);
                }
                break;
            case CompleteCancel:
                cancelSubject.onComplete();
                break;

        }
    }

    @Override
    public void notifyAbort(Object o, AbortInfoEntity abortInfoEntity) {

    }


    public class FuetrekSession implements STTManager.Session{

        BehaviorSubject<STTState> stateBehaviorSubject;

        BehaviorSubject<Integer> progressBehaviorSubject;

        SingleSubject<List<String>> resultSingleSubject;

        private VoiceControlEntity voiceControlEntity;
        private StartParamEntity startParamEntity;


        private Throwable throwable;

        private Disposable listenDisposable;

        FuetrekSession() {
            stateBehaviorSubject = BehaviorSubject.create();
            progressBehaviorSubject = BehaviorSubject.create();
            resultSingleSubject = SingleSubject.create();
            stateBehaviorSubject.onNext(STTState.IDLE);
            stateBehaviorSubject.subscribe(sttState -> Log.e("FUETREK","STATE:" + sttState.name()));
        }

        @Override
        public void cancel() {

            if (stateBehaviorSubject.getValue() == STTState.LISTENING|| stateBehaviorSubject.getValue() == STTState.RECORDING
                    || stateBehaviorSubject.getValue() == STTState.PROCESSING) {
                stateBehaviorSubject.onNext(STTState.ERROR);
                try {
                    cancelSubject = CompletableSubject.create();
                    fsrService.cancelRecognition();
                    throwable = new CancellationException();
                } catch (AbortException | OperationException e) {
                    e.printStackTrace();
                }
             } else {
                if (listenDisposable != null && !listenDisposable.isDisposed()) {
                    listenDisposable.dispose();
                }
                cancelSubject.onComplete();
            }
        }

        @Override
        public Observable<STTState> getStateObserver() {
            return stateBehaviorSubject;
        }

        @Override
        public Single<List<String>> listen() {
            if (currentSession != null) {
                currentSession.cancel();
            }
            setCurrentSession(this);

            Log.e("FUETREK", "request recognition");
            listenDisposable = Completable.mergeArray(cancelSubject,initSubject).subscribe(() -> {
                Log.e("FUETREK", "start recognition");
                if (stateBehaviorSubject.getValue() == STTState.IDLE) {
                    initVoiceControlEntity();
                    initStartParamEntity();
                    try {
                        stateBehaviorSubject.onNext(STTState.LISTENING);
                        fsrService.startRecognition(backendType, voiceControlEntity, startParamEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new IllegalStateException("Session already started");
                }
            });
            return resultSingleSubject;
        }

//        @Override
//        public Observable<Integer> getProgressObserver() {
//            return progressBehaviorSubject;
//        }

        @Override
        public Throwable getError() {
            return throwable;
        }

        private void  initVoiceControlEntity() {
            voiceControlEntity = new VoiceControlEntity();
            voiceControlEntity.setAutoStart(true);
            voiceControlEntity.setAutoStop(true);
            voiceControlEntity.setVadOffTime((short) 500);
            voiceControlEntity.setVadSensibility(0);
            voiceControlEntity.setListenTime(0);
            voiceControlEntity.setLevelSensibility(10);
        }

        private void  initStartParamEntity() {
            startParamEntity = new StartParamEntity();
        }


    }
}
