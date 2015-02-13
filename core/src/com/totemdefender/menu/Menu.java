package com.totemdefender.menu;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;

//Base menu from which all other menus derive. Should provide the ability
//to add Buttons and Text. Should have its own input processor which its component buttons will attach to. 

public class Menu extends InputHandler{
	private ArrayList<Component> components = new ArrayList<Component>();
	private boolean shouldRender;
	private Component mouseOver; //The component the mouse is over, used for mouseEnter/Exit events
	private Component focus; //This will be set to the last component that was click on, or selected by keyboard.
	private int index;       //This is used for navigating with keyboard input and represents where the cursor is currently
	private Vector2 position, size;
	
	private boolean traverseIndexDown = false;
	private boolean traverseIndexUp = false;
	private long lastTraversalTime = 0;
	private long traversalTime = 250; //Time between movement (miliseconds)
	
	/** Creates a default menu with mouseListeners attached */
	public Menu(TotemDefender game, boolean attachMouseListeners) {
		super(game);
		
		initialize(attachMouseListeners);
	}
	
	public Menu(TotemDefender game){
		super(game);
		
		initialize(true);
	}
	
	/** Used to for constructor override */
	private final void initialize(boolean attachMouseListeners){		
		if(attachMouseListeners){
			attachMouseListeners();
		}		
		
		index = -1;
		shouldRender = true;
		position = new Vector2();
		size = new Vector2();
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(!shouldRender) return;
		
		for(Component cmp : components){
			Matrix4 tmp = batch.getTransformMatrix().cpy();
			batch.getTransformMatrix().translate(-position.x, -position.y, 0); //Positions will be reletive to menu position
			shapeRenderer.translate(position.x, position.y, 0);
			cmp.render(batch, shapeRenderer);
			shapeRenderer.identity(); //Undo translation
			batch.setTransformMatrix(tmp);
		}
		
		if(TotemDefender.DEBUG){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(position.x, position.y, size.x, size.y);
			shapeRenderer.end();
		}
	}
	
	public void update(TotemDefender game){
		if(traverseIndexDown && shouldTraverse()){
			indexDown();
		}else if(traverseIndexUp && shouldTraverse()){
			indexUp();			
		}
		
		for(Component cmp : components){
			cmp.update(game);
		}
	}
	
	private boolean shouldTraverse(){
		return (System.currentTimeMillis() - lastTraversalTime > traversalTime);
	}
	
	//Calculates size based on attached components
	public void calculateSize(){
		size.x = 0;
		size.y = 0;
		for(Component component : components){
			//Check if x-bounds exeeds our current width
			if(component.getPosition().x + component.getWidth() > size.x){
				size.x = component.getPosition().x + component.getWidth();
			}
			
			//Check if y-bounds exeeds our current width
			if(component.getPosition().y + component.getHeight() > size.y){
				size.y = component.getPosition().y + component.getHeight();
			}
		}
	}
	
	//Allows menu to be rendered
	public void show()
	{ setShouldRender(true); }
	
	//Stops menu rendering
	public void hide()
	{ setShouldRender(false); }
	
	//Adds a component (Should attach to inputprocessor if menu is visible)
	public void addComponent(Component cmp)
	{ 
		components.add(cmp);
		calculateSize();
	}
	
	//Removes a component
	public void removeComponent(Component cmp)
	{ 
		components.remove(cmp);
		calculateSize();
	} 
	
	//Adds a listener to the listener list
	public void addListener(InputProcessor listener, ArrayList<InputProcessor> list)
	{ list.add(listener); }

	//Removes a listener
	public void removeListener(InputProcessor listener, ArrayList<InputProcessor> list)
	{ list.remove(listener); }
	
	public boolean shouldRender() 
	{ return shouldRender; }

	public void setShouldRender(boolean shouldRender) 
	{ this.shouldRender = shouldRender; }

	public Component getFocus() {
		return focus;
	}

	public void setFocus(Component focus, boolean setIndex) {
		this.focus = focus;
		
		//Set the index to this so it stay synchronized if the user uses the mouse
		if(setIndex && focus != null){
			index = components.lastIndexOf(focus);
		}else if(focus == null){
			index = -1;
		}
		
		if(focus != null){
			System.out.println("Focus: " + focus.getBackgroundColor());
		}else{
			System.out.println("Focus: null");			
		}
		
	}
	
	public void setFocus(Component focus){
		setFocus(focus, true);
	}
	
	public void setFocus(int index){
		this.index = index;
		setFocus(components.get(index), false);
	}
	
	public void indexDown(int count){
		if(count >= components.size()){
			index = -1;
			return;
		}
		
		if(index == -1){
			index = 0;
		}else{
			index--;
			
			if(index < 0)
				index = components.size() - 1;
		}

		if(!components.get(index).isSelectable())
			indexDown(count++);
		
		setFocus(index);
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public void indexDown(){
		indexDown(0);
	}
	
	public void indexUp(){
		indexUp(0);
	}
	public void indexUp(int count){
		if(count >= components.size()){
			index = -1;
			return;
		}
		
		if(index == -1){
			index = 0;
		}else{		
			index++;
			
			if(index == components.size())
				index = 0;
		}
		
		if(!components.get(index).isSelectable())
			indexUp();
		setFocus(index);
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public void setIndex(Component cmp){
		index = components.indexOf(cmp);
	}
	
	public void attachPlayer1Listeners(){
		/** Up Key down Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				indexDown();
				traverseIndexDown = true;
				return true;
			}
		});
		/** Up Key Up Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_U){
			@Override
			public boolean callback(){
				traverseIndexDown = false;
				return true;
			}
		});
		/** Down Key down Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				indexDown();
				traverseIndexDown = true;
				return true;
			}
		});
		/** Down Key Up Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_D){
			@Override
			public boolean callback(){
				traverseIndexDown = false;
				return true;
			}
		});
		/** Select Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_1_SELECT){
			@Override
			public boolean callback(){
				if(getFocus() != null)
					getFocus().onSelect();
				return true;
			}
		});		
	}
	
	public void attachPlayer2Listeners(){
		/** Up Key down Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_2_U){
			@Override
			public boolean callback(){
				indexDown();
				traverseIndexDown = true;
				return true;
			}
		});
		/** Up Key Up Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_U){
			@Override
			public boolean callback(){
				traverseIndexDown = false;
				return true;
			}
		});
		/** Down Key down Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, InputHandler.PL_2_D){
			@Override
			public boolean callback(){
				indexDown();
				traverseIndexDown = true;
				return true;
			}
		});
		/** Down Key Up Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_D){
			@Override
			public boolean callback(){
				traverseIndexDown = false;
				return true;
			}
		});
		/** Select Listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, InputHandler.PL_2_SELECT){
			@Override
			public boolean callback(){
				if(getFocus() != null)
					getFocus().onSelect();
				return true;
			}
		});				
	}
	
	public void attachMouseListeners(){
		/** Mouse Down Event 
		 * Changes focus to component clicked.
		 * */
		addListener(new MouseEvent(MouseEvent.MOUSE_DOWN){
			@Override
			public boolean callback(){
				//Check if its within this menu so we don't interfere with others.
				if(!pointIsInBounds(mousePosition))
					return false;
				
				//If there is a focus, check that first
				if(focus != null){
					if(focus.pointIsInBounds(mousePosition)){
						return focus.onMouseDown(); //Mouse was pressed on focus
					}
					
					//Something other than the focus was clicked
					focus.onLoseFocus();
					setFocus(null);
				}
				
				//Look for component that was clicked
				for(Component component : components){
					if(!component.isSelectable()) continue;
					
					if(component.pointIsInBounds(mousePosition) ){
						setFocus(component);
						focus.onGainFocus();
						return focus.onMouseDown();
					}
				}
				
				//Mouse not pressed on menu component
				return false;
			}
		});
		
		/** Mouse up event
		 * Always called on focus.
		 */
		addListener(new MouseEvent(MouseEvent.MOUSE_UP){
			@Override
			public boolean callback(){
				//Check if its within this menu so we don't interfere with others.
				if(!pointIsInBounds(mousePosition))
					return false;
				
				if(focus != null){
					return focus.onMouseUp();
				}
				
				return false;
			}
		});
		
		/** Mouse over event
		 * Doesn't affect focus
		 */
		addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){				
				if(mouseOver == null){
					//Find the component we are over if any
					for(Component component : components){
						if(!component.isSelectable()) continue;
						
						if(component.pointIsInBounds(mousePosition) ){
							mouseOver = component;
							setIndex(mouseOver);
							return mouseOver.onCursorOver();
						}
					}
				}else{
					if(!mouseOver.pointIsInBounds(mousePosition)){
						boolean handled = mouseOver.onCursorExit();
						mouseOver = null;
						index = -1;
						return handled;
					}
				}
				return false;
			}
		});
	}
	
	/** Checks if given point lies within the menu */
	public boolean pointIsInBounds(Vector2 point){
		return (point.x >= position.x &&
				point.x <= position.x + size.x &&
				point.y >= position.y &&
				point.y <= position.y + size.y);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}
}