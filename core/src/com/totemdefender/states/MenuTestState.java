package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.NavigableContainer;
import com.totemdefender.menu.Panel;

public class MenuTestState implements State{
	NavigableContainer menu;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		System.out.println("MenuTestState:onEnter");
		
		menu = new NavigableContainer(null);
		menu.attachKeyboardListeners(new Player(2));
		//menu.setSize(100, 100);
		
		Button testButton = new Button(menu);
		testButton.setSize(100, 100);
		testButton.setText("Hello World1");
		menu.addComponent(testButton);

		Button testButton2 = new Button(menu);
		testButton2.setSize(100, 100);
		testButton2.setPosition(100, 0);
		testButton2.setText("Hello World2");
		menu.addComponent(testButton2);
		menu.connectComponents(testButton, testButton2);
		
		Button testButton3 = new Button(menu);
		testButton3.setSize(100, 100);
		testButton3.setPosition(0, 100);
		testButton3.setText("Hello World3");
		menu.addComponent(testButton3);
		menu.connectComponents(testButton3, testButton);
		
		Button testButton4 = new Button(menu);
		testButton4.setSize(100, 100);
		testButton4.setPosition(100, 100);
		testButton4.setText("Hello World4");
		menu.addComponent(testButton4);
		menu.connectComponents(testButton4, testButton3);
		menu.connectComponents(testButton4, testButton2);

		menu.validate();
		menu.setPosition(TotemDefender.V_WIDTH/2 - menu.getWidth()/2, TotemDefender.V_HEIGHT/2 - menu.getHeight()/2);
		menu.create(game);
		
		Container test = new Container();
		test.create(game);
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
