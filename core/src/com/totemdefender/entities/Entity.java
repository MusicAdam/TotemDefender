package com.totemdefender.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;



public abstract class Entity {
	/** Setup Bitmasks */
	public static final short GROUND 				=	0x0001;
	public static final short PEDESTAL 				=	0x0002;
	public static final short BLOCK		 			=	0x0004;
	public static final short PLAYER1_PROJECTILE 	= 	0x0008;
	public static final short PLAYER2_PROJECTILE 	= 	0x0010;
	public static final short EVERYTHING	= 	(short) 0xFFFF;
	
	public String name="Entity";
	private Body body;
	private Sprite sprite;
	protected boolean isSpawned=false;
	protected Player owner;
	
	public Entity(){
		
	}
	
	public Entity(String name){
		this.name=name;
	}
	
	public Entity(Player owner, String name){
		this.name=name;
		this.owner= owner;
	}
	
	public Entity(Player owner){
		this.owner=owner;
	}
	
	public void update(TotemDefender game){
		//Sprite follows b2dBody if it exists
		if(this.getBody()!=null && sprite != null){
			float bodyX = body.getPosition().x * TotemDefender.BOX_TO_WORLD;
			float bodyY = body.getPosition().y * TotemDefender.BOX_TO_WORLD;
			float spriteHW = (sprite.getWidth() * sprite.getScaleX()) /2;
			float spriteHH = (sprite.getHeight() * sprite.getScaleY()) /2;
			sprite.setPosition(0, 0);
			sprite.setRotation((float)Math.toDegrees(body.getAngle()));
			sprite.setPosition(bodyX - spriteHW, bodyY - spriteHH);
		}
		
	}
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(sprite != null){
			batch.begin();
			sprite.draw(batch);
			batch.end();
		}
	}
	
	public abstract void spawn(TotemDefender game);
	
	public boolean isSpawned(){
		return isSpawned;
	}
	
	public Vector2 getPosition(){
		if(body != null){
			return body.getPosition().scl(TotemDefender.BOX_TO_WORLD);
		}else if(sprite != null){
			return new Vector2(sprite.getX(), sprite.getY());
		}
		
		return new Vector2();
	}
	
	public void setPosition(Vector2 position){
		if(body != null){
			position.scl(TotemDefender.WORLD_TO_BOX);
			body.setTransform(position,getRotation());
		}else if(sprite != null){
			sprite.setPosition(position.x, position.y);
		}
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
		body.setTransform(getPosition().scl(TotemDefender.WORLD_TO_BOX),rotation);
	}
	
	/*
	public void rotateAround(float rotation, Vector2 point){
		if(body != null){
			float rad = (float)Math.toRadians(rotation);		
			setRotation(getBody().getAngle() + rad);
		}
		/*
		if(sprite != null){
			sprite.setPosition(sprite.getX() - point.x, sprite.getY() - point.y);
			sprite.rotate(rotation);
			sprite.setPosition(sprite.getX() + point.x, sprite.getY() + point.y);
		}
	}*/
	
	public void setSprite(Sprite sprite){
		this.sprite=sprite;
	}
	
	public Sprite getSprite(){
	return sprite;	
	}
	

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	
}