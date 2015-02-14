package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Menu;

public class MainMenuState implements State {

	private Menu menu;//will be MainMenu in the future
	protected boolean exit=false;
	
	
	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		game.addMenu(menu);
		
	}

	@Override
	public void onExit(TotemDefender game) {
		game.removeMenu(menu);
	
		
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
