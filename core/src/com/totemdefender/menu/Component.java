package com.totemdefender.menu;

import com.badlogic.gdx.math.Vector2;

public abstract class Component {
	
	private Vector2 size, position;
	public abstract void render();
	
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