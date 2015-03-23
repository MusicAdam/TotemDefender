package com.totemdefender.entities.blocks;

import com.totemdefender.Player;

public class RectangleBlockEntity extends BlockEntity {

	public RectangleBlockEntity(Player owner) {
		super(owner, Material.Wood, Shape.Rectangle);
		this.setCost(200);
	}
	
	public RectangleBlockEntity(Player owner, Material material) {
		super(owner, material, Shape.Rectangle);
		this.setCost(200);
	}

}
