package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.hud.HUD;

public class PostGameState implements State {
	HUD hud;
	
	public PostGameState(HUD hud){
		this.hud = hud;
	}
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return game.getWinner() != null;
	}

	@Override
	public void onEnter(TotemDefender game) {
		Label winner = new Label(hud);
		winner.setTextColor(new Color(.3f, .3f, .3f, 1));
		winner.setFont("hud_large.ttf");
		winner.create(game);
		
		if(game.getWinner().getID() == 1){
			winner.setText("Player 1 Wins!");
		}else{
			winner.setText("Player 2 Wins!");			
		}
		winner.setPosition(TotemDefender.V_WIDTH/2 - winner.getWidth()/2, TotemDefender.V_HEIGHT/2);
	}

	@Override
	public void onExit(TotemDefender game) {
		
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false;
	}

	@Override
	public void update(TotemDefender game) {
		
	}
	
}
