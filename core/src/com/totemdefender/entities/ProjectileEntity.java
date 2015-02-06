package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.CollisionListener;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class ProjectileEntity extends Entity{
	public static final long LIFESPAN = 3000; //Lifespan in ms until projectile is marked for deletion
	public static final float RADIUS = 7;
	private Vector2 barrelPos;
	private boolean shouldDelete;
	private long startTime;
	
	public ProjectileEntity(Player owner, Vector2 barrelPos) {
		super(owner);
		
		this.barrelPos = barrelPos;
	}

	@Override
	public void spawn(TotemDefender game) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;		
		def.position.set(barrelPos.x * TotemDefender.WORLD_TO_BOX, barrelPos.y * TotemDefender.WORLD_TO_BOX);
		setBody(game.getWorld().createBody(def));
		
		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS * TotemDefender.WORLD_TO_BOX);
		
		short categoryBits 	= (getOwner() == game.getPlayer1()) ? Entity.PLAYER1 : Entity.PLAYER2;
		short maskBits 			= (categoryBits == Entity.PLAYER1) ? Entity.PLAYER2 : Entity.PLAYER1;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.4f;
		fixtureDef.filter.categoryBits = Entity.PROJECTILE;
		fixtureDef.filter.maskBits = (short) (maskBits | Entity.GROUND);

		// Create our fixture and attach it to the body
		final ProjectileEntity thisRef = this;
		getBody().createFixture(fixtureDef).setUserData(new CollisionListener(){
			@Override
			public void beginContact(Fixture other, Contact contact) {
				if(other.getBody().getUserData() instanceof GroundEntity){
					thisRef.shouldDelete = true;					
				}
			}

			@Override
			public void endContact(Fixture other, Contact contact) {
				
			}
		});
		getBody().setBullet(true);

		shape.dispose();
		
		startTime = System.currentTimeMillis();
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

}
