package com.totemdefender;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.totemdefender.entities.Entity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.Menu;
import com.totemdefender.states.BuildState;
import com.totemdefender.states.StateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TotemDefender extends ApplicationAdapter {
	/** Static configuration variables */
	public static final float 	STEP 				= 1/60f; 	//Time step for simulation in seconds.
	public static final float 	WORLD_TO_BOX = 0.01f;
	public static final float   BOX_TO_WORLD = 100f;
	public static final Vector2 GRAVITY				= new Vector2(0, -9.8f); //Gravity for physics simulation
	public static final int 	POSITION_ITERATIONS = 6; 		//Position iterations for box2d
	public static final int 	VELOCITY_ITERATIONS = 8; 		//Velocity iterations for box2d
	public static final boolean DEBUG				= true;		//Debug rendering and output when true 
	public static final float	BLOCK_SIZE			= 30f;     //The default size of a block
	public static final float 	STACK_LOCATION	 	= 3/4f; 	//The "stack" (player's weapon, pedastal, and build area) will be this proportion away from the center of the screen.
	public static final	float	PEDESTAL_WIDTH		= 75;
	public static final	float	PEDESTAL_HEIGHT		= 100;
	public static final float 	GROUND_HEIGHT		= 20;
	

	/** Instance variables */
	private static TotemDefender game;
	private int					screenWidth;
	private int					screenHeight;	
	private SpriteBatch 		entityBatch;	//Sprite batch for rendering
	private ArrayList<Entity> 	entities; //List of spawned entities
	private ArrayList<Menu> 	menus; //List of spawned entities
	private StateManager		stateManager; //Controls game's states.
	private World				world; //Box2d physics world.
	private OrthographicCamera  camera; //Games camera
	private float 				accum = 0;	//Time accumulator to ensure updates are performed at the same rate as step.
	private AssetManager 		assetManager; //Libgdx utility to asynchronusly load assets.
	private InputMultiplexer	inputMultiplexer; //Multiplexer for input handling
	private InputMultiplexer	menuMultiplexer; //Menus will attach to this
	private InputHandler		inputHandler; //Game controls will add listeners to this
	private Viewport			viewport;	//Keep the game looking good at any aspect ratio
	private ShapeRenderer		menuRenderer; //This is for menus and is therefore not not transformed by cam matrix
	private ShapeRenderer		entityRenderer;
	private SpriteBatch 		menuBatch; // ""			""
	private Player				player1;
	private Player				player2;
	private Queue<Entity> 		deleteQueue;
	
	/** Control Variables */
	private boolean isDoneBuilding;
	
	/** Debug */
	private Box2DDebugRenderer b2dRenderer; //Create a renderer for box2d
	
	@Override
	public void create () {
		//Initialize
		game = this;
		
		//Set virtual size aspect ratio to the desktop's aspect ratio.
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode());
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		entityBatch  = new SpriteBatch();
		camera = new OrthographicCamera(screenWidth, screenHeight);
		camera.update();
		world  = new World(GRAVITY, true);	
		stateManager= new StateManager(this); 
		entities 	= new ArrayList<Entity>(); 	
		b2dRenderer = new Box2DDebugRenderer();
		assetManager = new AssetManager();
		inputMultiplexer = new InputMultiplexer();
		menuMultiplexer = new InputMultiplexer();
		inputHandler = new InputHandler(this);
		viewport = new ExtendViewport(screenWidth, screenHeight, camera);
		menus = new ArrayList<Menu>();
		menuRenderer = new ShapeRenderer();
		entityRenderer = new ShapeRenderer();
		menuBatch = new SpriteBatch();
		deleteQueue = new ConcurrentLinkedQueue<Entity>();
		
		inputMultiplexer.addProcessor(menuMultiplexer);
		inputMultiplexer.addProcessor(inputHandler);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		world.setContactListener(new ContactHandler());
		
		//Load resources
		assetManager.load("cannon.png", Texture.class);
		assetManager.finishLoading(); //Block until finished loading for now.
		
		////		DEBUG STUFF	 /////	 
		//stateManager.attachState(new ResolutionTestState());	
		//stateManager.attachState(new TestState());		
		stateManager.attachState(new BuildState());
		//Add an exit function
		inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.ESCAPE){
			@Override
			public boolean callback(){
				Gdx.app.exit();
				return true;
			}
		});
		
	}

	@Override
	public void render () {		
		//GL Housekeeping
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.18f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//Process deletions
		while(!deleteQueue.isEmpty()){
			Entity ent = deleteQueue.poll();
			world.destroyBody(ent.getBody());
		}
		
		accum += Gdx.graphics.getDeltaTime(); //Add previous frame time to accumulator
				
		
		
		//Maintain updates at the STEP rate
		while(accum >= STEP){
			accum -= STEP;
			
			//Update physics world & state manager
			world.step(STEP, 8, 6);
			stateManager.update();
			
			for(Menu menu : menus){
				menu.update();
			}
			
			//Update entities
			for(Entity ent : entities){
				ent.update(this);
			}
		}
		
		//Update batch projection incase it has changed
		entityBatch.setProjectionMatrix(camera.combined);
		entityRenderer.setProjectionMatrix(camera.combined);
		entityBatch.enableBlending();
		menuBatch.enableBlending();
		
		//Render entities
		for(Entity ent : entities){
			ent.render(entityBatch, entityRenderer);
		}
		
		for(Menu menu : menus){
			menu.render(menuBatch, menuRenderer);
		}
		
		//Debug b2d rendering
		if(DEBUG){
			b2dRenderer.render(world, camera.combined.cpy().scl(BOX_TO_WORLD));
		}
		
		camera.update();
	}
	
	@Override
	public void resize(int w, int h){
		viewport.update(w, h);
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
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
		if(ent.getBody() != null){
			if(!deleteQueue.contains(ent))
				deleteQueue.add(ent);
		}
		
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
	
	/**
	 * 
	 * @return camera instance
	 */
	public OrthographicCamera getCamera(){
		return camera;
	}
	
	/**
	 * 
	 * @return inputHandler instance
	 */
	public InputHandler getInputHandler(){
		return inputHandler;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	public void addMenu(Menu menu){
		menus.add(menu);
		menuMultiplexer.addProcessor(menu);
	}
	
	public void removeMenu(Menu menu){
		menus.remove(menu);
		menuMultiplexer.removeProcessor(menu);
	}
	
	/**
	 * 
	 * @return isDoneBuilding instance variable representing whether both players have finished building
	 */
	public boolean isDoneBuilding(){ return isDoneBuilding; }
	/**
	 * 
	 * @param toggle boolean indicating whether both players have finished building
	 */
	public void setDoneBuilding(boolean toggle){ isDoneBuilding = toggle; }
	
	/**
	 * 
	 * @param screen coordinates
	 * @return screen coordinates 
	 */
	public Vector2 screenToWorld(Vector2 screen){
		Vector3 screen3 = new Vector3(screen.x, screen.y, 0);
		Vector3 world3 = camera.project(screen3);
		return new Vector2(world3.x, world3.y);
	}
	
	/**
	 * 
	 * @return static game reference
	 */
	public static TotemDefender Get(){
		return game;
	}
	
	/**
	 * Shortcut to enable alpha channel for shaperenderer
	 */
	public static void EnableBlend(){
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void DisableBlend(){
		Gdx.gl.glDisable(GL20.GL_BLEND);		
	}
	
	public Player getPlayer1(){ return player1; }
	public Player getPlayer2(){ return player2; }
	public void setPlayer1(Player pl){ player1 = pl; }
	public void setPlayer2(Player pl){ player2 = pl; }
}
