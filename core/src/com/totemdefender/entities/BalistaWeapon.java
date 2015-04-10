package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.menu.ScoreLine;
import com.totemdefender.player.Player;
import com.totemdefender.states.BattleState;
import com.totemdefender.TotemDefender;

public class BalistaWeapon extends WeaponEntity {
	
	
	//private ProjectileEntity projectile2;
	//private Vector2 barrelPos2;
	//private Vector2 fireDirection2;
	
	
	private Texture bandTexture;
	private Vector2 bandPosition;
	private float rotation, bandWidth, bandHeight,pull;
	private float maxRotation = 30;
	private float minRotation = -90;
	private boolean rotationLocked = false;
	private float originalPull;
	private boolean doSwing = false;
	private float radius; 
	private float maxPull=40;
	private float minPull=-maxPull;
	private boolean restored=false;

	public BalistaWeapon(Player owner) {
		super(owner);
		
		rotation=15;
		pull=0;
		radius=projectile.RADIUS;
	
	}
	
	
	@Override
	public void update(TotemDefender game){
		
		
		/*if(projectile2 != null){
			if(projectile2.shouldDelete()){
				game.destroyEntity(projectile2);
				projectile2.setShouldDelete(false);
				
				if(!(projectile.getContactedBlock() && projectile2.getContactedBlock())){
					owner.addScore(ScoreLine.ScoreType.Miss, -(Player.MISS_SCORE));
				}
				
				projectile2 = null;
				
				
			
			
			
			}
			return; //Dont update while there still exists a projectile.
		}*/
		
		super.update(game);
		
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		
		
		/*if(TotemDefender.DEBUG){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 1, 1, 1);
			shapeRenderer.circle(getPosition().x + barrelPos2.x, getPosition().y + barrelPos2.y, 5);
			shapeRenderer.line(getPosition().x + barrelPos2.x, getPosition().y + barrelPos2.y, getPosition().x + barrelPos2.x + fireDirection2.x * 10, getPosition().y + barrelPos2.y + fireDirection2.y * 10);
			shapeRenderer.end();
		}*/
		
		
		
		
			batch.begin();
			if(owner.getID() == 1){
				batch.draw(bandTexture, getPosition().x + bandPosition.x, getPosition().y + bandPosition.y, bandWidth, bandHeight * .31f, bandWidth+pull, bandHeight, 1, 1, rotation * flip, 0, 0, bandTexture.getWidth(), bandTexture.getHeight(), (flip == -1) ? true : false, false);
			}else{
				batch.draw(bandTexture, getPosition().x + bandPosition.x, getPosition().y + bandPosition.y, 0, bandHeight * .31f, bandWidth+pull, bandHeight, 1, 1, rotation * flip, 0, 0, bandTexture.getWidth(), bandTexture.getHeight(), (flip == -1) ? true : false, false);
			}
			batch.end();
			
			super.render(batch, shapeRenderer);
			
	}
	
	
public void spawn(TotemDefender game){
		
		weaponSprite="slingshot1.png";	
		
			
			
			if(owner.getID()==1){
				origin=new Vector2(150,100);
			}
			else
				origin=new Vector2(358,230);
			
			barrelPosX=0;
			barrelPosY=20;

			super.spawn(game);
			
			fireDirection.rotate(25);
			System.out.println("size: " + this.getSprite().getHeight() + "..." + this.getSprite().getWidth());
			this.getSprite().setSize(300f, 150f);
			System.out.println(this.getSprite().getX()+ "..." + this.getSprite().getY());
			this.getSprite().setPosition(-450, -390);
		/*	barrelPos2=barrelPos.cpy();
			barrelPos2.x-=10*flip;
			barrelPos2.y-=10;
			
			fireDirection2=fireDirection.cpy();
			
			if(owner.getID()==1)
			fireDirection2.rotate(30);
			
			else
				fireDirection2.rotate(-30);*/
			
			
			
			//band stuff
			
			bandTexture = game.getAssetManager().get("band1.png", Texture.class);
					

					bandWidth = getSprite().getWidth()/4;
					bandHeight = getSprite().getHeight()/4;
					
					
					
					
					if(owner.getID() == 2){
						bandPosition = origin.cpy();
						
					}
					
					else
						bandPosition = origin.cpy().sub(new Vector2(bandWidth, 0));
					
					
					
					System.out.println("Ballista was spawned");
					
				}
			
			
		


public void fireProjectile(TotemDefender game, float radius){

	super.fireProjectile(game,radius);
	
	/*float vel = projectileVelocity * charge;
	projectile2 = new ProjectileEntity(owner, getPosition().add(barrelPos2));
	projectile2.setRadius(radius);
	projectile2.spawn(game);
	game.addEntity(projectile2, TotemDefender.PROJECTILE_DEPTH);
	
	
	projectile2.getBody().applyForce(fireDirection2.cpy().scl(vel), projectile2.getBody().getWorldCenter(), true);
*/
	
	
	
}




public void rotateUp(){


	if(rotationLocked) return;
	if(rotation < minRotation) return;		
	
	float pX = getSprite().getOriginX();
	float pY = getSprite().getOriginY();
	barrelPos.sub(pX, pY);
	barrelPos.rotate(-ROTATION*flip);
	barrelPos.add(pX, pY);
	fireDirection.rotate(-ROTATION *flip);
	rotation -= ROTATION ;
	

	/*barrelPos2.sub(pX, pY);
	barrelPos2.rotate(ROTATION * flip);
	barrelPos2.add(pX, pY);
	fireDirection2.rotate(ROTATION * flip);*/
	
}

public void rotateDown(){
	
	if(rotationLocked) return;
	if(rotation > maxRotation) return;
	
	float pX = getSprite().getOriginX();
	float pY = getSprite().getOriginY();
	barrelPos.sub(pX, pY);
	barrelPos.rotate(ROTATION*flip);
	barrelPos.add(pX, pY);
	fireDirection.rotate(ROTATION*flip);
	rotation += ROTATION ;
	
	/*barrelPos2.sub(pX, pY);
	barrelPos2.rotate(-ROTATION * flip);
	barrelPos2.add(pX, pY);
	fireDirection2.rotate(-ROTATION * flip);*/
}





@Override
protected void doCharge(TotemDefender game){
	if(completed)
		return; //Don't bother with the update if completed.
	
	for(BattleState state : game.getStateManager().getAttachedState(BattleState.class)){
		if(state.getPlayerTurn().getID() == owner.getID()){
			if(state.spaceIsDown()){
				if(!started){
					started = true;
					rotationLocked = true;
					originalPull = pull;
					restored=false;
				}
				
				//Make the charge speed up as the charge increases
				if(hasFilled){
					currentRate -= CHARGE_RATE;
				}else{
					currentRate += CHARGE_RATE;
				}
				
				if(charge < 1 && !hasFilled){
					charge += currentRate;
				}else{
					hasFilled = true;
					if(charge > 0){
						charge -= currentRate;
					}
				}					

				//Set the rotation relative to charge to make swing look nice
				pull = originalPull + ((maxPull - originalPull) * charge);
			}else if(started){
				doSwing = true;
			}
			
			if(charge > 1)
				charge = 1;
			if(charge < 0)
				charge = 0;
			
			if(hasFilled && charge == 0)
				completed = true;
			
			if(doSwing){
				if(pull == minPull){
					doSwing = false;
					completed = true;
				}else{
					pull += (minPull-pull)/Math.abs(minPull-pull)*10;
					if(pull < minPull){
						pull = minPull;
					}
				}
			}
			
			if(completed){
				fireProjectile(game, radius);
				rotationLocked = false;
				pull=originalPull;
			}
			
		
		}
	}
}


}
