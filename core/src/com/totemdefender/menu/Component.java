package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;

public class Component {
	
	protected Vector2 size, position;
	protected Sprite backgroundSprite;
	protected Color backgroundColor;
	protected Menu parent; //Parent menu
	protected boolean selectable; //Determines whether a component in a menu will recieve "onSelect" events and can be traversed/hovered with mouse
	
	public Component(Menu parent){
		position = new Vector2();
		size = new Vector2();
		this.parent = parent;
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(backgroundSprite != null)
			backgroundSprite.draw(batch);
		if(backgroundColor != null){
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(backgroundColor);
			shapeRenderer.rect(position.x, position.y, size.x, size.y);
			shapeRenderer.end();
		}
	}
	public void update(TotemDefender game){}
	
	public boolean onSelect()
	{ return false; }

	public boolean onMouseUp()
	{ return onSelect(); }
	public boolean onCursorOver()
	{ return false; }
	
	public boolean onCursorExit()
	{ return false; }
	
	public boolean onMouseDown()
	{ return false; }
	
	public void onLoseFocus(){}
	public void onGainFocus(){}

	public Vector2 getSize() 
	{ return size; }
	
	public float getWidth()
	{ return size.x; }
	
	public float getHeight()
	{ return size.y;	}
	
	public void setSize(float w, float h){
		setSize(new Vector2(w, h));
	}

	public void setSize(Vector2 size) 
	{ 
		this.size = size;
		parent.calculateSize();
	}

	public Vector2 getPosition() 
	{ return position; }
	
	public void setPosition(float x, float y){
		setPosition(new Vector2(x, y));
	}
	public void setPosition(Vector2 position) 
	{ this.position = position; }
	
	public Sprite getBackgroundSprite() {
		return backgroundSprite;
	}
	public void setBackgroundSprite(Sprite sprite) {
		this.backgroundSprite = sprite;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	/** Checks if given point lies within the component */
	public boolean pointIsInBounds(Vector2 point){
		return (point.x >= position.x &&
				point.x <= position.x + size.x &&
				point.y >= position.y &&
				point.y <= position.y + size.y);
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
}