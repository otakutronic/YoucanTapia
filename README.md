## How to Install

This apk require system permitions.

It needs to be installed directly in the system directories.

To do that you will need root access

### Get root access
1. Connect tapia to your pc and make sure to have adb debug turned on
2. Open a terminal
3. Run this command : 
```
adb shell am broadcast -a malata.adb.root
```
4. Then run this : 
```
adb remount
```
5. And finally you can open the shell with root access:
```
adb shell
```

After getting root access you will need to install the apk in the system directory
### Install the system application
1. Create the directory
```
adb shell mkdir /system/priv-app/youcan
```
2. Push your apk to the youcan directory
```
adb push "your/apk/path" /system/priv-app/youcan
```
3. Restart your tapia to see the change

Note: The system apk won't work by itself. You will need to install also the apk in a regular way.

### Make the system apk run in standalone
If you want the system apk to run by itself
you will need to add the native libraries to the system apk folder 
1. Install apk normally
2. Install apk in system app folder
3. Get root access
3. Open root shell
4. Go to 
```
cd /data/app/com.mji.tapia.youcantapia-some-number/
```
5. Copy the lib folder into the system app folder
```
cp -r /data/app/com.mji.tapia.youcantapia-some-number/lib /system/priv-app/youcan/
```
6. Restart your tapia