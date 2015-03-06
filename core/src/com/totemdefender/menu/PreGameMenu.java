package com.totemdefender.menu;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.states.MainMenuState;
import com.totemdefender.states.PreGameState;

public class PreGameMenu extends NavigableContainer {
	private Player owner;
	private TextEntry userName;
	private Button ready;
	private Button weapon1, weapon2, weapon3;
	private Boolean weaponSelected=false;
	
	public PreGameMenu(TotemDefender game, Player owner) {
		super(null);
		this.owner = owner;
		
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/6),(TotemDefender.V_WIDTH/6)/4.65517f); //4.6.. is the apsect ratio of the button texture
		float side = 10;

		userName = new TextEntry(this){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		userName.setText("Player 1");
		
		if(owner.getID() == 2) {
			side = TotemDefender.V_WIDTH - buttonSize.x - side;
			userName.setText("Player 2");
		}
		userName.setSize(buttonSize);
		userName.setPosition(new Vector2(side, TotemDefender.V_HEIGHT - buttonSize.y));
		userName.setFont("hud_small.ttf");
		userName.setTextOffset(buttonSize.x/2 - userName.getTextBounds().width/2, buttonSize.y/2 - userName.getTextBounds().height/2 + 5);
		userName.create(game);

		weapon1 = new Button(this, "Player 1 Weapon 1", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*2), Color.RED){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		weapon1.setFont("hud_small.ttf");
		weapon1.setTextOffset(buttonSize.x/2 - weapon1.getTextBounds().width/2, buttonSize.y/2 - weapon1.getTextBounds().height/2 + 5);
		weapon1.create(game);
		
		weapon2 = new Button(this, "Player 1 Weapon 2", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*3), Color.BLUE){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		weapon2.setFont("hud_small.ttf");
		weapon2.setTextOffset(buttonSize.x/2 - weapon2.getTextBounds().width/2, buttonSize.y/2 - weapon2.getTextBounds().height/2 + 5);
		weapon2.create(game);
		
		weapon3 = new Button(this, "Player 1 Weapon 3", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*4), Color.YELLOW){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		weapon3.setFont("hud_small.ttf");
		weapon3.setTextOffset(buttonSize.x/2 - weapon3.getTextBounds().width/2, buttonSize.y/2 - weapon3.getTextBounds().height/2 + 5);
		weapon3.create(game);
		

		ready = new Button(this, "READY", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*5), Color.GREEN){
			@Override
			public boolean onClick(){
				weaponSelected = true;
				return true;
			}
		};
		ready.setFont("hud_small.ttf");
		ready.setTextOffset(buttonSize.x/2 - ready.getTextBounds().width/2, buttonSize.y/2 - ready.getTextBounds().height/2 + 5);
		ready.create(game);

		connectComponents(userName, weapon1);
		connectComponents(weapon1, weapon2);
		connectComponents(weapon2, weapon3);
		connectComponents(weapon3, ready);
		connectComponents(ready, userName, true);
		
		attachKeyboardListeners(owner);
	}
	
	public boolean hasSelected(){
		return weaponSelected;
	}
}