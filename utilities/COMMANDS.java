package me.utilities;

import net.minecraft.client.Minecraft;

import javax.swing.Spring;

import org.lwjgl.input.Keyboard;

import me.main.Bear;
import me.mods.Flight;
import me.mods.Mod;
import me.mods.Nuker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class COMMANDS {
	
	public static void output(String s) { 											//send output to variable s
        EntityPlayer player = Minecraft.getMinecraft().player; 			//get player
        player.addChatMessage(new TextComponentString(s));		
        //send msg to player
	}
	
	public static void enableText(String s) {
		COMMANDS.output(s + "§2 ENABLED");
	}
	
	public static void disableText(String s) {
		COMMANDS.output(s + "§4 DISABLED");
	}
	
	public static void input(String s) { 			
		//change keybinds
    	if (s.startsWith(".bind")) 
			COMMANDS.output("Changed keybind");
    	
    	//edit mod settings
    	if (s.startsWith(".ref")) {
    		if (s.contains("nuker")) {
    			COMMANDS.output("§l//Showing settings for Mod: Nuker");
    			Nuker.settings();
    			COMMANDS.output("§l//");
    		}
    	}
    	
    	
    	//lists all available mods
    	if (s.startsWith(".list")) {
    		for(int i = 0; i < Bear.getMods().size() - 1; i++) 
    			COMMANDS.output(Bear.getMods().get(i).getName());
    	}
    	
    	//lists all available commands
    	if (s.startsWith(".help")) {
    		COMMANDS.output(".list");
    		COMMANDS.output(".ref");
    		COMMANDS.output(".bind");
    	}
    	
    	
    	
    	
	}
	
}










/*
public void onCommand(String command, String[] args) throws Exception {
	args[1] = args[1].toUpperCase();
	int key = Keyboard.getKeyIndex(args[1]);
	for(Mod m: mods)
		if(args[0].equalsIgnoreCase(m.getName())){
			m.setKey(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
			COMMANDS.output("Changed Bind to" + args[1]);
		}
*/