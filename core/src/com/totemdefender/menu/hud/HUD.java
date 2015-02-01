package com.totemdefender.menu.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.menu.Menu;
import com.totemdefender.states.BattleState;

public class HUD extends Menu{	
	TotemDefender game;
	BattleState battleState;
	
	ChargeMeter weapon1ChargeMeter;
	ChargeMeter weapon2ChargeMeter;
	
	public HUD(TotemDefender game, BattleState battleState){
		this.game = game;
		this.battleState = battleState;
		
		weapon1ChargeMeter = new ChargeMeter(battleState.getWeapon1());
		weapon2ChargeMeter = new ChargeMeter(battleState.getWeapon2());
		
		addComponent(weapon1ChargeMeter);
		addComponent(weapon2ChargeMeter);
		
		this.setShouldRender(true);
	}
}
