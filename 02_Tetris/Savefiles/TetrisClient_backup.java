package com.stoeger.tetris.Client;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stoeger.tetris.Server.TetrisArena;
import com.stoeger.tetris.Server.Tetromino;
import com.stoeger.tetris.cmd.TetrisServer;

public class TetrisClient_backup implements InputProcessor {
	private SpriteBatch batch;
	private Texture img;
	private static int WORLD_HEIGHT = 1600;
	private static int WORLD_WIDTH = 1000;
	private ArrayList<Texture> textures = new ArrayList<Texture>();
	private OrthographicCamera cam;
	private Viewport viewport;
	private TetrisServer tetris;
		
	public void create() {
		batch = new SpriteBatch();

		for (int iCount = 0; iCount <= 7; iCount++) {
			textures.add(new Texture(Gdx.files.internal(iCount + ".png")));
		}

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT * (w / h));
		cam.position.set(WORLD_HEIGHT / 2, WORLD_WIDTH / 2, 0);
		cam.update();
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, cam);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		drawArena();
		drawTetromino();
		drawNextTetromino();

		batch.end();
	}

	public void drawArena() {
		Sprite sprite;
		int val = 0;
				
		for (int outCount = TetrisArena.HEIGHT - 1; outCount >= 0; outCount--) {
			for (int iCount = 0; iCount < TetrisArena.WIDTH; iCount++) {
				val = this.tetris.getArenaContent(iCount, outCount);
				img = textures.get(val);

				sprite = new Sprite(img, 0, 0, img.getHeight(), img.getWidth());
				sprite.setSize(25, 25);

				sprite.setPosition(iCount * sprite.getWidth(),
						(TetrisArena.HEIGHT - 1 - outCount) * sprite.getHeight());
				sprite.draw(batch);

				val = 0;
			}
		}
	}

	public void drawTetromino() {
		int[][] tetromino = tetro.toArray();
		Sprite sprite;

		for (int outCount = tetromino.length; outCount > 0; outCount--) {
			for (int inCount = 0; inCount < tetromino.length; inCount++) {
				if (tetromino[tetromino.length - outCount][inCount] != 0) {
					img = textures.get(tetromino[tetromino.length - outCount][inCount]);

					sprite = new Sprite(img, 0, 0, img.getHeight(), img.getWidth());
					sprite.setSize(25, 25);

					sprite.setPosition((tetro.getPosx()) * sprite.getWidth() + inCount * sprite.getWidth(),
							(TetrisArena.HEIGHT - 1 - tetro.getPosy()) * sprite.getHeight()
									+ (outCount - tetromino.length) * sprite.getHeight());
					sprite.draw(batch);
				}
			}
		}
	}

	public void drawNextTetromino() {
		int[][] tetromino = displayNext.toArray();
		Sprite sprite;

		for (int outCount = tetromino.length; outCount > 0; outCount--) {
			for (int inCount = 0; inCount < tetromino.length; inCount++) {
				if (tetromino[tetromino.length - outCount][inCount] != 0) {
					img = textures.get(tetromino[tetromino.length - outCount][inCount]);

					sprite = new Sprite(img, 0, 0, img.getHeight(), img.getWidth());
					sprite.setSize(25, 25);

					sprite.setPosition((TetrisArena.WIDTH * sprite.getWidth() + 100 + inCount * sprite.getWidth()),
							(TetrisArena.HEIGHT  * sprite.getHeight() + outCount * sprite.getHeight() - 200));
					sprite.draw(batch);
				}
			}
		}
	}
	
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
			//fallDelay = 125;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.S:
			//fallDelay = 500;
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
	

	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
	}
}
