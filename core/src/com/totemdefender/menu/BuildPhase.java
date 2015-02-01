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

public class BuildPhase extends Menu {
	private Button triangle;
	private Button square;
	private Button rect;
	private Button ready;
	
	public BuildPhase(TotemDefender game) {
		Vector2 buttonSize = new Vector2(new Vector2((game.getScreenWidth()/4), 50));
		float topArea = (game.getScreenHeight() - buttonSize.y);
		
		triangle = new Button("Triangle", buttonSize, new Vector2(0,topArea), Color.GREEN);
		square = new Button("Square", buttonSize, new Vector2(buttonSize.x, topArea), Color.YELLOW);
		rect = new Button("Rectangle", buttonSize, new Vector2(buttonSize.x * 2,topArea), Color.RED);
		ready = new Button("READY!", buttonSize, new Vector2((buttonSize.x * 3),topArea), Color.CYAN);
		
		this.addComponent(ready);
		this.addComponent(triangle);
		this.addComponent(square);
		this.addComponent(rect);
	};

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

}