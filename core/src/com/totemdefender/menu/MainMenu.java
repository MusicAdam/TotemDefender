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
import com.totemdefender.states.PresGameState;

public class MainMenu extends NavigableContainer{
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
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),(TotemDefender.V_WIDTH/4)/4.65517f); //4.6.. is the apsect ratio of the button texture
		float areaWidth = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		float centerScreenHeight = TotemDefender.V_HEIGHT/2;
		
		start = new Button(this, "Start Game", buttonSize, 
				new Vector2(areaWidth, centerScreenHeight + buttonSize.y + (buttonSize.y * 3/40)), null){
			@Override
			public boolean onClick(){
				state.startButtonPressed(true);
				return true;
			}
		};
		start.setFont("hud_large.ttf");
		start.setTextOffset(buttonSize.x/2 - start.getTextBounds().width/2, buttonSize.y/2 - start.getTextBounds().height/2 + 5); //-20 because of https://github.com/MusicAdam/TotemDefender/issues/40
		start.setBackgroundTexture(game, "ui/bar_tall.png");
		start.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		start.create(game);
		
		leaderboard = new Button(this, "Leaderboard", buttonSize, 
					  new Vector2(areaWidth, centerScreenHeight + buttonSize.y/40), null){
			@Override
			public boolean onClick(){
				return true;
			}
		};
		leaderboard.setFont("hud_large.ttf");
		leaderboard.setTextOffset(buttonSize.x/2 - start.getTextBounds().width/2, buttonSize.y/2 - start.getTextBounds().height/2 + 5);
		leaderboard.setBackgroundTexture(game, "ui/bar_tall.png");
		leaderboard.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		leaderboard.create(game);
		
		option = new Button(this, "Options", buttonSize, 
				 new Vector2(areaWidth, centerScreenHeight - (buttonSize.y + buttonSize.y/40)), null){
			@Override
			public boolean onClick(){
			return true;
			}
		};
		option.setFont("hud_large.ttf");
		option.setTextOffset(buttonSize.x/2 - start.getTextBounds().width/2, buttonSize.y/2 - start.getTextBounds().height/2 + 5);
		option.setBackgroundTexture(game, "ui/bar_tall.png");
		option.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		option.create(game);
		
		instruction = new Button(this, "Instructions", buttonSize, 
					  new Vector2(areaWidth, centerScreenHeight - (buttonSize.y*2) - (buttonSize.y * 3/40)), null){
			@Override
			public boolean onClick(){
				return true;
			}
		};
		instruction.setFont("hud_large.ttf");
		instruction.setTextOffset(buttonSize.x/2 - start.getTextBounds().width/2, buttonSize.y/2 - start.getTextBounds().height/2 + 5);
		instruction.setBackgroundTexture(game, "ui/bar_tall.png");
		instruction.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		instruction.create(game);
		
		connectComponents(start, leaderboard);
		connectComponents(leaderboard, option);
		connectComponents(option, instruction);
		connectComponents(instruction, start);
		
		attachKeyboardListeners(game.getPlayer2());
		super.create(game);
	}
}