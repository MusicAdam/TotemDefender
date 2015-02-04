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
	private Vector2 barrelPos;
	public ProjectileEntity(Player owner, Vector2 barrelPos) {
		super(owner);
		
		this.barrelPos = barrelPos;
	}

	@Override
	public void spawn(TotemDefender game) {
		BodyDef testDef = new BodyDef();
		testDef.type = BodyType.DynamicBody;
		
		float rnd = (float)Math.random() * (Gdx.graphics.getWidth()/10) - ((float)Math.random() * (Gdx.graphics.getWidth()/10)); //-width + 10 <= rnd <= width
		
		testDef.position.set(barrelPos.x * TotemDefender.WORLD_TO_BOX, barrelPos.y * TotemDefender.WORLD_TO_BOX);
		setBody(game.getWorld().createBody(testDef));
		
		CircleShape shape = new CircleShape();
		shape.setRadius(5f * TotemDefender.WORLD_TO_BOX);
		
		short categoryBits 	= (getOwner() == game.getPlayer1()) ? Entity.PLAYER1 : Entity.PLAYER2;
		short maskBits 			= (categoryBits == Entity.PLAYER1) ? Entity.PLAYER2 : Entity.PLAYER1;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = (short) (maskBits | Entity.GROUND);

		// Create our fixture and attach it to the body
		getBody().createFixture(fixtureDef);

		shape.dispose();	
	}

}
