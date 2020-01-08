package com.company;


/**
 * klasa glowna programu
 * @author PAWEL
 */
public class Main {
    /**
     * konstruk kalsy glownej , sluzy do uruchomienia modelu, widoku i kontrolera
     * @param args parametry podawana przy uruchomieniu programu
     */
    public static void main(String[] args) {
        View v =new Start(10,10,5);
        Model m=new Model();
        Controller c=new Controller(m,v);
    }
}
