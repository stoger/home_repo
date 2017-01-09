package com.stoeger.tetris.Server;

import com.badlogic.gdx.utils.TimeUtils;
import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PollRequest;

public class RUNNING extends TetrisGameState {
	private TetrisGameLogic gameLogic;
	private static final int TIME_MILLIS_BETWEEN_FALLS = 100;
	long startTime = TimeUtils.nanoTime();
	long checkTime = 230000L;
	long timeLastMove = System.nanoTime();

	public RUNNING(TetrisGameLogic gameLogic) {
		super(); // TODO what for?
		this.gameLogic = gameLogic;
	}
	
	public PollRequest start(PollRequest request) {
		return null;
	}
	
	public PollRequest run(PollRequest request) {
		if(checkGameOver(request)) {
//			lose(request);
			System.out.println("Game Over");
			gameLogic.setCurState(new LOST(gameLogic));
			request.setGameState(GameState.GAME_LOST);
			request.getArena().reset();
		}
		
		handleInput(request);
		boolean isRunning = true;
		
		while(isRunning) {
			if(TimeUtils.timeSinceNanos(startTime) > checkTime) {
				startTime = TimeUtils.nanoTime();
				isRunning = false;
			}
		}
		checkTime = 230000000;
		fallTetromino(request);
		return request;
	}
	
	public PollRequest stop(PollRequest request) {
		return null;
	}
	
	public PollRequest pause(PollRequest request) {
		return null;
	}
	
	public PollRequest lose(PollRequest request) {
		return null;
	}
	
	public PollRequest win(PollRequest request) {
		return null;
	}
	
	public boolean checkGameOver(PollRequest request) {
		for(int i = 0; i < TetrisArena.WIDTH; i++) {
			
			System.out.println("Content: " + request.getArena().getArenaContent(i, 0));
			
			if(request.getArena().getArenaContent(i, 0) != 0) {
				return true;
			}
		}
		return false;
	}
	
	public void handleInput(PollRequest request) {
		Tetromino tetro = request.getActTetronimo();
		TetrisArena arena = request.getArena();
		boolean up = true, down = true, left = true, right = true;
		
		switch(request.getPlayerAction()) {
		case NOKEY:
			up = true;
			down = true;
			left = true;
			right = true;
			break;
		case LEFT:
			if(left) {
				if(arena.checkTetromino(tetro, tetro.getPosx() - 1, tetro.getPosy(), tetro.getRotation()) == true) {
					tetro.moveTetromino(tetro, -1, 0);
					timeLastMove = System.nanoTime();
				}
			}
			left = false;
			
			if((System.nanoTime() - timeLastMove) / 1e6 > TIME_MILLIS_BETWEEN_FALLS) {
				if(arena.checkTetromino(tetro, tetro.getPosx() - 1, tetro.getPosy(), tetro.getRotation()) == true) {
					tetro.moveTetromino(tetro, -1, 0);
					timeLastMove = System.nanoTime();
				}
			}
			break;
			
		case RIGHT:
				if(right) {
					if(arena.checkTetromino(tetro, tetro.getPosx() + 1, tetro.getPosy(), tetro.getRotation()) == true) {
						tetro.moveTetromino(tetro, 1, 0);
						timeLastMove = System.nanoTime();
					}
				}
				right = false;
				
				if((System.nanoTime() - timeLastMove) / 1e6 > TIME_MILLIS_BETWEEN_FALLS) {
					if(arena.checkTetromino(tetro, tetro.getPosx() + 1, tetro.getPosy(), tetro.getRotation()) == true) {
						tetro.moveTetromino(tetro, 1, 0);
						timeLastMove = System.nanoTime();
					}
				}
			break;
			
		case UP:
			if(up) {
				if(arena.checkTetromino(tetro, tetro.getPosx(), tetro.getPosy(), tetro.getRotation() + 1) == true) {
					tetro.rotate();
				}
			}
			up = false;
			break;
			
		case DOWN:
			if(down) {
				// TODO phil's check time, MAYBE
				if(arena.checkTetromino(tetro, tetro.getPosx(), tetro.getPosy() + 1, tetro.getRotation()) == true) {
					tetro.moveTetromino(tetro, 0, 1);
					timeLastMove = System.nanoTime();
				} else {
					arena.setTetromino(tetro, tetro.getPosx(), tetro.getPosy());
					tetro.setIsFixed(true);
				}
			}
			break;
			
		case DROP:
			fallDownTetromino(request);
			break;
			
		case PAUSE:
			gameLogic.setCurState(new PAUSED(gameLogic));
			request.setGameState(GameState.GAME_PAUSED);
			break;
			
		case RESUME:
			request.setGameState(GameState.GAME_RUNNING);
			break;
			
		case QUIT:
			break;
			
		case START: 
			break;
		}
		
		if(tetro.getIsFixed()) {
			if(arena.checkTetromino(request.getNextTetronimo(), request.getNextTetronimo().getPosx(), request.getNextTetronimo().getPosy(), request.getNextTetronimo().getRotation()) == true) {
				tetro = request.getNextTetronimo();
				request.setNextTetronimo(new Tetromino(4, 0));
			} else {
				System.out.println("Game Over");
				this.gameLogic.setCurState(new LOST(gameLogic));
			}
		}
		request.setActTetronimo(tetro);
		request.setActTetronimoX(tetro.getPosx());
		request.setActTetronimoY(tetro.getPosy());
	}
	
	public void fallTetromino(PollRequest request) {
		Tetromino tetro = request.getActTetronimo();
		TetrisArena arena = request.getArena();
		
		if(arena.checkTetromino(tetro, tetro.getPosx(), tetro.getPosy() + 1, tetro.getRotation()) == true) {
			tetro.moveTetromino(tetro, 0, 1);
		} else {
			arena.setTetromino(tetro, tetro.getPosx(), tetro.getPosy());
			tetro.setIsFixed(true);
		}
	}
	
	public void fallDownTetromino(PollRequest request) {
		Tetromino tetro = request.getActTetronimo();
		
		while(!tetro.getIsFixed()) {
			if(request.getArena().checkTetromino(tetro, tetro.getPosx(), tetro.getPosy() + 1, tetro.getRotation()) == true) {
				tetro.moveTetromino(tetro, 0, 1);
			} else {
				request.getArena().setTetromino(tetro, tetro.getPosx(), tetro.getPosy());
				tetro.setIsFixed(true);
			}
		}
	}
}
