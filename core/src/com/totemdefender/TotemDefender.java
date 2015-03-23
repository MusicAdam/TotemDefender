package com.totemdefender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.totemdefender.entities.Entity;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.Panel;
import com.totemdefender.states.AnimationController;
import com.totemdefender.states.BuildState;
import com.totemdefender.states.DepthTestState;
import com.totemdefender.states.MainMenuState;
import com.totemdefender.states.MenuTestState;
import com.totemdefender.states.ResolutionTestState;
import com.totemdefender.states.StateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public class TotemDefender extends ApplicationAdapter {
	/** Static configuration variables */
	public static int		V_WIDTH				= 1280;
	public static int		V_HEIGHT			= 800;
	
	/** Setup supported resolutions on 16:10, 16:9, and 4:3 aspect ratios */
	public static final int		V_WIDTH_43		= 1280;
	public static final int		V_HEIGHT_43		= 960;
	public static final int		V_WIDTH_169		= 1280;
	public static final int		V_HEIGHT_169	= 720;
	public static final int		V_WIDTH_1610	= 1280;
	public static final int		V_HEIGHT_1610	= 800;
	
	public static final float 	STEP 				= 1/60f; 	//Time step for simulation in seconds.
	public static final float 	WORLD_TO_BOX = 0.01f;
	public static final float   BOX_TO_WORLD = 100f;
	public static final Vector2 GRAVITY				= new Vector2(0, -9.8f); //Gravity for physics simulation
	public static final int 	POSITION_ITERATIONS = 6; 		//Position iterations for box2d
	public static final int 	VELOCITY_ITERATIONS = 8; 		//Velocity iterations for box2d
	public static final boolean DEBUG				= true;		//Debug rendering and output when true 
	public static final float	BLOCK_SIZE			= 30f;     //The default size of a block
	public static final float 	STACK_LOCATION	 	= 3/4f; 	//The "stack" (player's weapon, pedastal, and build area) will be this proportion away from the center of the screen.
	public static final	float	PEDESTAL_WIDTH		= BLOCK_SIZE * 4;
	public static final	float	PEDESTAL_HEIGHT		= 100;
	public static final float 	GROUND_HEIGHT		= 20;
	
	/** Pre defined depths for entities (0 is bottom) */
	public static final int BACKGROUND_DEPTH = -1;
	public static final int GROUND_DEPTH = 0;
	public static final int PEDESTAL_DEPTH = 1;
	public static final int BLOCK_DEPTH = 2;
	public static final int WEAPON_DEPTH = 3;
	public static final int TOTEM_DEPTH = 4;
	public static final int PROJECTILE_DEPTH = 5;
	
	

	/** Instance variables */
	private static TotemDefender game;
	private SpriteBatch 		entityBatch;	//Sprite batch for rendering
	private DepthMap<Entity> 	entities; //List of spawned entities
	private DepthMap<Container> 	menus; //List of spawned entities
	private StateManager		stateManager; //Controls game's states.
	private World				world; //Box2d physics world.
	private OrthographicCamera  worldCamera; //World camera
	private OrthographicCamera  menuCamera; //Menu camera
	private float 				accum = 0;	//Time accumulator to ensure updates are performed at the same rate as step.
	private AssetManager 		assetManager; //Libgdx utility to asynchronusly load assets.
	private InputMultiplexer	inputMultiplexer; //Multiplexer for input handling
	private InputHandler		menuInputHandler; //Menus will attach to this
	private InputHandler		gameInputHandler; //Game controls will add listeners to this
	private Viewport			worldViewport;	//Keep the game looking good at any aspect ratio
	private Viewport			menuViewport;	//Can't have two cameras on the same viewport
	private ShapeRenderer		menuRenderer; //This is for menus and is therefore not not transformed by cam matrix
	private ShapeRenderer		entityRenderer;
	private SpriteBatch 		menuBatch; // ""			""
	private Player				player1;
	private Player				player2;
	private Player				winner; //The winner of the last game
	private Queue<Entity> 		entityDeleteQueue; //Need these queues to avoid concurrent modifications during box2d step and menu/entity iteration
	private Queue<DepthWrapper<Entity>> 		entityAddQueue;
	private ConcurrentLinkedQueue<DepthWrapper<Entity>> entityDepthQueue; //Depth change queue
	private ConcurrentLinkedQueue<DepthWrapper<Container>> menuDepthQueue; //Depth change queue
	private Queue<Container> 		menuDeleteQueue;
	private Queue<DepthWrapper<Container>> 		menuAddQueue;
	private Level level;
	private Component keyboardFocus;
	private AnimationController animationController;
	
	/** Control Variables */
	private boolean isDoneBuilding;
	
	/** Debug */
	private Box2DDebugRenderer b2dRenderer; //Create a renderer for box2d
	
	@Override
	public void create () {
		//Initialize
		game = this;
		
		/** Intialize aspect ratio and Virtual resolution to desktop aspect ratio */
		setVirtualSize((float)Gdx.graphics.getDesktopDisplayMode().width/(float)Gdx.graphics.getDesktopDisplayMode().height);
		
		/** Rendering */
		worldCamera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
		worldCamera.update();
		menuCamera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
		menuCamera.update();
		worldViewport = new ExtendViewport(V_WIDTH, V_HEIGHT, worldCamera);
		menuViewport = new ExtendViewport(V_WIDTH, V_HEIGHT, menuCamera);
		menuRenderer = new ShapeRenderer();
		menuBatch = new SpriteBatch();
		entityRenderer = new ShapeRenderer();
		entityBatch  = new SpriteBatch();
		
		world  = new World(GRAVITY, true);	
		stateManager= new StateManager(this); 
		entities 	= new DepthMap<Entity>(); 	
		b2dRenderer = new Box2DDebugRenderer();
		assetManager = new AssetManager();
		inputMultiplexer = new InputMultiplexer();
		menuInputHandler = new InputHandler(this);
		gameInputHandler = new InputHandler(this);
		menus = new DepthMap<Container>();
		entityDeleteQueue = new ConcurrentLinkedQueue<Entity>();
		entityAddQueue = new ConcurrentLinkedQueue<DepthWrapper<Entity>>();
		entityDepthQueue = new ConcurrentLinkedQueue<DepthWrapper<Entity>>();
		menuDepthQueue = new ConcurrentLinkedQueue<DepthWrapper<Container>>();
		menuDeleteQueue = new ConcurrentLinkedQueue<Container>();
		menuAddQueue = new ConcurrentLinkedQueue<DepthWrapper<Container>>();
		
		inputMultiplexer.addProcessor(menuInputHandler);
		inputMultiplexer.addProcessor(gameInputHandler);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		world.setContactListener(new ContactHandler());
		
		loadResources();
		assetManager.finishLoading(); //Block until finished loading for now.
		

		//Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode()); //Default to fullscreen desktop mode
		animationController = new AnimationController();
		stateManager.attachState(animationController);
		stateManager.attachState(new MainMenuState());

		//Add an exit function
		gameInputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, Input.Keys.ESCAPE){
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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		
		
		/** QUEUES **/
		//Process additions
		while(!entityAddQueue.isEmpty()){
			DepthWrapper<Entity> ent = entityAddQueue.poll();
			entities.insert(ent.depth, ent.object);
		}
		
		while(!menuAddQueue.isEmpty()){
			DepthWrapper<Container> cmp = menuAddQueue.poll();
			menus.insert(cmp.depth, cmp.object);
		}
		
		//Process depth changes
		while(!entityDepthQueue.isEmpty()){
			DepthWrapper<Entity> ent = entityDepthQueue.poll();
			entities.move(ent.object, ent.depth);
		}
		
		while(!menuDepthQueue.isEmpty()){
			DepthWrapper<Container> cmp = menuDepthQueue.poll();
			menus.move(cmp.object, cmp.depth);
		}
		
		//Process deletions
		while(!entityDeleteQueue.isEmpty()){
			Entity ent = entityDeleteQueue.poll();
			if(ent.getBody() != null)
				world.destroyBody(ent.getBody());
			entities.remove(ent);
		}
		
		while(!menuDeleteQueue.isEmpty()){
			Container cmp = menuDeleteQueue.poll();
			menus.remove(cmp);
		}
		
		//Maintain updates at the STEP rate
		accum += Gdx.graphics.getDeltaTime(); //Add previous frame time to accumulator		
		while(accum >= STEP){
			accum -= STEP;
			
			//Update physics world & state manager
			world.step(STEP, 8, 6);
			stateManager.update();
			
			for(Container cmp : menus.getObjectList()){
				cmp.update(this);
			}
			
			//Update entities
			for(Entity ent : entities.getObjectList()){
				ent.update(this);
			}
		}
		
		//Update batch projection incase it has changed
		entityBatch.setProjectionMatrix(worldCamera.combined);
		entityRenderer.setProjectionMatrix(worldCamera.combined);
		menuBatch.setProjectionMatrix(menuCamera.combined);
		menuBatch.setProjectionMatrix(menuCamera.combined);
		entityBatch.enableBlending();
		menuBatch.enableBlending();
		
		//Render entities
		for(Entity ent : entities.getObjectList()){
			ent.render(entityBatch, entityRenderer);
		}
		
		for(Container cmp : menus.getObjectList()){
			cmp.render(menuBatch, menuRenderer);
		}
		
		//Debug b2d rendering
		if(DEBUG){
			b2dRenderer.render(world, worldCamera.combined.cpy().scl(BOX_TO_WORLD));
		}
		
		worldCamera.update();
		menuCamera.update();
		
		
		
	
		
		
	
	}
	
	@Override
	public void resize(int w, int h){
		worldViewport.update(w, h);

		menuCamera.translate(-menuViewport.getWorldWidth()/2, -menuViewport.getWorldHeight()/2); //Move origin to screen coordinates
		menuViewport.update(w, h);
		menuCamera.translate(menuViewport.getWorldWidth()/2, menuViewport.getWorldHeight()/2); //Translate to world origin
		
		setVirtualSize(w/(float)h);
		
		System.out.println(w +", " + h);
	}
	
	private void setVirtualSize(float aspectRatio){
		if(aspectRatio == (float)V_WIDTH_43/V_HEIGHT_43){
			V_WIDTH = V_WIDTH_43;
			V_HEIGHT = V_HEIGHT_43;
		}else if(aspectRatio == (float)V_WIDTH_169/V_HEIGHT_169){
			V_WIDTH = V_WIDTH_169;
			V_HEIGHT = V_HEIGHT_169;			
		}else if(aspectRatio == (float)V_WIDTH_1610/V_HEIGHT_1610){
			V_WIDTH = V_WIDTH_1610;
			V_HEIGHT = V_HEIGHT_1610;				
		}else{
			System.out.println("Unsuported aspect ratio: " + aspectRatio);
		}
	}
	
	private void loadResources(){
		/** Setup Resource parameters **/
		TextureParameter textureParam = new TextureParameter();
		textureParam.minFilter = TextureFilter.Linear;
		textureParam.magFilter = TextureFilter.Linear;
		
		/** HUD Font Parameters */
		FreeTypeFontLoaderParameter defaultFont = new FreeTypeFontLoaderParameter();
		defaultFont.fontFileName = "fonts/times.ttf";
		defaultFont.fontParameters.size = 12;
		
		String hudFontName = "fonts/Think Thick.ttf";
		FreeTypeFontLoaderParameter hud_small = new FreeTypeFontLoaderParameter();
		hud_small.fontFileName = hudFontName;
		hud_small.fontParameters.size = 12;
		FreeTypeFontLoaderParameter hud_medium = new FreeTypeFontLoaderParameter();
		hud_medium.fontFileName =hudFontName;
		hud_medium.fontParameters.size = 14;
		FreeTypeFontLoaderParameter hud_large = new FreeTypeFontLoaderParameter();
		hud_large.fontFileName = hudFontName;
		hud_large.fontParameters.size = 16;
		

		/** Set special loaders for fonts */
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		//Game Textures
		assetManager.load("cannon.png", Texture.class, textureParam);
		assetManager.load("projectiles/cannon_projectile.png", Texture.class, textureParam);		
		assetManager.load("wooden_pedestal.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_wood_1.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_wood_2.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_wood_3.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_stone_1.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_stone_2.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_stone_3.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_jade_1.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_jade_2.png", Texture.class, textureParam);
		assetManager.load("blocks/block_square_jade_3.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_wood_1.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_wood_2.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_wood_3.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_stone_1.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_stone_2.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_stone_3.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_jade_1.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_jade_2.png", Texture.class, textureParam);
		assetManager.load("blocks/block_rectangle_jade_3.png", Texture.class, textureParam);
		assetManager.load("totem_face_flat.png", Texture.class, textureParam);
		assetManager.load("totem_face_shaded.png", Texture.class, textureParam);
		assetManager.load("bg.png", Texture.class, textureParam);
		//UI Textures
		assetManager.load("ui/square_highlight.png", Texture.class, textureParam);	
		assetManager.load("ui/rectangle_highlight.png", Texture.class, textureParam);	
		assetManager.load("ui/square_highlight_hover.png", Texture.class, textureParam);	
		assetManager.load("ui/rectangle_highlight_hover.png", Texture.class, textureParam);		
		assetManager.load("ui/bar.png", Texture.class, textureParam);				
		assetManager.load("ui/bar_hover.png", Texture.class, textureParam);	
		assetManager.load("ui/bar_tall.png", Texture.class, textureParam);			
		assetManager.load("ui/bar_tall_hover.png", Texture.class, textureParam);		
		assetManager.load("ui/shadow.png", Texture.class, textureParam);	
		assetManager.load("ui/arrow.png", Texture.class, textureParam);
		assetManager.load("ui/arrow_hover.png", Texture.class, textureParam);	
		assetManager.load("ui/invalid.png", Texture.class, textureParam);					
		//Fonts
		assetManager.load("default.ttf", BitmapFont.class, defaultFont);
		assetManager.load("hud_small.ttf", BitmapFont.class, hud_small);
		assetManager.load("hud_medium.ttf", BitmapFont.class, hud_medium);
		assetManager.load("hud_large.ttf", BitmapFont.class, hud_large);
		assetManager.load("keyboard.png", Texture.class, textureParam);

		//Backgorund
		assetManager.load("background/bg.png", Texture.class, textureParam);
		assetManager.load("background/castle.png", Texture.class, textureParam);	
		assetManager.load("background/temple.png", Texture.class, textureParam);	
		assetManager.load("background/sun.png", Texture.class, textureParam);	
		assetManager.load("background/cloud_1.png", Texture.class, textureParam);
		assetManager.load("background/cloud_2.png", Texture.class, textureParam);				
	}
	
	/** addEntity registers a spawned entity with the game so it will be rendered and updated.
	 * @return true if the entity is spawned and was added, false otherwise	 */
	public boolean addEntity(Entity ent){		
		return addEntity(ent, 0);
	}
	
	/** addEntity registers a spawned entity at a specified depth with the game so it will be rendered and updated.
	 * @return true if the entity is spawned and was added, false otherwise	 */
	public boolean addEntity(Entity ent, int depth){
		if(!ent.isSpawned()) return false;
		
		DepthWrapper<Entity> wrapper = new DepthWrapper<Entity>();
		wrapper.depth = depth;
		wrapper.object = ent;
		
		return entityAddQueue.add(wrapper);
	}
	
	/** destroyEntity removes a spawned entity from the entities list and also removes it from the box2d world if it has a body 
	 * @return true on success, false on failure*/
	public boolean destroyEntity(Entity ent){
		if(!entityDeleteQueue.contains(ent))
			return entityDeleteQueue.add(ent);
		return false;
	}
	
	/** 
	 * @param name to search for
	 * @return list of entities by given name, or empty list	 */
	public ArrayList<Entity> findEntitiesByName(String name){
		ArrayList<Entity> found = new ArrayList<Entity>();
		
		for(Entity ent : entities.getObjectList()){
			if(ent.getName().equals(name))
				found.add(ent);
		}
		
		return found;
	}
	
	/**
	 * @return all spawned entities in the world
	 */
	public ArrayList<Entity> getAllEntities(){
		return entities.getObjectList();
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
	public OrthographicCamera getEntityCamera(){
		return worldCamera;
	}
	
	public OrthographicCamera getMenuCamera(){
		return menuCamera;
	}
	
	/**
	 * 
	 * @return inputHandler instance
	 */
	public InputHandler getGameInputHandler(){
		return gameInputHandler;
	}
	
	public InputHandler getMenuInputHandler(){
		return menuInputHandler;
	}
	
	public void addMenu(Container menu){
		addMenu(menu, 0);
	}
	
	public void addMenu(Container menu, int depth){
		DepthWrapper<Container> wrapper = new DepthWrapper<Container>();
		wrapper.depth = depth;
		wrapper.object = menu;
		menuAddQueue.add(wrapper);
	}
	
	public void removeMenu(Container menu){
		menuDeleteQueue.add(menu);
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
	
	/** Translates given screen space vector to world space.
	 * Example: Let 100, 100 be the center of the screen. In world space, the origin (0, 0) is the center
	 * 			if you pass 100, 100 you will get 0, 0 back. 
	 * 
	 * @param screen coordinates
	 * @return world coordinates 
	 */
	public Vector2 screenToWorld(Vector2 screen){
		Vector2 offsetScreen = screen.cpy();
		offsetScreen = menuViewport.project(offsetScreen);
		offsetScreen = worldViewport.unproject(offsetScreen);
		offsetScreen.y *= -1;
		return offsetScreen;
	}
	
	/** Translates given world space vector to screen space.
	 * Example: Let 100, 100 be the center of the screen. In world space, the origin (0, 0) is the center
	 * 			if you pass 0, 0 you will get 100, 100 back. 
	 * 
	 * @param screen coordinates
	 * @return world coordinates 
	 */
	public Vector2 worldToScreen(Vector2 world){
		Vector2 offsetWorld = world.cpy();
		offsetWorld.y *= -1;
		offsetWorld = worldViewport.project(offsetWorld);
		offsetWorld = menuViewport.unproject(offsetWorld);
		//offsetWorld.y *= -1;
		return offsetWorld;
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

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public int getDepth(Entity ent) {
		return entities.getDepth(ent);
	}
	
	public int getDepth(Container menu) {
		return menus.getDepth(menu);
	}

	public void setDepth(Entity ent, int newDepth) {
		DepthWrapper<Entity> wrapper = new DepthWrapper<Entity>();
		wrapper.object = ent;
		wrapper.depth = newDepth;
		entityDepthQueue.add(wrapper);
	}
	
	public void setDepth(Container menu, int newDepth) {
		DepthWrapper<Container> wrapper = new DepthWrapper<Container>();
		wrapper.object = menu;
		wrapper.depth = newDepth;
		menuDepthQueue.add(wrapper);
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}


	public void setKeyboardFocus(Component focus) {
		keyboardFocus = focus;
	}
	
	public Component getKeyboardFocus(){ return keyboardFocus; }
	
	public AnimationController getAnimationController(){ return animationController; }
}
