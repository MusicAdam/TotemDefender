package com.totemdefender.input;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.totemdefender.TotemDefender;

/**
 * Handles game input
 */
public class InputHandler implements InputProcessor{
	
	/**
	 * Default key bindings
	 */
	public static int PL_1_L 		= Input.Keys.A;
	public static int PL_1_U 		= Input.Keys.W;
	public static int PL_1_R 		= Input.Keys.D;
	public static int PL_1_D 		= Input.Keys.S;
	public static int PL_1_SELECT = Input.Keys.SHIFT_LEFT; 
	public static int CHARGE_SHOT = Input.Keys.SPACE;
	public static int PL_2_L		= Input.Keys.J;
	public static int PL_2_U		= Input.Keys.I;
	public static int PL_2_R 		= Input.Keys.L;
	public static int PL_2_D		= Input.Keys.K;
	public static int PL_2_SELECT = Input.Keys.SHIFT_RIGHT;
	
	private ArrayList<KeyboardEvent> keyboardListeners;
	private ArrayList<MouseEvent> mouseListeners;
	
	private TotemDefender game;
	
	public InputHandler(TotemDefender game){
		this.game = game;
		keyboardListeners = new ArrayList<KeyboardEvent>();
		mouseListeners = new ArrayList<MouseEvent>();
	}
	
	public KeyboardEvent addListener(KeyboardEvent event){
		keyboardListeners.add(event);
		return event;
	}
	
	public MouseEvent addListener(MouseEvent event){
		mouseListeners.add(event);
		return event;
	}
	
	public void removeListener(KeyboardEvent event){
		keyboardListeners.remove(event);
	}
	
	public void removeListener(MouseEvent event){
		mouseListeners.remove(event);
	}
	
	private boolean dispatchKeyboardEvent(KeyboardEvent cmp){
		for(KeyboardEvent evt : keyboardListeners){
			if(cmp.equals(evt)){
				if(evt.callback()){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean dispatchMouseEvent(MouseEvent cmp){
		for(MouseEvent evt : mouseListeners){
			if(cmp.equals(evt)){
				if(evt.callback()){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Takes mouse coordinates and converts them into game (window) coordinates
	 * 
	 * @param coord - Mouse coordinates
	 * @return window coordinates
	 */
	private Vector2 screenCoordsToGame(Vector2 coord, Camera cam){
		Vector3 screenCoord = new Vector3(coord.x, coord.y, 0);
		cam.unproject(screenCoord);
		return new Vector2(screenCoord.x, screenCoord.y);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		KeyboardEvent cmp = new KeyboardEvent(KeyboardEvent.KEY_DOWN, keycode);
		return dispatchKeyboardEvent(cmp);
	}
	@Override
	public boolean keyUp(int keycode) {
		KeyboardEvent cmp = new KeyboardEvent(KeyboardEvent.KEY_UP, keycode);
		return dispatchKeyboardEvent(cmp);
	}
	@Override
	public boolean keyTyped(char character) {
		//Unused
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		MouseEvent cmp = new MouseEvent(MouseEvent.MOUSE_DOWN, button, screenCoordsToGame(new Vector2(screenX, screenY), game.getCamera()), 0);
		return dispatchMouseEvent(cmp);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		MouseEvent cmp = new MouseEvent(MouseEvent.MOUSE_UP, button, screenCoordsToGame(new Vector2(screenX, screenY), game.getCamera()), 0);
		return dispatchMouseEvent(cmp);
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// Unused
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		MouseEvent cmp = new MouseEvent(MouseEvent.MOUSE_MOVE, -1, screenCoordsToGame(new Vector2(screenX, screenY), game.getCamera()), 0);
		return dispatchMouseEvent(cmp);
	}
	@Override
	public boolean scrolled(int amount) {
		MouseEvent cmp = new MouseEvent(MouseEvent.MOUSE_SCROLL, -1, null, amount);
		return dispatchMouseEvent(cmp);
	}
	
	

}
