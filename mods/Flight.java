package me.mods;

import org.lwjgl.input.Keyboard;

import me.main.CATEGORY;
import me.utilities.COMMANDS;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class Flight extends Mod{

	float flightSpeed = 0.2F;
	
	public Flight() {
		super("Flight", "Flight", Keyboard.KEY_NONE, CATEGORY.MOVEMENT);
	}

	public void onUpdate() {
		if(this.isToggled()) {
			mc.player.capabilities.isFlying = true;
		}
	}
	public void onEnable() {
		mc.player.capabilities.setFlySpeed(flightSpeed);
		mc.player.capabilities.allowFlying = true;
		COMMANDS.output("Flight §2ON!");
    }
	public void onDisable() {
		mc.player.capabilities.isFlying = false;
		mc.player.capabilities.allowFlying = false;
        COMMANDS.output("Flight §4Off!");
	}

}
