package com.totemdefender.menu;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.NavigableContainer.ConnectionType;
import com.totemdefender.menu.buildmenu.BuildMenu;
import com.totemdefender.states.BattleState;
import com.totemdefender.states.MainMenuState;
import com.totemdefender.states.PreGameState;

public class MainMenu extends NavigableContainer{
	private Button start;
	private Button leaderboard;
	private Button quit;
	private MainMenuState state;
	
	public MainMenu(MainMenuState state){
		super(null, ConnectionType.Vertical);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/4),(TotemDefender.V_WIDTH/4)/4.65517f); //4.6.. is the apsect ratio of the button texture
		float areaWidth = TotemDefender.V_WIDTH/2 - buttonSize.x/2; //width position of where the button are position
		float centerScreenHeight = TotemDefender.V_HEIGHT/2;
		
		start = new Button(this, "Start Game", buttonSize, 
				new Vector2(areaWidth, centerScreenHeight + (buttonSize.y * 21.5f/35f)), null){
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
					  new Vector2(areaWidth, centerScreenHeight - buttonSize.y/2), null){
			@Override
			public boolean onClick(){
				state.leaderButtonPressed(true);
				return true;
			}
		};
		leaderboard.setFont("hud_large.ttf");
		leaderboard.setTextOffset(buttonSize.x/2 - start.getTextBounds().width/2, buttonSize.y/2 - start.getTextBounds().height/2 + 5);
		leaderboard.setBackgroundTexture(game, "ui/bar_tall.png");
		leaderboard.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		leaderboard.create(game);
		
		quit = new Button(this, "Quit", buttonSize, 
				 new Vector2(areaWidth, centerScreenHeight - (buttonSize.y * (56.5f/35f))), null){
			@Override
			public boolean onClick(){
				Gdx.app.exit();
				return true;
			}
		};
		quit.setFont("hud_large.ttf");
		quit.setTextOffset(buttonSize.x/2 - start.getTextBounds().width/2, buttonSize.y/2 - start.getTextBounds().height/2 + 5);
		quit.setBackgroundTexture(game, "ui/bar_tall.png");
		quit.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		quit.create(game);
		
		connectComponentsVertically(start, leaderboard);
		connectComponentsVertically(leaderboard, quit);
		
		attachKeyboardListeners(game.getPlayer2());
		super.create(game);
	}
}