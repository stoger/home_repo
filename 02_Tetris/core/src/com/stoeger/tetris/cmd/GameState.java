package com.stoeger.tetris.cmd;

/**
 *  @author Helml Thomas (tetris specific code)
 *
 *  @version 1.01
 */

/**
 * Enumeration containing the gamestate of the game (communication between tetris client and server)
 */
public enum GameState {
    GAME_WON, GAME_LOST, GAME_STARTING, GAME_RUNNING, GAME_PAUSED, GAME_STOPPED
}

