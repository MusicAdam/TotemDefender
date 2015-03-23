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
	private Level level;
	private Label player1Score;
	private Label player2Score;
	private boolean drawScores=false;
	
	public HUD(TotemDefender game, Level level){
		super(null);
		this.level = level;
		weapon1ChargeMeter = new ChargeMeter(this, level.getPlayer1Weapon());
		weapon1ChargeMeter.create(game);
		weapon2ChargeMeter = new ChargeMeter(this, level.getPlayer2Weapon());
		weapon2ChargeMeter.create(game);
		
		 player1Score =new Label(this);
		 player2Score =new Label(this);

	}	
	
	public void update(TotemDefender game){
		if(drawScores==true){
			player1Score.setFont("hud_medium.ttf");
			player1Score.setPosition(0,750);
			player1Score.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
			
			 
			player2Score.setFont("hud_medium.ttf");
			player2Score.setPosition(1000,750);
			player2Score.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
			
			
			player1Score.create(game);
			player2Score.create(game);
			
			setDrawScores(false);
		}
		
		player1Score.setText("Player1 Score: " + game.getPlayer1().getScore());
		player2Score.setText("Player2 Score: " + game.getPlayer2().getScore());
		super.update(game);
		
	}
	
	public Boolean getDrawScores() {
		return drawScores;
	}

	public void setDrawScores(Boolean drawScores) {
		this.drawScores = drawScores;
	}
	
}