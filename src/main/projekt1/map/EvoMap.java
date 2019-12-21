package main.projekt1.map;

import main.projekt1.ecosystem.EvoAnimal;
import main.projekt1.ecosystem.Grass;
import main.projekt1.mechanics.MapVisualizer;
import main.projekt1.mechanics.Vector2d;

import java.util.*;

public class EvoMap extends AbstractWorldMap {

    private int plantEnergy;
    private double jungleRatio;

    public EvoMap(int mapWidth, int mapHeight, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio){
        this.width = mapWidth;
        this.height = mapHeight;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;

        this.grass = new HashMap<>();
        this.animalsOnPosition = new HashMap<>();
        this.animalsOnMap = new LinkedList<>();
    }

    public void generatePlants(){// uwzglednic jungleratio
        if(grass.keySet().size() != this.height*this.width){
            //generate coordinates for plant in jungle
            //jungle is located in lower left corner
            int safeCounter = 0;
            Vector2d newGrassPosition;

            do{
                newGrassPosition = new Vector2d(
                        (int)(Math.random()*(this.jungleRatio*this.width)),
                        (int)(Math.random()*(this.jungleRatio*this.height))
                );
                safeCounter++;
            }while(
                    this.grass.get(newGrassPosition)!=null &&
                    safeCounter < this.jungleRatio*this.width*this.jungleRatio*this.height
            );

            if(safeCounter!=this.jungleRatio*this.width*this.jungleRatio*this.height){
                Grass jungleGrass = new Grass(newGrassPosition);
                grass.put(newGrassPosition,jungleGrass);
            }

            //generate coordinates for plant in steppe
            safeCounter = 0;

            do {
                newGrassPosition = new Vector2d((int)(Math.random()*this.width),(int)(Math.random()*this.height));
                safeCounter++;
            }while(
                    newGrassPosition.precedes(new Vector2d(
                            (int)(this.width*this.jungleRatio),
                            (int)(this.width*this.jungleRatio)
                    )) &&
                    this.grass.get(newGrassPosition) != null &&
                    safeCounter < this.height*this.width-this.jungleRatio*this.width*this.jungleRatio*this.height
            );

            if(safeCounter < this.height*this.width-this.jungleRatio*this.width*this.jungleRatio*this.height){
                Grass steppeGrass = new Grass(newGrassPosition);
                grass.put(newGrassPosition,steppeGrass);
            }
        }
    }

    @Override
    public void run() {
        //death round
        super.run();

        //movement round
        for (EvoAnimal a : this.animalsOnMap) {
            a.move(this.moveEnergy);
        }
    }

    public void eat() {
        //eating round
        for (Vector2d position : this.animalsOnPosition.keySet()) {
            if (this.grass.containsKey(position)) {

                LinkedList<EvoAnimal> contenders = this.getStrongest(position);

                if (contenders.size() > 1) {
                    for (EvoAnimal c : contenders) {
                        c.eat(this.plantEnergy / contenders.size());
                    }
                } else {
                    contenders.get(0).eat(this.plantEnergy);
                }

                this.grass.remove(position);
            }
        }
    }

    public void reproduce() {
        //reproducing round
        for (Vector2d position : this.animalsOnPosition.keySet()) {

            if (this.animalsOnPosition.get(position).size() > 1) {

                LinkedList<EvoAnimal> partners = getStrongest(position);

                if (partners.size() > 1 && partners.get(0).getEnergy() > 5) {

                    int fatherIndex, motherIndex;

                    do {
                        fatherIndex = (int) (Math.random() * partners.size());
                        motherIndex = (int) (Math.random() * partners.size());
                    } while (fatherIndex == motherIndex);

                    this.place(partners.get(fatherIndex).reproduce(partners.get(motherIndex)));
                } else {

                    LinkedList<EvoAnimal> animalsAtPosition = this.animalsOnPosition.get(position);
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
                    this.place(partners.get(0).reproduce(animalsAtPosition.get(animalsAtPosition.size() - motherIndex)));
                }
            }

        }
    }

    public LinkedHashSet<Vector2d> getFreeSpaces(Vector2d position) {
        LinkedHashSet<Vector2d> result = new LinkedHashSet<>();
        int x = position.getX();
        int y = position.getY();

        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(animalsAt(new Vector2d(x+i,y+j))==null){
                    result.add(new Vector2d(x+i,y+j));
                }
            }
        }

        return result;
    }

    private LinkedList<EvoAnimal> getStrongest(Vector2d position){
        LinkedList<EvoAnimal> result = new LinkedList<>();
        int maxEnergy = this.animalsOnPosition.get(position).getLast().getEnergy();

        for(EvoAnimal a : this.animalsOnPosition.get(position)){
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
