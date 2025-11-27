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


public class RouteCal extends JPanel
{
	public boolean loggedin = false;
	
	public boolean ismarkin = true;
	
	public JFormattedTextField vonField;
	public JFormattedTextField nachField;
	
	
	public RoomData roomData= new RoomData();
	public List<RoomData> Rooms = new ArrayList<>(); 						/** Liste zur Speicherung der roten Punkte **/
	public RoomData startPoint;
	public RoomData endPoint;
   
	public BufferedImage[] imageHolder;
	
    public ArrayList<Point> omarkedpath = new ArrayList<>();						/**List of all Points that may be opened**/
    public ArrayList<Point> cmarkedpath = new ArrayList<>();						/**List of all Points that may be closed**/
    public ArrayList<Point> coloredfields = new ArrayList<>();
     
    public  boolean mouseheld;
    																				/**runtime mapdata**/
    public int pixelArray[][];
    																				/**datapaths**/
    public String maparrayPath = "files/Maparray.txt";
    public String imagePath = "files/image.png";
    
    public RouteCal()
	{
		setLayout(null);
		
		
		try {
			
			
			imageHolder = new BufferedImage[1]; // Array zur Speicherung des Bildes
	        imageHolder[0] = ImageIO.read(new File(imagePath));
	        imageReader(imageHolder[0]);	
	        
	        int b = 0;
	        for(RoomData rpoint: Rooms){												/**Creation of the roombuttons on the panel**/
	        	b++;
	        	JButton button = new JButton(Integer.toString(rpoint.getId()));
	        	button.setSize(30, 13);
	        	button.setMargin(new Insets(1, 1, 1, 1));

	        	if(rpoint.isLocked()) {
	        	button.setBackground(Color.red);
	        	}
	        	button.setFont(new Font("Arial", Font.PLAIN, 9));
	        	int buttonWidth = button.getWidth();
	            int buttonHeight = button.getHeight();

	            // Center the button horizontally and place it above the pixel
	            int buttonX = rpoint.getX() - buttonWidth / 2;
	            int buttonY = rpoint.getY() - buttonHeight + 4; // 5 pixels above
	            
	            
	            button.setLocation(buttonX, buttonY);
	            button.addActionListener(new ActionListener() {
	            	
					@Override
					public void actionPerformed(ActionEvent arg0) {
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
			
			if(maparrayfile.exists()) {																			/**if the maparray.txt file exists - Read it and use it to make the map**/
				
				BufferedReader reader = new BufferedReader(new FileReader(maparrayPath));
				
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
			
			if(startPoint != null) {																				/**removes the Point if it is already picked**/
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
			if(wasremoved == false) {																				/**if no Point was removed**/
				if(startPoint == null){																				/**add the clicked Point**/
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
	
	/**Adds the Point via the given textnumber and the used textfield**/
	public void addPoint(String textroomnumber, JFormattedTextField textfield) 
	{
		if(ismarkin == false) 
		{
			boolean roomexists = false;
			
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
				
				for (RoomData red: Rooms) 
				{
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
				if(roomexists == false) {
					
				}
			}
		}
	}
	
	public String addEnd(String textroomnumber) 
	{
				
		if(textroomnumber != null) 
		{
			
			int roomnumber = Integer.parseInt(textroomnumber);
			
			for (RoomData red: Rooms) 
			{
				if(red.getId() == roomnumber && red.getId() != startPoint.getId()) {
					endPoint = red;
					nachField.setText(Integer.toString(red.getId()));
				}
			}
		}
		return Integer.toString(endPoint.getId());
	}
	
	public void removePoints() {
		startPoint = null;
		endPoint = null;
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
	                        	color = new Color(130,00,30);
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
          
                for(Point point : coloredfields) {						/**marked fields visualised**/
                	
                	if(pixelArray[point.y][point.x]  == 0) {
	                	g.setColor(new Color(imageHolder[0].getRGB(point.x, point.y)-80));
	                	g.drawLine(point.x, point.y, point.x , point.y);
                	}
                	else if(pixelArray[point.y][point.x]  == 1) {
                		g.setColor(Color.blue);
	                	g.drawLine(point.x, point.y, point.x , point.y);	
                	}
                	else if(pixelArray[point.y][point.x]  == 5) {
                		g.setColor(Color.orange);
	                	g.drawLine(point.x, point.y, point.x , point.y);	
                	}
                }	
                }
            } 
     
	
            
	
	public void markpath(int x, int y) {   /** marking of the pressed area and saving the actual path**/
		
		if(mouseheld == true) {
			
			for(int xarea = -4; xarea <= 4; xarea++){				
	    		for(int yarea = -5; yarea <= 5; yarea++) {
	    			if(pixelArray[y-yarea][x-xarea] == 1) {
	    				cmarkedpath.add(new Point(x-xarea, y-yarea));
	    			}
	    			else if(pixelArray[y-yarea][x-xarea] == 5) {
	    				omarkedpath.add(new Point(x-xarea, y-yarea));
	    			}
	    			coloredfields.add(new Point(x-xarea, y-yarea));
	    				 maprepaint();
	    		}
	        }
	    }
	}

	public void closePath() {						/**Method that closes the dedicated marked points**/
		for(Point pathpoint : cmarkedpath) {
			pixelArray[pathpoint.y][pathpoint.x] = 5 ;
		}
		updatemap();
		
		cmarkedpath = new ArrayList<>();
		coloredfields = new ArrayList<>();
		ismarkin = false;
		maprepaint();
	}
	public void openPath() {					/**Method that open the dedicated marked points**/
		
		for(Point pathpoint : omarkedpath) {
			pixelArray[pathpoint.y][pathpoint.x] = 1 ;
		}
		updatemap();
		
		omarkedpath = new ArrayList<>();
		coloredfields = new ArrayList<>();
		ismarkin = false;
		maprepaint();
	}
	
	public void clearMarkedPaths() {			/**clears all Arrays dedicated to the mapmarking**/
		coloredfields = new ArrayList<>();
		omarkedpath = new ArrayList<>();
		cmarkedpath = new ArrayList<>();
		ismarkin = false;
		maprepaint();
	}
	
	public void updatemap() {				/**Updates the maparray file with the current data**/
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(maparrayPath));
		
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
	
	public void imageReader(BufferedImage image) {			/**Gets all the rooms through the File**/
    		
		Rooms = roomData.getAllRooms();
                
	}
    
	
	
	public void maprepaint() {
		this.repaint();
	}
	
	/** Route Calculation**/
	
	public void createRoute()
	{
		
		if(startPoint != null && endPoint != null) 
		{	
				int ex = endPoint.getX();
				int ey = endPoint.getY();
			
				int sx = startPoint.getX();
				int sy = startPoint.getY();	

		   GameState gameState = new GameState(pixelArray).withStartEnd(ex,ey,sx,sy);
           int step = 0;
           
           
        while (!gameState.isStarttheEnd()) 
        {					/**as long as the End is not reached **/
		      gameState = this.moveCat(gameState);				/** check **/
		      int Sx = gameState.getsx();
	          int Sy =  gameState.getsy();
	          drawRoute(Sx, Sy);
		          
	        }
        startPoint = null;					/**reset of all inputs **/
        endPoint = null;
        vonField.setText("");
        nachField.setText("");
        
		}
		else{
		
			JDialog dialog = new JDialog();
            dialog.setSize(300, 100);
            dialog.setLayout(new FlowLayout(FlowLayout.CENTER));

            dialog.add(new JLabel("Bitte geben sie einen Start- und einen Endraum an"));

            JButton close = new JButton("Schließen");
            close.addActionListener(event -> dialog.dispose());
            dialog.add(close);

            dialog.setLocationRelativeTo(this);
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
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
                  gameState.getsx(),
                  gameState.getsy(),
                  gameState.getex(),
                  gameState.getey());
        return gameState.withMoveCat(catDir);
    }
    
    
    
    private Direction findShortestPathToMouse(int[][] lab, int sx, int sy, int ex, int ey) {					/**Algorythm for the route **/
        // Create a queue for all nodes we will process in breadth-first order.
        // Each node is a data structure containing the cat's position and the
        // initial direction it took to reach this point.
        Queue<Node> queue = new ArrayDeque<>();

        																				/**matrix that contains all fields which are already processed**/
        int[][] discovered = new int[605][820];
        
        /**set the start position as discovered and adds it to the queue**/
        discovered[sy][sx] = 10;
        queue.add(new Node(sx, sy, null));

        while (!queue.isEmpty()) {
          Node node = queue.poll();

          														/** look into every direction**/
          for (Direction dir : Direction.values()) {
            // First step
        	  if(node.x + dir.getDx() >=10 && node.x + dir.getDx() <=800 && node.y + dir.getDx() >=10 && node.y + dir.getDx() <=595) {
            int newX = node.x + dir.getDx();
            int newY = node.y + dir.getDy();
            Direction newDir = node.initialDir == null ? dir : node.initialDir;
        	  
         
            if (newX == ex && newY == ey) {
              return newDir;
            }
      
												            	/** Is there a path or is it a non walkable field **/
												            	/**if not then check another direction **/
            
          if (lab[newY][newX] == 9 || lab[newY][newX] == 0 || lab[newY][newX] == 5 && loggedin == false) continue;
            

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
        
        JDialog dialog = new JDialog();
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER));

        dialog.add(new JLabel("Der Raum konnte nicht erreicht werden"));

        JButton close = new JButton("Schließen");
        close.addActionListener(event -> dialog.dispose());
        dialog.add(close);

        dialog.setLocationRelativeTo(this);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
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

