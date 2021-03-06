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
	private boolean shouldRender = true;	
	private Container parent;
	
	private boolean hasFocus;
	private boolean valid = false;
	
	public Component(Container parent){
		this.parent = parent;
		rectangle = new Rectangle();
		invalidate();
	}
	
	public Component(){
		this.parent = null;
		rectangle = new Rectangle();
		invalidate();
	}
	
	public void update(TotemDefender game){
		if(!isValid())
			validate();
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(!shouldRender()) return;
		
		if(TotemDefender.DEBUG){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(getPosition().x, getPosition().y, getSize().x, getSize().y);
			shapeRenderer.end();
		}
	}
	
	public void create(TotemDefender game){
		if(this instanceof Container && parent == null){
			game.addMenu((Container)this);
		}else if(parent != null){
			parent.addComponent(this);
		}
	}
	
	public void destroy(TotemDefender game){
		if(this instanceof Container && parent == null){
			game.removeMenu((Container)this);
		}else if(parent != null){
			parent.removeComponent(this);
		}
	}
	public void onMouseEnter(MouseEvent event){ setMouseOver(true); }
	
	public void onMouseExit(MouseEvent event){ setMouseOver(false); }
	
	public boolean onMouseMove(MouseEvent event){ return false; }
	
	public boolean onMouseDown(MouseEvent event){ return false; }
	
	public boolean onMouseUp(MouseEvent event){ return false; }
	
	public boolean onClick(){return false;} //Called when mouseup falls on the component
	
	public boolean onKeyboardSelect(){ return onClick(); } //Called when selected with the keyboard
	
	public void onGainFocus(){}
	
	public void onLoseFocus(){}
	

	public boolean onUpKeyDown(){ return false; }
	
	public boolean onUpKeyUp(){ return false; }
	
	public boolean onDownKeyDown(){ return false; }
	
	public boolean onDownKeyUp(){ return false; }
	
	public boolean onLeftKeyDown(){ return false; }
	
	public boolean onLeftKeyUp(){ return false; }
	
	public boolean onRightKeyDown(){ return false; }
	
	public boolean onRightKeyUp(){ return false; }
	
	public boolean onSelectKeyUp(){ return false; }
	
	/** Checks if given point lies within the menu */
	public boolean pointIsInBounds(Vector2 point){ return rectangle.contains(point); }
	
	public Vector2 getPosition() { 
		/*if(!isValid())
		validate();*/
		return new Vector2(rectangle.x, rectangle.y); 
	}
	
	public void setPosition(Vector2 position) {
		rectangle.setPosition(position);
	}
	
	public void setPosition(float x, float y){
		setPosition(new Vector2(x, y));
	}

	public Vector2 getSize() {
		/*if(!isValid())
		validate();*/
		return rectangle.getSize(new Vector2());
	}
	
	public void setSize(Vector2 size) {
		rectangle.setSize(size.x, size.y);
	}
	
	public void setSize(float w, float h){
		setSize(new Vector2(w, h));
	}
	
	public void setWidth(float w){ setSize(w, getHeight()); }
	
	public void setHeight(float h) { setSize(getWidth(), h); }
	
	public float getWidth(){
		return rectangle.getWidth();
	}
	
	public float getHeight(){
		/*if(!isValid())
			validate();*/
		return rectangle.getHeight();
	}
	
	public Rectangle getRectangle(){ 
		/*if(!isValid())
		validate();*/
		return rectangle; 
	}
	
	public Container getParent() { return parent;}
	
	public void setParent(Container parent) { 
		this.parent = parent;
	}
	
	public boolean isMouseOver(){ return mouseOver; }
	
	public void setMouseOver(boolean b) { mouseOver = b; }
	
	//Gets the local point from a world point
	public Vector2 worldToLocal(Vector2 worldPoint){
		/*if(!isValid())
		validate();*/
		if(parent == null){
			return new Vector2(worldPoint.x - rectangle.x, worldPoint.y - rectangle.y);
		}else{
			float x = worldPoint.x - (parent.getPosition().x + getPosition().x);
			float y = worldPoint.y - (parent.getPosition().y + getPosition().y);
			return new Vector2(x, y);
		}
	}
	
	public Vector2 getWorldPosition(){
		if(parent == null){
			return new Vector2(rectangle.x, rectangle.y);
		}else{
			return new Vector2(rectangle.x, rectangle.y).add(parent.getWorldPosition());
		}
	}

	public boolean hasFocus() {
		return hasFocus;
	}

	public void setHasFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}
	
	public void validate(){  
		valid = true;
	}
	
	public void invalidate(){
		invalidate(false);
	}
	
	public void invalidate(boolean parentOnly){
		if(getParent() != null)
			getParent().invalidate(true);
		
		if(!parentOnly)
			valid = false;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public void show()
	{ setShouldRender(true); }
	
	public void hide()
	{ setShouldRender(false); }
	
	public boolean shouldRender() 
	{ return shouldRender; }

	public void setShouldRender(boolean shouldRender) 
	{ this.shouldRender = shouldRender; }
}
