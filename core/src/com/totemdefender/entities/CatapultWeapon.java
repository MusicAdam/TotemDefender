package com.totemdefender.entities;

import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class CatapultWeapon extends WeaponEntity {

	public CatapultWeapon(Player owner) {
		
		super(owner);
		// TODO Auto-generated constructor stub
	}
	
public void spawn(TotemDefender game){
		
	weaponSprite="catapult.png";	
	
		origin.y=14;
		
		if(owner.getID()==1){
			origin.x=98;
		}
		else
			origin.x=30;
		
		barrelPosX=15;
		barrelPosY=20;
		
		super.spawn(game);
		System.out.println("Catapult Weapon was Spawned");
		
	}

}
