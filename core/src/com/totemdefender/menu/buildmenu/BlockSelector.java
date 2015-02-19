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
	private ArrowButton arrowRight;
	private ArrowButton arrowLeft;
	private float blockWidth, blockHeight;	
	private boolean hovered = false;
	private BlockEntity mouseSpawned = null;
	private Vector2 mouseLocation = null;
	private MouseEvent mouseMoveListener;
	
	public BlockSelector(Container parent, Player owner, BlockEntity.Shape shape){
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
		cost.setFont("hud_small.ttf");
		cost.setText("$100", true);
		cost.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
		cost.setPosition(getWidth()/2 - cost.getWidth()/2, 20 - bar.getHeight()/2);
		addComponent(cost);
		
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
		
		final TotemDefender gameRef = game;
		/*mouseMoveListener = game.getMenuInputHandler().addListener(new MouseEvent(MouseEvent.MOUSE_MOVE){
			@Override
			public boolean callback(){
				mouseLocation = gameRef.screenToWorld(mousePosition);
				System.out.println("MouseLocation: " + mouseLocation);
				return false;
			}
		});*/
		
		super.create(game);
	}
	
	@Override
	public void destroy(TotemDefender game){
		game.getMenuInputHandler().removeListener(mouseMoveListener);
		arrowLeft.destroy(game);
		arrowRight.destroy(game);
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);

		if(mouseSpawned != null){
			mouseSpawned.setPosition(mouseLocation.x, mouseLocation.y);
		}
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		float centerX = getWidth()/2;
		float x = getPosition().x;
		float y = getPosition().y;
		
		Texture blockHighlightActive, barActive, shadowActive;
		if(!hovered){
			blockHighlightActive = blockHighlightHover;
			barActive = bar;
		}else{
			blockHighlightActive = blockHighlight;		
			barActive = barHover;
		}
		
		TotemDefender.EnableBlend();
		
		Rectangle rect = ScissorStack.popScissors(); //Pop the parent scissors as we don't want to clip the blockHighlight
		batch.begin();
		batch.draw(blockHighlightActive, x + centerX - blockHighlight.getWidth()/2, y + getHeight() - blockHighlight.getHeight()/2 - 15, blockHighlight.getWidth()-1, blockHighlight.getHeight()-1);
		batch.end();
		ScissorStack.pushScissors(rect); //Return to previous state
		
		batch.begin();
		batch.draw(block, x + centerX - blockWidth/2, y + getHeight() - blockHeight, blockWidth, blockHeight);
		batch.draw(barActive, x + centerX - bar.getWidth()/2, y + 15 - bar.getHeight()/2, bar.getWidth(), bar.getHeight());
		batch.draw(shadow, x + centerX - shadow.getWidth()/2, y, shadow.getWidth(), shadow.getHeight());
		batch.end();
		TotemDefender.DisableBlend();
		
		super.render(batch, shapeRenderer);
	}
	
	public void setBlockMaterial(TotemDefender game, BlockEntity.Material material){
		block = game.getAssetManager().get(BlockEntity.GetRandomAsset(material, this.shape), Texture.class);
	}
	
	@Override
	public void onGainFocus(){
		hovered = true;
	}
	
	@Override
	public void onLoseFocus(){
		hovered = false;
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
	
	@Override
	public boolean onMouseDown(MouseEvent event){
		mouseSpawned = spawnBlock(TotemDefender.Get());
		return super.onMouseDown(event);
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		mouseSpawned = null;
		return super.onMouseUp(event);
	}
	
	@Override
	public boolean onClick(){
		if(mouseSpawned != null) return false;
		//TODO: Since the arrow buttons are children of this container, events should get passed to them,
		//		however that is not happening, so this is a temporary fix.
		if(arrowLeft.isMouseOver()){
			return arrowLeft.onClick();
		}else if(arrowRight.isMouseOver()){
			return arrowRight.onClick();
		}
		spawnBlock(TotemDefender.Get());
		return true;
	}
	
	public BlockEntity spawnBlock(TotemDefender game){
		BlockEntity blockEntity = null;
		if(shape == Shape.Square){
			blockEntity = new SquareBlockEntity(owner);
		}else if(shape == Shape.Rectangle){
			blockEntity = new RectangleBlockEntity(owner);
		}
		
		if(blockEntity != null){
			blockEntity.spawn(game);
			game.addEntity(blockEntity);
			blockEntity.getBody().setActive(false);
		}
		
		return blockEntity;
	}
}
