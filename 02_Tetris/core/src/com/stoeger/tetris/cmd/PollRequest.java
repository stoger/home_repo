package com.stoeger.tetris.cmd;

import java.util.Arrays;

import com.stoeger.tetris.Server.TetrisArena;
import com.stoeger.tetris.Server.TetrisServerImp;
import com.stoeger.tetris.Server.Tetromino;

/**
 *  @author Hell Ferdinand (network components)
 *  @author Helml Thomas (tetris specific code)
 *
 *  @version 1.0
 */

public class PollRequest implements Request {

    /**
     *
     */
    private static final long serialVersionUID = 4901226751889619197L;

    protected String game;

    public PollRequest(String gameId) {
        this.game = gameId;
    }

    /**
     * {@link Request} reply of the server reply
     */
//	PollRequest result;

    /**
     * {@link PlayerAction} player action, depending on the key a player pressed -> server
     */
    private PlayerAction playerAction;

    /**
     * number of the player (0 .. Player 1 / 1 .. Player 2) -> server
     */
    private int playerNo;

    /**
     * {@link GameState} game state of a player -> client
     */
    private GameState gameState;

    /**
     * player's own game arena represented by a 10x20 int array
     */
    private TetrisArena arena;

    /**
     * player's actual tetronimo he is in control of, represented by a 4x4 int array
     */
    private Tetromino actTetronimo;

    /**
     * x-position of the player's actual tetronimo
     */
    private int actTetronimoX;

    /**
     * x-position of the player's actual tetronimo
     */
    private int actTetronimoY;

    /**
     * preview of the next tetronimo the player will get
     */
    private Tetromino nextTetronimo;

    /**
     * the player's actual score
     */
    private int score;

    /**
     * the player's actual level
     */
    private int level;

    /**
     * the player's remaining time until the next level
     */
    private long time;

    private TetrisServerImp server;
    /** ********************************************************************************************** */

    /**
     *  @return {@link Request} the servers reply of it's request method
     */
    @Override
    public Request execute(TetrisServer srv) throws Exception {
        // return the servers reply of it's request method
        return /*result =*/ srv.pollGameState(this);
    }

    /**
     *  @return {@link Request} the clients reply of it's request method
     */
    @Override
    public Request execute(TetrisClient cln) throws Exception {
        //cln.pollGameState(this);
        return null;///*result=*/cln.pollGameState(this);
    }

    public String getGame() {
        return game;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PollRequest [text=" + game //+ ", result=" + result
                + ", playerAction=" + playerAction + ", playerNo=" + playerNo
                + ", gameState=" + gameState + ", arena="
                + Arrays.toString(arena.toArray()) + ", actTetronimo="
                + Arrays.toString(actTetronimo.toArray()) + ", actTetronimoX="
                + actTetronimoX + ", actTetronimoY=" + actTetronimoY
                + ", nextTetronimo=" + Arrays.toString(nextTetronimo.toArray())
                + ", score=" + score + ", level=" + level + ", time=" + time
                + "]";
    }

    /**
     *  @return returns the {@link PlayerAction}
     */
    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    /**
     * @param sets the {@link PlayerAction}
     */
    public void setPlayerAction(PlayerAction k) {
        this.playerAction = k;
    }

    /**
     * @return returns the number of the player (0 .. Player 1 / 1 .. Player 2)
     */
    public int getPlayerNo() {
        return playerNo;
    }

    /**
     * @param sets the number of the player (0 .. Player 1 / 1 .. Player 2)
     */
    public void setPlayerNo(int player) {
        this.playerNo = player;
    }

    /**
     * @return {@link GameState} returns the player's actual gamestate => Client
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @param {@link GameState} sets the actual gamestate of the player => Client
     */
    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    /**
     * @return returns the player's own game arena represented by a 10x20 byte array => Client
     */
    public TetrisArena getArena() {
        return arena;
    }

    /**
     * @param sets the player's own game arena represented by a 10x20 byte array => Client
     */
    public void setArena(TetrisArena arena) {
        this.arena = arena;
    }

    /**
     * @return the actual tetronimo the player is in control of
     */
    public Tetromino getActTetronimo() {
        return actTetronimo;
    }

    /**
     * @param sets the actual tetronimo the player is in control of
     */
    public void setActTetronimo(Tetromino act) {
        this.actTetronimo = act;
    }

    /**
     * @return returns the x-position of the actual tetronimo
     */
    public int getActTetronimoX() {
        return actTetronimoX;
    }

    /**
     * @param changes the x-position of the actual tetronimo
     */
    public void setActTetronimoX(int actX) {
        this.actTetronimoX = actX;
    }

    /**
     * @return returns the y-position of the actual tetronimo
     */
    public int getActTetronimoY() {
        return actTetronimoY;
    }

    /**
     * @param changes the x-position of the actual tetronimo
     */
    public void setActTetronimoY(int actY) {
        this.actTetronimoY = actY;
    }

    /**
     * @return returns the preview of the next tetronimo the player will get
     */
    public Tetromino getNextTetronimo() {
        return nextTetronimo;
    }

    /**
     * @param changes the preview of the next tetronimo the player will get
     */
    public void setNextTetronimo(Tetromino next) {
        this.nextTetronimo = next;
    }

    /**
     * @return returns the player's actual score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param sets the player's actual score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return returns the player's actual level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param sets the player's actual level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return returns the player's remaining time until the next level
     */
    public long getTime() {
        return time;
    }

    /**
     * @param sets the player's remaining time until the next level
     */
    public void setTime(long time) {
        this.time = time;
    }

    public void setServer(TetrisServerImp server) {
        this.server = server;
    }
    
    public TetrisServerImp getServer() {
        return server;
    }
}
