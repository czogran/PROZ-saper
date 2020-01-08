package com.company;

import java.util.Random;


/**
 * klasa odpowiedzialna za logike programu
 */
public class Model {
    /** ilosc plytek na szerokosc*/
    private int sizeX;
    /** ilosc plytek na wysokosc*/
    int sizeY;
    /** ilosc min*/
    int minen;
    /** liczba min oznaczonych prawidlowo*/
    int minenClickedGood =0;
    /**liczba min zaznaczoncyh blednie*/
    int minenClickedGenerally =0;
    /**licbz kliknietych guzikow*/
    int clickedButtons =0;

    /**wartosci planszy dla kazdego pola*/
    int [][] boardSpecifications;
    /** specyfikacja co ma sie wyswietlac na danym polu w zaleznosci od wartosci na tym polu
     * 0-zwykly guzik
     * 1-flaga
     * 2-znak zapytania
     * 15-etykieta znajdujaca sie pod guzikiem
     */
    int [][] visible;

    /**
     * domyslny konsruktor deklarujacy parametry startowe aplikacji
      */
    public Model()
    {
        sizeY =10;
        sizeX =10;
        minen=5;

    }

    /**
     * zlicza zmiany ilosci pol na szerokosc
     * @param dir kierunek w ktorym ma sie zmieniac, 1 w gore, 0 w dol
     * @return zwraca aktualna liczbe pol na szerokosc
     */
    int changeX(int dir)
    {
        if(dir==1)
            ++sizeX;
        else
            --sizeX;
        if(sizeX <4)
            sizeX =4;
        if ( sizeX>40)
            sizeX=40;
        return sizeX;
    }

    /**
     * zlicza zmiany ilosci pol na wysokosc
     * @param dir kierunek w ktorym ma sie zmieniac, 1 w gore, 0 w dol
     * @return zwraca aktualna liczbe pol na wysokosc
     */
    int changeY(int dir)
    {
        if(dir==1)
            ++sizeY;
        else
            --sizeY;
        if(sizeY <4)
            sizeY =4;
        if ( sizeX>40)
            sizeY =40;

        return sizeY;
    }

    /**
     * zlicza zminy ilosci min do polozenia
     * @param dir kierunek w ktorym ma sie zmieniac, 1 w gore, 0 w dol
     * @return zwraca aktualna liczbe min do polozenia
     */
    int changeMinen(int dir)
    {
        if(dir==1)
            ++minen;
        else
            --minen;
        if(minen <1)
            minen =1;
        if ( minen>sizeX*sizeY*0.5)
            minen=(int)(sizeY*sizeX*0.5);
        return minen;
    }

    /**
     * w sposob losowy rozmieszcza miny na planszy i zlicza wartosci pol dookola miny
     * @param x ilosc pol na wysokosc
     * @param y ilosc pol na szerokosc
     * @param min ilosc min do podlozenia
     */

    public void setBoard(int x, int y, int min)
    {
        int mines_to_put=minen;

        minenClickedGenerally=0;
        clickedButtons =0;
        minenClickedGood =0;
        //names specification 0-9 mines around, 9 mine, //10 mine marked correctly, 11 badly, 12 question mark
        Random rand = new Random();
        int xx,yy;
        visible=new int[x+1][y+1];
        boardSpecifications =new int[x+1][y+1];

        for(int i=0; i<x;i++)
            for(int k=0;k<y;k++) {
                boardSpecifications[i][k] = 0;
                visible[i][k]=0;
            }
        putMinen(x,y);

        for(int i=0; i<x;i++)
            for(int k=0;k<y;k++)

            {
                if(boardSpecifications[i][k]>9)
                {
                    // System.out.println(boardSpecifications[i][k]);
                    boardSpecifications[i][k] = 9;
                }
            }
    }

    /**
     * sluzy do rozmieszczenia min na planszy
     * @param x rozmiar na wyskosc
     * @param y rozmiar na szerokosc
     */
    void putMinen(int x,int y)
{
    int xx,yy;
    int minesToPut=minen;
    Random rand = new Random();

    while (minesToPut!=0)
    {
        xx=rand.nextInt((sizeX -1));
        yy=rand.nextInt((sizeY -1));
        //System.out.println(""+xx+"  "+yy);
        //if(xx!=x &&yy!=y && boardSpecifications[xx][yy]!=9 )

        if(boardSpecifications[xx][yy]!=9 )
        {
            boardSpecifications[xx][yy]=9;
            if(xx==0&& yy==0)
            {
                boardSpecifications[xx+1][yy+1]++;
                boardSpecifications[xx+1][yy]++;
                boardSpecifications[xx][yy+1]++;
            }
            else if (xx==0)
            {
                boardSpecifications[xx][yy+1]++;
                boardSpecifications[xx+1][yy+1]++;
                boardSpecifications[xx+1][yy]++;
                boardSpecifications[xx][yy-1]++;
                boardSpecifications[xx+1][yy-1]++;


            }
            else if(yy==0)
            {
                boardSpecifications[xx+1][yy]++;
                boardSpecifications[xx-1][yy]++;
                boardSpecifications[xx-1][yy+1]++;
                boardSpecifications[xx][yy+1]++;
                boardSpecifications[xx+1][yy+1]++;

            }
            else {

                boardSpecifications[xx+1][yy]++;
                boardSpecifications[xx+1][yy-1]++;
                boardSpecifications[xx + 1][yy + 1]++;
                boardSpecifications[xx][yy - 1]++;
                boardSpecifications[xx][yy + 1]++;
                boardSpecifications[xx - 1][yy - 1]++;
                boardSpecifications[xx - 1][yy]++;
                boardSpecifications[xx - 1][yy + 1]++;
            }

            minesToPut--;
        }
    }
}


    /**
     * sprawdza jaka wartosc kryje sie po kliknieci na dane pole lewym przyciskiem myszy
     * @param x wspolzedna x pola
     * @param y wspolzedna y pola
     * @return w zaleznosci od tego na jakie pole trafi zwraca odpoeidnia wartosc
     *      5-zwyciestwo
     *      1-przegrana
     *      0-odkrycie wielu guzikow
     *      2-odkrycie 1 guzika
     */
     public Functions.Stan checkField(int x, int y)
    {
        if(visible[x][y]==0) {
            System.out.println("nooo");
            if (boardSpecifications[x][y] == 0) {

                showZeros(x, y);
                clickedButtons =0;
                for(int i = 0; i< sizeX; i++)
                    for(int k = 0; k< sizeY; k++)
                    {
                        if(visible[i][k]==15)
                        {

                            clickedButtons++;
                           // System.out.println(clickedButtons+ "to");
                        }
                    }
                if(clickedButtons + minenClickedGood == sizeX * sizeY)

                {
                    System.out.println("blad1");
                    return Functions.Stan.WINNER;
                }
                return Functions.Stan.NUMBER;
            }
            else if (boardSpecifications[x][y] == 9) {
                System.out.println("looooooseeer");
                return Functions.Stan.LOSER;
            } else {

                clickedButtons++;
                visible[x][y]=15;
                System.out.println(clickedButtons +" one click"+ minenClickedGood);
                if(clickedButtons + minenClickedGood == sizeX * sizeY) {
                    System.out.println("blad2");
                    return Functions.Stan.WINNER;
                }
                    return Functions.Stan.ZEROS;
            }
        }
        else
            return Functions.Stan.OTHER;

    }
    //visible==0 nothin, 1 minen, 2 question

    /**
     * umieszcze flage, znak zapytania w zaleznosci od tego co obecnie jest na przycisku
     * @param x wspolzedne x guzika
     * @param y wspolzedna y guzika
     * @return zwraca to co ma byc narysowane lub zwyciestwo dy oznaczymy ostatnia mine
     */
    public  String checkRight(int x, int y)
    {

        if(visible[x][y]==0)
        {
            if(boardSpecifications[x][y]==9)
            {

                minenClickedGood++;
                if((clickedButtons + minenClickedGood)==(sizeY * sizeX))
                {
                    System.out.println("blad3");
                    return "victory";
                }
            }

            minenClickedGenerally++;
            visible[x][y]=1;
            return "flag.png";
        }
        else if(visible [x][y]==1)
        {
            visible[x][y]=2;
            //System.out.println("why");
            if(boardSpecifications[x][y]==9)
                 minenClickedGood--;
            minenClickedGenerally--;
            return "question.jpg";
        }
        else
        {
            System.out.println("visible");
            visible[x][y]=0;
            return "";
        }

    }

    /**
     * przerobiona funkcja z neta
     * @author http://komputerowyglownie.blogspot.com/2013/08/klon-sapera-w-javie.html
     * @param x wspolzedna x na ktorej sprawdzamy
     * @param y wspolzedna y na ktorej sprawdzamy
     */
    public void showZeros(int x, int y){
        visible[x][y]=15;
        //clickedButtons++;
        if (boardSpecifications[x][y]==0)
            for (int i=x-1; i<=x+1; i++)
                if (i>=0 && i< sizeX)
                    for (int j=y-1; j<=y+1; j++)
                        if (j>=0 && j< sizeY)
                            if (i!=x || j!=y){
                              //  fieldButtons[i][j].setText(Integer.toString(field[i][j]));
                                if (visible[i][j]==0){
                                    visible[i][j]=15;
                                   // System.out.println("zer "+i+" "+j);
                                   // System.out.println(clickedButtons+"this");

                                    //    System.out.println(""+boardSpecifications[i][j]);
                                    showZeros(i,j);

                                }
                            }
    }

    /**
     * restuje liczbe kliknietych guzkow i dobrze oznaczonych min
     */
    public void reset()
    {
        clickedButtons =0;
        minenClickedGood =0;

    }

    /**
     *
     * @return zwraca boardSpecifications
     */
    public int[][] getBoardSpecifications() {
        return boardSpecifications;
    }

    /**
     *
     * @return zwraca liczbe min
     */
    public int getMinen() {
        return minen;
    }

    /**
     *
      * @return zwraca liczbe pol na szerokosc
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     *
     * @return zwraca liczbe pol na wysokosc
     */
    public int getSizeY() {
        return sizeY;
    }
    public int getMinenToPut()
    {
        return minen-minenClickedGenerally;
    }
}


/* void regulation(int x,int y,int min)
    {
        if(x<4)
        {
            x=4;
            sizeX =4;
        }
        if(y<4) {
            y = 4;
            sizeY=4;
        }
        if(min>x*y*0.5)
        {
            minen=(int)(x*y*0.5);
        }
        if(min<1) {
            min = 1;
            minen=min;
        }
    }*/