package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.PollRequest;

public class PAUSED extends TetrisGameState {
	TetrisGameLogic gameLogic;
	
	public PAUSED(TetrisGameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
	
	public PollRequest start(PollRequest request) {
		return null;
	}
	
	public PollRequest run(PollRequest request) {
		return null;
	}
	
	public PollRequest stop(PollRequest request) {
		return null;
	}
	
	public PollRequest pause(PollRequest request) {
		return request;
	}
	
	public PollRequest lose(PollRequest request) {
		return null;
	}
	
	public PollRequest win(PollRequest request) {
		return null;
	}
}
