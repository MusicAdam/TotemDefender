package com.totemdefender.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.totemdefender.TotemDefender;



public abstract class Entity {
	
	public String name="Entity";
	private Body body;
	private Sprite sprite;
	public boolean spawned=false;
	
	public Entity(){
		
	}
	
	public Entity(String name){
		this.name=name;
	}
	
	public void update(){
		//Sprite follows b2dBody if it exists
		if(this.getBody()!=null){
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
		sprite.setRotation(body.getAngle());
		}
		
	}
	public abstract void render();
	
	public abstract void spawn(TotemDefender game);
	
	public boolean isSpawned(){
		return spawned;
	}
	
	public Vector2 getPosition(){
	return body.getPosition();	
	}
	
	public void setPosition(Vector2 position){
		
		position=body.getWorldVector(position);
		body.setTransform(position,getRotation());
		
		
	}
	
	public Body getBody(){
		return body;
	}
	
	public void setBody(Body body){
		this.body=body;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public float getRotation(){
		
		return body.getAngle();
	}
	
	public void setRotation(float rotation){
		
		body.setTransform(getPosition(),rotation);
	}
	
	public void setSprite(Sprite sprite){
		this.sprite=sprite;
	}
	
	public Sprite getSprite(){
	return sprite;	
	}

	
}