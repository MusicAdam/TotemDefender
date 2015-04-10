package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;

/** Label allows a text field to be created and provides utilities for displaying text. */
public class Label extends Panel{
	private BitmapFont font;
	private String text;
	private Color textColor;
	protected Vector2 textOffset;
	protected TextBounds bounds;

	public Label(Container parent){
		super(parent);
		font = TotemDefender.Get().getAssetManager().get("default.ttf", BitmapFont.class);
		bounds = new TextBounds();
		textColor = Color.WHITE;
		textOffset = new Vector2();
		setText("Default");
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		if(!shouldRender()) return;
		super.render(batch, shapeRenderer);
		batch.begin();
		font.setColor(textColor);
		font.drawWrapped(batch, text, getPosition().x + textOffset.x, getPosition().y + textOffset.y + bounds.height, getWidth());
		batch.end();
	}
	
	public BitmapFont getFont() { return font; }
	public void updateBounds(){
		font.getBounds(text, bounds);
		//invalidate();
	}
	
	public void setFont(String fontName) {
		this.font = TotemDefender.Get().getAssetManager().get(fontName, BitmapFont.class);
		updateBounds();
	}
	
	public String getText() { return text; }
	
	public void setText(String text, boolean scale) {
		this.text = text;
		updateBounds();
		if(scale){
			sizeToBounds();
		}
	}
	
	public void sizeToBounds(){ setSize(bounds.width, bounds.height); }
	
	public void setText(String text){ setText(text, true); }
	
	public Color getTextColor() { return textColor; }
	
	public void setTextColor(Color textColor) { 
		if(textColor == null) return;
		this.textColor = textColor; 
	}
	
	public void setTextOffset(Vector2 offset){ textOffset = offset; }
	
	public void setTextOffset(float x, float y){ setTextOffset(new Vector2(x, y)); }
	
	public Vector2 getTextOffset(){ return textOffset; }
	
	public TextBounds getTextBounds(){ return bounds; }

}
