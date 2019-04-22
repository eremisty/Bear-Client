package me.mods;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

import me.GUI.ClickGUI;
import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.TickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class CommandAssist extends Mod {
	private String command = "/tp";
	private int delay = 10000;
	private TickTimer CAtest = new TickTimer(delay);
	private static boolean activated = false;
	
	public CommandAssist() {
		super("CommandAssist", "CommandAssist", Keyboard.KEY_NONE, CATEGORY.PLAYER);
	}
	
	public void settings() {
		ArrayList<String> text = new ArrayList<String>();
		text.add("command (int): §e" + command + "§5§o The command that will be typed.");
		text.add("delay (int): §e" + delay + "§5§o Delay between each command");

		for(int i = 0; i < text.size() - 1; i++) //send text
			COMMANDS.output(text.get(i));
		
	}
	

	
	public void onUpdate() {
		if(this.isToggled()) {
			/*
			if (CAtest.Tick()) {
				if (!command.contains("/"))
					command = "/" + command;
				mc.thePlayer.sendChatMessage(command);
			}
			*/
			
			if (activated) {
				mc.player.sendChatMessage("/sell all");
				activated = false;
			}
		}
	}
	
	public static void sendSell() {
		activated = true;
	}

	public void onEnable() {
		COMMANDS.enableText(this.getName());
    }
	public void onDisable() {
		COMMANDS.disableText(this.getName());
	}

}
