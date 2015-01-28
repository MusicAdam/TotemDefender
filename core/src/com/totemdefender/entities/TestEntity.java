package com.totemdefender.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.totemdefender.TotemDefender;

public class TestEntity extends Entity {
	
	public TestEntity(){
		super("Circle Test");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawn(TotemDefender game) {
		BodyDef testDef = new BodyDef();
		testDef.type = BodyType.DynamicBody;
		
		float rnd = (float)Math.random() * TotemDefender.V_WIDTH - ((float)Math.random() * TotemDefender.V_WIDTH); //-width <= rnd <= width
		
		testDef.position.set(rnd * TotemDefender.WORLD_TO_BOX, 0);
		setBody(game.getWorld().createBody(testDef));
		
		CircleShape shape = new CircleShape();
		shape.setRadius(5f * TotemDefender.WORLD_TO_BOX);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;

		// Create our fixture and attach it to the body
		getBody().createFixture(fixtureDef);

		shape.dispose();		
	}
	


}
