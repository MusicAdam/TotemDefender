package com.totemdefender.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
		System.out.println(isValid());
		validate();
	}
	
	public void validate(){
		if(!isValid()){
			sizeToContents();
		}
		setValid(true);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		ScissorStack.pushScissors(getRectangle());
		batch.getTransformMatrix().translate(getPosition().x, getPosition().y, 0); //Positon relative to container position
		shapeRenderer.translate(getPosition().x, getPosition().y, 0);
		for(Component cmp : components){
			cmp.render(batch, shapeRenderer);
		}
		shapeRenderer.translate(-getPosition().x, -getPosition().y, 0);
		batch.getTransformMatrix().translate(-getPosition().x, -getPosition().y, 0); 
		ScissorStack.popScissors();

		super.render(batch, shapeRenderer);
	}
	
	public void attachMouseListeners(InputHandler inputHandler){
		mouseUpListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_DOWN){
			@Override
			public boolean callback(){
				if(pointIsInBounds(mousePosition)){
					for(Component cmp : components){
						if(cmp.pointIsInBounds(mousePosition)){
							setFocus(cmp);
							return cmp.onMouseDown(this);
						}
					}
				}
				return false;
			}
		});
		
		mouseDownListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_UP){
			@Override
			public boolean callback(){
				if(focus != null){
					return focus.onMouseUp(this);
				}
				return false;
			}
		});
		
		mouseMoveListener = inputHandler.addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){
				if(pointIsInBounds(mousePosition)){
					for(Component cmp : components){
						if(cmp.pointIsInBounds(mousePosition)){
							if(!cmp.isMouseOver()){
								cmp.setMouseOver(true);
								System.out.println("THIS THO" + cmp.mouseOver );
								return cmp.onMouseEnter(this);
							}
						}
					}
				}
				
				for(Component cmp : components){
					System.out.println("DOIN IT " + cmp.isMouseOver());
					if(cmp.isMouseOver()){
						System.out.println("THIS");
						if(!cmp.pointIsInBounds(mousePosition)){
							cmp.mouseOver = false;
							return cmp.onMouseExit(this);
						}
					}
				}
				return false;
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
	
	//Calculates size based on attached components
	//TODO: This is a little buggy, needs further testing. Should be okay for now.
	public void sizeToContents(){
		System.out.println("OK");
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
