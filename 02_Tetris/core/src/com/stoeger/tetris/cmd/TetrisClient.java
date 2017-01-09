package com.stoeger.tetris.cmd;

/**
 * Created by tom on 21.11.16.
 */
public interface TetrisClient {
    public abstract PollRequest pollGameState(PollRequest request)
            throws Exception;
}
