package me.mods;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import io.netty.util.Timeout;
import me.main.Bear;
import me.main.CATEGORY;
import me.utilities.COMMANDS;
import me.utilities.INVENTORY;
import me.utilities.WORLD;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

public class Nuker extends Mod{
	static int radius = 5; 
	static int radiusMode = 0;
	static boolean cOri = true;
	static boolean autoBreak = true;
	static int breakMode = 0;
	static float seqSpeed = 1;
	WORLD instance = new WORLD(radius);

	public Nuker() {
		super("Nuker", "Nuker", Keyboard.KEY_NONE, CATEGORY.WORLD);
	}
	
	public static void settings() {
		
		ArrayList<String> text = new ArrayList<String>();
		text.add("radius (int): §e" + radius);
		text.add("radiusMode (int): §e" + radiusMode + "§5§o (0:flat, 1:sphere)");
		text.add("centeredOrientation (int): §e" + cOri);
		text.add("autoBreak (boolean): §e" + autoBreak);
		text.add("breakingMode (int): §e" + breakMode + "§5§o (0:all/creative, 1:seq/creative, §5§o2:all/survival, 3:seq/survival)");	
		text.add("sequentialSpeed (float): §e" + seqSpeed);
		
		for(int i = 0; i < text.size() - 1; i++) {
			COMMANDS.output(text.get(i));
		}
		
	}

	public void onUpdate() {
		if(this.isToggled()) {
			
			if (radius > 10) {
				radius = 10;
			}

	 		for(int x = -radius; x < radius + 1; x++) {
	 			int blockX = (int) (Math.round(mc.player.posX - 0.5) + x);
	 			for(int z = -radius; z < radius + 1; z++) {
	 					int blockZ = (int) (Math.round(mc.player.posZ - 0.5) + z);
	 					int blockY = (int) (mc.player.posY);
	 					
	 					BlockPos blockTargetPos = new BlockPos(blockX, blockY, blockZ);          //Specify position of target block
	 					Block block = mc.world.getBlockState(blockTargetPos).getBlock();      //Focuses on the block at targeted position
	 					int blockID = Block.getIdFromBlock(block);								 //Gets ID of focused block
	 					if (blockID != 0) {														 //Break block if it isn't air
	 						mc.player.connection.sendPacket(
	 						new net.minecraft.network.play.client.CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockTargetPos, EnumFacing.UP));
	 					}
	 				}
	 			/*
	 			ArrayList<BlockPos> blockQueue = new ArrayList<BlockPos>();
	 			instance.GetBlockID(blockQueue.get(0));
	 			blockQueue.remove(0);
	 			cars.remove(0);
	 			*/
	 			
	 		}
		}
	}

	public void onEnable() {
		COMMANDS.enableText(this.getClass().getSimpleName().toString());
    }
	
	public void onDisable() {
		COMMANDS.disableText(this.getClass().getSimpleName().toString());
	}

}
