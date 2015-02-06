package com.totemdefender.states;

import com.badlogic.gdx.Input;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.GroundEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
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
	private KeyboardEvent pl2DownKeyDownListener;
	private KeyboardEvent pl2DownKeyUpListener;
	private Level level;
	private HUD hud;
	private boolean spaceIsDown = false;
	private Player turn; //the player whose turn it is
	private boolean p1UpKeyDown = false;
	private boolean p1DownKeyDown = false;
	private boolean p2UpKeyDown = false;
	private boolean p2DownKeyDown = false;
	
	public BattleState(Level level){
		this.level = level;
		turn = TotemDefender.Get().getPlayer1();
	}
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return game.isDoneBuilding();
	}

	@Override
	public void onEnter(TotemDefender game) {
		hud = new HUD(game, this);
		game.addMenu(hud);
		
		for(BlockEntity ent : level.getPlacedBlocks()){
			ent.getBody().setActive(true);
		}
		
		level.getPlayer1Totem().getBody().setActive(true);
		level.getPlayer2Totem().getBody().setActive(true);
		
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
				thisRef.p1UpKeyDown = false; 
				return true;
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
				thisRef.p1DownKeyDown = false;
				return true;
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
		pl2DownKeyDownListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_2_D){
			@Override
			public boolean callback(){
				return thisRef.p2DownKeyDown = true; 
			}
		});
		pl2DownKeyUpListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_D){
			@Override
			public boolean callback(){
				thisRef.p2DownKeyDown = false; 
				return  true;
			}
		});
		/** TODO: REMOVE THIS */
		resetListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.R){
			@Override
			public boolean callback(){
				return thisRef.dbg_resetWeapon();
			}
		});
	}

	@Override
	public void onExit(TotemDefender game) {
		game.getInputHandler().removeListener(spaceDownListener);
		game.getInputHandler().removeListener(spaceUpListener);
		game.getInputHandler().removeListener(resetListener);
		game.getInputHandler().removeListener(pl1UpKeyDownListener);
		game.getInputHandler().removeListener(pl1UpKeyUpListener);
		game.getInputHandler().removeListener(pl1DownKeyDownListener);
		game.getInputHandler().removeListener(pl1DownKeyUpListener);
		game.getInputHandler().removeListener(pl2UpKeyDownListener);
		game.getInputHandler().removeListener(pl2UpKeyUpListener);
		game.getInputHandler().removeListener(pl2DownKeyDownListener);
		game.getInputHandler().removeListener(pl2DownKeyUpListener);
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false; //Totem is on ground?
	}

	@Override
	public void update(TotemDefender game) {
		if(p1UpKeyDown && game.getPlayer1() == getPlayerTurn())
			level.getPlayer1Weapon().rotateUp();
		if(p1DownKeyDown && game.getPlayer1() == getPlayerTurn())
			level.getPlayer1Weapon().rotateDown();
		if(p2UpKeyDown && game.getPlayer2() == getPlayerTurn())
			level.getPlayer2Weapon().rotateUp();
		if(p2DownKeyDown && game.getPlayer2() == getPlayerTurn())
			level.getPlayer2Weapon().rotateDown();
		
		if(turn.getID() == 1){
			if(level.getPlayer1Weapon().isCompleted() && level.getPlayer1Weapon().getProjectile() == null){		
				System.out.println(level.checkActivePlayerEntities(2));
				if(!level.checkActivePlayerEntities(2)){
					turn = game.getPlayer2();
					level.getPlayer1Weapon().resetCharge();
				}
			}
		}else{
			if(level.getPlayer2Weapon().isCompleted() && level.getPlayer2Weapon().getProjectile() == null){
				if(!level.checkActivePlayerEntities(1)){
					turn = game.getPlayer1();
					level.getPlayer2Weapon().resetCharge();
				}		
			}
		}
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
		level.getPlayer1Weapon().resetCharge();
		level.getPlayer2Weapon().resetCharge();
		return true;
	}
	
	public Level getLevel(){ return level; }
	public boolean spaceIsDown(){ return spaceIsDown; }
	
	public Player getPlayerTurn(){ return turn; }

}
