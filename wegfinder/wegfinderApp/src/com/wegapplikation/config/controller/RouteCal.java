package com.wegapplikation.config.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.wegapplikation.config.model.RoomData;

/**
 * The {@code RouteCal} class is a custom JPanel responsible for displaying the map,
 * handling user interactions for route selection, rendering the pathfinding visualization,
 * and executing the pathfinding logic (Breadth-First Search).
 */
public class RouteCal extends JPanel
{
	/** Flag indicating if a user is logged in (potentially for admin features like opening/closing paths). */
	public boolean loggedin = false;
	
	/** Flag indicating if the map is currently in path marking mode. */
	public boolean ismarkin = false;
	
	/** Reference to the text field for the starting location ("Von"). */
	public JFormattedTextField vonField;
	/** Reference to the text field for the ending location ("Nach"). */
	public JFormattedTextField nachField;
	
	
	public RoomData roomData= new RoomData();
	/** List to store all known room data points (the red points). **/
	public List<RoomData> Rooms = new ArrayList<>(); 						
	/** The selected starting room/point for the route. */
	public RoomData startPoint;
	/** The selected ending room/point for the route. */
	public RoomData endPoint;
   
	/** Array to hold the map image. */
	public BufferedImage[] imageHolder;
	
    /** List of all Points in the marked area that are currently open (color 1) and may be closed (set to 5). **/
    public ArrayList<Point> omarkedpath = new ArrayList<>();						
    /** List of all Points in the marked area that are currently closed (color 5) and may be opened (set to 1). **/
    public ArrayList<Point> cmarkedpath = new ArrayList<>();						
    /** List of all points currently colored/highlighted on the map for visualization (used during marking). */
    public ArrayList<Point> coloredfields = new ArrayList<>();
     
    /** Flag indicating if the mouse is currently held down on the panel (used for drag-marking). */
    public  boolean mouseheld;
    																				
    /** Runtime map data: A 2D array representing the map pixels with color codes (e.g., 0=white, 1=green, 5=red path, 8/9=walls). **/
    public int pixelArray[][];
    																				
    /** File path for the saved map array matrix. **/
    public String maparrayPath = "files/Maparray.txt";
    /** File path for the map image. */
    public String imagePath = "files/image.png";
    
    /**
     * Constructs the {@code RouteCal} panel, initializing the layout, loading the map image,
     * reading or generating the map array, and creating room buttons.
     */
    public RouteCal()
	{
		setLayout(null);
		
		
		try {
			
			
			imageHolder = new BufferedImage[1]; // Array to store the image
	        imageHolder[0] = ImageIO.read(new File(imagePath));
	        imageReader(imageHolder[0]);	// Load room data
	        
	        int b = 0;
	        for(RoomData rpoint: Rooms){												/** Creation of the room buttons on the panel **/
	        	b++;
	        	JButton button = new JButton(Integer.toString(rpoint.getId()));
	        	button.setSize(30, 13);
	        	// Set minimal margins for small buttons
	        	button.setMargin(new Insets(1, 1, 1, 1));

	        	// If the room is locked/inaccessible, set the background to red
	        	if(rpoint.isLocked()) {
	        	button.setBackground(Color.red);
	        	}
	        	button.setFont(new Font("Arial", Font.PLAIN, 9));
	        	int buttonWidth = button.getWidth();
	            int buttonHeight = button.getHeight();

	            // Center the button horizontally and place it slightly above the coordinate
	            int buttonX = rpoint.getX() - buttonWidth / 2;
	            int buttonY = rpoint.getY() - buttonHeight + 4; 
	            
	            
	            button.setLocation(buttonX, buttonY);
	            button.addActionListener(new ActionListener() {
	            	
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// Only allow point selection if not in path marking mode
						if(ismarkin == false) {
						maprepaint();
						addPoint(rpoint.getId(), rpoint.getDesignation(), rpoint.isLocked(), rpoint.getY(), rpoint.getX());
						}
					}
	            });
	        	add(button);
	        	
	        }
	        		
			File maparrayfile = new File(maparrayPath);
			this.pixelArray = new int[imageHolder[0].getHeight()][imageHolder[0].getWidth()];
			
			if(maparrayfile.exists()) {																			/** If the maparray.txt file exists - Read it and use it to populate the pixelArray **/
				
				BufferedReader reader = new BufferedReader(new FileReader(maparrayPath));
				
				String maparray = reader.readLine();
				String[] maparraydata = maparray.split(" ;");
                    
				int imageWidth = imageHolder[0].getWidth();
				int imageHeight = imageHolder[0].getHeight();
				
				// Reconstruct the 2D array from the saved string data
				for (int x = 0; x < imageWidth; x++) {
                    for (int y = 0; y < imageHeight; y++) { 
                    	// The array is stored row-major (y then x), but read sequentially
                    	pixelArray[y][x] = Integer.parseInt(maparraydata[y+imageHeight*x]);
                    }	
				}
				reader.close();
			}
			else 																								/** If the .txt file does not exist - Read the image pixel by pixel and create the pixelArray **/
			{
		
			if (imageHolder[0] != null) {
                for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                    for (int y = 0; y < imageHolder[0].getHeight(); y++) {
			
                    	 int rgb = imageHolder[0].getRGB(x, y);
                         Color color = new Color(rgb);
                    	
							if (color.getRed() == 255 && color.getGreen() < 100 && color.getBlue() < 100)		/** red area (Locked path/Zone 5) **/
				        	{
				        	 pixelArray[y][x] = 2;	
				        	}
				    	else if(color.getRed() < 100 && color.getGreen() == 255 && color.getBlue() < 100) {		/** green area (Open path/Zone 1) **/
				    		pixelArray[y][x] = 1;
				    		}
				    	else if(color.getRed() < 150 && color.getGreen() < 150 && color.getBlue() < 150) { 		/** dark gray/black walls (Zone 8) **/
				    		pixelArray[y][x] = 8;
				    	}
				    	else if(color.getRed() < 200 && color.getGreen() < 200 && color.getBlue() < 200) { 		/** light gray walls (Zone 9) **/
				    		pixelArray[y][x] = 9;
				    	}
				    	else {
				    		pixelArray[y][x] = 0;																/** white (anything else) **/
				    	}	
                    }
                }     
                updatemap(); // Save the newly generated map array to the file
			}	
			}

		}catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	/**
	 * Adds or removes a room as a start or end point based on a button click.
	 * * @param id The ID of the clicked room.
	 * @param designation The designation (name) of the room.
	 * @param open The open/locked status of the room.
	 * @param y The y-coordinate of the room.
	 * @param x The x-coordinate of the room.
	 */
	public void addPoint(int id, String designation ,boolean open, int y, int x) {
		maprepaint();
		
			RoomData selected = new RoomData(
					id,
					designation,
					open,
					y,
					x
					);
			if(selected.isLocked() == false) 
			{
			
			boolean wasremoved = false;
			
			/** Removes the Point if it is already selected **/
			if(startPoint != null) {												
				if(startPoint.getId() == selected.getId()) {
					startPoint = null;
					wasremoved = true;
					vonField.setText("");
				}
			}
			if(endPoint != null) {
				if(endPoint.getId() == selected.getId()) {
					endPoint = null;
					wasremoved = true;
					nachField.setText("");
				}
			}
			
			/** If no Point was removed, add the clicked Point **/
			if(wasremoved == false) {												
				if(startPoint == null){												
					startPoint = selected;
					vonField.setText(Integer.toString(selected.getId()));
				}
				else if(endPoint == null){
					endPoint = selected;
					nachField.setText(Integer.toString(selected.getId()));
				}
			}	
		}
	}  
	
	/**
	 * Adds the Point via a room number entered into a text field.
	 * * @param textroomnumber The room number entered as a String.
	 * @param textfield The {@code JFormattedTextField} that triggered the input (Von or Nach).
	 */
	public void addPoint(String textroomnumber, JFormattedTextField textfield) 
	{
		// Only allow point selection if not in path marking mode
		if(ismarkin == false) 
		{
			boolean roomexists = false;
			
			// Clear the current point and text field corresponding to the input field
			if(textfield == vonField) 
			{
				startPoint = null;	
				vonField.setText("");
			}
			else if(textfield == nachField)
			{
			endPoint = null;
			nachField.setText("");
			}
			
			if(textroomnumber != null) 
			{
				
				int roomnumber = Integer.parseInt(textroomnumber);
				
				// Find the corresponding RoomData object
				for (RoomData red: Rooms) 
				{
					// Check if the room ID matches and is not locked
					if(red.getId() == roomnumber && red.isLocked() == false) {
						if(textfield == vonField) {
						startPoint = red;
						vonField.setText(Integer.toString(red.getId()));
						}
						else if(textfield == nachField)
						{
							endPoint = red;
							nachField.setText(Integer.toString(red.getId()));
						}
						roomexists = true;
					}
				}
				// If roomexists is false, a message might be shown (but currently the block is empty)
				if(roomexists == false) {
					
				}
			}
		}
	}
	
	/**
	 * Sets the end point using a room number provided as a string.
	 * * @param textroomnumber The room number for the end point.
	 * @return The ID of the set end point as a string.
	 */
	public String addEnd(String textroomnumber) 
	{
				
		if(textroomnumber != null) 
		{
			
			int roomnumber = Integer.parseInt(textroomnumber);
			
			for (RoomData red: Rooms) 
			{
				// Set as end point if ID matches and it's not the same as the start point
				if(red.getId() == roomnumber && red.getId() != startPoint.getId()) {
					endPoint = red;
					nachField.setText(Integer.toString(red.getId()));
				}
			}
		}
		return Integer.toString(endPoint.getId());
	}
	
	/**
	 * Clears both the start and end points.
	 */
	public void removePoints() {
		startPoint = null;
		endPoint = null;
	}
	
    /**
     * Custom painting method to render the map based on the {@code pixelArray} data.
     * This method is called automatically by Swing when the component needs to be redrawn.
     * * @param g The Graphics context used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
    		
        super.paintComponent(g);
        
        Color color = new Color(0);
        
        if (imageHolder[0] != null) {
            for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                for (int y = 0; y < imageHolder[0].getHeight(); y++) {
                	                	
                    // Determine color based on the pixelArray value
                    switch(pixelArray[y][x])
                    {
                    case 1: // Open path (Green)
                    	color = Color.green;
                        g.setColor(color);
                        break;
                    case 5: // Closed/Blocked path (Dark Red/Brown)
                    	color = new Color(130,00,30);
                        g.setColor(color);
                        break;
		            case 8: // Dark Wall (Gray)
		            	color = Color.gray;
		                g.setColor(color);
		                break;
			        case 9: // Black Wall
			        	color = Color.black;
			            g.setColor(color);
			            break;
				    case 0: // Default/White area
				    	color = Color.white;
				        g.setColor(color);
				        break;
                    }
                    g.drawLine(x, y, x, y); // Draw a single pixel
                }   
            }
          
            /** Marked fields visualization **/
            for(Point point : coloredfields) {						
            	
            	// Highlight the marked field
            	if(pixelArray[point.y][point.x]  == 0) {
            	    // Use a darker version of the original color for white areas
                	g.setColor(new Color(imageHolder[0].getRGB(point.x, point.y)-80)); 
                	g.drawLine(point.x, point.y, point.x , point.y);
            	}
            	else if(pixelArray[point.y][point.x]  == 1) {
            		// Highlight open paths (1) in blue
            		g.setColor(Color.blue);
                	g.drawLine(point.x, point.y, point.x , point.y);	
            	}
            	else if(pixelArray[point.y][point.x]  == 5) {
            		// Highlight closed paths (5) in orange
            		g.setColor(Color.orange);
                	g.drawLine(point.x, point.y, point.x , point.y);	
            	}
            }	
        }
    } 
     
	
            
	
	/** * Handles the drag operation for path marking, highlighting a 9x11 pixel area
	 * around the cursor and recording points that are either open (1) or closed (5).
	 * * @param x The current x-coordinate of the mouse.
	 * @param y The current y-coordinate of the mouse.
	 */
	public void markpath(int x, int y) {   
		
		if(mouseheld == true) {
			
			// Iterate over a small area around the cursor
			for(int xarea = -4; xarea <= 4; xarea++){				
	    		for(int yarea = -5; yarea <= 5; yarea++) {
	    			try {
	    				// Check if the current pixel is an open path (1)
		    			if(pixelArray[y-yarea][x-xarea] == 1) {
		    				cmarkedpath.add(new Point(x-xarea, y-yarea)); // Can be closed
		    			}
		    			// Check if the current pixel is a closed path (5)
		    			else if(pixelArray[y-yarea][x-xarea] == 5) {
		    				omarkedpath.add(new Point(x-xarea, y-yarea)); // Can be opened
		    			}
		    			coloredfields.add(new Point(x-xarea, y-yarea)); // Add to fields to be highlighted
	    			} catch (ArrayIndexOutOfBoundsException ignored) {
	    				// Ignore if the marking goes outside the map boundaries
	    			}
	    			maprepaint();
	    		}
	        }
	    }
	}

	/**
	 * Permanently changes the state of all marked open paths (color 1) to closed (color 5)
	 * and updates the map file.
	 */
	public void closePath() {						
		for(Point pathpoint : cmarkedpath) {
			// Change status from open (1) to closed (5)
			pixelArray[pathpoint.y][pathpoint.x] = 5 ;
		}
		updatemap();
		
		// Clear marking arrays and disable marking mode
		cmarkedpath = new ArrayList<>();
		coloredfields = new ArrayList<>();
		ismarkin = false;
		maprepaint();
	}
	
	/**
	 * Permanently changes the state of all marked closed paths (color 5) to open (color 1)
	 * and updates the map file.
	 */
	public void openPath() {					
		
		for(Point pathpoint : omarkedpath) {
			// Change status from closed (5) to open (1)
			pixelArray[pathpoint.y][pathpoint.x] = 1 ;
		}
		updatemap();
		
		// Clear marking arrays and disable marking mode
		omarkedpath = new ArrayList<>();
		coloredfields = new ArrayList<>();
		ismarkin = false;
		maprepaint();
	}
	
	/**
	 * Clears all arrays dedicated to the map marking process and disables marking mode,
	 * causing the marked areas to disappear on the next repaint.
	 */
	public void clearMarkedPaths() {			
		coloredfields = new ArrayList<>();
		omarkedpath = new ArrayList<>();
		cmarkedpath = new ArrayList<>();
		ismarkin = false;
		maprepaint();
	}
	
	/**
	 * Writes the current state of the {@code pixelArray} to the {@code maparrayPath} file,
	 * persisting the map data.
	 */
	public void updatemap() {				
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(maparrayPath));
		
			// Write the 2D array content sequentially, separated by " ;"
            for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                for (int y = 0; y < imageHolder[0].getHeight(); y++) {
			writer.write(pixelArray[y][x]+" ;");
                }
            }
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Placeholder method to retrieve all room data using the {@code RoomData} model.
	 * * @param image The map image (currently unused but passed for context).
	 */
	public void imageReader(BufferedImage image) {			
		Rooms = roomData.getAllRooms();
	}
    
	
	/**
	 * Forces the component to repaint itself, triggering the {@code paintComponent} method.
	 */
	public void maprepaint() {
		this.repaint();
	}
	
	/** * Initiates the route calculation process using the Breadth-First Search (BFS) algorithm
	 * if both a start and end point have been selected.
	 * If points are missing, a dialog box is displayed.
	 */
	public void createRoute()
	{
		
		if(startPoint != null && endPoint != null) 
		{	
				int ex = endPoint.getX();
				int ey = endPoint.getY();
			
				int sx = startPoint.getX();
				int sy = startPoint.getY();	

		   // Initialize GameState with start and end points
		   GameState gameState = new GameState(pixelArray).withStartEnd(ex,ey,sx,sy);
           int step = 0;
           
           
        // Loop until the current position (sx, sy) reaches the end position (ex, ey) 
        while (!gameState.isStarttheEnd()) 
        {					
		      // Calculate the next optimal move
		      gameState = this.moveCat(gameState);				
		      int Sx = gameState.getsx();
	          int Sy =  gameState.getsy();
	          drawRoute(Sx, Sy); // Draw the current pixel of the path
		          
	    }
        // Reset all inputs after the route is found
        startPoint = null;					
        endPoint = null;
        vonField.setText("");
        nachField.setText("");
        
		}
		else{
		
			// Dialog to prompt the user to enter start and end rooms
			JDialog dialog = new JDialog();
            dialog.setSize(300, 100);
            dialog.setLayout(new FlowLayout(FlowLayout.CENTER));

            dialog.add(new JLabel("Bitte geben sie einen Start- und einen Endraum an")); // Please enter a start and end room

            JButton close = new JButton("Schließen");
            close.addActionListener(event -> dialog.dispose());
            dialog.add(close);

            dialog.setLocationRelativeTo(this);
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
		}
	}
	
	/**
	 * Draws a single pixel of the calculated route on the map using the component's Graphics object.
	 * * @param x The x-coordinate of the pixel to draw.
	 * @param y The y-coordinate of the pixel to draw.
	 */
	public void drawRoute(int x, int y) {					
		Graphics g = this.getGraphics();
		g.setColor(Color.RED);
		g.drawLine(x,y,x,y);
	}
	
	/**
	 * Calculates the next move direction for the current position using the shortest path algorithm.
	 * * @param gameState The current state containing positions and map data.
	 * @return A new {@code GameState} reflecting the move in the calculated direction.
	 */
	public GameState moveCat(GameState gameState) {									
        Direction catDir = gameState.getCatDir();  
          catDir =
              findShortestPathToMouse(
                  gameState.getpixelArray(),
                  gameState.getsx(), // Start X (current X)
                  gameState.getsy(), // Start Y (current Y)
                  gameState.getex(), // End X
                  gameState.getey()); // End Y
        return gameState.withMoveCat(catDir);
    }
    
    
    
    /**
     * Implements the Breadth-First Search (BFS) algorithm to find the shortest path
     * from the current position (sx, sy) to the end position (ex, ey).
     * * @param lab The 2D map array used as the search grid.
     * @param sx The starting (current) x-coordinate.
     * @param sy The starting (current) y-coordinate.
     * @param ex The ending (target) x-coordinate.
     * @param ey The ending (target) y-coordinate.
     * @return The {@code Direction} of the first step on the shortest path.
     * @throws IllegalStateException if no path can be found between the start and end points.
     */
    private Direction findShortestPathToMouse(int[][] lab, int sx, int sy, int ex, int ey) {					
        // Create a queue for all nodes to be processed in BFS order.
        Queue<Node> queue = new ArrayDeque<>();

        // Matrix that contains all fields which are already processed (10 = discovered)
        int[][] discovered = new int[605][820];
        
        /** Set the start position as discovered and add it to the queue **/
        discovered[sy][sx] = 10;
        queue.add(new Node(sx, sy, null));

        while (!queue.isEmpty()) {
          Node node = queue.poll();

          /** Check neighbors in every direction **/
          for (Direction dir : Direction.values()) {
            
        	  // Check bounds within the visible map area
        	  if(node.x + dir.getDx() >=10 && node.x + dir.getDx() <=800 && node.y + dir.getDy() >=10 && node.y + dir.getDy() <=595) {
            int newX = node.x + dir.getDx();
            int newY = node.y + dir.getDy();
            // Determine the initial direction from the very first move
            Direction newDir = node.initialDir == null ? dir : node.initialDir;
        	  
         
            // If the target is reached, return the initial direction taken
            if (newX == ex && newY == ey) {
              return newDir;
            }
      
			/** Check for non-walkable fields (9=black wall, 0=white area, 5=closed path) **/
			// Note: If not logged in, closed paths (5) are treated as walls.
            if (lab[newY][newX] == 9 || lab[newY][newX] == 0 || (lab[newY][newX] == 5 && loggedin == false)) continue;
            

          	/** If the field has not yet been discovered**/
            if (discovered[newY][newX] != 10) {
            	/** Set field as discovered and add to the queue**/
              discovered[newY][newX] = 10;
              queue.add(new Node(newX, newY, newDir));
            }
        	  }
          }
        }
        														/** If no way to the end is found **/
        
        // Display a dialog if no path is found
        JDialog dialog = new JDialog();
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER));

        dialog.add(new JLabel("Der Raum konnte nicht erreicht werden")); // The room could not be reached

        JButton close = new JButton("Schließen");
        close.addActionListener(event -> dialog.dispose());
        dialog.add(close);

        dialog.setLocationRelativeTo(this);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
        // Throw exception to stop execution since the target couldn't be reached
        throw new IllegalStateException("No path found");
        
      }

      /**
       * Helper class representing a node in the Breadth-First Search (BFS) algorithm.
       * It stores the position and the direction of the first step taken to reach this position.
       */
      private static class Node {
        final int x;
        final int y;
        final Direction initialDir; // The very first direction taken from the start point

        /**
         * Constructs a new BFS node.
         * @param x The x-coordinate of the node.
         * @param y The y-coordinate of the node.
         * @param initialDir The direction taken from the start point to reach this node.
         */
        public Node(int x, int y, Direction initialDir) {
          this.x = x;
          this.y = y;
          this.initialDir = initialDir;
        }
      }
	
}