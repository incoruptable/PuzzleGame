package com.ai;

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

    GameState() {
        state = new byte[22];
    }

}
