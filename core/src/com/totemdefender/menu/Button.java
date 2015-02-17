package com.totemdefender.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.MouseEvent;

public class Button extends Label {		
	public Button(Container parent, String labelText, Vector2 newSize, Vector2 newPosition, Color newColor) {
		super(parent);
		setText(labelText);		
		setSize(newSize); 
		setPosition(newPosition);
		setColor(newColor);
	}
	
	public Button(Container parent){
		super(parent);
		setColor(Color.BLACK);
	}
	
	public void dispose() {
	}
	
	@Override
	public void setPosition(Vector2 pos){
		super.setPosition(pos);
	}
	
	public void onClick(){}
	
	@Override
	public void onGainFocus(){
		setColor(Color.LIGHT_GRAY);
	}
	
	@Override
	public void onLoseFocus(){
		setColor(Color.BLACK);
	}
	
	@Override
	public boolean onMouseEnter(MouseEvent event){
		setColor(Color.GRAY);
		return true;
	}
	
	@Override
	public boolean onMouseExit(MouseEvent event){
		setColor(Color.BLACK);
		return true;
	}
	
}