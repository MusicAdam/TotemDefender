package com.totemdefender.player;

import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.menu.ScoreLine;

public class Player {
	public static final int MAX_SCORE_MULTIPLIER 	= 10; //The maximum possible score multiplier
	public static final int MISS_SCORE  			= -10;
	public static final int WIN_SCORE  				= 500;
	
	private int id; //1 or 2
	private String name; //User entered nickname
	private int budget=1000; //The amount of money the player has to spend on blocks
	private WeaponEntity.WeaponType weaponType = WeaponEntity.WeaponType.Cannon;
	private PlayerScore score;
	private int scoreMultiplier = 1; //Resets after a miss, maintianed through turns

	
	public Player(int id){
		this.id = id;
		score = new PlayerScore(this);
	}
	
	public int getID(){
		return id;
	}

	public int getSelectKey() {
		return (id == 1) ? InputHandler.PL_1_SELECT : InputHandler.PL_2_SELECT;
	}

	public int getUpKey() {
		return (id == 1) ? InputHandler.PL_1_U : InputHandler.PL_2_U;
	}

	public int getDownKey() {
		return (id == 1) ? InputHandler.PL_1_D : InputHandler.PL_2_D;
	}

	public int getLeftKey() {
		return (id == 1) ? InputHandler.PL_1_L : InputHandler.PL_2_L;
	}
	

	public int getRightKey() {
		return (id == 1) ? InputHandler.PL_1_R : InputHandler.PL_2_R;
	}

	public int getRotateKey() {
		return (id == 1) ? InputHandler.PL_1_ROTATE : InputHandler.PL_2_ROTATE;
	}
	
	public int getBudget(){
		return budget;
	}
	
	public void setBudget(int budget){
		this.budget=budget;
	}

	public int getTotalScore() {
		return score.getTotalScore();
	}
	
	public PlayerScore getScore(){ 
		return score;
	}

	public void addScore(ScoreLine.ScoreType type, int value) {
		this.score.insertScore(type, value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public WeaponEntity.WeaponType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(WeaponEntity.WeaponType weaponType) {
		this.weaponType = weaponType;
	}
	
	public int getScoreMultiplier(){
		return scoreMultiplier;
	}
	
	public void incrementScoreMultiplier(){
		if(scoreMultiplier < MAX_SCORE_MULTIPLIER){
			scoreMultiplier++;
		}
	}
	
	public void resetScoreMultiplier(){
		scoreMultiplier = 1;

	}
}
