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
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.Entity;
import com.totemdefender.entities.GroundEntity;
import com.totemdefender.menu.ScoreLine;
import com.totemdefender.player.Player;

public abstract class BlockEntity extends Entity{
	public static final int RECTANGLE_XSCALE 	= 2;
	public static final int RECTANGLE_YSCALE 	= 1;
	public static final int SQUARE_XSCALE 		= 1;
	public static final int SQUARE_YSCALE 		= 1;
	public static final int TOTEM_XSCALE 		= 1;
	public static final int TOTEM_YSCALE 		= 2;
	public static final int WOOD_SQUARE_COST			= 25;
	public static final int WOOD_RECTANGLE_COST			= 50;
	public static final int STONE_SQUARE_COST			= 75;
	public static final int STONE_RECTANGLE_COST		= 100;
	public static final int JADE_SQUARE_COST			= 200;
	public static final int JADE_RECTANGLE_COST			= 300;
	public static final float BASE_DENSITY = .8f;
	public static final float WOOD_DENSITY_MODIFIER = .9f;
	public static final float STONE_DENSITY_MODIFIER = 1.2f;
	public static final float JADE_DENSITY_MODIFIER = 1.35f;
	
	public enum Material{
		Wood,
		Stone,
		Jade,
		Totem
	}
	
	public enum Shape{
		Rectangle, 
		Square,
		Totem
	}
	
	protected float xScale, yScale; //This is the scale of the block in multiples of TotemDefender.BlockSize in each direction
	private int cost;
	private Fixture fixture;
	private boolean rotated = false;
	private boolean rotatable = true;
	private Material material;
	private Shape	shape;
	private boolean shouldDelete=false;
	
	public static int GetCost(Material material, Shape shape){
		if(material == Material.Wood && shape == Shape.Square){
			return WOOD_SQUARE_COST;
		}else if(material == Material.Wood && shape == Shape.Rectangle){
			return WOOD_RECTANGLE_COST;
		}else if(material == Material.Stone && shape == Shape.Square){
			return STONE_SQUARE_COST;			
		}else if(material == Material.Stone && shape == Shape.Rectangle){
			return STONE_RECTANGLE_COST;						
		}else if(material == Material.Jade && shape == Shape.Square){
			return JADE_SQUARE_COST;
		}else if(material == Material.Jade && shape == Shape.Rectangle){
			return JADE_RECTANGLE_COST;
		}
		
		return 0;
	}
	
	public static float GetDensity(Material material){
		switch(material){
			case Wood:
				return BASE_DENSITY * WOOD_DENSITY_MODIFIER;
			case Stone:
				return BASE_DENSITY * STONE_DENSITY_MODIFIER;
			case Jade:
				return BASE_DENSITY * JADE_DENSITY_MODIFIER;
			default:
				break;
		}
		return BASE_DENSITY;
	}
	
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
		
		this.cost = GetCost(material, shape);
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
		fixtureDef.density = GetDensity(material); 
		fixtureDef.friction = 0.6f;
		fixtureDef.restitution = 0.2f;
		fixtureDef.filter.categoryBits = Entity.BLOCK;
		fixtureDef.filter.maskBits = (short) (Entity.GROUND | Entity.PEDESTAL | projectileMask | Entity.BLOCK);
		
		//deletion of blocks
		final BlockEntity thisRef=this;
		
	
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
		getBody().setUserData(this);

		shape.dispose();	

		isSpawned = true;
	}
	
	public void delete(TotemDefender game){
		if(shouldDelete==true){
			Player pl = (getOwner().getID() == 1) ? game.getPlayer2() : game.getPlayer1();
			pl.addScore(ScoreLine.ScoreType.Destruction, getCost() * pl.getScoreMultiplier());
			pl.incrementScoreMultiplier();
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
