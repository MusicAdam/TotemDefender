package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
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
	
	public Grid(BuildMenu parent){
		super(parent);
		index = new Vector2(WIDTH/2, HEIGHT/2);
		setSize(WIDTH * TotemDefender.BLOCK_SIZE, HEIGHT * TotemDefender.BLOCK_SIZE);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		super.render(batch, shapeRenderer);
		if(!hasEntity()) return; 
		
		TotemDefender.EnableBlend();
		shapeRenderer.begin(ShapeType.Line);
		for(int x = 0; x < WIDTH; x++){
			for(int y = 0; y < HEIGHT; y++){		
				shapeRenderer.setColor(.8f, .3f, .3f, 1);
				//Render the grid
				if(x == 0 || y == 0){
					if(x == WIDTH - 1 || y == HEIGHT -1) continue;
					float xPos = getPosition().x + (x+1) * TotemDefender.BLOCK_SIZE;
					float yPos = getPosition().y + (y+1) * TotemDefender.BLOCK_SIZE;
					shapeRenderer.line(xPos, yPos, xPos - TotemDefender.BLOCK_SIZE, yPos);
					shapeRenderer.line(xPos, yPos, xPos, yPos - TotemDefender.BLOCK_SIZE);
				}else{
					float xPos = getPosition().x + x * TotemDefender.BLOCK_SIZE;
					float yPos = getPosition().y + y * TotemDefender.BLOCK_SIZE;
					shapeRenderer.line(xPos, yPos, xPos + TotemDefender.BLOCK_SIZE, yPos);
					shapeRenderer.line(xPos, yPos, xPos, yPos + TotemDefender.BLOCK_SIZE);
				}
			}
		}
		shapeRenderer.end();
		TotemDefender.DisableBlend();
		
	}
	
	public float getWidth(){
		return WIDTH * TotemDefender.BLOCK_SIZE;
	}
	
	public float getHeight(){
		return HEIGHT * TotemDefender.BLOCK_SIZE;
	}
	
	public void setEntity(BlockEntity entity){
		index = new Vector2(WIDTH/2, HEIGHT/2);
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
		
		if(getParent().isKeyboardMode()){
			snapToIndex();
		}else if(getParent().isMouseMode()){
			index = getIndexFromPosition(mousePosition);
			snapToIndex();
		}
	}
	
	public void snapToIndex(){
		TotemDefender game = TotemDefender.Get();

		Vector2 indexWorldPos = game.screenToWorld(getIndexPosition());//game.screenToWorld(getIndexPosition()); //.add(getWorldPosition())
		indexWorldPos.add(entity.getWidth()/2, entity.getHeight()/2);
		entity.setPosition(indexWorldPos);
	}
	
	public Vector2 getIndexPosition(){
		Vector2 pos = getWorldPosition();
		return new Vector2(	pos.x + index.x * TotemDefender.BLOCK_SIZE,
							pos.y + index.y * TotemDefender.BLOCK_SIZE);
	}
	
	public Vector2 getIndexFromPosition(Vector2 position){
		Vector2 pos = getWorldPosition();
		return new Vector2(	(float)Math.floor((position.x - pos.x) / TotemDefender.BLOCK_SIZE),
							(float)Math.floor((position.y - pos.y) / TotemDefender.BLOCK_SIZE));
	}
	
	@Override
	public boolean onMouseMove(MouseEvent event){
		if(getParent().getSpawnedBlock() == null || !getParent().isMouseMode()) return false;
		mousePosition = event.mousePosition;

		if(pointIsInBounds(getParent().worldToLocal(event.mousePosition))){
			if(getParent().isMouseMode()){
				if(getEntity() == null){
					setEntity(getParent().getSpawnedBlock());
				}
				snapEntityToGrid();
			}
		}
		
		return super.onMouseMove(event);
	}
	
	@Override
	public void onMouseEnter(MouseEvent event){
		if(getParent().isMouseMode()){
			if(getEntity() == null){
				setEntity(getParent().getSpawnedBlock());
			}			
		}
		super.onMouseEnter(event);
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		if(getParent().isMouseMode() && hasEntity()){
			getParent().addPlacedBlock(getEntity());
			setEntity(null);
		}
		return false;
	}
	
	@Override
	public void onMouseExit(MouseEvent event){
		if(getParent().isMouseMode()){
			setEntity(null);
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
}