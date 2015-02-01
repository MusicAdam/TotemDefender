package com.totemdefender.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Button extends Component {
<<<<<<< HEAD
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont bitMapFont = generator.generateFont(parameter); // font size 12 pixels
	private Vector2 textPosition;
	
	ShapeRenderer shapeMaker;
=======
>>>>>>> 655f50c78fd46ed875747aa7b422115755907437
	private String label;
	private Color color;
	
	//gdx.files.internal("C/Windows/Fonts/Consolas")
	public Button(String newLabel, Vector2 newSize, Vector2 newPosition, Color newColor) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/consola.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		bitMapFont = generator.generateFont(parameter); // font size 12 pixels
		
		setLabel(newLabel);
		setSize(newSize); 
		setPosition(newPosition);
		setColor(newColor);
<<<<<<< HEAD
		
		textPosition = new Vector2(this.getPosition().x + (this.getSize().x/2), this.getPosition().y + (this.getSize()).y);
		shapeMaker = new ShapeRenderer();
	}
	
	public void render(SpriteBatch batch) {
		
		batch.begin();
			bitMapFont.draw(batch, label, textPosition.x, textPosition.y);;
		batch.end();
		generator.dispose();
		
		shapeMaker.begin(ShapeType.Filled);
		shapeMaker.setColor(this.getColor());
		shapeMaker.rect(this.getPosition().x, this.getPosition().y, this.getSize().x, this.getSize().y);
		shapeMaker.end();
=======
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(this.getColor());
		shapeRenderer.rect(this.getPosition().x, this.getPosition().y, this.getSize().x, this.getSize().y);
		shapeRenderer.end();
>>>>>>> 655f50c78fd46ed875747aa7b422115755907437
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