package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class ProjectileEntity extends Entity{
	public static final float RADIUS = 7;
	private Vector2 barrelPos;
	public ProjectileEntity(Player owner, Vector2 barrelPos) {
		super(owner);
		
		this.barrelPos = barrelPos;
	}

	@Override
	public void spawn(TotemDefender game) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;		
		def.position.set(barrelPos.x * TotemDefender.WORLD_TO_BOX, barrelPos.y * TotemDefender.WORLD_TO_BOX);
		setBody(game.getWorld().createBody(def));
		
		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS * TotemDefender.WORLD_TO_BOX);
		
		short categoryBits 	= (getOwner() == game.getPlayer1()) ? Entity.PLAYER1 : Entity.PLAYER2;
		short maskBits 			= (categoryBits == Entity.PLAYER1) ? Entity.PLAYER2 : Entity.PLAYER1;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = (short) (maskBits | Entity.GROUND);

		// Create our fixture and attach it to the body
		getBody().createFixture(fixtureDef);
		getBody().setBullet(true);

		shape.dispose();	
	}

}
