package com.totemdefender.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.CollisionListener;
import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TestEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.BuildMenu;

public class TestState implements State {
	int count = 0;
	int touchCount = 0;
	long spawnDelay = 100;
	long lastTime = 0;
	KeyboardEvent spaceListener;
	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {
		System.out.println("TestState:onEnter");	
		
		//Make the ground
		float hw = Gdx.graphics.getWidth() / 2;
		float hh = 10;
		
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, -Gdx.graphics.getHeight()/2 * TotemDefender.WORLD_TO_BOX + ((hh + 1) * TotemDefender.WORLD_TO_BOX));
		
		Body groundBody = game.getWorld().createBody(groundDef);
		
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox((hw - 1) * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
		
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
		
		//Setup test keys
		final TestState thisRef = this;
		final TotemDefender gameRef = game;
		spaceListener = game.getInputHandler().addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.SPACE){
			@Override
			public boolean callback(){
				thisRef.spawnBall(gameRef);
				return true;
			}
		});
		
		lastTime = System.currentTimeMillis();
		
		Level level = new Level(game);
		
		/*BuildMenu buildMenu = new BuildMenu(game, level);
		game.addMenu(buildMenu);
		buildMenu.show();*/
		
	}

	@Override
	public void onExit(TotemDefender game) {
		game.getInputHandler().removeListener(spaceListener);
		
		System.out.println("Balls have touched the ground " + touchCount + " times");
		System.out.println("TestState:onExit");
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return count == 10;
	}

	@Override
	public void update(TotemDefender game) {
		/* This is in the keyboard event now
		long now = System.currentTimeMillis(); //The long now
		
		if((now - lastTime) >= spawnDelay){
			spawnBall(game);
			lastTime = now;
			count++;
		}
		*/
	}
	
	private void spawnBall(TotemDefender game){
		TestEntity ball = new TestEntity();
		ball.spawn(game);
		game.addEntity(ball);
		count++;
	}

}
