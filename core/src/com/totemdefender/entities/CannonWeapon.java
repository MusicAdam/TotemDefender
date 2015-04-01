package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class CannonWeapon extends WeaponEntity {

	public CannonWeapon(Player owner) {
		super(owner);
	}

	
	public void spawn(TotemDefender game){
		Texture weaponTexture = game.getAssetManager().get("catapult.png", Texture.class);
		setSprite(new Sprite(weaponTexture));
		
		float aspectRatio = getSprite().getWidth()/getSprite().getHeight(); //Get aspect ratio to maintain for scaling
		float scale = 1/20f; //Relative to screen;
		
		getSprite().setSize(getSprite().getWidth() * scale * aspectRatio,
							getSprite().getHeight() * scale * aspectRatio);
		if(owner.getID() == 2){
			origin = new Vector2(98, 14); //This is based on the logical rotation point on the cannon sprite
			getSprite().flip(true, false);
		}else{
			origin = new Vector2(30, 14);
		}

		barrelPos = new Vector2(origin.x + 95 * flip, origin.y + 20);
		
		super.spawn(game);
	}
	
}
