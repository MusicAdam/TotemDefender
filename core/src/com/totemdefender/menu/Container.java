package com.totemdefender.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.MouseEvent;

public class Container extends Component{
	protected ArrayList<Component> components; 
	private Component focus;
	private MouseEvent mouseUpListener;
	private MouseEvent mouseDownListener;
	private MouseEvent mouseMoveListener;
	private Rectangle scissors;
	
	public Container(Container parent){
		super(parent);
		components = new ArrayList<Component>();
		scissors = new Rectangle();
	}
	
	public Container(){
		super();
		components = new ArrayList<Component>();
		scissors = new Rectangle();
	}
	
	@Override
	public void create(TotemDefender game){
		super.create(game);
		
		if(parent == null)
			attachMouseListeners(game.getMenuInputHandler());
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
	public void update(TotemDefender game) {
		for(Component cmp : components){
			if(!cmp.isValid())
				setValid(false);
			cmp.update(game);
		}

		validate();
	}
	
	public void validate(){
		if(!isValid()){
			sizeToContents();
			ScissorStack.calculateScissors(TotemDefender.Get().getMenuCamera(), new Matrix4(), new Rectangle(getRectangle()), scissors);
		}
		setValid(true);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		boolean shouldPop = ScissorStack.pushScissors(new Rectangle(scissors));
		batch.getTransformMatrix().translate(getPosition().x, getPosition().y, 0); //Positon relative to container position
		shapeRenderer.translate(getPosition().x, getPosition().y, 0);
		for(Component cmp : components){
			cmp.render(batch, shapeRenderer);
		}
		shapeRenderer.translate(-getPosition().x, -getPosition().y, 0);
		batch.getTransformMatrix().translate(-getPosition().x, -getPosition().y, 0); 
		if(shouldPop)
			ScissorStack.popScissors();

		super.render(batch, shapeRenderer);
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
		}else{
			if(getFocus() != null){
				setFocus(null);
			}
		}
		return false;
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		if(focus != null){
			boolean handled = focus.onMouseUp(event);
			if(focus.pointIsInBounds(worldToLocal(event.mousePosition)))
				handled = handled || focus.onClick();
			setFocus(null);
			return handled;
		}
		return false;
	}
	
	@Override
	public boolean onMouseMove(MouseEvent event){
		if(!isMouseOver()) return false;

		for(Component cmp : components){
			if(cmp.pointIsInBounds(worldToLocal(event.mousePosition))){
				if(!cmp.isMouseOver()){
					cmp.setMouseOver(true);
					return cmp.onMouseEnter(event);
				}
			}else if(cmp.isMouseOver()){
				cmp.setMouseOver(false);
				return cmp.onMouseExit(event);
			}
		}
		return false;
	}
	
	@Override
	public boolean onMouseEnter(MouseEvent event){
		setMouseOver(true);
		
		return true;
	}
	
	@Override
	public boolean onMouseExit(MouseEvent event){
		setMouseOver(false);
		for(Component cmp : components){
			if(cmp.isMouseOver()){
				if(!cmp.pointIsInBounds(worldToLocal(event.mousePosition))){
					cmp.mouseOver = false;
					return cmp.onMouseExit(event);
				}
			}
		}
		
		return true;
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
		mouseUpListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_DOWN){
			@Override
			public boolean callback(){
				return onMouseDown(this);
			}
		});
		
		mouseDownListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_UP){
			@Override
			public boolean callback(){
				return onMouseUp(this);
			}
		});
		
		mouseMoveListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){
				if(pointIsInBounds(mousePosition) && !isMouseOver()){
					return onMouseEnter(this) || onMouseMove(this);
				}else if(!pointIsInBounds(mousePosition) && isMouseOver()){
					return onMouseExit(this) || onMouseMove(this);
				}
				
				return onMouseMove(this);
			}
		});
	}
	
	public void addComponent(Component cmp){
		components.add(cmp);
		setValid(false);
	}
	
	public void removeComponent(Component cmp){
		components.remove(cmp);
		setValid(false);
	}
	
	public void setFocus(Component cmp){
		if(focus != null)
			focus.onLoseFocus();
		focus = cmp;
		if(focus != null)
			focus.onGainFocus();
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
	
}
