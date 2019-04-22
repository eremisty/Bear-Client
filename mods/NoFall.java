package me.mods;

import org.lwjgl.input.Keyboard;

import me.main.CATEGORY;
import me.utilities.COMMANDS;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Mod{

	public NoFall() {
		super("NoFall", "NoFall", Keyboard.KEY_NONE, CATEGORY.PLAYER);	
	}
	public void onUpdate() {
		if(this.isToggled()) {
			if(mc.player.fallDistance >= 2F) 
				mc.player.connection.sendPacket(new CPacketPlayer(true));
		}
	}
	public void onEnable() {
		COMMANDS.output("NoFall §2ON");
    }
	public void onDisable() {
		COMMANDS.output("NoFall §4OFF");
	}

}
