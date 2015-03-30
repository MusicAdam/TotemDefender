package com.totemdefender.menu;

import java.util.ArrayList;

import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;
import com.totemdefender.menu.buildmenu.ReadyButton;
import com.totemdefender.player.Player;

public class NavigableContainer extends Container{
	public enum ConnectionType{
		Horizontal, Vertical
	}
	public class Edge{
		public Node node1;
		public Node node2;
		public ConnectionType connection;
		private int dir;
		private float dist;
		
		public void update(){
			if(connection == ConnectionType.Horizontal){
				dist = node2.component.getPosition().x - node1.component.getPosition().x;
				dir = (int)(dist/Math.abs(dist));
			}else{
				dist = node2.component.getPosition().y - node1.component.getPosition().y;
				dir = (int)(dist/Math.abs(dist));
			}
		}
		
		public int getDir(){
			return dir;
		}
		
		public float getDistance(){
			return dist;
		}
	}
	
	public class Node{
		public ArrayList<Edge> edges = new ArrayList<Edge>();
		public Component component;
		public Node(){}	
		public void update(){
			for(Edge e : edges){
				e.update();
			}
		}
	}
	
	private ArrayList<Node> graph;
	private Node			nodeFocus;
	protected KeyboardEvent 	upKeyDownListener;
	protected KeyboardEvent 	upKeyUpListener;
	protected KeyboardEvent 	downKeyDownListener;
	protected KeyboardEvent 	downKeyUpListener;
	protected KeyboardEvent 	leftKeyDownListener;
	protected KeyboardEvent 	leftKeyUpListener;
	protected KeyboardEvent 	rightKeyDownListener;
	protected KeyboardEvent 	rightKeyUpListener;
	protected KeyboardEvent 	selectKeyUpListener;
	protected boolean traverseDown;
	protected boolean traverseUp;
	protected boolean traverseLeft;
	protected boolean traverseRight;
	private boolean disableTraverse;
	private long lastTraversalTime = 0;
	private long traversalTime = 150; //Time between movement (miliseconds)
	private ConnectionType orientation;
	
	public NavigableContainer(Container parent, ConnectionType orientation) {
		super(parent);
		
		graph = new ArrayList<Node>();
		this.orientation = orientation;
	}
	
	@Override
	public void update(TotemDefender game){
		for(Component cmp : components){
			cmp.update(game);
		}
		
		doTraverse();
		
		if(!isValid())
			validate();
	}
	
	@Override
	public void validate(){
		for(Node n : graph)
			n.update();
		super.validate();
	}
	
	public void doTraverse(){
		if(!shouldTraverse()) return;
				
		if(traverseDown){
			onTraverseDown();
		}else if(traverseUp){
			onTraverseUp();		
		}else if(traverseLeft){
			onTraverseLeft();			
		}else if(traverseRight){
			onTraverseRight();		
		}
	}
	
	public boolean shouldTraverse(){
		return (System.currentTimeMillis() - lastTraversalTime > traversalTime) && !disableTraverse;
	}
	
	public boolean onTraverseDown(){
		resetTraversalTime();
		return moveFocusDown();		
	}
	
	public boolean onTraverseUp(){
		resetTraversalTime();
		return moveFocusUp();			
	}
	
	public boolean onTraverseLeft(){
		resetTraversalTime();
		return moveFocusLeft();		
	}
	
	public boolean onTraverseRight(){
		resetTraversalTime();
		return moveFocusRight();			
	}
	
	public void connectComponents(Component cmp1, Component cmp2, ConnectionType connectionType, boolean isWrapper){
		Edge edge1 = new Edge();
		Edge edge2 = new Edge();

		Node node1 = findNode(cmp1);
		Node node2 = findNode(cmp2);
		
		if(node1 == null){
			node1 = new Node();
			node1.component = cmp1;
			graph.add(node1);
		}
		
		if(node2 == null){
			node2 = new Node();
			node2.component = cmp2;
			graph.add(node2);
		}
		
		edge1.node1 = node1;
		edge1.node2 = node2;
		edge1.connection = connectionType;
		edge2.node1 = node2;
		edge2.node2 = node1;
		edge2.connection = connectionType;
		node1.edges.add(edge1);
		node2.edges.add(edge2);
	}
	
	public void connectComponents(Component cmp1, Component cmp2, ConnectionType connectionType){
		connectComponents(cmp1, cmp2, connectionType, false);
	}
	
	public void connectComponentsHorizontally(Component cmp1, Component cmp2){
		connectComponents(cmp1, cmp2, ConnectionType.Horizontal);
	}
	
	public void connectComponentsVertically(Component cmp1, Component cmp2){
		connectComponents(cmp1, cmp2, ConnectionType.Vertical);
	}
	
	public Node findNode(Component cmp){
		for(Node node : graph){
			if(node.component == cmp)
				return node;
		}
		
		return null;
	}
	
	@Override
	public void destroy(TotemDefender game){
		super.destroy(game);
		
		game.getMenuInputHandler().removeListener(upKeyDownListener);
		game.getMenuInputHandler().removeListener(upKeyUpListener);
		game.getMenuInputHandler().removeListener(downKeyDownListener);
		game.getMenuInputHandler().removeListener(downKeyUpListener);
		game.getMenuInputHandler().removeListener(leftKeyDownListener);
		game.getMenuInputHandler().removeListener(leftKeyUpListener);
		game.getMenuInputHandler().removeListener(rightKeyDownListener);
		game.getMenuInputHandler().removeListener(rightKeyUpListener);
		game.getMenuInputHandler().removeListener(selectKeyUpListener);
	}
	
	@Override
	public void setFocus(Component cmp){
		super.setFocus(cmp);
		
		nodeFocus = findNode(cmp);
	}
	
	public void setFocus(Node node){
		if(node == null){
			super.setFocus(null);
		}else{
			super.setFocus(node.component);			
		}
		
		nodeFocus = node;
	}
	
	private Node findClosestNode(Node node, ConnectionType connectionType, int dir){
		if(node == null) return null;
		
		Node closestNode = null;
		float closestDist = Float.MAX_VALUE;
		for(Edge edge : node.edges){
			if(edge.getDir() == dir && edge.connection == connectionType){
				if(closestNode == null){	
					closestDist = edge.getDistance();
					if(edge.node1 == node){
						closestNode = edge.node2;
					}else{
						closestNode = edge.node1;
					}
				}else if(edge.getDistance() < closestDist){
					closestDist = edge.getDistance();
					if(edge.node1 == node){
						closestNode = edge.node2;
					}else{
						closestNode = edge.node1;
					}					
				}
			}
		}
		
		return closestNode;
	}
	
	public boolean moveFocusDown(){
		Node found = findClosestNode(nodeFocus, ConnectionType.Vertical, -1);
		if(found != null){
			setFocus(found);
			return true;
		}
		if(orientation == ConnectionType.Vertical){
			if(!graph.isEmpty()){
				setFocus(graph.get(0));
				return true;
			}
		}
		return false;
	}
	
	public boolean moveFocusLeft(){
		Node found = findClosestNode(nodeFocus, ConnectionType.Horizontal, -1);
		if(found != null){
			setFocus(found);
			return true;
		}
		if(orientation == ConnectionType.Horizontal){
			if(!graph.isEmpty()){
				setFocus(graph.get(graph.size() - 1));
				return true;
			}
		}
		return false;
	}
	
	public boolean moveFocusRight(){
		Node found = findClosestNode(nodeFocus, ConnectionType.Horizontal, 1);
		if(found != null){
			setFocus(found);
			return true;
		}
		
		if(orientation == ConnectionType.Horizontal){
			if(!graph.isEmpty()){
				setFocus(graph.get(0));
				return true;
			}
		}
		return false;
	}
	
	public boolean moveFocusUp(){
		Node found = findClosestNode(nodeFocus, ConnectionType.Vertical, 1);
		if(found != null){
			setFocus(found);
			return true;
		}
		
		if(orientation == ConnectionType.Vertical){
			if(!graph.isEmpty()){
				setFocus(graph.get(graph.size() - 1));
				return true;
			}
		}
		return false;
	}
	
	public void resetTraversalTime(){
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public boolean onUpKeyDown(){
		if(keyboardFocusIsNull()){
			if(moveFocusUp()){
				resetTraversalTime();
				traverseUp = true;
				return true;
			}else{
				for(Component cmp : components){
					if(!cmp.hasFocus()) continue;
					if(cmp.onUpKeyDown()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean onUpKeyUp(){
		if(keyboardFocusIsNull()){
			traverseUp = false;
			
			for(Component cmp : components){
				if(!cmp.hasFocus()) continue;
				if(cmp.onUpKeyUp()){
					return true;
				}
			}
			
			return true;	
		}
		return false;
	}
	
	public boolean onDownKeyDown(){
		if(keyboardFocusIsNull()){
			if(moveFocusDown()){
				resetTraversalTime();
				traverseDown = true;
				return true;
			}else{
				for(Component cmp : components){
					if(!cmp.hasFocus()) continue;
					if(cmp.onDownKeyDown()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean onDownKeyUp(){
		if(keyboardFocusIsNull()){
			traverseDown = false;
			
			for(Component cmp : components){
				if(!cmp.hasFocus()) continue;
				if(cmp.onDownKeyDown()){
					return true;
				}
			}
			
			return true;		
		}
		return false;
	}
	
	public boolean onLeftKeyDown(){
		if(keyboardFocusIsNull()){
			if(moveFocusLeft()){
				resetTraversalTime();
				traverseLeft = true;
				return true;
			}else{
				for(Component cmp : components){
					if(cmp.onLeftKeyDown()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean onLeftKeyUp(){
		if(keyboardFocusIsNull()){
			traverseLeft = false;
			
			for(Component cmp : components){
				if(!cmp.hasFocus()) continue;
				if(cmp.onLeftKeyUp()){
					return true;
				}
			}
			
			return true;	
		}
		return false;
	}
	
	public boolean onRightKeyDown(){
		if(keyboardFocusIsNull()){
			if(moveFocusRight()){
				resetTraversalTime();
				traverseRight = true;
				return true;
			}else{
				for(Component cmp : components){
					if(!cmp.hasFocus()) continue;
					if(cmp.onRightKeyDown()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean onRightKeyUp(){
		if(keyboardFocusIsNull()){
			traverseRight = false;
			
			for(Component cmp : components){
				if(!cmp.hasFocus()) continue;
				if(cmp.onRightKeyUp()){
					return true;
				}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean onSelectKeyUp(){
		if(keyboardFocusIsNull()){
			if(getFocus() != null){
				getFocus().onKeyboardSelect();
			return true;
			}
		}
		return false;
	}
	
	public void attachKeyboardListeners(Player player){
		InputHandler inputHandler = TotemDefender.Get().getMenuInputHandler();
		
		upKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getUpKey()){
			@Override
			public boolean callback(){
				return onUpKeyDown();
			}
		});
		
		upKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getUpKey()){
			@Override
			public boolean callback(){
				return onUpKeyUp();
			}
		});
		
		downKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getDownKey()){
			@Override
			public boolean callback(){
				return onDownKeyDown();
			}
		});
		
		downKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getDownKey()){
			@Override
			public boolean callback(){
				return onDownKeyUp();
			}
		});
		
		leftKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getLeftKey()){
			@Override
			public boolean callback(){
				return onLeftKeyDown();
			}
		});
		
		leftKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getLeftKey()){
			@Override
			public boolean callback(){
				return onLeftKeyUp();
			}
		});
		
		rightKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getRightKey()){
			@Override
			public boolean callback(){
				return onRightKeyDown();
			}
		});
		
		rightKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getRightKey()){
			@Override
			public boolean callback(){
				return onRightKeyUp();
			}
		});
		
		selectKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getSelectKey()){
			@Override
			public boolean callback(){
				return onSelectKeyUp();
			}
		});
	}

	public void setDisableTraverse(boolean disableTraverse) {
		this.disableTraverse = disableTraverse;
	}
	
	@Override
	public void removeComponent(Component cmp){
		if(getFocus() == cmp){
			setFocus((Node)null);
		}
		graph.remove(findNode(cmp));
		super.removeComponent(cmp);
	}
	
	public boolean keyboardFocusIsNull(){
		return TotemDefender.Get().getKeyboardFocus() == null;
	}
}
