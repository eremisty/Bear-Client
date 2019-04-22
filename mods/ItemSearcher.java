package me.mods;

import org.lwjgl.input.Keyboard;

import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.WORLD;
import me.utilities.WORLD.LookType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ItemSearcher extends Mod{
	private int searchRadius = 5;
	private boolean artificalPress = false;
	
	public ItemSearcher() {
		super("ItemSearcher", "ItemSearcher", Keyboard.KEY_N, CATEGORY.MOVEMENT);
	}

	public void onUpdate() {
		if(this.isToggled()) {
			
			boolean itemsFound = false;
			
			for(Object o: mc.world.loadedEntityList) {
				if (o instanceof EntityItem) {
					//COMMANDS.output(String.valueOf(((EntityItem) o).getEntityId()));
					if (((EntityItem) o).getDistanceToEntity(mc.player) < 5 && ((EntityItem) o).getDistanceToEntity(mc.player) > 0.5) {
						//check for type of entity
						//check if entity is hidden
						
						
						itemsFound = true;
						artificalPress = true;
						mc.gameSettings.keyBindForward.pressed = true;

						//COMMANDS.output(String.valueOf(((EntityItem) o).getDistanceToEntity(mc.player)));
						WORLD.faceBlock(((EntityItem) o).getPosition(), LookType.Client);
					}
				}
			}
			
			
			if (artificalPress && !itemsFound) {
				if (mc.gameSettings.keyBindForward.isKeyDown()) {
					mc.gameSettings.keyBindForward.unpressKey();
				}
				artificalPress = false;
			}
			

		}
	}
	public void onEnable() {
		COMMANDS.enableText(this.getName());
    }
	public void onDisable() {
		COMMANDS.disableText(this.getName());
		mc.gameSettings.keyBindForward.unpressKey();
	}

}
