package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totemdefender.TotemDefender;

/** Label allows a text field to be created and provides utilities for displaying text. */
public class Label extends Component{

	private BitmapFont font;
	private String text;

	public Label(Menu parent){
		super(parent);
		text = "Default";
		font = TotemDefender.Get().getAssetManager().get("default.ttf", BitmapFont.class);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.begin();
		font.setColor(getColor());
		font.drawWrapped(batch, text, getPosition().x, getPosition().y + getHeight()/2, getWidth());
		batch.end();
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(String fontName) {
		this.font = TotemDefender.Get().getAssetManager().get(fontName, BitmapFont.class);;
	}

	public String getText() {
		return text;
	}

	public void setText(String text, boolean scale) {
		this.text = text;
		if(scale){
			TextBounds bounds = font.getBounds(text);
			setSize(bounds.width, bounds.height);
		}
	}
	
	public void setText(String text){
		setText(text, true);
	}

}
