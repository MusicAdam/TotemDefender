package com.totemdefender.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;

public class Button extends Component {	
	private Label label;
	
	public Button(Menu parent, String labelText, Vector2 newSize, Vector2 newPosition, Color newColor) {
		super(parent);
		label = new Label(parent);
		label.setText(labelText);
		
		setSize(newSize); 
		setPosition(newPosition);
		setColor(newColor);
		setSelectable(true);
	}
	
	public Button(Menu parent){
		super(parent);
		label = new Label(parent);
		color = Color.BLACK;
	}
	
	@Override
	public void update(TotemDefender game){
		label.update(game);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		super.render(batch, shapeRenderer);
		label.render(batch, shapeRenderer);
	}
	
	public void dispose() {
	}
	
	public Label getLabel() 
	{ return label; }
	
	public void setLabel(Label label) 
	{ this.label = label; }
	
	public void setText(String text){
		label.setText(text);
	}
	
	@Override
	public void setPosition(Vector2 pos){
		super.setPosition(pos);
		alignLabelToCenter();
	}
	
	public void alignLabelToCenter(){
		label.setPosition(getPosition().x + getWidth()/2 - label.getWidth()/2, getPosition().y + getHeight()/2);
	}

}