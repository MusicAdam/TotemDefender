package com.totemdefender.entities.blocks;

import com.totemdefender.player.Player;

public class RectangleBlockEntity extends BlockEntity {

	public RectangleBlockEntity(Player owner) {
		super(owner, Material.Wood, Shape.Rectangle);
	}
	
	public RectangleBlockEntity(Player owner, Material material) {
		super(owner, material, Shape.Rectangle);
	}

}
