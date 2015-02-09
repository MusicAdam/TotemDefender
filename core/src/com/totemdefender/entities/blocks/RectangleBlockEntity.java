package com.totemdefender.entities.blocks;

import com.totemdefender.Player;

public class RectangleBlockEntity extends BlockEntity {

	public RectangleBlockEntity(Player owner) {
		super(owner, Material.Stone, Shape.Rectangle);
	}

}
