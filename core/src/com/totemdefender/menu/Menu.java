package com.totemdefender.menu;
import java.awt.Component;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

//Base menu from which all other menus derive. Should provide the ability
//to add Buttons and Text. Should have its own input processor which its component buttons will attach to. 

public abstract class Menu implements InputProcessor{
	
	public abstract void render();
	
	//Allows menu to be rendered, attaches inputprocessor to main multiplexer
	public void show(InputMultiplexer multiplexer) {
		multiplexer.addProcessor(this);
	}
	
	//Stops menu rendering, detaches inputprocessor from multiplexer.
	public void hide(InputMultiplexer multiplexer) {
		multiplexer.removeProcessor(this);
	}
	
	//Adds a component (Should attach to inputprocessor if menu is visible)
	public void addComponent(Component cmp){
		//don't know how to add a component to inputprocessor try override
	}
	
	//Removes a component
	public void removeComponent(Component cmp){
		//don't know how to remove a component to inputprocessor try override
	} 
	
	//Adds a listener to the listener list
	public void addListener(InputProcessor listener, InputMultiplexer multiplexer){
		multiplexer.addProcessor(listener);
	}

	//Removes a listener
	public void removeListener(InputProcessor listener, InputMultiplexer multiplexer){
		multiplexer.removeProcessor(listener);
	}
}