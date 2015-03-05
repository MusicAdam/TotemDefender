package com.totemdefender.states;

import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.MainMenu;
import com.totemdefender.menu.PreGameMenu;
import com.totemdefender.menu.TextEntry;
import com.totemdefender.menu.buildmenu.BuildMenu;

public class PresGameState implements State {

private TextEntry p1Name, p2Name;
private PreGameMenu p1PreMenu;
private PreGameMenu p2PreMenu;
Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/6),(TotemDefender.V_WIDTH/6)/4.65517f); //4.6.. is the apsect ratio of the button texture


	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		p1PreMenu = new PreGameMenu(game, game.getPlayer1());
		
		p1Name = new TextEntry(this.p1PreMenu);
		p1Name.setText("Player 1");
		p1Name.setSize(buttonSize);
		p1Name.setPosition(new Vector2(10, TotemDefender.V_HEIGHT - buttonSize.y));
		p1Name.setFont("hud_small.ttf");
		p1Name.setTextOffset(buttonSize.x/2 - p1Name.getTextBounds().width/2, buttonSize.y/2 - p1Name.getTextBounds().height/2 + 5);

		p2PreMenu = new PreGameMenu(game, game.getPlayer2());
		p2Name = new TextEntry(this.p2PreMenu);
		p2Name.setText("Player 1");
		p2Name.setSize(buttonSize);
		p2Name.setPosition(new Vector2(10, TotemDefender.V_HEIGHT - buttonSize.y));
		p2Name.setFont("hud_small.ttf");
		p2Name.setTextOffset(buttonSize.x/2 - p2Name.getTextBounds().width/2, buttonSize.y/2 - p2Name.getTextBounds().height/2 + 5);

		p1Name.create(game);
		p2Name.create(game);
		p1PreMenu.create(game);
		p2PreMenu.create(game);
	}

	@Override
	public void onExit(TotemDefender game) {
		p1PreMenu.destroy(game);
		p2PreMenu.destroy(game);
		game.setDoneBuilding(true);
		game.getStateManager().attachState(new BuildState());
		
	}
	@Override
	public boolean canExit(TotemDefender game) {
		if(p1PreMenu == null || p2PreMenu == null) return false;
		return p1PreMenu.hasSelected() && p2PreMenu.hasSelected();
	}
	@Override
	public void update(TotemDefender game) {
	}

	public PreGameMenu getP1PreMenu() {
		return p1PreMenu;
	}
	public PreGameMenu getP2PreMenu() {
		return p2PreMenu;
	}
}
