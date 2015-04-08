package com.totemdefender.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.files.FileHandleStream;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.totemdefender.TotemDefender;
import com.totemdefender.menu.NavigableContainer.ConnectionType;
import com.totemdefender.states.LeaderBoardState;

public class LeaderBoardMenu extends NavigableContainer {
	private Button returner;
	private	Label leaderboard;
	private LeaderBoardState state;
	private String scoreList = "LEADER BOARD\n";
	private FileHandle file = Gdx.files.local("td_ranking.txt");
	private String text = file.readString();
	private String[] textBox = text.split("\n");
	
	public LeaderBoardMenu(LeaderBoardState state){
		super(null, ConnectionType.Vertical);
		this.state = state;
	}
	
	@Override
	public void create(TotemDefender game){
		Vector2 buttonSize = new Vector2((TotemDefender.V_WIDTH/3), (TotemDefender.V_WIDTH/4)/4.65517f); //4.6.. is the apsect ratio of the button texture
		Vector2 LabelSize = new Vector2(TotemDefender.V_WIDTH, TotemDefender.V_HEIGHT - buttonSize.y);

		ScoreBubbleSort(textBox);
		file.writeString("", false);

		for(int i=0; i<textBox.length; i++) {
			file.writeString(textBox[i] + "\n", true);
			text = textBox[i].replace("<>", " - ");
			scoreList += i+1 + ". " + text + "\n";
			
			if(i == 5) { break; }
		}
		
		leaderboard = new Label(this);
		leaderboard.setPosition(new Vector2(0,buttonSize.y));
		leaderboard.setTextOffset(new Vector2((TotemDefender.V_WIDTH/2.0f) - buttonSize.x/2, TotemDefender.V_HEIGHT/2));
		leaderboard.setFont("hud_large.ttf");
		leaderboard.setText(scoreList);
		leaderboard.setSize(LabelSize);
		
		returner = new Button(this, "Main Menu", buttonSize, new Vector2((TotemDefender.V_WIDTH/2.0f) - buttonSize.x/2, 0), null){
			@Override
			public boolean onClick(){
				state.returnButtonPressed(true);
				return true;
			}
			
		};
		returner.setFont("hud_large.ttf");
		returner.setTextOffset(buttonSize.x/2 - returner.getTextBounds().width/2, buttonSize.y/2 - returner.getTextBounds().height/2 + 5); 
		returner.setBackgroundTexture(game, "ui/bar_tall.png");
		returner.setBackgroundHighlightTexture(game, "ui/bar_tall_hover.png");
		returner.create(game);
		
		this.addComponent(leaderboard);
		this.addComponent(returner);
		this.attachKeyboardListeners(game.getPlayer2());
		super.create(game);
	}
	
	public void ScoreBubbleSort(String[] array) {
	    boolean swapped = true;
	    int j = 0;
	    String tmp;
	    String[] splitBox = null;
	    int rightScore;
	    int leftScore;
	    
	    while (swapped) {
	        swapped = false;
	        j++;
	        for (int i = 0; i < array.length - j; i++) {
	        	if(array[i].contains("<>")) {
	        		splitBox = array[i].split("<>"); 
	        		System.out.println(splitBox[1]);
		        	rightScore = Integer.parseInt(splitBox[1]);
	        	}
		        else {
		        	rightScore = 0;
		        }
		        
	        	if(array[i+1].contains("<>")) {
	        		splitBox = array[i+1].split("<>");
		        	leftScore = Integer.parseInt(splitBox[1]);
	        	}
	        	else {
			        leftScore = 0;
			    }
	
	            if (rightScore < leftScore) {
	                tmp = array[i];
	                array[i] = array[i + 1];
	                array[i + 1] = tmp;
	                swapped = true;
	            }
	        }
	    }
	}
}