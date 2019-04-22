package me.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;

import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.INVENTORY;
import me.utilities.TickTimer;
import me.utilities.WORLD;
import me.utilities.WORLD.LookType;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockChest.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class ChestManager extends Mod{
	private int searchRadius = 10;
	private static boolean findChestCarrotsTrap = false;
	private static boolean findChestBonesBasic = false;
	WORLD instance = new WORLD(1);
	private TickTimer GuiD = new TickTimer(1000);
	
	public ChestManager() {
		super("ChestManager", "ChestManager", Keyboard.KEY_Y, CATEGORY.WORLD);
	}

	public void onUpdate() {
		if(this.isToggled()) {
			
			
			
			
			
			if (findChestCarrotsTrap) { //for carrots
				if(searchChest(BlockChest.Type.TRAP, searchRadius, true)) {
					findChestCarrotsTrap = false;
				}
			}
			
			
			if (findChestBonesBasic) { //for bone
				if(searchChest(BlockChest.Type.BASIC, searchRadius, true)) {
					findChestBonesBasic = false;
				}
			}
			

			
			
			
			
		}
	}
	
	
	
	private boolean searchChest (Type chestType, int radius, boolean click) {
		//chestType refers to the type of chest
		//radius refers to search radius
		//click refers to whether the chest will be clicked when found
		//the return value refers to whether the chest is sucessfully found within the radius (and clicked if boolean click is true)

		for(Object o: mc.world.loadedTileEntityList) {
			if (o instanceof TileEntityChest) {
				Type type = ((TileEntityChest) o).getChestType();
				double distanceToPlayer = Math.sqrt(((TileEntityChest) o).getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ));
				
				if (distanceToPlayer < radius && type == chestType && ((TileEntityChest) o).getPos().getY() == mc.player.posY) {
					
					WORLD.faceBlock(((TileEntityChest) o).getPos(), LookType.Client);
	
					if (click && distanceToPlayer < 2) {
						//reset move
						if (mc.gameSettings.keyBindForward.isKeyDown()) {
							mc.gameSettings.keyBindForward.unpressKey(); }
						
						
						if (!(mc.currentScreen instanceof GuiChest)) {
							instance.RightClickBlock(((TileEntityChest) o).getPos()); 
							//COMMANDS.output("Chest found, clicking"); //chest found within clickable range, clicking
							return true;
						} else {	
							//COMMANDS.output("Chest found, within range of clicking but unable to click?"); 
							return false;
						}

					} else if (click && distanceToPlayer > 2) {
						mc.gameSettings.keyBindForward.pressed = true; 
						//COMMANDS.output("Chest found, not in range, moving");
						return false; //chest found but not within clickable range, moving
					} else if (!click) {
						//COMMANDS.output("Chest found, facing");
						return true; //chest found within range 
					}
				}
			}
		}
		//COMMANDS.output("Chest not found");
		return false; //no chests available in loaded region
	}

	public static void findCarrotsChest () {
		findChestCarrotsTrap = true;
	}
	
	public static void findBonesChest () {
		findChestBonesBasic = true;
	}
	
	public void onEnable() {
		COMMANDS.enableText(this.getName());
		findChestCarrotsTrap = true;
		findChestBonesBasic = true;
    }
	public void onDisable() {
		COMMANDS.disableText(this.getName());
		if (mc.gameSettings.keyBindForward.isKeyDown()) {
			mc.gameSettings.keyBindForward.unpressKey(); }
	}

}






/* 
//possible things to look into
	//mc.thePlayer.rotationYaw = mc.thePlayer.getRotationYawHead() + 90;
	//mc.thePlayer.moveEntity(instance.GetBlocksX(), 0, instance.GetBlocksZ());
	//mc.thePlayer.moveToBlockPosAndAngles(blockTargetPos, 0, 0);
	//mc.playerController.resetBlockRemoving();	
*/