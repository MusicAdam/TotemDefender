package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class CannonWeapon extends WeaponEntity {

	public CannonWeapon(Player owner) {
		super(owner);
	}

	
public void spawn(TotemDefender game){
		
		weaponSprite="cannon.png";	
		
			
			
			if(owner.getID()==1){
				origin=new Vector2(33,30);
			}
			else
				origin=new Vector2(98,30);
			
			barrelPosX=95;
			barrelPosY=20;
			
			super.spawn(game);
			System.out.println("Cannon Weapon was Spawned");
			
		}
	
}
