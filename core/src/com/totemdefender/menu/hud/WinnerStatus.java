package com.totemdefender.menu.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Component;

public class WinnerStatus extends Component {

	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont bitMapFont;
	private String text;
	
	public WinnerStatus(){
		generator = new FreeTypeFontGenerator(Gdx.files.internal("consola.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 72;
		bitMapFont = generator.generateFont(parameter);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.begin();
			bitMapFont.setColor(Color.WHITE);
			bitMapFont.draw(batch, text, TotemDefender.V_WIDTH/2 - 300, TotemDefender.V_HEIGHT/2);
		batch.end();
	}

}
