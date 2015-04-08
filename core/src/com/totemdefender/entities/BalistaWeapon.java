package com.totemdefender.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.menu.ScoreLine;
import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class BalistaWeapon extends WeaponEntity {
	
	
	private ProjectileEntity projectile2;
	private Vector2 barrelPos2;
	private Vector2 fireDirection2;

	public BalistaWeapon(Player owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	
	}
	
	
	@Override
	public void update(TotemDefender game){
		
		
		if(projectile2 != null){
			if(projectile2.shouldDelete()){
				game.destroyEntity(projectile2);
				projectile2.setShouldDelete(false);
				
				if(!(projectile.getContactedBlock() && projectile2.getContactedBlock())){
					owner.addScore(ScoreLine.ScoreType.Miss, -(Player.MISS_SCORE));
				}
				
				projectile2 = null;
				
				
			
			
			
			}
			return; //Dont update while there still exists a projectile.
		}
		
		super.update(game);
		
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		super.render(batch, shapeRenderer);
		
		if(TotemDefender.DEBUG){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 1, 1, 1);
			shapeRenderer.circle(getPosition().x + barrelPos2.x, getPosition().y + barrelPos2.y, 5);
			shapeRenderer.line(getPosition().x + barrelPos2.x, getPosition().y + barrelPos2.y, getPosition().x + barrelPos2.x + fireDirection2.x * 10, getPosition().y + barrelPos2.y + fireDirection2.y * 10);
			shapeRenderer.end();
		}
	}
	
	
public void spawn(TotemDefender game){
		
		weaponSprite="cannon.png";	
		
			
			
			if(owner.getID()==1){
				origin=new Vector2(33,30);
			}
			else
				origin=new Vector2(98,30);
			
			barrelPosX=95;
			barrelPosY=20;
			
			super.spawn(game);
			
			barrelPos2=barrelPos.cpy();
			barrelPos2.x-=10*flip;
			barrelPos2.y-=10;
			
			fireDirection2=fireDirection.cpy();
			
			if(owner.getID()==1)
			fireDirection2.rotate(30);
			
			else
				fireDirection2.rotate(-30);
			
			
			
			
			
		}


public void fireProjectile(TotemDefender game, float radius){

	super.fireProjectile(game,radius);
	
	float vel = projectileVelocity * charge;
	projectile2 = new ProjectileEntity(owner, getPosition().add(barrelPos2));
	projectile2.setRadius(radius);
	projectile2.spawn(game);
	game.addEntity(projectile2, TotemDefender.PROJECTILE_DEPTH);
	
	
	projectile2.getBody().applyForce(fireDirection2.cpy().scl(vel), projectile2.getBody().getWorldCenter(), true);

	
	
	
}




public void rotateUp(){

	super.rotateUp();
	
	
	float pX = getSprite().getOriginX();
	float pY = getSprite().getOriginY();
	barrelPos2.sub(pX, pY);
	barrelPos2.rotate(ROTATION * flip);
	barrelPos2.add(pX, pY);
	fireDirection2.rotate(ROTATION * flip);
	
}

public void rotateDown(){
	
	super.rotateDown();
	
	float pX = getSprite().getOriginX();
	float pY = getSprite().getOriginY();
	barrelPos2.sub(pX, pY);
	barrelPos2.rotate(-ROTATION * flip);
	barrelPos2.add(pX, pY);
	fireDirection2.rotate(-ROTATION * flip);
}



}
