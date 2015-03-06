package com.totemdefender.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.MouseEvent;

public class Container extends Component{
	public static boolean DEBUG_HIGHLIGHT_FOCUS = false;

	protected ArrayList<Component> components; 
	private Component focus;
	private MouseEvent mouseUpListener;
	private MouseEvent mouseDownListener;
	private MouseEvent mouseMoveListener;
	private boolean valid;
	
	public Container(Container parent){
		super(parent);
		components = new ArrayList<Component>();
	}
	
	public Container(){
		super();
		components = new ArrayList<Component>();
	}
	
	@Override
	public void create(TotemDefender game){
		super.create(game);
		
		if(getParent() == null){
			attachMouseListeners(game.getMenuInputHandler());
		}
	}
	
	@Override
	public void destroy(TotemDefender game){
		super.destroy(game);
		
		if(getParent() == null){
			game.getMenuInputHandler().removeListener(mouseUpListener);
			game.getMenuInputHandler().removeListener(mouseDownListener);
			game.getMenuInputHandler().removeListener(mouseMoveListener);
		}
	}
	
	@Override
	public void update(TotemDefender game) {
		for(Component cmp : components){
			cmp.update(game);
		}

		validate();
	}
	
	public void validate(){
		if(!isValid()){
			sizeToContents();
		}
		valid = true;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.getTransformMatrix().translate(getPosition().x, getPosition().y, 0); //Positon relative to container position
		shapeRenderer.translate(getPosition().x, getPosition().y, 0);
		for(Component cmp : components){
			cmp.render(batch, shapeRenderer);
		}
		shapeRenderer.translate(-getPosition().x, -getPosition().y, 0);
		batch.getTransformMatrix().translate(-getPosition().x, -getPosition().y, 0); 
		shapeRenderer.end();
		
		super.render(batch, shapeRenderer);
		
		if(focus != null && DEBUG_HIGHLIGHT_FOCUS){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(0, 0, 1, 1);
			shapeRenderer.rect(focus.getWorldPosition().x, focus.getWorldPosition().y, focus.getWidth(), focus.getHeight());
			shapeRenderer.end();
		}
	}
	
	@Override
	public boolean onMouseDown(MouseEvent event){
		if(pointIsInBounds(event.mousePosition)){
			for(Component cmp : components){
				if(cmp.pointIsInBounds(worldToLocal(event.mousePosition))){
					setFocus(cmp);
					return cmp.onMouseDown(event);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		if(focus != null){
			boolean handled = focus.onMouseUp(event);

			if(focus.pointIsInBounds(worldToLocal(event.mousePosition))){
				handled = handled || focus.onClick();
			}else{
				for(Component cmp : components){
					if(cmp.pointIsInBounds(worldToLocal(event.mousePosition))){
						cmp.onMouseUp(event);
					}
				}
			}
			return handled;
		}
		return false;
	}
	
	@Override
	/** Muse move delegates onMouseEnter/Exit events for this container and child components
	 *  It also sets onMouseOver for this container.
	 */
	public boolean onMouseMove(MouseEvent event){
		Vector2 mousePos;
		if(getParent() == null){
			mousePos = event.mousePosition;
		}else{
			mousePos = getParent().worldToLocal(event.mousePosition);
		}
		
		if(pointIsInBounds(mousePos)){
			//Handled onMouseEnter
			if(!isMouseOver()){
				onMouseEnter(event);
			}
			
			for(Component cmp : components){
				if(cmp.onMouseMove(event)){
					return true;
				}
			}
			return true;
		}else if(isMouseOver()){
			onMouseExit(event);
			return true;
		}
		return false;
	}
	
	@Override
	public void onMouseEnter(MouseEvent event){
		super.onMouseEnter(event);
	}
	
	@Override
	public void onMouseExit(MouseEvent event){		
		for(Component cmp : components){
			if(cmp.isMouseOver()){
				cmp.onMouseExit(event);
			}
		}
		
		super.onMouseExit(event);
	}
	
	/** Pass onClick to components */
	@Override
	public boolean onClick(){
		boolean handled = false;
		for(Component cmp : components){
			handled = handled || cmp.onClick();
		}
		return handled;
	}
	
	public void attachMouseListeners(InputHandler inputHandler){
		mouseDownListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_DOWN){
			@Override
			public boolean callback(){
				return onMouseDown(this);
			}
		});
		
		mouseUpListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_UP){
			@Override
			public boolean callback(){
				return onMouseUp(this);
			}
		});
		
		mouseMoveListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){
				return onMouseMove(this);
			}
		});
	}
	
	public void addComponent(Component cmp){
		components.add(cmp);
		invalidate();
	}
	
	public void removeComponent(Component cmp){
		components.remove(cmp);
		invalidate();
	}
	
	public void setFocus(Component cmp){
		if(focus != null){
			focus.setHasFocus(false);
			focus.onLoseFocus();
		}
		focus = cmp;
		if(focus != null && !focus.hasFocus()){
			focus.setHasFocus(true);
			focus.onGainFocus();
		}
	}
	
	public Component getFocus(){
		return focus;
	}
	
	//Calculates size based on attached components
	public void sizeToContents(){
		for(Component cmp : components){
			//Check if x-bounds exeeds our current width
			if(cmp.getPosition().x + cmp.getWidth() > getSize().x){
				rectangle.width = cmp.getPosition().x + cmp.getWidth();
			}
			
			//Check if y-bounds exeeds our current width
			if(cmp.getPosition().y + cmp.getHeight() > getSize().y){
				rectangle.height = cmp.getPosition().y + cmp.getHeight();
			}
		}
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public void invalidate(){
		valid = false;
		if(getParent() != null)
			getParent().invalidate();
	}
	
	
}
