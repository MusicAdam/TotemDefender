package com.totemdefender.menu;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;

public class Animation {
	public enum Easing{
		Linear,
		QuadraticIn,
		QuadraticOut
	}
	
	public static Vector2 EaseLinear(float duration, Vector2 destination, Vector2 position){
		Vector2 distance = destination.sub(position);
		float step = Math.round(duration/(TotemDefender.STEP * 1000));
		return distance.scl(1/step);
	}
	//Accelerating from 0 velocity
	public static Vector2 EaseQuadraticIn(float currentTime, float duration, Vector2 currentPosition, Vector2 destination){
		Vector2 distance = destination.cpy().sub(currentPosition);
		currentTime /= duration;
		return distance.scl(currentTime * currentTime);
	}
	
	
	//Accelerate until halfway, then decelerate
	public static Vector2 EaseQuadraticOut(float currentTime, float duration, Vector2 currentPosition, Vector2 destination){
		Vector2 distance = destination.cpy().sub(currentPosition);
		currentTime /= duration/2;
		if(currentTime < 1) return distance.scl(.5f).scl(currentTime * currentTime);
		currentTime--;
		return distance.scl(-.5f).scl(currentTime * (currentTime-2) -1);
	}
	
	protected Component target; //The components to be animated
	private Vector2 destination; //The location we will animate to 
	private long duration = 200; //Time in MS given to animate to target
	private Easing easing  = Easing.Linear; //The algorithm used for easing
	private long start; //The time the animation was started
	private Vector2 step = null; //The amount to change each step;
	private boolean valid = false;
	private Vector2 initialPosition = null;
	
	public Animation(Component target){
		setTarget(target);
	}
	
	public void update(TotemDefender game){
		calculateStep();
		target.setPosition(target.getPosition().add(step));
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
		calculateStep();
		
		valid = true;
	}
	
	public void calculateStep(){
		switch(easing){
			case Linear:
				if(step == null)//Only calculate once
					step = EaseLinear(duration, destination, target.getPosition());
				break;
			case QuadraticIn:
				step = EaseQuadraticIn(getElapsedTime(), duration, target.getPosition(), destination);
				break;
			case QuadraticOut:
				step = EaseQuadraticIn(getElapsedTime(), duration, target.getPosition(), destination);
				break;
		}
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
		initialPosition = target.getPosition();
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

	public void setDestination(Vector2 destination) {
		this.destination = destination;
	}
	
	
}
