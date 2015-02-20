package com.totemdefender.menu.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Panel;
import com.totemdefender.menu.Button;
import com.totemdefender.states.BattleState;
import com.totemdefender.states.BuildState;
import com.totemdefender.states.State;
import com.totemdefender.states.StateManager;
import com.totemdefender.states.StateListener;

public class HUD extends Container{	
	private ChargeMeter weapon1ChargeMeter;
	private ChargeMeter weapon2ChargeMeter;
	private Grid player1Grid;
	private Grid player2Grid;
	
	public HUD(TotemDefender game){
		super(null);
		
		//weapon1ChargeMeter = new ChargeMeter(this, battleState.getLevel().getPlayer1Weapon());
		//weapon2ChargeMeter = new ChargeMeter(this, battleState.getLevel().getPlayer2Weapon());
		

		game.getStateManager().addListener(new StateListener(BuildState.class, StateManager.Event.Enter){
			@Override
			public void callback(TotemDefender game, State state){
				onBuildStateEnter(game, (BuildState)state);
			}
		});
		
		game.getStateManager().addListener(new StateListener(BuildState.class, StateManager.Event.Exit){
			@Override
			public void callback(TotemDefender game, State state){
				onBuildStateExit(game, (BuildState)state);
			}
		});
	}
	public void onBuildStateEnter(TotemDefender game, BuildState state){
	}
	
	public void onBuildStateExit(TotemDefender game, BuildState state){
	}
}
