package com.totemdefender.menu.buildmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Container;

public class ReadyButton extends Button{
	private Player owner;
	private TotemEntity totem;
	private Texture barTall, barTallHover, totemTexture;
	private Vector2 totemPosition;
	private boolean totemSpawned;
	private float alpha;
	
	public ReadyButton(BuildMenu parent, Player owner) {
		super(parent);
		this.owner = owner;
		setSize(135, 29);
		setColor(null);
		alpha = 1;
	}
	
	@Override
	public void create(TotemDefender game){
		barTall 		= game.getAssetManager().get("ui/bar_tall.png", Texture.class);
		barTallHover 	= game.getAssetManager().get("ui/bar_tall_hover.png", Texture.class);
		totemTexture	= game.getAssetManager().get("totem_face_shaded.png", Texture.class);

		setFont("hud_small.ttf");
		setTextColor(new Color(0.960784f, 0.858824f, 0.866667f, 1));
		setText("Ready");
		setTextOffset(TotemDefender.BLOCK_SIZE + 13, getSize().y/2 - getTextBounds().height/2 + 5);		
		getParent().invalidate();
		super.create(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){		
		Texture barActive;
		if(isHighlighted()){
			barActive = barTallHover;
		}else{
			barActive = barTall;
		}
		TotemDefender.EnableBlend();
		batch.begin();
		batch.setColor(1, 1, 1, alpha);
		batch.draw(barActive, getPosition().x, getPosition().y, getWidth(), getHeight());

		if(!totemSpawned)
			batch.draw(totemTexture, totemPosition.x, totemPosition.y, TotemDefender.BLOCK_SIZE, TotemDefender.BLOCK_SIZE*2);
		batch.end();
		TotemDefender.DisableBlend();
		
		super.render(batch, shapeRenderer);
	}
	
	public TotemEntity getTotem(){
		return totem;
	}
	
	@Override
	public boolean onClick(){
		if(totemSpawned) return false;
		if(getParent().getGrid().hasEntity()) return false;
		spawnTotem();
		getParent().getGrid().setEntity(getParent().getSpawnedBlock());
			
		return true;
	}
	
	@Override
	public boolean onKeyboardSelect(){
		if(totemSpawned) return false;
		if(getParent().getGrid().hasEntity()) return false;
		spawnTotem();
		getParent().getGrid().setEntity(getParent().getSpawnedBlock());
			
		return true;		
	}

	@Override
	public BuildMenu getParent(){
		return (BuildMenu)super.getParent();
	}
	
	@Override
	public void setPosition(Vector2 position){
		super.setPosition(position);
		totemPosition = new Vector2(getPosition().x + 10, getPosition().y - TotemDefender.BLOCK_SIZE/2);
	}
	
	public boolean isTotemSpawned(){
		return totemSpawned;
	}
	
	public void spawnTotem(){
		if(totemSpawned) return;
		
		TotemDefender game = TotemDefender.Get();
		
		totem = new TotemEntity(owner);
		totem.spawn(game);
		totem.setPosition(game.screenToWorld(totemPosition.add(getParent().getWorldPosition())));
		totem.getBody().setActive(false);
		game.addEntity(totem, TotemDefender.PROJECTILE_DEPTH); //Eclipse is giving me errors using the TOTEM_DEPTH const. No idea why.
		getParent().setSpawnedBlock(totem);
		
		totemSpawned = true;
	}
	
	public void setAlpha(float alpha){
		this.alpha = alpha;
		getTextColor().a = alpha;
	}
}
