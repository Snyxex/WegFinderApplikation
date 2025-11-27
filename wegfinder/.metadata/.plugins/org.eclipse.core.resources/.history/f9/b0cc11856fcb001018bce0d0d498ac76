package com.wegapplikation.config.controller;

/**
 * The {@code RedPoint} class represents a specific location or point of interest
 * on the map/grid, typically used to mark start and end points or intermediate
 * points in a route.
 * Each point is defined by its room number, coordinates, and an open/closed status.
 */
public class RedPoint 
{
	
	/**
	 * Constructs a new {@code RedPoint} with the specified attributes.
	 *
	 * @param room The unique identifier or number of the room associated with this point.
	 * @param xpos The x-coordinate of the point on the map/grid.
	 * @param ypos The y-coordinate of the point on the map/grid.
	 * @param Open A boolean indicating if the room/path associated with this point is currently considered open (accessible) or closed.
	 */
	public RedPoint(String room,  int xpos, int ypos, boolean Open) {
		this.roomnumber = room;
		this.x = xpos;
		this.y = ypos;
		this.isOpen = Open;
	}

	/**
	 * The identifier or number of the room/location.
	 */
	public String roomnumber;
	
	/**
	 * The x-coordinate of the point.
	 */
	public int x;
	
	/**
	 * The y-coordinate of the point.
	 */
	public int y;
	
	/**
	 * Status indicating whether the location or path associated with this point is open (accessible).
	 */
	public boolean isOpen;
	
	// Note: Standard getter methods (getRoomnumber(), getX(), getY(), is/getIsOpen()) are usually
	// added here in a typical Java class, but since the fields are public, direct access is used.
}