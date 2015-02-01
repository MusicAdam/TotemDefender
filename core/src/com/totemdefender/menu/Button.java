package com.totemdefender.menu;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Button extends Component {
	private String label;
	private Color color;
	
	public Button(String newLabel, Vector2 newSize, Vector2 newPosition, Color newColor) {
		setLabel(newLabel);
		setSize(newSize); 
		setPosition(newPosition);
		setColor(newColor);
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(this.getColor());
		shapeRenderer.rect(this.getPosition().x, this.getPosition().y, this.getSize().x, this.getSize().y);
		shapeRenderer.end();
	}
	
	public boolean onButtonArea(int x, int y) {
		float positionX = getPosition().x;
		float positionY = getPosition().y;
		float halfSizeX = (getSize().x / 2);
		float halfSizeY = (getSize().y / 2);
		
		float minX = positionX - halfSizeX;
		float minY = positionY - halfSizeY;
		float maxX = positionX + halfSizeX;
		float maxY = positionY + halfSizeY;
		
		if((x >= minX && x <= maxX) && (y >= minY && y <= maxY)) 
		{ return true; }
		
		return false;
	}
	
	public String getLabel() 
	{ return label; }
	
	public void setLabel(String label) 
	{ this.label = label; }

	public Color getColor()
	{ return color; }

	public void setColor(Color color)
	{ this.color = color; }

}