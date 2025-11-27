package com.wegapplikation.config.controller;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code GameState} class represents the current state of the pathfinding or
 * "Game" logic within the application. It stores the map data (pixel array),
 * the **Start** and **End** positions, and the direction of the last move.
 * This class is designed to be immutable, where methods that modify the state
 * (like moving the current position) return a new {@code GameState} object.
 */
public class GameState {

	  private final int[][] pixelArray;
	  /** End and Start/Current positions: (ex, ey) is the **E**nd position, (sx, sy) is the **S**tart/Current position. **/
	  private final int ex, ey, sx, sy; 
	  /** Direction of the last move made by the current point ('cat' in previous versions). **/
	  private Direction catDir = null; 

	  /**
	   * Initializes a new GameState object for a new route, setting the map data
	   * and all coordinates to 0.
	   *
	   * @param pixelArray The 2D array representing the map or grid structure.
	   */
	  public GameState(int[][] pixelArray) { 
	    this.pixelArray = pixelArray;
	    this.ex = 0;
	    this.ey = 0;
	    this.sx = 0;
	    this.sy = 0;	  
	  }

	  /**
	   * Initializes a GameState with specified positions and direction.
	   * This is the primary constructor used to create new states based on existing ones.
	   * * @param pixelArray The map data.
	   * @param ex The x-coordinate of the **E**nd position.
	   * @param ey The y-coordinate of the **E**nd position.
	   * @param sx The x-coordinate of the **S**tart/Current position.
	   * @param sy The y-coordinate of the **S**tart/Current position.
	   * @param catDir The direction of the last move made by the current position.
	   */
	  public GameState(int[][] pixelArray, int ex, int ey, int sx, int sy, Direction catDir) { 	
	    this.pixelArray = pixelArray;
	    this.ex = ex;
	    this.ey = ey;
	    this.sx = sx;
	    this.sy = sy;
	    this.catDir = catDir;
	  }	
	 
	 /**
	  * Returns a new immutable {@code GameState} object with specified start (sx, sy) and end (ex, ey) positions.
	  * * @param ex The x-coordinate of the End position.
	  * @param ey The y-coordinate of the End position.
	  * @param sx The x-coordinate of the Start/Current position.
	  * @param sy The y-coordinate of the Start/Current position.
	  * @return A new {@code GameState} instance with the provided coordinates.
	  */
	 public GameState withStartEnd(int ex, int ey, int sx, int sy) {			
			  return new GameState(this.pixelArray, ex, ey, sx, sy, null);
		  }
	 
	  /**
	   * Returns a new {@code GameState} representing a move of the current position (sx, sy)
	   * in the specified direction. The end position (ex, ey) remains unchanged.
	   *
	   * @param dir The {@code Direction} to move the current position (sx, sy).
	   * @return A new {@code GameState} with updated (sx, sy) coordinates and the new direction.
	   */
	  public GameState withMoveCat(Direction dir) {
	    return new GameState(
	        this.pixelArray, 
	        this.ex, 
	        this.ey, 
	        this.sx + dir.getDx(), // New Start/Current X
	        this.sy + dir.getDy(), // New Start/Current Y
	        dir);
	  }

	  /**
	   * Gets the 2D array representing the map structure.
	   * @return The pixel array of the map.
	   */
	  public int[][] getpixelArray() {
	    return pixelArray;
	  }

	  /**
	   * Gets the x-coordinate of the **E**nd position.
	   * @return The x-coordinate (ex).
	   */
	  public int getex() {
	    return ex;
	  }

	  /**
	   * Gets the y-coordinate of the **E**nd position.
	   * @return The y-coordinate (ey).
	   */
	  public int getey() {
	    return ey;
	  }

	  /**
	   * Gets the x-coordinate of the **S**tart/Current position.
	   * @return The x-coordinate (sx).
	   */
	  public int getsx() {
	    return sx;
	  }

	  /**
	   * Gets the y-coordinate of the **S**tart/Current position.
	   * @return The y-coordinate (sy).
	   */
	  public int getsy() {
	    return sy;
	  }

	  /**
	   * Gets the direction of the last move made by the current position.
	   * @return The last move direction, or {@code null} if no move has been made.
	   */
	  public Direction getCatDir() {
	    return catDir;
	  }

	  /**
	   * Checks if the Start/Current position (sx, sy) has reached the End position (ex, ey).
	   * @return {@code true} if the current coordinates match the target coordinates, {@code false} otherwise.
	   */
	  public boolean isStarttheEnd() {		
	    return sx == ex && sy == ey;
	  }

	}