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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.CollisionListener;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.Entity;
import com.totemdefender.entities.GroundEntity;

public abstract class BlockEntity extends Entity{
	public static final int RECTANGLE_XSCALE 	= 2;
	public static final int RECTANGLE_YSCALE 	= 1;
	public static final int SQUARE_XSCALE 		= 1;
	public static final int SQUARE_YSCALE 		= 1;
	public static final int TOTEM_XSCALE 		= 1;
	public static final int TOTEM_YSCALE 		= 2;
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
	
	protected int cost=0;
	protected float xScale, yScale; //This is the scale of the block in multiples of TotemDefender.BlockSize in each direction
	private Fixture fixture;
	private boolean rotated = false;
	private boolean rotatable = true;
	private Material material;
	private Shape	shape;
	private boolean shouldDelete=false;
	
	public BlockEntity(Player owner, Material material, Shape shape){
		super(owner);
		
		if(shape == Shape.Rectangle){
			xScale = RECTANGLE_XSCALE;
			yScale = RECTANGLE_YSCALE;
		}else if(shape == Shape.Totem){
			xScale = TOTEM_XSCALE;
			yScale = TOTEM_YSCALE;
		}else{
			xScale = SQUARE_XSCALE;
			yScale = SQUARE_YSCALE;
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
		shape.setAsBox((xScale * TotemDefender.BLOCK_SIZE * TotemDefender.WORLD_TO_BOX)/2 - 1 * TotemDefender.WORLD_TO_BOX, (yScale * TotemDefender.BLOCK_SIZE * TotemDefender.WORLD_TO_BOX)/2 - 1 * TotemDefender.WORLD_TO_BOX);

		short projectileMask = (getOwner().getID() == 1) ? Entity.PLAYER2_PROJECTILE : Entity.PLAYER1_PROJECTILE;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = Entity.BLOCK;
		fixtureDef.filter.maskBits = (short) (Entity.GROUND | Entity.PEDESTAL | projectileMask | Entity.BLOCK);

		// Create our fixture and attach it to the body
		
		
		
		//deletion of blocks
		final BlockEntity thisRef=this;
		final TotemDefender gameRef=game;
		
	
		getBody().createFixture(fixtureDef).setUserData(new CollisionListener(){
			@Override
			public void beginContact(Fixture other, Contact contact) {
				if(other.getBody().getUserData() instanceof GroundEntity){
					thisRef.shouldDelete=true;
					
					
				}
			}

			@Override
			public void endContact(Fixture other, Contact contact) {
				
				
			}
		});
		
		//end of deletion code

		shape.dispose();	
		
		isSpawned = true;
	}
	
	public void delete(TotemDefender game){
		if(shouldDelete==true){
			if(this.getOwner().getID()==1)
				game.getPlayer2().setScore(game.getPlayer2().getScore()+cost);
			else
				game.getPlayer1().setScore(game.getPlayer1().getScore()+cost);
			
			game.getLevel().removePlacedBlock(this);
			game.destroyEntity(this);
		}	
	}
		
	public void setDensity(float density){
		if(fixture == null) return;
		
		fixture.setDensity(density);
	}
	
	public float getDensity(){
		if(fixture == null) return 0.0f;
		
		return fixture.getDensity();
	}
	
	public void setCost(int amount){
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
	
	public int getCost(){ return cost; }
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
	
	public void update(TotemDefender game){
		super.update(game);
		delete(game);
	}
}
