package com.wegapplikation.config.controller;

import java.util.concurrent.ThreadLocalRandom;

public class GameState {

	  private final int[][] pixelArray;
	  private final int mx, my, cx, cy; /** Start and Endpositions **/
	  private Direction catDir = null; /**direction **/

	  public GameState(int[][] pixelArray) { /**initilazing of a new object for a new route**/
	    this.pixelArray = pixelArray;
	    this.mx = 0;
	    this.my = 0;
	    this.cx = 0;
	    this.cy = 0;	  
	  }

	  public GameState(int[][] pixelArray, int mx, int my, int cx, int cy, Direction catDir) { 	/****/
	    this.pixelArray = pixelArray;
	    this.mx = mx;
	    this.my = my;
	    this.cx = cx;
	    this.cy = cy;
	    this.catDir = catDir;
	  }

	 public GameState withCatMousePositions() {
	    // Position cat on any even field
		 int mx = 69;
		 int my = 302;
		 int cx = 162;
		 int cy = 325;
	    return new GameState(this.pixelArray, mx, my, cx, cy, null);
	  }
	 
	 public GameState withCatMousePositions2(int mx, int my, int cx, int cy) {
		    // Position cat on any even field
			  return new GameState(this.pixelArray, mx, my, cx, cy, null);
		  }
	 
	  public GameState withMoveCat(Direction dir) {
	    return new GameState(
	        this.pixelArray, this.mx, this.my, this.cx + dir.getDx(), this.cy + dir.getDy(), dir);
	  }

	  public int[][] getpixelArray() {
	    return pixelArray;
	  }

	  public int getMx() {
	    return mx;
	  }

	  public int getMy() {
	    return my;
	  }

	  public int getCx() {
	    return cx;
	  }

	  public int getCy() {
	    return cy;
	  }

	  public Direction getCatDir() {
	    return catDir;
	  }

	  public boolean hasCatEatenMouse() {		/**check if the End and the current position are the same**/
	    return cx == mx && cy == my;
	  }

	}