import java.util.InputMismatchException;
import java.util.Scanner;

import static utils.PrintUtils.*;

public class Common {

    /**
     * Print the current working directory
     *
     * @param directoryTree the directory tree
     */
    public static void printWd(DirectoryTree directoryTree) {
        println(directoryTree.getWd().data);
    }

    /**
     * Helper method to display an item rename
     *
     * @param newName the new name of the item
     * @param oldName the old name of the item
     */
    public static void renamePrompt(String newName, String oldName) {
        printf("Renamed %s to %s\n", oldName, newName);
    }

    /**
     * Search item helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void search(Scanner in, DirectoryTree tree) {
        print("Enter the name of the item you want to search for > ");
        String searchTerm = in.nextLine();
        if (searchTerm.isBlank()) {
            throw new InputMismatchException("Invalid search term");
        }

        var result = tree.search(searchTerm);
        println(result);
    }

    /**
     * Helper method to format the size of a file in bytes
     *
     * @param bytes the size in bytes
     * @return the formatted size
     */
    public static String formatSize(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }

        int pointer = 0;
        String suffix = "KMGTPE";
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            pointer++;
        }
        return String.format("%.1f %cB", bytes / 1000.0, suffix.charAt(pointer));
    }
}
