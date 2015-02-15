package com.totemdefender.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.totemdefender.TotemDefender;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = TotemDefender.V_WIDTH;
		config.height = TotemDefender.V_HEIGHT;
		config.samples = 4;
		config.resizable = false;
		config.fullscreen = false;
		new LwjglApplication(new TotemDefender(), config);
	}
}