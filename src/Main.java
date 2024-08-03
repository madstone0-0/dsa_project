import java.util.*;

import static utils.PrintUtils.printf;
import static utils.PrintUtils.print;
import static utils.PrintUtils.println;

/**
 * Main program class for the virtual file system application, supports the following operations
 * <ul>
 *     <li> Create a file or directory </li>
 *     <li> Delete a file or directory </li>
 *     <li> Change directory </li>
 *     <li> Rename a file or directory </li>
 *     <li> Sort files and directories </li>
 *     <li> View file or directory stats </li>
 *     <li> Cut and paste files and directories </li>
 *     <li> Search for a file or directory </li>
 *     <li> Print the directory structure </li>
 *     <li> Print the current working directory </li>
 * </ul>
 */
public class Main {
    private static void testTree(DirectoryTree tree) {
        var rand = new Random();
        for (int i = 0; i < 5; i++) {
            var node = tree.create(new Directory("dir" + i));
            tree.cd(node);
            for (int j = 0; j < 5; j++) {
                try {
                    tree.create(new File("file" + rand.nextInt(10), "txt", 100));
                } catch (IllegalArgumentException e) {
                }
            }
            tree.cd((GeneralTreeNode<FileSystem>) tree.getDirectoryTree().root());
        }

        for (int i = 0; i < 5; i++) {
            try {
                tree.create(new File("file" + i, "txt", 100));
            } catch (IllegalArgumentException e) {
            }
        }

        tree.cd((GeneralTreeNode<FileSystem>) tree.getDirectoryTree().root());
        tree.cd(tree.create(new Directory("paste")));
        tree.create(new File("test", "txt", 100));
        tree.cd((GeneralTreeNode<FileSystem>) tree.getDirectoryTree().root());
    }


    public static void main(String[] args) {
        boolean mode = false;

        if (args.length > 0) {
            if (args[0].equals("menu")) {
                mode = false;
            } else if (args[0].equals("command")) {
                mode = true;
            } else {
                throw new IllegalArgumentException("Invalid mode");
            }
        } else {
            println("Usage: java Main <mode>");
            println("Modes: menu, command");
            return;
        }

        DirectoryTree directoryTree = new DirectoryTree(new Directory(""));
//        testTree(directoryTree);

        try (Scanner in = new Scanner(System.in)) {
            if (mode) {
                CommandMode.commandMode(in, directoryTree);
            } else {
                MenuMode.menuMode(in, directoryTree);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


}
