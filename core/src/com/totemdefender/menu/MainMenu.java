package com.totemdefender.menu;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
=======
>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.input.KeyboardEvent;
<<<<<<< HEAD
import com.totemdefender.menu.buildmenu.BuildMenu;
import com.totemdefender.states.BattleState;
import com.totemdefender.states.MainMenuState;
import com.totemdefender.states.StartState;

public class MainMenu extends NavigableContainer{
	public enum PlacementMode{
		Mouse,
		Keyboard
	}
=======

public class MainMenu extends Menu{
>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3

	private Button start;
	private Button leaderboard;
	private Button option;
	private Button instruction;
<<<<<<< HEAD
	private MainMenuState state;
	
	public MainMenu(MainMenuState state){
		super(null);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
=======
	
	public MainMenu(TotemDefender game){
		super(game);
>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),TotemDefender.V_HEIGHT/7);
		float buttonArea = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		float centerScreenHeight = TotemDefender.V_HEIGHT/2;
		float nearMiddle = buttonSize.y + buttonSize.y/70; //height position of two button near the center of the screen
		float farMiddle = buttonSize.y + buttonSize.y/35 + nearMiddle;  //height position of two button far the center of the screen
		
		start = new Button(this, "Start Game", buttonSize, new Vector2(buttonArea, centerScreenHeight + farMiddle), Color.GREEN){
			@Override
<<<<<<< HEAD
			public boolean onClick(){
				state.startButtonPressed(true);
				return true;
			}
		};
		start.create(game);
		
		leaderboard = new Button(this, "Leaderboard", buttonSize, new Vector2(buttonArea, centerScreenHeight + nearMiddle), Color.YELLOW){
			@Override
			public boolean onClick(){
=======
			public boolean onSelect(){
				
				return true;
			}
		};
		
		leaderboard = new Button(this, "Leaderboard", buttonSize, new Vector2(buttonArea, centerScreenHeight + nearMiddle), Color.YELLOW){
			@Override
			public boolean onSelect(){
>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3
				
				return true;
			}
		};
<<<<<<< HEAD
		leaderboard.create(game);

		option = new Button(this, "Option", buttonSize, new Vector2(buttonArea, centerScreenHeight - farMiddle), Color.RED){
			@Override
			public boolean onClick(){
=======
		
		option = new Button(this, "Option", buttonSize, new Vector2(buttonArea, centerScreenHeight - nearMiddle), Color.RED){
			@Override
			public boolean onSelect(){
>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3
				
				return true;
			}
		};
<<<<<<< HEAD
		option.create(game);
		
		instruction = new Button(this, "Instruction", buttonSize, new Vector2(buttonArea, centerScreenHeight - nearMiddle) , Color.BLUE){
			@Override
			public boolean onClick(){
=======
		
		instruction = new Button(this, "Instruction", buttonSize, new Vector2(buttonArea, centerScreenHeight - farMiddle) , Color.BLUE){
			@Override
			public boolean onSelect(){
>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3
				
				return true;
			}
		};
<<<<<<< HEAD
		instruction.create(game);
		connectComponents(start, leaderboard);
		connectComponents(leaderboard, option);
		connectComponents(option, instruction);
		connectComponents(instruction, start);
		
		attachKeyboardListeners(game.getPlayer1());
		super.create(game);
	}
=======
		
		this.addComponent(start);
		this.addComponent(leaderboard);
		this.addComponent(option);
		this.addComponent(instruction);
		attachListeners();
	}
	
	public void highlightIndex(ArrayList<Button> buttonList, int index){
		if(index == -1) return;
		buttonList.get(index).setHighlighted(true);
	}
	
	public void dehighlightIndex(ArrayList<Button> buttonList, int index){
		if(index == -1) return;
		buttonList.get(index).setHighlighted(false);		
	}
	
	public void attachListeners() {
		//mouse event
		addListener(new KeyboardEvent(MouseEvent.MOUSE_CLICKED, Input.Buttons.LEFT){
			@Override
			public boolean callback(){
				return false;
			}
			
		});
		
	}

>>>>>>> 1011e7cac35d1579a59b02044f0e19451d1df9d3
}