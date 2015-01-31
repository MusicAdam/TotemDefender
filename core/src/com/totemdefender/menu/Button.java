package com.totemdefender.menu;
import com.badlogic.gdx.graphics.Color;
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

	@Override
	public void render() {
		// TODO Auto-generated method stub
	
	}
	
	public boolean onButtonArea(int x, int y) {
		float minX = getPosition().x - (getSize().x/2);
		float minY = getPosition().y - (getSize().y/2);
		float maxX = getPosition().x + (getSize().x/2);
		float maxY = getPosition().y + (getSize().y/2);
		
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