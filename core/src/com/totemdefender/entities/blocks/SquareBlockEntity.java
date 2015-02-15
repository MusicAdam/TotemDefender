package com.totemdefender.entities.blocks;

import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class SquareBlockEntity extends BlockEntity {	
	public static final float DENSITY = 5.0f;
	
	public SquareBlockEntity(Player owner){
		super(owner, Material.Stone, Shape.Square);
	}
	
	@Override
	public void spawn(TotemDefender game){
		super.spawn(game);
		
		
		
		setDensity(DENSITY);
	}
}
