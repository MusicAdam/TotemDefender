package com.totemdefender.states;

import com.totemdefender.TotemDefender;

public interface State {
	public boolean canEnter(TotemDefender game); //Entry condition
	public void onEnter(TotemDefender game); //Called once upon entry
	public void onExit(TotemDefender game); //Called once upon exit
	public boolean canExit(TotemDefender game); //Exit condition
	public void update(TotemDefender game); //Called while the entry condition is true.
}
