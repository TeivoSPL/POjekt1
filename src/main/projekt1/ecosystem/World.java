package main.projekt1.ecosystem;

import java.util.Scanner;

public class World {


    public static void main(String[] args) { //dodaj ioexception
        Scanner input = new Scanner(System.in);

        String externalFile;
        do {
            System.out.println("Do you want to read variables from an external file? (y/n)");
            externalFile = input.next("> ");

            switch (externalFile) {
                case "y":
                    JSONObject
                    break;
                case "n":

                    break;
                default:
                    System.out.println("Could not understand input.");
            }
        }while(!(externalFile.equals("y") || externalFile.equals("n")));
    }
}
