package com.totemdefender.entities.blocks;

import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity.Material;
import com.totemdefender.entities.blocks.BlockEntity.Shape;
import com.totemdefender.player.Player;

public class SquareBlockEntity extends BlockEntity {	
	public static final float DENSITY = 5.0f;
	
	public SquareBlockEntity(Player owner){
		super(owner, Material.Wood, Shape.Square);
		this.setCost(100);
	}
	
	public SquareBlockEntity(Player owner, Material material) {
		super(owner, material, Shape.Square);
		this.setCost(200);
	}
	
	@Override
	public void spawn(TotemDefender game){
		super.spawn(game);
		setDensity(DENSITY);
	}
}
