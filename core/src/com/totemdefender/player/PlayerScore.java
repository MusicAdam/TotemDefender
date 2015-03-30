package com.totemdefender.player;

import java.util.ArrayList;
import java.util.HashMap;

import com.totemdefender.menu.ScoreLine;

public class PlayerScore {
	private Player owner;
	private HashMap<ScoreLine.ScoreType, ArrayList<ScoreLine> > scoreLines; 
	private int totalScore = 0;
	private boolean valid = false;
	
	public PlayerScore(Player owner){
		this.owner = owner;
		scoreLines = new HashMap<ScoreLine.ScoreType, ArrayList<ScoreLine> >();
	}
	
	public void insertScore(ScoreLine.ScoreType type, int value){
		ArrayList<ScoreLine> lines;
		if(!scoreLines.containsKey(type)){
			lines = new ArrayList<ScoreLine>();
			scoreLines.put(type, lines);
		}else{
			lines = scoreLines.get(type);
		}
		
		lines.add(new ScoreLine(value, type));
		totalScore += value;
	}
	
	public void calculateScore(){
		totalScore = 0;
		for(ArrayList<ScoreLine> lines : scoreLines.values()){
			for(ScoreLine line : lines){
				totalScore += line.getValue();
			}
		}
	}
	
	public int getTotalScore(){
		return totalScore;
	}
	
	public ArrayList<ScoreLine> getScoreLines(ScoreLine.ScoreType type){
		ArrayList<ScoreLine> results = scoreLines.get(type);
		if(results == null)
			results = new ArrayList<ScoreLine>();
		return results;
	}
}
