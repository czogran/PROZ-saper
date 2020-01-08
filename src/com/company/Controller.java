package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;


/**
 * klasa wywolujaca odpowiednie dzialania na widoki w zaleznosci od wynikow z modelu
 */
public class Controller  {
    /**
     * modela ktory wykonuje dzialania dla kontrolera
     */
    private Model model;
    /**
     * widok na ktorym wywietlane jest GUI
     */
    private View view;

   // private Start start;

    /**
     * ustawienie kontrolera i jego parametrow
     * @param model model ktory ma wykonywac obliczenia dla kontrolera
     * @param view widok na ktorym kontroler ma wyswietlac GUI
     */


    Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        view.listen(new StartListener());

    }

    /**
     * sluzy do wyswietlenia pa planszy po nacisnieciu start, w menu startowym oraz do schowania menu startowego
     */
    void newView()
    {
        view.setVisible(false);
        view = new Board(model.getSizeX(),model.getSizeY(),model.getBoardSpecifications(),model.getMinen());
        BoardListener bl=new BoardListener();
        view.listenBoard(bl);
    }

    /**
     *sluzy do przywrocenia menu startowego po przegranej lub wygranej rozgrywce
     */
    void resetView()
    {
        view.setVisible(false);
        view = new Start(model.getSizeX(),model.getSizeY(),model.getMinen());
        StartListener bl=new StartListener();
        view.listen(bl);
    }

    /**
     * ustwaia ActionListener do menu startowego, w zaleźności od akcji na GUI startowym zmienia jego parametry lub wywołuja
     * metode newView() i przechodzi do wlasciwej rozgrywki
     */
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String command= e.getActionCommand();

            switch (command) {
                case "upX":view.setX(model.changeX(1));
                    break;
                case "downX" : view.setX(model.changeX(0));
                    break;
                case "upY": view.setY(model.changeY(1));
                    break;
                case "downY": view.setY(model.changeY(0));
                    break;
                case "upMinen":view.setMinen(model.changeMinen(1));
                    break;
                case "downMinen": view.setMinen(model.changeMinen(0));
                    break;
                case "START":
                    model.setBoard(model.getSizeX(),model.getSizeY(),model.getMinen());
                    newView();

            }
        }
    }

    /**
     * sluzy do resetowania ekranu po kliknieciu na guzi na planszy koncowej
     */
    class EndListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            resetView();
        }
    }

    /**
     * ustawia MouseListener i ActionListener na wlasciwa plansze
     * w sumie wystarczyl by sam MouseListener, ale obsluga lewego przycisku myszy powstala w ActionLIstenerze
     * a prawego przycisku nie umialem zrobic w ActionListenerze, wiec zostal zastosowany
     * MouseListener ktorym daloby tez sie zrobic dla lewego
     */
    class BoardListener implements MouseListener{
        int x,y;
        int index;

        @Override
        public void mouseClicked(MouseEvent e) {
            int[] hash=view.returnHashCode( e.getSource().hashCode());
            x=hash[0];
            y=hash[1];
            if(e.getButton() == MouseEvent.BUTTON1)
            {
               // int value=model.checkField(x,y);
                Functions.Stan value=model.checkField(x,y);
                //0 oznacza ze sie trafilo  na jakas liczbe
                if(value== Functions.Stan.NUMBER)
                {
                   // System.out.println(Functions.Stan.NUMBER);
                    view.hideManyButtons(model.visible);
                }
                // 1 oznacza ze sie przegralo
                if(value==Functions.Stan.LOSER)
                {

                    view.hideButtons(x,y);

                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {

                                view.listenEndGame(new EndListener());
                                view.loser(x,y);

                                //view.hideButtons(x,y);
                                Thread.sleep(2000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });




                    view.listenEndGame(new EndListener());
                    //view.loser(x,y);

                }
                // 2 oznacza ze sie trafilo na skupiska 0
                if(value==Functions.Stan.ZEROS)
                {
                    view.hideManyButtons(model.visible);
                  //  view.hideButtons(x,y);
                }
                //5 oznacza ze sie wygralo gre
                if(value==Functions.Stan.WINNER)
                {
                    view.listenEndGame(new EndListener());
                    view.winner();
                }

            }
            if(e.getButton() == MouseEvent.BUTTON3)

            {

                view.rightClick(x,y,model.checkRight(hash[0],hash[1]),model.getMinenToPut());
                if(model.getSizeX()*model.getSizeY()==model.minenClickedGood +model.clickedButtons)
                {
                    view.listenEndGame(new EndListener());
                    view.winner();
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }


    }

}



/* class StartListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
             //  System.out.println( e.getSource());
                // System.out.println(view.getClass());
                String command= e.getActionCommand();
               System.out.println(command);
                switch (command) {
                    case "upX":view.setX(model.changeX(1));
                        break;
                    case "downX" : view.setX(model.changeX(0));
                        break;
                    case "START":
                        model.setBoard(model.getSizeX(),model.getSizeY());
                        newView();


                }
               //System.out.println(e.getActionCommand());
            }
        }*/



// System.out.println(e.getComponent().getAccessibleContext().getAccessibleText());
//  System.out.println(e.getComponent().getAccessibleContext().getAccessibleAction());
// System.out.println(e.getComponent().getAccessibleContext().getAccessibleAction().getAccessibleActionDescription(1));
// System.out.println(MouseEvent.BUTTON3);
// System.out.println(""+x+" "+y);

/* class BoardListener implements MouseListener, ActionListener{
        int x,y;
        int index;
        String comand;
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(e.getActionCommand());
            comand=e.getActionCommand();
            index=comand.indexOf(" ");
            //System.out.println(comand);
            x=Integer.parseInt(comand.substring(0,index));
            y=Integer.parseInt(comand.substring(index+1));
         //   System.out.println(""+x+" "+y+" ");

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                int value=model.checkField(x,y);
                //view.hideButtons(x,y);
                //0 oznacza ze sie trafilo  na jakas liczbe
                if(value==0)
                {
                    view.hideManyButtons(model.visible);
                }
                // 1 oznacza ze sie przegralo
                if(value==1)
                {
                    //view.getContentPane().removeAll();
                    //view.getContentPane().add(new Functions.MyPanel());
                    //view.getContentPane().repaint();
                    //view.add(new Functions.MyPanel());

                    view.hideButtons(x,y);

                    /*try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                          resetView();
}
// 2 oznacza ze sie trafilo na skupiska 0
                if(value==2)
                        {
                        view.hideButtons(x,y);
                        }
                        //5 oznacza ze sie wygralo gre
                        if(value==5)
                        {
                        resetView();
                        // System.out.println("ddddddddddddddddddd");
                        // view.removeAll();
                        // view.add(new Functions.MyPanel());
                        //view.add(Functions.set_picture("victory.gif",view.getWidth(),view.getHeight()));
                        }

                        }
                        if(e.getButton() == MouseEvent.BUTTON3)

                        {
                        Object source =e.getSource();
                        // source.hashCode();
                        //  System.out.println(source.getClass().getName().toString());
                        //System.out.println("con "+source.hashCode());
                        int[] hash=view.returnHashCode( e.getSource().hashCode());
                        view.rightClick(hash[0],hash[1],model.checkRight(hash[0],hash[1]));
                        if(model.getSizeX()*model.getSizeY()==model.minenClickedGood+model.clickedButtons)
                        {
                        resetView();
                        }
                        //   System.out.println("bb");
                        }

                        }

@Override
public void mousePressed(MouseEvent e) {
        }
@Override
public void mouseReleased(MouseEvent e) {
        }
@Override
public void mouseEntered(MouseEvent e) {
        }
@Override
public void mouseExited(MouseEvent e) {
        }


        }*/