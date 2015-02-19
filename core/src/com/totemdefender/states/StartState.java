package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.StartMenu;

public class StartState implements State {
	
private StartMenu startmenu;
protected boolean exit=false;

	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		
		game.addMenu(startmenu);
	}

	@Override
	public void onExit(TotemDefender game) {
		game.removeMenu(startmenu);
		game.getStateManager().attachState(new BuildState());
		
	}

	@Override
	public boolean canExit(TotemDefender game) {
		// TODO Auto-generated method stub
		return exit;
	}

	@Override
	public void update(TotemDefender game) {
		// TODO Auto-generated method stub
		
	}

}
