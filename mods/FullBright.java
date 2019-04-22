package me.mods;

import org.lwjgl.input.Keyboard;

import me.main.CATEGORY;
import me.utilities.COMMANDS;

public class FullBright extends Mod{

	public FullBright() {
		super("FullBright", "FullBright", Keyboard.KEY_NONE, CATEGORY.RENDER);
	}
	
	public void onUpdate() {
		if(this.isToggled()) {
			//nothing
		}
		
	}
	public void onEnable() {
		mc.gameSettings.gammaSetting = 100F;
		COMMANDS.enableText(this.getClass().getSimpleName().toString());
    }
	public void onDisable() {
		mc.gameSettings.gammaSetting = 0F;
		COMMANDS.disableText(this.getClass().getSimpleName().toString());
	}

}