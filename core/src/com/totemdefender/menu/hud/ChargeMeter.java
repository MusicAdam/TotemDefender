package com.totemdefender.menu.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.menu.Component;

public class ChargeMeter extends Component{
	private static final Color chargeBG = new Color(.01f, .5f, .01f, 1); 
	private static final Color chargeFG = new Color(.8f, 1f, .8f, 1); 
	WeaponEntity weapon;
	
	public ChargeMeter(WeaponEntity weapon){
		this.weapon = weapon;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		float spriteHeight = weapon.getSprite().getHeight();
		float width = weapon.getSprite().getWidth();
		float flip = (weapon.getOwner().getID() == 1) ? 1 : -1;
		float height = 6;
		float padding = 4;
		
		if(weapon.chargeStarted()){
			Vector2 weaponPos = TotemDefender.Get().worldToScreen(weapon.getPosition());
			float xPos = weaponPos.x - width/2 * flip;
			float yPos = weaponPos.y + spriteHeight/2 +padding/2;
			
			Color fg = new Color(chargeFG.r, chargeFG.g, chargeFG.b, weapon.getCharge());
			
			TotemDefender.EnableBlend();
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(chargeBG);
			shapeRenderer.rect(xPos - padding/2* flip, yPos, width * flip + padding* flip , height + padding);
			shapeRenderer.setColor(fg);
			shapeRenderer.rect(xPos + padding/2* flip , yPos + padding/2, width * weapon.getCharge() * flip - padding/2 * flip, height);
			shapeRenderer.end();
			TotemDefender.DisableBlend();
		}
	}

}
