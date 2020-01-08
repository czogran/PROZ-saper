package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * klasa majaca na celu zrobienie statycznych metod do obslugi funkcjonalnosci innych klas
 * @author PAWEL
 */
public class Functions {
    /**
     * funkcja umieszczajaca na podanym przycisku obrazek o danej nazwie i wymiarach
     * @param button guzik na którym mamy umieszczamy obrazek
     * @param width szerokosc obrazka
     * @param hight wysokosc obrazka
     * @param name nazwa obrazka
     */
    public  static void setImage(JButton button, int width, int hight, String name)
    {
        try {
            // Image img = ImageIO.read(getClass().getResource("question.jpg"));
            //System.out.println(width);

            Icon img = new ImageIcon(name);//("flag.png");
            Image image =((ImageIcon) img).getImage();
            Image newimg = image.getScaledInstance(width, hight,  java.awt.Image.SCALE_SMOOTH);
            Icon final_picture = new ImageIcon(newimg);  // transform it back

            //img.paintIcon(buttons[i][k]);

            button.setIcon(final_picture);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }



    /**
     * miala zluzyc do umieszczanie obrazku na JLabel, nie jest uzywana
     * @param label
     * @param width
     * @param hight
     * @param name
     */
    public  static void setImageLabel(JLabel label, int width, int hight, String name)
    {
        try {
            // Image img = ImageIO.read(getClass().getResource("question.jpg"));
            System.out.println(width);

            Icon img = new ImageIcon(name);//("flag.png");
            Image image =((ImageIcon) img).getImage();
            Image newimg = image.getScaledInstance(width, hight,  java.awt.Image.SCALE_SMOOTH);
            Icon final_picture = new ImageIcon(newimg);  // transform it back
            System.out.println(width);

            //img.paintIcon(buttons[i][k]);

            label.setIcon(final_picture);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * nie uzywana, koncepcja jej TODO
     */
    class Panel2 extends JPanel {
        String name;
        Panel2(int width,int height,String name) {
            // set a preferred size for the custom panel.
            this.name=name;
            setPreferredSize(new Dimension(width,height));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage img = null;

            try {

                img = ImageIO.read(new File(name));
            } catch (IOException e) {
            }
            //boolean b = g.drawImage(img);
            g.drawString("BLAH", 20, 20);
            g.drawRect(200, 200, 200, 200);
        }
    }

    /**
     * nie uzywana, koncepcja TODO
     */
    static class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(10,10,100,100);
        }
    }

    /**
     * enumy ustalajace stan rozgrywki
     */
    public enum Stan
    {
        WINNER (5),
        LOSER (1),
        ZEROS(2),
        NUMBER(0),
        OTHER(3)
        ;
        Stan(int i) {
        }
    }

    /**
     * ustawia obrazek na panelu
     */
    public static class MyBackground extends JPanel {
    private Image image;

        /**
         * ustawia obrazek
         * @param file nazwa obrazka do wczytania
         */
        public MyBackground(String file) {
            setBackground(new Color(0, true));
            image = new ImageIcon(file).getImage();
        }
        @Override

        public void paintComponent(Graphics g) {

            //Paint background first

            g.drawImage (image, 0, 0, getWidth (), getHeight (), this);
            //Paint the rest of the component. Children and self etc.
            super.paintComponent(g);

        }

    }



}
