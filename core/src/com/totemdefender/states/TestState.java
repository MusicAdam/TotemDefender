package com.totemdefender.states;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.TotemDefender;

public class TestState implements State {
	int count = 0;
	long spawnDelay = 100;
	long lastTime = 0;
	
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		System.out.println("TestState:onEnter");	
		
		//Make the ground
		float hw = TotemDefender.V_WIDTH / 2;
		float hh = 10;
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, -TotemDefender.V_HEIGHT/2 * TotemDefender.WORLD_TO_BOX);
		
		Body groundBody = game.getWorld().createBody(groundDef);
		
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(hw * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
		
		groundBody.createFixture(groundShape, 0.0f);
		
		groundShape.dispose();
		
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void onExit(TotemDefender game) {
		System.out.println("TestState:onExit");
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return count == 50;
	}

	@Override
	public void update(TotemDefender game) {
		long now = System.currentTimeMillis(); //The long now
		
		if((now - lastTime) >= spawnDelay){
			spawnBall(game);
			lastTime = now;
			count++;
		}
		System.out.println("TestState:update");
	}
	
	private void spawnBall(TotemDefender game){
		BodyDef testDef = new BodyDef();
		testDef.type = BodyType.DynamicBody;
		
		float rnd = (float)Math.random() * TotemDefender.V_WIDTH - ((float)Math.random() * TotemDefender.V_WIDTH); //-width <= rnd <= width
		
		testDef.position.set(rnd * TotemDefender.WORLD_TO_BOX, 0);
		Body body = game.getWorld().createBody(testDef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(5f * TotemDefender.WORLD_TO_BOX);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;

		// Create our fixture and attach it to the body
		body.createFixture(fixtureDef);
		shape.dispose();
	}

}
