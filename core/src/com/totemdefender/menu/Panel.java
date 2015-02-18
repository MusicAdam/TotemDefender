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
	private Container parent;
	private boolean shouldRender;
	private MouseEvent mouseUpListener;
	private MouseEvent mouseDownListener;
	private MouseEvent mouseMoveListener;
	private Color color;
	private Sprite sprite;
	private Color highlightColor; 
	private boolean highlighted;
	
	/** Creates a default menu with mouseListeners attached */
	public Panel(Container parent) {
		this.parent = parent;
		shouldRender = true;
	}
	
	@Override
	public void create(TotemDefender game){
		super.create(game);
		if(parent == null){
			attachMouseListeners(game.getMenuInputHandler());
		}		
	}

	@Override
	public void destroy(TotemDefender game){
		super.destroy(game);
		if(parent == null){
			game.getMenuInputHandler().removeListener(mouseUpListener);
			game.getMenuInputHandler().removeListener(mouseDownListener);
			game.getMenuInputHandler().removeListener(mouseMoveListener);
		}		
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(!shouldRender) return;

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
	
	//Allows menu to be rendered
	public void show()
	{ setShouldRender(true); }
	
	//Stops menu rendering
	public void hide()
	{ setShouldRender(false); }
	
	public boolean shouldRender() 
	{ return shouldRender; }

	public void setShouldRender(boolean shouldRender) 
	{ this.shouldRender = shouldRender; }
	
	public void attachMouseListeners(InputHandler inputHandler){
		mouseUpListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_DOWN){
			@Override
			public boolean callback(){
				if(pointIsInBounds(mousePosition)){
					return onMouseDown(this);
				}
				return false;
			}
		});
		
		mouseDownListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_UP){
			@Override
			public boolean callback(){
				if(pointIsInBounds(mousePosition)){
					return onMouseUp(this);
				}
				return false;
			}
		});
		
		mouseMoveListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){
				if(pointIsInBounds(mousePosition)){
					if(!mouseOver){
						mouseOver = true;
						return onMouseEnter(this);
					}
				}
				return false;
			}
		});
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