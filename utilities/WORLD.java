package me.utilities;

import java.util.ArrayList;

import javax.swing.Spring;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.*;

public class WORLD {
    Minecraft mc =  Minecraft.getMinecraft();	
    private static final Minecraft	mc1	= Minecraft.getMinecraft();
	private ArrayList<Integer> BlockXList;
	private ArrayList<Integer> BlockZList;
	int counterX = 0;
	int counterZ = 0;
	
    //Actions
	public void RightClickBlock (BlockPos blockTargetPos) {	
		mc.playerController.processRightClickBlock(mc.player, mc.world, blockTargetPos,EnumFacing.UP, mc.objectMouseOver.hitVec, EnumHand.MAIN_HAND); 
		mc.rightClickDelayTimer = 4;
		mc.player.swingArm(EnumHand.MAIN_HAND);
		}

	public void DestoryBlock(BlockPos blockTargetPos) {
		mc.player.connection.sendPacket(
		new net.minecraft.network.play.client.CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockTargetPos, EnumFacing.UP));						
		mc.player.swingArm(EnumHand.MAIN_HAND);
		
		/*
        mc.thePlayer.sendQueue.addToSendQueue(
        new net.minecraft.network.play.client.C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockTargetPos, EnumFacing.UP));
        mc.playerController.resetBlockRemoving();
        mc.playerController.blockHitDelay = 5;
        */
	}
	
	//Resources
	public int GetBlockID (BlockPos blockTargetPos) {
		Block block = mc.world.getBlockState(blockTargetPos).getBlock();      
		int blockID = Block.getIdFromBlock(block);
		return blockID;	
	}
	
	public int GetBlockMetaValue (BlockPos blockTargetPos) {
		int meta = mc.world.getBlockState(blockTargetPos).getBlock().getMetaFromState(mc.world.getBlockState(blockTargetPos));
		return meta; 
	}
		
	public WORLD(int PublicRadius) {
	        BlockXList = new ArrayList<Integer>();
	        BlockZList = new ArrayList<Integer>();
	        for(int x = -PublicRadius; x < PublicRadius+1; x++) 
	        	for(int z = -PublicRadius; z < PublicRadius+1; z++) {
	        		BlockXList.add(x);
	        		BlockZList.add(z);
	        	}
	}
	
	/*
    int getArrayPos(int pos) {
      return BlockXList.get(pos);
    }
    */
	
	public int GetBlocksX() {
		counterX++;
		if (counterX == BlockXList.size())
			counterX = 0;
		return BlockXList.get(counterX);
 	}
	
	public int GetBlocksZ() {
		counterZ++;
		if (counterZ == BlockZList.size())
			counterZ = 0;
		return BlockZList.get(counterZ);
 	}
	
	
	//complex shit
	public static EnumFacing faceBlock(BlockPos pos, LookType type) {
		return faceBlock(pos,type, 180, 90);
	}

	public static EnumFacing faceBlock(BlockPos pos, LookType type, float vertLookSpeed, float horzLookSpeed) {
		float[] rotations = getBlockLook(pos);
		float yaw = rotations[0];
		float pitch = rotations[1];
		if (type == LookType.Client) {
			float[] rotation = new float[] { mc1.player.rotationYaw, mc1.player.rotationPitch };
			final float[] newRot = new float[] { yaw, pitch };
			final float[] rotDif = { (float) angleDifference(rotation[0], newRot[0]), (float) angleDifference(rotation[1], newRot[1]) };
			rotation[0] -= ((rotDif[0] >= 0.0f) ? Math.min(vertLookSpeed, rotDif[0]) : Math.max(-vertLookSpeed, rotDif[0]));
			rotation[1] -= ((rotDif[1] >= 0.0f) ? Math.min(horzLookSpeed, rotDif[1]) : Math.max(-horzLookSpeed, rotDif[1]));
			mc1.player.rotationYaw = rotation[0];
			mc1.player.rotationPitch = rotation[1];
		} else if (type == LookType.Server) {
			i++;
			if (mc1.player.onGround ? i >= 8 : i >= 15) {
				mc1.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, pitch, false));
				i = 0;
			}
		}
		double vertThreshold = 50.0;
		if (mc1.world.canBlockSeeSky(pos) || mc1.world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getMaterial() == Material.AIR) return EnumFacing.UP;
		if (pitch >= vertThreshold) return EnumFacing.UP;
		if (pitch <= -vertThreshold) return EnumFacing.DOWN;
		int dir = MathHelper.floor((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3;
		EnumFacing f = EnumFacing.getHorizontal(dir);
		if (f == EnumFacing.NORTH) f = EnumFacing.SOUTH;
		else if (f == EnumFacing.SOUTH) f = EnumFacing.NORTH;
		else if (f == EnumFacing.WEST) f = EnumFacing.EAST;
		else if (f == EnumFacing.EAST) f = EnumFacing.WEST;
		return f;

	}
	
	public static double angleDifference(final float a, final float b) {
		return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
	}
	
	private static int	i	= 0;
	
	public enum LookType {
		Client, Server, None
	}
	
	public static float[] getBlockLook(BlockPos pos) {
		double xDiff = pos.getX() + 0.5 - mc1.player.posX;
		double zDiff = pos.getZ() + 0.5 - mc1.player.posZ;
		double yDiff = pos.getY() - mc1.player.posY - 1.1;
		double horzDiff = (double) MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
		
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (-(Math.atan2(yDiff, horzDiff) * 180.0D / Math.PI));

		return new float[] { mc1.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc1.player.rotationYaw),
				mc1.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc1.player.rotationPitch) };
	}
}