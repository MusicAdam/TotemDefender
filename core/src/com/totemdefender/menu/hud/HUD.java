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
import com.totemdefender.menu.Label;
import com.totemdefender.menu.Menu;
import com.totemdefender.menu.Button;
import com.totemdefender.states.BattleState;

public class HUD extends Menu{	
	private TotemDefender game;
	private BattleState battleState;	
	private ChargeMeter weapon1ChargeMeter;
	private ChargeMeter weapon2ChargeMeter;
	private Grid grid;
	
	//private Label p1Score;
	//private Label p2Score;
	
	public HUD(TotemDefender game, BattleState battleState){
		super(game, false);
	
		this.game = game;
		this.battleState = battleState;
		
		weapon1ChargeMeter = new ChargeMeter(this, battleState.getLevel().getPlayer1Weapon());
		weapon2ChargeMeter = new ChargeMeter(this, battleState.getLevel().getPlayer2Weapon());
		
		
		addComponent(weapon1ChargeMeter);
		addComponent(weapon2ChargeMeter);
		
		this.setShouldRender(true);
	}
}
