package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


/**
 * glowna klasa widoku
 * po niej dziedziczy Start i Board
 * zawiera funkcje ktore sa nadpisane albo w Start albo w Board
 */
public  class View {
    JFrame frame;
protected  int xx=10;
    public View()
    {
        frame=new JFrame();
        this.frame.setResizable(false);
     //   frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

     void setX(int i){};
    void setY(int i){};
    void setMinen(int i){};
    void listen(ActionListener click)
    {
        System.out.println("hh");
    }
    void listenBoard(MouseListener click){}
    void hideButtons(int x, int y){};
    public int[] returnHashCode(int hash){
        int [] i = {0,0};
        return  i;
    }
    void rightClick(int x, int y, String name,int minen){}
    void hideManyButtons(int [][]board) {
    }
    void setVisible(boolean bool)
    {
        frame.setVisible(bool);
    }
    void winner(){}
    void loser(int x,int y){}
    void winner(String file){}
    void listenEndGame(ActionListener click)
    {}
}
