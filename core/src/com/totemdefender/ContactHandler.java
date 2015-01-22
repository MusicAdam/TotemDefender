package com.totemdefender;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactHandler implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB(); 
		
		resolveContacts(fixA, fixB, contact, true);
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB(); 
		
		resolveContacts(fixA, fixB, contact, false);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// Unused
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// Unused
		
	}
	
	private void resolveContacts(Fixture fixA, Fixture fixB, Contact contact, boolean begin){
		CollisionListener listA = (CollisionListener)fixA.getUserData();
		CollisionListener listB = (CollisionListener)fixB.getUserData();
		
		if(listA == null && listB == null)
			return; //Fixture's user data does not reference a ContactListener, so don't process contact
		
		//TODO: I think the default box2d filter will not report filtered contacts, should test
		//This says anything without a filter contacts everything
		//if(fixA.getFilterData() == null || fixB.getFilterData() == null){
			if(begin){
				if(listA != null)
					listA.beginContact(fixB, contact);
				
				if(listB != null)
					listB.beginContact(fixA, contact);
			}else{
				if(listA != null)
					listA.endContact(fixB, contact);
				if(listB != null)
					listB.endContact(fixA, contact);	
			}
			return;
		//}
		/*
		//Don't attempt filtering if both fixture don't have a filter
		if(fixA.getFilterData() == null && fixB.getFilterData() == null)
			return;
		
		//fixture A collides with fixture B
		if((fixA.getFilterData().categoryBits & fixB.getFilterData().maskBits) != 0){
			if(begin){
				if(listA != null)
					listA.beginContact(fixB, contact);
			}else{
				if(listA != null)
					listA.endContact(fixB, contact);
			}
		}
		
		//fixture B collides with fixture B
		if((fixB.getFilterData().categoryBits & fixA.getFilterData().maskBits) != 0){
			if(begin){
				if(listB != null)
					listB.beginContact(fixA, contact);
			}else{
				if(listB != null)
					listB.endContact(fixA, contact);
			}
		}
		*/
	}
}
