package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.BlockEntity.Shape;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Label;

public class BlockSelector extends Container{
	private Player owner;
	private BlockEntity.Shape shape;
	private Label cost;
	private Texture block;
	private Texture blockHighlight;
	private Texture blockHighlightHover;
	private Texture bar;
	private Texture barHover;
	private Texture shadow;
	/** This is the best way to do this without some sort of XML layout system */
	//Derived these numbers from the illustrator design src file
	float barW = 135;
	float barH = 15;
	float shadowW = 73;
	float shadowH = 18;
	float squareHighlightW = 56f;
	float squareHighlightH = 56f;
	float rectangleHighlightW = 86;
	float rectangleHighlightH = 56;
	
	private ArrowButton arrowRight;
	private ArrowButton arrowLeft;
	private float blockWidth, blockHeight;	
	private boolean hovered = false;
	private BlockEntity mouseSpawned = null;
	private Vector2 mouseLocation = null;
	private MouseEvent mouseMoveListener;
	private float alpha;
	private Label playerBudget;
	
	public BlockSelector(BuildMenu parent, Player owner, BlockEntity.Shape shape){
		super(parent);
		setSize(135, 73);
		this.shape = shape;
		this.owner = owner;
		
		if(shape == Shape.Rectangle){
			blockWidth = TotemDefender.BLOCK_SIZE * BlockEntity.RECTANGLE_XSCALE;
			blockHeight = TotemDefender.BLOCK_SIZE * BlockEntity.RECTANGLE_YSCALE;
		}else{
			blockWidth = TotemDefender.BLOCK_SIZE * BlockEntity.SQUARE_XSCALE;
			blockHeight = TotemDefender.BLOCK_SIZE * BlockEntity.SQUARE_YSCALE;
		}
		
		alpha = 1;
	}
	
	@Override
	public void create(TotemDefender game){
		String shapeString 	= shape.toString().toLowerCase();
		blockHighlight 		= game.getAssetManager().get("ui/"+ shapeString + "_highlight.png", Texture.class);
		blockHighlightHover = game.getAssetManager().get("ui/"+ shapeString + "_highlight_hover.png", Texture.class);
		bar 				= game.getAssetManager().get("ui/bar.png", Texture.class);
		barHover			= game.getAssetManager().get("ui/bar_hover.png", Texture.class);
		shadow				= game.getAssetManager().get("ui/shadow.png", Texture.class);
		
		
		//Cost label
		cost = new Label(this);
		cost.setFont("hud_medium.ttf");
		if(shape==Shape.Square){
			cost.setText("$100", true);
		}
		else{
			cost.setText("$200",true);
		}
		cost.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
		cost.setPosition(getWidth()/2 - cost.getWidth()/2, 20 - barH/2);
		cost.create(game);
		
		float yPos = getHeight() - blockHeight/2;
		//Arrow button left
		arrowLeft = new ArrowButton(this);
		arrowLeft.setPosition(0, yPos - arrowLeft.getHeight()/2);//getHeight()-TotemDefender.BLOCK_SIZE - arrowLeft.getHeight()/2);
		arrowLeft.create(game);
		
		//Arrow button right
		arrowRight = new ArrowButton(this);
		arrowRight.setPosition(getWidth() - arrowRight.getWidth(), yPos - arrowRight.getHeight()/2);//getHeight()-TotemDefender.BLOCK_SIZE/2 - arrowLeft.getHeight()/2);
		arrowRight.flip();
		arrowRight.create(game);
		
		//We need to add out own handler because we need to detect the mouse movement even when the mouse moves outside of the container.
		final TotemDefender gameRef = game;
		 mouseMoveListener = game.getMenuInputHandler().addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){
				mouseLocation = gameRef.screenToWorld(mousePosition);
				return false;
			}
		});
		
		super.create(game);
	}
	
	@Override
	public void destroy(TotemDefender game){
		game.getMenuInputHandler().removeListener(mouseMoveListener);
		arrowLeft.destroy(game);
		arrowRight.destroy(game);
		super.destroy(game);
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		float centerX = getWidth()/2;
		float x = getPosition().x;
		float y = getPosition().y;
		
		Texture blockHighlightActive, barActive;
		if(!hovered){
			blockHighlightActive = blockHighlight;	
			barActive = bar;
		}else{
			blockHighlightActive = blockHighlightHover;	
			barActive = barHover;
		}
		
		TotemDefender.EnableBlend();		
		batch.begin();
		batch.setColor(1, 1, 1, alpha);
		batch.draw(blockHighlightActive, x + centerX - blockHighlightActive.getWidth()/2, y + getHeight() - blockHighlightActive.getHeight()/2 - 15, blockHighlightActive.getWidth() - 2, blockHighlightActive.getHeight() - 1);
		batch.draw(block, x + centerX - blockWidth/2, y + getHeight() - blockHeight, blockWidth, blockHeight);
		batch.draw(barActive, x + centerX - barW/2, y + 15 - barH/2, barW, barH);
		batch.draw(shadow, x + centerX - shadowW/2, y, shadowW, shadowH);
		
		if(this.owner.getID()==1){
			playerBudget=new Label(this);
			playerBudget.setFont("hud_medium.ttf");
			playerBudget.getFont().draw(batch, "Player "+this.owner.getID()+": "+this.owner.getBudget(), -150+getWidth(), 873-getHeight());
		}
		
		if(this.owner.getID()==2){
			playerBudget=new Label(this);
			playerBudget.setFont("hud_medium.ttf");
			playerBudget.getFont().draw(batch, "Player "+this.owner.getID()+": "+this.owner.getBudget(), 200-getWidth(), 873-getHeight());
		}
		
		batch.end();
		TotemDefender.DisableBlend();
		
		super.render(batch, shapeRenderer);
	}
	
	public void setBlockMaterial(TotemDefender game, BlockEntity.Material material){
		block = game.getAssetManager().get(BlockEntity.GetRandomAsset(material, this.shape), Texture.class);
	}
	
	@Override
	public boolean onMouseMove(MouseEvent event){
		return super.onMouseMove(event);
	}
	
	@Override
	public void onGainFocus(){
		hovered = true;
		super.onGainFocus();
	}
	
	@Override
	public void onLoseFocus(){
		hovered = false;
		super.onLoseFocus();
	}
	
	@Override
	public void onMouseEnter(MouseEvent event){
		hovered = true;
		super.onMouseEnter(event);
	}
	
	@Override
	public void onMouseExit(MouseEvent event){
		if(!hasFocus())
			hovered = false;
		super.onMouseExit(event);
	}
	
	/*
	@Override
	public boolean onMouseDown(MouseEvent event){
		if(!arrowLeft.isMouseOver() && !arrowRight.isMouseOver() && !getParent().isKeyboardMode()){ 
			mouseSpawned = spawnBlock(TotemDefender.Get());
			getParent().setPlacementMode(BuildMenu.PlacementMode.Mouse);
		}
		return super.onMouseDown(event);
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		super.onMouseUp(event);
		if(getParent().isMouseMode()){
			getParent().destroySpawnedBlock(TotemDefender.Get());
			mouseSpawned = null;
		}
		return false;
	}*/
	
	@Override
	public boolean onKeyboardSelect(){
		if(getParent().getGrid().hasEntity()) return false;
		
		getParent().setSpawnedBlock(spawnBlock(TotemDefender.Get()));
		return true;
	}
	
	@Override
	public boolean onClick(){
		if(getParent().getGrid().hasEntity()) return false;
		
		if(arrowLeft.isMouseOver()){
			return arrowLeft.onClick();
		}else if(arrowRight.isMouseOver()){
			return arrowRight.onClick();
		}
		

		getParent().setSpawnedBlock(spawnBlock(TotemDefender.Get()));		
		return true;
	}
	
	public BlockEntity spawnBlock(TotemDefender game){
		if(getParent().getGrid().hasEntity()) return null;
		
		BlockEntity blockEntity = null;
		if(shape == Shape.Square){
			blockEntity = new SquareBlockEntity(owner);
		}else if(shape == Shape.Rectangle){
			blockEntity = new RectangleBlockEntity(owner);
		}

		if(blockEntity.getCost()<=blockEntity.getOwner().getBudget()){
			if(blockEntity != null){
				blockEntity.spawn(game);
				game.addEntity(blockEntity);
				blockEntity.getBody().setActive(false);
			}
			
			getParent().setSpawnedBlock(blockEntity);
			blockEntity.getOwner().setBudget(blockEntity.getOwner().getBudget()-blockEntity.getCost());
			return blockEntity;
		}else{
			return null;
		}
		
	}
	
	public boolean isMouseMode(){
		return mouseSpawned != null;
	}
	
	@Override
	public BuildMenu getParent(){
		return (BuildMenu)super.getParent();
	}

	public Vector2 getMouseLocation() {
		return mouseLocation;
	}	
	
	public void reset(){
		mouseSpawned = null;
	}
	
	public void setAlpha(float alpha){
		this.alpha = alpha;
		cost.getTextColor().a = alpha;
	}
}
