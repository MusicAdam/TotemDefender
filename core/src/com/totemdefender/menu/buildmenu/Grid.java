package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Panel;

public class Grid extends Panel {
	public static final int WIDTH = 6;
	public static final int HEIGHT = 10; //The build area.
	
	private BlockEntity entity; 	//The entity being positioned.
	private Vector2 index; 			//The current position in the grid.
	private Vector2 mousePosition;
	private boolean shouldDelete = false;
	private boolean mouseDown = false;
	private boolean doRenderInvalid = false;
	private boolean hideGrid = false;
	private Texture invalid;
	
	public Grid(BuildMenu parent){
		super(parent);
		index = new Vector2(WIDTH/2, HEIGHT/2);
		setSize(WIDTH * (TotemDefender.BLOCK_SIZE + 1), HEIGHT * (TotemDefender.BLOCK_SIZE + 1));
	}
	
	@Override
	public void create(TotemDefender game){
		invalid = game.getAssetManager().get("ui/invalid.png", Texture.class);
		super.create(game);
	}
	
	@Override
	public void update(TotemDefender game){
		if(!placementValid() && hasEntity()){
			doRenderInvalid = true;
		}else{
			doRenderInvalid = false;
		}
		
		super.update(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		super.render(batch, shapeRenderer);
		
		TotemDefender.EnableBlend();
		if(!shouldHideGrid()){
			shapeRenderer.begin(ShapeType.Line);
			for(int x = 0; x < WIDTH; x++){
				for(int y = 0; y < HEIGHT; y++){		
					shapeRenderer.setColor(.8f, .3f, .3f, 1);
					//Render the grid
					if(x == 0 || y == 0){
						if(x == WIDTH - 1 || y == HEIGHT -1) continue;
						float xPos = getPosition().x + (x+1) * (TotemDefender.BLOCK_SIZE + 1);
						float yPos = getPosition().y + (y+1) * (TotemDefender.BLOCK_SIZE + 1);
						shapeRenderer.line(xPos, yPos, xPos - (TotemDefender.BLOCK_SIZE + 1), yPos);
						shapeRenderer.line(xPos, yPos, xPos, yPos - (TotemDefender.BLOCK_SIZE + 1));
					}else{
						float xPos = getPosition().x + x * (TotemDefender.BLOCK_SIZE + 1);
						float yPos = getPosition().y + y * (TotemDefender.BLOCK_SIZE + 1);
						shapeRenderer.line(xPos, yPos, xPos + (TotemDefender.BLOCK_SIZE + 1), yPos);
						shapeRenderer.line(xPos, yPos, xPos, yPos + (TotemDefender.BLOCK_SIZE + 1));
					}
				}
			}
		}
		shapeRenderer.end();
		if(doRenderInvalid){
			Vector2 pos = getParent().worldToLocal(getIndexPosition());
			float w = (TotemDefender.BLOCK_SIZE + 1);
			float h = (TotemDefender.BLOCK_SIZE + 1);
			float xOffset = (getEntity().getXScale() - 1) * w/2;
			batch.begin();
			batch.draw(invalid, pos.x + xOffset, pos.y, w, h);
			batch.end();
		}
		TotemDefender.DisableBlend();
		
	}
	
	public float getWidth(){
		return WIDTH * (TotemDefender.BLOCK_SIZE + 1);
	}
	
	public float getHeight(){
		return HEIGHT * (TotemDefender.BLOCK_SIZE + 1);
	}
	
	public void setEntity(BlockEntity entity){
		this.entity = entity;
		snapEntityToGrid();
	}
	
	public BlockEntity getEntity(){
		return entity;
	}
	
	public boolean hasEntity(){
		return !(entity == null);
	}
	
	public void shiftIndexLeft(){
		if(!hasEntity()) return; 
		
		if(index.x != 0){
			index.x -= 1;
			snapEntityToGrid();
		}
	}
	
	public void shiftIndexRight(){
		if(!hasEntity()) return; 
		
		if(index.x != WIDTH - 1){
			index.x += 1;
			snapEntityToGrid();
		}
	}
	
	public void shiftIndexUp(){
		if(!hasEntity()) return; 
		
		if(index.y != HEIGHT - 1){
			index.y += 1;
			snapEntityToGrid();
		}
	}
	
	public void shiftIndexDown(){
		if(!hasEntity()) return; 
		
		if(index.y != 0){
			index.y -= 1;
			snapEntityToGrid();
		}
	}
	
	public void rotateEntity(){
		if(!hasEntity()) return; 
		
		entity.rotate();
		snapEntityToGrid();
	}
	
	public void snapEntityToGrid(){
		if(!hasEntity()) return; 
		snapToIndex();
	}
	
	public void snapToIndex(){
		TotemDefender game = TotemDefender.Get();

		Vector2 indexWorldPos = game.screenToWorld(getIndexPosition());//game.screenToWorld(getIndexPosition()); //.add(getWorldPosition())
		indexWorldPos.add(entity.getWidth()/2, entity.getHeight()/2);
		entity.setPosition(indexWorldPos);
	}
	
	public Vector2 getIndexPosition(){
		return getIndexPosition(index);
	}
	
	public Vector2 getIndexPosition(Vector2 index){
		Vector2 pos = getWorldPosition();
		return new Vector2(	pos.x + index.x * (TotemDefender.BLOCK_SIZE + 1),
							pos.y + index.y * (TotemDefender.BLOCK_SIZE + 1));
	}
	
	public Vector2 getIndexFromPosition(Vector2 position){
		Vector2 pos = getWorldPosition();
		return new Vector2(	(float)Math.floor((position.x - pos.x) / (TotemDefender.BLOCK_SIZE + 1)),
							(float)Math.floor((position.y - pos.y) / (TotemDefender.BLOCK_SIZE + 1)));
	}
	
	@Override
	public boolean onMouseMove(MouseEvent event){
		if(!hasEntity()) return false;
		mousePosition = event.mousePosition;

		if(pointIsInBounds(getParent().worldToLocal(event.mousePosition)) && mouseDown){
			index = getIndexFromPosition(mousePosition);
			snapEntityToGrid();
		}
		
		return super.onMouseMove(event);
	}
	
	@Override
	public void onMouseEnter(MouseEvent event){
		shouldDelete = false;
		super.onMouseEnter(event);
	}
	
	@Override
	public boolean onMouseDown(MouseEvent event){
		mouseDown = true;
		Vector2 mouseWorldPosition = TotemDefender.Get().screenToWorld(event.mousePosition);
		if(hasEntity()){
			if(!getEntity().getSprite().getBoundingRectangle().contains(mouseWorldPosition)){
				selectBlockAtPosition(event.mousePosition);				
			}
		}else{
			selectBlockAtPosition(event.mousePosition);
		}
		return super.onMouseDown(event);
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		mouseDown = false;
		if(hasEntity()){
			if(shouldDelete){
				if(!(getEntity() instanceof TotemEntity)){
					getParent().destroyBlock(TotemDefender.Get(), getEntity());
					setEntity(null);
				}
				shouldDelete = false;
			}else{
				placeBlock();
			}
		}
		return false;
	}
	
	@Override
	public void onMouseExit(MouseEvent event){
		if(hasEntity() && mouseDown){
			shouldDelete = true;
		}else{
			shouldDelete = false;
		}
		super.onMouseExit(event);
	}
	
	@Override
	public BuildMenu getParent(){
		return (BuildMenu)super.getParent();
	}
	
	public void reset(){
		setEntity(null);
	}
	
	public void selectBlockAtPosition(Vector2 position){
		BlockEntity block = getBlockAtPosition(position);
		if(block != null){
			placeBlock();
			index = getIndexFromPosition(position);
			setEntity(block);					
		}
	}
	
	public BlockEntity getBlockAtPosition(Vector2 position){
		BlockEntity found = null;
		position = TotemDefender.Get().screenToWorld(position);
		for(BlockEntity block : getParent().getLevel().getPlacedBlocks()){
			if(	block.getSprite().getBoundingRectangle().contains(position)){
				found = block;
				break;
			}
		}
		return found;
	}
	
	public void placeBlock(){
		if(!hasEntity() || !placementValid()) return;
		getParent().addPlacedBlock(getEntity());
		setEntity(null);		
	}
	
	public boolean placementValid(){
		if(!hasEntity()) return false;

		for(BlockEntity block : getParent().getLevel().getPlacedBlocks()){
			if(block == getEntity()) continue;
			if(getEntity().getSprite().getBoundingRectangle().overlaps(block.getSprite().getBoundingRectangle())){
				return false;
			}
		}		
		return true;
	}
	
	public void centerIndex(){
		index = new Vector2(WIDTH/2, HEIGHT/2);
	}

	public boolean shouldHideGrid() {
		return hideGrid;
	}

	public void setHideGrid(boolean hideGrid) {
		this.hideGrid = hideGrid;
	}
}
