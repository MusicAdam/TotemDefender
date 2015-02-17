package com.totemdefender.states;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.BuildMenu;
import com.totemdefender.menu.hud.HUD;


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
		/** TODO: Move to pregame menu */
		game.setPlayer1(new Player(1));
		game.setPlayer2(new Player(2));
		
		level = new Level(game);
		
		p1BuildMenu=new BuildMenu(game, level, game.getPlayer1());
		game.addMenu(p1BuildMenu);
		
		p2BuildMenu=new BuildMenu(game, level, game.getPlayer2());
		game.addMenu(p2BuildMenu);
		
		
		/* Disabling this for now
		  timer.scheduleTask(new Task(){
		 
			
			public void run(){
				exit=true;
			}
			
		}, 4000*60);
		*/
	}
	@Override
	public void onExit(TotemDefender game) {
		game.removeMenu(p1BuildMenu);
		game.removeMenu(p2BuildMenu);
		game.getStateManager().attachState(new BattleState(level));
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		return exit || game.isDoneBuilding();
	}
	@Override
	public void update(TotemDefender game) {
		
		
	}//end of update function
}//end of class
