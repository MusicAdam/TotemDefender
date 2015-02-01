package com.totemdefender.menu;
import com.totemdefender.TotemDefender;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BattlePhase extends Menu{
	private int player1score = 0, player2score = 0;
	private int turnValue = 1;
	
	Button player1;
	Button gameTurn;
	Button player2;
	
	public BattlePhase(TotemDefender game) {
		Vector2 buttonSize = new Vector2((game.getScreenWidth()/3),50);
		float topArea = game.getScreenHeight() - buttonSize.y;
		
		player1 = new Button("Player 1 Score: " + player1score, buttonSize, 
				new Vector2(0,buttonSize.y), Color.RED);
		gameTurn = new Button("Turn: Player " + turnValue, buttonSize, 
				new Vector2(buttonSize.x,350), Color.GREEN);
		player2 = new Button("Player 2 Score: " + player2score, buttonSize, 
				new Vector2((buttonSize.x * 2),350), Color.BLUE);
		
		this.addComponent(player1);
		this.addComponent(player2);
		this.addComponent(gameTurn);
	};
}
