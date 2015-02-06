package com.totemdefender;

import java.util.ArrayList;

import com.totemdefender.entities.GroundEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.blocks.BlockEntity;

/** Provides easy way to maintain references to placed blocks and other game entities */
public class Level {
	private GroundEntity ground;
	private WeaponEntity player1Weapon;
	private WeaponEntity player2Weapon;
	private ArrayList<BlockEntity> placedBlocks = new ArrayList<BlockEntity>();
	
	public Level(TotemDefender game){		
		/** Create the world **/
		ground = new GroundEntity();
		ground.setName("Ground");
		ground.spawn(game);
		game.addEntity(ground);
		
		player1Weapon = new WeaponEntity(game.getPlayer1());
		player1Weapon.setName("Weapon 1");
		player1Weapon.spawn(game);
		game.addEntity(player1Weapon);
		
		player2Weapon = new WeaponEntity(game.getPlayer2());
		player2Weapon.setName("Weapon 2");
		player2Weapon.spawn(game);
		game.addEntity(player2Weapon);
	}

	public GroundEntity getGround() {
		return ground;
	}

	public void setGround(GroundEntity ground) {
		this.ground = ground;
	}

	public WeaponEntity getPlayer1Weapon() {
		return player1Weapon;
	}

	public void setPlayer1Weapon(WeaponEntity player1Weapon) {
		this.player1Weapon = player1Weapon;
	}

	public WeaponEntity getPlayer2Weapon() {
		return player2Weapon;
	}

	public void setPlayer2Weapon(WeaponEntity player2Weapon) {
		this.player2Weapon = player2Weapon;
	}
	
	public void addPlacedBlock(BlockEntity ent){
		placedBlocks.add(ent);
	}
	
	public ArrayList<BlockEntity> getPlacedBlocks(){
		return placedBlocks;
	}
}
