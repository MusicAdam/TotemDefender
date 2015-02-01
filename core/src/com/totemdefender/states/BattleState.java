package com.totemdefender.states;

import com.badlogic.gdx.Input;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.hud.HUD;


public class BattleState implements State {
	private KeyboardEvent spaceDownListener;
	private KeyboardEvent spaceUpListener;
	private WeaponEntity weapon1; //p1's weapon
	private WeaponEntity weapon2; //p2's weapon
	private HUD hud;
	private boolean spaceIsDown = false;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true; // For testing
		//return game.isDoneBuilding();
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
		
		spaceUpListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.SPACE){
			@Override
			public boolean callback(){
				return thisRef.onSpaceUp();
			}
		});
		
		weapon1 = new WeaponEntity(new Player(1));
		weapon1.setName("Weapon 1");
		weapon1.spawn(game);
		game.addEntity(weapon1);
		
		weapon2 = new WeaponEntity(new Player(2));
		weapon2.setName("Weapon 2");
		weapon2.spawn(game);
		game.addEntity(weapon2);
		
		hud = new HUD(game, this);
		game.addMenu(hud);
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
		spaceIsDown = true;
		return true;
	}
	
	private boolean onSpaceUp(){
		spaceIsDown = false;
		return true;
	}
	
	public WeaponEntity getWeapon1(){ return weapon1; }
	public WeaponEntity getWeapon2(){ return weapon2; }
	public boolean spaceIsDown(){ return spaceIsDown; }

}
