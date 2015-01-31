package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class WeaponEntity extends Entity {	
	
	public static final float WEAPON_LOCATION = 1/3f; //The weapon will be this proportion away from the side of the screen.
	
	public WeaponEntity(Player owner){
		super(owner);
	}

	@Override
	public void spawn(TotemDefender game) {
		setSprite(new Sprite(new Texture(Gdx.files.internal("cannon.png"))));
		
		float aspectRatio = getSprite().getWidth()/getSprite().getHeight(); //Get aspect ratio to maintain for scaling
		float scale = 1/10f; //Relative to screen;
		
		getSprite().setSize(getSprite().getWidth() * scale * aspectRatio,
							getSprite().getHeight() * scale * aspectRatio);
		
		float hw = getSprite().getWidth()/2;
		float hh = getSprite().getHeight()/2;
		float xPos = (-game.getScreenWidth()/2) * WEAPON_LOCATION;
		float yPos = -game.getScreenHeight()/2 + 20 + hh; //20 is hardcoded ground size
		
		if(owner.getID() == 2){
			xPos = -xPos; //Put it on the right side if its player 2
			getSprite().flip(true, false);
		}
		
		BodyDef weaponDef = new BodyDef();
		weaponDef.type = BodyType.StaticBody;
		weaponDef.position.set(xPos * TotemDefender.WORLD_TO_BOX, yPos * TotemDefender.WORLD_TO_BOX);
		
		Body body = game.getWorld().createBody(weaponDef);
		
		PolygonShape cannonShape = new PolygonShape();
		cannonShape.setAsBox((hw - 1) * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
		
		body.createFixture(cannonShape, 0.0f);
		
		cannonShape.dispose();
		
		setBody(body);
		isSpawned = true;
	}

}
