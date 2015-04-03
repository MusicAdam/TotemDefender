package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.CollisionListener;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.menu.ScoreLine;
import com.totemdefender.player.Player;

public class ProjectileEntity extends Entity{
	public static final long LIFESPAN = 3000; //Lifespan in ms until projectile is marked for deletion
	public static final float RADIUS = 7.5f;
	private Vector2 barrelPos;
	private boolean shouldDelete, contactedBlock=false;
	private long startTime;
	private float radius;
	
	public ProjectileEntity(Player owner, Vector2 barrelPos) {
		super(owner);
		
		this.barrelPos = barrelPos;
		radius = RADIUS;
	}

	@Override
	public void spawn(final TotemDefender game) {
		Texture texture = game.getAssetManager().get("projectiles/cannon_projectile.png", Texture.class);
		setSprite(new Sprite(texture));
		
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;		
		def.position.set(barrelPos.x * TotemDefender.WORLD_TO_BOX, barrelPos.y * TotemDefender.WORLD_TO_BOX);
		setBody(game.getWorld().createBody(def));
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius * TotemDefender.WORLD_TO_BOX);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.2f; 
		fixtureDef.friction = 0.6f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = (getOwner().getID() == 1) ? Entity.PLAYER1_PROJECTILE : Entity.PLAYER2_PROJECTILE;
		fixtureDef.filter.maskBits = Entity.GROUND | Entity.BLOCK;

		// Create our fixture and attach it to the body
		final ProjectileEntity thisRef = this;
		getBody().createFixture(fixtureDef).setUserData(new CollisionListener(){
			@Override
			public void beginContact(Fixture other, Contact contact) {
				if(other.getBody().getUserData() instanceof GroundEntity){
					game.setSound("sounds/Cannon/ballhitsground.mp3");
					game.getSound().play();
					
					if(!thisRef.contactedBlock){
						thisRef.owner.addScore(ScoreLine.ScoreType.Miss, Player.MISS_SCORE);
						thisRef.owner.resetScoreMultiplier();
					}
					thisRef.shouldDelete = true;					
				}else if(other.getBody().getUserData() instanceof BlockEntity){
						thisRef.contactedBlock = true;	
				}
			}

			@Override
			public void endContact(Fixture other, Contact contact) {
				
			}
		});
		getBody().setBullet(true);

		shape.dispose();
		
		startTime = System.currentTimeMillis();
		
		isSpawned = true;
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);
		
		if(System.currentTimeMillis() - startTime >= LIFESPAN)
			shouldDelete = true;
	}
	
	public boolean shouldDelete(){ return shouldDelete; }

	public void setShouldDelete(boolean b) {
		this.shouldDelete = b;
		
	}
	
	public void setRadius(float r){
		radius = r;
	}
	
	public boolean getContactedBlock(){
		return contactedBlock;
	}

}
