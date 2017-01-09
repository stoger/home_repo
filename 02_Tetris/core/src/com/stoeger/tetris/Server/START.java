package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PollRequest;

public class START extends TetrisGameState {
	TetrisGameLogic gameLogic;
	
	public START(TetrisGameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	@Override
	public PollRequest start(PollRequest request) {
		TetrisArena arena = new TetrisArena();
		arena.reset();
		Tetromino tetro = new Tetromino(4, 0);
		Tetromino nextTetro = new Tetromino(4, 0);
		
		request.setArena(arena);
		request.setActTetronimo(tetro);
		request.setActTetronimoX(4);
		request.setActTetronimoY(0);
		request.setNextTetronimo(nextTetro);
		request.setGameState(GameState.GAME_RUNNING);
		this.gameLogic.setCurState(new RUNNING(this.gameLogic));
		
		return request;
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
		return null;
	}
}
