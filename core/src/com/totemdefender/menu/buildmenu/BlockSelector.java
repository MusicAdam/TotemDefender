package com.totemdefender.menu.buildmenu;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.BlockEntity.Shape;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Animation;
import com.totemdefender.menu.AnimationBucket;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.Label;
import com.totemdefender.menu.ScissorRegion;
import com.totemdefender.player.Player;

public class BlockSelector extends Container{
	/** This is the best way to do this without some sort of XML layout system */
	//Derived these numbers from the illustrator design src file
	private final float barW = 135;
	private final float barH = 15;
	private final float shadowW = 73;
	private final float shadowH = 18;
	private Player owner;
	private Label cost;
	private Texture bar;
	private Texture barHover;
	private Texture shadow;	
	private ArrowButton arrowRight;
	private ArrowButton arrowLeft;
	private boolean hovered = false;
	private BlockEntity mouseSpawned = null;
	private Vector2 mouseLocation = null;
	private MouseEvent mouseMoveListener;
	private float alpha;
	private PseudoBlock block;
	private PseudoBlock newBlock;
	private int materialIndex; //Index of the material in this.materialList
	private BlockEntity.Material[] materialList; //From BlockEntity.Material enum
	private AnimationBucket animateInBucket, animateOutBucket;
	private Animation transitionOutAnimation, transitionInAnimation;
	private BlockEntity.Shape shape;
	private Vector2 localPseudoBlockPosition;
	private long transitionDuration = 250;
	private ScissorRegion blockScissors; //These are the scissors that are the width of the visible block area, and are the height of the block
	
	public BlockSelector(BuildMenu parent, Player owner, BlockEntity.Shape shape){
		super(parent);
		setSize(135, 73);
		shouldSizeToContents(false);
		this.owner = owner;
		this.shape = shape;
		
		alpha = 1;

		materialIndex = 0;
		materialList = new BlockEntity.Material[]{
				BlockEntity.Material.Wood,
				BlockEntity.Material.Stone,
				BlockEntity.Material.Jade
		};
		animateInBucket = TotemDefender.Get().getAnimationController().createBucket();
		animateOutBucket = TotemDefender.Get().getAnimationController().createBucket();
	}
	
	@Override
	public void create(TotemDefender game){//Arrow button left
		bar 				= game.getAssetManager().get("ui/bar.png", Texture.class);
		barHover			= game.getAssetManager().get("ui/bar_hover.png", Texture.class);
		shadow				= game.getAssetManager().get("ui/shadow.png", Texture.class);

		arrowLeft = new ArrowButton(this){
			@Override
			public boolean onClick(){
				previousMaterial();
				return true;
			}
		};
		arrowLeft.create(game);
		
		//Arrow button right
		arrowRight = new ArrowButton(this){
			@Override
			public boolean onClick(){
				nextMaterial();
				return true;
			}
		};
		arrowRight.create(game);
		arrowRight.flip();
		
		blockScissors = new ScissorRegion(this);
		blockScissors.shouldSizeToContents(false);
		blockScissors.create(game);

		block = new PseudoBlock(blockScissors, owner, shape, materialList[0]);	
		block.create(game);
		
		//Cost label
		cost = new Label(this);
		cost.setFont("hud_medium.ttf");
		if(block.getShape()==Shape.Square){
			cost.setText("$100", true);
		}
		else{
			cost.setText("$200",true);
		}
		cost.setTextColor(new Color(0.011765f, 0.541176f, 0.239215f, 1));
		cost.create(game);		
		
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
	public void validate(){
		if(isValid()) return;

		float yPos = getHeight();
		arrowLeft.setPosition(0, yPos - arrowLeft.getHeight());
		arrowRight.setPosition(getWidth() - arrowRight.getWidth(), yPos - arrowRight.getHeight());

		blockScissors.setSize(getWidth() - arrowLeft.getWidth() * 2, block.getHeight() + block.getHighlightHeight());
		blockScissors.setPosition(arrowLeft.getWidth(), getHeight() - blockScissors.getHeight()/2);

		localPseudoBlockPosition = new Vector2(blockScissors.getWidth()/2 - block.getWidth()/2, blockScissors.getHeight()/2 - block.getHeight());
		block.setPosition(localPseudoBlockPosition.cpy());
		
		cost.setPosition(getWidth()/2 - cost.getWidth()/2, 20 - barH/2);
		super.validate();
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
		if(!shouldRender()) return;
		float centerX = getWidth()/2;
		float x = getPosition().x;
		float y = getPosition().y;
		
		Texture barActive;
		if(!hovered){
			barActive = bar;
		}else{
			barActive = barHover;
		}
		
		TotemDefender.EnableBlend();		
		batch.begin();
		batch.setColor(1, 1, 1, alpha);
		batch.draw(barActive, x + centerX - barW/2, y + 15 - barH/2, barW, barH);
		batch.draw(shadow, x + centerX - shadowW/2, y, shadowW, shadowH);
		batch.end();
		TotemDefender.DisableBlend();
		
		super.render(batch, shapeRenderer);
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
		if(block.getShape() == Shape.Square){
			blockEntity = new SquareBlockEntity(owner, getCurrentMaterial());
		}else if(block.getShape() == Shape.Rectangle){
			blockEntity = new RectangleBlockEntity(owner, getCurrentMaterial());
		}

		if(blockEntity.getCost()<=blockEntity.getOwner().getBudget()){
			if(blockEntity != null){
				blockEntity.spawn(game);
				game.addEntity(blockEntity, TotemDefender.BLOCK_DEPTH);
				blockEntity.getBody().setActive(false);
			}
			
			getParent().setSpawnedBlock(blockEntity);
			blockEntity.getOwner().setBudget(blockEntity.getOwner().getBudget()-blockEntity.getCost());
			return blockEntity;
		}else{
			return null;
		}
		
	}
	
	@Override
	public boolean onLeftKeyUp(){
		previousMaterial();
		return true;
	}
	
	@Override
	public boolean onRightKeyUp(){
		nextMaterial();
		return true;
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
	
	public BlockEntity.Material getCurrentMaterial(){
		return materialList[materialIndex];
	}
	
	public void nextMaterial(){
		materialIndex++;
		if(materialIndex == materialList.length)
			materialIndex = 0;
		transitionLeft();
	}
	
	public void previousMaterial(){
		materialIndex--;
		if(materialIndex < 0)
			materialIndex = materialList.length-1;
		transitionRight();
	}
	
	public void transitionLeft(){
		animateOutBucket.abortCurrentAnimation();
		animateInBucket.abortCurrentAnimation();
		
		final BlockSelector finalThis = this;
		newBlock = new PseudoBlock(blockScissors, owner, shape, getCurrentMaterial());
		newBlock.setShouldRender(false);
		newBlock.create(TotemDefender.Get());
		newBlock.setPosition(localPseudoBlockPosition.cpy().add(getWidth()/2, 0));
		transitionOutAnimation = animateOutBucket.queueAnimation(new Animation(block){
			@Override
			public void onComplete(){
				target.destroy(TotemDefender.Get());
			}
			
			@Override
			public void onStep(){
				float alpha = 1 - ((float)getElapsedTime()/(float)getDuration());
				((PseudoBlock)target).setHighlightAlpha(alpha);
			}
			
			@Override
			public void onAbort(){
				target.destroy(TotemDefender.Get());
			}
		});
		transitionOutAnimation.setDestination(new Vector2(-block.getWidth(), localPseudoBlockPosition.y));
		transitionOutAnimation.setDuration(transitionDuration + 1); //+1 ms to ensure this completes after animOut & new block instead deleted instead of old block
		transitionOutAnimation.setEasing(Animation.Easing.QuadraticOut);
		
		transitionInAnimation = animateInBucket.queueAnimation(new Animation(newBlock){
			@Override
			public void onStart(){
				target.setShouldRender(true);
			}
			
			@Override
			public void onComplete(){
				clean();
			}
			
			@Override
			public void onStep(){
				float alpha = ((float)getElapsedTime()/(float)getDuration());
				((PseudoBlock)target).setHighlightAlpha(alpha);
			}
			
			@Override
			public void onAbort(){
				clean();
			}
			
			private void clean(){
				finalThis.block = (PseudoBlock) target;
				finalThis.block.setPosition(finalThis.localPseudoBlockPosition.cpy());	
				finalThis.block.setHighlightAlpha(1);
			}
		});
		transitionInAnimation.setDestination(localPseudoBlockPosition.cpy());
		transitionInAnimation.setDuration(transitionDuration);
		transitionInAnimation.setEasing(Animation.Easing.QuadraticIn);
	}
	
	public void transitionRight(){
		animateOutBucket.abortCurrentAnimation();
		animateInBucket.abortCurrentAnimation();
		
		final BlockSelector finalThis = this;
		newBlock = new PseudoBlock(blockScissors, owner, shape, getCurrentMaterial());
		newBlock.setShouldRender(false);
		newBlock.create(TotemDefender.Get());
		newBlock.setPosition(localPseudoBlockPosition.cpy().add(-getWidth()/2 - newBlock.getWidth(), 0));
		transitionOutAnimation = animateInBucket.queueAnimation(new Animation(newBlock){
			@Override
			public void onStart(){
				target.setShouldRender(true);
			}
			
			@Override
			public void onComplete(){
				clean();
			}
			
			@Override
			public void onStep(){
				float alpha = 1 - ((float)getElapsedTime()/(float)getDuration());
				((PseudoBlock)target).setHighlightAlpha(alpha);
			}
			
			@Override
			public void onAbort(){
				clean();
			}

			private void clean(){
				finalThis.block = (PseudoBlock) target;
				finalThis.block.setPosition(finalThis.localPseudoBlockPosition.cpy());	
				finalThis.block.setHighlightAlpha(1);
			}
		});
		transitionOutAnimation.setDestination(localPseudoBlockPosition.cpy());
		transitionOutAnimation.setDuration(transitionDuration); //+1 ms to ensure this completes after animOut & new block instead deleted instead of old block
		transitionOutAnimation.setEasing(Animation.Easing.QuadraticIn);
		
		transitionInAnimation = animateOutBucket.queueAnimation(new Animation(block){
			@Override
			public void onComplete(){	
				clean();
			}
			
			@Override
			public void onStep(){
				float alpha = ((float)getElapsedTime()/(float)getDuration());
				((PseudoBlock)target).setHighlightAlpha(alpha);
			}
			
			@Override
			public void onAbort(){
				clean();
			}
			
			private void clean(){
				target.destroy(TotemDefender.Get());	
			}
		});
		transitionInAnimation.setDestination(new Vector2(getWidth() + block.getWidth(), localPseudoBlockPosition.y));
		transitionInAnimation.setDuration(transitionDuration + 1);
		transitionInAnimation.setEasing(Animation.Easing.QuadraticOut);
	}
}
