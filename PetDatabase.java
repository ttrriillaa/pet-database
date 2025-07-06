import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class PetDatabase {
    static ArrayList<Pet> pets = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Pet Database Program.");
        while (running) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> viewPets();
                case "2" -> addPets();
                case "3" -> updatePet();
                case "4" -> removePet();
                case "5" -> searchByName();
                case "6" -> searchByAge();
                case "7" -> {
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
        System.out.println("2) Add more pets");
        System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
        System.out.println("5) Search pets by name");
        System.out.println("6) Search pets by age");
        System.out.println("7) Exit program");
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
        while (true) {
            System.out.print("add pet (name, age): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            String[] parts = input.split(" ");
            if (parts.length == 2) {
                try {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    pets.add(new Pet(name, age));
                    added++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format.");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
        System.out.printf("%d pets added.%n", added);
    }

    private static void updatePet() {
        viewPets();
        System.out.print("\nEnter the pet ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pet pet = pets.get(id);
            System.out.print("Enter new name and new age: ");
            String[] parts = scanner.nextLine().split(" ");
            if (parts.length == 2) {
                String old = pet.getName() + " " + pet.getAge();
                pet.setName(parts[0]);
                pet.setAge(Integer.parseInt(parts[1]));
                System.out.printf("%s changed to %s %d.%n", old, pet.getName(), pet.getAge());
            } else {
                System.out.println("Invalid input.");
            }
        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void removePet() {
        viewPets();
        System.out.print("\nEnter the pet ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pet pet = pets.remove(id);
            System.out.printf("%s %d is removed.%n", pet.getName(), pet.getAge());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void searchByName() {
        System.out.print("Enter a name to search: ");
        String name = scanner.nextLine().toLowerCase(Locale.ROOT);
        ArrayList<Pet> results = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName().toLowerCase(Locale.ROOT).equals(name)) {
                results.add(pet);
            }
        }
        printTableHeader();
        for (Pet pet : results) {
            System.out.println(pet);
        }
        printTableFooter(results.size());
    }

    private static void searchByAge() {
        System.out.print("Enter age to search: ");
        try {
            int age = Integer.parseInt(scanner.nextLine());
            ArrayList<Pet> results = new ArrayList<>();
            for (Pet pet : pets) {
                if (pet.getAge() == age) {
                    results.add(pet);
                }
            }
            printTableHeader();
            for (Pet pet : results) {
                System.out.println(pet);
            }
            printTableFooter(results.size());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age.");
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
