package com.totemdefender.menu.buildmenu;
import java.util.ArrayList;

import com.totemdefender.Level;
import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.entities.PedestalEntity;
import com.totemdefender.entities.TotemEntity;
import com.totemdefender.entities.blocks.BlockEntity;
import com.totemdefender.entities.blocks.RectangleBlockEntity;
import com.totemdefender.entities.blocks.SquareBlockEntity;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.menu.Button;
import com.totemdefender.menu.Container;
import com.totemdefender.menu.NavigableContainer;
import com.totemdefender.menu.hud.Grid;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu extends NavigableContainer {
	private Player owner;
	private Level level;
	
	private BlockSelector squareSelector;
	private BlockSelector rectangleSelector;
	private boolean placingTotem = false;
	
	public BuildMenu(TotemDefender game, Level level, Player owner) {
		super(null);
		
		this.owner = owner;
		this.level = level;
		
		squareSelector = new BlockSelector(this, owner, BlockEntity.Shape.Square);
		squareSelector.setBlockMaterial(game, BlockEntity.Material.Stone);
		squareSelector.setPosition(0, squareSelector.getHeight() + 20);
		squareSelector.create(game);
		
		
		rectangleSelector = new BlockSelector(this, owner, BlockEntity.Shape.Rectangle);
		rectangleSelector.setBlockMaterial(game, BlockEntity.Material.Stone);
		rectangleSelector.create(game);
		connectComponents(squareSelector, rectangleSelector);
		
		attachKeyboardListeners(owner);
	};
}