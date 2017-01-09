package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.PollRequest;

public class TetrisServerImp {
	private TetrisGameLogic gameLogic;
//	pyrivate PollRequest request;
	
	public TetrisServerImp() {
		gameLogic = new TetrisGameLogic("Tetris");
		//gameLogic.startGame(request);
	}
	
	public PollRequest pollGameState(PollRequest request) throws Exception {
		return gameLogic.calculateGameStep(request);
	}
	
	public TetrisGameLogic getGameLogic() {
		return gameLogic;
	}
	
	public void setGameLogic(TetrisGameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
}
