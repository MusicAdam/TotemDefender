package com.totemdefender.entities.blocks;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.Entity;

public abstract class BlockEntity extends Entity{
	protected float cost;
	protected float xScale, yScale; //This is the scale of the block in multiples of TotemDefender.BlockSize in each direction
	private Fixture fixture;
	private boolean rotated = false;
	
	public BlockEntity(Player owner, float cost, float xScale, float yScale){
		super(owner);
		this.xScale = xScale;
		this.yScale = yScale;
		this.cost = cost;
	}
	
	@Override
	public void spawn(TotemDefender game) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		setBody(game.getWorld().createBody(def));
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((xScale * TotemDefender.BLOCK_SIZE * TotemDefender.WORLD_TO_BOX)/2, (yScale * TotemDefender.BLOCK_SIZE * TotemDefender.WORLD_TO_BOX)/2);
		
		short categoryBits 	= (getOwner() == game.getPlayer1()) ? Entity.PLAYER1 : Entity.PLAYER2;
		short maskBits 			= (categoryBits == Entity.PLAYER1) ? Entity.PLAYER2 : Entity.PLAYER1;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = (short) (maskBits | Entity.GROUND);

		// Create our fixture and attach it to the body
		fixture = getBody().createFixture(fixtureDef);

		shape.dispose();	
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
}
