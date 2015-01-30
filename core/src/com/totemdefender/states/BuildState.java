package com.totemdefender.states;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.totemdefender.TotemDefender;


public class BuildState implements State {
	
	
	StateManager statemanager;
	Timer timer;
	Task task;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onEnter(final TotemDefender game) {
		
		timer.scheduleTask(new Task(){
			
			public void run(){
				onExit(game);
			}
			
		}, 4000*60);
		
	}
	@Override
	public void onExit(TotemDefender game) {
		statemanager.attachState(new BattleState());
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void update(TotemDefender game) {
		// TODO Auto-generated method stub
		
	}
}
