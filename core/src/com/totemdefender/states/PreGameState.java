package com.totemdefender.states;

import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.PreGameMenu;

public class PreGameState implements State {
	//private TextEntry p1Name, p2Name;
	private PreGameMenu p1PreMenu;
	private PreGameMenu p2PreMenu;
	Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/6),(TotemDefender.V_WIDTH/6)/4.65517f); //4.6.. is the apsect ratio of the button texture
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		p1PreMenu = new PreGameMenu(game, game.getPlayer1());
		p1PreMenu.create(game);
		
		p2PreMenu = new PreGameMenu(game, game.getPlayer2());
		p2PreMenu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		p1PreMenu.destroy(game);
		p2PreMenu.destroy(game);
		game.setDoneBuilding(true);
		game.getStateManager().attachState(new BuildState());
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		if(p1PreMenu == null || p2PreMenu == null) return false;
		return p1PreMenu.hasSelected() && p2PreMenu.hasSelected();
	}
	@Override
	public void update(TotemDefender game) {
	}

	public PreGameMenu getP1PreMenu() {
		return p1PreMenu;
	}
	public PreGameMenu getP2PreMenu() {
		return p2PreMenu;
	}
}
