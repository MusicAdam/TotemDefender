package com.totemdefender.menu;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.buildmenu.BuildMenu;
import com.totemdefender.states.BattleState;
import com.totemdefender.states.MainMenuState;
import com.totemdefender.states.StartState;

public class MainMenu extends NavigableContainer{
	public enum PlacementMode{
		Mouse,
		Keyboard
	}

	private Button start;
	private Button leaderboard;
	private Button option;
	private Button instruction;
	private MainMenuState state;
	
	public MainMenu(MainMenuState state){
		super(null);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),TotemDefender.V_HEIGHT/7);
		float buttonArea = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		float centerScreenHeight = TotemDefender.V_HEIGHT/2;
		float nearMiddle = buttonSize.y + buttonSize.y/70; //height position of two button near the center of the screen
		float farMiddle = buttonSize.y + buttonSize.y/35 + nearMiddle;  //height position of two button far the center of the screen
		
		start = new Button(this, "Start Game", buttonSize, new Vector2(buttonArea, centerScreenHeight + farMiddle), Color.GREEN){
			@Override
			public boolean onClick(){
				state.startButtonPressed(true);
				return true;
			}
		};
		start.create(game);
		
		leaderboard = new Button(this, "Leaderboard", buttonSize, new Vector2(buttonArea, centerScreenHeight + nearMiddle), Color.YELLOW){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		leaderboard.create(game);

		option = new Button(this, "Option", buttonSize, new Vector2(buttonArea, centerScreenHeight - farMiddle), Color.RED){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		option.create(game);
		
		instruction = new Button(this, "Instruction", buttonSize, new Vector2(buttonArea, centerScreenHeight - nearMiddle) , Color.BLUE){
			@Override
			public boolean onClick(){
				
				return true;
			}
		};
		instruction.create(game);
		connectComponents(start, leaderboard);
		connectComponents(leaderboard, option);
		connectComponents(option, instruction);
		connectComponents(instruction, start);
		
		attachKeyboardListeners(game.getPlayer1());
		super.create(game);
	}
}