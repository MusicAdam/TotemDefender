package com.totemdefender.menu;

import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.NavigableContainer.ConnectionType;
import com.totemdefender.states.LeaderBoardState;
import com.totemdefender.states.MainMenuState;

public class LeaderBoardMenu extends NavigableContainer {
	private Button returner;
	private	Label leaderboard;
	private LeaderBoardState state;
	
	//private MainMenuState state;
	
	public LeaderBoardMenu(LeaderBoardState state){
		super(null, ConnectionType.Vertical);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/3),(TotemDefender.V_WIDTH/4)/4.65517f); //4.6.. is the apsect ratio of the button texture
		Vector2 LabelSize = new Vector2(TotemDefender.V_WIDTH - buttonSize.x,TotemDefender.V_HEIGHT);
		
		leaderboard = new Label(this);
		leaderboard.setPosition(new Vector2(0,buttonSize.y));
		leaderboard.setFont("hud_huge.ttf");
		leaderboard.setText("leaderboard");
		leaderboard.setSize(LabelSize);
		
		returner = new Button(this, "Main Menu", buttonSize, new Vector2((TotemDefender.V_WIDTH/2.0f) - buttonSize.x/2, 0), null){
			@Override
			public boolean onClick(){
				state.returnButtonPressed(true);
				return true;
			}
		};
		returner.setFont("hud_large.ttf");
		returner.setTextOffset(buttonSize.x/2 - returner.getTextBounds().width/2, buttonSize.y/2 - returner.getTextBounds().height/2 + 5); 
		returner.setBackgroundTexture(game, "ui/bar_tall.png");
		returner.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		returner.create(game);
		
		this.addComponent(leaderboard);
		this.addComponent(returner);
		this.attachKeyboardListeners(game.getPlayer2());
		super.create(game);
	}
}
