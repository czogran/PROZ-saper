package com.company;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * klasa prezentujaca poczatkowy wyglad ekranu
 * @author PAWEL
 */
public class Start extends View{
    /** szerokosc calego okienka*/
    int dimensionX =100;
    /** wysokosc calego okienka*/
    int dimensionY =400;
    /**przycisk uruchamiajacy plansze*/
    JButton bStart;
    /**zwieksza szerokosc o 1 po kliknieciu*/
    BasicArrowButton upX;
    /**zmniejsza szerokosc o jeden po kliknieciu*/
    BasicArrowButton downX;
    /**zwieksza wyosokosc o jeden po klknieciu*/
    BasicArrowButton upY;
    /**zmniejsza szerokosc o jeden po kliknieciu*/
    BasicArrowButton downY;
    /**zwieksza liczbe min o 1 po kliknieciu*/
    BasicArrowButton upMinen;
    /**zmniejsza liczbe min o 1 po kliknieciu*/
    BasicArrowButton downMinen;
    /**panel sluzacy do umieszczenia elementow opisujacych szerokosc*/
    JPanel panelX;
    /**panel sluzacy do umieszczanie elementow opisujacych wysokosc*/
    JPanel panelY;
    /**paenel sluzacy do umieszczania elementow opisujacyh liczbe min*/
    JPanel panelMinen;

    /** sluzy do podania napisu z prosba do uzytkownika o rozmiar x*/
    JLabel labelSizeX;
    /** sluzy do podania napisu z prosba do uzytkownika o rozmiar y*/
    JLabel labelSizeY;
    /** sluzy do podnia napisu z prosba do uzytkownika o podanie liczby min*/
    JLabel labelSizeMinen;

    /**wyswietla sie na nim liczba min jaka zostanie podlozona*/
    JTextPane areaMinen;
    /**wyswietla sie na nim jak szeroka bedzie plansza*/
    JTextPane areaSizeX;
    /**wyswietla sie na nim jak wysoka bedzie plansza*/
    JTextPane areaSizeY;


    /**
     *klasa inicjalizujaca ekran startowy, sluzy do zadeklarowania liczby min oraz wymierow planszy
     * @author PAWEL
     * @param functionSizeX podaje domyslna szerokosc planszy
     * @param functionSizeY podaje domyslna wyskosc planszy
     * @param minen podaje domyslna liczbe min na planszy
     *
     */
    public Start(int functionSizeX, int functionSizeY, int minen)
    {
        bStart =new JButton("START");


        addPanelX(functionSizeX);
        addPanelY(functionSizeY);
        addPanelMinen(minen);

        centerText();
        //this.getContentPane().add(panelX);
        //this.getContentPane().add(panelY);
        this.frame.getContentPane().setLayout(new GridLayout(7,1));
        addElements();
        //this.frame.setSize(functionSizeX, functionSizeY);
        //this.frame.setVisible(true);

        this.frame.setSize(350,400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setTitle("SAPER PARAMETRY");
        this.frame.setVisible(true);


    }

    /**
     * sluzy do dodania elementow do JFrame
     */
    void addElements()
    {
        this.frame.add(labelSizeX);
        this.frame.add(panelX);
        this.frame.add(labelSizeY);
        this.frame.add(panelY);
        this.frame.add(labelSizeMinen);
        this.frame.add(panelMinen);
        this.frame.add(bStart);
    }

    /**
     * sluzy do ustawienia elementow odpowiedzialnych za wczytywanie na wysokosc
     * @param functionSizeX wielkosc domyslna wysokosci
     */
    void addPanelX(int functionSizeX)
    {
        panelX =new JPanel( );
        panelX.setLayout(new GridLayout());
        labelSizeX =new JLabel("liczba pól na wysokosc z zakresu 4-50");
        areaSizeX =new JTextPane();
        areaSizeX.setText(String.valueOf(functionSizeX));
        areaSizeX.setFont(new Font("Courier New", Font.CENTER_BASELINE, 30));
        areaSizeX.setEditable(false);
        upX =new BasicArrowButton(SwingConstants.NORTH);
        upX.setActionCommand("upX");
        downX =new BasicArrowButton(SwingConstants.SOUTH);
        downX.setActionCommand("downX");

        panelX.add(areaSizeX, "West");
        panelX.add(upX,"North");
        panelX.add(downX,"South");
    }

    /**
     * sluzy do ustawienia elementow odpowiedzialnych za szerokosc
     * @param functionSizeY szerokosc domyslna
     */
    void addPanelY(int functionSizeY)
    {
        panelY =new JPanel( );
        panelY.setLayout(new GridLayout());

        labelSizeY =new JLabel("liczba pól na szerokosc z zakresu 4-50");
        areaSizeY =new JTextPane();
        areaSizeY.setText(String.valueOf(functionSizeY));
        areaSizeY.setFont(new Font("Courier New", Font.CENTER_BASELINE, 30));
        areaSizeY.setEditable(false);
        upY =new BasicArrowButton(SwingConstants.NORTH);
        upY.setActionCommand("upY");
        downY =new BasicArrowButton(SwingConstants.SOUTH);
        downY.setActionCommand("downY");

        panelY.add(areaSizeY, "West");
        panelY.add(upY,"North");
        panelY.add(downY,"South");
    }

    /**
     * sluzy do ustawienia elementow odpowiedzialnych za wczytanie ilosci min
     * @param minen domyslna liczba min
     */
    void addPanelMinen(int minen)
    {
        panelMinen =new JPanel( );
        panelMinen.setLayout(new GridLayout());

        labelSizeMinen =new JLabel("liczba min na planszę, mniej niż polowa pol na planszy");
        areaMinen =new JTextPane();
        areaMinen.setText((String.valueOf(minen)));
        areaMinen.setFont(new Font("Courier New", Font.CENTER_BASELINE, 30));
        areaMinen.setEditable(false);

        upMinen =new BasicArrowButton(SwingConstants.NORTH);
        downMinen =new BasicArrowButton(SwingConstants.SOUTH);
        upMinen.setActionCommand("upMinen");
        downMinen.setActionCommand("downMinen");

        panelMinen.add(areaMinen, "West");
        panelMinen.add(upMinen,"North");
        panelMinen.add(downMinen,"South");
    }


    /**
     * sluzy do wysrodkowania tekstu w ITextPanelach
     */
    void centerText()
    {
        StyledDocument docMinen = areaMinen.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        docMinen.setParagraphAttributes(0, docMinen.getLength(), center, false);

        StyledDocument docAreaX = areaSizeX.getStyledDocument();
        SimpleAttributeSet centerX = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerX, StyleConstants.ALIGN_CENTER);
        docAreaX.setParagraphAttributes(0, docMinen.getLength(), centerX, false);

        StyledDocument docAreaY = areaSizeY.getStyledDocument();
        SimpleAttributeSet center2 = new SimpleAttributeSet();
        StyleConstants.setAlignment(center2, StyleConstants.ALIGN_CENTER);
        docAreaY.setParagraphAttributes(0, docMinen.getLength(), center2, false);

    }

    /**
     * funkcja ma za zadania ustawienie ActionListenera na wszystkie guziki funkcyjne na ekranie startowym
     * @param click ActionListener ktory dodaje sie do guzikow
     */
        void listen (ActionListener click)
        {
            upX.addActionListener(click);
            downX.addActionListener(click);

            upY.addActionListener(click);
            downY.addActionListener(click);

            downMinen.addActionListener(click);
            upMinen.addActionListener(click);

            bStart.addActionListener(click);
        }

        /**
         * sluzy do ustawienia aktualnej szerokosci
         * @param size aktualna szerokosc
         */
        void setX(int size)
        {
            areaSizeX.setText(""+size);
        }

        /**
         * sluzy do ustawienia aktualnej wysokosci
         * @param size aktualna wysokosc
         */
        void setY(int size)
        {
            areaSizeY.setText(""+size);
        }

        /**
         * sluzy so ustawienia aktualnej liczby min
         * @param size aktualna liczba min
         */
        void setMinen(int size)
        {
            areaMinen.setText(""+size);
        }





}
