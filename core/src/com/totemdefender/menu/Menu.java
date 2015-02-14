package com.totemdefender.menu;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

//Base menu from which all other menus derive. Should provide the ability
//to add Buttons and Text. Should have its own input processor which its component buttons will attach to. 

public class Menu implements InputProcessor{
	private ArrayList<Component> cmpList = new ArrayList<Component>();
	private boolean shouldRender;
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(!shouldRender) return;
		
		for(Component cmp : cmpList){
			cmp.render(batch, shapeRenderer);
		}
	}
	
	public void update(){
		
	}
	
	//Allows menu to be rendered, attaches inputprocessor to main multiplexer
	public void show()
	{ setShouldRender(true); }
	
	//Stops menu rendering, detaches inputprocessor from multiplexer.
	public void hide()
	{ setShouldRender(false); }
	
	//Adds a component (Should attach to inputprocessor if menu is visible)
	public void addComponent(Component cmp)
	{ cmpList.add(cmp); }
	
	//Removes a component
	public void removeComponent(Component cmp)
	{ cmpList.remove(cmp); } 
	
	//Adds a listener to the listener list
	public void addListener(InputProcessor listener, ArrayList<InputProcessor> list)
	{ list.add(listener); }

	//Removes a listener
	public void removeListener(InputProcessor listener, ArrayList<InputProcessor> list)
	{ list.remove(listener); }
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("x: " + screenX + " Y: " + screenY);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean shouldRender() 
	{ return shouldRender; }

	public void setShouldRender(boolean shouldRender) 
	{ this.shouldRender = shouldRender; }

}