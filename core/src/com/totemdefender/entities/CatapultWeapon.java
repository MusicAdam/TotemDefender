package com.totemdefender.entities;

import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class CatapultWeapon extends WeaponEntity {

	public CatapultWeapon(Player owner) {
		
		super(owner);
		// TODO Auto-generated constructor stub
	}
	
public void Spawn(TotemDefender game){
		
		super.spawn(game,"cannon.png",95,20);
		
	}

}
