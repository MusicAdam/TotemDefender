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
		setHighlightColor(newColor.cpy().add(.5f, .5f, .5f, 1));
	}
	
	public Button(Container parent){
		super(parent);
		setColor(Color.BLACK);
		setHighlightColor(Color.WHITE);
	}
	
	@Override
	public void setColor(Color color){
		super.setColor(color);
	}
	
	@Override
	public void setText(String text){
		super.setText(text, true);
	}
	
	@Override
	public void setPosition(Vector2 pos){
		super.setPosition(pos);
	}
	
	@Override
	public boolean onClick(){
		return false;
	}
	
	@Override
	public void onGainFocus(){
		setHighlighted(true);
	}
	
	@Override
	public void onLoseFocus(){
		setHighlighted(false);
	}
	
	@Override
	public void onMouseEnter(MouseEvent event){
		setMouseOver(true);
		setHighlighted(true);
		super.onMouseEnter(event);
	}
	
	@Override
	public void onMouseExit(MouseEvent event){
		setMouseOver(false);
		setHighlighted(false);
		super.onMouseExit(event);
	}
	
}