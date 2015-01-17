package com.totemdefender;

import java.util.ArrayList;

import com.totemdefender.entities.Entity;
import com.totemdefender.states.StateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	
	/** Debug */
	private Box2DDebugRenderer b2dRenderer; //Create a renderer for box2d
	
	@Override
	public void create () {
		batch  = new SpriteBatch();
		camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
		camera.update();
		world  = new World(GRAVITY, true);	
		stateManager= new StateManager(); 
		entities 	= new ArrayList<Entity>(); 	
		b2dRenderer = new Box2DDebugRenderer();
		
		//Lets create a test physics body
		BodyDef testDef = new BodyDef();
		testDef.type = BodyType.DynamicBody;
		
		for(int i = 0; i < 50; i++){
			testDef.position.set(i * WORLD_TO_BOX, i * WORLD_TO_BOX);
			Body body = world.createBody(testDef);
			
			CircleShape shape = new CircleShape();
			shape.setRadius(5f * WORLD_TO_BOX);
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0.5f; 
			fixtureDef.friction = 0.4f;
			fixtureDef.restitution = 0.4f;
	
			// Create our fixture and attach it to the body
			body.createFixture(fixtureDef);
			shape.dispose();
		}
		
		
		//Make the ground
		float hw = V_WIDTH / 2;
		float hh = 10;
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, -V_HEIGHT/2 * WORLD_TO_BOX);
		
		Body groundBody = world.createBody(groundDef);
		
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(hw * WORLD_TO_BOX, hh * WORLD_TO_BOX);
		
		groundBody.createFixture(groundShape, 0.0f);
		
		groundShape.dispose();
		
	}

	@Override
	public void render () {		
		accum += Gdx.graphics.getDeltaTime();
		
		while(accum >= STEP){
			accum -= STEP;
			
			//Update
			world.step(STEP, 8, 6);
			stateManager.update();
			
			for(Entity ent : entities){
				ent.update();
			}
		}
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.18f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//Render
		for(Entity ent : entities){
			ent.render();
		}
		
		batch.end();
		
		if(DEBUG){
			b2dRenderer.render(world, camera.combined.cpy().scl(BOX_TO_WORLD));
		}
	}
}
