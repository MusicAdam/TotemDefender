package com.totemdefender.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.MouseEvent;

public class TextEntry extends Label{
	private int cursorIndex = 0;
	private long cursorLastFlash = 0;
	private long cursorFlashRate = 500;
	private boolean drawCursor = true;
	private float padding = 5;
	private boolean mouseDown = false;
	
	public TextEntry(Container parent) {
		super(parent);
		setColor(Color.WHITE);
		setTextColor(Color.BLACK);
		setSize(100, 0);
	}
	
	@Override
	public void update(TotemDefender game){
		if(System.currentTimeMillis() - cursorLastFlash > cursorFlashRate){
			drawCursor = !drawCursor;
			cursorLastFlash = System.currentTimeMillis();
		}
		super.update(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		super.render(batch, shapeRenderer);
		if(drawCursor){
			Vector2 tmp = textOffset.cpy();
			tmp.y = 0;
			Vector2 cursorPosition = getCursorPosition().add(tmp);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(0, 0, 0, 1);
			shapeRenderer.line(getPosition().x + cursorPosition.x, getPosition().y, getPosition().x + cursorPosition.x, getPosition().y + getHeight());
			shapeRenderer.end();
		}
	}
	
	@Override
	public void doLayout(){
		if(shouldLayout()){
			setSize(rectangle.width + padding, bounds.height + padding);
			textOffset = new Vector2(padding, bounds.height/2 - padding/2);
			super.doLayout();
		}
	}
	
	@Override
	public boolean onMouseDown(MouseEvent event){
		setCursorIndex(worldToLocal(event.mousePosition));
		mouseDown = true;
		return true;
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		mouseDown = true;
		return true;
	}
	
	@Override
	public boolean onMouseMove(MouseEvent event){
		if(mouseDown){
			setCursorIndex(worldToLocal(event.mousePosition));			
		}
		return true;
	}
	
	@Override
	public void onGainFocus(){
		System.out.println("Gain focus");
	}
	
	@Override
	public void onLoseFocus(){
		drawCursor = false;
	}
	
	public Vector2 getCursorPosition(){
		TextBounds bounds = getFont().getBounds(getText(), 0, cursorIndex);
		return new Vector2(bounds.width, 0);
	}
	
	//Sets cursor index to a local position
	public void setCursorIndex(Vector2 position){
		for(int i = 0; i < getText().length(); i++){
			TextBounds bounds = getFont().getBounds(getText(), 0, i);
			if(bounds.width >= position.x){
				cursorIndex = i - 1;
				return;
			}
		}

		cursorIndex = getText().length();
	}

}
