package com.stoeger.tetris.Server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.TimeUtils;

public class MyTetris extends ApplicationAdapter implements InputProcessor {
	
	private int fallDelay = 500;

	private TetrisArena tetris = new TetrisArena();
	private Tetromino tetro, displayNext;
	public long exec = System.currentTimeMillis();

	private int x = 0, y = 0;


	public boolean isNotFixed() {
		if (tetris.checkTetromino(tetro, tetro.getPosx(), tetro.getPosy(), tetro.getRotation())
				&& !tetro.getIsFixed()) {
			tetro.setIsFixed(false);
			return true;
		} else {
			tetro.setIsFixed(true);
			tetris.setTetromino(tetro, tetro.getPosx(), tetro.getPosy());
			return false;
		}
	}

	public void createTetromino() {
		tetro = displayNext;
		displayNext = new Tetromino(4, 0);

		displayNext.setRotation(0);
		displayNext.setIsFixed(false);

		tetro.setPosx(4);
		tetro.setPosy(0);
	}

	public void moveTetroDown() {
		if (TimeUtils.timeSinceMillis(exec) >= fallDelay) {
			exec = System.currentTimeMillis();
			if (tetris.checkTetromino(tetro, tetro.getPosx(), tetro.getPosy() + 1, tetro.getRotation())) {
				this.y = tetro.getPosy();
				this.y++;
				tetro.setPosy(this.y);
			} else {
				tetro.setIsFixed(true);
				tetris.setTetromino(tetro, tetro.getPosx(), tetro.getPosy());

			}

		}
	}

	public void moveTetroLeft() {
		this.x = tetro.getPosx();
		this.x--;
		tetro.setPosx(this.x);
	}

	public void moveTetroRight() {
		this.x = tetro.getPosx();
		this.x++;
		tetro.setPosx(this.x);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.A:
			if (tetris.checkTetromino(tetro, tetro.getPosx() - 1, tetro.getPosy(), tetro.getRotation()))
				moveTetroLeft();
			break;
		case Keys.D:
			if (tetris.checkTetromino(tetro, tetro.getPosx() + 1, tetro.getPosy(), tetro.getRotation()))
				moveTetroRight();
			break;
		case Keys.W:
			int rotate = tetro.getRotation();
			rotate++;
			tetro.setRotation(rotate);

		case Keys.S:
			fallDelay = 125;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.S:
			fallDelay = 500;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
