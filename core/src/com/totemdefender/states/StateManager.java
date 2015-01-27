package com.totemdefender.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.totemdefender.TotemDefender;

public class StateManager {	
	
	/** 
	 * This wraps the basic state class to keep track of whether or not its entry function has been run
	 */
	private class StateWrapper {
		public boolean hasEntered = false; //Set true after the first time entry condition has been met and onEnter has run
		public State state;
	}
	private ArrayList<StateWrapper> attachedStates; //List of states which affect the game
	private TotemDefender game; //Reference to the main game
	private ConcurrentLinkedQueue<StateWrapper> callEnterStateQueue; //Used to keep track of which onEnter calls need to be made without violating concurency
	private ConcurrentLinkedQueue<StateWrapper> callExitStateQueue;  //"                       "   onExit  "                                          "
	
	public StateManager(TotemDefender game){
		this.game = game;
		attachedStates = new ArrayList<StateWrapper>();
		callEnterStateQueue = new ConcurrentLinkedQueue<StateWrapper>();
		callExitStateQueue = new ConcurrentLinkedQueue<StateWrapper>();		
	}
	
	/** Calls active state's updates if entry condition is true, checks active states exit conditions, if the are met the state is updates and exited.
	 * Called by TotemDefender.render */
	public void update(){
		Iterator<StateWrapper> iterator = attachedStates.iterator();
		while(iterator.hasNext()){
			StateWrapper stateWrapper = iterator.next();
			State state = stateWrapper.state;
			
			if(state.canEnter(game)){
				if(!stateWrapper.hasEntered){
					stateWrapper.hasEntered = true;
					callEnterStateQueue.add(stateWrapper);
				}else{
					state.update(game);
				}
			}
			
			if(state.canExit(game)){
				callExitStateQueue.add(stateWrapper);
				iterator.remove();
			}
		}
		
		while(!callEnterStateQueue.isEmpty()){
			callEnterStateQueue.poll().state.onEnter(game);
		}
		
		while(!callExitStateQueue.isEmpty()){
			callExitStateQueue.poll().state.onExit(game);
		}
	}
	
	/** Attaches a state to the manager 
	 * @param state - State to be attached
	 */
	public void attachState(State state){
		StateWrapper wrapper = new StateWrapper();
		wrapper.state = state;
		attachedStates.add(wrapper);
	}
	
	/**Detaches a state
	 * 
	 * @param state: State to be removed
	 * @param ignoreExitCondition: if true (default) the state will be detached regardless of its exit condition.	 */
	public void detachState(State state, boolean ignoreExitCondition){
		StateWrapper wrapper = null;
		for(StateWrapper sw : attachedStates){
			if(sw.state == state){
				wrapper = sw;
				break;
			}
		}
		
		if(wrapper == null) return;
		
		if(ignoreExitCondition){
			state.onExit(game);
			attachedStates.remove(wrapper);
		}else if(state.canExit(game)){
			state.onExit(game);
			attachedStates.remove(wrapper);
		}
	}
	public void detachState(State state){
		detachState(state, true);
	}
	
	public ArrayList<State> getActiveStates(){ return null; }
}
