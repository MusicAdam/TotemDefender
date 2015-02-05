package com.totemdefender.menu.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.menu.Component;

public class Grid extends Component {
	public static final int WIDTH = 6;
	public static final int HEIGHT = 10; //The build area.
	
	private BlockEntity entity; 	//The entity being positioned.
	private Vector2 index; 			//The current position in the grid.
	
	public Grid(){
		this.position = new Vector2();
		index = new Vector2(WIDTH/2, HEIGHT/2);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		if(!hasEntity()) return; 
		
		TotemDefender.EnableBlend();
		shapeRenderer.begin(ShapeType.Line);
		for(int x = 0; x < WIDTH; x++){
			for(int y = 0; y < HEIGHT; y++){		
				shapeRenderer.setColor(.8f, .3f, .3f, 1);
				//Render the grid
				if(x == 0 || y == 0){
					if(x == WIDTH - 1 || y == HEIGHT -1) continue;
					float xPos = position.x + (x+1) * TotemDefender.BLOCK_SIZE;
					float yPos = position.y + (y+1) * TotemDefender.BLOCK_SIZE;
					shapeRenderer.line(xPos, yPos, xPos - TotemDefender.BLOCK_SIZE, yPos);
					shapeRenderer.line(xPos, yPos, xPos, yPos - TotemDefender.BLOCK_SIZE);
				}else{
					float xPos = position.x + x * TotemDefender.BLOCK_SIZE;
					float yPos = position.y + y * TotemDefender.BLOCK_SIZE;
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
		
		TotemDefender game = TotemDefender.Get();
		Vector2 screenOffset = new Vector2(-game.getScreenWidth()/2, -game.getScreenHeight()/2);
		Vector2 screenCoordinates = new Vector2(position.x + (index.x * TotemDefender.BLOCK_SIZE), position.y + (index.y * TotemDefender.BLOCK_SIZE));
		screenCoordinates.sub(-entity.getWidth()/2, -entity.getHeight()/2);
		screenCoordinates.add(screenOffset);
		entity.setPosition(screenCoordinates);	
	}
	
	public Vector2 getIndexPosition(){
		return new Vector2(	position.x + index.x * TotemDefender.BLOCK_SIZE,
							position.y + index.y * TotemDefender.BLOCK_SIZE);
	}

}
