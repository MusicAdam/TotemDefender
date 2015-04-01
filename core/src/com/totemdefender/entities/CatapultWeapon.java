package com.totemdefender.entities;

import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class CatapultWeapon extends WeaponEntity {

	public CatapultWeapon(Player owner) {
		super(owner);
	}
	
<<<<<<< HEAD
	public void spawn(TotemDefender game){
=======
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
		
>>>>>>> 99f6cbaacd3d378b81e25a32d83f98c06a0f6578
	}

}
