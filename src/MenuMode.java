import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import static utils.PrintUtils.*;

/**
 * Represents the menu mode for the virtual file system application.
 * The user can perform various operations on the file system using a menu interface.
 */
public class MenuMode {

    /**
     * Represents the type of file system item
     */
    enum ItemType {
        FILE, DIRECTORY
    }

    /**
     * Prompts the user for the type of file system item
     *
     * @param in the scanner object
     * @return the type of file system item
     */
    private static ItemType promptForItemType(Scanner in) {
        while (true) {
            print("Is the item a file (F) or directory (D) > ");
            String choice = in.next().trim().toUpperCase();
            switch (choice) {
                case "F":
                    return ItemType.FILE;
                case "D":
                    return ItemType.DIRECTORY;
                default:
                    println("Invalid choice. Please enter 'F' for file or 'D' for directory.");
            }
        }
    }

    /**
     * Create item helper method for menu mode
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void create(Scanner in, DirectoryTree tree) {
        var item = promptForItemType(in);
        if (item == ItemType.FILE) {
            print("Enter the name of the file with extension > ");
            in.nextLine();
            String name = in.nextLine();
            print("Enter the size of the file in bytes > ");
            long size = in.nextLong();
            var nameSplit = name.split("\\.");
            if (nameSplit.length != 2)
                throw new InputMismatchException("Invalid file name please include the extension");
            String fileName = nameSplit[0];
            String fileExtension = nameSplit[1];
            var file = tree.create(new File(fileName, fileExtension, size));
            printf("Created file %s\n", file.data);
        } else {
            print("Enter the name of the directory > ");
            in.nextLine();
            String name = in.nextLine();
            var dir = tree.create(new Directory(name));
            printf("Created directory %s\n", dir.data);
        }

    }

    /**
     * Get child choice overload with default action
     *
     * @param in     the scanner object
     * @param tree   the directory tree
     * @param action the action to perform
     * @return the child choice
     */
    public static GeneralTreeNode<FileSystem> getChildChoice(Scanner in, DirectoryTree tree, String action) {
        return getChildChoice(in, tree, action, false);
    }

    /**
     * Get child choice helper method to help with selecting a child node
     *
     * @param in            the scanner object
     * @param tree          the directory tree
     * @param action        the action to perform
     * @param defaultAction whether to perform the action automatically when there is only one child
     * @return the child choice
     */
    public static GeneralTreeNode<FileSystem> getChildChoice(Scanner in, DirectoryTree tree, String action,
                                                             boolean defaultAction) {
        ArrayList<TreeNode<FileSystem>> contents = tree.getWd().children;
        if (contents.isEmpty()) {
            println("No children");
            return null;
        } else if (contents.size() == 1 && defaultAction) {
            println("Only one child to " + action);
            return (GeneralTreeNode<FileSystem>) contents.getFirst();
        }

        println("Enter the id of the item you wish to " + action);

        int choice;
        for (choice = 0; choice < contents.size(); ++choice) {
            printf("%d: %s\n", choice, contents.get(choice).data);
        }

        print("> ");
        choice = in.nextInt();

        if (choice <= contents.size() && choice >= 0) {
            return (GeneralTreeNode<FileSystem>) contents.get(choice);
        } else {
            throw new InputMismatchException("Invalid child choice");
        }
    }

    /**
     * Delete item helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void delete(Scanner in, DirectoryTree tree) {
        GeneralTreeNode<FileSystem> choice = getChildChoice(in, tree, "delete");
        if (choice != null) {
            tree.remove(choice);
            println("Deleted " + choice.data);
        }
    }

    /**
     * Helper method to prompt the user to select the parent or child of a directory
     *
     * @param in     the scanner object
     * @param tree   the directory tree
     * @param action the action to perform
     * @return the choice
     */
    public static Boolean parentOrChild(Scanner in, DirectoryTree tree, String action) {
        println("Do you wish to " + action + " directory's parent (Y) or a child directory (N)");
        var wd = tree.getWd();
        print("> ");
        String choice = in.next().toLowerCase();
        if (choice.startsWith("y")) {
            return true;
        } else if (!choice.startsWith("y") && !choice.startsWith("n")) {
            throw new InputMismatchException();
        } else if (tree.getDirectoryTree().numChildren(wd) == 0) {
            println("Folder has no children");
            return null;
        }
        return false;
    }

    /**
     * Change directory helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void cd(Scanner in, DirectoryTree tree) {
        GeneralTreeNode<FileSystem> wd = tree.getWd();

        var choice = parentOrChild(in, tree, "go to this");
        if (choice == null) {
            return;
        } else if (choice) {
            if (tree.isRoot(wd)) {
                println("At root directory");
            } else {
                tree.cd((GeneralTreeNode<FileSystem>) wd.parent);
                Common.printWd(tree);
            }
            return;
        }

        GeneralTreeNode<FileSystem> childChoice = getChildChoice(in, tree, "change to", true);
        if (childChoice != null) {
            if (!childChoice.data.isDirectory()) {
                println("Cannot change to a file");
            } else {
                tree.cd(childChoice);
                Common.printWd(tree);
            }
        }
    }

    /**
     * Rename item helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void rename(Scanner in, DirectoryTree tree) {
        var choice = parentOrChild(in, tree, "rename the");
        var wd = tree.getWd();
        String newName;

        if (choice == null) return;
        else if (choice) {
            if (tree.isRoot(wd)) {
                println("Cannot rename root directory");
            } else {
                print("Enter the new name > ");
                in.nextLine();
                newName = in.nextLine();
                String oldName = tree.rename(wd, newName);
                Common.renamePrompt(newName, oldName);
            }
            return;
        }

        var childChoice = getChildChoice(in, tree, "rename", true);
        if (childChoice != null) {
            print("Enter the new name > ");
            in.nextLine();
            newName = in.nextLine();
            String oldName = tree.rename(childChoice, newName);
            Common.renamePrompt(newName, oldName);
        }
    }

    /**
     * Sort item helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void sort(Scanner in, DirectoryTree tree) {
        println("""
                Sort by:
                1 - Name
                2 - Size
                3 - Date Created
                4 - Date Modified""");
        print("> ");
        int choice = in.nextInt();
        print("Ascending (Y) or descending (N) > ");
        String order = in.next().toLowerCase();

        if (!order.startsWith("y") && !order.startsWith("n")) {
            throw new InputMismatchException("Invalid order choice");
        }

        boolean ascending = order.startsWith("y");
        switch (choice) {
            case 1 -> tree.sortByName(ascending);
            case 2 -> tree.sortBySize(ascending);
            case 3 -> tree.sortByCreatedDate(ascending);
            case 4 -> tree.sortByModifiedDate(ascending);
            default -> throw new InputMismatchException("Invalid sort choice");
        }
    }

    /**
     * Stat item helper method. Displays information about a file or directory
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void stat(Scanner in, DirectoryTree tree) {
        GeneralTreeNode<FileSystem> choice = getChildChoice(in, tree, "view stats for");
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
     * Cut item(s) helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void cut(Scanner in, DirectoryTree tree) {
        print("Do you want cut a single item (Y) or multiple items (N) > ");
        String choice = in.next().toLowerCase();

        if (!choice.startsWith("y") && !choice.startsWith("n")) {
            throw new InputMismatchException("Invalid choice");
        }

        ArrayList<GeneralTreeNode<FileSystem>> items = new ArrayList<>();
        if (choice.startsWith("y")) {
            var item = getChildChoice(in, tree, "cut");
            if (item != null) items.add(item);
        } else {
            ArrayList<TreeNode<FileSystem>> contents = tree.getWd().children;

            if (contents.isEmpty()) {
                println("No children");
                return;
            } else if (contents.size() == 1) {
                println("Only one child to move");
                items.add((GeneralTreeNode<FileSystem>) contents.getFirst());
                tree.cut(items);
                return;
            }

            println("Enter all the ids of the items you wish to cut separated with a comma");
            for (int i = 0; i < contents.size(); i++) {
                printf("%d: %s\n", i, contents.get(i).data);
            }

            print("> ");
            in.nextLine();
            String idString = in.nextLine();
            var ids = idString.split(",");
            if (ids.length == 0 || (ids.length == 1 && ids[0].isEmpty())) {
                throw new InputMismatchException("No items selected");
            }

            for (var id : ids) {
                int index = Integer.parseInt(id.strip());
                if (index < 0 || index >= contents.size()) {
                    throw new InputMismatchException("Invalid item id");
                }
                items.add((GeneralTreeNode<FileSystem>) contents.get(index));
            }
        }
        tree.cut(items);
    }

    /**
     * Paste item(s) helper method
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void paste(Scanner in, DirectoryTree tree) {
        var clipboard = tree.getClipboard();
        if (clipboard.isEmpty()) {
            println("Clipboard is empty");
            return;
        }
        print("Do you want paste a single item (Y) or multiple items (N) > ");
        String choice = in.next().toLowerCase();

        if (!choice.startsWith("y") && !choice.startsWith("n")) {
            throw new InputMismatchException("Invalid choice");
        }

        ArrayList<Integer> indices = new ArrayList<>();
        if (choice.startsWith("y")) {
            println("Enter the index of the item you want to paste");
            printClipboardItems(clipboard);
            print("> ");
            int choiceIndex = in.nextInt();

            if (choiceIndex < 0 || choiceIndex >= clipboard.size()) {
                throw new InputMismatchException("Invalid item index");
            }
            indices.add(choiceIndex);
        } else {
            println("Enter all the ids of the items you wish to paste separated with a comma");
            printClipboardItems(clipboard);
            print("> ");
            in.nextLine();
            String idString = in.nextLine();
            var ids = idString.strip().split(",");
            if (ids.length == 0) {
                throw new InputMismatchException("No items selected");
            }
            for (var id : ids) {
                int index = Integer.parseInt(id.strip());
                if (index < 0 || index >= clipboard.size()) {
                    throw new InputMismatchException("Invalid item id");
                }
                indices.add(index);
            }
        }
        tree.paste(indices);
    }

    /**
     * Helper method to print the items in the clipboard
     *
     * @param clipboard the clipboard
     */
    private static void printClipboardItems(Set<GeneralTreeNode<FileSystem>> clipboard) {
        int i = 0;
        for (GeneralTreeNode<FileSystem> fileSystemGeneralTreeNode : clipboard) {
            printf("%d: %s\n", i, fileSystemGeneralTreeNode.data);
            i++;
        }
    }

    /**
     * Menu mode for the virtual file system application
     *
     * @param in   the scanner object
     * @param tree the directory tree
     */
    public static void menuMode(Scanner in, DirectoryTree tree) {
        String help = """
                      1 - Create
                      2 - Delete
                      3 - Change Directory
                      4 - Rename
                      5 - Sort
                      6 - Stat
                      7 - Cut
                      8 - Paste
                      9 - Search
                      10 - Show directory structure
                      11 - Print working directory
                      12 - Help
                      13 - Exit""";
        println(help);
        boolean running = true;
        while (running) {
            try {
                print("> ");
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1 -> create(in, tree);
                    case 2 -> delete(in, tree);
                    case 3 -> cd(in, tree);
                    case 4 -> rename(in, tree);
                    case 5 -> sort(in, tree);
                    case 6 -> stat(in, tree);
                    case 7 -> cut(in, tree);
                    case 8 -> paste(in, tree);
                    case 9 -> Common.search(in, tree);
                    case 10 -> println(tree);
                    case 11 -> Common.printWd(tree);
                    case 12 -> println(help);
                    case 13 -> {
                        println("Exiting...");
                        running = false;
                    }
                    default -> throw new InputMismatchException("Invalid command choice");
                }
            } catch (InputMismatchException e) {
                println("Invalid option: " + e.getMessage());
                in.nextLine();
            } catch (UnsupportedOperationException e) {
                println("Invalid Operation: " + e.getMessage());
                in.nextLine();
            } catch (NumberFormatException e) {
                println("Invalid number: " + e.getMessage());
                in.nextLine();
            }
        }
    }
}
