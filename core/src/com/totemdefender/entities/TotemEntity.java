package com.totemdefender.entities;

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
import com.totemdefender.entities.blocks.BlockEntity;

public class TotemEntity extends BlockEntity {
	private boolean onGround = false;
	
	public TotemEntity(Player owner) {
		super(owner, Material.Totem, Shape.Totem);
		setRotatable(false);
	}
	

	@Override
	public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
		float angle = 0;
		if(getBody().isActive()){
			angle = (float)Math.toDegrees(getBody().getAngle());
		}
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(.8f, .6f, .8f, 1);
		shapeRenderer.identity();
		shapeRenderer.translate(getPosition().x, getPosition().y, 0);
		shapeRenderer.rotate(0, 0, 1, angle);
		shapeRenderer.rect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		shapeRenderer.identity();
		shapeRenderer.end();
	}
	
	@Override
	public void spawn(TotemDefender game){
		super.spawn(game);
		
		final TotemEntity thisRef = this;
		getBody().getFixtureList().first().setUserData(new CollisionListener(){

			@Override
			public void beginContact(Fixture other, Contact contact) {
				if(other.getBody().getUserData() instanceof GroundEntity){
					thisRef.onGround = true;
				}
			}

			@Override
			public void endContact(Fixture other, Contact contact) {
			}
			
		});
	}
	
	public boolean isOnGround(){
		return onGround; 
	}
}
