package com.totemdefender.menu.buildmenu;
import java.util.ArrayList;

import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.NavigableContainer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu extends NavigableContainer {	
	private Player owner;
	private Level level;
	
	private BlockSelector squareSelector;
	private BlockSelector rectangleSelector;
	private BlockEntity spawnedBlock;
	private Grid grid;
	private ReadyButton readyButton;
	private Vector2 buttonPosition;
	private KeyboardEvent rotateKeyUpListener;
	private float buttonPadding = 20;
	private float fade;
	private boolean isDone = false;
	private Label playerBudget;
	
	public BuildMenu(TotemDefender game, Level level, Player owner) {
		super(null);
		
		this.owner = owner;
		this.level = level;
		fade = 1;
		buttonPosition = new Vector2();
		
		grid = new Grid(this);
		grid.create(game);
		
		squareSelector = new BlockSelector(this, owner, BlockEntity.Shape.Square);
		squareSelector.setBlockMaterial(game, BlockEntity.Material.Stone);
		squareSelector.create(game);
		
		
		rectangleSelector = new BlockSelector(this, owner, BlockEntity.Shape.Rectangle);
		rectangleSelector.setBlockMaterial(game, BlockEntity.Material.Stone);
		rectangleSelector.create(game);
		
		readyButton = new ReadyButton(this, owner);
		readyButton.create(game);

		connectComponents(squareSelector, rectangleSelector);
		connectComponents(rectangleSelector, readyButton);
		connectComponents(readyButton, squareSelector);
		
		attachKeyboardListeners(owner);
		
		
		
	
		
	
		
		playerBudget=new Label(this);
		playerBudget.setFont("hud_medium.ttf");
		playerBudget.setText("Player "+this.owner.getID()+": "+this.owner.getBudget());
	
		playerBudget.setPosition(0,775);
		playerBudget.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
		
		
		playerBudget.create(game);
	
	
	};
	
	@Override
	public void destroy(TotemDefender game){
		game.getMenuInputHandler().removeListener(rotateKeyUpListener);
		super.destroy(game);
	}
	
	@Override
	public void validate(){
		if(!isValid()){
			squareSelector.setPosition(buttonPosition);
			rectangleSelector.setPosition(buttonPosition.x, buttonPosition.y - squareSelector.getHeight() - buttonPadding);
			readyButton.setPosition(buttonPosition.x + 5, buttonPosition.y - rectangleSelector.getHeight() - readyButton.getHeight() - buttonPadding - 20);
		}
		super.validate();
	}
	
	@Override
	public void update(TotemDefender game){		
		if(readyButton.isTotemSpawned() && fade > 0){
			fade -= .01f;
			if(fade < 0)
				fade = 0;
			squareSelector.setAlpha(fade);
			rectangleSelector.setAlpha(fade);
			readyButton.setAlpha(fade);
		}else if(fade == 0){
			squareSelector.destroy(game);
			rectangleSelector.destroy(game);
			readyButton.destroy(game);
			fade = -1; //So this condition stops firing
		}
		
		
		playerBudget.setText("Player "+this.owner.getID()+": "+this.owner.getBudget());
		
		super.update(game);
	}
	
	public BlockEntity getSpawnedBlock(){
		return spawnedBlock;
	}

	public void setSpawnedBlock(BlockEntity blockEntity) {
		spawnedBlock = blockEntity;
		
		if(blockEntity == null){
			grid.reset();
			squareSelector.reset();
			rectangleSelector.reset();
		}else{
			grid.centerIndex();
			grid.setEntity(spawnedBlock);
			setFocus(grid);
		}
	}
	
	public void addPlacedBlock(BlockEntity block){
		if(block == null) return;
		
		if(block instanceof TotemEntity){
			level.addTotem((TotemEntity)block);
			getGrid().setHideGrid(true);
			isDone = true;
		}else{
			level.addPlacedBlock(block);
		}
		
		setSpawnedBlock(null);	
	}
	
	public void removePlacedBlock(BlockEntity block){
		if(block instanceof TotemEntity) return;
		level.removePlacedBlock(block);
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
	
	public void destroyBlock(TotemDefender game, BlockEntity block){
		if(block instanceof TotemEntity) return;
		block.getOwner().setBudget(block.getOwner().getBudget() + block.getCost()); //Reimburse player if they didn't use the block
		
		if(block == getSpawnedBlock())
			setSpawnedBlock(null);
		level.removePlacedBlock(block);
		game.destroyEntity(block);
	}

	public void setButtonPosition(Vector2 buttonPosition) {
		this.buttonPosition = buttonPosition;
		invalidate();
	}
	
	public void setButtonPosition(float x, float y){
		setButtonPosition(new Vector2(x, y));
	}
	
	@Override
	public boolean onSelectKeyUp(){
		if(grid.hasEntity()){
			addPlacedBlock(getSpawnedBlock());
			setSpawnedBlock(null);
		}else{
			return super.onSelectKeyUp();
		}
		return true;
	}
	
	@Override
	public void onTraverseDown(){
		if(grid.hasEntity()){
			grid.shiftIndexDown();
			resetTraversalTime();
		}else{
			super.onTraverseDown();
		}
	}
	
	@Override
	public void onTraverseUp(){
		if(grid.hasEntity()){
			grid.shiftIndexUp();
			resetTraversalTime();
		}else{
			super.onTraverseUp();
		}
	}
	
	@Override
	public void onTraverseLeft(){
		if(grid.hasEntity()){
			grid.shiftIndexLeft();
			resetTraversalTime();
		}else{
			super.onTraverseLeft();
		}
	}
	
	@Override
	public void onTraverseRight(){
		if(grid.hasEntity()){
			grid.shiftIndexRight();
			resetTraversalTime();
		}else{
			super.onTraverseRight();
		}
	}
	
	public boolean doRotate(){
		if(grid.hasEntity())
			grid.getEntity().rotate();
			grid.snapEntityToGrid();
		return true;
	}
	
	@Override
	public void attachKeyboardListeners(Player player){
		InputHandler inputHandler = TotemDefender.Get().getMenuInputHandler();
		rotateKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getRotateKey()){
			@Override
			public boolean callback(){
				return doRotate();
			}
		});
		super.attachKeyboardListeners(player);
	}
	
	public boolean isDone(){
		return isDone;
	}
	
	public Level getLevel(){ return level; }
}