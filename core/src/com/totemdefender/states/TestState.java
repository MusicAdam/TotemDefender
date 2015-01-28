package com.totemdefender.states;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.CollisionListener;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TestEntity;

public class TestState implements State {
	int count = 0;
	int touchCount = 0;
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
		
		Fixture fix = groundBody.createFixture(groundShape, 0.0f);
		fix.setUserData(new CollisionListener(){

			@Override
			public void beginContact(Fixture other, Contact contact) {
				touchCount++;
			}

			@Override
			public void endContact(Fixture other, Contact contact) {
				//System.out.println("Something has stopped touching the ground!");
			}
			
		});
		
		groundShape.dispose();
		
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void onExit(TotemDefender game) {
		System.out.println("Balls have touched the ground " + touchCount + " times");
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
	}
	
	private void spawnBall(TotemDefender game){
		TestEntity ball = new TestEntity();
		ball.spawn(game);
		game.addEntity(ball);
	}

}
