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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu extends Menu {
	private Button triangle;
	private Button square;
	private Button rect;
	private Button ready;
	private Grid player1Grid;
	private Grid player2Grid;
	private Level level;
	private boolean placingTotem = false;
	
	public BuildMenu(TotemDefender game, Level level) {
		this.level = level;
		
		
		Vector2 buttonSize = new Vector2(new Vector2((game.getScreenWidth()/4), 50));
		float topArea = (game.getScreenHeight() - buttonSize.y);
		
		triangle = new Button("Triangle", buttonSize, new Vector2(0,topArea), Color.GREEN);
		square = new Button("Square", buttonSize, new Vector2(buttonSize.x, topArea), Color.YELLOW);
		rect = new Button("Rectangle", buttonSize, new Vector2(buttonSize.x * 2,topArea), Color.RED);
		ready = new Button("READY!", buttonSize, new Vector2((buttonSize.x * 3),topArea), Color.CYAN);
		
		Vector2 ped1Pos = game.screenToWorld(level.getPlayer1Pedestal().getPosition());	
		Vector2 ped2Pos = game.screenToWorld(level.getPlayer2Pedestal().getPosition());	
		
		player1Grid = new Grid();
		player1Grid.setPosition(new Vector2(	ped1Pos.x - player1Grid.getWidth()/2,
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
		
		this.addComponent(ready);
		this.addComponent(triangle);
		this.addComponent(square);
		this.addComponent(rect);
		this.addComponent(player1Grid);
		this.addComponent(player2Grid);
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