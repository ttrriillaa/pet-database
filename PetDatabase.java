import java.io.*;
import java.util.*;

public class PetDatabase {
    static final int MAX_PETS = 5;
    static ArrayList<Pet> pets = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "pets.txt";

    public static void main(String[] args) {
        loadPetsFromFile();
        boolean running = true;
        System.out.println("Pet Database Program.");
        while (running) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> viewPets();
                case "2" -> addPets();
                case "3" -> removePet();
                case "4" -> {
                    savePetsToFile();
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add new pets");
        System.out.println("3) Remove a pet");
        System.out.println("4) Exit program");
        System.out.print("Your choice: ");
    }

    private static void viewPets() {
        printTableHeader();
        for (Pet pet : pets) {
            System.out.println(pet);
        }
        printTableFooter(pets.size());
    }

    private static void addPets() {
        int added = 0;
        while (pets.size() < MAX_PETS) {
            System.out.print("add pet (name, age): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.printf("Error: %s is not a valid input.%n", input);
                continue;
            }
            try {
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                if (age < 1 || age > 20) {
                    System.out.printf("Error: %d is not a valid age.%n", age);
                } else {
                    pets.add(new Pet(name, age));
                    added++;
                }
            } catch (NumberFormatException e) {
                System.out.printf("Error: %s is not a valid input.%n", input);
            }
        }
        if (pets.size() == MAX_PETS) {
            System.out.println("Error: Database is full.");
        }
        System.out.printf("%d pets added.%n", added);
    }

    private static void removePet() {
        viewPets();
        System.out.print("Enter the pet ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id < 0 || id >= pets.size()) {
                System.out.printf("Error: ID %d does not exist.%n", id);
                return;
            }
            Pet pet = pets.remove(id);
            System.out.printf("%s %d is removed.%n", pet.getName(), pet.getAge());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID.");
        }
    }

    private static void loadPetsFromFile() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().split(",");
                if (line.length == 2) {
                    String name = line[0].trim();
                    int age = Integer.parseInt(line[1].trim());
                    pets.add(new Pet(name, age));
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet – that's fine
        }
    }

    private static void savePetsToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Pet pet : pets) {
                writer.println(pet.getName() + "," + pet.getAge());
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private static void printTableHeader() {
        System.out.println("+----------------------+");
        System.out.println("| ID | NAME      | AGE |");
        System.out.println("+----------------------+");
    }

    private static void printTableFooter(int count) {
        System.out.println("+----------------------+");
        System.out.printf("%d rows in set.%n", count);
    }
}
