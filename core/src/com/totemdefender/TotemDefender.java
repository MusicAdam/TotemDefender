package com.totemdefender;

import java.util.ArrayList;

import com.totemdefender.entities.Entity;
import com.totemdefender.states.StateManager;
import com.totemdefender.states.TestState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TotemDefender extends ApplicationAdapter {
	/** Static configuration variables */
	public static final float 	STEP 				= 1/60f; 	//Time step for simulation in seconds.
	public static final float 	WORLD_TO_BOX = 0.01f;
	public static final float   BOX_TO_WORLD = 100f;
	public static final Vector2 GRAVITY				= new Vector2(0, -9.8f); //Gravity for physics simulation
	public static final int 	POSITION_ITERATIONS = 6; 		//Position iterations for box2d
	public static final int 	VELOCITY_ITERATIONS = 8; 		//Velocity iterations for box2d
	public static final int 	V_WIDTH				= 400;		//Virtual width of the game
	public static final int		V_HEIGHT			= 400; 		//    "   height    " 	
	public static final boolean DEBUG				= true;		//Debug rendering and output when true 

	/** Instance variables */
	private SpriteBatch 		batch;	//Sprite batch for rendering
	private ArrayList<Entity> 	entities; //List of spawned entities
	private StateManager		stateManager; //Controls game's states.
	private World				world; //Box2d physics world.
	private OrthographicCamera  camera; //Games camera
	private float 				accum = 0;	//Time accumulator to ensure updates are performed at the same rate as step.
	private AssetManager 		assetManager; //Libgdx utility to asynchronusly load assets.
	
	/** Debug */
	private Box2DDebugRenderer b2dRenderer; //Create a renderer for box2d
	
	@Override
	public void create () {
		//Initialize
		batch  = new SpriteBatch();
		camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
		camera.update();
		world  = new World(GRAVITY, true);	
		stateManager= new StateManager(this); 
		entities 	= new ArrayList<Entity>(); 	
		b2dRenderer = new Box2DDebugRenderer();
		assetManager = new AssetManager();
		
		
		////		DEBUG STUFF	 /////	
		stateManager.attachState(new TestState());			
	}

	@Override
	public void render () {		
		accum += Gdx.graphics.getDeltaTime(); //Add previous frame time to accumulator
		
		//Maintain updates at the STEP rate
		while(accum >= STEP){
			accum -= STEP;
			
			//Update physics world & state manager
			world.step(STEP, 8, 6);
			stateManager.update();
			
			//Update entities
			for(Entity ent : entities){
				ent.update();
			}
		}
		
		//GL Housekeeping
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.18f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Update batch projection incase it has changed
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//Render entities
		for(Entity ent : entities){
			ent.render();
		}
		
		batch.end();
		
		//Debug b2d rendering
		if(DEBUG){
			b2dRenderer.render(world, camera.combined.cpy().scl(BOX_TO_WORLD));
		}
	}
	
	/** addEntity registers a spawned entity with the game so it will be rendered and updated.
	 * @return true if the entity is spawned and was added, false otherwise	 */
	public boolean addEntity(Entity ent){
		if(!ent.isSpawned()) return false;
		
		return entities.add(ent);
	}
	
	/** destroyEntity removes a spawned entity from the entities list and also removes it from the box2d world if it has a body 
	 * @return true on success, false on failure*/
	public boolean destroyEntity(Entity ent){
		if(ent.getBody() != null)
			world.destroyBody(ent.getBody());
		
		return entities.remove(ent);
	}
	
	/** 
	 * @param name to search for
	 * @return list of entities by given name, or empty list	 */
	public ArrayList<Entity> findEntitiesByName(String name){
		ArrayList<Entity> found = new ArrayList<Entity>();
		
		for(Entity ent : entities){
			if(ent.getName().equals(name))
				found.add(ent);
		}
		
		return found;
	}
	
	/**
	 * @return all spawned entities in the world
	 */
	public ArrayList<Entity> getAllEntities(){
		return entities;
	}
	
	/**
	 * @return stateManager instance
	 */
	public StateManager getStateManager(){
		return stateManager;
	}
	
	/**
	 * 
	 * @return box2d world instance
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * 
	 * @return assetManager instance
	 */
	public AssetManager getAssetManager(){
		return assetManager;
	}
}
