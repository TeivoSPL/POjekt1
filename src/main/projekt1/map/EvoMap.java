package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.ecosystem.Grass;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public class EvoMap extends AbstractWorldMap {

    private int width;
    private int height;
    private Map<Vector2d, Grass> grass;

    public EvoMap(){
        this.width = 100;
        this.height = 30;
        this.grass = new HashMap<>();
        this.animals = new HashMap<>();
    }

    /**
        This function generates two plants every time it is called.
     */
    private void generatePlants(){
        //generate coordinates for plant in jungle
        //safecounter is crucial if there is not a lot of space to grow a new plant
        int safeCounter = 0;
        Vector2d jpos;
        do{
            jpos = new Vector2d(
                    (int)(Math.random()*10+((this.width-1)/2-1)),
                    (int)(Math.random()*10+((this.height)/2-1))
            );
            safeCounter++;
        }while(
                this.grass.get(jpos)!=null &&
                safeCounter<100
        );
        if(safeCounter!=100){
            Grass jg = new Grass(jpos);
            grass.put(jpos,jg);
        }

        //generate coordinates for plant in steppe
        safeCounter = 0;
        Vector2d spos; // zmienic nazwe zmiennej
        do {
            spos = new Vector2d((int)(Math.random()*100),(int)(Math.random()*30));
            safeCounter++;
        }while(
                spos.precedes(new Vector2d(54,19)) &&
                spos.follows(new Vector2d(44,9)) &&
                this.grass.get(spos) != null &&
                safeCounter < this.height*this.width-100
        );
        if(safeCounter < this.height*this.width-100){
            Grass sg = new Grass(spos);
            grass.put(jpos,sg);
        }
    }

    @Override
    public void run() {
        //death round
        super.run();

        //movement round
        for (LinkedList<EvoAnimal> animalsOnPosition : this.animals.values()) {
            for (EvoAnimal a : animalsOnPosition) {
                a.move();
            }
        }
    }

    public void eat() {
        //eating round
        for (Vector2d position : this.animals.keySet()) {
            if (grass.get(position) != null) {

                LinkedList<EvoAnimal> contenders = this.getStrongest(position);

                if (contenders.size() > 1) {
                    for (EvoAnimal c : contenders) {
                        c.eat(5 / contenders.size());
                    }
                } else {
                    contenders.get(0).eat(5);
                }

                grass.remove((position));
            }
        }
    }

    public void reproduce() {
        //reproducing round
        for (Vector2d position : this.animals.keySet()) {

            if (this.animals.get(position).size() > 1) {

                LinkedList<EvoAnimal> partners = getStrongest(position);

                if (partners.size() > 1 && partners.get(0).getEnergy() > 5) { //dać zmienną na minimalną energię rozmnazania

                    int fatherIndex, motherIndex;

                    do {
                        fatherIndex = (int) (Math.random() * partners.size());
                        motherIndex = (int) (Math.random() * partners.size());
                    } while (fatherIndex == motherIndex);

                    this.place(partners.get(fatherIndex).reproduce(partners.get(motherIndex)));
                } else {

                    LinkedList<EvoAnimal> animalsAtPosition = this.animals.get(position);
                    int secondHighestEnergy = animalsAtPosition.get(animalsAtPosition.size() - 2).getEnergy();
                    int counter = 0;
                    for (int i = animalsAtPosition.size() - 2; i > 0; i--) {
                        if (animalsAtPosition.get(i).getEnergy() < secondHighestEnergy) {
                            break;
                        } else {
                            counter++;
                        }
                    }

                    int motherIndex = (int) (Math.random() * counter) + 2;
                    partners.get(0).reproduce(animalsAtPosition.get(animalsAtPosition.size() - motherIndex));
                }
            }

        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, EvoAnimal animal) {
        this.animals.get(oldPosition).remove(animal);

        if(this.animals.get(oldPosition).size()==0){
            this.animals.remove(oldPosition);
        }

        if(!this.animals.containsKey(newPosition)){
            this.animals.put(newPosition,new LinkedList<>());
        }

        this.animals.get(newPosition).add(animal);
        this.animals.get(newPosition).sort(Comparator.comparing(EvoAnimal::getEnergy));
    }

    public LinkedHashSet<Vector2d> getFreeSpaces(Vector2d position) {
        LinkedHashSet<Vector2d> result = new LinkedHashSet<>();
        int x = position.getX();
        int y = position.getY();

        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(objectAt(new Vector2d(x+i,y+j))==null){
                    result.add(new Vector2d(x+i,y+j));
                }
            }
        }

        return result;
    }

    private LinkedList<EvoAnimal> getStrongest(Vector2d position){
        LinkedList<EvoAnimal> result = new LinkedList<>();
        int maxEnergy = this.animals.get(position).getLast().getEnergy();

        for(EvoAnimal a : this.animals.get(position)){
            if(a.getEnergy() == maxEnergy){
                result.add(a);
            }
            else{
                break;
            }
        }

        return result;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
