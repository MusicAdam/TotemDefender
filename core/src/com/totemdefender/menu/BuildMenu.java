package com.totemdefender.menu;
import java.util.ArrayList;

import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.InputHandler;
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
	private Button p1Funding;
	private Button p2Funding;
	private Button ready;
	private Button quit;
	private int p1Amount = 1000;
	private int p2Amount = 1000;
	
	private Button p1Circle;
	private Button p1Triangle;
	private Button p1Square;
	private Button p1Rect;
	
	private Button p2Circle;
	private Button p2Triangle;
	private Button p2Square;
	private Button p2Rect;

	private Grid player1Grid;
	private Grid player2Grid;
	private Level level;
	private ArrayList<Button> p1ButtonOrder;
	private ArrayList<Button> p2ButtonOrder;
	private int p1Index;
	private int p2Index;
	private boolean placingTotem = false;
	
	public BuildMenu(TotemDefender game, Level level) {
		super(game);
		
		this.level = level;
		p1ButtonOrder = new ArrayList<Button>();
		p2ButtonOrder = new ArrayList<Button>();
		
		Vector2 squareButton = new Vector2((TotemDefender.V_HEIGHT/8), (TotemDefender.V_HEIGHT/8));
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4), squareButton.y);
		float topArea = (TotemDefender.V_HEIGHT - buttonSize.y);
		float rightSide = (TotemDefender.V_WIDTH - squareButton.x);
		float screenCenterX = TotemDefender.V_WIDTH/2;	
		
		p1Funding = new Button(this, "Player 1", new Vector2((TotemDefender.V_WIDTH/4), 20), 
				new Vector2(0, 0), new Color(0, 0, 0, 0));
		ready = new Button(this, "Game Turn", new Vector2((TotemDefender.V_WIDTH/4), 20),
				new Vector2((TotemDefender.V_WIDTH/4), 0), new Color(0, 0, 0, 0));
		quit = new Button(this, "QUIT", new Vector2((TotemDefender.V_WIDTH/4), 20), 
				new Vector2((TotemDefender.V_WIDTH/2), 0), new Color(0, 0, 0, 0));
		p2Funding = new Button(this, "Player 2", new Vector2((TotemDefender.V_WIDTH/4), 20), 
				new Vector2((float) (TotemDefender.V_WIDTH * 0.75), 0), new Color(0, 0, 0, 0));
		
		p1Funding.setTextPosition(p1Funding.getPosition().x + p1Funding.getSize().x/2, p1Funding.getPosition().y + 12);
		p2Funding.setTextPosition(p2Funding.getPosition().x + p1Funding.getSize().x/2, p2Funding.getPosition().y + 12);
		ready.setTextPosition(ready.getPosition().x + p1Funding.getSize().x/2, ready.getPosition().y + 12);
		quit.setTextPosition(quit.getPosition().x + p1Funding.getSize().x/2, quit.getPosition().y + 12);
		
		p1Circle = new Button(this, "Circle", squareButton, new Vector2(0, topArea), Color.BLUE);
		p1Triangle = new Button(this, "Triangle", squareButton, new Vector2(0, topArea - squareButton.y), Color.GREEN);
		p1Square = new Button(this, "Square", squareButton, new Vector2(0, topArea - squareButton.y * 2), Color.RED){
			@Override
			public boolean onSelect(){
				spawnSquare(TotemDefender.Get().getPlayer1());
				return true;
			}
		};
		p1Rect = new Button(this, "Rectangle", squareButton, new Vector2(0, topArea - squareButton.y * 3), Color.YELLOW){
			@Override
			public boolean onSelect(){
				spawnRectangle(TotemDefender.Get().getPlayer1());
				return true;
			}
		};
		
		p2Circle = new Button(this, "Circle", squareButton, new Vector2(rightSide, topArea), Color.BLUE);
		p2Triangle = new Button(this, "Triangle", squareButton, new Vector2(rightSide, topArea - squareButton.y), Color.GREEN);
		p2Square = new Button(this, "Square", squareButton, new Vector2(rightSide, topArea - squareButton.y * 2), Color.RED){
			@Override
			public boolean onSelect(){
				spawnSquare(TotemDefender.Get().getPlayer2());
				return true;
			}
		};
		p2Rect = new Button(this, "Rectangle", squareButton, new Vector2(rightSide, topArea - squareButton.y * 3), Color.YELLOW){
			@Override
			public boolean onSelect(){
				spawnRectangle(TotemDefender.Get().getPlayer2());
				return true;
			}
		};
		
		Vector2 ped1Pos = game.worldToScreen(level.getPlayer1Pedestal().getPosition());	
		Vector2 ped2Pos = game.worldToScreen(level.getPlayer2Pedestal().getPosition());	
		
		player1Grid = new Grid(this);
		player1Grid.setPosition(new Vector2(ped1Pos.x - player1Grid.getWidth()/2,
												TotemDefender.PEDESTAL_HEIGHT + TotemDefender.GROUND_HEIGHT));
		player2Grid = new Grid(this);
		player2Grid.setPosition(new Vector2(ped2Pos.x - player2Grid.getWidth()/2, 
											TotemDefender.PEDESTAL_HEIGHT + TotemDefender.GROUND_HEIGHT));

		//this.addComponent(p1Funding);
		//this.addComponent(p2Funding);
		this.addComponent(player1Grid);
		this.addComponent(player2Grid);
		//this.addComponent(ready);
		//this.addComponent(quit);
		
		//this.addComponent(p1Circle);
		//this.addComponent(p1Triangle);
		this.addComponent(p1Square);
		this.addComponent(p1Rect);
		p1ButtonOrder.add(p1Square);
		p1ButtonOrder.add(p1Rect);
		p1Index = -1;
		
		
		//this.addComponent(p2Circle);
		//this.addComponent(p2Triangle);
		this.addComponent(p2Square);
		this.addComponent(p2Rect);
		p2ButtonOrder.add(p2Square);
		p2ButtonOrder.add(p2Rect);
		p2Index = -1;
	};
	
	public void spawnSquare(Player player){
		SquareBlockEntity ent = new SquareBlockEntity(player);
		ent.spawn(TotemDefender.Get());
		TotemDefender.Get().addEntity(ent);
		ent.getBody().setActive(false);
		
		if(player.getID() == 1){
			player1Grid.setEntity(ent);
		}else{
			player2Grid.setEntity(ent);
		}
	}
	
	public void spawnRectangle(Player player){
		RectangleBlockEntity ent = new RectangleBlockEntity(player);
		ent.spawn(TotemDefender.Get());
		TotemDefender.Get().addEntity(ent);
		ent.getBody().setActive(false);
		
		if(player.getID() == 1){
			player1Grid.setEntity(ent);
		}else{
			player2Grid.setEntity(ent);
		}
	}
	
	public Grid getGrid(){
		return player1Grid;
	}
	
	public int indexDown(int index){
		if(index == -1){
			index = 0;
			return index;
		}
		
		index--;
		
		if(index < 0)
			index = p1ButtonOrder.size() - 1;
		return index;
	}
	
	public int indexUp(int index){
		if(index == -1){
			index = 0;
			return index;
		}
		
		index++;
		
		if(index == p1ButtonOrder.size())
			index = 0;
		
		return index;
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
	
	@Override
	public boolean keyUp(int keycode) {
		/** PLAYER 1 INPUTS */
		if(keycode == InputHandler.PL_1_SELECT){
			if(player1Grid.hasEntity()){
				if(!placingTotem){
					highlightIndex(p1ButtonOrder, p1Index);
					level.addPlacedBlock(player1Grid.getEntity());
				}
				player1Grid.setEntity(null);
			}else{
				dehighlightIndex(p1ButtonOrder, p1Index);
				return clickIndex(p1ButtonOrder, p1Index);
			}
			return true;
		}
		
		if(keycode == InputHandler.PL_1_U){
			if(player1Grid.hasEntity()){
				player1Grid.shiftIndexUp();
			}else{
				dehighlightIndex(p1ButtonOrder, p1Index);
				p1Index = indexDown(p1Index);
				highlightIndex(p1ButtonOrder, p1Index);
			}
			return true;
		}
		
		if(keycode == InputHandler.PL_1_L && player1Grid.hasEntity()){
			player1Grid.shiftIndexLeft();
			return true;
		}
		
		if(keycode == InputHandler.PL_1_R && player1Grid.hasEntity()){
			player1Grid.shiftIndexRight();
			return true;
		}
		
		if(keycode == InputHandler.PL_1_D){
			if(player1Grid.hasEntity()){
				player1Grid.shiftIndexDown();
			}else{
				dehighlightIndex(p1ButtonOrder, p1Index);
				p1Index = indexUp(p1Index);
				highlightIndex(p1ButtonOrder, p1Index);
			}
			return true;
		}
		
		if(keycode == InputHandler.PL_1_ROTATE && player1Grid.hasEntity()){
			player1Grid.rotateEntity();
			return true;
		}

		/** PLAYER 2 INPUTS */
		if(keycode == InputHandler.PL_2_SELECT){
			if(player2Grid.hasEntity()){
				if(!placingTotem){
					highlightIndex(p2ButtonOrder, p2Index);
					level.addPlacedBlock(player2Grid.getEntity());
				}
				player2Grid.setEntity(null);
			}else{
				dehighlightIndex(p2ButtonOrder, p2Index);
				return clickIndex(p2ButtonOrder, p2Index);
			}
			return true;
		}
		
		if(keycode == InputHandler.PL_2_U){
			if(player2Grid.hasEntity()){
				player2Grid.shiftIndexUp();
			}else{
				dehighlightIndex(p2ButtonOrder, p2Index);
				p2Index = indexDown(p2Index);
				highlightIndex(p2ButtonOrder, p2Index);
			}
			return true;
		}
		
		if(keycode == InputHandler.PL_2_L && player2Grid.hasEntity()){
			player2Grid.shiftIndexLeft();
			return true;
		}
		
		if(keycode == InputHandler.PL_2_R && player2Grid.hasEntity()){
			player2Grid.shiftIndexRight();
			return true;
		}
		
		if(keycode == InputHandler.PL_2_D){
			if(player2Grid.hasEntity()){
				player2Grid.shiftIndexDown();
			}else{
				dehighlightIndex(p2ButtonOrder, p2Index);
				p2Index = indexUp(p2Index);
				highlightIndex(p2ButtonOrder, p2Index);
			}
			return true;
		}
		
		if(keycode == InputHandler.PL_2_ROTATE && player2Grid.hasEntity()){
			player2Grid.rotateEntity();
			return true;
		}
		
		/** TODO: There should be a menu button for this */
		if(keycode == Input.Keys.ENTER){
			if(placingTotem){
				TotemDefender.Get().setDoneBuilding(true);				
			}else{
				placingTotem = true;
				
				TotemDefender game = TotemDefender.Get();
				TotemEntity p1Totem = new TotemEntity(game.getPlayer1());
				p1Totem.setName("Player 1's Totem");
				p1Totem.spawn(game);
				p1Totem.getBody().setActive(false);
				player1Grid.setEntity(p1Totem);
				game.addEntity(p1Totem);
				level.setPlayer1Totem(p1Totem);
				
				TotemEntity p2Totem = new TotemEntity(game.getPlayer2());
				p2Totem.setName("Player 2's Totem");
				p2Totem.spawn(game);
				p2Totem.getBody().setActive(false);
				player2Grid.setEntity(p2Totem);
				game.addEntity(p2Totem);
				level.setPlayer2Totem(p2Totem);		
			}
			return true;
		}
		return false;
	}

}