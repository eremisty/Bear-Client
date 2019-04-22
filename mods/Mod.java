package me.mods;

import me.main.CATEGORY;
import net.minecraft.client.Minecraft;

public class Mod {
	public static Minecraft mc = Minecraft.getMinecraft();
	private String Name;
	private String ArrayListName;
	private int Key; 
	private boolean Toggled;
	private CATEGORY Category;
	
	public Mod(String n, String an, int k, CATEGORY c) {
		Name = n;
		ArrayListName = an;
		Key = k;
		Category = c;
		Toggled = false;
	}
	public void toggle() {
		onToggle();
		Toggled = !Toggled;
		if (Toggled) {
			onEnable();
		}else {
			onDisable();
		}
	}
	public void onEnable() {}
	public void onDisable() {}
	public void onUpdate() {}
	public void onRender() {}
	public void onToggle() {}
	
	public Minecraft getMc() {
		return mc;
	}
	public void setMc(Minecraft mc) {
		this.mc = mc;
	}
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	public String getArrayListName() {
		return ArrayListName;
	}
	public void setArrayListName(String ArrayListName) {
		this.Name = ArrayListName;
	}
	public int getKey() {
		return Key;
	}
	public void setKey(int Key) {
		this.Key = Key;
	}
	public boolean isToggled() {
		return Toggled;
	}
	public void setToggled(boolean Toggled) {
		this.Toggled = Toggled;
	}
	public CATEGORY getCategory() {
		return Category;
	}
}
