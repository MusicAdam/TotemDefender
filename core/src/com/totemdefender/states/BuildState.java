package com.totemdefender.states;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.totemdefender.TotemDefender;


public class BuildState implements State {
	
	
	private StateManager statemanager;
	private Timer timer;
	private Task task;
	private boolean exit=false;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return false;
	}
	@Override
	public void onEnter(final TotemDefender game) {
		statemanager=game.getStateManager();
		
		timer.scheduleTask(new Task(){
			
			public void run(){
				exit=true;
			}
			
		}, 4000*60);
		
	}
	@Override
	public void onExit(TotemDefender game) {
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
