package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.TotemDefender;

public class GroundEntity extends Entity{

	@Override
	public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
		/*TotemDefender game = TotemDefender.Get();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.valueOf("124C1F"));
		shapeRenderer.rect(getPosition().x - TotemDefender.V_WIDTH/2, getPosition().y - TotemDefender.GROUND_HEIGHT/2, TotemDefender.V_WIDTH, TotemDefender.GROUND_HEIGHT);
		shapeRenderer.end();*/
	}
	
	@Override
	public void spawn(TotemDefender game) {
		float hw = Gdx.graphics.getWidth() * 2;
		float hh = 10;
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, -TotemDefender.V_HEIGHT/2 * TotemDefender.WORLD_TO_BOX + ((hh + 1) * TotemDefender.WORLD_TO_BOX));
		
		Body groundBody = game.getWorld().createBody(groundDef);
		groundBody.setUserData(this);
		
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox((hw - 1) * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
		Fixture fix = groundBody.createFixture(groundShape, 0.0f);
		Filter filter = fix.getFilterData();
		filter.categoryBits = Entity.GROUND;		
		filter.maskBits = Entity.EVERYTHING;
		fix.setFilterData(filter);
		
		groundShape.dispose();
		
		setBody(groundBody);
		isSpawned = true;
	}
	
}
