import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static utils.PrintUtils.printf;
import static utils.PrintUtils.print;
import static utils.PrintUtils.println;

public class Main {
    public static boolean fileOrDir(Scanner in) {
        print("Is the item a file (F) or directory (D) > ");
        String choice = in.next().toLowerCase();
        in.nextLine();
        if (!choice.startsWith("f") && !choice.startsWith("d")) throw new InputMismatchException("Invalid item choice");
        return choice.startsWith("f");
    }

    public static void create(Scanner in, DirectoryTree tree) {
        if (fileOrDir(in)) {
            print("Enter the name of the file with extension > ");
            String name = in.nextLine();
            print("Enter the size of the file in bytes > ");
            long size = in.nextLong();
            var nameSplit = name.split("\\.");
            if (nameSplit.length != 2)
                throw new InputMismatchException("Invalid file name please include the extension");
            String fileName = nameSplit[0];
            String fileExtension = nameSplit[1];
            tree.create(new File(fileName, fileExtension, size));
        } else {
            print("Enter the name of the directory > ");
            String name = in.nextLine();
            tree.create(new Directory(name));
        }
    }

    public static GeneralTreeNode<FileSystem> getChildChoice(Scanner in, DirectoryTree tree, String action) {
        return getChildChoice(in, tree, action, false);
    }

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
        in.nextLine();

        if (choice <= contents.size() && choice >= 0) {
            return (GeneralTreeNode<FileSystem>) contents.get(choice);
        } else {
            throw new InputMismatchException("Invalid child choice");
        }
    }

    public static void del(Scanner in, DirectoryTree tree) {
        GeneralTreeNode<FileSystem> choice = getChildChoice(in, tree, "delete");
        if (choice != null) {
            tree.remove(choice);
        }
    }

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

    private static void printWd(DirectoryTree directoryTree) {
        println(directoryTree.getWd().data);
    }

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
                printWd(tree);
            }
            return;
        }

        GeneralTreeNode<FileSystem> childChoice = getChildChoice(in, tree, "change to", true);
        if (childChoice != null) {
            if (!childChoice.data.isDirectory()) {
                println("Cannot change to a file");
            } else {
                tree.cd(childChoice);
                printWd(tree);
            }
        }
    }

    private static void renamePrompt(String newName, String oldName) {
        printf("Renamed %s to %s\n", oldName, newName);
    }

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
                newName = in.nextLine();
                String oldName = tree.rename(wd, newName);
                renamePrompt(newName, oldName);
            }
            return;
        }

        var childChoice = getChildChoice(in, tree, "rename", true);
        if (childChoice != null) {
            print("Enter the new name > ");
            newName = in.nextLine();
            String oldName = tree.rename(childChoice, newName);
            renamePrompt(newName, oldName);
        }
    }

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
        in.nextLine();
        switch (choice) {
            case 1:
                tree.sortByName(ascending);
                break;
            case 2:
                tree.sortBySize(ascending);
                break;
            case 3:
                tree.sortByCreatedDate(ascending);
                break;
            case 4:
                tree.sortByModifiedDate(ascending);
                break;
            default:
                throw new InputMismatchException("Invalid sort choice");
        }
    }

    public static String formatSize(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

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
                println("Size: " + formatSize(file.getSize()));
                println("Extension: " + file.getExtension());
            }
        }
    }

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
    }

    public static void commandMode(Scanner in, DirectoryTree tree) {

    }

    public static void menuMode(Scanner in, DirectoryTree tree) {
        String help = """
                      1 - Create
                      2 - Delete
                      3 - Change Directory
                      4 - Rename
                      5 - Sort
                      6 - Stat
                      7 - Show directory structure
                      8 - Print working directory
                      9 - Help
                      10 - Exit""";
        println(help);
        boolean running = true;
        while (running) {
            try {
                print("> ");
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        create(in, tree);
                        break;
                    case 2:
                        del(in, tree);
                        break;
                    case 3:
                        cd(in, tree);
                        break;
                    case 4:
                        rename(in, tree);
                        break;
                    case 5:
                        sort(in, tree);
                        break;
                    case 6:
                        stat(in, tree);
                        break;
                    case 7:
                        println(tree);
                        break;
                    case 8:
                        printWd(tree);
                        break;
                    case 9:
                        println(help);
                        break;
                    case 10:
                        println("Exiting...");
                        running = false;
                        break;
                    default:
                        throw new InputMismatchException("Invalid command choice");
                }
            } catch (InputMismatchException e) {
                println("Invalid option: " + e.getMessage());
                in.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        DirectoryTree directoryTree = new DirectoryTree();
        testTree(directoryTree);
        try (Scanner in = new Scanner(System.in)) {
            menuMode(in, directoryTree);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


}
