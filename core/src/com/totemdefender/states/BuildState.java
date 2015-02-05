package com.totemdefender.states;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.BuildMenu;
import com.totemdefender.menu.hud.HUD;


public class BuildState implements State {
	
	//private Timer timer;
	private boolean exit=false;
	private BuildMenu buildMenu;
	private KeyboardEvent player1UpKey_up;
	private KeyboardEvent player1DownKey_up;
	private KeyboardEvent player1LeftKey_up;
	private KeyboardEvent player1RightKey_up;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}
	@Override
	public void onEnter(final TotemDefender game) {
		buildMenu=new BuildMenu(game);
		buildMenu.setShouldRender(true);
		game.addMenu(buildMenu);
		
		
		/* Disabling this for now
		  timer.scheduleTask(new Task(){
		 
			
			public void run(){
				exit=true;
			}
			
		}, 4000*60);
		*/
		
		final BuildState thisRef = this;
		player1UpKey_up = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				return thisRef.onPlayer1UpKey_up();
			}
		});
		player1LeftKey_up = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_L){
			@Override
			public boolean callback(){
				return thisRef.onPlayer1LeftKey_up();
			}
		});
		player1RightKey_up = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_R){
			@Override
			public boolean callback(){
				return thisRef.onPlayer1RightKey_up();
			}
		});
		player1DownKey_up = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				return thisRef.onPlayer1DownKey_up();
			}
		});
	}
	@Override
	public void onExit(TotemDefender game) {
		game.removeMenu(buildMenu);
		game.getInputHandler().removeListener(player1UpKey_up);
		game.getInputHandler().removeListener(player1LeftKey_up);
		game.getInputHandler().removeListener(player1RightKey_up);
		game.getStateManager().attachState(new BattleState());
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		return exit || game.isDoneBuilding();
	}
	@Override
	public void update(TotemDefender game) {
		
		
	}//end of update function
	
	private boolean onPlayer1UpKey_up(){
		buildMenu.getGrid().indexUp();
		return true;
	}
	
	private boolean onPlayer1LeftKey_up(){
		buildMenu.getGrid().indexLeft();
		return true;		
	}
	
	public boolean onPlayer1RightKey_up(){
		buildMenu.getGrid().indexRight();
		return true;				
	}
	
	public boolean onPlayer1DownKey_up(){
		buildMenu.getGrid().indexDown();
		return true;
	}
}//end of class
