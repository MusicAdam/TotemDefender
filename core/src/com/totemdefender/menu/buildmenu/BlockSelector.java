package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Label;

public class BlockSelector extends Container{
	private BlockEntity.Shape shape;
	private Label cost;
	private Texture block;
	private Texture blockHighlight;
	private Texture blockHighlightHover;
	private Texture bar;
	private Texture shadow;
	private Texture arrowRight;
	private Texture arrowLeft;
	
	public BlockSelector(Container parent, BlockEntity.Shape shape){
		super(parent);
		setSize(135, 73);
		this.shape = shape;
	}
	
	@Override
	public void create(TotemDefender game){
		String shapeString 	= shape.toString().toLowerCase();
		blockHighlight 		= game.getAssetManager().get("ui/"+ shapeString + "_highlight.png", Texture.class);
		blockHighlightHover = game.getAssetManager().get("ui/"+ shapeString + "_highlight_hover.png", Texture.class);
		bar 				= game.getAssetManager().get("ui/bar.png", Texture.class);
		shadow				= game.getAssetManager().get("ui/shadow.png", Texture.class);
		//TODO: These should be buttons
		//arrowRight			= game.getAssetManager().get("ui/arrow_right.png", Texture.class);
		//arrowLeft			= game.getAssetManager().get("ui/arrow_left.png", Texture.class);
		cost = new Label(this);
		cost.setFont("hud_small.ttf");
		cost.setText("$100", true);
		cost.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
		cost.setPosition(getWidth()/2 - cost.getWidth()/2, 20 - bar.getHeight()/2);
		addComponent(cost);
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		float centerX = getWidth()/2;
		float centerY = getHeight()/2;
		
		TotemDefender.EnableBlend();
		
		Rectangle rect = ScissorStack.popScissors(); //Pop the parent scissors as we don't want to clip the blockHighlight
		batch.begin();
		batch.draw(blockHighlight, centerX - blockHighlight.getWidth()/2, getHeight() - blockHighlight.getHeight()/2 - 15, blockHighlight.getWidth()-1, blockHighlight.getHeight()-1);
		batch.end();
		ScissorStack.pushScissors(rect); //Return to previous state
		
		batch.begin();
		batch.draw(block, centerX - TotemDefender.BLOCK_SIZE/2, getHeight() - TotemDefender.BLOCK_SIZE, TotemDefender.BLOCK_SIZE, TotemDefender.BLOCK_SIZE);
		batch.draw(bar, centerX - bar.getWidth()/2, 15 - bar.getHeight()/2, bar.getWidth(), bar.getHeight());
		batch.draw(shadow, centerX - shadow.getWidth()/2, 0, shadow.getWidth(), shadow.getHeight());
		batch.end();
		TotemDefender.DisableBlend();
		
		super.render(batch, shapeRenderer);
	}
	
	public void setBlockMaterial(TotemDefender game, BlockEntity.Material material){
		block = game.getAssetManager().get(BlockEntity.GetRandomAsset(material, shape), Texture.class);
	}
	
}
