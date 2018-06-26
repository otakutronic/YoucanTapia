package com.mji.tapia.youcantapia.managers.scenario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 5/17/2018.
 */

public class Movement {

    public static class Move {
        enum Type {
            DOWN,
            LEFT,
            UP,
            RIGHT,
            WAIT
        }

        public Type type;
        public int value;
    }

    public String id;

    public List<Move> moveList;

    public Movement(String id, List<Move> moveList) {
        this.id = id;
        this.moveList = moveList;
        if (this.moveList == null)
            this.moveList = new ArrayList<>();
    }
}
