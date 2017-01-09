package com.stoeger.tetris.Client;

import java.io.File;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.stoeger.tetris.cmd.GameState;
import com.stoeger.tetris.cmd.PlayerAction;
import com.stoeger.tetris.cmd.PollRequest;
import com.stoeger.tetris.Server.TetrisArena;
import com.stoeger.tetris.Server.TetrisServerImp;

public class TetrisClient extends ApplicationAdapter implements InputProcessor {
	private TetrisServerImp server;
	private PollRequest pRequest;
	private PlayerAction userAction;

	private SpriteBatch batch;
	private Sprite mapSprite;
	private BitmapFont font;
	private Texture img;

	private int playerNo;
	private int pauseCount = 1;
	private int lineCount = 0;
	private int highscoreCounter = 0;
	private int countdown = 0;

	private CharSequence lines = "Lines: " + this.lineCount;
	private CharSequence highscore = "Highscore: " + this.highscoreCounter;

	public void create() {
		this.mapSprite = new Sprite(new Texture(Gdx.files.internal("background.png")));
		this.pRequest = new PollRequest("01tris");
		this.server = new TetrisServerImp();
		this.playerNo = 1;
		this.userAction = PlayerAction.NOKEY;
		this.batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		this.font = new BitmapFont();
		this.font.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		this.font.getData().setScale(1.3F, 1.3F);
	}

	public void render() {
		batch.begin();
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384); 

		batch.draw(new Texture("background.png"), 0.0F, 0.0F, 800.0F, 930.0F);
		pRequest.setPlayerNo(this.playerNo);
		pRequest.setPlayerAction(this.userAction);

		if (countdown == 0) {
			drawStartup();
		}

		try { // TODO all this into switch
			this.pRequest = server.pollGameState(pRequest);
			System.out.println(pRequest.getGameState()); // TODO syso here

			switch(pRequest.getGameState()) {
			case GAME_STARTING:
				drawCountdown();
				break;
			case GAME_LOST:
				System.out.println("Lost"); 
				break;
			case GAME_PAUSED:
				drawPaused();
				break;
			case GAME_RUNNING:
				drawArena(pRequest);
				drawTetromino(pRequest);
				drawNextTetromino(pRequest);

				if (pRequest.getActTetronimo().getIsFixed()) {
					lineCount += pRequest.getArena().checkLines();
				}

				lines = ("Lines: " + lineCount);
				font.draw(batch, lines, 600.0F, 600.0F);
				highscoreCounter = lineCount * lineCount * 64;
				highscore = ("Highscore: " + highscoreCounter);
				font.draw(batch, highscore, 600.0F, 620.0F);
				break;
			case GAME_STOPPED:
				drawGameOver();
				break;
			case GAME_WON:
				break;				
			}
			/*
			if (pRequest.getGameState() == GameState.GAME_STARTING) {
				 drawCountdown();
			} else if (pRequest.getGameState() == GameState.GAME_RUNNING) {
				drawArena(pRequest);
				drawTetromino(pRequest);
				drawNextTetromino(pRequest);

				if (pRequest.getActTetronimo().getIsFixed()) {
					lineCount += pRequest.getArena().checkLines();
				}

				lines = ("Lines: " + lineCount);
				font.draw(batch, lines, 600.0F, 600.0F);
				highscoreCounter = lineCount * lineCount * 64;
				highscore = ("Highscore: " + highscoreCounter);
				font.draw(batch, highscore, 600.0F, 620.0F);
			} else if (pRequest.getGameState() == GameState.GAME_STOPPED) {
				drawGameOver();
			} else if (pRequest.getGameState() == GameState.GAME_LOST) {
				System.out.println("Lost"); 
			} else if (pRequest.getGameState() == GameState.GAME_PAUSED) {
				drawPaused();
			}*/
		} catch (Exception e) {
			System.out.println("Problem " + e.getLocalizedMessage() + " occured, beacuse of " + e.getCause());
			e.printStackTrace();
		}
		batch.end();
	}

	public void handleInput(PollRequest request) {
		if ((Gdx.input.isKeyPressed(19)) || (Gdx.input.isKeyJustPressed(19))) {
			userAction = PlayerAction.UP;
		}

		if ((Gdx.input.isKeyPressed(20)) || (Gdx.input.isKeyJustPressed(20))) {
			userAction = PlayerAction.DOWN;
		}

		if ((Gdx.input.isKeyPressed(21)) || (Gdx.input.isKeyJustPressed(21))) {
			userAction = PlayerAction.LEFT;
		}

		if ((Gdx.input.isKeyPressed(22)) || (Gdx.input.isKeyJustPressed(22))) {
			userAction = PlayerAction.RIGHT;
		}

		if ((Gdx.input.isKeyPressed(44)) || (Gdx.input.isKeyJustPressed(44))) {
			if ((pauseCount % 2) == 0) {
				userAction = PlayerAction.PAUSE;
			} else {
				userAction = PlayerAction.RESUME;
			}
			this.pauseCount++;
		}
		
		if ((Gdx.input.isKeyPressed(62)) || (Gdx.input.isKeyJustPressed(62))) {
			userAction = PlayerAction.DROP;
		}
		
		request.setPlayerAction(userAction);
	}

	public void drawTetromino(PollRequest request) {
		int[][] tetromino = request.getActTetronimo().toArray();
		Sprite sprite;

		for (int outCount = tetromino.length; outCount > 0; outCount--) {
			for (int inCount = 0; inCount < tetromino.length; inCount++) {
				if (tetromino[tetromino.length - outCount][inCount] != 0) {
					img = new Texture(Gdx.files.internal("Tetro" + File.separator + tetromino[outCount - 1][inCount] + ".png"));

					sprite = new Sprite(img, 0, 0, img.getHeight(), img.getWidth());
					sprite.setSize(40.0F, 40.0F);

					sprite.setPosition((request.getActTetronimoX() * sprite.getWidth() + inCount * sprite.getWidth()),
							(TetrisArena.HEIGHT - 1 - request.getActTetronimoY() * sprite.getHeight()
									+ (outCount - tetromino.length) * sprite.getHeight()));
					sprite.draw(batch);
				}
			}
		}
	}

	public void drawArena(PollRequest request) {
		Sprite sprite;
		int val;

		for (int outCount = TetrisArena.HEIGHT - 1; outCount >= 0; outCount--) {
			for (int iCount = 0; iCount < TetrisArena.WIDTH; iCount++) {
				val = request.getArena().getArenaContent(iCount, outCount);
				img = new Texture(Gdx.files.internal("Tetro" + File.separator + val + ".png"));

				sprite = new Sprite(img, 0, 0, img.getHeight(), img.getWidth());
				sprite.setSize(40.0F, 40.0F);

				sprite.setPosition(iCount * sprite.getWidth(),
						(TetrisArena.HEIGHT - 1 - outCount) * sprite.getHeight());
				sprite.draw(batch);

				val = 0;
			}
		}
	}

	public void drawNextTetromino(PollRequest request) {
		int[][] tetromino = request.getNextTetronimo().toArray();
		Sprite sprite;

		for (int outCount = tetromino.length; outCount > 0; outCount--) {
			for (int inCount = 0; inCount < tetromino.length; inCount++) {
				if (tetromino[tetromino.length - outCount][inCount] != 0) {
					img = new Texture(Gdx.files.internal("Tetro" + File.separator + tetromino[tetromino.length - outCount][inCount] + ".png"));

					sprite = new Sprite(img, 0, 0, img.getHeight(), img.getWidth());
					sprite.setSize(40.0F, 40.0F);

					sprite.setPosition((TetrisArena.WIDTH * sprite.getWidth() + 100 + inCount * sprite.getWidth()),
							(TetrisArena.HEIGHT * sprite.getHeight() + outCount * sprite.getHeight() - 200));
					sprite.draw(batch);
				}
			}
		}
	}
	
	public void drawCountdown() {
		long startTime = TimeUtils.nanoTime();
		long checkTime = 200000L;
		int time = 3;
		boolean timeFlag = true;
		
		SpriteBatch sBatch = new SpriteBatch();
		sBatch.begin();
		
		if(timeFlag) {
			timeFlag = false;
			img = new Texture(Gdx.files.internal(time + ".png"));
			time--;
			
			sBatch.draw(img, 255.0F, 200.0F);
			
			try {
				Thread.sleep(1000L);
			} catch (Exception e) {
				System.out.println("Problem " + e.getLocalizedMessage() + " occured, beacuse of " + e.getCause());
				e.printStackTrace();
			}
		}
		sBatch.end();
	}

	public void drawGameOver() {
		img = new Texture("game_over.png");
		this.batch.draw(img, 0.0F, 0.0F, 800.0F, 930.0F);
	}

	public void drawPaused() {
		img = new Texture("paused.png");
		this.batch.draw(img, 0.0F, 0.0F, 800.0F, 930.0F);
	}

	public void drawStartup() {
		img = new Texture("start.png");
		this.batch.draw(img, 0.0F, 0.0F, 800.0F, 930.0F);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case 19:
			userAction = PlayerAction.UP;
			break;
		case 20:
			userAction = PlayerAction.DOWN;
			break;
		case 21:
			userAction = PlayerAction.LEFT;
			break;
		case 22:
			userAction = PlayerAction.RIGHT;
			break;
		case 44:
			if ((pauseCount % 2) == 0) {
				userAction = PlayerAction.PAUSE;
			} else {
				userAction = PlayerAction.RESUME;
			}
			this.pauseCount++;
			break;
		case 45:
			userAction = PlayerAction.QUIT;
		case 47:
			userAction = PlayerAction.START;
			countdown++;
			break;
		case 62:
			userAction = PlayerAction.DROP;
			break;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case 19:
			userAction = PlayerAction.NOKEY;
			break;
		case 20:
			userAction = PlayerAction.NOKEY;
			break;
		case 21:
			userAction = PlayerAction.NOKEY;
			break;
		case 22:
			userAction = PlayerAction.NOKEY;
			break;
		case 44:
			userAction = PlayerAction.NOKEY;
			break;
		case 45:
			userAction = PlayerAction.NOKEY;
		case 47:
			userAction = PlayerAction.NOKEY;
			break;
		case 62:
			userAction = PlayerAction.NOKEY;
			break;
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
