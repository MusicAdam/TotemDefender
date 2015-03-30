package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.PostGameMenu;
import com.totemdefender.menu.ScoreLine;
import com.totemdefender.menu.hud.HUD;

public class PostGameState implements State {
	HUD hud;
	PostGameMenu postGameMenu;
	private boolean shouldExit = false;
	
	public PostGameState(HUD hud){
		this.hud = hud;
	}
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return game.getWinner() != null;
	}

	@Override
	public void onEnter(TotemDefender game) {
		//Fill up empty score lines
		for(int i = 1; i <= 2; i++){
			if(game.getPlayer(i).getScore().getScoreLines(ScoreLine.ScoreType.Destruction).isEmpty()){
				game.getPlayer(i).addScore(ScoreLine.ScoreType.Destruction, 0);
			}
			if(game.getPlayer(i).getScore().getScoreLines(ScoreLine.ScoreType.Miss).isEmpty()){
				game.getPlayer(i).addScore(ScoreLine.ScoreType.Miss, 0);
			}
		}
		postGameMenu = new PostGameMenu(this);
		postGameMenu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		postGameMenu.destroy(game);
		game.getLevel().destroy(game);
		game.setLevel(null);
		game.getStateManager().attachState(new MainMenuState());
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return shouldExit;
	}

	@Override
	public void update(TotemDefender game) {
		
	}
	
	public void setShouldExit(boolean t){
		shouldExit = t;
	}
	
}
