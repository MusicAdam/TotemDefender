package com.totemdefender.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Component {
	
	private Vector2 size, position;
	public abstract void render(SpriteBatch batch, ShapeRenderer shapeRenderer);
	
	public boolean onClick()
	{ return false; }
	
	public boolean onMouseOver()
	{ return false; }
	
	public boolean onMouseExit()
	{ return false; }

	public Vector2 getSize() 
	{ return size; }

	public void setSize(Vector2 size) 
	{ this.size = size; }

	public Vector2 getPosition() 
	{ return position; }

	public void setPosition(Vector2 position) 
	{ this.position = position; }
	
}