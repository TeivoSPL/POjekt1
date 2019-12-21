package main.projekt1.ecosystem;

import main.projekt1.map.EvoMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class World {

    public static void main(String[] args) { //dodaj ioexception
        Scanner input = new Scanner(System.in);

        String externalFile;
        do {
            System.out.println("Do you want to read variables from an external file? (y/n)");
            externalFile = input.next("> ");
            JSONObject variables = new JSONObject();

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

        );
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

        return output;
    }
}
