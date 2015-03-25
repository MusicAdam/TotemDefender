package com.totemdefender;

import com.totemdefender.input.InputHandler;

public class Player {
	private int id; //1 or 2
	private String name; //User entered nickname
	private int budget=1000; //The amount of money the player has to spend on blocks
	private int score=0;
	private int weaponType=1;
	
	public Player(int id){
		this.id = id;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}
}
