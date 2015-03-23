package com.totemdefender.menu;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.totemdefender.TotemDefender;

//Animations in a bucket will run sequentially
public class AnimationBucket {
	Queue<Animation> queue = new ConcurrentLinkedQueue<Animation>();
	Animation active;
	
	public AnimationBucket(){
		
	}
	
	public Animation queueAnimation(Animation anim){
		queue.add(anim);
		return anim;
	}
	
	public void update(TotemDefender game){
		if(active == null && !queue.isEmpty()){
			active = queue.poll();
			active.validate();
			if(!active.isValid()){
				active = null;
				//TODO: Throw error?
			}else{
				active.onStart();
			}
		}
		if(active != null){
			active.update(game);
			if(active.getElapsedTime() >= active.getDuration()){
				active.onComplete();
				active = null;
			}
		}
	}
}
