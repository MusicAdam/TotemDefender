package com.totemdefender.states;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.NavigableContainer;
import com.totemdefender.menu.Panel;
import com.totemdefender.menu.TextEntry;

public class MenuTestState implements State{
	Container menu;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		System.out.println("MenuTestState:onEnter");
		
		menu = new Container();
		/*NavigableContainer test = new NavigableContainer(menu){
			@Override
			public boolean onMouseMove(MouseEvent event){
				System.out.println("MOuseMOVE");
				return super.onMouseMove(event);
			}
		};
		test.attachKeyboardListeners(new Player(2));
		test.create(game);
		//menu.setSize(100, 100);
		
		Button testButton = new Button(test);
		testButton.setSize(100, 100);
		testButton.setText("Hello World1");
		testButton.create(game);
		
		Button testButton2 = new Button(test);
		testButton2.setSize(100, 100);
		testButton2.setPosition(100, 0);
		testButton2.setText("Hello World2");
		testButton2.create(game);
		test.connectComponents(testButton, testButton2);
		
		Button testButton3 = new Button(test);
		testButton3.setSize(100, 100);
		testButton3.setPosition(0, 100);
		testButton3.setText("Hello World3");
		testButton3.create(game);
		test.connectComponents(testButton3, testButton);
		
		Button testButton4 = new Button(test);
		testButton4.setSize(100, 100);
		testButton4.setPosition(100, 100);
		testButton4.setText("Hello World4");
		testButton4.create(game);
		test.connectComponents(testButton4, testButton3);
		test.connectComponents(testButton4, testButton2);
		
		test.setPosition(10, 10);*/
		
		TextEntry entry = new TextEntry(menu, new Player(1));
		entry.setPosition(210, 210);
		entry.create(game);
		//menu.setPosition(TotemDefender.V_WIDTH/2 - menu.getWidth()/2, TotemDefender.V_HEIGHT/2 - menu.getHeight()/2);
		menu.setPosition(10, 10);
		menu.create(game);
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
