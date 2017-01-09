package com.stoeger.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stoeger.tetris.Client.TetrisClient;

public class DesktopLauncher {
	public static void main (String[] arg) {					
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = -1;
		config.foregroundFPS = 60;
		config.height = 1080;
		config.width = 720;
		new LwjglApplication(new TetrisClient(), config);
	}	
}