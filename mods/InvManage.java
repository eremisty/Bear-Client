package me.mods;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.main.Bear;
import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.INVENTORY;
import me.utilities.TickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class InvManage extends Mod{
	private boolean sell = true;
	private int delay = 1000;
	private TickTimer c = new TickTimer(delay);
	private TickTimer c1 = new TickTimer(delay);
	private TickTimer c2 = new TickTimer(450);
	private TickTimer c3 = new TickTimer(700);
	private TickTimer c4 = new TickTimer(5000);
	private TickTimer CH1 = new TickTimer(5000);
	private TickTimer CH2 = new TickTimer(5000);
	
	public InvManage() {
		super("InvManage", "InvManage", Keyboard.KEY_I, CATEGORY.PLAYER);	
	}
	
	public void onUpdate() {
		if(this.isToggled()) {

			if (/*mc.currentScreen instanceof GuiInventory &&*/ c.Tick()) { //ensure GUI is open
				convertBoneToBonemeal();
			}
			
			if (c1.Tick()) {
				//COMMANDS.output("moveBonemealIntoHotbar");
				moveBonemealIntoHotbar();
			}
			
			if (c3.Tick()) {
				//COMMANDS.output("moveCarrotsIntoUpperInv");
				moveCarrotsIntoUpperInv();
			}
			
			if (c2.Tick()) {
				//COMMANDS.output("ensureFirstSlotCarrots");
				ensureFirstSlotCarrots();
			}
			
			//if inventory no longer has bones or bonemeal and has more than one stack of carrots, send sell command to Command Assist
			if ((sell && getEntireInvSlotFromItemID(352) == -1 
					&& getEntireInvSlotFromItemID(351) == -1 
					&& stackCounter(391) > 1) && c.Tick()) {
				CommandAssist.sendSell();
			}
			
			//Chest Manager Link
			if (getEntireInvSlotFromItemID(391) == -1 && CH1.Tick()) { 
				ChestManager.findCarrotsChest();
			}
			if (getEntireInvSlotFromItemID(352) == -1 && CH2.Tick()) {
				ChestManager.findBonesChest();
			}
			
			/*
			//sell if ready
			if (mc.currentScreen instanceof GuiChest == false) { //if shop is not open
				int c = getEntireInvSlotFromItemID(351);
				int h = getEntireInvSlotFromItemID(352);
				int k = getEntireInvSlotFromItemID(391);
				if (c == -1 && h == -1 && delay4 > 40 || k == -1) { 
					delay4 = 0;
					mc.setIngameFocus();
					mc.thePlayer.sendChatMessage("/sell all");
					ChestManager.checkChest();
					goAgain = false;
					tt2.resetTick();
					//mc.thePlayer.sendChatMessage("/shop");
				}		
			}
			
			//checks if there are bones in inventory, if so, open crafting inventory
				//mc.thePlayer.stopUsingItem();
			
			
				if (goAgain) {
					if (tt2.Tick()) {
						int ha = getEntireInvSlotFromItemID(352);
						if (ha != -1) { 
							//COMMANDS.output("trying to open inventory!");
							mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
						}		
					}
				}
			*/
			
			
			
		}
	}

	public void onEnable() {
		COMMANDS.enableText(this.getName());
    }
	public void onDisable() {
		COMMANDS.disableText(this.getName());
	}
	
	private void convertBoneToBonemeal () {
		if (emptyInventorySlotCounter() >= 3 && getEntireInvSlotFromItemID(352) != -1) {
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, getEntireInvSlotFromItemID(352), 0, ClickType.PICKUP, mc.player); //click on stack
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 1, 0, ClickType.PICKUP, mc.player); //click on crafting bench
			mc.playerController.updateController();
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.QUICK_MOVE, mc.player); //shiftclick crafted item
		}
	}
	
	private void moveBonemealIntoHotbar () {
		int upperBonemealSlot = getUpperInvSlotFromItemID(351); //finds the first upper inventory occurance of bonemeal

		if (emptyHotbarSlotCounter() != 0 && upperBonemealSlot != -1) { //if there is an empty hotbarslot and bonemeal available slot in upper inventory
			COMMANDS.output("moveBonemealIntoHotbar");
			INVENTORY.clickSlot(upperBonemealSlot, true);
		}	
		mc.playerController.updateController();
	}
	
	private void moveCarrotsIntoUpperInv () {
		for (int slot = 37; slot < 44 + 1; slot++) { //checks hotbar except for first slot
			ItemStack checkCarrots = getStackFromSlot(slot);
			if (INVENTORY.getIDFromStack(checkCarrots) == 391) { //if hotbar contains carrots
				INVENTORY.clickSlot(slot, true); //move carrots to upper inventory
				break;
			}
		}
		mc.playerController.updateController();
	}
	
	private void ensureFirstSlotCarrots () {
		//ensures first slot is carrots
		ItemStack firstStack = getStackFromSlot(36); //get the first stack on first slot
		int firstStackID = INVENTORY.getIDFromStack(firstStack);
		int carrotStackSlot = getUpperInvSlotFromItemID(391);

		if (firstStackID != 391) { //if first slot does not contain carrots
			
			if (!isSlotEmpty(36)) { //if first slot is not empty, get rid of those items
				INVENTORY.clickSlot(36, true); } 
			if (isSlotEmpty(36) && carrotStackSlot != -1) {	//if first slot is empty and carrots are available, move carrots
				INVENTORY.clickSlot(carrotStackSlot, true); } 

		} else if (firstStackID == 391 && firstStack.stackSize <= 32) { //if first slot contains carrots but quantity is less than 32
			INVENTORY.clickSlot(carrotStackSlot, true);
		}
		
		
		mc.playerController.updateController();
	}
	
	//given a slot value, function will obtain the stack at that slot
	public static ItemStack getStackFromSlot (int slot) {
		return mc.player.inventoryContainer.getSlot(slot).getStack();
	}

	//given a slot, function will check to see if the slot is empty
	public boolean isSlotEmpty (int slot) {
		ItemStack stack = getStackFromSlot(slot); //get the first stack on first slot
		int stackID = INVENTORY.getIDFromStack(stack);
		
		if (stackID == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//given an item id, function will search upper inventory for the first instance of item and return the value of that slot
	public int getUpperInvSlotFromItemID (int itemid) {
		for (int i = 9; i < 35 + 1; i++) {
			ItemStack stack = getStackFromSlot(i);
			if (INVENTORY.getIDFromStack(stack) == itemid) 
				return i;
		}
		return -1;
	}
	
	//given an item id, function will search lower (hotbar) inventory for the first instance of item and return the value of that slot
	public int getLowerInvSlotFromItemID (int itemid) {
		for (int i = 36; i < 44 + 1; i++) {
			ItemStack stack = getStackFromSlot(i);
			if (INVENTORY.getIDFromStack(stack) == itemid) 
				return i;
		}
		return -1;
	}
	
	//given an item id, function will search the entire inventory for the first instance of item and return the value of that slot
	public int getEntireInvSlotFromItemID (int itemid) {
		for (int i = 9; i < 44 + 1; i++) {
			ItemStack stack = getStackFromSlot(i);
			//COMMANDS.output(String.valueOf(stack));
			//COMMANDS.output(String.valueOf(stack.stackSize));
			if (INVENTORY.getIDFromStack(stack) == itemid) 
				return i;
		}
		return -1;
	}

	//function will count the number of empty slots in the inventory of a hotbar and return that value
	public int emptyHotbarSlotCounter () {
		int emptycounter = 0;
		for (int slot = 36; slot < 44 + 1; slot++) {
			if (isSlotEmpty(slot))
				emptycounter++;
		}
		return emptycounter;
	}
	
	public static int stackCounter (int ID) {
		int counter = 0;
		for (int slot = 9; slot < 44 + 1; slot++) {
			int stackID = INVENTORY.getIDFromStack(getStackFromSlot(slot));
			if (stackID == ID)
				counter++;
		}
		return counter;
	}
	
	//function will count the number of empty slots in an entire inventory and return that value
	public int emptyInventorySlotCounter () {
		int emptycounter = 0;
		for (int slot = 9; slot < 44 + 1; slot++) {
			if (isSlotEmpty(slot))
				emptycounter++;
		}
		return emptycounter;
	}

}

