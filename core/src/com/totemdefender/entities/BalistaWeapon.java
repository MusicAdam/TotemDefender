package com.totemdefender.entities;

import com.totemdefender.Player;
import com.totemdefender.TotemDefender;

public class BalistaWeapon extends WeaponEntity {

	public BalistaWeapon(Player owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}
	
public void Spawn(TotemDefender game){
		
		super.spawn(game,"cannon.png",95,20);
		
	}

}
