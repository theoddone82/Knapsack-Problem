import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class population
{
    Random rand = new Random();
    public int anzahlRucksaecke = 10000;
    Rucksack[] population = new Rucksack[anzahlRucksaecke];
    ArrayList<Rucksack> populationsListe = new ArrayList<Rucksack>();
    public int populationsNummer = 0;
    Rucksack temporaererRucksack = new Rucksack();
    int anzahlMoeglicheItems = temporaererRucksack.anzahlMoeglicheItems;
    Rucksack[] neuePopulation = new Rucksack[anzahlRucksaecke];
    public population()
    {
        initialisierung();
        int besteFitness = populationsFitness();
        int gesuchteFitness = 10000;
        int mutationsrate = 10;                    // Wahrscheinlichkeit von Mutation = mutationsrate / 1000 
        while(populationsNummer< 100){
            Selektion();
            if(populationsNummer % 10 == 0){
                printPopulationsBesten();
            }
            Mutation(mutationsrate);
            population = neuePopulation;
            besteFitness = populationsFitness();
            populationsNummer+=1;
        }

        printPopulationsBesten();
    }

    public int populationsFitness(){

        int temp = 0;
        for(int i = 0; i < anzahlRucksaecke; i++){
            if(population[i].fitness() > temp){
                temp = population[i].fitness();
            }
        }
        return temp;
    }

    public Rucksack populationsBestenGeben(){
        int temp = -1;
        Rucksack besterRucksack = null;
        for(int i = 0; i < anzahlRucksaecke; i++){
            if(population[i].fitness() > temp){
                temp = population[i].fitness();
                besterRucksack = population[i];
            }
        }
        return besterRucksack;
    }

    public Rucksack neuerPopulationsBesterGeben(){
        int temp = -1;
        Rucksack besterRucksack = null;
        for(int i = 0; i < anzahlRucksaecke-1; i++){
            if(neuePopulation[i].fitness() > temp){
                temp = population[i].fitness();
                besterRucksack = population[i];
            }
        }
        return besterRucksack;
    }

    public void initialisierung(){
        for(int i = 0; i < anzahlRucksaecke; i ++){
            population[i] = new Rucksack();
        }
    }

    public Rucksack[] Selektion(){
        for(int i = 0; i < anzahlRucksaecke-2500; i++){
            Rucksack r1 = tournamentSelection();
            Rucksack r2 = tournamentSelection();

            neuePopulation[i] = Crossover(r1,r2);
            neuePopulation[i+2500] = Crossover(r2,r1);
        }
        for(int i = anzahlRucksaecke-5000; i < anzahlRucksaecke-1; i++){
            neuePopulation[i] = Crossover(roulettSelection(),roulettSelection());
        }

        neuePopulation[anzahlRucksaecke-1] = Elitismus();

        return neuePopulation;
    }    

    public Rucksack roulettSelection(){
        int temp = rand.nextInt(anzahlRucksaecke);
        int roulettZahl = rand.nextInt(population[temp].fitness()+ 1);
        if(roulettZahl > population[temp].fitness() - 1000){
            return roulettSelection();
        }
        return population[temp];
    }

    public Rucksack Elitismus(){
        Rucksack elternPopulationsBester = populationsBestenGeben();

        return elternPopulationsBester;
    }

    public Rucksack tournamentSelection(){
        Rucksack[] teilnehmer = new Rucksack[10];
        for(int i = 0;i < 10; i++){
            teilnehmer[i] = population[rand.nextInt(anzahlRucksaecke)];
        }
        Rucksack temp = teilnehmer[0];
        for(int i = 0;i < 10; i++){
            if(teilnehmer[i].fitness() >= temp.fitness()){
                temp = teilnehmer[i];
            }
        }
        return temp;
    }

    public Rucksack Crossover(Rucksack r1, Rucksack r2){
        int temp = rand.nextInt(anzahlMoeglicheItems);
        r1.Crossover(r2,temp);
        return r1;
    }

    public void Mutation(int mutationsrate){
        for(int i = 0; i < anzahlRucksaecke; i++){
            int zufallszahl = rand.nextInt(1000);
            if(zufallszahl < mutationsrate){
                population[i].punktmutation();
            }
        }
    }

    public boolean alleRucksaeckeZugelassen(){
        boolean zugelassen = true; 
        for(int i = 0; i < anzahlRucksaecke; i++){
            if(population[i].zulassenErmitteln() == false){
                return false;
            }
        }
        return zugelassen;
    }

    public void printPopulationsBesten(){
        int temp = -1;
        Rucksack besterRucksack = null;
        for(int i = 0; i < anzahlRucksaecke; i++){
            if(population[i].fitness() > temp){
                temp = population[i].fitness();
                besterRucksack = population[i];
            }
        }
        System.out.println(/*"Bestes Individuum der Population:"+ populationsNummer + "F Fitness: " + */temp);
        //System.out.println("Bestes Individuum: RucksackInhalt: ");
        //besterRucksack.rucksackInhaltAusgeben();
    }
}
