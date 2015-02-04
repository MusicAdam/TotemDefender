package com.totemdefender.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.BuildMenu;

public class ResolutionTestState implements State {
	ShapeRenderer shapeRenderer;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void onExit(TotemDefender game) {
		
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false;
	}

	@Override
	public void update(TotemDefender game) {
		shapeRenderer.setProjectionMatrix(game.getCamera().combined);		
		
		//(0, 0) = Center of screen
		float hw = game.getScreenWidth()/2;
		float hh = game.getScreenHeight()/2;
		Vector2 topLeft = new Vector2(-hw, hh);
		Vector2 topRight = new Vector2(hw, hh);
		Vector2 bottomLeft = new Vector2(-hw, -hh);
		Vector2 bottomRight = new Vector2(hw, -hh);
		float radius = 15;
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.line(topLeft, bottomRight);//Draw line from top left to bottom right of screen
		shapeRenderer.circle(topLeft.x, topLeft.y, radius); //Circle tl corner
		shapeRenderer.circle(bottomRight.x, bottomRight.y, radius); //Circle br corner
		shapeRenderer.line(topRight, bottomLeft);//Draw line from top right to bottom left of screen
		shapeRenderer.circle(topRight.x, topRight.y, radius); //Circle tl corner
		shapeRenderer.circle(bottomLeft.x, bottomLeft.y, radius); //Circle br corner
		shapeRenderer.circle(0, 0, radius); //Center
		shapeRenderer.line(0, topLeft.y, 0, bottomLeft.y);
		shapeRenderer.end();
		
	}

}
