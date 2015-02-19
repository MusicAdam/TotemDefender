package com.totemdefender;

import java.util.ArrayList;

/** 
 *  Maps a "Virtual depth" to the user supplied depth.
 *  Sorts an array list based on the given depth.
 *  */
public class DepthMap<T> {
	private ArrayList<DepthWrapper<T>> depthMap;
	
	public DepthMap(){
		depthMap = new ArrayList<DepthWrapper<T>>();
	}
	
	public void insert(int depth, T obj){
		DepthWrapper<T> depthWrapper = new DepthWrapper<T>();
		depthWrapper.object = obj;
		depthWrapper.depth = depth;
		
		int insertAt = -1;
		for(int i = 0; i < depthMap.size(); i++){
			DepthWrapper<T> entry = depthMap.get(i);
			if(entry.depth > depth){
				insertAt = i;
				break;
			}
		}
		
		if(insertAt != -1){
			depthMap.add(insertAt, depthWrapper);
		}else{
			depthMap.add(depthWrapper);
		}
	}
	
	public void insertFirst(T obj){
		DepthWrapper<T> wrapper = new DepthWrapper<T>();
		if(!depthMap.isEmpty()){
			wrapper.depth = depthMap.get(0).depth - 1;
		}else{
			wrapper.depth = 0;
		}
		wrapper.object = obj;
		
		depthMap.add(0, wrapper);
	}
	
	public void insertLast(T obj){
		DepthWrapper<T> wrapper = new DepthWrapper<T>();
		if(!depthMap.isEmpty()){
			wrapper.depth = depthMap.get(depthMap.size() - 1).depth + 1;
		}else{
			wrapper.depth = 0;
		}
		wrapper.object = obj;
		
		depthMap.add(wrapper);
	}
	
	public boolean remove(T obj){
		DepthWrapper<T> toRemove = null;
		for(DepthWrapper<T> wrapper : depthMap){
			if(wrapper.object == obj || wrapper.object.equals(obj)){
				toRemove = wrapper;
				break;
			}
		}
		
		if(toRemove != null){
			return depthMap.remove(toRemove);
		}
		
		return false;
	}
	
	public void move(T obj, int depth){
		if(remove(obj))
			insert(depth, obj);
	}
	
	public ArrayList<T> getObjectList(){
		ArrayList<T> list = new ArrayList<T>();
		for(DepthWrapper<T> wrapper : depthMap){
			list.add(wrapper.object);
		}
		return list;
	}

	public int getDepth(T obj) {
		for(DepthWrapper<T> wrapper : depthMap){
			if(wrapper.object == obj || wrapper.object.equals(obj)){
				return wrapper.depth;
			}
		}
		
		return 0;
	}
}
