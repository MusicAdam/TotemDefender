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
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.states.BattleState;

public class WeaponEntity extends Entity {	
	
	public static final float WEAPON_LOCATION = 3/4f; //The weapon will be this proportion away from the side of the screen.
	public static final float CHARGE_RATE = 1/1000f;     //Speed at which the charge meter increases
	
	private final float projectileVelocity;
	private float currentRate = CHARGE_RATE; //Current charge rate as it will change as charge changes.
	private float charge = 0; //0 <= charge <= 1
	private boolean hasFilled = false; //True when the charge meter fills
	private boolean started = false; //True the first time player presses spacebar on his turn
	private boolean completed = false; //True when the player releases spacebar or meter falls back to 0
	private Vector2 fireDirection;
	private Vector2 barrelPos;
	
	public WeaponEntity(Player owner){
		super(owner);
		
		projectileVelocity = 500 * TotemDefender.WORLD_TO_BOX;
		barrelPos = new Vector2(50, 25);
		fireDirection = new Vector2(.5f, .4f);
		fireDirection.nor();
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		super.render(batch, shapeRenderer);
		
		//System.out.println( getPosition().add(barrelPos.cpy().add(fireDirection.cpy().scl(10))));
		System.out.println(getPosition().add(barrelPos));
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.circle(getPosition().x + barrelPos.x, getPosition().y + barrelPos.y, 5);
		shapeRenderer.line(getPosition().x + barrelPos.x, getPosition().y + barrelPos.y, getPosition().x + barrelPos.x + fireDirection.x * 10, getPosition().y + barrelPos.y + fireDirection.y * 10);
		shapeRenderer.end();
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);
		
		if(completed)
			return; //Don't bother with the update if completed.
		
		for(BattleState state : game.getStateManager().getAttachedState(BattleState.class)){
			if(state.getPlayerTurn() == owner){
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
					fireProjectile(game);
			}
		}
	}
	
	@Override
	public void spawn(TotemDefender game) {
		Texture weaponTexture = game.getAssetManager().get("cannon.png", Texture.class);
		setSprite(new Sprite(weaponTexture));
		
		float aspectRatio = getSprite().getWidth()/getSprite().getHeight(); //Get aspect ratio to maintain for scaling
		float scale = 1/10f; //Relative to screen;
		
		getSprite().setSize(getSprite().getWidth() * scale * aspectRatio,
							getSprite().getHeight() * scale * aspectRatio);
		
		float hw = getSprite().getWidth()/2;
		float hh = getSprite().getHeight()/2;
		float xPos = (-game.getScreenWidth()/2) * WEAPON_LOCATION;
		float yPos = -game.getScreenHeight()/2 + 20 + hh; //20 is hardcoded ground size
		
		if(owner.getID() == 2){
			xPos = -xPos; //Put it on the right side if its player 2
			getSprite().flip(true, false);
		}
		
		BodyDef weaponDef = new BodyDef();
		weaponDef.type = BodyType.StaticBody;
		weaponDef.position.set(xPos * TotemDefender.WORLD_TO_BOX, yPos * TotemDefender.WORLD_TO_BOX);
		
		Body body = game.getWorld().createBody(weaponDef);
		
		PolygonShape cannonShape = new PolygonShape();
		cannonShape.setAsBox((hw - 1) * TotemDefender.WORLD_TO_BOX, hh * TotemDefender.WORLD_TO_BOX);
		

		short categoryBits 	= (getOwner() == game.getPlayer1()) ? Entity.PLAYER1 : Entity.PLAYER2;
		short maskBits 			= (categoryBits == Entity.PLAYER1) ? Entity.PLAYER2 : Entity.PLAYER1;
		Fixture fix = body.createFixture(cannonShape, 0.0f);
		Filter filter = fix.getFilterData();
		filter.categoryBits = categoryBits;
		filter.maskBits = (short) (maskBits | Entity.GROUND);
		fix.setFilterData(filter);
		
		cannonShape.dispose();
		
		setBody(body);
		isSpawned = true;
	}
	
	public void fireProjectile(TotemDefender game){
		float vel = projectileVelocity * charge;
		ProjectileEntity prj = new ProjectileEntity(owner, getPosition().add(barrelPos));
		prj.spawn(game);
		game.addEntity(prj);
		
		prj.getBody().applyForce(fireDirection.cpy().scl(vel), prj.getBody().getWorldCenter(), true);
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

}
