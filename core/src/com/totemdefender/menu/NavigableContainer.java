package com.totemdefender.menu;

import java.util.ArrayList;

import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;

public class NavigableContainer extends Container{
	public class Edge{
		public Node node1;
		public Node node2;
		public float distance;
		public int yDir;
		public int xDir;
		
		public void updateDistances(){
			this.distance = node2.component.getPosition().dst(node1.component.getPosition());
			float xDist = node2.component.getPosition().x - node1.component.getPosition().x;
			float yDist = node2.component.getPosition().y - node1.component.getPosition().y;
			this.xDir =(int)(xDist / (float)Math.abs(xDist));
			this.yDir =(int)(yDist / (float)Math.abs(yDist));
		}
	}
	
	public class Node{
		public ArrayList<Edge> edges = new ArrayList<Edge>();
		public Component component;
		public Node(){}
		public void updateEdges(){
			for(Edge edge : edges){
				edge.updateDistances();
			}
		}
		
	}
	
	private ArrayList<Node> graph;
	private Node			focus;
	private KeyboardEvent 	upKeyDownListener;
	private KeyboardEvent 	upKeyUpListener;
	private KeyboardEvent 	downKeyDownListener;
	private KeyboardEvent 	downKeyUpListener;
	private KeyboardEvent 	leftKeyDownListener;
	private KeyboardEvent 	leftKeyUpListener;
	private KeyboardEvent 	rightKeyDownListener;
	private KeyboardEvent 	rightKeyUpListener;
	private boolean traverseDown;
	private boolean traverseUp;
	private boolean traverseLeft;
	private boolean traverseRight;
	private long lastTraversalTime = 0;
	private long traversalTime = 250; //Time between movement (miliseconds)
	
	public NavigableContainer(Container parent) {
		super(parent);
		
		graph = new ArrayList<Node>();
	}
	
	@Override
	public void update(TotemDefender game){
		for(Component cmp : components){
			if(!cmp.isValid()){
				findNode(cmp).updateEdges();
				setValid(false);
			}
			cmp.update(game);
		}
		
		if(traverseDown && shouldTraverse()){
			moveFocusDown();
		}else if(traverseUp && shouldTraverse()){
			moveFocusUp();			
		}else if(traverseLeft && shouldTraverse()){
			moveFocusLeft();			
		}else if(traverseRight && shouldTraverse()){
			moveFocusRight();			
		}
		
		
		validate();
	}
	
	@Override
	public void addComponent(Component cmp){
		super.addComponent(cmp);
		Node node = new Node();
		node.component = cmp;
		graph.add(node);
	}
	
	private boolean shouldTraverse(){
		return (System.currentTimeMillis() - lastTraversalTime > traversalTime);
	}
	
	public void connectComponents(Component cmp1, Component cmp2){
		Edge edge = new Edge();
		Node node1 = findNode(cmp1);
		Node node2 = findNode(cmp2);
		
		if(node1 == null || node2 == null)
			throw new NullPointerException("Component doesn't exist in graph");
		
		edge.node1 = node1;
		edge.node2 = node2;
		edge.updateDistances();
		node1.edges.add(edge);
		node2.edges.add(edge);
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
	}
	
	@Override
	public void setFocus(Component cmp){
		super.setFocus(cmp);
		
		focus = findNode(cmp);
	}
	
	public void setFocus(Node node){
		if(node == null){
			super.setFocus(null);
		}else{
			super.setFocus(node.component);			
		}
		
		focus = node;
	}
	
	private Node findClosestNode(Node node, int xDir, int yDir){
		Node closest = null;
		float dist = Float.MAX_VALUE;
		for(Edge edge : node.edges){
			if(	edge.yDir != yDir &&
				edge.xDir != xDir ) continue;
			if(edge.distance < dist){
				dist = edge.distance;
				if(edge.node1 == focus){
					closest = edge.node2;
				}else{
					closest = edge.node1;
				}
			}
		}
		
		return closest;
	}
	
	public void moveFocusDown(){
		if(focus == null){
			if(!components.isEmpty())
				setFocus(components.get(0));
		}else{
			setFocus(findClosestNode(focus, 0, -1));
		}
		
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public void moveFocusLeft(){
		if(focus == null){
			if(!components.isEmpty())
				setFocus(components.get(0));
		}else{
			setFocus(findClosestNode(focus, -1, 0));
		}
		
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public void moveFocusRight(){
		if(focus == null){
			if(!components.isEmpty())
				setFocus(components.get(0));
		}else{
			setFocus(findClosestNode(focus, 1, 0));
		}
		
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public void moveFocusUp(){
		if(focus == null){
			if(!components.isEmpty())
				setFocus(components.get(0));
		}else{
			setFocus(findClosestNode(focus, 0, 1));
		}
		
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public void attachKeyboardListeners(Player player){
		InputHandler inputHandler = TotemDefender.Get().getMenuInputHandler();
		
		upKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getUpKey()){
			@Override
			public boolean callback(){
				moveFocusUp();
				traverseUp = true;
				return true;
			}
		});
		
		upKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getUpKey()){
			@Override
			public boolean callback(){
				traverseUp = false;
				return true;
			}
		});
		
		downKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getDownKey()){
			@Override
			public boolean callback(){
				moveFocusDown();
				traverseDown = true;
				return true;
			}
		});
		
		downKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getDownKey()){
			@Override
			public boolean callback(){
				traverseDown = false;
				return true;
			}
		});
		
		leftKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getLeftKey()){
			@Override
			public boolean callback(){
				moveFocusLeft();
				traverseLeft = true;
				return true;
			}
		});
		
		leftKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getLeftKey()){
			@Override
			public boolean callback(){
				traverseLeft = false;
				return true;
			}
		});
		
		rightKeyDownListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_DOWN, player.getRightKey()){
			@Override
			public boolean callback(){
				moveFocusRight();
				traverseRight = true;
				return true;
			}
		});
		
		rightKeyUpListener = inputHandler.addListener(new KeyboardEvent(KeyboardEvent.KEY_UP, player.getRightKey()){
			@Override
			public boolean callback(){
				traverseRight = false;
				return true;
			}
		});
	}

}
