package com.totemdefender.states;

import java.util.ArrayList;

import com.totemdefender.TotemDefender;
import com.totemdefender.menu.Animation;
import com.totemdefender.menu.AnimationBucket;

public class AnimationController implements State{
	
	ArrayList<AnimationBucket> buckets = new ArrayList<AnimationBucket>(); //Animations in Buckets will run in parallel

	@Override
	public boolean canEnter(TotemDefender game) {
		return true;
	}

	@Override
	public void onEnter(TotemDefender game) {		
	}

	@Override
	public void onExit(TotemDefender game) {		
	}

	@Override
	public boolean canExit(TotemDefender game) {
		return false;
	}

	@Override
	public void update(TotemDefender game) {
		if(buckets.isEmpty()) return;
		
		for(AnimationBucket bucket : buckets){
			bucket.update(game);
		}
	}
	
	public AnimationBucket createBucket(){
		AnimationBucket bucket = new AnimationBucket();
		buckets.add(bucket);
		return bucket;
	}
	
	public Animation insertAnimation(Animation anim, AnimationBucket bucket){
		bucket.queueAnimation(anim);
		return anim;
	}

}
