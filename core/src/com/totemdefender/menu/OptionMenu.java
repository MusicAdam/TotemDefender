package com.totemdefender.menu;

import com.totemdefender.TotemDefender;

public class OptionMenu extends NavigableContainer{

	/*
	->Volume
	->Graphics
	->Brightness
	 */
	
	public OptionMenu(TotemDefender game) {
		super(null, ConnectionType.Vertical);
	}
}