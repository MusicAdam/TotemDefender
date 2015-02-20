package com.totemdefender.states;

import com.totemdefender.TotemDefender;
import com.totemdefender.states.StateManager.Event;

public class StateListener{
	public Event event;
	public Class klass;
	public void callback(TotemDefender game, State state){}
	
	public StateListener(Class klass, Event evt){
		this.klass = klass;
		this.event = evt;
	}
}
