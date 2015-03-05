package com.totemdefender.menu;

import java.util.ArrayList;

import com.totemdefender.Player;
import com.totemdefender.TotemDefender;
import com.totemdefender.input.InputHandler;
import com.totemdefender.input.KeyboardEvent;
import com.totemdefender.input.MouseEvent;

public class NavigableContainer extends Container{
	public class Edge{
		public Node node1;
		public Node node2;
		public int yDir;
		public int xDir;
		public boolean isWrapper = false;
		
		public void updateDirection(){
			float xDist = node2.component.getPosition().x - node1.component.getPosition().x;
			float yDist = node2.component.getPosition().y - node1.component.getPosition().y;
			this.xDir =(int)(xDist / (float)Math.abs(xDist));
			this.yDir =(int)(yDist / (float)Math.abs(yDist));
			if(isWrapper){
				xDir *= -1;
				yDir *= -1;
			}
		}
	}
	
	public class Node{
		public ArrayList<Edge> edges = new ArrayList<Edge>();
		public Component component;
		public Node(){}
		public void updateEdges(){
			for(Edge edge : edges){
				edge.updateDirection();
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
	private boolean traverseDown;
	private boolean traverseUp;
	private boolean traverseLeft;
	private boolean traverseRight;
	private boolean disableTraverse;
	private long lastTraversalTime = 0;
	private long traversalTime = 250; //Time between movement (miliseconds)
	
	public NavigableContainer(Container parent) {
		super(parent);
		
		graph = new ArrayList<Node>();
	}
	
	@Override
	public void update(TotemDefender game){
		for(Component cmp : components){
			cmp.update(game);
		}
		
		doTraverse();
		
		validate();
	}
	
	@Override
	public void validate(){
		if(!isValid()){
			for(Node node : graph)
				node.updateEdges();
		}
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
	
	public void onTraverseDown(){
		moveFocusDown();		
	}
	
	public void onTraverseUp(){
		moveFocusUp();			
	}
	
	public void onTraverseLeft(){
		moveFocusLeft();		
	}
	
	public void onTraverseRight(){
		moveFocusRight();			
	}
	
	public void connectComponents(Component cmp1, Component cmp2, boolean isWrapper){
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
		edge1.isWrapper = isWrapper;
		edge1.updateDirection();
		node1.edges.add(edge1);
		edge2.node1 = node2;
		edge2.node2 = node1;
		edge2.isWrapper = isWrapper;
		edge2.updateDirection();
		node2.edges.add(edge2);
	}
	
	public void connectComponents(Component cmp1, Component cmp2){
		connectComponents(cmp1, cmp2, false);
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
	
	private Node findClosestNode(Node node, int xDir, int yDir){
		for(Edge edge : node.edges){
			if(	edge.yDir == xDir ||
				edge.yDir == yDir){
				if(edge.node1 == node){
					return edge.node2;
				}else{
					return edge.node1;
				}
			}
		}
		
		return null;
	}
	
	public void moveFocusDown(){
		//if(!shouldTraverse()) return;
		if(nodeFocus == null){
			if(!graph.isEmpty())
				setFocus(graph.get(0));
		}else{
			Node found = findClosestNode(nodeFocus, 0, -1);
			if(found != null)
				setFocus(found);
		}

		resetTraversalTime();
	}
	
	public void moveFocusLeft(){
		//if(!shouldTraverse()) return;
		if(nodeFocus == null){
			if(!graph.isEmpty())
				setFocus(graph.get(0));
		}else{
			Node found = findClosestNode(nodeFocus, -1, 0);
			if(found != null)
				setFocus(found);
		}

		resetTraversalTime();
	}
	
	public void moveFocusRight(){
		//if(!shouldTraverse()) return;
		if(nodeFocus == null){
			if(!graph.isEmpty())
				setFocus(graph.get(0));
		}else{
			Node found = findClosestNode(nodeFocus, 1, 0);
			if(found != null)
				setFocus(found);
		}

		resetTraversalTime();
	}
	
	public void moveFocusUp(){
		//if(!shouldTraverse()) return;
		if(nodeFocus == null){
			if(!graph.isEmpty())
				setFocus(graph.get(0));
		}else{
			Node found = findClosestNode(nodeFocus, 0, 1);
			if(found != null)
				setFocus(found);
		}
		
		resetTraversalTime();
	}
	
	public void resetTraversalTime(){
		lastTraversalTime = System.currentTimeMillis();
	}
	
	public boolean onUpKeyDown(){
		onTraverseUp();
		traverseUp = true;
		return true;		
	}
	
	public boolean onUpKeyUp(){
		traverseUp = false;
		return true;		
	}
	
	public boolean onDownKeyDown(){
		onTraverseDown();
		traverseDown = true;
		return true;		
	}
	
	public boolean onDownKeyUp(){
		traverseDown = false;
		return true;		
	}
	
	public boolean onLeftKeyDown(){
		onTraverseLeft();
		traverseLeft = true;
		return true;
	}
	
	public boolean onLeftKeyUp(){
		traverseLeft = false;
		return true;		
	}
	
	public boolean onRightKeyDown(){
		onTraverseRight();
		traverseRight = true;
		return true;
	}
	
	public boolean onRightKeyUp(){
		traverseRight = false;
		return true;
	}
	
	public boolean onSelectKeyUp(){
		if(getFocus() != null)
			getFocus().onKeyboardSelect();
		return true;
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

}
