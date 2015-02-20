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
import com.totemdefender.menu.Panel;

public class WinnerStatus extends Component {

	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont bitMapFont;
	private String text;
	
	public WinnerStatus(Panel parent)
	{
		super(parent);
		bitMapFont = parent.game.getAssetManager().get("hud_large.ttf", BitmapFont.class);
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
