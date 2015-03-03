package com.totemdefender;

/** Wraps given objects to store their real depth, separate from their array index */
public class DepthWrapper<T> {
	public T object;
	public int depth;
};