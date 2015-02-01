package com.totemdefender.states;

import com.badlogic.gdx.Input;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.KeyboardEvent;

public class BattleState implements State {
	private KeyboardEvent spaceDownListener;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return game.isDoneBuilding();
	}

	@Override
	public void onEnter(TotemDefender game) {
		final BattleState thisRef = this;
		//Add onSpaceDown listener
		spaceDownListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, Input.Keys.SPACE){
			@Override
			public boolean callback(){
				return thisRef.onSpaceDown();
			}
		});
	}

	@Override
	public void onExit(TotemDefender game) {
		game.getInputHandler().removeListener(spaceDownListener);
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false; //Totem is on ground?
	}

	@Override
	public void update(TotemDefender game) {
		
	}
	
	private boolean onSpaceDown(){
		System.out.println("Space down event");
		return true;
	}

}
