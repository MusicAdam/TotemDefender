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
	private boolean placingTotem = false;
	
	public BuildMenu(TotemDefender game, Level level) {
		this.level = level;
		Vector2 squareButton = new Vector2((game.getScreenHeight()/8), (game.getScreenHeight()/8));
		Vector2 buttonSize = new Vector2((game.getScreenWidth()/4), squareButton.y);
		float topArea = (game.getScreenHeight() - buttonSize.y);
		float rightSide = (game.getScreenWidth() - squareButton.x);
		float screenCenterX = game.getScreenWidth()/2;	
		
		p1Funding = new Button("Player 1", new Vector2((game.getScreenWidth()/4), 20), 
				new Vector2(0, 0), new Color(0, 0, 0, 0));
		ready = new Button("Game Turn", new Vector2((game.getScreenWidth()/4), 20),
				new Vector2((game.getScreenWidth()/4), 0), new Color(0, 0, 0, 0));
		quit = new Button("QUIT", new Vector2((game.getScreenWidth()/4), 20), 
				new Vector2((game.getScreenWidth()/2), 0), new Color(0, 0, 0, 0));
		p2Funding = new Button("Player 2", new Vector2((game.getScreenWidth()/4), 20), 
				new Vector2((float) (game.getScreenWidth() * 0.75), 0), new Color(0, 0, 0, 0));
		
		p1Funding.setTextPosition(p1Funding.getPosition().x + p1Funding.getSize().x/2, p1Funding.getPosition().y + 12);
		p2Funding.setTextPosition(p2Funding.getPosition().x + p1Funding.getSize().x/2, p2Funding.getPosition().y + 12);
		ready.setTextPosition(ready.getPosition().x + p1Funding.getSize().x/2, ready.getPosition().y + 12);
		quit.setTextPosition(quit.getPosition().x + p1Funding.getSize().x/2, quit.getPosition().y + 12);
		
		p1Circle = new Button("Circle", squareButton, new Vector2(0, topArea), Color.BLUE);
		p1Triangle = new Button("Triangle", squareButton, new Vector2(0, topArea - squareButton.y), Color.GREEN);
		p1Square = new Button("Square", squareButton, new Vector2(0, topArea - squareButton.y * 2), Color.RED);
		p1Rect = new Button("Rectangle", squareButton, new Vector2(0, topArea - squareButton.y * 3), Color.YELLOW);
		
		p2Circle = new Button("Circle", squareButton, new Vector2(rightSide, topArea), Color.BLUE);
		p2Triangle = new Button("Triangle", squareButton, new Vector2(rightSide, topArea - squareButton.y), Color.GREEN);
		p2Square = new Button("Square", squareButton, new Vector2(rightSide, topArea - squareButton.y * 2), Color.RED);
		p2Rect = new Button("Rectangle", squareButton, new Vector2(rightSide, topArea - squareButton.y * 3), Color.YELLOW);
		
		Vector2 ped1Pos = game.screenToWorld(level.getPlayer1Pedestal().getPosition());	
		Vector2 ped2Pos = game.screenToWorld(level.getPlayer2Pedestal().getPosition());	
		
		player1Grid = new Grid();
		player1Grid.setPosition(new Vector2(ped1Pos.x - player1Grid.getWidth()/2,
												TotemDefender.PEDESTAL_HEIGHT + TotemDefender.GROUND_HEIGHT));
		player2Grid = new Grid();
		player2Grid.setPosition(new Vector2(ped2Pos.x - player1Grid.getWidth()/2, 
											TotemDefender.PEDESTAL_HEIGHT + TotemDefender.GROUND_HEIGHT));
		
		RectangleBlockEntity entity = new RectangleBlockEntity(game.getPlayer1());		
		entity.spawn(game);
		game.addEntity(entity);
		
		entity.getBody().setActive(false);
		player1Grid.setEntity(entity);
		
		RectangleBlockEntity entity2 = new RectangleBlockEntity(game.getPlayer2());		
		entity2.spawn(game);
		game.addEntity(entity2);
		
		entity2.getBody().setActive(false);
		player2Grid.setEntity(entity2);
		
		this.addComponent(p1Funding);
		this.addComponent(p2Funding);
		this.addComponent(player1Grid);
		this.addComponent(player2Grid);
		this.addComponent(ready);
		this.addComponent(quit);
		
		this.addComponent(p1Circle);
		this.addComponent(p1Triangle);
		this.addComponent(p1Square);
		this.addComponent(p1Rect);
		
		this.addComponent(p2Circle);
		this.addComponent(p2Triangle);
		this.addComponent(p2Square);
		this.addComponent(p2Rect);
	};
	
	public Grid getGrid(){
		return player1Grid;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		/** PLAYER 1 INPUTS */
		if(keycode == InputHandler.PL_1_SELECT && player1Grid.hasEntity()){
			if(!placingTotem)
				level.addPlacedBlock(player1Grid.getEntity());
			player1Grid.setEntity(null);
			return true;
		}
		
		if(keycode == InputHandler.PL_1_U && player1Grid.hasEntity()){
			player1Grid.shiftIndexUp();
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
		
		if(keycode == InputHandler.PL_1_D && player1Grid.hasEntity()){
			player1Grid.shiftIndexDown();
			return true;
		}
		
		if(keycode == InputHandler.PL_1_ROTATE && player1Grid.hasEntity()){
			player1Grid.rotateEntity();
			return true;
		}

		/** PLAYER 2 INPUTS */
		if(keycode == InputHandler.PL_2_SELECT && player2Grid.hasEntity()){
			if(!placingTotem)
				level.addPlacedBlock(player2Grid.getEntity());
			player2Grid.setEntity(null);
			return true;
		}
		
		if(keycode == InputHandler.PL_2_U && player2Grid.hasEntity()){
			player2Grid.shiftIndexUp();
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
		
		if(keycode == InputHandler.PL_2_D && player2Grid.hasEntity()){
			player2Grid.shiftIndexDown();
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