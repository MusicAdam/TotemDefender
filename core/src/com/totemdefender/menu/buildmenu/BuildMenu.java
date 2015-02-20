package com.totemdefender.menu.buildmenu;
import java.util.ArrayList;

import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.PedestalEntity;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.NavigableContainer;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu extends NavigableContainer {
	private Player owner;
	private Level level;
	
	private BlockSelector squareSelector;
	private BlockSelector rectangleSelector;
	private BlockEntity spawnedBlock;
	private Grid grid;
	private Vector2 buttonPosition;
	private boolean placingTotem = false;
	
	public BuildMenu(TotemDefender game, Level level, Player owner) {
		super(null);
		
		this.owner = owner;
		this.level = level;
		buttonPosition = new Vector2();
		
		grid = new Grid(this);
		grid.create(game);
		
		squareSelector = new BlockSelector(this, owner, BlockEntity.Shape.Square);
		squareSelector.setBlockMaterial(game, BlockEntity.Material.Stone);
		squareSelector.create(game);
		
		
		rectangleSelector = new BlockSelector(this, owner, BlockEntity.Shape.Rectangle);
		rectangleSelector.setBlockMaterial(game, BlockEntity.Material.Stone);
		rectangleSelector.create(game);
		connectComponents(squareSelector, rectangleSelector);
		
		attachKeyboardListeners(owner);
	};
	
	@Override
	public void validate(){
		if(!isValid()){
			squareSelector.setPosition(buttonPosition);
			rectangleSelector.setPosition(buttonPosition.x, buttonPosition.y - squareSelector.getHeight());
		}
		super.validate();
	}
	
	@Override
	public void update(TotemDefender game){
		if(isMouseMode() && getSpawnedBlock() != null && !grid.hasEntity()){
			getSpawnedBlock().setPosition(squareSelector.getMouseLocation().x, squareSelector.getMouseLocation().y);
		}
		super.update(game);
	}
	
	@Override
	public boolean onMouseUp(MouseEvent event){
		if(isMouseMode() && getSpawnedBlock() != null && !grid.hasEntity()){
			destroySpawnedBlock(TotemDefender.Get());
		}
		
		return super.onMouseUp(event);
	}
	
	public boolean isMouseMode(){
		return squareSelector.isMouseMode() || rectangleSelector.isMouseMode();
	}
	
	public BlockEntity getSpawnedBlock(){
		return spawnedBlock;
	}

	public void setSpawnedBlock(BlockEntity blockEntity) {
		spawnedBlock = blockEntity;
	}
	
	public void addPlacedBlock(BlockEntity block){
		level.addPlacedBlock(block);
		
		if(block == getSpawnedBlock()){
			setSpawnedBlock(null);
		}
	}
	
	public BlockSelector getSquareBlockSelector(){
		return squareSelector;
	}
	
	public Grid getGrid(){
		return grid;
	}

	public Vector2 getButtonPosition() {
		return buttonPosition;
	}
	
	public void destroySpawnedBlock(TotemDefender game){
		if(getSpawnedBlock() == null) return;
		game.destroyEntity(getSpawnedBlock());
		setSpawnedBlock(null);
	}

	public void setButtonPosition(Vector2 buttonPosition) {
		this.buttonPosition = buttonPosition;
		invalidate();
	}
	
	public void setButtonPosition(float x, float y){
		setButtonPosition(new Vector2(x, y));
	}
}