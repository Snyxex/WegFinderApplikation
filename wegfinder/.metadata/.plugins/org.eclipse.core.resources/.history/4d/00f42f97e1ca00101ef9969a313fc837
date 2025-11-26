package com.wegapplikation.config.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.wegapplikation.config.controller.Direction;
import com.wegapplikation.config.controller.GameState;

public class RouteCal extends JPanel
{
	public ArrayList<RedPoint> redPoints = new ArrayList<>(); /** Liste zur Speicherung der roten Punkte **/
	public ArrayList<RedPoint> visibleRedPoints = new ArrayList<>(); 
	public RedPoint startPoint;
	public RedPoint endPoint;
   
	public BufferedImage[] imageHolder;
	
    public ArrayList<Point> markedpath = new ArrayList<>();
    public ArrayList<Point> coloredfields = new ArrayList<>();
     
    public  boolean mouseheld;
    																				/**runtime mapdata**/
    public int pixelArray[][];
    																				/**datapaths**/
    public String maparraypath = "files/Maparray.txt";
    public String imagePath = "files/image.png";
    
	public RouteCal()
	{
		setLayout(null);
		
		
		try {
			
			
			imageHolder = new BufferedImage[1]; // Array zur Speicherung des Bildes
	        imageHolder[0] = ImageIO.read(new File(imagePath));
	        imageReader(imageHolder[0]);	
		
	        int b = 0;
	        
	        for(RedPoint rpoint: redPoints){
	        	b++;
	        	JButton button = new JButton(rpoint.roomnumber);
	        	button.setSize(15, 20);
	        //	button.setFont(new Font("Arial", Font.PLAIN, 40)); Plan to get smaller Text
	        	int buttonWidth = button.getWidth();
	            int buttonHeight = button.getHeight();

	            // Center the button horizontally and place it above the pixel
	            int buttonX = rpoint.x - buttonWidth / 2;
	            int buttonY = rpoint.y - buttonHeight + 4; // 5 pixels above

	            button.setLocation(buttonX, buttonY);
	            button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						addPoint(rpoint.roomnumber, rpoint.x, rpoint.y, rpoint.isOpen);
						
					}
	            });
	        	add(button);
	        	
	        }
	        
	        
			
			
			File maparrayfile = new File(maparraypath);
			this.pixelArray = new int[imageHolder[0].getHeight()][imageHolder[0].getWidth()];
			
			if(maparrayfile.exists()) {																			/**if the maparray.txt file exists - Read it and use it to make the map**/
				
				BufferedReader reader = new BufferedReader(new FileReader(maparraypath));
				
				String maparray = reader.readLine();
				String[] maparraydata = maparray.split(" ;");
                    
				int imageWidth = imageHolder[0].getWidth();
				int imageHeight = imageHolder[0].getHeight();
				
				for (int x = 0; x < imageWidth; x++) {
                    for (int y = 0; y < imageHeight; y++) { 
                    	
                    	pixelArray[y][x] = Integer.parseInt(maparraydata[y+imageHeight*x]);
                    }	
				}
			}
			else 																								/**if the .txt file does not exist - Read the image and create the maparray.txt file**/
			{
		
			if (imageHolder[0] != null) {
                for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                    for (int y = 0; y < imageHolder[0].getHeight(); y++) {
			
                    	 int rgb = imageHolder[0].getRGB(x, y);
                         Color color = new Color(rgb);
                    	
							if (color.getRed() == 255 && color.getGreen() < 100 && color.getBlue() < 100)		/** red  **/
				        	{
				        	 pixelArray[y][x] = 2;	
				        	}
				    	else if(color.getRed() < 100 && color.getGreen() == 255 && color.getBlue() < 100) {		/** green **/
				    		pixelArray[y][x] = 1;
				    		}
				    	else if(color.getRed() < 150 && color.getGreen() < 150 && color.getBlue() < 150) { 		/**black walls**/
				    		pixelArray[y][x] = 8;
				    	}
				    	else if(color.getRed() < 200 && color.getGreen() < 200 && color.getBlue() < 200) { 		/**black walls**/
				    		pixelArray[y][x] = 9;
				    	}
				    	else {
				    		pixelArray[y][x] = 0;																/**white anything else**/
				    	}	
                    }
                }     
                updatemap();
			}	
			}
			

		}catch(Exception E) {
			
		}
	}
																												/**Adds the Point of the clicked Button**/
	public void addPoint(String roomnumber, int x, int y, boolean open) {
		RedPoint selected = new RedPoint(
				roomnumber,
				x,
				y,
				open
				);
		boolean wasremoved = false;
		
		if(startPoint != null) {																				/**removes the Point if it is already picked**/
			if(startPoint.roomnumber == selected.roomnumber) {
				startPoint = null;
				wasremoved = true;
			}
		}
		if(endPoint != null) {
			if(endPoint.roomnumber == selected.roomnumber) {
				endPoint = null;
				wasremoved = true;
			}
		}
		if(wasremoved == false) {																				/**if no Point was removed**/
			if(startPoint == null){																				/**add the clicked Point**/
				startPoint = selected;
			}
			else if(endPoint == null){
				endPoint = selected;
			}
		}
			
		
	}
        
	public void addStart(String roomnumber) {
		for (RedPoint red: redPoints) 
		{
			if(red.roomnumber == roomnumber && red.roomnumber != endPoint.roomnumber) {
				startPoint = red;
			}
			else if(roomnumber == "") {
				startPoint = null;
			}
		}
	}
	public void addEnd(String roomnumber) {
		for (RedPoint red: redPoints) 
		{
			if(red.roomnumber == roomnumber && red.roomnumber != startPoint.roomnumber) {
				endPoint = red;
			}
			else if(roomnumber == "") {
				endPoint = null;
			}
		}
	}
	
            @Override
            protected void paintComponent(Graphics g) {
            		
                super.paintComponent(g);
                
                Color color = new Color(0);
                
                if (imageHolder[0] != null) {
                    for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                        for (int y = 0; y < imageHolder[0].getHeight(); y++) {
                        	                	
                            switch(pixelArray[y][x])
                            {
                            case 1:
                            	color = Color.green;
                                g.setColor(color);
                                break;
	                        case 5:
	                        	color = new Color(200,50,50);
	                            g.setColor(color);
	                            break;
		                    case 8:
		                    	color = Color.gray;
		                        g.setColor(color);
		                        break;
			                case 9:
			                	color = Color.black;
			                    g.setColor(color);
			                    break;
				            case 0:
				            	color = Color.white;
				                g.setColor(color);
				                break;
                            }
                            g.drawLine(x, y, x, y);
                            											/**fill .txt-document with the mapmatrix **/
                        }   
                        }
          
             // Zeichne die sichtbaren roten Punkte
                for(Point point : coloredfields) {
                	int checkrgb = imageHolder[0].getRGB(point.x, point.y);
                	if(checkrgb  == -1) {
	                	g.setColor(new Color(imageHolder[0].getRGB(point.x, point.y)-100));
	                	g.drawLine(point.x, point.y, point.x , point.y);
                	}
                	else if(checkrgb == -16711936) {
                		g.setColor(Color.red);//new Color(imageHolder[0].getRGB(point.x, point.y)+1000
	                	g.drawLine(point.x, point.y, point.x , point.y);	
                	}
                }
                }
            } 
     
	
            
	
	public void markpath(int x, int y) {   /** marking of the pressed area and saving the actual path**/
		
		if(mouseheld == true) {
			
			for(int xarea = -5; xarea <= 5; xarea++){					
	    		for(int yarea = -5; yarea <= 5; yarea++) {
	    			if(pixelArray[y-yarea][x-xarea] == 1) {
	    				markedpath.add(new Point(x-xarea, y-yarea));
	    			}
	    			coloredfields.add(new Point(x-xarea, y-yarea));
	    			
	    				 maprepaint();
	    			
	    		}
	        }
	    }
	}
	
	public void closepath() {
		
		for(Point pathpoint : markedpath) {
			pixelArray[pathpoint.y][pathpoint.x] = 5 ;
		}
		updatemap();
		
		markedpath = new ArrayList<>();
		coloredfields = new ArrayList<>();
		maprepaint();
	}
	
	public void updatemap() {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(maparraypath));
		
			int imageWidth = imageHolder[0].getWidth();
			int imageHeight = imageHolder[0].getHeight();
		
	
            for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                for (int y = 0; y < imageHolder[0].getHeight(); y++) {
			writer.write(pixelArray[y][x]+" ;");
                }
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void imageReader(BufferedImage image) {
    	
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                // �berpr�fen, ob der Pixel rot ist
                if (color.getRed() > 200 && color.getGreen() < 100 && color.getBlue() < 100) {
                    redPoints.add(new RedPoint("10"+x, x, y, true)); // Rote Punkte speichern
                }
            }
        }
    }
	
	
	public void maprepaint() {
		this.repaint();
	}
	
	/** Route Calculation**/
	
	public void createRoute()
	{
		
				int mx = startPoint.x;
				int my = startPoint.y;
			
				int cx = endPoint.x;
				int cy = endPoint.y;	

		   GameState gameState = new GameState(pixelArray).withCatMousePositions2(mx,my,cx,cy);
           int step = 0;
           
           
        while (!gameState.hasCatEatenMouse()) {					/**as long as the End is not reached **/
		      gameState = this.moveCat(gameState);				/** check **/
		      int Cx = gameState.getCx();
	          int Cy =  gameState.getCy();
	        //  System.out.printf("Step %2d: catDir = %s%n", ++step, gameState.getCatDir());
	          drawRoute(Cx, Cy);
	          
        }
	}
	public void drawRoute(int x, int y) {					/** drawing of the actual route**/
		Graphics g = this.getGraphics();
		g.setColor(Color.RED);
		g.drawLine(x,y,x,y);
	}
	
	public GameState moveCat(GameState gameState) {									/**takes the positions of the start and end with the matrix to calculate the path**/ 						
        Direction catDir = gameState.getCatDir();  
          catDir =
              findShortestPathToMouse(
                  gameState.getpixelArray(),
                  gameState.getCx(),
                  gameState.getCy(),
                  gameState.getMx(),
                  gameState.getMy());
        return gameState.withMoveCat(catDir);
    }
    
    
    
    private Direction findShortestPathToMouse(int[][] lab, int cx, int cy, int mx, int my) {					/**Algorythm for the route **/
        // Create a queue for all nodes we will process in breadth-first order.
        // Each node is a data structure containing the cat's position and the
        // initial direction it took to reach this point.
        Queue<Node> queue = new ArrayDeque<>();

        																				/**matrix that contains all fields which are already processed**/
        int[][] discovered = new int[605][820];
        
        /**set the start position as discovered and adds it to the queue**/
        discovered[cy][cx] = 10;
        queue.add(new Node(cx, cy, null));

        while (!queue.isEmpty()) {
          Node node = queue.poll();

          														/** look into every direction**/
          for (Direction dir : Direction.values()) {
            // First step
        	  if(node.x + dir.getDx() >=10 && node.x + dir.getDx() <=800 && node.y + dir.getDx() >=10 && node.y + dir.getDx() <=595) {
            int newX = node.x + dir.getDx();
            int newY = node.y + dir.getDy();
            Direction newDir = node.initialDir == null ? dir : node.initialDir;
        	  
         
            if (newX == mx && newY == my) {
              return newDir;
            }
      
												            	/** Is there a path or is it a non walkable field **/
												            	/**if not then check another direction **/
          if (lab[newY][newX] == 9 || lab[newY][newX] == 0 || lab[newY][newX] == 5) continue;
            

          														/** if the field has not yet been discovered**/
            if (discovered[newY][newX] != 10) {
            													/** set field as discovered**/
              discovered[newY][newX] = 10;
              queue.add(new Node(newX, newY, newDir));
            }
        	  }
          }
        }
        														/**if no way to the end is found**/
        throw new IllegalStateException("No path found");
      }

      private static class Node {
        final int x;
        final int y;
        final Direction initialDir;

        public Node(int x, int y, Direction initialDir) {
          this.x = x;
          this.y = y;
          this.initialDir = initialDir;
        }
      }
	
}

