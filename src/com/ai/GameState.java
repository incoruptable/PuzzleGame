package com.ai;

import java.util.Comparator;

/**
 * Created by Incoruptable on 1/22/2017.
 */
public class GameState {
    GameState prev;
    byte[] state;

    GameState(GameState prev) {
        this.prev = prev;
        state = new byte[22];
    }

    GameState(GameState prev, byte[] state) {
        this.prev = prev;
        this.state = state;
    }

    GameState() {
        state = new byte[22];
    }

}

class StateComparator implements Comparator<GameState> {
    public int compare(GameState a, GameState b) {
        for (int i = 0; i < 22; i++) {
            if (a.state[i] < b.state[i])
                return -1;
            else if (a.state[i] > b.state[i])
                return 1;

        }
        return 0;
    }
}
