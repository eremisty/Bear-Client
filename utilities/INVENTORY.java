package me.utilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class INVENTORY {
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static NonNullList<ItemStack> getArmorStacks() {
		return mc.player.inventory.armorInventory;
	}

	public static List<ItemStack> getToolbarStacks() {
		ArrayList<ItemStack> itemstacks = new ArrayList<ItemStack>();
		for (int i = 36; i < 45; i++) 
			itemstacks.add(mc.player.inventoryContainer.getSlot(i).getStack());
		return itemstacks;
	}

	public static List<ItemStack> getUpperInvStacks() {
		ArrayList<ItemStack> itemstacks = new ArrayList<ItemStack>();
		for (int i = 0; i < 36; i++)
			itemstacks.add(mc.player.inventoryContainer.getSlot(i).getStack());
		return itemstacks;
	}

	public static int getStackCountOf(int id, boolean upperInv) {
		for (ItemStack is : upperInv ? getUpperInvStacks() : getToolbarStacks()) {
			if (getIDFromStack(is) == id) return is.stackSize;
		}
		return -1;
	}

	public static void clickSlot(int slot, int mouseButton, boolean shiftClick) {
		mc.playerController.windowClick(mc.currentScreen == null ? 0 : mc.player.inventoryContainer.windowId, slot, mouseButton, shiftClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, mc.player);
	}

	public static void clickSlot(int slot, boolean shiftClick) {
		mc.playerController.windowClick(mc.currentScreen == null ? 0 : mc.player.inventoryContainer.windowId, slot, 0, shiftClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, mc.player);
	}

	public static void doubleClickSlot(int slot) {
		mc.playerController.windowClick(mc.currentScreen == null ? 0 : mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(mc.currentScreen == null ? 0 : mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP_ALL, mc.player);
		mc.playerController.windowClick(mc.currentScreen == null ? 0 : mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
	}

	public static int getIDFromStack(ItemStack is) {
		return (is != null) ? is.getItem().getIdFromItem(is.getItem()) : -1;
	}

	public static int getSlotWithID(boolean upperInv, int id) {
		if (upperInv) {
			int i = 9;
			for (ItemStack is : INVENTORY.getUpperInvStacks()) {
				if (INVENTORY.getIDFromStack(is) == id) return i;
				i++;
			}
		} 
		else {
			int i = 0;
			for (ItemStack is : INVENTORY.getToolbarStacks()) {
				if (INVENTORY.getIDFromStack(is) == id) return i;
				i++;
			}
		}
		return -1;
	}
}
