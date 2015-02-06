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
import com.totemdefender.menu.Menu;
import com.totemdefender.menu.Button;
import com.totemdefender.states.BattleState;

public class HUD extends Menu{	
	TotemDefender game;
	BattleState battleState;
	Button player1, player2, gameTurn, quit;
	int p1Score = 0, p2Score = 0, playerTurn = 1;
	
	ChargeMeter weapon1ChargeMeter;
	ChargeMeter weapon2ChargeMeter;
	Grid grid;
	
	public HUD(TotemDefender game, BattleState battleState){
		float buttonHeight = game.getScreenHeight()/10;
		float topArea = game.getScreenHeight() - buttonHeight;
		this.game = game;
		this.battleState = battleState;
		
		weapon1ChargeMeter = new ChargeMeter(battleState.getLevel().getPlayer1Weapon());
		weapon2ChargeMeter = new ChargeMeter(battleState.getLevel().getPlayer2Weapon());
		
		player1 = new Button("Player 1", new Vector2((game.getScreenWidth()/4), buttonHeight), 
				new Vector2(0, topArea), new Color(0, 0, 0, 0));
		gameTurn = new Button("Game Turn", new Vector2((game.getScreenWidth()/4), buttonHeight),
				new Vector2((game.getScreenWidth()/4), topArea), new Color(0, 0, 0, 0));
		quit = new Button("QUIT", new Vector2((game.getScreenWidth()/4), buttonHeight), 
				new Vector2((game.getScreenWidth()/2), topArea), new Color(0, 0, 0, 0));
		player2 = new Button("Player 2", new Vector2((game.getScreenWidth()/4), buttonHeight), 
				new Vector2((float) (game.getScreenWidth() * 0.75), topArea), new Color(0, 0, 0, 0));
		
		player1.setTextPosition(player1.getPosition().x + player1.getSize().x/2, player1.getPosition().y + 12);
		player2.setTextPosition(player2.getPosition().x + player1.getSize().x/2, player2.getPosition().y + 12);
		gameTurn.setTextPosition(gameTurn.getPosition().x + player1.getSize().x/2, gameTurn.getPosition().y + 12);
		quit.setTextPosition(quit.getPosition().x + player1.getSize().x/2, quit.getPosition().y + 12);
		
		addComponent(weapon1ChargeMeter);
		addComponent(weapon2ChargeMeter);
		
		addComponent(player1);
		addComponent(player2);
		addComponent(quit);
		addComponent(gameTurn);
		
		this.setShouldRender(true);
	}
}
