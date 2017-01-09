package com.stoeger.tetris.cmd;

public interface TetrisServer {
    public abstract PollRequest pollGameState(PollRequest request)
            throws Exception;
}
