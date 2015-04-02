package com.totemdefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.totemdefender.player.Player;
import com.totemdefender.states.BattleState;
import com.totemdefender.TotemDefender;

public class CatapultWeapon extends WeaponEntity {
	private Texture armTexture;
	private Vector2 armPosition;
	private float rotation, armWidth, armHeight;
	private float maxRotation = 15;
	private float minRotation = -90;
	private boolean rotationLocked = false;
	private float originalRotation;
	private boolean doSwing = false;
	private float radius; 

	public CatapultWeapon(Player owner) {
		super(owner);
		rotation = maxRotation;
		projectileVelocity = 3500 * TotemDefender.WORLD_TO_BOX;
		radius = 12f;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		batch.begin();
		if(owner.getID() == 1){
			batch.draw(armTexture, getPosition().x + armPosition.x, getPosition().y + armPosition.y, armWidth, armHeight * .31f, armWidth, armHeight, 1, 1, rotation * flip, 0, 0, armTexture.getWidth(), armTexture.getHeight(), (flip == -1) ? true : false, false);
		}else{
			batch.draw(armTexture, getPosition().x + armPosition.x, getPosition().y + armPosition.y, 0, armHeight * .31f, armWidth, armHeight, 1, 1, rotation * flip, 0, 0, armTexture.getWidth(), armTexture.getHeight(), (flip == -1) ? true : false, false);
		}
		batch.end();
		
		super.render(batch, shapeRenderer);
	}
	
	@Override
	public void spawn(TotemDefender game){
		armTexture = game.getAssetManager().get("catapult_arm.png", Texture.class);
		Texture weaponTexture = game.getAssetManager().get("catapult_body.png", Texture.class);
		setSprite(new Sprite(weaponTexture));
		
		float aspectRatio = getSprite().getWidth()/getSprite().getHeight(); //Get aspect ratio to maintain for scaling
		float scale = 1/20f; //Relative to screen;
		
		getSprite().setSize(getSprite().getWidth() * scale * aspectRatio,
							getSprite().getHeight() * scale * aspectRatio);
		armWidth = getSprite().getWidth() * .57f;
		armHeight = getSprite().getHeight() * .29f;
		
		if(owner.getID() == 2){
			origin = new Vector2(37, 35); //This is based on the logical rotation point on the cannon sprite
			getSprite().flip(true, false);
			armPosition = origin.cpy();
		}else{
			origin = new Vector2(96, 35);
			armPosition = origin.cpy().sub(new Vector2(armWidth, 0));
		}
		
		barrelPos = new Vector2(origin.x - 65 * flip, origin.y + 5);
		fireDirection = new Vector2(-.1f, 1);
		fireDirection.nor();
		if(owner.getID() == 2){
			fireDirection.x *= -1;
		}
		super.spawn(game);
	}
	
	@Override
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
	}
	
	@Override
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
						originalRotation = rotation;
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
					rotation = originalRotation + ((maxRotation - originalRotation) * charge);
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
					if(rotation == originalRotation){
						doSwing = false;
						completed = true;
					}else{
						rotation += (originalRotation-rotation)/Math.abs(originalRotation-rotation)*10;
						if(rotation < originalRotation){
							rotation = originalRotation;
						}
					}
				}
				
				if(completed){
					fireProjectile(game, radius);
					rotationLocked = false;
				} 
			}
		}
	}

}
