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
	private Texture backgroundTexture;
	private Texture backgroundHighlightTexture;
	
	public Button(Container parent, String labelText, Vector2 newSize, Vector2 newPosition, Color newColor) {
		super(parent);
		setText(labelText, true);		
		setSize(newSize); 
		setPosition(newPosition);
		setColor(newColor);
		if(newColor == null){
			setHighlightColor(null);
		}else{
			setHighlightColor(newColor.cpy().add(.5f, .5f, .5f, 1));
		}
	}
	
	public Button(Container parent){
		super(parent);
		setColor(Color.BLACK);
		setHighlightColor(Color.WHITE);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		batch.begin();
		if(isHighlighted() && backgroundHighlightTexture != null){
			batch.draw(backgroundHighlightTexture, getPosition().x, getPosition().y, getWidth(), getHeight());
		}else if(backgroundTexture != null){
			batch.draw(backgroundTexture, getPosition().x, getPosition().y, getWidth(), getHeight());
		}
		batch.end();
		super.render(batch, shapeRenderer);
	}
	
	@Override
	public void setText(String text){
		super.setText(text, false);
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
	
	public void setBackgroundTexture(TotemDefender game, String file){
		backgroundTexture = game.getAssetManager().get(file, Texture.class);
	}
	
	public void setBackgroundHighlightTexture(TotemDefender game, String file){
		backgroundHighlightTexture = game.getAssetManager().get(file, Texture.class);
	}
	
}