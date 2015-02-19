package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Container;

public class ArrowButton extends Button{
	private Texture arrow;
	private Texture arrowHover;
	private boolean flip = false;
	
	public ArrowButton(Container parent) {
		super(parent);
	}
	
	@Override
	public void create(TotemDefender game){
		arrow 		= game.getAssetManager().get("ui/arrow_left.png", Texture.class);
		arrowHover 	= game.getAssetManager().get("ui/arrow_hover_left.png", Texture.class);
		setSize(arrow.getWidth(), arrow.getHeight());
		setColor(null);
		setText("");
		super.create(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		super.render(batch, shapeRenderer);
		
		Texture tex;
		
		if(isHighlighted()){
			tex = arrowHover;
		}else{
			tex = arrow;
		}
		
		TotemDefender.EnableBlend();
		batch.begin();
		if(flip){
			batch.draw(tex, getPosition().x, getPosition().y, getWidth(), getHeight(), 1, 1, 0, 0);
		}else{
			batch.draw(tex, getPosition().x, getPosition().y, getWidth(), getHeight());
		}
		batch.end();
		TotemDefender.DisableBlend();
	}
	
	public void flip(){
		flip = !flip;
	}	
}
