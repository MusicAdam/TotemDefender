package com.totemdefender.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.buildmenu.BuildMenu;

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
		/*
		shapeRenderer.setProjectionMatrix(game.getEntityCamera().combined);		
		//(0, 0) = Center of screen
		float hw = TotemDefender.V_WIDTH/2;
		float hh = TotemDefender.V_HEIGHT/2;
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
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.circle(0, 0, radius); //Center
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.line(0, topLeft.y, 0, bottomLeft.y);
		shapeRenderer.end();
		*/
		
		
		shapeRenderer.setProjectionMatrix(game.getMenuCamera().combined);		
		//(0, 0) = Bottom left of screen
		float w = TotemDefender.V_WIDTH;
		float h = TotemDefender.V_HEIGHT;
		Vector2 topLeft = new Vector2(0, h);
		Vector2 topRight = new Vector2(w, h);
		Vector2 bottomLeft = new Vector2(0, 0);
		Vector2 bottomRight = new Vector2(w, 0);
		//Test screen/world translations
		Vector2 worldOrigin = game.worldToScreen(new Vector2(0, 0)); //Get the world origin (0, 0) in screen coordinates
		Vector2 originFromWorld = game.screenToWorld(new Vector2(w/2, h/2)); //Get screen origin from worlds origin (relative to screen)
		float radius = 5;
		
		System.out.println(worldOrigin);
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.line(topLeft, bottomRight);//Draw line from top left to bottom right of screen
		shapeRenderer.circle(topLeft.x, topLeft.y, radius); //Circle tl corner
		shapeRenderer.circle(bottomRight.x, bottomRight.y, radius); //Circle br corner
		shapeRenderer.line(topRight, bottomLeft);//Draw line from top right to bottom left of screen
		shapeRenderer.circle(topRight.x, topRight.y, radius); //Circle tl corner
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.circle(originFromWorld.x, originFromWorld.y, radius); //Screen Origin
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.circle(worldOrigin.x, worldOrigin.y, radius); //World origin
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.line(w/2, h, w/2, 0);
		
		shapeRenderer.end();
		
	}

}
