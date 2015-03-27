package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.PostGameMenu;
import com.totemdefender.menu.hud.HUD;

public class PostGameState implements State {
	HUD hud;
	PostGameMenu postGameMenu;
	
	public PostGameState(HUD hud){
		this.hud = hud;
	}
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return game.getWinner() != null;
	}

	@Override
	public void onEnter(TotemDefender game) {
		postGameMenu = new PostGameMenu();
		postGameMenu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		postGameMenu.destroy(game);
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false;
	}

	@Override
	public void update(TotemDefender game) {
		
	}
	
}
