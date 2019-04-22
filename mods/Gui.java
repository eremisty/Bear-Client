package me.mods;

import org.lwjgl.input.Keyboard;

import me.GUI.ClickGUI;
import me.main.CATEGORY;
import me.utilities.COMMANDS;

public class Gui extends Mod{

	public Gui() {
		super("Gui", "Gui", Keyboard.KEY_RSHIFT, CATEGORY.RENDER);
	}
	
	public void onEnable() {
		mc.displayGuiScreen(new ClickGUI());
		toggle();
    }

}