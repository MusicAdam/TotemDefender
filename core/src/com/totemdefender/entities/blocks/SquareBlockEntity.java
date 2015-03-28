package com.totemdefender.entities.blocks;

import com.totemdefender.TotemDefender;
import com.totemdefender.entities.blocks.BlockEntity.Material;
import com.totemdefender.entities.blocks.BlockEntity.Shape;
import com.totemdefender.player.Player;

public class SquareBlockEntity extends BlockEntity {
	public SquareBlockEntity(Player owner){
		super(owner, Material.Wood, Shape.Square);
	}
	
	public SquareBlockEntity(Player owner, Material material) {
		super(owner, material, Shape.Square);
	}
}
