package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * wyswietla wlasciwa klase rozgrywki
 */
public class Board extends View{
    /**licbza choragiewek do podlozenia przez gracza*/
    JLabel minenToPut;
    /**tablica przyciskow na kazda instancje pola minowego*/
    JButton[][] buttons;
    /**etykiety, ktore sie wyswietlaja po zdjeciu danego przyciska, jedna etykieta na jedno pole planszy*/
    JLabel[][] playground;
    /**panele sluzace do umieszczania na kazdym guzika a po jego zdjeciu odpowiedniej etykiety*/
    JPanel[][] panel;
    /**jeszcze nie uzyta, koncepcja*/
    JLabel counter;
    /**ilosc plytek na szerokosc*/
    int sizeX;
    /** ilosc plytek na wysokosc*/
    int sizeY;
    /** domyslna szerokosc plytki*/
    int width=30;
    /**domyslna wysokosc plytki*/
    int height=30;

    /** ustawia ktore panele wyswietlaja sie po sobie*/
    CardLayout card;
    /** kontener zawierajcy plansze rozgrywki*/
    Container container;
    /**panel na ktorym sa rozne karty*/
    JPanel pane;
    /**panel ktory sie wyswietla jak sie przegralo*/
    JPanel paneLoser;
    /**panel ktory sie wyswietla jak sie wygralo*/
    JPanel panelWin;
    /** guzik wyswietlajacy sie na panelWin i panelLoser by przejsc do menu*/
    JButton button_menu = new JButton("DO MENU");
    /**
     * klasa inicjalizujaca plansze rozgrywki
     * @author PAWEL
     * @param sizeX ilosc plytek na szerokosc
     * @param sizeY ilosc plytek na wysokosc
     * @param specifications tablica specyfikujaca kazde pole gry, ozanacz jaka wartosc zostanie wyswietlona na etykiecie po zdjeciu przycisku
     */
   public Board(int sizeX,int sizeY,int[][] specifications,int minen)
    {
        counter=new JLabel("MINY");
        this.sizeX =sizeX;
        this.sizeY =sizeY;

        panel=new JPanel[sizeX][sizeY];
        card=new CardLayout();
        pane =new JPanel();
        pane.setLayout(card);
        paneLoser = new Functions.MyBackground("looser.png");
        panelWin=new Functions.MyBackground("victory.jpg");        //this.getContentPane().setLayout(new BorderLayout());
        container=new Container();
        container.setLayout(new GridLayout(sizeX,sizeY));
        minenToPut=new JLabel("MINY DO PODLOZENIA:"+minen);

        playground=new JLabel[sizeX][sizeY];
        buttons= new JButton[sizeX][sizeY];

        this.frame.setSize(sizeY*height,sizeX*width);

        BoardSetup(specifications);

        paneLoser.add(button_menu);

        pane.add("a1",container);
        pane.add("winner",panelWin);
        pane.add("loser",paneLoser);

        this.frame.setLayout(new BorderLayout());
        this.frame.add(minenToPut,BorderLayout.PAGE_START);
        this.frame.add(pane,BorderLayout.CENTER);
        this.frame.setTitle("PLANSZA SAPER");
        this.frame.setLocationRelativeTo(null);
        this.frame.setSize(sizeY*width,sizeX*height);
        this.frame.setVisible(true);
    }

    /**
     * ustawia odpowiednia wartosci do wyswietlenia na planszy i dodaje plytki do kontenera
     * @param specifications wartosci planszy
     */
    void BoardSetup(int[][] specifications)
    {
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);

        for(int i=0;i<sizeX;i++)
        {
            for (int k=0;k<sizeY;k++)
            {
                playground[i][k]=new JLabel();//String.valueOf(SwingConstants.CENTER));
                buttons[i][k]=new JButton();
                panel[i][k]=new JPanel();

                if(specifications[i][k]==9)
                    Functions.setImageLabel(playground[i][k],20,20,"mine1.jpg");

                playground[i][k].setBorder(border);
                panel[i][k].setLayout(new GridLayout());

                panel[i][k].add(buttons[i][k]);
                panel[i][k].setBackground(Color.WHITE);

                if(specifications[i][k]!=0&&specifications[i][k]<9)
                {
                    playground[i][k].setText("" + specifications[i][k]);
                    playground[i][k].setVerticalAlignment(SwingConstants.CENTER);
                    playground[i][k].setHorizontalAlignment(SwingConstants.CENTER);
                    panel[i][k].setBackground(Color.LIGHT_GRAY);
                }
                container.add(panel[i][k]);
            }

        }
    }

    /**
     * sluzy do ukywania konkretnego przyciku i na jego miejcu wyswietlania etykiety
     * @param x pozycja x przycisku
     * @param y pozycja y przycisku
     */
    void hideButtons(int x, int y)
    {
        buttons[x][y].setVisible(false);
        panel[x][y].remove(buttons[x][y]);
        panel[x][y].add(playground[x][y]);
        playground[x][y].setVisible(true);
    }

    /**
     * sluzy do ukrywania calej tablicy przyciskow
     * @param board tablica z parametrami calej planszy pokazujaca ktore guziki maja byc ukryte, wartosc wieksza niz 14 znaczy ze
     *              guzik jest do ukrycia
     */
    void hideManyButtons(int [][]board)
    {
        for(int i = 0; i< sizeX; i++) {
            for (int k = 0; k < sizeY; k++) {
                if (board[i][k] > 14) {
                    buttons[i][k].setVisible(false);
                    panel[i][k].remove(buttons[i][k]);
                    panel[i][k].add(playground[i][k]);
                    playground[i][k].setVisible(true);
                }
            }
        }
    }

    /**
     * wywoluje funkcje, ktora ustawia odpowiednia ikone na przycisku
     * @param x wspolzedne guzika x
     * @param y wpolzedne gzuika y
     * @param name nazwa ikony ktora ma ustawic
     */
    void rightClick(int x, int y, String name,int minen)
    {
        minenToPut.setText("MINY DO PODLOZENIA: "+minen);
        Functions.setImage(buttons[x][y],buttons[x][y].getWidth(),buttons[x][y].getHeight(),name);
    }

    /**
     * ustawia listemery na przyciki na planszy
     * @param click Mouse Listener
     *
     */
    void listenBoard(MouseListener click)
    {
        for(int i = 0; i< sizeX; i++)
        {
            for (int k = 0; k< sizeY; k++)
            {
                buttons[i][k].addMouseListener(click);
            }
        }
    }

    /**
     * sluzy do czekania az uzytkownik postanowi wysjc z panelu koncowego do panelu start
     * @param click
     */
    void listenEndGame(ActionListener click)
    {
        button_menu.addActionListener(click);
    }

    /**
     * sluzy do zorientowania sie na ktory przycisk sie kliknelo
     * @param hash na jego podstawie szukamy guzika, ktorego wspolzedne mamy zwrocic
     * @return zwraca tablice z wspolzedna x i y naszego przycisku na planszy
     */
    public int[] returnHashCode(int hash)
    {
        int[] value ={0,0};
        int y;
        for(int i = 0; i< sizeX; i++)
        {
            for(int k = 0; k< sizeY; k++)
            {
                if(buttons[i][k].hashCode()==hash)
                {
                    value[0]=i;
                    value[1]=k;
                    return value;
                }
            }
        }
        return value;
    }

    /**
     * panel ktory sie wyswietla jak sie przegra rozgrywke
     * @param x
     * @param y
     */
    void loser(int x, int y)
    {
        minenToPut.setVisible(false);
        playground[x][y].setVisible(true);

        //card.next(pane);
        card.show(pane,"loser");
    }

    /**
     * panel ktory sie wyswietla jak sie wygra rozgrywke
     */
    void winner()
    {
        minenToPut.setVisible(false);
        button_menu.setText("DO MENU ZWYCIEZCO");
        panelWin.add(button_menu);
        card.show(pane,"winner");
    }
}



/*  void listenBoard (MouseListener click, ActionListener clc)
    {
        for(int i = 0; i< sizeX; i++)
        {
            for (int k = 0; k< sizeY; k++)
            {
                //  bStart.addActionListener(click);
                buttons[i][k].setName("sss");
                 //System.out.println(buttons[i][k].getName());
                //buttons[i][k].setText("sss");
                //buttons[i][k].setText("dd");
                buttons[i][k].setName("aa");
              //  buttons[i][k].se
                buttons[i][k].setActionCommand(i+" "+k);
                buttons[i][k].addMouseListener(click);
                buttons[i][k].addActionListener(clc);
            }
        }
    }*/



/*void listen (ActionListener click)
    {
        for(int i=0;i<dimensionX;i++)
        {
            for (int k=0;k<dimensionY;k++)
            {
              //  bStart.addActionListener(click);
               // buttons[i][k].setName("sss");
                buttons[i][k].setActionCommand(i+" "+k);
                buttons[i][k].addActionListener(click);
            }
        }
    }*/


/*

                {
                    try {
                        Icon img = new ImageIcon("mine1.jpg");//("flag.png");
                        Image image =((ImageIcon) img).getImage();
                        Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
                        Icon final_picture = new ImageIcon(newimg);  // transform it back
                     //   playground[i][k].setIcon(final_picture);

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
 */
//http://komputerowyglownie.blogspot.com/2013/08/klon-sapera-w-javie.html