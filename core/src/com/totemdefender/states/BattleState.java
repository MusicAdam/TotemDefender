package com.totemdefender.states;

import com.badlogic.gdx.Input;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.GroundEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.hud.HUD;


public class BattleState implements State {
	private KeyboardEvent spaceDownListener;
	private KeyboardEvent spaceUpListener;
	private KeyboardEvent resetListener;
	private KeyboardEvent pl1UpKeyDownListener;
	private KeyboardEvent pl1UpKeyUpListener;
	private KeyboardEvent pl2UpKeyDownListener;
	private KeyboardEvent pl2UpKeyUpListener;
	private KeyboardEvent pl1DownKeyDownListener;
	private KeyboardEvent pl1DownKeyUpListener;
	private WeaponEntity weapon1; //p1's weapon
	private WeaponEntity weapon2; //p2's weapon
	private GroundEntity ground; //the ground.
	private HUD hud;
	private boolean spaceIsDown = false;
	private Player turn; //the player whose turn it is
	private boolean p1UpKeyDown = false;
	private boolean p2UpKeyDown = false;
	private boolean p1DownKeyDown = false;
	
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
		
		pl1UpKeyDownListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				return thisRef.p1UpKeyDown = true; 
			}
		});
		
		pl1UpKeyUpListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				return thisRef.p1UpKeyDown = false; 
			}
		});
		
		pl2UpKeyDownListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_2_U){
			@Override
			public boolean callback(){
				return thisRef.p2UpKeyDown = true; 
			}
		});
		
		pl2UpKeyUpListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_U){
			@Override
			public boolean callback(){
				return thisRef.p2UpKeyDown = false; 
			}
		});
		
		pl1DownKeyDownListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				return thisRef.p1DownKeyDown = true; 
			}
		});
		
		pl1DownKeyUpListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				return thisRef.p1DownKeyDown = false; 
			}
		});
		
		/** TODO: REMOVE THIS */
		resetListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.R){
			@Override
			public boolean callback(){
				return thisRef.dbg_resetWeapon();
			}
		});
		
		/** TODO: Should be in Pregame state **/
		game.setPlayer1(new Player(1));
		game.setPlayer2(new Player(2));
		turn = game.getPlayer1();
		
		/** Create the world **/
		ground = new GroundEntity();
		ground.setName("Ground");
		ground.spawn(game);
		game.addEntity(ground);
		
		weapon1 = new WeaponEntity(game.getPlayer1());
		weapon1.setName("Weapon 1");
		weapon1.spawn(game);
		game.addEntity(weapon1);
		
		weapon2 = new WeaponEntity(game.getPlayer2());
		weapon2.setName("Weapon 2");
		weapon2.spawn(game);
		game.addEntity(weapon2);
		
		hud = new HUD(game, this);
		game.addMenu(hud);
		
	}

	@Override
	public void onExit(TotemDefender game) {
		game.getInputHandler().removeListener(spaceDownListener);
		game.getInputHandler().removeListener(spaceUpListener);
		game.getInputHandler().removeListener(resetListener);
		game.getInputHandler().removeListener(pl1UpKeyDownListener);
		game.getInputHandler().removeListener(pl1UpKeyUpListener);
		game.getInputHandler().removeListener(pl2UpKeyDownListener);
		game.getInputHandler().removeListener(pl2UpKeyUpListener);
		game.getInputHandler().removeListener(pl1DownKeyDownListener);
		game.getInputHandler().removeListener(pl1DownKeyUpListener);
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false; //Totem is on ground?
	}

	@Override
	public void update(TotemDefender game) {
		if(p1UpKeyDown && game.getPlayer1() == getPlayerTurn())
			weapon1.rotateUp();
		if(p2UpKeyDown && game.getPlayer2() == getPlayerTurn())
			weapon2.rotateUp();
		if(p1DownKeyDown && game.getPlayer1() == getPlayerTurn())
			weapon1.rotateDown();
			
	}
	
	private boolean onSpaceDown(){
		spaceIsDown = true;
		return true;
	}
	
	private boolean onSpaceUp(){
		spaceIsDown = false;
		return true;
	}
	
	private boolean dbg_resetWeapon(){
		weapon1.resetCharge();
		weapon2.resetCharge();
		return true;
	}
	
	private boolean onPlayer1UpKey_down(){
		if(getPlayerTurn().getID() == 1){
			weapon1.rotateDown();
			return true;
		}
		return false;
	}
	
	private boolean onPlayer1UpKey_up(){
		if(getPlayerTurn().getID() == 1){
			weapon1.rotateUp();
			return true;
		}
		return false;
	}
	
	public WeaponEntity getWeapon1(){ return weapon1; }
	public WeaponEntity getWeapon2(){ return weapon2; }
	public boolean spaceIsDown(){ return spaceIsDown; }
	
	public Player getPlayerTurn(){ return turn; }

}
