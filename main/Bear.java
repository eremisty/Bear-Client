package me.main;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.GUI.ClickGUI;
import me.GUI.RenderedGUI;
import me.mods.*;
import me.utilities.COMMANDS;
import me.utilities.TickTimer;

public class Bear {
	private static ArrayList<Mod> mods;
	private static RenderedGUI renderedGUI = new RenderedGUI();
	private static ClickGUI clickGUI = new ClickGUI();
	
	public Bear () {
		mods = new ArrayList<Mod>();
		addMod(new Flight());
		addMod(new NoFall());
		addMod(new FullBright());
		addMod(new CarrotNuker()); //*
		addMod(new ChestManager()); //*
		addMod(new ItemSearcher()); //*
		//addMod(new Nuker());
		addMod(new CommandAssist()); //*
		addMod(new InvManage()); //*
		addMod(new ChestLoot()); //*
		addMod(new Gui()); 
		
	}
	
	public static void addMod(Mod m) {
		mods.add(m);
	}
	
	public static ArrayList<Mod> getMods(){

		return mods;
	}
	
	public static int getModListSize() {
		return mods.size();
	}
	
	public static RenderedGUI getRenderedGUI() { //hooked
		return renderedGUI;
	}
	
	public static ClickGUI getClickGUI() { //hooked
		return clickGUI;
	}
	
	public static void onUpdate() {
		for(Mod m: mods) {
			m.onUpdate();
		}
	}
	public static void onRender() {
		for(Mod m: mods) {
			m.onRender();
		}
	}
	public static void onKeyPressed(int var1) {
		//mod toggle
		for(Mod m: mods) {
			if(m.getKey() == var1) {
				m.toggle();
			}
		}
		
	}
}
