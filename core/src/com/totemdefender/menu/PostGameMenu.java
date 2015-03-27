package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.TotemDefender;

public class PostGameMenu extends Container{

	Label winnerLabel;
	
	public PostGameMenu(){
		super(null);
		shouldSizeToContents(false);
	}
	
	@Override
	public void create(TotemDefender game){
		winnerLabel = new Label(this);
		winnerLabel.setText(game.getWinner().getName() + " wins!");
		winnerLabel.setTextColor(new Color(.3f, .3f, .3f, 1));
		winnerLabel.setFont("hud_large.ttf");
		winnerLabel.create(game);
		super.create(game);
	}
	
	@Override
	public void validate(){
		setSize(TotemDefender.V_WIDTH, TotemDefender.V_HEIGHT/2);
		setPosition(0, TotemDefender.V_HEIGHT/2 - getHeight()/2);
		winnerLabel.sizeToBounds();
		winnerLabel.setPosition(getWidth()/2 - winnerLabel.getWidth()/2, getHeight() - winnerLabel.getHeight()/2);
		super.validate();
	}

}
