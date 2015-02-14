package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totemdefender.TotemDefender;

public class BackgroundEntity extends Entity{
	
	@Override
	public void spawn(TotemDefender game) {
		Texture bg = game.getAssetManager().get("bg.png", Texture.class);
		setSprite(new Sprite(bg));
		
		getSprite().setSize(TotemDefender.V_WIDTH, TotemDefender.V_HEIGHT);
		getSprite().setPosition(-TotemDefender.V_WIDTH/2, -TotemDefender.V_HEIGHT/2);
		
		isSpawned = true;
	}

}
