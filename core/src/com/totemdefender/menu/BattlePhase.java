package com.totemdefender.menu;
import com.totemdefender.TotemDefender;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BattlePhase extends Menu implements ApplicationListener {
	private int mainWindowSizeX = TotemDefender.V_WIDTH;
	private int mainWindowSizeY = TotemDefender.V_HEIGHT;
	private Vector2 buttonSize = new Vector2((mainWindowSizeX/3),50);
	private float topArea = mainWindowSizeY - buttonSize.y;
	private ShapeRenderer shapeMaker;
	private int player1score = 0, player2score = 0;
	private int turnValue = 1;
	
	Button player1 = new Button("Player 1 Score: " + player1score, buttonSize, 
			new Vector2(0,buttonSize.y), Color.RED);
	Button gameTurn = new Button("Turn: Player " + turnValue, buttonSize, 
			new Vector2(buttonSize.x,350), Color.GREEN);
	Button player2 = new Button("Player 2 Score: " + player2score, buttonSize, 
			new Vector2((buttonSize.x * 2),350), Color.BLUE);
	
	public BattlePhase() {
		this.addComponent(player1);
		this.addComponent(player2);
		this.addComponent(gameTurn);
	};
	
	@Override
	public void create()
	{ shapeMaker = new ShapeRenderer(); }

	@Override
	public void render() {
		// TODO Auto-generated method stub
		//GL Housekeeping
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.18f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player1.render();
		player2.render();
		gameTurn.render();
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
