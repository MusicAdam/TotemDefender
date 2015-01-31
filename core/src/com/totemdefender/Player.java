package com.totemdefender;

public class Player {
	private int id; //1 or 2
	private String nickname; //User entered nickname
	
	public Player(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
}
