package main.projekt1.ecosystem;

import main.projekt1.map.EvoMap;
import main.projekt1.mechanics.Vector2d;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class World {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        JSONObject variables = new JSONObject();

        String externalFile;
        do {
            System.out.println("Do you want to read variables from an external file? (y/n)");
            externalFile = input.next("> ");

            switch (externalFile) {
                case "y":
                    variables = readFromJson();
                    break;
                case "n":
                    variables = readFromCommandLine();
                    break;
                default:
                    System.out.println("Could not understand input.");
            }
        }while(!(externalFile.equals("y") || externalFile.equals("n")));

        EvoMap map = new EvoMap(
                (int)variables.get("mapWidth"),
                (int)variables.get("mapHeight"),
                (int)variables.get("startEnergy"),
                (int)variables.get("moveEnergy"),
                (int)variables.get("plantEnergy"),
                (double)variables.get("jungleRatio")
        );

        for(int i = 0; i<(int)variables.get("animalsOnStart"); i++){
            Vector2d startingPosition = new Vector2d(
                    (int)(Math.random()*(int)variables.get("mapWidth")),
                    (int)(Math.random()*(int)variables.get("mapHeight"))
                    );
            EvoAnimal a = new EvoAnimal(map, startingPosition, (int)variables.get("startEnergy"));
            map.place(a);
        }

        String decision;
        do{
            System.out.println("What to do next? (run/exit)");
            decision = input.next("> ");

            switch (decision){
                case "exit":
                    break;
                case "run":
                    int skipDays;
                    System.out.println("How many days do you want the simulation to run?");
                    skipDays = input.nextInt();

                    for(int i=0; i< skipDays; i++){
                        System.out.println(map.toString());
                        map.run();
                        map.eat();
                        map.reproduce();
                        map.generatePlants();
                    }
                    break;
                default:
                    System.out.println("Could not understand input.");
            }

        }while(!decision.equals("exit"));
    }

    private static JSONObject readFromJson(){
        System.out.println("Loading map variables from parameters.json");

        JSONParser parser = new JSONParser();
        JSONObject output = new JSONObject();

        try (FileReader reader = new FileReader("parameters.json")){
            output = (JSONObject)parser.parse(reader);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        return output;
    }

    private static JSONObject readFromCommandLine(){
        JSONObject output = new JSONObject();
        Scanner variableInput = new Scanner(System.in);

        System.out.println("Please enter starting variables.");
        output.put("mapWidth",variableInput.next("Map width: "));
        output.put("mapHeight",variableInput.next("Map height: "));
        output.put("startEnergy",variableInput.next("Animal starting energy: "));
        output.put("moveEnergy",variableInput.next("Energy required to move: "));
        output.put("plantEnergy",variableInput.next("Plant energy: "));
        output.put("jungleRatio",variableInput.next("Jungle to steppe ratio: "));
        output.put("animalsOnStart",variableInput.next("Number of animals in the beginning of the simulation: "));

        return output;
    }
}
