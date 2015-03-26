package com.totemdefender.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.totemdefender.TotemDefender;

public class ScissorRegion extends Container{
	private Rectangle scissors = new Rectangle();
	
	public ScissorRegion(Container parent){
		super(parent);
	}
	
	public ScissorRegion(){
		super(null);
	}
	
	@Override
	public void validate(){
		Rectangle rect = new Rectangle(rectangle);
		rect.setPosition(getWorldPosition());
		ScissorStack.calculateScissors(TotemDefender.Get().getMenuCamera(), new Matrix4(), rect, scissors);
		
		super.validate();
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		boolean shouldPop = ScissorStack.pushScissors(new Rectangle(scissors));

		super.render(batch, shapeRenderer);
		
		if(shouldPop)
			ScissorStack.popScissors();

	}
}
