package com.totemdefender.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.totemdefender.Level;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.GroundEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.hud.HUD;
import com.totemdefender.player.Player;


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
		game.getStateManager().attachState(new PostGameState(level.getHUD()));
		
		for(BlockEntity ent : level.getPlacedBlocks()){
			ent.getBody().setActive(true);
		}
		
		level.getPlayer1Totem().getBody().setActive(true);
		level.getPlayer2Totem().getBody().setActive(true);
		
		final BattleState thisRef = this;
		//Add onSpaceDown listener
		spaceDownListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, Input.Keys.SPACE){
			@Override
			public boolean callback(){
				return thisRef.onSpaceDown();
			}
		});	
		spaceUpListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.SPACE){
			@Override
			public boolean callback(){
				return thisRef.onSpaceUp();
			}
		});		
		pl1UpKeyDownListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				return thisRef.p1UpKeyDown = true; 
			}
		});		
		pl1UpKeyUpListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				thisRef.p1UpKeyDown = false; 
				return true;
			}
		});		
		pl1DownKeyDownListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				return thisRef.p1DownKeyDown = true; 
			}
		});		
		pl1DownKeyUpListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				thisRef.p1DownKeyDown = false;
				return true;
			}
		});		
		pl2UpKeyDownListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_2_U){
			@Override
			public boolean callback(){
				return thisRef.p2UpKeyDown = true; 
			}
		});		
		pl2UpKeyUpListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_U){
			@Override
			public boolean callback(){
				return thisRef.p2UpKeyDown = false; 
			}
		});
		pl2DownKeyDownListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_2_D){
			@Override
			public boolean callback(){
				return thisRef.p2DownKeyDown = true; 
			}
		});
		pl2DownKeyUpListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_D){
			@Override
			public boolean callback(){
				thisRef.p2DownKeyDown = false; 
				return  true;
			}
		});
		/** TODO: REMOVE THIS */
		resetListener = game.getGameInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.R){
			@Override
			public boolean callback(){
				return thisRef.dbg_resetWeapon();
			}
		});
		
		game.getLevel().getHUD().setDrawScores(true);
	}

	@Override
	public void onExit(TotemDefender game) {
		game.getGameInputHandler().removeListener(spaceDownListener);
		game.getGameInputHandler().removeListener(spaceUpListener);
		game.getGameInputHandler().removeListener(resetListener);
		game.getGameInputHandler().removeListener(pl1UpKeyDownListener);
		game.getGameInputHandler().removeListener(pl1UpKeyUpListener);
		game.getGameInputHandler().removeListener(pl1DownKeyDownListener);
		game.getGameInputHandler().removeListener(pl1DownKeyUpListener);
		game.getGameInputHandler().removeListener(pl2UpKeyDownListener);
		game.getGameInputHandler().removeListener(pl2UpKeyUpListener);
		game.getGameInputHandler().removeListener(pl2DownKeyDownListener);
		game.getGameInputHandler().removeListener(pl2DownKeyUpListener);
	}

	@Override
	public boolean canExit(TotemDefender game) {
		if(level.checkTotemStatus() != null){
			if(level.checkTotemStatus().getID() == 1){
				game.setWinner(game.getPlayer2());
			}else{
				game.setWinner(game.getPlayer1());				
			}
			return true;
		}
		
		return false;
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
