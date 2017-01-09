package com.stoeger.tetris.Server;

import java.util.Arrays;
import java.util.Random;

public class Tetromino {
	private int id;
	private int rotation;
	private int[][][] matrix;
	private int posx;
	private int posy;
	private Boolean isFixed;

	private static final int matrixI[][][] = { 
			{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };

	private static final int matrixT[][][] = { 
			{ { 0, 0, 0, 0 }, { 2, 2, 2, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 2, 0, 0 }, { 2, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 2, 0, 0 }, { 2, 2, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 2, 0, 0 }, { 0, 2, 2, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 } } };

	private static final int matrixL[][][] = { 
			{ { 0, 0, 0, 0 }, { 3, 3, 3, 0 }, { 3, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 3, 3, 0, 0 }, { 0, 3, 0, 0 }, { 0, 3, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 3, 0 }, { 3, 3, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 3, 0, 0 }, { 0, 3, 0, 0 }, { 0, 3, 3, 0 }, { 0, 0, 0, 0 } } };

	private static final int matrixJ[][][] = { 
			{ { 4, 0, 0, 0 }, { 4, 4, 4, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 4, 4, 0 }, { 0, 4, 0, 0 }, { 0, 4, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 4, 4, 4, 0 }, { 0, 0, 4, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 4, 0, 0 }, { 0, 4, 0, 0 }, { 4, 4, 0, 0 }, { 0, 0, 0, 0 } } };

	private static final int matrixZ[][][] = { 
			{ { 0, 0, 0, 0 }, { 5, 5, 0, 0 }, { 0, 5, 5, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 5, 0 }, { 0, 5, 5, 0 }, { 0, 5, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 5, 5, 0, 0 }, { 0, 5, 5, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 5, 0 }, { 0, 5, 5, 0 }, { 0, 5, 0, 0 }, { 0, 0, 0, 0 } } };

	private static final int matrixS[][][] = { 
			{ { 0, 0, 0, 0 }, { 0, 6, 6, 0 }, { 6, 6, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 6, 0, 0 }, { 0, 6, 6, 0 }, { 0, 0, 6, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 6, 6, 0 }, { 6, 6, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 6, 0, 0 }, { 0, 6, 6, 0 }, { 0, 0, 6, 0 }, { 0, 0, 0, 0 } } };

	private static final int matrixO[][][] = { 
			{ { 0, 7, 7, 0 }, { 0, 7, 7, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 7, 7, 0 }, { 0, 7, 7, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 7, 7, 0 }, { 0, 7, 7, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 7, 7, 0 }, { 0, 7, 7, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };

	public Tetromino() {
		this.id = 0;
		int seed = 7;
		Random rd = new Random();
		this.rotation = 0;

		id = rd.nextInt(seed) + 1;
		System.out.println("ID:\t\t" + id);
		switch (id) {
		case 1:
			this.matrix = matrixI;
			break;
		case 2:
			this.matrix = matrixT;
			break;
		case 3:
			this.matrix = matrixL;
			break;
		case 4:
			this.matrix = matrixJ;
			break;
		case 5:
			this.matrix = matrixZ;
			break;
		case 6:
			this.matrix = matrixS;
			break;
		case 7:
			this.matrix = matrixO;
			break;
		}
	}
	
	// Für Testzwecke: Erstellen bestimmter Tetrominos
	public Tetromino(int id) {
		this.rotation = 0;

		switch (id) {
		case 1:
			this.matrix = matrixI;
			break;
		case 2:
			this.matrix = matrixT;
			break;
		case 3:
			this.matrix = matrixL;
			break;
		case 4:
			this.matrix = matrixJ;
			break;
		case 5:
			this.matrix = matrixZ;
			break;
		case 6:
			this.matrix = matrixS;
			break;
		case 7:
			this.matrix = matrixO;
			break;
		}
	}
	
	@Override
	public String toString() {
		System.out.println("Rotation:\t" + rotation + "\n");
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j<4; j++) {
				System.out.print(matrix[rotation][i][j]);
			}
			System.out.println();
		}
		
		return "Tetromino [matrix=" + Arrays.toString(matrix) + "]";
	}

	public void rotate(){
		if(this.rotation < 3) {
			setRotation(rotation++);
		} else {
			setRotation(0);
		}
	}
	
	public int[][] toArray() {
		return matrix[this.getRotation()];
	}
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		if(rotation == 4)
			this.rotation = 0;
		else
			this.rotation = rotation;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}
	
	public Boolean getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Boolean isFixed) {
		this.isFixed = isFixed;
	}
}
