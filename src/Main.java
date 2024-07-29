import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void createDir(Scanner in, DirectoryTree tree) {
        System.out.print("Enter the name of the directory > ");
        String name = in.nextLine();
        tree.createDirectory(name);
    }

    public static GeneralTreeNode<String> getChildChoice(Scanner in, DirectoryTree tree, String action) {
        return getChildChoice(in, tree, action, false);
    }

    public static GeneralTreeNode<String> getChildChoice(Scanner in, DirectoryTree tree, String action,
                                                         boolean defaultAction) {
        ArrayList<TreeNode<String>> contents = tree.getWd().children;
        if (contents.isEmpty()) {
            System.out.println("No children");
            return null;
        } else if (contents.size() == 1 && defaultAction) {
            System.out.println("Only one child to " + action);
            return (GeneralTreeNode<String>) contents.getFirst();
        }

        System.out.println("Enter the id of the directory you wish to " + action);

        int choice;
        for (choice = 0; choice < contents.size(); ++choice) {
            System.out.printf("%d: %s\n", choice, contents.get(choice).data);
        }

        System.out.print("> ");
        choice = in.nextInt();
        in.nextLine();

        if (choice <= contents.size() && choice >= 0) {
            return (GeneralTreeNode<String>) contents.get(choice);
        } else {
            throw new InputMismatchException();
        }
    }

    public static void delDir(Scanner in, DirectoryTree tree) {
        GeneralTreeNode<String> choice = getChildChoice(in, tree, "delete");
        if (choice != null) {
            tree.removeDirectory(choice);
        }

    }

    public static Boolean parentOrChild(Scanner in, DirectoryTree tree, String action) {
        System.out.println("Do you wish to " + action + " directory's parent (Y) or a child directory (N)");
        var wd = tree.getWd();
        String choice = in.next().toLowerCase();
        if (choice.startsWith("y")) {
            return true;
        } else if (!choice.startsWith("y") && !choice.startsWith("n")) {
            throw new InputMismatchException();
        } else if (tree.getDirectoryTree().numChildren(wd) == 0) {
            System.out.println("Folder has no children");
            return null;
        }
        return false;
    }

    public static void cd(Scanner in, DirectoryTree tree) {
        GeneralTreeNode<String> wd = tree.getWd();

        var choice = parentOrChild(in, tree, "go to this");
        if (choice == null) {
            return;
        } else if (choice) {
            if (tree.isRoot(wd)) {
                System.out.println("At root directory");
            } else {
                tree.cd((GeneralTreeNode<String>) wd.parent);
            }
            return;
        }

        GeneralTreeNode<String> childChoice = getChildChoice(in, tree, "change to", true);
        if (childChoice != null) {
            tree.cd(childChoice);
        }

    }

    public static void rename(Scanner in, DirectoryTree tree) {
        var choice = parentOrChild(in, tree, "rename the");
        var wd = tree.getWd();
        String newName;

        if (choice == null) return;
        else if (choice) {
            if (tree.isRoot(wd)) {
                System.out.println("Cannot rename root directory");
            } else {
                System.out.print("Enter the new name > ");
                newName = in.nextLine();
                tree.renameDirectory(wd, newName);
            }
            return;
        }

        var childChoice = getChildChoice(in, tree, "rename", true);
        if (childChoice != null) {
            System.out.print("Enter the new name > ");
            newName = in.nextLine();
            tree.renameDirectory(childChoice, newName);
        }
    }

    public static void main(String[] args) {
        DirectoryTree directoryTree = new DirectoryTree();
        boolean running = true;
        String help = """
                      1 - Create Directory
                      2 - Delete Directory
                      3 - Change Directory
                      4 - Rename Directory
                      5 - Show directory structure
                      6 - Print working directory
                      7 - Help
                      8 - Exit""";
        System.out.println(help);

        try (Scanner in = new Scanner(System.in)) {
            while (running) {
                try {
                    System.out.print("> ");
                    int choice = in.nextInt();
                    in.nextLine();
                    switch (choice) {
                        case 1:
                            createDir(in, directoryTree);
                            break;
                        case 2:
                            delDir(in, directoryTree);
                            break;
                        case 3:
                            cd(in, directoryTree);
                        case 4:
                            rename(in, directoryTree);
                            break;
                        case 5:
                            System.out.println(directoryTree);
                            break;
                        case 6:
                            System.out.println(directoryTree.getWd().data);
                            break;
                        case 7:
                            System.out.println(help);
                            break;
                        case 8:
                            System.out.println("Exiting");
                            running = false;
                            break;
                        default:
                            throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid option");
                    in.nextLine();
                }
            }
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}