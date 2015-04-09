package com.totemdefender.states;

import com.totemdefender.Level;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.MainMenu;
import com.totemdefender.player.Player;
import com.totemdefender.states.PreGameState;

public class MainMenuState implements State {

	private MainMenu menu;
	protected boolean startButtonPressed=false;
	protected boolean leaderButtonPressed=false;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		game.setWinner(null);
		game.setPlayer1(new Player(1));
		game.setPlayer2(new Player(2));
		
		if(game.getLevel() == null){
			game.setLevel(new Level(game));
		}else{
			game.getLevel().clearPlayerEntities();
			game.getLevel().createPlayerWeapons();
		}
		
		menu = new MainMenu(this);
		menu.create(game);
		if(game.getMusic() == null || !game.getMusic().isPlaying()){
			game.setMusic("sounds/Menu Music/MainMenu.mp3");
			game.getMusic().play();
			game.getMusic().setLooping(true);
		}
	}

	@Override
	public void onExit(TotemDefender game) {
		menu.destroy(game);
		
		if(startButtonPressed){
			game.getStateManager().attachState(new PreGameState());
		}
		else if(leaderButtonPressed) {
			game.getStateManager().attachState(new LeaderBoardState());
		}
	}

	@Override
	public boolean canExit(TotemDefender game) {
		if(startButtonPressed || leaderButtonPressed) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void update(TotemDefender game) {}
	
	
	public void startButtonPressed(boolean t){
		startButtonPressed = t;
	}
	public void leaderButtonPressed(boolean t){
		leaderButtonPressed = t;
	}

}
