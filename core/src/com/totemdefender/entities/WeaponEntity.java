package com.totemdefender.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.totemdefender.TotemDefender;
import com.totemdefender.player.Player;
import com.totemdefender.states.BattleState;

public class WeaponEntity extends Entity {	
	public enum WeaponType {
		Cannon, Catapult, Ballista
	}

	public static final float CHARGE_RATE = 1/1000f;     //Speed at which the charge meter increases
	public static final float ROTATION = 1f;  //Degrees the weapon will rotate
	public static final float VELOCITY = 1600f;
	
	protected float projectileVelocity;
	protected float currentRate = CHARGE_RATE; //Current charge rate as it will change as charge changes.
	protected float charge = 0; //0 <= charge <= 1
	protected boolean hasFilled = false; //True when the charge meter fills
	protected boolean started = false; //True the first time player presses spacebar on his turn
	protected boolean completed = false; //True when the player releases spacebar or meter falls back to 0
	protected Vector2 fireDirection;
	protected Vector2 barrelPos, origin;
	protected int flip;
	protected ProjectileEntity projectile;
	
	protected String weaponSprite="cannon.png";
	protected float barrelPosX=95,barrelPosY=20;
	
	public WeaponEntity(Player owner){
		super(owner);
		
		projectileVelocity = VELOCITY * TotemDefender.WORLD_TO_BOX;
		flip = (owner.getID() == 1) ? 1 : -1;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		super.render(batch, shapeRenderer);
		
		if(TotemDefender.DEBUG){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(1, 0, 0, 1);
			shapeRenderer.circle(getPosition().x + getSprite().getOriginX(), getPosition().y + getSprite().getOriginY(), 5);
			shapeRenderer.setColor(1, 1, 1, 1);
			shapeRenderer.circle(getPosition().x + barrelPos.x, getPosition().y + barrelPos.y, 5);
			shapeRenderer.line(getPosition().x + barrelPos.x, getPosition().y + barrelPos.y, getPosition().x + barrelPos.x + fireDirection.x * 10, getPosition().y + barrelPos.y + fireDirection.y * 10);
			shapeRenderer.end();
		}
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);
		
		if(projectile != null){
			if(projectile.shouldDelete()){
				game.destroyEntity(projectile);
				projectile.setShouldDelete(false);
				projectile = null;
			}
			return; //Dont update while there still exists a projectile.
		}
		
		doCharge(game);
	}
	
	protected void doCharge(TotemDefender game){
		if(completed)
			return; //Don't bother with the update if completed.
		
		for(BattleState state : game.getStateManager().getAttachedState(BattleState.class)){
			if(state.getPlayerTurn().getID() == owner.getID()){
				if(state.spaceIsDown()){
					started = true;
					
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
				}else if(started){
					completed = true;
				}
	
				if(charge > 1)
					charge = 1;
				if(charge < 0)
					charge = 0;
				
				if(hasFilled && charge == 0)
					completed = true;
				
				if(completed)
					{game.setSound("sounds/Cannon/Cannon.mp3");game.getSound().play();fireProjectile(game);}
			}
		}
	}
	
	@Override
	public void spawn(TotemDefender game) {
		if( origin == null) throw new NullPointerException("Cannot spawn WeaponEntity, origin is not set");
		
		Texture weaponTexture = game.getAssetManager().get(weaponSprite, Texture.class);
		setSprite(new Sprite(weaponTexture));
		
		float aspectRatio = getSprite().getWidth()/getSprite().getHeight(); //Get aspect ratio to maintain for scaling
		float scale = 1/20f; //Relative to screen;
		
		getSprite().setSize(getSprite().getWidth() * scale * aspectRatio,
							getSprite().getHeight() * scale * aspectRatio);
		
		float hw = getSprite().getWidth()/2;
		float hh = getSprite().getHeight()/2;
		float xPos = (-TotemDefender.V_WIDTH/2) * TotemDefender.STACK_LOCATION + 200;
		float yPos = -TotemDefender.V_HEIGHT/2 + TotemDefender.GROUND_HEIGHT;
		
		if(owner.getID() == 2){
			xPos = -xPos; //Put it on the right side if its player 2
			getSprite().flip(true, false);
			
		}
			getSprite().setOrigin(origin.x, origin.y); //This is based on the logical rotation point on the cannon sprite
		
		
		getSprite().setPosition(xPos - hw, yPos);
		

		barrelPos = new Vector2(getSprite().getOriginX() + barrelPosX * flip, getSprite().getOriginY() + barrelPosY);
		fireDirection = new Vector2(.5f, 0);
		fireDirection.nor();
		if(owner.getID() == 2){
			fireDirection.x *= -1;
		}
		
		isSpawned = true;
	}
	
	
	public void fireProjectile(TotemDefender game){
		fireProjectile(game, ProjectileEntity.RADIUS);
	}
	
	public void fireProjectile(TotemDefender game, float radius){
		float vel = projectileVelocity * charge;
		projectile = new ProjectileEntity(owner, getPosition().add(barrelPos));
		projectile.setRadius(radius);
		projectile.spawn(game);
		game.addEntity(projectile, TotemDefender.PROJECTILE_DEPTH);
		
		projectile.getBody().applyForce(fireDirection.cpy().scl(vel), projectile.getBody().getWorldCenter(), true);
	}
	
	public float getCharge(){
		return charge;
	}
	
	public boolean chargeStarted(){
		return started;
	}
	
	public void resetCharge(){
		started = false;
		charge = 0;
		completed = false;
		hasFilled = false;
		currentRate = CHARGE_RATE;
	}
	
	public void rotateUp(){
		if(!isSpawned()) return;
		float pX = getSprite().getOriginX();
		float pY = getSprite().getOriginY();
		barrelPos.sub(pX, pY);
		barrelPos.rotate(ROTATION * flip);
		barrelPos.add(pX, pY);
		fireDirection.rotate(ROTATION * flip);
		getSprite().rotate(ROTATION * flip);
	}
	
	public void rotateDown(){
		if(!isSpawned()) return;
		float pX = getSprite().getOriginX();
		float pY = getSprite().getOriginY();
		barrelPos.sub(pX, pY);
		barrelPos.rotate(-ROTATION * flip);
		barrelPos.add(pX, pY);
		fireDirection.rotate(-ROTATION * flip);
		getSprite().rotate(-ROTATION * flip);
	}
	
	public boolean isCompleted(){ return completed; }
	
	public ProjectileEntity getProjectile(){
		return projectile;
	}

}
