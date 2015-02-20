package com.totemdefender.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.totemdefender.TotemDefender;

public class StartMenu extends Panel {
	
	private Button start;
	private Button leaderboard;
	private Button option;
	private Button instruction;
	
	public StartMenu(TotemDefender game){		
		super(null);
		/*
		Vector2 buttonSize = new Vector2(new Vector2((TotemDefender.V_WIDTH/3),TotemDefender.V_HEIGHT/6));
		
		start = new Button(this, "Start Game", buttonSize, 
				new Vector2(buttonSize.x, buttonSize.y * 4), Color.GREEN);
		leaderboard = new Button(this, "Leaderboard", buttonSize, 
				new Vector2(buttonSize.x, buttonSize.y * 3), Color.YELLOW);
		option = new Button(this, "Option", buttonSize, 
				new Vector2(buttonSize.x, buttonSize.y * 2), Color.RED);
		instruction = new Button(this, "Instruction", buttonSize, 
				new Vector2(buttonSize.x ,buttonSize.y), Color.CYAN);
		
		this.addPanel(start);
		this.addPanel(leaderboard);
		this.addPanel(option);
		this.addPanel(instruction);
		*/
	}

}