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

public class Button extends Component {
	public static final Color HIGHLIGHT = new Color(.5f, .5f, .5f, 1);
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont bitMapFont;
	private Vector2 textPosition;
	
	private String label;
	private Color color;
	private Color textColor = Color.MAGENTA;
	
	private boolean highlighted = false;
	private float padding = 5;
	
	Texture texture;
	
	public Button(String newLabel, Vector2 newSize, Vector2 newPosition, Color newColor) {
		setLabel(newLabel);
		setSize(newSize); 
		setPosition(newPosition);
		setColor(newColor);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("consola.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		bitMapFont = generator.generateFont(parameter);
		
		textPosition = new Vector2((this.getPosition().x + parameter.size/2), 
									this.getPosition().y + ((this.getSize().y/2) + parameter.size/2));
	}
	
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeType.Filled);
		if(highlighted){
			shapeRenderer.setColor(this.getColor().cpy().add(HIGHLIGHT));
		}else{
			shapeRenderer.setColor(this.getColor());
		}
		shapeRenderer.rect(this.getPosition().x + padding, this.getPosition().y + padding, this.getSize().x, this.getSize().y);
		shapeRenderer.end();
		
		batch.begin();
			bitMapFont.setColor(textColor);
			bitMapFont.draw(batch, label, textPosition.x, textPosition.y);;
		batch.end();
	}
	
	public void dispose() {
		generator.dispose();
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
	
	public Vector2 getTextPosition()
	{ return textPosition; }

	public void setTextPosition(float x, float y)
	{ this.textPosition = new Vector2(x,y); }

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

}