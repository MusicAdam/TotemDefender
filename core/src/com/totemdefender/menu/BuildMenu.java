package com.totemdefender.menu;
import java.util.ArrayList;

import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.PedestalEntity;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.hud.Grid;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu extends Menu {
	private Button funding;
	private int amount = 1000;
	
	private Button circle;
	private Button triangle;
	private Button square;
	private Button rect;
	private Sprite cir = new Sprite(game.getAssetManager().get("circle blue.png", Texture.class));
	private Sprite tri = new Sprite(game.getAssetManager().get("triangle.png", Texture.class));
	private Sprite squ = new Sprite(game.getAssetManager().get("Square red.png", Texture.class));

	private Player owner;
	private Grid grid;
	private Level level;
	private boolean placingTotem = false;
	
	public BuildMenu(TotemDefender game, Level level, Player owner) {
		super(game);
		
		this.owner = owner;
		this.level = level;
		
		Vector2 buttonSize = new Vector2((TotemDefender.V_HEIGHT/8), (TotemDefender.V_HEIGHT/8));
		
		float top = (TotemDefender.V_HEIGHT - buttonSize.y);
		float right;
		
		if(owner.getID() == 1){
			right = 0;
			funding = new Button(this, "Player 1 Funding: \n " + amount, new Vector2 (TotemDefender.V_WIDTH/2, buttonSize.y), 
					new Vector2(right, top), Color.CYAN);
		}else{
			right = (TotemDefender.V_WIDTH - buttonSize.x);
			funding = new Button(this, "Player 2 Funding: \n " + amount, new Vector2 (TotemDefender.V_WIDTH/2, buttonSize.y), 
					new Vector2(right, top), Color.ORANGE);
		}
		
		circle = new Button(this, "Circle", buttonSize, new Vector2(right, top), Color.BLUE);
		circle.getLabel().setColor(Color.BLACK);
		
		triangle = new Button(this, "Triangle", buttonSize, new Vector2(right, top - buttonSize.y * 2), Color.GREEN);
		triangle.getLabel().setColor(Color.BLACK);
		
		square = new Button(this, "Square", buttonSize,  new Vector2(right, top - buttonSize.y * 3), Color.RED){
			@Override
			public boolean onSelect(){
				spawnSquare();
				return true;
			}
		};
		square.getLabel().setColor(Color.BLACK);
		
		rect = new Button(this, "Rectangle", buttonSize, new Vector2(right, top - buttonSize.y * 4), Color.YELLOW){
			@Override
			public boolean onSelect(){
				spawnRectangle();
				return true;
			}
		};
		rect.getLabel().setColor(Color.BLACK);
		
		PedestalEntity pedestal = level.getPedestal(owner);
		Vector2 pedPos = game.worldToScreen(pedestal.getPosition());	
		grid = new Grid(this);
		grid.setPosition(new Vector2(pedPos.x - grid.getWidth()/2,
												TotemDefender.PEDESTAL_HEIGHT + TotemDefender.GROUND_HEIGHT));

		this.addComponent(grid);
		this.addComponent(funding);
		this.addComponent(circle);
		this.addComponent(triangle);
		this.addComponent(square);
	
		attachListeners();
		if(owner.getID() == 1){
			attachPlayer1Listeners();
		}else{
			attachPlayer2Listeners();
		}
	};
	
	/*
	public void spawnCircle(){
		SquareBlockEntity ent = new SquareBlockEntity(owner);
		ent.spawn(TotemDefender.Get());
		TotemDefender.Get().addEntity(ent);
		ent.getBody().setActive(false);
		grid.setEntity(ent);
	}
	
	public void spawnTriangle(){
		RectangleBlockEntity ent = new RectangleBlockEntity(owner);
		ent.spawn(TotemDefender.Get());
		TotemDefender.Get().addEntity(ent);
		ent.getBody().setActive(false);
		grid.setEntity(ent);
	}
	*/
	
	public void spawnSquare(){
		SquareBlockEntity ent = new SquareBlockEntity(owner);
		ent.spawn(TotemDefender.Get());
		TotemDefender.Get().addEntity(ent, TotemDefender.BLOCK_DEPTH);
		ent.getBody().setActive(false);
		grid.setEntity(ent);
	}
	
	public void spawnRectangle(){
		RectangleBlockEntity ent = new RectangleBlockEntity(owner);
		ent.spawn(TotemDefender.Get());
		TotemDefender.Get().addEntity(ent, TotemDefender.BLOCK_DEPTH);
		ent.getBody().setActive(false);
		grid.setEntity(ent);
	}
	
	public Grid getGrid(){
		return grid;
	}
	
	public void highlightIndex(ArrayList<Button> buttonList, int index){
		if(index == -1) return;
		buttonList.get(index).setHighlighted(true);
	}
	
	public void dehighlightIndex(ArrayList<Button> buttonList, int index){
		if(index == -1) return;
		buttonList.get(index).setHighlighted(false);		
	}
	
	public boolean clickIndex(ArrayList<Button> buttonList, int index){
		if(index == -1) return false;
		return buttonList.get(index).onSelect();
	}
	
	public void attachListeners() {
		/** Select key listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, owner.getSelectKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					return true;
				}
				
				return false;
			}
		});
		
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, owner.getSelectKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					if(!placingTotem){
						level.addPlacedBlock(grid.getEntity());
					}
					grid.setEntity(null);
					return true;
				}
				
				return false;
			}
		});
		
		/** Up key down listener 
		 *  This overrides the default menu's functionality
		 * */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, owner.getUpKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					return true;
				}
				return false;
			}
		});
		/** Up key up listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, owner.getUpKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					grid.shiftIndexUp();
					return true;
				}
				
				return false;
			}
		});
		
		/** Down key down listener 
		 *  This overrides the default menu's functionality
		 * */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, owner.getDownKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					return true;
				}
				return false;
			}
		});
		/** Down key up listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, owner.getDownKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					grid.shiftIndexDown();
					return true;
				}
				
				return false;
			}
		});
		
		/** Left key down listener 
		 *  This overrides the default menu's functionality
		 * */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, owner.getLeftKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					return true;
				}
				return false;
			}
		});
		/** Left key up listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, owner.getLeftKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					grid.shiftIndexLeft();
					return true;
				}
				
				return false;
			}
		});

		/** Right key down listener 
		 *  This overrides the default menu's functionality
		 * */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, owner.getRightKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					return true;
				}
				return false;
			}
		});
		/** Right key up listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, owner.getRightKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					grid.shiftIndexRight();
					return true;
				}
				
				return false;
			}
		});
		
		/** Right key down listener 
		 *  This overrides the default menu's functionality
		 * */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, owner.getRotateKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					return true;
				}
				return false;
			}
		});
		/** Right key up listener */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, owner.getRotateKey()){
			@Override
			public boolean callback(){
				if(grid.hasEntity()){
					grid.rotateEntity();
					return true;
				}
				
				return false;
			}
		});
		
		/** TODO: There should be a menu button for this */
		addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.ENTER){
			@Override
			public boolean callback(){
				if(placingTotem){
					TotemDefender.Get().setDoneBuilding(true);				
				}else{
					placingTotem = true;
					
					TotemDefender game = TotemDefender.Get();
					TotemEntity totem = new TotemEntity(owner);
					totem.setName("Player " + owner.getID() + "'s Totem");
					totem.spawn(game);
					totem.getBody().setActive(false);
					grid.setEntity(totem);
					game.addEntity(totem);
					level.addTotem(totem);	
				}
				
				return false;
			}
		});
	}

}