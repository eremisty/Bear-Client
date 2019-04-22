package me.utilities;

public class TickTimer {
	private int tick = 0;
	private int time = 20;
	private double value = 0;
	
	public TickTimer(int i) {
		value = (i / 1000.0) * 20.0;
		time = (int) value; //coverts to ticks
	}

	public boolean Tick () {
		tick++;
		if (tick >= time) {
			tick = 0;
			return true; 
		}
		return false;
	}
	
	public String debug () {
		return (String.valueOf(tick) + "/" + String.valueOf(time));
	}
	
	public void resetTick () {
		tick = 0;
	}
	
}
