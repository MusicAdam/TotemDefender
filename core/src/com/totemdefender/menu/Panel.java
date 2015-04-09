package com.totemdefender.menu;
import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;

//Base UI element from which all other elemetns derive. 

public class Panel extends Component{
	private InputHandler inputHandler; 
	private MouseEvent mouseUpListener;
	private MouseEvent mouseDownListener;
	private MouseEvent mouseMoveListener;
	private Color color;
	private Sprite sprite;
	private Color highlightColor; 
	private boolean highlighted;
	
	/** Creates a default menu with mouseListeners attached */
	public Panel(Container parent) {
		super(parent);
		
		if(parent == null) throw new NullPointerException("Parent may not be null");
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(!shouldRender()) return;

		if(color != null){
			shapeRenderer.begin(ShapeType.Filled);
			if(!isHighlighted()){
				shapeRenderer.setColor(color);				
			}else{
				shapeRenderer.setColor(getHighlightColor());
			}
			shapeRenderer.rect(getPosition().x, getPosition().y, getSize().x, getSize().y);
			shapeRenderer.end();
		}
		
		if(sprite != null){
			sprite.draw(batch);
		}		

		super.render(batch, shapeRenderer);
	}
	
	@Override
	public boolean onMouseMove(MouseEvent event){
		if(pointIsInBounds(getParent().worldToLocal(event.mousePosition))){
			if(!isMouseOver()){
				onMouseEnter(event);
			}
		}else if(isMouseOver()){
			onMouseExit(event);
		}
		return false;
	}
	public boolean isMouseOver(){
		return mouseOver;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setColor(float r, float g, float b, float a){
		this.color = new Color(r,g,b,a);
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Color getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}