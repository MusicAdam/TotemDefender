package com.totemdefender.menu;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.totemdefender.TotemDefender;
import com.totemdefender.states.PostGameState;

public class PostGameMenu extends Container{
	public static Color winnerColor = Color.valueOf("74E247");
	public static Color loserColor = Color.valueOf("AF2B25");
	public static Color fontColor = new Color(.9f, .9f, .9f, 1);
	
	Button continueButton;
	Label winnerLabel;
	Color leftColor;
	Color rightColor;
	
	float scoreDisplayRatioH = .75f;
	float scoreDisplayRatioW = .5f;
	
	long updateRate = 0; //Time in ms for updates to take place
	long lastUpdate = 0;
	Queue<ScoreLine> p1ScoreLines;
	Queue<ScoreLine> p2ScoreLines;
	boolean newLine = false;
	float padding = 5;
	int[] lines;
	ScoreLine[] currentScoreLine;
	ScoreLine.ScoreType[] currentScoreType;
	PostGameState postGameState;
	
	public PostGameMenu(PostGameState postGameState){
		super(null);
		shouldSizeToContents(false);
		
		p1ScoreLines = new ConcurrentLinkedQueue<ScoreLine>();
		p2ScoreLines = new ConcurrentLinkedQueue<ScoreLine>();
		currentScoreLine = new ScoreLine[2];
		currentScoreType = new ScoreLine.ScoreType[2];
		lines = new int[]{0, 0};
		
		this.postGameState = postGameState;
	}
	
	@Override
	public void create(TotemDefender game){
		continueButton = new Button(this){
			@Override
			public boolean onClick(){
				postGameState.setShouldExit(true);
				return true;
			}
		};
		continueButton.setColor(null);
		continueButton.setFont("hud_small.ttf");
		continueButton.setText("End Game");
		continueButton.setBackgroundTexture(game, "ui/bar_tall.png");
		continueButton.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		continueButton.create(game);
		
		winnerLabel = new Label(this);
		winnerLabel.setText(game.getWinner().getName() + " wins!");
		winnerLabel.setTextColor(fontColor);
		winnerLabel.setFont("hud_large.ttf");
		winnerLabel.create(game);
		
		if(game.getWinner().getID() == 1){
			leftColor = winnerColor;
			rightColor = loserColor;
		}else{
			leftColor = loserColor;
			rightColor = winnerColor;
		}
		
		ScoreLine sumLine = new ScoreLine(0, ScoreLine.ScoreType.Destruction);;
		for(ScoreLine scoreLine : game.getPlayer1().getScore().getScoreLines(ScoreLine.ScoreType.Destruction)){
			sumLine.setValue(sumLine.getValue() + scoreLine.getValue());
		}
		p1ScoreLines.add(sumLine);

		sumLine = new ScoreLine(0, ScoreLine.ScoreType.Miss);
		for(ScoreLine scoreLine : game.getPlayer1().getScore().getScoreLines(ScoreLine.ScoreType.Miss)){
			sumLine.setValue(sumLine.getValue() + scoreLine.getValue());
		}
		p1ScoreLines.add(sumLine);

		sumLine = new ScoreLine(0, ScoreLine.ScoreType.Win);
		for(ScoreLine scoreLine : game.getPlayer1().getScore().getScoreLines(ScoreLine.ScoreType.Win)){
			sumLine.setValue(sumLine.getValue() + scoreLine.getValue());
		}
		p1ScoreLines.add(sumLine);
		p1ScoreLines.add(new ScoreLine(game.getPlayer1().getTotalScore(), ScoreLine.ScoreType.Total));

		sumLine = new ScoreLine(0, ScoreLine.ScoreType.Destruction);
		for(ScoreLine scoreLine : game.getPlayer2().getScore().getScoreLines(ScoreLine.ScoreType.Destruction)){
			sumLine.setValue(sumLine.getValue() + scoreLine.getValue());
		}
		p2ScoreLines.add(sumLine);

		sumLine = new ScoreLine(0, ScoreLine.ScoreType.Miss);
		for(ScoreLine scoreLine : game.getPlayer2().getScore().getScoreLines(ScoreLine.ScoreType.Miss)){
			sumLine.setValue(sumLine.getValue() + scoreLine.getValue());
		}
		p2ScoreLines.add(sumLine);
		
		sumLine = new ScoreLine(0, ScoreLine.ScoreType.Win);
		for(ScoreLine scoreLine : game.getPlayer2().getScore().getScoreLines(ScoreLine.ScoreType.Win)){
			sumLine.setValue(sumLine.getValue() + scoreLine.getValue());
		}
		p2ScoreLines.add(sumLine);
		p2ScoreLines.add(new ScoreLine(game.getPlayer2().getTotalScore(), ScoreLine.ScoreType.Total));
		
		super.create(game);
	}
	
	@Override
	public void validate(){
		setSize(TotemDefender.V_WIDTH/2.5f, TotemDefender.V_HEIGHT/4f);
		setPosition(TotemDefender.V_WIDTH/2 - getWidth()/2, TotemDefender.V_HEIGHT/2 - getHeight()/2);
		winnerLabel.sizeToBounds();
		winnerLabel.setPosition(getWidth()/2 - winnerLabel.getWidth()/2, getHeight() * scoreDisplayRatioH + winnerLabel.getHeight()/2 + 5);
		continueButton.setSize(getWidth()/4, (getWidth()/4)/4.65517f);
		continueButton.setPosition(getWidth()/2 - continueButton.getWidth()/2, 0);
		continueButton.setTextOffset(continueButton.getWidth()/2 - continueButton.getTextBounds().width/2, continueButton.getHeight()/2 - 5);
		super.validate();
	}
	
	@Override
	public void update(TotemDefender game){
		super.update(game);
		
		for(int i = 0; i <= 1; i++){
			if(getPlayerScoreLines(i + 1).isEmpty() && currentScoreLine[i] == null) continue;
			
			if(currentScoreLine[i] == null){
				currentScoreLine[i] = getPlayerScoreLines(i + 1).poll();
				currentScoreLine[i].setParent(this); 
				currentScoreLine[i].create(game);
				currentScoreLine[i].setSize(getWidth() * scoreDisplayRatioW - padding*2, currentScoreLine[i].getDescription().bounds.height);
				currentScoreLine[i].setPosition(i * (getWidth() * scoreDisplayRatioW) + padding, (getHeight() * scoreDisplayRatioH) - ((currentScoreLine[i].getHeight() + 10) * lines[i]) - padding - 20);
				currentScoreLine[i].doCount();
				lines[i]++;
			}else if(currentScoreLine[i].doneCounting()){
				currentScoreLine[i] = null;
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
		TotemDefender.EnableBlend();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(leftColor);
		shapeRenderer.rect(getPosition().x, getPosition().y, getWidth() * scoreDisplayRatioW, getHeight() * scoreDisplayRatioH);
		shapeRenderer.setColor(rightColor);
		shapeRenderer.rect(getPosition().x + getWidth() * scoreDisplayRatioW, getPosition().y, getWidth() * scoreDisplayRatioW, getHeight() * scoreDisplayRatioH);
		shapeRenderer.setColor(0, 0, 0, .5f);
		shapeRenderer.rect(getPosition().x, getPosition().y + getHeight() * scoreDisplayRatioH, getWidth(), getHeight() * (1 - scoreDisplayRatioH));
		shapeRenderer.end();
		TotemDefender.DisableBlend();
		super.render(batch, shapeRenderer);
	}
	
	public Queue<ScoreLine> getPlayerScoreLines(int i){
		if(i == 1) return p1ScoreLines; return p2ScoreLines;
	}

}
