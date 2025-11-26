package com.wegapplikation.config.controller;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code GameState} class represents the current state of the pathfinding or
 * "Cat and Mouse" game within the application. It stores the map data,
 * the start and end positions, and the direction of the last move.
 * This class is designed to be immutable, where methods that modify the state
 * (like moving the cat) return a new {@code GameState} object.
 */
public class GameState {

	  private final int[][] pixelArray;
	  /** Start and End positions: (mx, my) is the Mouse/End position, (cx, cy) is the Cat/Current position. **/
	  private final int mx, my, cx, cy; 
	  /** Direction of the last move (of the 'cat' or current point). **/
	  private Direction catDir = null; 

	  /**
	   * Initializes a new GameState object for a new route, setting all coordinates to 0.
	   *
	   * @param pixelArray The 2D array representing the map or grid structure.
	   */
	  public GameState(int[][] pixelArray) { 
	    this.pixelArray = pixelArray;
	    this.mx = 0;
	    this.my = 0;
	    this.cx = 0;
	    this.cy = 0;	  
	  }

	  /**
	   * Initializes a GameState with specified positions and direction.
	   * This is the primary constructor used to create new states based on existing ones.
	   * * @param pixelArray The map data.
	   * @param mx The x-coordinate of the mouse/target/end position.
	   * @param my The y-coordinate of the mouse/target/end position.
	   * @param cx The x-coordinate of the cat/current position.
	   * @param cy The y-coordinate of the cat/current position.
	   * @param catDir The direction of the last move made by the cat/current position.
	   */
	  public GameState(int[][] pixelArray, int mx, int my, int cx, int cy, Direction catDir) { 	
	    this.pixelArray = pixelArray;
	    this.mx = mx;
	    this.my = my;
	    this.cx = cx;
	    this.cy = cy;
	    this.catDir = catDir;
	  }

	  /**
	   * Returns a new {@code GameState} object with hardcoded default start (cat) and end (mouse) positions.
	   *
	   * @return A new {@code GameState} instance with initial coordinates set.
	   */
	 public GameState withCatMousePositions() {
	    // Position cat on any even field (Note: Actual coordinates are hardcoded values)
		 int mx = 69; // Mouse X (Target X)
		 int my = 302; // Mouse Y (Target Y)
		 int cx = 162; // Cat X (Current X)
		 int cy = 325; // Cat Y (Current Y)
	    return new GameState(this.pixelArray, mx, my, cx, cy, null);
	  }
	 
	 /**
	  * Returns a new {@code GameState} object with specified start and end positions.
	  *
	  * @param mx The x-coordinate of the mouse/target/end position.
	  * @param my The y-coordinate of the mouse/target/end position.
	  * @param cx The x-coordinate of the cat/current position.
	  * @param cy The y-coordinate of the cat/current position.
	  * @return A new {@code GameState} instance with the provided coordinates.
	  */
	 public GameState withCatMousePositions2(int mx, int my, int cx, int cy) {
		    // Position cat on any even field (The comment is likely a leftover from a different implementation)
			  return new GameState(this.pixelArray, mx, my, cx, cy, null);
		  }
	 
	  /**
	   * Returns a new {@code GameState} representing a move of the "Cat" (current position)
	   * in the specified direction. The mouse/end position remains unchanged.
	   *
	   * @param dir The {@code Direction} to move the current position (cx, cy).
	   * @return A new {@code GameState} with updated (cx, cy) coordinates and the new direction.
	   */
	  public GameState withMoveCat(Direction dir) {
	    return new GameState(
	        this.pixelArray, 
	        this.mx, 
	        this.my, 
	        this.cx + dir.getDx(), // New Cat X
	        this.cy + dir.getDy(), // New Cat Y
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
	   * Gets the x-coordinate of the mouse/target/end position.
	   * @return The x-coordinate (mx).
	   */
	  public int getMx() {
	    return mx;
	  }

	  /**
	   * Gets the y-coordinate of the mouse/target/end position.
	   * @return The y-coordinate (my).
	   */
	  public int getMy() {
	    return my;
	  }

	  /**
	   * Gets the x-coordinate of the cat/current position.
	   * @return The x-coordinate (cx).
	   */
	  public int getCx() {
	    return cx;
	  }

	  /**
	   * Gets the y-coordinate of the cat/current position.
	   * @return The y-coordinate (cy).
	   */
	  public int getCy() {
	    return cy;
	  }

	  /**
	   * Gets the direction of the last move made by the cat/current position.
	   * @return The last move direction, or {@code null} if no move has been made.
	   */
	  public Direction getCatDir() {
	    return catDir;
	  }

	  /**
	   * Checks if the cat (current position) has reached the mouse (target/end position).
	   * @return {@code true} if the current x and y coordinates match the target x and y coordinates, {@code false} otherwise.
	   */
	  public boolean hasCatEatenMouse() {		
	    return cx == mx && cy == my;
	  }

	}