package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.totemdefender.TotemDefender;

public class ScoreLine extends Container{	
	public enum ScoreType{
		Destruction,
		Miss,
		Win,
		Total
	}
	
	public static String GetDescription(ScoreType type){
		switch(type){
		case Destruction:
			return "Destruction";
		case Miss:
			return "Miss";
		case Win:
			return "Winner";
		case Total:
			return "Total";
		}
		return "Misc";
	}
	
	protected int 	value; //Represents the base value for the event the player is getting score for
	protected Label 	description; //Tells the user what the score was recieved for. 
	protected Label		scoreDisplay;
	protected ScoreType scoreType;
	private boolean counting = false;
	private int currentCount, increment;
	private Color fontColor;
	private long delay = 500; //in ms
	private long startTime = -1;
	
	public ScoreLine(int value, ScoreType scoreType){
		this.value = value;
		this.scoreType = scoreType;
		this.description = new Label(this);
		this.description.setFont("hud_small.ttf");
		this.description.setTextColor(fontColor);
		this.description.setText(GetDescription(scoreType));
		this.scoreDisplay = new Label(this);
		this.scoreDisplay.setText(value+"");
		this.scoreDisplay.setTextColor(fontColor);
		this.scoreDisplay.setText(""+value);
		this.scoreDisplay.setFont("hud_small.ttf");
	}
	
	public ScoreLine(int value, ScoreType scoreType, String description){
		this.value = value;
		this.scoreType = scoreType;
		this.description = new Label(this);
		this.description.setText(description);
		this.description.setFont("hud_small.ttf");
		this.description.setTextColor(fontColor);
		this.scoreDisplay = new Label(this);
		this.scoreDisplay.setTextColor(fontColor);
		this.scoreDisplay.setText(""+value);
		this.scoreDisplay.setFont("hud_small.ttf");
	}
	
	@Override
	public void create(TotemDefender game){
		description.create(game);
		scoreDisplay.create(game);
		
		super.create(game);
	}
	
	@Override
	public void validate(){
		description.sizeToBounds();
		description.setPosition(0, 0);
		updateScorePosition();
		super.validate();
	}
	
	@Override 
	public void update(TotemDefender game){
		if(counting){
			if(currentCount == value){
				if(startTime == -1)
					startTime = System.currentTimeMillis();
				if(System.currentTimeMillis() - startTime > delay) {
					counting = false;
					startTime = -1;
				}
			}else{
				currentCount += increment;
				if(currentCount > value)
					currentCount = value;
				scoreDisplay.setText(""+currentCount);
				updateScorePosition();
			}
		}
		
		super.update(game);
	}
	
	private void updateScorePosition(){
		scoreDisplay.sizeToBounds();
		scoreDisplay.setPosition(getWidth() - scoreDisplay.getWidth(), 0);
	}
	
	public void setValue(int value){ this.value = value; }
	public int getValue(){ return value; }
	public Label getDescription(){ return description; }
	public String getDescriptionText(){ return description.getText(); }
	public ScoreType getScoreType(){ return scoreType; }
	
	public void doCount(){
		counting = true;
		currentCount = 0;
		if(value == 0){
			increment = 0;
		}else{
			increment = (int) Math.ceil((float)value/50f) + (value/Math.abs(value));
		}
		scoreDisplay.setText("0");
	}
	
	public boolean doneCounting(){
		return counting == false;
	}
	
	public void setFontColor(Color color){
		fontColor = color;
		description.setTextColor(fontColor);
		scoreDisplay.setTextColor(fontColor);
	}
}
