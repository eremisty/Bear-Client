package me.GUI;

import me.main.Bear;
import me.utilities.COMMANDS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.*;

public class RenderedGUI extends GuiScreen {
	public RenderedGUI() {
		this.mc = Minecraft.getMinecraft();
	}
	
	public void renderScreen() {
		int directionValue = MathHelper.floor((double)((mc.player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		String direction = "NORTH";
		
		switch(directionValue) {
		  	case 0:
		  		direction = "SOUTH";
		  		break;
		  	case 1:
		  		direction = "WEST";
		  		break;
		  	case 2:
			    direction = "NORTH";
			    break;
		  	case 3:
		  		direction = "EAST";
			    break;
		}		
		
		String xPos = String.format("%.2f", mc.player.posX);
		String zPos = String.format("%.2f", mc.player.posZ);
		String yPos = String.format("%.2f", mc.player.posY);
		
		if (! mc.gameSettings.showDebugInfo && mc.inGameHasFocus) {
			drawRect(2, 2, 60, 40, 0x50000000);
			mc.fontRendererObj.drawString(direction, 4, 4, 0xffffffff);
			mc.fontRendererObj.drawString("X: " + xPos, 4, 12, 0xffffffff);
			mc.fontRendererObj.drawString("Z: " + zPos, 4, 20, 0xffffffff);
			mc.fontRendererObj.drawString("Y: " + yPos, 4, 28, 0xffffffff);
			
			//toggled mod list
			int modList = -1;
	    	for(int i = 0; i < Bear.getMods().size() - 1; i++) {
	    		if (Bear.getMods().get(i).isToggled()) {
	    			modList++;
	    			mc.fontRendererObj.drawString(Bear.getMods().get(i).getName(), 360, 4 +8*modList, 0xFF0000);
	    		}
	    	}	
	
		}
    	
		
	}
}
