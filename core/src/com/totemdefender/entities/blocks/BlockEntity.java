package com.totemdefender.entities.blocks;

import com.badlogic.gdx.graphics.Color;
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
	protected float cost;
	protected float xScale, yScale; //This is the scale of the block in multiples of TotemDefender.BlockSize in each direction
	private Fixture fixture;
	private boolean rotated = false;
	private boolean rotatable = true;
	
	public BlockEntity(Player owner, float cost, float xScale, float yScale){
		super(owner);
		this.xScale = xScale;
		this.yScale = yScale;
		this.cost = cost;
	}
	
	@Override
	public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
		float angle = 0;
		if(getBody().isActive()){
			if(rotated)
				angle = 90;
			angle += (float)Math.toDegrees(getBody().getAngle());
		}
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(.8f, .6f, 0, 1);
		shapeRenderer.identity();
		shapeRenderer.translate(getPosition().x, getPosition().y, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		shapeRenderer.identity();
		shapeRenderer.end();
	}
	
	@Override
	public void spawn(TotemDefender game) {
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
