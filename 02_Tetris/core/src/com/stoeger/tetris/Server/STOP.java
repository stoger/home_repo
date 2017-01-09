package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PollRequest;
import com.stoeger.tetris.cmd.PlayerAction;

public class STOP extends TetrisGameState {
	TetrisGameLogic gameLogic;
	
	public STOP(TetrisGameLogic gameLogic) {
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
		if(request.getPlayerAction() == PlayerAction.START) {
			this.gameLogic.setCurState(new START(this.gameLogic));
			request.setGameState(GameState.GAME_STARTING);
		} else if(request.getPlayerAction() == PlayerAction.QUIT) {
			System.exit(0);
		}
		return request;
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
		return null;
	}

}
