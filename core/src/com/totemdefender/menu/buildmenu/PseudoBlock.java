package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.BlockEntity.Shape;
import com.totemdefender.menu.Component;
import com.totemdefender.menu.Container;

public class PseudoBlock extends Component {
	private final float squareHighlightW = 56f;
	private final float squareHighlightH = 56f;
	private final float rectangleHighlightW = 86;
	private final float rectangleHighlightH = 56;
	
	private Player owner;
	private boolean hovered = false;
	private BlockEntity.Shape shape;
	private BlockEntity.Material material;
	private Texture block;
	private Texture blockHighlight;
	private Texture blockHighlightHover;
	private float highlightAlpha = 1;
	
	public PseudoBlock(Container parent, Player owner, BlockEntity.Shape shape, BlockEntity.Material material){
		super(parent);
		this.shape = shape;
		this.material = material;
		this.owner = owner;
		
		if(shape == Shape.Rectangle){
			setSize(TotemDefender.BLOCK_SIZE * BlockEntity.RECTANGLE_XSCALE,
					TotemDefender.BLOCK_SIZE * BlockEntity.RECTANGLE_YSCALE);
		}else{
			setSize(TotemDefender.BLOCK_SIZE * BlockEntity.SQUARE_XSCALE,
					TotemDefender.BLOCK_SIZE * BlockEntity.SQUARE_YSCALE);
		}
	}
	
	@Override
	public void create(TotemDefender game){
		String shapeString 	= shape.toString().toLowerCase();
		blockHighlight 		= game.getAssetManager().get("ui/"+ shapeString + "_highlight.png", Texture.class);
		blockHighlightHover = game.getAssetManager().get("ui/"+ shapeString + "_highlight_hover.png", Texture.class);
		block = game.getAssetManager().get(BlockEntity.GetRandomAsset(material, shape), Texture.class);
		
		super.create(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		if(!shouldRender()) return;
		super.render(batch, shapeRenderer);
		
		float centerX = getWidth()/2;
		float x = getPosition().x;
		float y = getPosition().y;
		Texture blockHighlightActive;
		float offsetX = 0;
		if(!getParent().getParent().isMouseOver()){
			blockHighlightActive = blockHighlight;	
		}else{
			blockHighlightActive = blockHighlightHover;	
			offsetX = squareHighlightW;
		}
	
		batch.begin();
		batch.setColor(1, 1, 1, highlightAlpha);
		batch.draw(blockHighlightActive, x - blockHighlightActive.getWidth()/2 + getWidth()/2 + 1, y - blockHighlightActive.getHeight()/2 + getHeight()/2, blockHighlightActive.getWidth() - 3, blockHighlightActive.getHeight() - 1);
		batch.setColor(1, 1, 1, 1);
		batch.draw(block, x, y, getWidth(), getHeight());
		batch.end();
	}
	
	
	public BlockEntity.Shape getShape(){ return shape; }	
	public BlockEntity.Material getMaterial(){ return material; }
	
	public float getHighlightHeight(){ return blockHighlightHover.getHeight(); }
	public void setHighlightAlpha(float alpha){
		highlightAlpha = alpha;
	}
}
