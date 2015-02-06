package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totemdefender.TotemDefender;

public class BackgroundEntity extends Entity{
	
	@Override
	public void spawn(TotemDefender game) {
		Texture bg = game.getAssetManager().get("background.png", Texture.class);
		setSprite(new Sprite(bg));
		
		getSprite().setSize(game.getScreenWidth(), game.getScreenHeight());
		getSprite().setPosition(-game.getScreenWidth()/2, -game.getScreenHeight()/2);
		
		isSpawned = true;
	}

}
