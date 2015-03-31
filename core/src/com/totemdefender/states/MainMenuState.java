package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.MainMenu;
import com.totemdefender.player.Player;
import com.totemdefender.states.PreGameState;

public class MainMenuState implements State {

	private MainMenu menu;
	protected boolean startButtonPressed=false;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		game.setPlayer1(new Player(1));
		game.setPlayer2(new Player(2));
		
		menu = new MainMenu(this);
		menu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		menu.destroy(game);
		
		if(startButtonPressed){
			game.getStateManager().attachState(new PreGameState());
		}
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return startButtonPressed;
	}

	@Override
	public void update(TotemDefender game) {}
	
	
	public void startButtonPressed(boolean t){
		startButtonPressed = t;
	}
}
