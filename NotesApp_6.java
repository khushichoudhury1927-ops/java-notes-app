import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NotesApp_6 {

    private static final String FILE_NAME = "notes.txt";
    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    public static void main(String[] args) {

        System.out.println("\nHey! What's your name?");
        System.out.print("> ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "there";

        System.out.println("\nNice to meet you, " + name + "! Welcome to your Notes App.");
        System.out.println("All your notes are saved to a file, so they'll be here next time too.\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readChoice();

            switch (choice) {
                case 1 -> addNote();
                case 2 -> viewAllNotes();
                case 3 -> deleteNote();
                case 4 -> searchNotes();
                case 5 -> clearAllNotes();
                case 6 -> {
                    System.out.println("\nTake care, " + name + "! Your notes are safe.");
                    scanner.close();
                    running = false;
                }
                default -> System.out.println("Hmm, that's not one of the options. Try 1 through 6.");
            }
        }
    }

    private static void addNote() {
        System.out.println("\nWhat's on your mind?");
        System.out.print("> ");
        String noteText = scanner.nextLine().trim();

        if (noteText.isEmpty()) {
            System.out.println("Looks like you didn't type anything! Give it another go.");
            return;
        }

        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String fullEntry = "[" + timestamp + "] " + noteText;

        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(fullEntry);
            bw.newLine();
            System.out.println("Got it! Saved on " + timestamp + ".");

        } catch (IOException e) {
            System.out.println("Something went wrong while saving. Details: " + e.getMessage());
        }
    }

    private static void viewAllNotes() {
        List<String> notes = loadNotes();

        if (notes.isEmpty()) {
            System.out.println("\nYou haven't added any notes yet. Go ahead and start!");
            return;
        }

        System.out.println("\nHere are all your notes:\n");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + notes.get(i));
        }
        System.out.println("\n  " + notes.size() + " note(s) in total.");
    }

    private static void deleteNote() {
        List<String> notes = loadNotes();

        if (notes.isEmpty()) {
            System.out.println("\nNothing to delete — there are no notes yet.");
            return;
        }

        viewAllNotes();

        System.out.println("\nWhich note do you want to remove? (Type its number)");
        System.out.print("> ");

        try {
            int num = Integer.parseInt(scanner.nextLine().trim());

            if (num < 1 || num > notes.size()) {
                System.out.println("That number doesn't match any note. Try again.");
                return;
            }

            String removed = notes.remove(num - 1);
            saveAllNotes(notes);

            String justTheText = removed.replaceAll("^\\[.*?\\] ", "");
            System.out.println("Done! Removed: \"" + justTheText + "\"");

        } catch (NumberFormatException e) {
            System.out.println("That doesn't look like a number. Please try again.");
        }
    }

    private static void searchNotes() {
        System.out.println("\nWhat are you looking for?");
        System.out.print("> ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        if (keyword.isEmpty()) {
            System.out.println("You'll need to type something to search for.");
            return;
        }

        List<String> notes   = loadNotes();
        List<String> matches = new ArrayList<>();

        for (String note : notes) {
            if (note.toLowerCase().contains(keyword)) {
                matches.add(note);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No notes found with \"" + keyword + "\" in them.");
        } else {
            System.out.println("\nFound " + matches.size() + " match(es) for \"" + keyword + "\":\n");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + matches.get(i));
            }
        }
    }

    private static void clearAllNotes() {
        System.out.println("\nThis will permanently delete ALL your notes. Are you sure? (yes / no)");
        System.out.print("> ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes") || answer.equals("y")) {
            try (FileWriter fw = new FileWriter(FILE_NAME, false)) {
                System.out.println("All cleared. Fresh start!");
            } catch (IOException e) {
                System.out.println("Couldn't clear the file. Here's why: " + e.getMessage());
            }
        } else {
            System.out.println("No worries — nothing was deleted.");
        }
    }

    private static List<String> loadNotes() {
        List<String> notes = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return notes;

        try (FileReader fr = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    notes.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Had trouble reading your notes: " + e.getMessage());
        }

        return notes;
    }

    private static void saveAllNotes(List<String> notes) {
        try (FileWriter fw = new FileWriter(FILE_NAME, false);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (String note : notes) {
                bw.write(note);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Had trouble saving your changes: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("  1. Add a note");
        System.out.println("  2. See all notes");
        System.out.println("  3. Delete a note");
        System.out.println("  4. Search notes");
        System.out.println("  5. Clear everything");
        System.out.println("  6. Exit");
        System.out.print("\nYour choice: ");
    }

    private static int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
