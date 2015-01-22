package com.totemdefender;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

//Listens for Collisions with "I collided with other" logic or "Other Collided with me"
public interface CollisionListener {
	public void beginContact(Fixture other, Contact contact);
	public void endContact(Fixture other, Contact contact);
}
