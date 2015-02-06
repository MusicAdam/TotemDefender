package com.totemdefender.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.CollisionListener;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;

public class TotemEntity extends BlockEntity {
	private boolean onGround = false;
	
	public TotemEntity(Player owner) {
		super(owner, 0, 1, 2);
		setRotatable(false);
	}
	
	@Override
	public void spawn(TotemDefender game){
		super.spawn(game);
		
		final TotemEntity thisRef = this;
		getBody().getFixtureList().first().setUserData(new CollisionListener(){

			@Override
			public void beginContact(Fixture other, Contact contact) {
				if(other.getBody().getUserData() instanceof GroundEntity){
					thisRef.onGround = true;
				}
			}

			@Override
			public void endContact(Fixture other, Contact contact) {
			}
			
		});
	}
	
	public boolean isOnGround(){
		return onGround; 
	}
}
