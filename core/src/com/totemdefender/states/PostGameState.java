package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.hud.HUD;
import com.totemdefender.menu.hud.WinnerStatus;

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
		WinnerStatus winner = new WinnerStatus(hud);
		hud.addPanel(winner);
		
		if(game.getWinner().getID() == 1){
			winner.setText("Player 1 Wins!");
		}else{
			winner.setText("Player 2 Wins!");			
		}
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
