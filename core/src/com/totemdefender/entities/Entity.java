package com.totemdefender.entities;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity {
	public void update(){
		//Sprite follows b2dBody if it exists
	}
	public abstract void render();
	
	public boolean isSpawned(){
		return false;
	}
	
	public Body getBody(){
		return null;
	}
	
	public String getName(){
		return null;
	}
}
