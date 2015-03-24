package com.totemdefender.menu;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;

public class Animation {
	public enum Easing{
		None
	}
	
	private Component target; //The components to be animated
	private Vector2 destination; //The location we will animate to 
	private long duration = 200; //Time in MS given to animate to target
	private Easing easing  = Easing.None; //The algorithm used for easing
	private long start; //The time the animation was started
	private float stepX, stepY; //The amount to change each step;
	private boolean valid = false;
	
	public Animation(Component target){
		this.target = target;
	}
	
	public void update(TotemDefender game){
		target.setPosition(target.getPosition().add(stepX, stepY));
		onStep();
	}
	
	public long getElapsedTime(){
		return System.currentTimeMillis() - start;
	}
	
	public Component getTargets(){
		return target;
	}
	
	public void validate(){
		if(	target == null ||
			destination == null) return;
		
		float distanceX = destination.x - target.getPosition().x;
		float distanceY = destination.y - target.getPosition().y;
		stepX = -.5f;//distanceX/(float)duration;
		stepY = 0;//distanceY/(float)duration;
		valid = true;
	}
	
	public boolean isValid(){ return valid; }
	
	public Vector2 getDestination(){ return destination; }
	
	public void onComplete(){}//Called by bucket
	public void onStep(){}//Called by update
	public void onStart(){} //Called by bucket
	
	public Component getTarget() {
		return target;
	}

	public void setTarget(Component target) {
		this.target = target;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Easing getEasing() {
		return easing;
	}

	public void setEasing(Easing easing) {
		this.easing = easing;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public float getStepX() {
		return stepX;
	}

	public void setStepX(float stepX) {
		this.stepX = stepX;
	}

	public float getStepY() {
		return stepY;
	}

	public void setStepY(float stepY) {
		this.stepY = stepY;
	}

	public void setDestination(Vector2 destination) {
		this.destination = destination;
	}
	
	
}
