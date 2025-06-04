package controll;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class App {
    private static ArrayList<Point> redPoints = new ArrayList<>(); // Liste zur Speicherung der roten Punkte

    public static void main(String[] args) {
        // Beispielaufruf der Methode
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(loadImageToPanel("farbenlesen/src/controll/Image2.png")); 
        frame.setSize(900, 700);
        frame.setVisible(true);
    }

    public static JPanel loadImageToPanel(String imagePath) {
        BufferedImage[] imageHolder = new BufferedImage[1]; // Array zur Speicherung des Bildes

        JPanel panel = new JPanel() {
            {
                try {
                    imageHolder[0] = ImageIO.read(new File(imagePath));
                    imageReader(imageHolder[0]); // Rote Punkte erkennen
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imageHolder[0] != null) {
                    for (int x = 0; x < imageHolder[0].getWidth(); x++) {
                        for (int y = 0; y < imageHolder[0].getHeight(); y++) {
                            int rgb = imageHolder[0].getRGB(x, y);
                            g.setColor(new Color(rgb));
                            g.drawLine(x, y, x, y); // Zeichne jeden Pixel, außer rot
                        }
                    }
                }

                // Zeichne die roten Punkte, wenn sie sichtbar sind
                g.setColor(Color.RED);
                for (Point point : redPoints) {
                    g.fillOval(point.x - 5, point.y - 5, 10, 10); // Zeichne einen roten Punkt
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
                
                // Überprüfen, ob der Klick in der Nähe eines roten Punktes war
                if (isNearRedPoint(x, y)) {
                    System.out.println("Roter Punkt sichtbar gemacht.");
                    panel.repaint(); // Panel neu zeichnen
                } else {
                    System.out.println("Klick außerhalb der roten Punkte.");
                }
            }
        });

        return panel;
    }

    public static void imageReader(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                // Überprüfen, ob der Pixel rot ist
                if (color.getRed() > 200 && color.getGreen() < 100 && color.getBlue() < 100) {
                    redPoints.add(new Point(x, y)); // Rote Punkte speichern
                }
            }
        }
    }

    private static boolean isNearRedPoint(int x, int y) {
        for (Point point : redPoints) {
            if (point.distance(x, y) <= 5) { // 10 Pixel Nähe
                return true;
            }
        }
        return false;
    }

    public void imageReader() {
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