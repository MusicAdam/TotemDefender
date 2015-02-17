package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.MouseEvent;

public abstract class Component {
	protected Rectangle rectangle; //Mathematical representation of the component
	protected boolean mouseOver;
	protected Component parent;
	private boolean valid; //Set to false after the rectangle changes. used to update containers on "in-need" basis.
	
	public Component(Component parent){
		this.parent = parent;
		rectangle = new Rectangle();
	}
	
	public Component(){
		this.parent = null;
		rectangle = new Rectangle();
	}
	
	public void update(TotemDefender game){
		validate();
	}
	
	public void validate(){
		setValid(true);
	}
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(TotemDefender.DEBUG){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(getPosition().x, getPosition().y, getSize().x, getSize().y);
			shapeRenderer.end();
		}
		
	}
	
	public void create(TotemDefender game){
		game.addMenu(this);
	}
	
	public void destroy(TotemDefender game){
		game.removeMenu(this);
	}

	public boolean onMouseEnter(MouseEvent event){ return false; }
	public boolean onMouseExit(MouseEvent event){ return false; }
	public boolean onMouseDown(MouseEvent event){ return false; }
	public boolean onMouseUp(MouseEvent event){ return false; }
	public void onGainFocus(){}
	public void onLoseFocus(){}
	
	/** Checks if given point lies within the menu */
	public boolean pointIsInBounds(Vector2 point){
		return rectangle.contains(point);
	}

	public Vector2 getPosition() {
		return rectangle.getPosition(new Vector2());
	}

	public void setPosition(Vector2 position) {
		rectangle.setPosition(position);
		setValid(false);
	}
	
	public void setPosition(float x, float y){
		setPosition(new Vector2(x, y));
	}

	public Vector2 getSize() {
		return rectangle.getSize(new Vector2());
	}
	
	public void setSize(Vector2 size){
		rectangle.setSize(size.x, size.y);
		setValid(false);
	}
	
	public void setSize(float w, float h){
		setSize(new Vector2(w, h));
	}
	
	public void setWidth(float w){
		setSize(w, getHeight());
	}
	
	public void setHeight(float h){
		setSize(getWidth(), h);
	}
	
	public float getWidth(){
		return rectangle.getWidth();
	}
	
	public float getHeight(){
		return rectangle.getHeight();
	}
	
	public Rectangle getRectangle(){ return rectangle; }

	public Component getParent() {
		return parent;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	public boolean isMouseOver(){ return mouseOver; }

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void setMouseOver(boolean b) {
		mouseOver = b;
	}
}
