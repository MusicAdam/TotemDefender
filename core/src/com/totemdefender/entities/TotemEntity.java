package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
import com.totemdefender.entities.blocks.BlockEntity;

public class TotemEntity extends BlockEntity {
	private boolean onGround = false;
	
	public TotemEntity(Player owner) {
		super(owner, Material.Totem, Shape.Totem);
		setRotatable(false);
	}
	
	@Override
	public void spawn(TotemDefender game){
		super.spawn(game);
		
		Texture tex = game.getAssetManager().get("totem_face_shaded.png", Texture.class);
		setSprite(new Sprite(tex));
		getSprite().setSize(TotemDefender.BLOCK_SIZE * xScale, TotemDefender.BLOCK_SIZE * yScale);
		getSprite().setOriginCenter();
		
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
		
		isSpawned = true;
	}
	
	public boolean isOnGround(){
		return onGround; 
	}
}
