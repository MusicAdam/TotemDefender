package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Panel;
import com.totemdefender.player.Player;

public class DepthTestState implements State{
	TotemEntity totem;
	Panel menu;
	
	long timeStart = 0;
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		Vector2 position = new Vector2(10, 10);
		SquareBlockEntity entity = new SquareBlockEntity(new Player(1));
		entity.spawn(game);
		entity.setPosition(position);
		entity.getBody().setActive(false);
		game.addEntity(entity, 0);
		totem = new TotemEntity(new Player(1));
		totem.spawn(game);
		totem.setPosition(position.x, position.y);
		totem.getBody().setActive(false);
		game.addEntity(totem, -1);
		
		/* TODO: Fix this
		menu = new Panel(game);
		menu.setPosition(200, 200);
		Component cmp = new Component(menu);
		cmp.setColor(Color.RED);
		cmp.setSize(100, 100);
		menu.addPanel(cmp);
		game.addMenu(menu, -1);
		
		Panel menu2 = new Panel(game);
		menu2.setPosition(250, 250);
		Component cmp2 = new Component(menu);
		cmp2.setColor(Color.BLUE);
		cmp2.setSize(100, 100);
		menu2.addPanel(cmp2);
		game.addMenu(menu2, 0);
		*/
		timeStart = System.currentTimeMillis();
	}

	@Override
	public void onExit(TotemDefender game) {
		
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false;
	}

	@Override
	public void update(TotemDefender game) {
		if(System.currentTimeMillis() - timeStart > 500){
			int newDepth = (game.getDepth(totem) == 1) ? -1 : 1;
			game.setDepth(totem, newDepth);		
			//game.setDepth(menu, newDepth);		
			timeStart = System.currentTimeMillis();
		}
	}

}
