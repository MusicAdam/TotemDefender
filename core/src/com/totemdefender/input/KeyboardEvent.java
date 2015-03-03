package com.totemdefender.input;

public class KeyboardEvent {

	public static final int KEY_DOWN 	= 1;
	public static final int KEY_UP		= 2;
	public static final int KEY_TYPED	= 3;
	
	public int event;
	public int key;
	public char character;
	
	public KeyboardEvent(int event){
		this.event = event;
		this.key = -1;
		character = '\0';
	}
	
	public KeyboardEvent(int event, int key){
		this.event = event;
		this.key = key;
	}
	
	public KeyboardEvent(int event, int key, char character){
		this.event = event;
		this.key = key;
		this.character = character;
	}
	
	public KeyboardEvent(){
		event = 0;
		key = -1;
		character = '\0';
	}
	
	public boolean callback(){ return false; }; //Should be overriden to perform an action
	
	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		
		KeyboardEvent evt = (KeyboardEvent)other;
		if(evt == null) return false; //Eclipse is complaining this is dead code, thats not true if other is not an instance of KeyboardEvent
		
		return (event == evt.event &&
				key == evt.key);
	}
}
