package com.totemdefender.states;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
	FileHandle file = Gdx.files.local("td_ranking.txt");        
	String winnerScore = "";
	Calendar cal = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    String date = dateFormat.format(cal.getTime());
	
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
		FileHandle file = Gdx.files.local("td_ranking.txt");        
		winnerScore = (game.getWinner().getName() + "<>" +  game.getWinner().getScore().getTotalScore() + "<>" + date + "\n");
		file.writeString(winnerScore, true);
		game.setWinner(null);
		game.getLevel().clearPlayerEntities();
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
