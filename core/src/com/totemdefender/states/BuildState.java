package com.totemdefender.states;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.BuildMenu;


public class BuildState implements State {
	
	
	private StateManager statemanager;
	private Timer timer;
	private boolean exit=false;
	private BuildMenu buildmenu;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return false;
	}
	@Override
	public void onEnter(final TotemDefender game) {
		statemanager=game.getStateManager();
		buildmenu=new BuildMenu(game);
		game.addMenu(buildmenu);
		timer.scheduleTask(new Task(){
			
			public void run(){
				exit=true;
			}
			
		}, 4000*60);
		
	}
	@Override
	public void onExit(TotemDefender game) {
		game.removeMenu(buildmenu);
		statemanager.attachState(new BattleState());
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		
		return exit;
	}
	@Override
	public void update(TotemDefender game) {
		statemanager.detachState(this,canExit(game));
		if(canExit(game)){
			onExit(game);
		}//end of if statement
		
	}//end of update function
}//end of class
