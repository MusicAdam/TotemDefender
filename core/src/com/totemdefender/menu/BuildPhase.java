package com.totemdefender.menu;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BuildPhase extends Menu implements ApplicationListener {
	private ShapeRenderer shapeMaker;
	private Vector2 buttonSize = new Vector2(75,50);
	
	//this is based on TotemDefender V_Width and height = 400 and 400 respectfully
	Button ready = new Button("READY!", buttonSize, new Vector2(325,350), Color.CYAN);
	Button triangle = new Button("Triangle", buttonSize, new Vector2(0,350), Color.OLIVE);
	Button square = new Button("Square", buttonSize, new Vector2(75,350), Color.YELLOW);
	Button rect = new Button("Rectangle", buttonSize, new Vector2(150,350), Color.RED);
	
	public BuildPhase() {
		this.addComponent(ready);
		this.addComponent(triangle);
		this.addComponent(square);
		this.addComponent(rect);
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
		
		shapeMaker.setColor(ready.getColor());
		shapeMaker.rect(ready.getPosition().x, ready.getPosition().y, ready.getSize().x, ready.getSize().y);
		
		shapeMaker.setColor(triangle.getColor());
		shapeMaker.rect(triangle.getPosition().x, triangle.getPosition().y, triangle.getSize().x, triangle.getSize().y);
		
		shapeMaker.setColor(square.getColor());
		shapeMaker.rect(square.getPosition().x, square.getPosition().y, square.getSize().x, square.getSize().y);
		
		shapeMaker.setColor(rect.getColor());
		shapeMaker.rect(rect.getPosition().x, rect.getPosition().y, rect.getSize().x, rect.getSize().y);
		
		shapeMaker.end();

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