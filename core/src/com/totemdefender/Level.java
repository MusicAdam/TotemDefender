package com.totemdefender;

import java.util.ArrayList;

import com.totemdefender.entities.BackgroundEntity;
import com.totemdefender.entities.BalistaWeapon;
import com.totemdefender.entities.CannonWeapon;
import com.totemdefender.entities.CatapultWeapon;
import com.totemdefender.entities.GroundEntity;
import com.totemdefender.entities.PedestalEntity;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.entities.WeaponEntity.WeaponType;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.menu.hud.HUD;
import com.totemdefender.player.Player;

/** Provides easy way to maintain references to placed blocks and other game entities */
public class Level {
	private GroundEntity ground;
	private WeaponEntity player1Weapon;
	private WeaponEntity player2Weapon;
	private PedestalEntity 	player1Pedestal;
	private PedestalEntity	player2Pedestal;
	private TotemEntity		player1Totem;
	private BackgroundEntity background; 
	private HUD	hud;

	private TotemEntity		player2Totem;
	private ArrayList<BlockEntity> placedBlocks = new ArrayList<BlockEntity>();
	
	public Level(TotemDefender game){	
		hud = new HUD(game, this);
		hud.create(game);
		
		/** Create the world **/
		background = new BackgroundEntity();
		background.setName("Background");
		background.spawn(game);
		game.addEntity(background, TotemDefender.BACKGROUND_DEPTH);
		
		ground = new GroundEntity();
		ground.setName("Ground");
		ground.spawn(game);
		game.addEntity(ground, TotemDefender.GROUND_DEPTH);
		
		createPlayerWeapons();
		
		player1Pedestal = new PedestalEntity(game.getPlayer1());
		player1Pedestal.setName("Player 1 Pedestal");
		player1Pedestal.spawn(game);
		game.addEntity(player1Pedestal, TotemDefender.PEDESTAL_DEPTH);
		
		player2Pedestal = new PedestalEntity(game.getPlayer2());
		player2Pedestal.setName("Player 2 Pedestal");
		player2Pedestal.spawn(game);
		game.addEntity(player2Pedestal, TotemDefender.PEDESTAL_DEPTH);
	}
	
	public void destroy(TotemDefender game){
		game.destroyEntity(background);
		game.destroyEntity(ground);
		game.destroyEntity(player1Weapon);
		game.destroyEntity(player2Weapon);
		game.destroyEntity(player1Pedestal);
		game.destroyEntity(player2Pedestal);
		game.destroyEntity(player1Totem);
		game.destroyEntity(player2Totem);
		hud.destroy(game);
		
		for(BlockEntity ent : placedBlocks){
			game.destroyEntity(ent);
		}
		placedBlocks.clear();
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
		hud.setPlayer1Weapon(player1Weapon);
	}

	public WeaponEntity getPlayer2Weapon() {
		return player2Weapon;
	}

	public void setPlayer2Weapon(WeaponEntity player2Weapon) {
		this.player2Weapon = player2Weapon;
		hud.setPlayer2Weapon(player2Weapon);
	}
	
	public void addPlacedBlock(BlockEntity ent){
		if(!placedBlocks.contains(ent)){
			placedBlocks.add(ent);
		}
	}
	
	public void removePlacedBlock(BlockEntity ent){
		placedBlocks.remove(ent);
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
	
	public HUD getHUD(){
		return hud;
	}

	public void clearPlayerEntities() {
		TotemDefender game = TotemDefender.Get();
		
		for(BlockEntity block : placedBlocks){
			game.destroyEntity(block);
		}
		
		placedBlocks.clear();
		game.destroyEntity(player1Totem);
		game.destroyEntity(player2Totem);
		player1Totem = null;
		player2Totem = null;
		

		game.destroyEntity(player1Weapon);
		game.destroyEntity(player2Weapon);
		player1Weapon = null;
		player2Weapon = null;
		
		hud.destroy(game);
		hud = new HUD(game, this);
		hud.create(game);
	}
	
	public void createPlayerWeapons(){
		TotemDefender game = TotemDefender.Get();
		if(game.getPlayer1().getWeaponType()==WeaponEntity.WeaponType.Cannon)
			setPlayer1Weapon(new CannonWeapon(game.getPlayer1()));
		
		if(game.getPlayer1().getWeaponType()==WeaponEntity.WeaponType.Catapult)
			setPlayer1Weapon(new CatapultWeapon(game.getPlayer1()));
		
		if(game.getPlayer1().getWeaponType()==WeaponEntity.WeaponType.Ballista)
			setPlayer1Weapon(new BalistaWeapon(game.getPlayer1()));
		
		player1Weapon.setName(game.getPlayer1().getWeaponType().toString());
		player1Weapon.spawn(game);
		game.addEntity(player1Weapon, TotemDefender.WEAPON_DEPTH);
		
		if(game.getPlayer2().getWeaponType()==WeaponEntity.WeaponType.Cannon)
			setPlayer2Weapon(player2Weapon = new CannonWeapon(game.getPlayer2()));
		
		if(game.getPlayer2().getWeaponType()==WeaponEntity.WeaponType.Catapult)
			setPlayer2Weapon(player2Weapon = new CatapultWeapon(game.getPlayer2()));
		
		if(game.getPlayer2().getWeaponType()==WeaponEntity.WeaponType.Ballista)
			setPlayer2Weapon(new BalistaWeapon(game.getPlayer2()));
		player2Weapon.setName(game.getPlayer1().getWeaponType().toString());
		player2Weapon.spawn(game);
		game.addEntity(player2Weapon, TotemDefender.WEAPON_DEPTH);
	}

	public void changePlayer1Weapon(WeaponType type) {
		TotemDefender game = TotemDefender.Get();
		game.destroyEntity(player1Weapon);
		game.getPlayer1().setWeaponType(type);
		if(game.getPlayer1().getWeaponType()==WeaponEntity.WeaponType.Cannon)
			setPlayer1Weapon(new CannonWeapon(game.getPlayer1()));		
		if(game.getPlayer1().getWeaponType()==WeaponEntity.WeaponType.Catapult)
			setPlayer1Weapon(new CatapultWeapon(game.getPlayer1()));		
		if(game.getPlayer1().getWeaponType()==WeaponEntity.WeaponType.Ballista)
			setPlayer1Weapon(new BalistaWeapon(game.getPlayer1()));
		
		player1Weapon.setName(game.getPlayer1().getWeaponType().toString());
		player1Weapon.spawn(game);
		game.addEntity(player1Weapon, TotemDefender.WEAPON_DEPTH);
	}
	
	public void changePlayer2Weapon(WeaponType type) {
		TotemDefender game = TotemDefender.Get();
		game.destroyEntity(player2Weapon);
		game.getPlayer2().setWeaponType(type);
		if(game.getPlayer2().getWeaponType()==WeaponEntity.WeaponType.Cannon)
			setPlayer2Weapon(new CannonWeapon(game.getPlayer2()));		
		if(game.getPlayer2().getWeaponType()==WeaponEntity.WeaponType.Catapult)
			setPlayer2Weapon(new CatapultWeapon(game.getPlayer2()));		
		if(game.getPlayer2().getWeaponType()==WeaponEntity.WeaponType.Ballista)
			setPlayer2Weapon(new BalistaWeapon(game.getPlayer2()));
		
		player2Weapon.setName(game.getPlayer2().getWeaponType().toString());
		player2Weapon.spawn(game);
		game.addEntity(player2Weapon, TotemDefender.WEAPON_DEPTH);
	}
}
