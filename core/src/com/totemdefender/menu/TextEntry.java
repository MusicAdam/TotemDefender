package com.totemdefender.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;

public class TextEntry extends Label{
	private Rectangle scissors = new Rectangle();
	private int cursorIndex = 0;
	private long cursorLastFlash = 0;
	private long cursorFlashRate = 500;
	private boolean drawCursor = false;
	private float padding = 5;
	private boolean mouseDown = false;
	
	public TextEntry(Container parent) {
		super(parent);
		setColor(Color.WHITE);
		setTextColor(Color.BLACK);
		setSize(100, 0);
		
		TotemDefender.Get().getMenuInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_TYPED){
			@Override
			public boolean callback(){
				if(hasFocus())
					keyTyped(character);
				return true;
			}
		});
		
		TotemDefender.Get().getMenuInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.ENTER){
			@Override
			public boolean callback(){
				if(hasFocus())
					onKeyboardSelect();
				return true;
			}
		});
	}
	
	@Override
	public void update(TotemDefender game){
		if(System.currentTimeMillis() - cursorLastFlash > cursorFlashRate && hasFocus()){
			drawCursor = !drawCursor;
			cursorLastFlash = System.currentTimeMillis();
		}
		super.update(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		boolean shouldPop = ScissorStack.pushScissors(new Rectangle(scissors));
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
		if(shouldPop)
			ScissorStack.popScissors();
	}
	
	@Override
	public void doLayout(){
		if(shouldLayout()){
			setSize(rectangle.width, bounds.height + padding);
			textOffset = new Vector2(padding, bounds.height/2 - padding/2);
			ScissorStack.calculateScissors(TotemDefender.Get().getMenuCamera(), new Matrix4(), new Rectangle(getRectangle()), scissors);
			scissors.setPosition(getWorldPosition());
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
		mouseDown = false;
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
	public boolean onKeyboardSelect(){
		if(getParent() instanceof NavigableContainer){
			((NavigableContainer)getParent()).setKeyboardFocus(getParent());
			((NavigableContainer)getParent()).moveFocusDown();
		}
		
		return true;
	}
	
	@Override
	public void onGainFocus(){
		if(getParent() instanceof NavigableContainer){
			((NavigableContainer)getParent()).setKeyboardFocus(this);
		}
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
	
	public void keyTyped(char c){
		String text1 = "";
		String text2 = "";
		if((int)c == 8 && cursorIndex != 0){
			text1 = getText().substring(0, cursorIndex-1);
			text2 = getText().substring(cursorIndex, getText().length());

			cursorIndex--;
		}else{
			text1 = getText().substring(0, cursorIndex);
			text2 = getText().substring(cursorIndex, getText().length());
			cursorIndex++;
		}
		setText(text1 + c + text2, false);
	}

}
