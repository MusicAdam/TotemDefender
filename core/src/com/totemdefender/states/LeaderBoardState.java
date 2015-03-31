package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.LeaderBoardMenu;
import com.totemdefender.menu.MainMenu;
import com.totemdefender.player.Player;

public class LeaderBoardState implements State{

	private LeaderBoardMenu menu;
	protected boolean returnButtonPressed=false;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		menu = new LeaderBoardMenu(this);
		menu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		menu.destroy(game);
		game.getStateManager().attachState(new MainMenuState());
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return returnButtonPressed;
	}

	@Override
	public void update(TotemDefender game) {}
	
	
	public void returnButtonPressed(boolean t){
		returnButtonPressed = t;
	}
}
