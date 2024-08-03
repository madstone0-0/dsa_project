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
        // Create the src directory and its substructure
        GeneralTreeNode<FileSystem> srcNode = tree.create(new Directory("src"));
        tree.cd(srcNode);

        String[] srcFiles = {"AbBinaryTree", "AbGeneralTree", "AbTree", "BinarySearchTree", "BinaryTreeADT",
                "BinaryTreeNode", "CommandMode", "Common", "Directory", "DirectoryTree", "File", "FileSystem",
                "GeneralTreeNode.java", "LinkedGeneralTree", "Main", "MenuMode", "TreeADT", "TreeNode"};
        GeneralTreeNode<FileSystem> utilsNode = tree.create(new Directory("utils"));
        tree.cd(utilsNode);
        tree.create(new File("PrintUtils", "java", 100));
        tree.cd((GeneralTreeNode<FileSystem>) tree.getWd().parent);

        for (String fileName : srcFiles) {
            tree.create(new File(fileName, "java", 100));
        }
        tree.cd((GeneralTreeNode<FileSystem>) tree.getDirectoryTree().root());

        // Create the out directory and its substructure
        GeneralTreeNode<FileSystem> outNode = tree.create(new Directory("out"));
        tree.cd(outNode);
        GeneralTreeNode<FileSystem> productionNode = tree.create(new Directory("production"));
        tree.cd(productionNode);
        GeneralTreeNode<FileSystem> dsaProjectNode = tree.create(new Directory("dsa_project"));
        tree.cd(dsaProjectNode);

        String[] outFiles = {"AbBinaryTree", "AbGeneralTree", "AbTree", "BinarySearchTree", "BinaryTreeADT",
                "BinaryTreeNode", "CommandMode", "Common", "Directory", "DirectoryTree", "File", "FileSystem",
                "GeneralTreeNode", "LinkedGeneralTree", "Main", "MenuMode$ItemType", "MenuMode", "TreeADT", "TreeNode"};
        utilsNode = tree.create(new Directory("utils"));
        tree.cd(utilsNode);
        tree.create(new File("PrintUtils", "class", 100));
        tree.cd((GeneralTreeNode<FileSystem>) tree.getWd().parent);

        for (String fileName : outFiles) {
            tree.create(new File(fileName, "class", 100));
        }
        tree.cd((GeneralTreeNode<FileSystem>) tree.getDirectoryTree().root());

        // Create the root-level files
        String[] rootFiles = {"dsa_project"};
        for (String fileName : rootFiles) {
            tree.create(new File(fileName, "iml", 100));
        }
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
        testTree(directoryTree);

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
