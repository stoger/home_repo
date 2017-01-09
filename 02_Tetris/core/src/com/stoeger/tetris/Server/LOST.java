package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PollRequest;

public class LOST extends TetrisGameState {
	TetrisGameLogic gameLogic;
	
	public LOST(TetrisGameLogic gameLogic) {
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
		return null;
	}
	
	public PollRequest lose(PollRequest request) {
		gameLogic.setCurState(new STOP(gameLogic));
		request.setGameState(GameState.GAME_STOPPED);
		return request;
	}
	
	public PollRequest win(PollRequest request) {
		return null;
	}
}
