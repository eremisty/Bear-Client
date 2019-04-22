package me.GUI;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Spring;

import me.main.Bear;
import me.main.CATEGORY;
import me.mods.FullBright;
import me.utilities.COMMANDS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawTitles();
		drawMods();
		//drawRect(200, 200, 250, 250, 0xff3CB371); //background
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		determineModButtons();
		//buttonList.add(new GuiButton(1, 200, 200, 50, 50, "hi"));
	}//

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		/*
		if (button.id == 1) {
			COMMANDS.output("HHIIIII");
		}
		*/
		
		for(int j = 0; j < Bear.getModListSize() - 1; j++) {
			if (button.id == j) {
				Bear.getMods().get(j).toggle(); } }
	}
	
	private void drawTitles () {
		int i = -1;
		for (CATEGORY modType : CATEGORY.values()) {
			i++;
			int xPos = 4 + 90 * i;
			int yPos = 4;
			mc.fontRendererObj.drawString("§l§6" + String.valueOf(modType), xPos, yPos, 0xffffffff);
		}

	}
	
	private void determineModButtons () {
		int i = -1;
		for (CATEGORY modType : CATEGORY.values()) {
			i++;
			int xPos = 4 + 90 * i;

			int u = 0;
			for(int j = 0; j < Bear.getModListSize() - 1; j++) {
				CATEGORY k = Bear.getMods().get(j).getCategory();
				
				if (k == modType) {
					u++;
					
					//determine button coordinates
					int x1 = xPos;
					int y1 = 8 + 10 * u;
					int x2 = xPos + Bear.getMods().get(j).getName().length()*6;
					int y2 = 8 + 10 * u + 8;
					
					buttonList.add(new GuiButton(j, x1, y1, x2 - x1, y2 - y1, ""));
				
				}
			}
		}
	}
	
	private void drawMods () {
		int i = -1;
		for (CATEGORY modType : CATEGORY.values()) {
			i++;
			int xPos = 4 + 90 * i;

			int u = 0;
			for(int j = 0; j < Bear.getModListSize() - 1; j++) {
				CATEGORY k = Bear.getMods().get(j).getCategory();
				
				if (k == modType) {
					u++;
	
					
					//determine button coordinates
					int x1 = xPos;
					int y1 = 8 + 10 * u;
					int x2 = xPos + Bear.getMods().get(j).getName().length()*6;
					int y2 = 8 + 10 * u + 8;
					
					//determine color of mod button and draw mod button
					if (Bear.getMods().get(j).isToggled()) {
						drawRect(x1, y1, x2, y2, 0xff19AA26);
					} else {
						drawRect(x1, y1, x2, y2, 0xffff1f71);
					}
		
					//draws mod text
					mc.fontRendererObj.drawString(Bear.getMods().get(j).getName(), xPos, 8 + 10 * u, 0xffffffff);
				
				}
			}
		}
	}

	//Logic for dragging?
	/*
	protected void mouseClicked (int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k); //(mouseX, mouseY, mouseButton) 
		
		if (5 < i && 20 > i && 10 < j && 20 > j) 
			COMMANDS.output("test button clicked");

		for(int mod = 0; mod < Bear.getModListSize() - 1; mod++) {
			int x1 = modButtons[mod][0];
			int y1 = modButtons[mod][1];
			int x2 = modButtons[mod][2];
			int y2 = modButtons[mod][3];
		
			if (x1 < i && x2 > i && y1 < j && y2 > j)
				Bear.getMods().get(mod).toggle();
		}
		
	}
	*/
	
}
