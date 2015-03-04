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
import com.totemdefender.states.PresGameState;

public class PreGameMenu extends NavigableContainer {
	TextEntry player1, player2;
	Button ready;
	Button p1_weapon1, p1_weapon2, p1_weapon3;
	Button p2_weapon1, p2_weapon2, p2_weapon3;
	
	private PresGameState state;
	
	public PreGameMenu(PresGameState state){
		super(null);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
		
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/6),TotemDefender.V_HEIGHT/8);
		float rightSide = TotemDefender.V_WIDTH - buttonSize.x;
		float buttonArea = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		
		player1 = new TextEntry(this);
		player1.setSize(buttonSize);
		player1.setPosition(new Vector2(0, TotemDefender.V_HEIGHT - (buttonSize.y)*2));
		
		p1_weapon1 = new Button(this, "Player 1 Weapon 1", buttonSize, new Vector2(0, TotemDefender.V_HEIGHT - (buttonSize.y)*2), Color.RED){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		
		p1_weapon2 = new Button(this, "Player 1 Weapon 2", buttonSize, new Vector2(0, TotemDefender.V_HEIGHT - (buttonSize.y)*3), Color.BLUE){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		
		p1_weapon3 = new Button(this, "Player 1 Weapon 3", buttonSize, new Vector2(0, TotemDefender.V_HEIGHT - (buttonSize.y)*4), Color.YELLOW){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		
		player2 = new TextEntry(this);
		player2.setSize(buttonSize);
		player2.setPosition(new Vector2(0, TotemDefender.V_HEIGHT - (buttonSize.y)*2));
		
		p2_weapon1 = new Button(this, "Player 2 Weapon 2", buttonSize, new Vector2(rightSide, TotemDefender.V_HEIGHT - (buttonSize.y)*2), Color.RED){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		
		p2_weapon2 = new Button(this, "Player 2 Weapon 3", buttonSize, new Vector2(rightSide, TotemDefender.V_HEIGHT - (buttonSize.y)*3), Color.BLUE){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		
		p2_weapon3 = new Button(this, "Player 2 Weapon 3", buttonSize, new Vector2(rightSide, TotemDefender.V_HEIGHT - (buttonSize.y)*4), Color.YELLOW){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		
		
		ready = new Button(this, "READY", buttonSize, new Vector2(buttonArea, 30), Color.GREEN){
			@Override
			public boolean onClick(){
				state.readyButtonPressed(true);
				return true;
			}
		};
		
		this.addComponent(player1);
		this.addComponent(p1_weapon1);
		this.addComponent(p1_weapon2);
		this.addComponent(p1_weapon3);
		this.addComponent(player2);
		this.addComponent(p2_weapon1);
		this.addComponent(p2_weapon2);
		this.addComponent(p2_weapon3);
		this.addComponent(ready);
		
		attachKeyboardListeners(game.getPlayer2());
		super.create(game);
	}
}