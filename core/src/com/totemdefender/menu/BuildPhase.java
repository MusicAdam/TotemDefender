package com.totemdefender.menu;
import com.totemdefender.TotemDefender;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BuildPhase extends Menu implements ApplicationListener {
	private int mainWindowSizeX = TotemDefender.V_WIDTH;
	private int mainWindowSizeY = TotemDefender.V_HEIGHT;
	private Vector2 buttonSize = new Vector2(75,50);
	private ShapeRenderer shapeMaker;
	private float topArea = mainWindowSizeY - buttonSize.y;
		
	private Button triangle = new Button("Triangle", buttonSize, new Vector2(0,topArea), Color.GREEN);
	private Button square = new Button("Square", buttonSize, 
			new Vector2(mainWindowSizeX - (buttonSize.x),topArea), Color.YELLOW);
	private Button rect = new Button("Rectangle", buttonSize, 
			new Vector2(mainWindowSizeX - (buttonSize.x * 2),topArea), Color.RED);
	private Button ready = new Button("READY!", buttonSize, 
			new Vector2((mainWindowSizeX - buttonSize.x),topArea), Color.CYAN);
	
	public BuildPhase() {
		
		this.addComponent(ready);
		this.addComponent(triangle);
		this.addComponent(square);
		this.addComponent(rect);
	};
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		shapeMaker = new ShapeRenderer();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		//GL Housekeeping
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.18f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		triangle.render();
		square.render();
		rect.render();
		ready.render();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode) {
		//player 1
		case Input.Keys.A:
			//position player shape to the left
		case Input.Keys.D:
			//position player shape to the right
		case Input.Keys.S:
			//position player shape to the down
		case Input.Keys.W:
			//position player shape to the up
		
		//player 2
		case Input.Keys.LEFT:
			//position player shape to the left
		case Input.Keys.RIGHT:
			//position player shape to the right
		case Input.Keys.DOWN:
			//position player shape to the bottom
		case Input.Keys.UP:
			//position player shape to the up
		}
		return false;
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