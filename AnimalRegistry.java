package pethome;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AnimalRegistry implements AutoCloseable {
    private Map<String, Animal> animals;
    private Counter counter;
    private final String dataFile = "animal_data.txt";

    public AnimalRegistry() {
        animals = new HashMap<>();
        counter = new Counter();
        loadDataFromFile();
        }

    public void addAnimal(String name, String species, int age) {
        animals.put(name, new Pet(name, species, age));
        counter.add();
        System.out.println("Animal successfully added!");
        saveDataToFile();
    }
    

    public void determineAnimalClass(String name) {
        Animal animal = animals.get(name);
        if (animal != null) {
            System.out.println(name + " is a " + animal.getSpecies());
        } else {
            System.out.println("Animal not found.");
        }
    }

    public void showAnimalCommands(String name) {
        Animal animal = animals.get(name);
        if (animal != null) {
            System.out.println(name + " can perform the following commands:");
            for (String command : animal.getCommands()) {
                System.out.println("- " + command);
            }
        } else {
            System.out.println("Animal not found.");
        }
    }

    public void teachAnimalCommand(String name, String newCommand) {
        Animal animal = animals.get(name);
        if (animal != null) {
            animal.addCommand(newCommand);
            System.out.println(name + " has learned a new command: " + newCommand);
            saveDataToFile();
        } else {
            System.out.println("Animal not found.");
        }
    }

    public void showAllPets() {
        System.out.println("List of all pets:");
        for (Map.Entry<String, Animal> entry : animals.entrySet()) {
            String name = entry.getKey();
            Animal animal = entry.getValue();
            if (animal instanceof Pet) {
                System.out.println("Name: " + name + ", Species: " + animal.getSpecies());
                System.out.println("Age: " + animal.getAge());
                System.out.println("Commands:");
            }
        }
    }
    
    

    public void navigateMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\nAnimal Registry Menu:");
                System.out.println("1. Add new animal");
                System.out.println("2. Determine animal class");
                System.out.println("3. Show animal commands");
                System.out.println("4. Teach animal new command");
                System.out.println("5. Show all pets");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter animal name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter species: ");
                        String species = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        addAnimal(name, species, age);
                        break;
                    case 2:
                        System.out.print("Enter animal name: ");
                        determineAnimalClass(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Enter animal name: ");
                        showAnimalCommands(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Enter animal name: ");
                        String animalName = scanner.nextLine();
                        System.out.print("Enter new command: ");
                        String newCommand = scanner.nextLine();
                        teachAnimalCommand(animalName, newCommand);
                        break;
                    case 5:
                        showAllPets();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            }
        }
        System.out.println("Exiting Animal Registry. Goodbye!");
    }

    @Override
    public void close() {
        saveDataToFile();
        try {
            counter.close();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loadDataFromFile() {
        System.out.println("Loading data from file...");
        File file = new File(dataFile);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] data = scanner.nextLine().split(",");
                    String name = data[0];
                    String species = data[1];
                    int age = Integer.parseInt(data[2]);
                    animals.put(name, new Animal(name, species, age));
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error loading data from file: " + e.getMessage());
            }
        }
    }

    private void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(dataFile)) {
            for (Animal animal : animals.values()) {
                writer.println(animal.getName() + "," + animal.getSpecies() + "," + animal.getAge());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (AnimalRegistry registry = new AnimalRegistry()) {
            registry.navigateMenu();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
