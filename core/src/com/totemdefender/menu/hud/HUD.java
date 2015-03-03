package com.totemdefender.menu.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Panel;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.buildmenu.Grid;
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
	private Level level;
	
	public HUD(TotemDefender game, Level level){
		super(null);
		this.level = level;
		weapon1ChargeMeter = new ChargeMeter(this, level.getPlayer1Weapon());
		weapon1ChargeMeter.create(game);
		weapon2ChargeMeter = new ChargeMeter(this, level.getPlayer2Weapon());
		weapon2ChargeMeter.create(game);
	//private Label p1Score;
	//private Label p2Score;
	
	}
}