package com.totemdefender.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.totemdefender.TotemDefender;

public class StartMenu extends Menu {
	
	private Button start;
	private Button leaderboard;
	private Button option;
	private Button instruction;
	
	public StartMenu(TotemDefender game){
		Vector2 buttonSize = new Vector2(new Vector2((game.getScreenWidth()/3),game.getScreenHeight()/6));
		
		start = new Button("Start Game", buttonSize, 
				new Vector2(buttonSize.x, buttonSize.y * 4), Color.GREEN);
		leaderboard = new Button("Leaderboard", buttonSize, 
				new Vector2(buttonSize.x, buttonSize.y * 3), Color.YELLOW);
		option = new Button("Option", buttonSize, 
				new Vector2(buttonSize.x, buttonSize.y * 2), Color.RED);
		instruction = new Button("Instruction", buttonSize, 
				new Vector2(buttonSize.x ,buttonSize.y), Color.CYAN);
		
		this.addComponent(start);
		this.addComponent(leaderboard);
		this.addComponent(option);
		this.addComponent(instruction);
	}

}