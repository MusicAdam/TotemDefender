package com.totemdefender.entities;

import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class BalistaWeapon extends WeaponEntity {

	public BalistaWeapon(Player owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}
	
public void spawn(TotemDefender game){
		
		int originX;
		int originY=14;
		
		if(owner.getID()==1){
			originX=98;
		}
		else
			originX=30;
		
		super.spawn(game,"cannon.png",originX,originY,95,20);
		System.out.println("Balista Weapon was Spawned");
		
	}

}
