import java.util.Random;

public class Rucksack
{
    Random rand = new Random();
    int anzahlItems = 0;
    boolean zugelassen = true;
    int zugelassenesGewicht = 100000;
    int gesammtGewicht;
    int fitness;
    public int[] werteListe = {2190,2055,1984,1539,1498,1386,1345,1308,1208,1159,1135,1069,930,910,834,812,713,705,645,634,545,506,486,451,411,383,332,285,200,190};
    public int[] gewichtListe = {1545,743,632,840,904,863,1905,135,649,468,147,378,1300,158,358,995,958,689,246,864,348,947,853,742,825,146,368,378,284,258};
    public boolean[] rucksackInhalt = new boolean[werteListe.length];
    int anzahlMoeglicheItems = werteListe.length;
    public Rucksack()
    {
        zufaelligenInhaltErstellen();
    }

    public void rucksackZurueksetzen(){
        for(int i = 0; i < rucksackInhalt.length; i ++){
            rucksackInhalt[i] = false; 
        }
        anzahlItems = 0;
        zugelassen = true;
    }

    public int[] werteListeGeben(){
        return werteListe;
    }

    public int[] gewichtListeGeben(){
        return gewichtListe;
    }

    public boolean[] inhalteListeGeben(){
        return rucksackInhalt;
    }

    public void rucksackInhaltAusgeben(){
        for(int i = 0; i < rucksackInhalt.length; i ++){
            System.out.println("ItemNr: " + i + ":" + rucksackInhalt[i] + "  | gewicht:"+ gewichtListe[i]+ "  | wert:" + werteListe[i]);
        }
    }

    public boolean zulassenErmitteln(){
        if(zugelassenesGewicht > gesammtGewichtErmitteln()){
            zugelassen = true;
        }else zugelassen = false;
        return zugelassen;
    }

    private int gesammtGewichtErmitteln(){
        int g = 0;
        for(int i = 0; i < rucksackInhalt.length; i ++){
            if(rucksackInhalt[i] == true){
                g = g + gewichtListe[i];
                //System.out.print("g ++"+ g + gewichtListe[i]);
            }
        }
        return gesammtGewicht = g;

    }

    public int fitness(){
        fitness = 0;
        for(int i = 0; i < rucksackInhalt.length; i++){
            if(rucksackInhalt[i] == true){
                fitness +=werteListe[i];
            }
        }
        if(gesammtGewichtErmitteln() > zugelassenesGewicht) {
            fitness = 0; return 0;
        }
        return fitness;
    }

    public void punktmutation(){
        int i = rand.nextInt(anzahlMoeglicheItems);
        if(rucksackInhalt[i] == true)
        { 
            rucksackInhalt[i] = false;
            anzahlItems--;
        }
        else{ 
            rucksackInhalt[i] = true; anzahlItems ++;
        }
        zulassenErmitteln();
    }

    public void zufaelligenInhaltErstellen(){
        punktmutation();
        punktmutation();
        punktmutation();
    }

    public void Crossover(Rucksack crossoverPartner,int zahl){
        for(int i = zahl; i < anzahlMoeglicheItems; i ++){
            rucksackInhalt[i] = crossoverPartner.inhaltZuIndexGeben(i);
        }
        fitness = fitness();
    }

    public boolean inhaltZuIndexGeben(int index){
        return rucksackInhalt[index];
    }
}
