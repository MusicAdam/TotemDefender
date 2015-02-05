package com.totemdefender.menu;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
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
	private Grid grid;
	
	public BuildMenu(TotemDefender game) {
		Vector2 buttonSize = new Vector2(new Vector2((game.getScreenWidth()/4), 50));
		float topArea = (game.getScreenHeight() - buttonSize.y);
		
		triangle = new Button("Triangle", buttonSize, new Vector2(0,topArea), Color.GREEN);
		square = new Button("Square", buttonSize, new Vector2(buttonSize.x, topArea), Color.YELLOW);
		rect = new Button("Rectangle", buttonSize, new Vector2(buttonSize.x * 2,topArea), Color.RED);
		ready = new Button("READY!", buttonSize, new Vector2((buttonSize.x * 3),topArea), Color.CYAN);
		grid = new Grid();
		
		RectangleBlockEntity entity = new RectangleBlockEntity(new Player(1));		
		entity.spawn(game);
		game.addEntity(entity);
		
		entity.getBody().setActive(false);
		grid.setEntity(entity);
		
		this.addComponent(ready);
		this.addComponent(triangle);
		this.addComponent(square);
		this.addComponent(rect);
		this.addComponent(grid);
	};
	
	public Grid getGrid(){
		return grid;
	}

}