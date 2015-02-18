package com.totemdefender.entities.blocks;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.Entity;

public abstract class BlockEntity extends Entity{
	public enum Material{
		Wood,
		Stone,
		Jade,
		Totem
	}
	
	public enum Shape{
		Triangle,
		Rectangle, 
		Square,
		Totem
	}
	
	protected float cost;
	protected float xScale, yScale; //This is the scale of the block in multiples of TotemDefender.BlockSize in each direction
	private Fixture fixture;
	private boolean rotated = false;
	private boolean rotatable = true;
	private Material material;
	private Shape	shape;
	
	public BlockEntity(Player owner, Material material, Shape shape){
		super(owner);
		
		if(shape == Shape.Rectangle){
			xScale = 2;
			yScale = 1;
		}else if(shape == Shape.Totem){
			xScale = 1;
			yScale = 2;
		}else{
			xScale = 1;
			yScale = 1;
		}
		
		this.cost = 0;
		this.material = material;
		this.shape = shape;
	}
	
	public static String GetRandomAsset(Material material, Shape shape){
		String materialId="", shapeId="";
		
		switch(material){
			case Wood:
				materialId = "wood";
				break;
			case Stone:
				materialId = "stone";
				break;
			case Jade:
				materialId = "jade";
				break;
			case Totem:
				materialId = "totem";
				break;
		}
		
		switch(shape){
			case Square:
				shapeId = "square";
				break;
			case Rectangle:
				shapeId = "rectangle";
				break;
			case Triangle:
				shapeId = "triangle";
				break;
			case Totem:
				shapeId = "totem";
				break;
		}
		
		Random rand = new Random();
		int num = rand.nextInt(3) + 1;
		
		return "blocks/block_"+shapeId+"_"+materialId+"_"+num+".png";
	}
	
	@Override
	public void spawn(TotemDefender game) {
		if(shape != Shape.Totem){
			Texture blockTexture = game.getAssetManager().get(GetRandomAsset(material, shape), Texture.class);
			blockTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			setSprite(new Sprite(blockTexture));
			getSprite().setSize(TotemDefender.BLOCK_SIZE * xScale, TotemDefender.BLOCK_SIZE * yScale);
			getSprite().setOriginCenter();
		}
		
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		setBody(game.getWorld().createBody(def));
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((xScale * TotemDefender.BLOCK_SIZE * TotemDefender.WORLD_TO_BOX)/2, (yScale * TotemDefender.BLOCK_SIZE * TotemDefender.WORLD_TO_BOX)/2);

		short projectileMask = (getOwner().getID() == 1) ? Entity.PLAYER2_PROJECTILE : Entity.PLAYER1_PROJECTILE;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = Entity.BLOCK;
		fixtureDef.filter.maskBits = (short) (Entity.GROUND | Entity.PEDESTAL | projectileMask | Entity.BLOCK);

		// Create our fixture and attach it to the body
		fixture = getBody().createFixture(fixtureDef);

		shape.dispose();	
		
		isSpawned = true;
	}
	
	public void setDensity(float density){
		if(fixture == null) return;
		
		fixture.setDensity(density);
	}
	
	public float getDensity(){
		if(fixture == null) return 0.0f;
		
		return fixture.getDensity();
	}
	
	public void setCost(float amount){
		this.cost = amount;
	}
	
	public void rotate(){
		if(!isRotatable()) return;
		
		float rad = (float)Math.toRadians(90);		
		if(rotated){
			setRotation(getBody().getAngle() - rad);
			rotated = false;
		}else{
			rotated = true;
			setRotation(getBody().getAngle() + rad);			
		}
	}
	
	public float getCost(){ return cost; }
	public float getXScale(){ return xScale; }
	public float getYScale(){ return yScale; }
	public float getWidth(){ 
		if(!rotated)
			return xScale * TotemDefender.BLOCK_SIZE; 
		return yScale * TotemDefender.BLOCK_SIZE;
	}
	public float getHeight(){ 
		if(!rotated)
			return yScale * TotemDefender.BLOCK_SIZE; 
		return xScale * TotemDefender.BLOCK_SIZE;
	}

	public boolean isRotatable() {
		return rotatable;
	}

	public void setRotatable(boolean rotateable) {
		this.rotatable = rotateable;
	}
}
