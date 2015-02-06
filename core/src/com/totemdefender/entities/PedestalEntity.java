package com.totemdefender.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class PedestalEntity extends Entity{
	
	public PedestalEntity(Player owner){
		super(owner);
	}
	
	@Override
	public void spawn(TotemDefender game) {
		float hw   = TotemDefender.PEDESTAL_WIDTH/2;
		float hh   = TotemDefender.PEDESTAL_HEIGHT/2;
		float xPos = (-game.getScreenWidth()/2) * TotemDefender.STACK_LOCATION;
		float yPos = -game.getScreenHeight()/2 + 20 + hh; //20 is hardcoded ground size
		
		if(getOwner().getID() == 2)
			xPos = (game.getScreenWidth()/2) * TotemDefender.STACK_LOCATION;
		
		BodyDef weaponDef = new BodyDef();
		weaponDef.type = BodyType.StaticBody;
		weaponDef.position.set(xPos * TotemDefender.WORLD_TO_BOX, yPos * TotemDefender.WORLD_TO_BOX);
		
		Body body = game.getWorld().createBody(weaponDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((hw - 1) * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
	
		Fixture fix = body.createFixture(shape, 0.0f);
		Filter filter = fix.getFilterData();
		filter.categoryBits = Entity.PEDESTAL;
		filter.maskBits = Entity.BLOCK;
		fix.setFilterData(filter);
		
		shape.dispose();
		
		setBody(body);
	}

}
