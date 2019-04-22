package me.mods;
import java.util.Stack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Timer;

import io.netty.util.Timeout;
import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.INVENTORY;
import me.utilities.TickTimer;
import me.utilities.WORLD;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

public class CarrotNuker extends Mod{
	private int radius = 2; 
	private int delay = 0;
	WORLD instance = new WORLD(radius);

	public CarrotNuker() {
		super("CarrotNuker", "CarrorNuker", Keyboard.KEY_O, CATEGORY.WORLD);
	}
	
	public void onUpdate() {
		if(this.isToggled()) {
			
	 		//hoe
	 		for(int x = -radius; x < radius + 1; x++) {
	 			int blockX = (int) (Math.round(mc.player.posX - 0.5) + x);
	 			for(int z = -radius; z < radius + 1; z++) {
	 				int blockZ = (int) (Math.round(mc.player.posZ - 0.5) + z);
	 				int blockY = (int) (mc.player.posY - 0.5);
	 					
	 				//getting coordinates of ground block
	 				BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
	 				int blockID = instance.GetBlockID(blockPos);  
	 				
	 				//getting coordinates of block above ground
	 				BlockPos blockPosAbove = new BlockPos(blockX, mc.player.posY + 0.5, blockZ);   
	 				int blockIDAbove = instance.GetBlockID(blockPosAbove);  
	 				int blockMetaAbove = instance.GetBlockMetaValue(blockPosAbove);
	 					
	 				if ((blockID == 2 || blockID == 208 || blockID == 3) && mc.world.isAirBlock(blockPosAbove)) {
	 					hoeDirt(blockPos); 
	 				}
	
	 			}
	 		}
		 		
		 		//plant and bone
		 		for(int x = -radius; x < radius + 1; x++) {
		 			int blockX = (int) (Math.round(mc.player.posX - 0.5) + x);
		 			for(int z = -radius; z < radius + 1; z++) {
		 				int blockZ = (int) (Math.round(mc.player.posZ - 0.5) + z);
		 				int blockY = (int) (mc.player.posY - 0.5);
		 					
		 				//getting coordinates of ground block
		 				BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
		 				int blockID = instance.GetBlockID(blockPos);  
		 				
		 				//getting coordinates of block above ground
		 				BlockPos blockPosAbove = new BlockPos(blockX, mc.player.posY + 0.5, blockZ);   
		 				int blockIDAbove = instance.GetBlockID(blockPosAbove);  
		 				int blockMetaAbove = instance.GetBlockMetaValue(blockPosAbove);
		 					
		 				if (blockID == 60 && mc.world.isAirBlock(blockPosAbove)) { //if farmland and no carrot is growing above it (WHY IS THIS REVERSED
		 					//COMMANDS.output("getting carrots");
			 				plantCarrot(blockPos);
		 				}
		 				
						if (blockIDAbove == 141 && blockMetaAbove != 7) {
							boneCarrot(blockPosAbove);
						}
		
		 			}
		 		}
		 		
		 		//harvest
		 		for(int x = -radius; x < radius + 1; x++) {
		 			int blockX = (int) (Math.round(mc.player.posX - 0.5) + x);
		 			for(int z = -radius; z < radius + 1; z++) {
		 				int blockZ = (int) (Math.round(mc.player.posZ - 0.5) + z);
		 				int blockY = (int) (mc.player.posY - 0.5);
		 					
		 				//getting coordinates of ground block
		 				BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
		 				int blockID = instance.GetBlockID(blockPos);  
		 				
		 				//getting coordinates of block above ground
		 				BlockPos blockPosAbove = new BlockPos(blockX, mc.player.posY + 0.5, blockZ);   
		 				int blockIDAbove = instance.GetBlockID(blockPosAbove);  
		 				int blockMetaAbove = instance.GetBlockMetaValue(blockPosAbove);
		 					
		 				//harvest
		 				if (blockIDAbove == 141 && blockMetaAbove == 7) {
		 					instance.DestoryBlock(blockPosAbove);
		 				}
		
		 			}
		 		}
		 		
		 		
		 		
		 		
		 		
		 		
		 		
		 		
		 		
		 		
		 		
			}
		
	}
	public void onEnable() {
		COMMANDS.enableText(this.getName());
    }
	
	public void onDisable() {
		COMMANDS.disableText(this.getName());
	}
	
	private void hoeDirt (BlockPos g) {
		for(int HoeType = 290; HoeType <= 294; HoeType++) {	
			int i = INVENTORY.getSlotWithID(false, HoeType);
			if (i != -1) { //if hoe found in hotbar
				mc.player.inventory.currentItem = i; //set current item hoe
		 		instance.RightClickBlock(g);
				break;
			}
		}	 	
		
	}

	private void plantCarrot (BlockPos c) {
		int i = INVENTORY.getSlotWithID(false, 391);
		if (i != -1) { //if carrot found in hotbar
			mc.player.inventory.currentItem = i; //set current item carrot
			instance.RightClickBlock(c);
		}
	}
	
	private void boneCarrot (BlockPos b) {
		int i = INVENTORY.getSlotWithID(false, 351);
		if (i != -1) { //if bonemeal found in hotbar
			mc.player.inventory.currentItem = i; //set current item bonemeal
			instance.RightClickBlock(b);
		}	
	}
	
	public boolean ShouldIPlant () {
		ItemStack firststack = mc.player.inventoryContainer.getSlot(36).getStack();
		if (INVENTORY.getIDFromStack(firststack) == 391 && firststack.stackSize >= 1) 
			return true;
		else
			return false;
	}

}
