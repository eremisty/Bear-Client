package me.mods;

import org.lwjgl.input.Keyboard;

import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.INVENTORY;
import me.utilities.TickTimer;
import me.utilities.WORLD;
import me.utilities.WORLD.LookType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

public class ChestLoot extends Mod{
	private boolean getItem = false;
	private TickTimer itemTimer = new TickTimer(500);
	private static boolean close = false;
	private TickTimer closeTimer = new TickTimer(1000);
	
	public ChestLoot() {
		super("ChestLoot", "ChestLoot", Keyboard.KEY_NONE, CATEGORY.MOVEMENT);
	}

	public void onUpdate() {
		if(this.isToggled()) {

			
			/*
			if (getItem == true) {
				if (getItemsFromChest(391, false)) {
					mc.setIngameFocus();
					getItem = false; }
			}
			*/
			
			if (mc.currentScreen instanceof GuiChest && close != true && itemTimer.Tick()) {
				boolean break1 = false;
				if (getItemsFromChest(352, false, 9))
					break1 = true;
				if (break1) {
					getItemsFromChest(391, true, 0);
					close = true;
				}
			}
			
			if (close && closeTimer.Tick() && mc.currentScreen instanceof GuiChest) {
				//mc.player.inventory.closeInventory(mc.player);
				mc.player.closeScreen();
				//mc.setIngameFocus();
				close = false;
			}
			
			if (!(mc.currentScreen instanceof GuiChest)) {
				close = false;
			}
			
			
			//COMMANDS.output(itemTimer.debug());
			
			/*
			if (getItem != false && (mc.currentScreen instanceof GuiChest)) {
				if (itemTimer.Tick()) {
					mc.setIngameFocus();
				}
			}
			*/
			
		}
	}
	
	private boolean getItemsFromChest(int itemID, boolean oneStackOnly, int stackLimit) {
		//returns true if successful
		if (mc.currentScreen instanceof GuiChest) { //if chest gui is open
			ContainerChest chest = (ContainerChest) mc.player.openContainer; 
			
			for (int slot = 0; slot < chest.getLowerChestInventory().getSizeInventory(); slot++) { 
				ItemStack stack = chest.getLowerChestInventory().getStackInSlot(slot); //get the stack of each slot
				if (INVENTORY.getIDFromStack(stack) == itemID) { //check if stack in slot matches desired item
					if (!oneStackOnly) {
						//INVENTORY.clickSlot(slot, true);
						if ((InvManage.stackCounter(itemID)) >= stackLimit) {
							return true;
						}
						mc.playerController.windowClick(chest.windowId, slot, 0, ClickType.QUICK_MOVE, mc.player);
						mc.playerController.updateController();
						//COMMANDS.output(String.valueOf(InvManage.stackCounter(itemID)));
						return false;
					} else if (oneStackOnly) {
						//INVENTORY.clickSlot(slot, true);
						mc.playerController.windowClick(chest.windowId, slot, 0, ClickType.QUICK_MOVE, mc.player);
						mc.playerController.updateController();
						return true;
					}
				}
			}
			//mc.setIngameFocus(); //quits gui
		}
		return true;
	}
	
	public void onEnable() {
		COMMANDS.enableText(this.getName());
		getItem = true;
    }
	public void onDisable() {
		COMMANDS.disableText(this.getName());
	}

}
