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
import com.totemdefender.menu.Panel;
import com.totemdefender.menu.Button;
import com.totemdefender.states.BattleState;

public class HUD extends Panel{	
	private TotemDefender game;
	private BattleState battleState;	
	private ChargeMeter weapon1ChargeMeter;
	private ChargeMeter weapon2ChargeMeter;
	private Grid grid;
	
	public HUD(TotemDefender game, BattleState battleState){
		super(game, false);
	
		this.game = game;
		this.battleState = battleState;
		
		weapon1ChargeMeter = new ChargeMeter(this, battleState.getLevel().getPlayer1Weapon());
		weapon2ChargeMeter = new ChargeMeter(this, battleState.getLevel().getPlayer2Weapon());
		
		addPanel(weapon1ChargeMeter);
		addPanel(weapon2ChargeMeter);
		
		this.setShouldRender(true);
	}
}
