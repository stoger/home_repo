package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PollRequest;
import com.stoeger.tetris.Server.TetrisGameState;

public class TetrisGameLogic {
	private String gameName;
	long timeStart = 0;
	long timeCheck = 230000L;
	TetrisGameState curState = new STOP(this);
	TetrisServerImp server;
	PollRequest pRequest;
	
	public TetrisGameLogic(String gameName) {
		this.gameName = gameName;
	}
	
	public PollRequest calculateGameStep(PollRequest request) {
		server = request.getServer();
		
		if(request.getGameState() == GameState.GAME_STOPPED || request.getGameState() == null) {
			stop(request);
		}
		
		if(request.getGameState() == GameState.GAME_STARTING) {
			start(request);
		}
		
		if(request.getGameState() == GameState.GAME_RUNNING) {
			run(request);
		}
		
		if(request.getGameState() == GameState.GAME_LOST) {
			lose(request);
		}
		
		if(request.getGameState() == GameState.GAME_WON) {
			win(request);
		}
		
		if(request.getGameState() == GameState.GAME_PAUSED) {
			pause(request);
		}
		
		return pRequest;
	}
	
	public void start(PollRequest req){
		pRequest = curState.start(req);
	}
	public void run(PollRequest req){
		pRequest = curState.run(req);
	}
	public void pause(PollRequest req){
		pRequest = curState.pause(req);
	}
	public void resume(PollRequest req){
		pRequest = curState.run(req);
	}
	public void stop(PollRequest req){
		pRequest = curState.stop(req);
	}
	public void win(PollRequest req){
		pRequest = curState.win(req);
	}
	public void lose(PollRequest req){
		pRequest = curState.lose(req);
	}
	public TetrisGameState getCurState() {
		return curState;
	}
	public void setCurState(TetrisGameState curGame) {
		this.curState = curGame;
	}	
}
