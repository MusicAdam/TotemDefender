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
import com.totemdefender.states.MainMenuState;
import com.totemdefender.states.StartState;

public class PreGameMenu extends NavigableContainer {

	Button ready;
	private StartState state;
	
	public PreGameMenu(StartState state){
		super(null);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
		
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),TotemDefender.V_HEIGHT/7);
		float buttonArea = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		
		ready = new Button(this, "READY", buttonSize, new Vector2(buttonArea, 0), Color.GREEN){
			@Override
			public boolean onClick(){
				state.readyButtonPressed(true);
				return true;
			}
		};
		this.addComponent(ready);
		attachKeyboardListeners(game.getPlayer1());
		super.create(game);
	}
}