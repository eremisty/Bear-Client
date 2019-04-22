package me.main;

import org.lwjgl.opengl.Display;

public class Title {
	
	public static Title INSTANCE = new Title();
	
	public final static double CLIENT_VERSION = 2.0;
	public final static String CLIENT_NAME = "Bear";

	public static void onEnable() {
		Display.setTitle(CLIENT_NAME + " | " + CLIENT_VERSION);
	}
	
	/*
	public void onDisable() {
		
	}
	*/
}
