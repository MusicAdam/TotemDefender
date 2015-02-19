package com.totemdefender.menu;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.KeyboardEvent;

public class PreGameMenu extends Menu {
	/*
	Choose Weapon 
	Game Rules
	Ready (Starts Games)
	*/
	public Button ready;
	
	public PreGameMenu(TotemDefender game){
		super(game);
	
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),TotemDefender.V_HEIGHT/7);
		float buttonArea = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		
		ready = new Button(this, "Ready", buttonSize, new Vector2(buttonArea, buttonSize.y), Color.GREEN){
			@Override
			public boolean onSelect(){
				
				return true;
			}
		};
		
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
	
	public boolean clickIndex(ArrayList<Button> buttonList, int index){
		if(index == -1) return false;
		return buttonList.get(index).onSelect();
	}
	
	public void attachListeners() {
		addListener(new KeyboardEvent(MouseEvent.MOUSE_CLICKED, Input.Buttons.LEFT){
			@Override
			public boolean callback(){
				if(true) {
					
					return true;
				}
				return false;
			}
			
		});
		
	}
}