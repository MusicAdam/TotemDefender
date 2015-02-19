package com.totemdefender.menu;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.input.KeyboardEvent;

public class MainMenu extends Menu{

	private Button start;
	private Button leaderboard;
	private Button option;
	private Button instruction;
	
	public MainMenu(TotemDefender game){
		super(game);
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),TotemDefender.V_HEIGHT/7);
		float buttonArea = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		float centerScreenHeight = TotemDefender.V_HEIGHT/2;
		float nearMiddle = buttonSize.y + buttonSize.y/70; //height position of two button near the center of the screen
		float farMiddle = buttonSize.y + buttonSize.y/35 + nearMiddle;  //height position of two button far the center of the screen
		
		start = new Button(this, "Start Game", buttonSize, new Vector2(buttonArea, centerScreenHeight + farMiddle), Color.GREEN){
			@Override
			public boolean onSelect(){
				
				return true;
			}
		};
		
		leaderboard = new Button(this, "Leaderboard", buttonSize, new Vector2(buttonArea, centerScreenHeight + nearMiddle), Color.YELLOW){
			@Override
			public boolean onSelect(){
				
				return true;
			}
		};
		
		option = new Button(this, "Option", buttonSize, new Vector2(buttonArea, centerScreenHeight - nearMiddle), Color.RED){
			@Override
			public boolean onSelect(){
				
				return true;
			}
		};
		
		instruction = new Button(this, "Instruction", buttonSize, new Vector2(buttonArea, centerScreenHeight - farMiddle) , Color.BLUE){
			@Override
			public boolean onSelect(){
				
				return true;
			}
		};
		
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

}