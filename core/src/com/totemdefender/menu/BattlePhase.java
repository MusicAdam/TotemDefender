package com.totemdefender.menu;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BattlePhase extends Menu implements ApplicationListener {
	private ShapeRenderer shapeMaker;
	private Vector2 buttonSize = new Vector2(133, 50);
	private int player1score = 0, player2score = 0;
	private int turnValue = 1;
	
	Button player1 = new Button("Player 1 Score: " + player1score, buttonSize, new Vector2(0,350), Color.RED);
	Button player2 = new Button("Player 2 Score: " + player2score, buttonSize, new Vector2(268,350), Color.BLUE);
	Button gameTurn = new Button("Turn: Player " + turnValue, buttonSize, new Vector2(134,350), Color.GREEN);
	
	public BattlePhase() {
		this.addComponent(player1);
		this.addComponent(player2);
		this.addComponent(gameTurn);
	};
	
	public void create()
	{ shapeMaker = new ShapeRenderer(); }

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		//GL Housekeeping
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.18f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		shapeMaker.begin(ShapeType.Filled);
		
		shapeMaker.setColor(player1.getColor());
		shapeMaker.rect(player1.getPosition().x, player1.getPosition().y, player1.getSize().x, player1.getSize().y);
		
		shapeMaker.setColor(player2.getColor());
		shapeMaker.rect(player2.getPosition().x, player2.getPosition().y, player2.getSize().x, player2.getSize().y);
		
		shapeMaker.setColor(gameTurn.getColor());
		shapeMaker.rect(gameTurn.getPosition().x, gameTurn.getPosition().y, gameTurn.getSize().x, gameTurn.getSize().y);
		
		shapeMaker.end();
	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
