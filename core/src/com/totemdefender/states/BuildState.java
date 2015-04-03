package com.totemdefender.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.totemdefender.Level;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.buildmenu.BuildMenu;
import com.totemdefender.menu.hud.HUD;
import com.totemdefender.player.Player;


public class BuildState implements State {
	
	//private Timer timer;
	private boolean exit=false;
	private BuildMenu p1BuildMenu;
	private BuildMenu p2BuildMenu;
	private Level level;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}
	@Override
	public void onEnter(final TotemDefender game) {		
		level = new Level(game);
		game.setLevel(level);
		float menuPadding = 10; //Distance from the size of the screen to the menu
		
		p1BuildMenu=new BuildMenu(game, level, game.getPlayer1());
		p1BuildMenu.setPosition(menuPadding, 0);
		p1BuildMenu.setButtonPosition(0, (p1BuildMenu.getSquareBlockSelector().getHeight() * 3) + TotemDefender.V_HEIGHT/2);
		p1BuildMenu.create(game);
		
		//Position the grid
		Vector2 p1PedPos = game.worldToScreen(level.getPlayer1Pedestal().getPosition());
		p1PedPos.add(-(p1BuildMenu.getGrid().getWidth()/2) - 8, TotemDefender.PEDESTAL_HEIGHT/2); //-8 for some reason because it doesn't line up correctly.
		p1BuildMenu.getGrid().setPosition(p1PedPos);
		p1BuildMenu.validate();
		
		Vector2 p2PedPos = game.worldToScreen(level.getPlayer2Pedestal().getPosition());
		
		p2BuildMenu=new BuildMenu(game, level, game.getPlayer2());
		p2BuildMenu.setPosition(p2PedPos.x - p2BuildMenu.getGrid().getWidth()/2, 0);
		p2BuildMenu.setButtonPosition(	p2BuildMenu.getGrid().getWidth()/2, 
										(p2BuildMenu.getSquareBlockSelector().getHeight() * 3) + TotemDefender.V_HEIGHT/2);
		p2BuildMenu.create(game);
		
		p2BuildMenu.getGrid().setPosition(0, TotemDefender.PEDESTAL_HEIGHT/2 + p2PedPos.y);
		p2BuildMenu.validate();
		
		game.setMusic("sounds/Menu Music/Build phase.mp3");
		game.getMusic().play();
		game.getMusic().setLooping(true);
	}
	@Override
	public void onExit(TotemDefender game) {
		p1BuildMenu.destroy(game);
		p2BuildMenu.destroy(game);
		game.setDoneBuilding(true);
		game.getStateManager().attachState(new BattleState(level));
		
		game.getMusic().stop();
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		if(p1BuildMenu == null || p2BuildMenu == null) return false;
		return p1BuildMenu.isDone() && p2BuildMenu.isDone();
	}
	@Override
	public void update(TotemDefender game) {
	}

	public BuildMenu getP1BuildMenu() {
		return p1BuildMenu;
	}
	public BuildMenu getP2BuildMenu() {
		return p2BuildMenu;
	}
}//end of class
