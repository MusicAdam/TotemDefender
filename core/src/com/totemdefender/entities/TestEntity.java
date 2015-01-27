package com.totemdefender.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.totemdefender.TotemDefender;

public class TestEntity extends Entity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawn(TotemDefender game) {
	this.setBody(game.getWorld().createBody(new BodyDef()));
		
	}
	


}
