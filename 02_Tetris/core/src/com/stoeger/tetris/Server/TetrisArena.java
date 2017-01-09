/*
Tetris Arena
 ---------------
 - arena[][]: int
 - width
 - height
 ---------------
 + TetrisArena() //opt width/height
 + toString()
 + toArray: int[][]
 - eliminateLine(int)
 + checkLines(): int
 + setTetromino(Tetromino, int, int)
 + addLines(int)
 + reset()
 + getArenaContent(int, int)
 */
package com.stoeger.tetris.Server;

import java.util.*;

public class TetrisArena {
	public static int WIDTH = 10, HEIGHT = 20;
	public int[][] arena = new int[HEIGHT][WIDTH];

	public TetrisArena() {
		// Initializes Arena with zeros on each position
		for (int count = 0; count < HEIGHT; count++) {
			for (int innerCount = 0; innerCount < WIDTH; innerCount++) {
				arena[count][innerCount] = 0;
			}
		}
	}
	
	public int[][] toArray() {
		return arena;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (int count = 0; count < HEIGHT; count++) {
			sb.append(count + "\t\t");
			for (int innerCount = 0; innerCount < WIDTH; innerCount++) {
				sb.append(getArenaContent(innerCount, count));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public int checkLines() {
		int countFields = 0, countDel = 0;

		for (int outterCount = HEIGHT - 1; outterCount != 0; outterCount--) {
			for (int innerCount = 0; innerCount < WIDTH; innerCount++) {
				if (arena[outterCount][innerCount] != 0) {
					countFields++;
				} else if (arena[outterCount][innerCount] == 0) {
					break;
				}
			}

			if (countFields == WIDTH) {
				eliminateLines(outterCount);
				countDel++;
			}

			countFields = 0;
		}

		// return how many rows were cleared
		return countDel;
	}

	private int eliminateLines(int line) { // eliminate Line which checkLines
											// sent
		// Sets specific line back to zero on all blocks - probably not needed
		// to do
		for (int delBlocks = 0; delBlocks < WIDTH; delBlocks++) {
			arena[line][delBlocks] = 0;
		}

		for (int moveLineDown = line - 1; moveLineDown > 0; moveLineDown--) {
			for (int innerCheck = 0; innerCheck < WIDTH; innerCheck++) {
				arena[moveLineDown + 1][innerCheck] = arena[moveLineDown][innerCheck];
			}
		}

		return 0;
	}

	public int setTetromino(Tetromino tetro, int x, int y) {
		int[][] toSet = tetro.toArray();

		for (int counter = 0; counter < toSet.length; counter++) {
			for (int inCounter = 0; inCounter < toSet.length; inCounter++) {
				if (toSet[counter][inCounter] != 0) {
					this.arena[y + counter][x + inCounter] = toSet[counter][inCounter];
				}
			}
		}
		return 0;
	}

	public Boolean checkTetromino(Tetromino tetro, int xWanted, int yWanted, int rotWanted) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (tetro.toArray()[y][x] != 0) {
					// Check x coordinates
					if (x + xWanted > WIDTH - 1 || x + xWanted < 0)
						return false;
					// Check y coordinates
					else if (y + yWanted > HEIGHT - 1 || y + yWanted < 0)
						return false;
					// Check if arena is empty at this coordinates
					else if (this.getArenaContent(x + xWanted, y + yWanted) != 0)
						return false;

				}
			}
		}
		return true;
	}

	public int addLines(int count) {
		int line = arena.length - 1;
		int seed = 7;
		Random rd, setZero;

		for (int anz = count; anz > 0; anz--) {
			// Move all lines up one line
			for (int run = 1; run < HEIGHT; run++) {
				for (int innerRun = 0; innerRun < WIDTH; innerRun++) {
					arena[run - 1][innerRun] = arena[run][innerRun];
					arena[run][innerRun] = 0;
				}
			}

			// Add another line
			for (int add = 0; add < WIDTH; add++) {
				rd = new Random();

				arena[line][add] = rd.nextInt(seed) + 1;
			}

			setZero = new Random();
			arena[line][setZero.nextInt(WIDTH)] = 0;
		}

		return 0;
	}

	public void reset() {
		for (int outterCount = 0; outterCount < HEIGHT; outterCount++) {
			for (int innerCount = 0; innerCount < WIDTH; innerCount++) {
				arena[outterCount][innerCount] = 0;
			}
		}
	}

	public int getArenaContent(int xCoordinate, int yCoordinate) {
		if (xCoordinate < WIDTH && yCoordinate < HEIGHT)
			return arena[yCoordinate][xCoordinate];
		return 0;
	}
}
