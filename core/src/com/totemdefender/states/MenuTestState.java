package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.Menu;

public class MenuTestState implements State{
	Menu menu;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		System.out.println("MenuTestState:onEnter");
		menu = new Menu(game);
		menu.attachPlayer1Listeners();
		game.addMenu(menu);
		

		final Label testLabel = new Label(menu);
		testLabel.setText("Hello World!");
		testLabel.setPosition(200, 200);
		menu.addComponent(testLabel);
		
		
		Button testCmp = new Button(menu){
			@Override
			public boolean onSelect(){
				testLabel.setText("White clicked");
				return true;
			}
		};
		testCmp.setColor(Color.WHITE);
		testCmp.setSize(100, 100);
		testCmp.setSelectable(true);
		menu.addComponent(testCmp);
		
		Component testCmp2 = new Component(menu);
		testCmp2.setColor(Color.RED);
		testCmp2.setSize(100, 100);
		testCmp2.setPosition(100, 0);
		menu.addComponent(testCmp2);
		
		Component testCmp3 = new Component(menu);
		testCmp3.setColor(Color.GREEN);
		testCmp3.setSize(100, 100);
		testCmp3.setPosition(200, 0);
		testCmp3.setSelectable(true);
		menu.addComponent(testCmp3);
		
		
		
	}

	@Override
	public void onExit(TotemDefender game) {
		game.removeMenu(menu);
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false;
	}

	@Override
	public void update(TotemDefender game) {
	}

}
