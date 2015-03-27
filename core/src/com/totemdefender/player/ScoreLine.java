package com.totemdefender.player;

public class ScoreLine {	
	public enum ScoreType{
		Destruction,
		Miss,
		Win
	}
	
	public static String GetDescription(ScoreType type){
		switch(type){
		case Destruction:
			return "Destruction";
		case Miss:
			return "Miss";
		case Win:
			return "Winner";
		}
		return "Misc";
	}
	
	protected int 	value; //Represents the base value for the event the player is getting score for
	protected String 	description; //Tells the user what the score was recieved for. 
	protected ScoreType scoreType;
	
	public ScoreLine(int value, ScoreType scoreType){
		this.value = value;
		this.scoreType = scoreType;
		this.description = GetDescription(scoreType);
	}
	
	public ScoreLine(int value, ScoreType scoreType, String description){
		this.value = value;
		this.scoreType = scoreType;
		this.description = description;
	}
	
	public int getValue(){ return value; }
	public String getDescription(){ return description; }
}
