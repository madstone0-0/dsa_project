import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static utils.PrintUtils.*;

/**
 * Represents the command mode for the virtual file system application.
 * Allows the user to interact with the virtual file system using commands equivalent to unix based system commands
 * taking into account the limitations of java's pattern matching capabilities
 */
public class CommandMode {

    /**
     * Create file helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void createFile(Scanner in, DirectoryTree tree) {
        println("Usage: <file_path> <size>");
        print("(create_file)> ");
        String nameSize = in.nextLine();
        var parts = nameSize.split(" ");
        if (parts.length != 2) {
            throw new InputMismatchException("Invalid number of arguments");
        }
        var fileParts = parts[0].split("/");
        var file = fileParts[fileParts.length - 1];
        fileParts[fileParts.length - 1] = "";
        var oldWd = tree.cd(String.join("/", fileParts));
        var fileExt = file.split("\\.");

        if (fileExt.length != 2) {
            throw new InputMismatchException("Invalid file name please include the extension");
        }

        var res = tree.create(new File(fileExt[0], fileExt[1], Long.parseLong(parts[1])));
        printf("Created file %s\n", res.data);
        tree.cd(oldWd);
    }

    /**
     * Create directory helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void createDirectory(Scanner in, DirectoryTree tree) {
        println("Usage: <dir_path>");
        print("(create_directory)> ");
        String name = in.nextLine();
        if (name.isBlank()) {
            throw new InputMismatchException("Invalid path");
        }
        var parts = name.split("/");
        var dirName = parts[parts.length - 1];
        parts[parts.length - 1] = "";
        var oldWd = tree.cd(String.join("/", parts));
        var dir = tree.create(new Directory(dirName));
        printf("Created directory %s\n", dir.data);
        tree.cd(oldWd);
    }

    /**
     * Delete item helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void delete(Scanner in, DirectoryTree tree) {
        println("Usage: <path>");
        print("(delete)> ");
        String path = in.nextLine();
        if (path.isBlank()) {
            throw new InputMismatchException("Invalid path");
        }
        var choice = tree.getNodeByPath(path);
        if (choice != null) {
            tree.remove(choice);
            println("Deleted " + choice.data);
        }
    }

    /**
     * Change directory helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void cd(Scanner in, DirectoryTree tree) {
        println("Usage: <path>");
        print("(cd)> ");
        String path = in.nextLine();
        if (path.isBlank()) {
            throw new InputMismatchException("Invalid path");
        }
        var choice = tree.getNodeByPath(path);
        if (choice != null) {
            if (!choice.data.isDirectory()) {
                println("Cannot change to a file");
            } else {
                tree.cd(choice);
                Common.printWd(tree);
            }
        }
    }


    /**
     * Rename item helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void rename(Scanner in, DirectoryTree tree) {
        println("Usage: <path> <new_name>");
        print("(rename)> ");
        String pathName = in.nextLine();
        var parts = pathName.split(" ");
        if (parts.length != 2) {
            throw new InputMismatchException("Invalid number of arguments");
        }
        var choice = tree.getNodeByPath(parts[0]);
        if (choice != null) {
            var oldName = tree.rename(choice, parts[1]);
            Common.renamePrompt(parts[1], oldName);
        }
    }

    /**
     * Sort item helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void sort(Scanner in, DirectoryTree tree) {
        println("Usage: <option>");
        String[] opts = {"name", "size", "date_created", "date_modified"};
        println("Options: ");
        for (var opt : opts) println(opt);
        print("(sort)> ");
        String choice = in.nextLine().toLowerCase();

        if (choice.isBlank() || !Arrays.asList(opts).contains(choice)) {
            throw new InputMismatchException("Invalid option");
        }

        print("Ascending (Y) or descending (N) > ");
        String order = in.nextLine().toLowerCase();
        if (!order.startsWith("y") && !order.startsWith("n")) {
            throw new InputMismatchException("Invalid order choice");
        }

        boolean ascending = order.startsWith("y");
        switch (choice) {
            case "name" -> tree.sortByName(ascending);
            case "size" -> tree.sortBySize(ascending);
            case "date_created" -> tree.sortByCreatedDate(ascending);
            case "date_modified" -> tree.sortByModifiedDate(ascending);
            default -> throw new InputMismatchException("Invalid sort choice");
        }
    }

    /**
     * Stat item helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void stat(Scanner in, DirectoryTree tree) {
        println("Usage: <path>");
        print("(stat)> ");
        String path = in.nextLine();
        if (path.isBlank()) {
            throw new InputMismatchException("Invalid path");
        }

        var choice = tree.getNodeByPath(path);
        if (choice != null) {
            var data = choice.data;
            if (data.isDirectory()) {
                var dir = (Directory) data;
                println("Directory: " + dir.getName());
                println("Number of items: " + choice.children.size());
            } else {
                var file = (File) data;
                println("File: " + file.getName());
                println("Size: " + Common.formatSize(file.getSize()));
                println("Extension: " + file.getExtension());
            }
        }
    }

    /**
     * Move item helper method for command mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void move(Scanner in, DirectoryTree tree) {
        println("Usage: <source> <destination>");
        print("(move)> ");
        String sourceDest = in.nextLine();
        if (sourceDest.isBlank()) {
            throw new InputMismatchException("Invalid source and destination");
        }

        var parts = sourceDest.split(" ");
        if (parts.length != 2) {
            throw new InputMismatchException("Invalid number of arguments");
        }

        var source = tree.getNodeByPath(parts[0]);
        var oldWd = tree.cd(parts[1]);
        tree.move(source);
        printf("Moved %s to %s\n", source.data, tree.getWd().data);
        tree.cd(oldWd);
    }

    /**
     * Menu mode for the virtual file system application
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void commandMode(Scanner in, DirectoryTree tree) {
        String help = """
                      create_file / touch - Create a file with the given path and size in bytes
                      create_directory / mkdir - Create a directory with the given path
                      delete / rm - Delete the file or directory at the given path
                      move / mv - Move the file or directory from the source to the destination
                      stat - View the stats of the file or directory at the given path
                      rename - Rename the file or directory at the given path
                      cd - Change directory to the given path
                      pwd - Print the current working directory
                      search / find - Search file or directory with the given name
                      sort - Sort the files and directories by the given option
                      show_structure / ls - Show the directory structure
                      help - Show this help message
                      exit - Exit the program""";
        println(help);
        boolean running = true;
        while (running) {
            try {
                print("> ");
                var command = in.nextLine().toLowerCase().strip();
                switch (command) {
                    case "create_file", "touch" -> createFile(in, tree);
                    case "create_directory", "mkdir" -> createDirectory(in, tree);
                    case "delete", "rm" -> delete(in, tree);
                    case "move", "mv" -> move(in, tree);
                    case "search", "find" -> Common.search(in, tree);
                    case "sort" -> sort(in, tree);
                    case "stat" -> stat(in, tree);
                    case "rename" -> rename(in, tree);
                    case "cd" -> cd(in, tree);
                    case "pwd" -> Common.printWd(tree);
                    case "show_structure", "ls" -> println(tree);
                    case "help", "h" -> println(help);
                    case "exit" -> {
                        println("Exiting...");
                        running = false;
                    }
                    default -> throw new InputMismatchException("Invalid command");
                }
            } catch (InputMismatchException e) {
                println("Invalid option: " + e.getMessage());
//                in.nextLine(); // Clear the buffer in case of invalid input
            } catch (UnsupportedOperationException e) {
                println("Invalid Operation: " + e.getMessage());
//                in.nextLine(); // Clear the buffer in case of invalid input
            } catch (NumberFormatException e) {
                println("Invalid number: " + e.getMessage());
//                in.nextLine(); // Clear the buffer in case of invalid input
            }
        }
    }
}
