package com.wegapplikation.config.controller;

/**
 * The {@code Direction} enum represents the four cardinal directions: UP, RIGHT, DOWN, and LEFT.
 * It is primarily used to define changes in coordinates (deltas) in a 2D grid system,
 * such as when calculating movement or adjacency in a map or pathfinding application.
 */
public enum Direction {
  /**
   * Represents the Up direction, typically corresponding to a change of 0 on the x-axis (dx)
   * and -1 on the y-axis (dy) in a standard computer graphics coordinate system (y-axis points down).
   */
  UP(0, -1),
  
  /**
   * Represents the Right direction, corresponding to a change of +1 on the x-axis (dx)
   * and 0 on the y-axis (dy).
   */
  RIGHT(1, 0),
  
  /**
   * Represents the Down direction, typically corresponding to a change of 0 on the x-axis (dx)
   * and +1 on the y-axis (dy) in a standard computer graphics coordinate system.
   */
  DOWN(0, 1),
  
  /**
   * Represents the Left direction, corresponding to a change of -1 on the x-axis (dx)
   * and 0 on the y-axis (dy).
   */
  LEFT(-1, 0);

  // --- Instance Fields ---

  private final int dx; // delta x: change in the x-coordinate
  private final int dy; // delta y: change in the y-coordinate

  /**
   * Constructs a {@code Direction} constant with the specified coordinate changes.
   *
   * @param dx The change in the x-coordinate (delta x).
   * @param dy The change in the y-coordinate (delta y).
   */
  Direction(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  // --- Public Accessor Methods ---

  /**
   * Gets the delta x value (change in the x-coordinate) associated with this direction.
   *
   * @return The integer value of the x-coordinate change.
   */
  public int getDx() {
    return dx;
  }

  /**
   * Gets the delta y value (change in the y-coordinate) associated with this direction.
   *
   * @return The integer value of the y-coordinate change.
   */
  public int getDy() {
    return dy;
  }
}