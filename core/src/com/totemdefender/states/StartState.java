package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.MainMenu;
import com.totemdefender.menu.PreGameMenu;

public class StartState implements State {
	
private PreGameMenu menu;
protected boolean readyButtonPressed=false;

	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		menu = new PreGameMenu(this);
		menu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		menu.destroy(game);
		if(readyButtonPressed){
			game.getStateManager().attachState(new BuildState());
		}
	}

	@Override
	public boolean canExit(TotemDefender game) {
		// TODO Auto-generated method stub
		return readyButtonPressed;
	}

	@Override
	public void update(TotemDefender game) {
		// TODO Auto-generated method stub
		
	}

	public void readyButtonPressed(boolean t){
		readyButtonPressed = true;
	}
}
