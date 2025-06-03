package controll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args){

        
        // Beispielaufruf der Methode
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(loadImageToPanel("src/controll/Image2.png")); 
        frame.setSize(900, 700);
        frame.setVisible(true);
    }

    public static JPanel loadImageToPanel(String imagePath) {
        JPanel panel = new JPanel() {
            private BufferedImage image;
    
            {
                try {
                    image = ImageIO.read(new File(imagePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, this);
                }
            }
        };
    
        panel.setPreferredSize(new Dimension(600, 600)); // Größe des Panels anpassen
    
        // MouseListener hinzufügen
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("Pixel-Koordinaten: (" + x + ", " + y + ")");
            }
        });
    
        return panel;
    }

    public void imageReader(){
        try {
            File bildDatei = new File("src/controll/Image2.png");
            
            BufferedImage bild = ImageIO.read(bildDatei);
    
          
            if (bild != null) {
              
                System.out.println("Bild erfolgreich geladen.");
               
            } else {
               
                System.err.println("Fehler beim Lesen des Bildes.");
            }
        } catch (IOException e) {
          
            System.err.println("Fehler beim Lesen des Bildes: " + e.getMessage());
            e.printStackTrace();
        }
    
    } 
     
   
}
