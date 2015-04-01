package com.totemdefender.entities;

import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class CannonWeapon extends WeaponEntity {

	public CannonWeapon(Player owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	
	public void spawn(TotemDefender game){
		
		weaponSprite="cannon.png";	
		
			origin.y=14;
			
			if(owner.getID()==1){
				origin.x=98;
			}
			else
				origin.x=30;
			
			barrelPosX=95;
			barrelPosY=20;
			
			super.spawn(game);
			System.out.println("Cannon Weapon was Spawned");
			
		}
	
}
