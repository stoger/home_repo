package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PollRequest;

public class WON extends TetrisGameState {
	TetrisGameLogic gameLogic;
	
	public WON(TetrisGameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	@Override
	public PollRequest start(PollRequest request) {
		return null;
	}

	@Override
	public PollRequest run(PollRequest request) {
		return null;
	}

	@Override
	public PollRequest stop(PollRequest request) {
		return null;
	}

	@Override
	public PollRequest pause(PollRequest request) {
		return null;
	}

	@Override
	public PollRequest lose(PollRequest request) {
		return null;
	}

	@Override
	public PollRequest win(PollRequest request) {
		this.gameLogic.setCurState(new WON(this.gameLogic));
		request.setGameState(GameState.GAME_WON);
		
		return request;
	}
}
