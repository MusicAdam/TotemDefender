package com.totemdefender.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.entities.WeaponEntity;
import com.totemdefender.player.Player;
import com.totemdefender.TotemDefender;

public class PreGameMenu extends NavigableContainer {
	private Player owner;
	private TextEntry userName;
	private Button instruct;
	private Button ready;
	private Button weapon1, weapon2, weapon3;
	private Boolean weaponSelected=false;
	
	public PreGameMenu(TotemDefender game, final Player owner) {
		super(null,ConnectionType.Vertical);
		this.owner = owner;

		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/6),(TotemDefender.V_HEIGHT/1.5f)/4.65517f); //4.6.. is the apsect ratio of the button texture
		float padding = 10;
		float side = padding;
		int player = owner.getID();
		
		if(owner.getID() == 2) {
			side = TotemDefender.V_WIDTH - buttonSize.x - padding;
			instruct = new Button(this, "", 
					new Vector2(((TotemDefender.V_WIDTH * (2.0f/3.0f)) - 20), buttonSize.y*5), 
					new Vector2(buttonSize.x + padding, TotemDefender.V_HEIGHT - (buttonSize.y)*5 - padding), null);
			instruct.setBackgroundTexture(game, "keyboard.png");
			instruct.setBackgroundHighlightTexture(game, "keyboard.png");
			instruct.create(game);
		}
		
		userName = new TextEntry(this, owner);
		userName.setText("Player " + player);
		userName.setSize(buttonSize);
		userName.setPosition(new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y + padding)));
		userName.setFont("hud_small.ttf");
		userName.setTextOffset(buttonSize.x/2 - userName.getTextBounds().width/2, buttonSize.y/2 - userName.getTextBounds().height/2 + 5);
		userName.create(game);

		weapon1 = new Button(this, "Cannon", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*2 - padding), Color.RED){
			@Override
			public boolean onClick(){
				if(owner.getID() == 1){
					changePlayer1Weapon(WeaponEntity.WeaponType.Cannon);
				}else{
					changePlayer2Weapon(WeaponEntity.WeaponType.Cannon);					
				}
				return true;
			}
		};
		weapon1.setFont("hud_small.ttf");
		weapon1.setTextOffset(buttonSize.x/2 - weapon1.getTextBounds().width/2, buttonSize.y/2 - weapon1.getTextBounds().height/2 + 5);
		weapon1.create(game);
	
		weapon2 = new Button(this, "Catapult", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*3 - padding), Color.BLUE){
			@Override
			public boolean onClick(){				
				if(owner.getID() == 1){
					changePlayer1Weapon(WeaponEntity.WeaponType.Catapult);
				}else{
					changePlayer2Weapon(WeaponEntity.WeaponType.Catapult);					
				}
				return true;
			}
		};
		weapon2.setFont("hud_small.ttf");
		weapon2.setTextOffset(buttonSize.x/2 - weapon2.getTextBounds().width/2, buttonSize.y/2 - weapon2.getTextBounds().height/2 + 5);
		weapon2.create(game);
		
		weapon3 = new Button(this, "Ballista", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*4 - padding), Color.ORANGE){
			@Override
			public boolean onClick(){
				if(owner.getID() == 1){
					changePlayer1Weapon(WeaponEntity.WeaponType.Ballista);
				}else{
					changePlayer2Weapon(WeaponEntity.WeaponType.Ballista);					
				}
				return true;
			}
		};
		weapon3.setFont("hud_small.ttf");
		weapon3.setTextOffset(buttonSize.x/2 - weapon3.getTextBounds().width/2, buttonSize.y/2 - weapon3.getTextBounds().height/2 + 5);	
		weapon3.create(game);
		
		ready = new Button(this, "READY", buttonSize, new Vector2(side, TotemDefender.V_HEIGHT - (buttonSize.y)*5 - padding), Color.GREEN){
			@Override
			public boolean onClick(){
				weaponSelected = true;
				return true;
			}
		};
		ready.setFont("hud_small.ttf");
		ready.setTextOffset(buttonSize.x/2 - ready.getTextBounds().width/2, buttonSize.y/2 - ready.getTextBounds().height/2 + 5);
		ready.create(game);

		connectComponentsVertically(weapon1, weapon2);
		connectComponentsVertically(weapon2, weapon3);
		connectComponentsVertically(weapon3, ready);
		
		attachKeyboardListeners(owner);
	}
	
	public boolean hasSelected(){
		return weaponSelected;
	}

	public TextEntry getUserName() {
		return userName;
	}

	public void setUserName(TextEntry userName) {
		this.userName = userName;
	}
	
	public void changePlayer1Weapon(WeaponEntity.WeaponType type){		
		TotemDefender.Get().getLevel().changePlayer1Weapon(type);
	}
	
	public void changePlayer2Weapon(WeaponEntity.WeaponType type){		
		TotemDefender.Get().getLevel().changePlayer2Weapon(type);
	}
}