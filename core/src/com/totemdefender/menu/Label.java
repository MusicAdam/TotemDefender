package com.totemdefender.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** Label allows a text field to be created and provides utilities for displaying text. */
public class Label extends Component{

	private BitmapFont font;
	private String text;
	
	public Label(Menu parent){
		super(parent);
		text = "Default";
		//font = 
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
	}

}
