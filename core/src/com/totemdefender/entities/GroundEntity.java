package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.TotemDefender;

public class GroundEntity extends Entity{

	@Override
	public void spawn(TotemDefender game) {
		//Make the ground
		float hw = Gdx.graphics.getWidth() * 2;
		float hh = 10;
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, -Gdx.graphics.getHeight()/2 * TotemDefender.WORLD_TO_BOX + ((hh + 1) * TotemDefender.WORLD_TO_BOX));
		
		Body groundBody = game.getWorld().createBody(groundDef);
		groundBody.setUserData(this);
		
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox((hw - 1) * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
		Fixture fix = groundBody.createFixture(groundShape, 0.0f);
		Filter filter = fix.getFilterData();
		filter.categoryBits = Entity.GROUND;		
		filter.maskBits = Entity.EVERYTHING;
		fix.setFilterData(filter);
		
		groundShape.dispose();
		
		setBody(groundBody);
		isSpawned = true;
	}
	
}
