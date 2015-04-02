package com.totemdefender.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.totemdefender.TotemDefender;

public class BackgroundEntity extends Entity{
	private class Cloud{
		Texture cloud;
		float width, height;
		float x, y;
		float speed;
	}
	
	private Texture backgroundTexture;
	private Texture sunTexture;
	private ArrayList<Cloud> clouds;
	private Texture castleTexture;
	private float castleAspectRatio = 1.14f;
	private float castleDrawWidth, castleDrawHeight;
	private Color color = Color.valueOf("124C1F");
	
	private long spawnDelay = 4000;
	private long lastSpawn = 0;
	
	public BackgroundEntity(){
		clouds = new ArrayList<Cloud>();
	}
	
	@Override
	public void spawn(TotemDefender game) {
		backgroundTexture = game.getAssetManager().get("background/bg.png", Texture.class);
		sunTexture = game.getAssetManager().get("background/sun.png", Texture.class);
		castleTexture = game.getAssetManager().get("background/temple.png", Texture.class);
		castleDrawWidth = castleTexture.getWidth()/2;//TotemDefender.V_WIDTH/5;
		castleDrawHeight = castleDrawWidth / castleAspectRatio;
		
		isSpawned = true;
	}
	
	@Override
	public void update(TotemDefender game){
		Iterator<Cloud> it = clouds.iterator();
		while(it.hasNext()){
			Cloud c = it.next();
			c.x += c.speed;
			if(c.x > TotemDefender.V_WIDTH + c.width){
				it.remove();
			}
		}
		
		if(System.currentTimeMillis() - lastSpawn > spawnDelay){
			spawnCloud(game);
		}
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		super.render(batch, shapeRenderer);
		
		batch.begin();
		batch.draw(backgroundTexture, -TotemDefender.V_WIDTH/2, -TotemDefender.V_HEIGHT/2, TotemDefender.V_WIDTH, TotemDefender.V_HEIGHT);
		batch.end();
		
		batch.begin();
		batch.draw(sunTexture, TotemDefender.V_WIDTH/2 - 400, TotemDefender.V_HEIGHT/2 - 400, 300, 300); 
		for(Cloud c : clouds){
			batch.draw(c.cloud, c.x, c.y, c.width, c.height);
		}
		batch.setColor(1, 1, 1, .9f);
		batch.draw(castleTexture, -castleDrawWidth/2, -TotemDefender.V_HEIGHT/2 + TotemDefender.GROUND_HEIGHT, castleDrawWidth, castleDrawHeight); 
		batch.setColor(1, 1, 1, 1);
		batch.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(-TotemDefender.V_WIDTH/2, -TotemDefender.V_HEIGHT/2, TotemDefender.V_WIDTH, TotemDefender.GROUND_HEIGHT);
		shapeRenderer.end();
	}
	
	private void spawnCloud(TotemDefender game){
		Random rand = new Random();
		int num = rand.nextInt(2) + 1;
		
		Cloud c = new Cloud();
		c.cloud = game.getAssetManager().get("background/cloud_"+num+".png", Texture.class);
		float aspectRatio = 0;
		if(num == 1){
			aspectRatio = 2.34f;
		}else{
			aspectRatio = aspectRatio;		
		}
		c.width = rand.nextFloat() * 200 + 50;
		c.height = c.width/2.34f;
		c.y = rand.nextFloat() * TotemDefender.V_HEIGHT/2 - 50;
		c.x = -TotemDefender.V_WIDTH/2 - c.width;
		c.speed = c.width/500;
		clouds.add(c);

		lastSpawn = System.currentTimeMillis();
	}

}
