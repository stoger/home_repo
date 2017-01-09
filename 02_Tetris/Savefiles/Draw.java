package com.stoeger.tetris.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.stoeger.tetris.Server.TetrisArena;

public class Draw {
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		drawArena();

		if (isNotFixed()) {
			moveTetroDown();
			drawTetromino();
		} else {
			createTetromino();
		}

		batch.end();

		tetris.checkLines();
	}

	public void drawArena() {
		Sprite sprite;
		
		drawNextTetromino();
		
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
}
