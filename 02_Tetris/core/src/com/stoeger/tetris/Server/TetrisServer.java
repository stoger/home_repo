package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.PollRequest;

public abstract interface TetrisServer {
	 public abstract PollRequest pollGameState(PollRequest paramPollRequest) throws Exception;
}
