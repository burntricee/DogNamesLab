package cmsc256;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class DogNamesLab {

    private static String promptForFileName() {
        System.out.println("Enter the file name: ");
        @SuppressWarnings("resource")
        Scanner keyIn = new Scanner(System.in);
        return keyIn.next();
    }

    private static Scanner openFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        while (!file.exists()) {
            file = new File(promptForFileName());
        }
        return new Scanner(file);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Read data file to build data structure
        ArrayList<Dog> doglist = new ArrayList<>();

        try {
            // verify file and create file Scanner
            Scanner fileReader = openFile("Dog_Names.csv");

            //  Discard header line
            fileReader.nextLine();

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                int commaIndex = line.indexOf(',');
                String name = line.substring(0, commaIndex).trim();
                int count = Integer.parseInt(line.substring(commaIndex + 1).trim());
                doglist.add(new Dog(name, count));
            }
            fileReader.close();
        } catch (FileNotFoundException noFile) {
            System.out.println("There was an error opening or reading from the file.");
            System.exit(0);
        }

        Scanner readInput = new Scanner(System.in);
        String prompt = "\nWhat do you want to do?\n"
                + "\t1. Check a dog name\n" + "\t2. See all the dog names\n"
                + "\t3. Play a game\n" + "\t4. Exit"
                + ".\n"
                + "Enter the number corresponding to your choice.";

        System.out.println(prompt);
        int option = readInput.nextInt();

        switch (option) {
            case 1:
                System.out.println("Enter a dog’s name?");
                String name = in.nextLine();
                int nameCount = getCountForDog(doglist, name);
                System.out.println(name + " is registered " + nameCount + " times.");
                break;
            case 2:
                System.out.println(getDogNamesAlphabetically(doglist));
                break;
            case 3:
                playGuessingGame(doglist, in);
                break;
            default:
                System.out.println("Invalid option.");
        }
        in.close();
    }

    public static int getCountForDog(ArrayList<Dog> dogs, String name) {
        // TODO:
        // search the list for the Dog named name
        // display dogs name and the number of registrations for that name
        for (Dog dog : dogs) {
            if (dog.getDogName().equals(name)) return dog.getCount();
        }
        return 0;
    }

    public static String getDogNamesAlphabetically(ArrayList<Dog> dogs) {
        // TODO Sort the list of Dog by name return
        if (dogs.size() == 0) return "";
        Collections.sort(dogs);
        StringBuilder output = new StringBuilder();
        for (Dog dog : dogs) {
            output.append(dog.getDogName()).append("\n");
        }
        return output.toString();
    }

    Random rand = new Random();

    public static void playGuessingGame(ArrayList<Dog> dogs, Scanner readIn) {
        DogNamesLab lab = new DogNamesLab();
        // TODO: implement the guessing game.
        // while not done playing
        // pull two random Dogs from the list
        // display the names and prompt player to pick the more popular name
        // read player input
        // increment total number of guesses
        // check registration counts to determine if player is correct
        // if correct, respond and increment number of correct answers
        // if wrong, respond
        // ask user if they want to quit
        // if yes, display number of correct out of total number of guesses
        // if no, continue
        boolean playing = true;
        int wins = 0;
        int plays = 0;
        while (playing) {
            plays++;
            Dog[] selectDogs = new Dog[2];
            selectDogs[0] = dogs.get(lab.rand.nextInt(dogs.size() + 1));
            selectDogs[1] = dogs.get(lab.rand.nextInt(dogs.size() + 1));
            while (selectDogs[0].getCount() == selectDogs[1].getCount() || selectDogs[1].equals(selectDogs[0])) selectDogs[1] = dogs.get(lab.rand.nextInt(dogs.size() + 1));
            String morePopName;
            if (selectDogs[0].getCount() > selectDogs[1].getCount()) {
                morePopName = selectDogs[0].getDogName();
            } else {
                morePopName = selectDogs[1].getDogName();
            }
            System.out.printf("""
                    Which name is more popular for Anchorage dogs? (Type 1 or 2)
                    \t1. %s
                    \t2. %s
                    User types:\s""", selectDogs[0].getDogName(), selectDogs[1].getDogName());
            if (morePopName.equalsIgnoreCase(selectDogs[readIn.nextInt() - 1].getDogName())) {
                wins++;
                System.out.print("""
                        Yes, that’s right.
                        Do you want to play again? (Y/N)
                        """);
            } else {
                System.out.printf("""
                        Nope, the more popular dog name is %s.
                        Do you want to play again? (Y/N)
                        """, morePopName);
            }
            readIn.nextLine();
            if (readIn.nextLine().equalsIgnoreCase("N")) playing = false;
        }
        System.out.printf("You guessed correctly %d out of %d times.", wins, plays);
    }


}
