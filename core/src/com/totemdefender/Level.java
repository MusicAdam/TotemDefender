package com.totemdefender;

import java.util.ArrayList;

import com.totemdefender.entities.BackgroundEntity;
import com.totemdefender.entities.GroundEntity;
import com.totemdefender.entities.PedestalEntity;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.blocks.BlockEntity;

/** Provides easy way to maintain references to placed blocks and other game entities */
public class Level {
	private GroundEntity ground;
	private WeaponEntity player1Weapon;
	private WeaponEntity player2Weapon;
	private PedestalEntity 	player1Pedestal;
	private PedestalEntity	player2Pedestal;
	private TotemEntity		player1Totem;
	private BackgroundEntity background; 

	private TotemEntity		player2Totem;
	private ArrayList<BlockEntity> placedBlocks = new ArrayList<BlockEntity>();
	
	public Level(TotemDefender game){		
		/** Create the world **/
		background = new BackgroundEntity();
		background.setName("Background");
		background.spawn(game);
		game.addEntity(background, TotemDefender.BACKGROUND_DEPTH);
		
		ground = new GroundEntity();
		ground.setName("Ground");
		ground.spawn(game);
		game.addEntity(ground, TotemDefender.GROUND_DEPTH);
		
		player1Weapon = new WeaponEntity(game.getPlayer1());
		player1Weapon.setName("Weapon 1");
		player1Weapon.spawn(game);
		game.addEntity(player1Weapon, TotemDefender.WEAPON_DEPTH);
		
		player2Weapon = new WeaponEntity(game.getPlayer2());
		player2Weapon.setName("Weapon 2");
		player2Weapon.spawn(game);
		game.addEntity(player2Weapon, TotemDefender.WEAPON_DEPTH);
		
		player1Pedestal = new PedestalEntity(game.getPlayer1());
		player1Pedestal.setName("Player 1 Pedestal");
		player1Pedestal.spawn(game);
		game.addEntity(player1Pedestal, TotemDefender.PEDESTAL_DEPTH);
		
		player2Pedestal = new PedestalEntity(game.getPlayer2());
		player2Pedestal.setName("Player 2 Pedestal");
		player2Pedestal.spawn(game);
		game.addEntity(player2Pedestal, TotemDefender.PEDESTAL_DEPTH);
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

	public PedestalEntity getPlayer1Pedestal() {
		return player1Pedestal;
	}

	public void setPlayer1Pedestal(PedestalEntity player1Pedestal) {
		this.player1Pedestal = player1Pedestal;
	}

	public PedestalEntity getPlayer2Pedestal() {
		return player2Pedestal;
	}

	public void setPlayer2Pedestal(PedestalEntity player2Pedestal) {
		this.player2Pedestal = player2Pedestal;
	}
	public TotemEntity getPlayer1Totem() {
		return player1Totem;
	}

	public void setPlayer1Totem(TotemEntity player1Totem) {
		this.player1Totem = player1Totem;
	}

	public TotemEntity getPlayer2Totem() {
		return player2Totem;
	}

	public void setPlayer2Totem(TotemEntity player2Totem) {
		this.player2Totem = player2Totem;
	}
	
	public boolean checkActivePlayerEntities(int plID){
		for(BlockEntity ent : getPlacedBlocks()){
			if(ent.getOwner().getID() == plID && ent.getBody().isAwake()){
				return true;
			}
		}
		
		return ((plID == 1) ? player1Totem.getBody().isAwake() : player2Totem.getBody().isAwake());
	}
	
	public Player checkTotemStatus(){
		if(player1Totem.isOnGround()){
			return player1Totem.getOwner();
		}else if(player2Totem.isOnGround()){
			return player2Totem.getOwner();
		}
		
		return null;
	}

	public PedestalEntity getPedestal(Player owner) {
		if(owner.getID() == 1){
			return player1Pedestal;
		}else{
			return player2Pedestal;
		}
	}

	public void addTotem(TotemEntity totem) {
		if(totem.getOwner().getID() == 1){
			player1Totem = totem;
		}else{
			player2Totem = totem;
		}
	}
}
