import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a directory tree structure for managing files and directories in a file system.
 * Provides methods for sorting, creating, removing, renaming, and navigating through directories.
 */
public class DirectoryTree {

    // The tree structure representing the file system
    private final LinkedGeneralTree<FileSystem> directoryTree = new LinkedGeneralTree<>();
    private final GeneralTreeNode<FileSystem> root; // Root of the directory tree
    private GeneralTreeNode<FileSystem> wd; // Current working directory
    private Comparator<? super TreeNode<FileSystem>> sorter = Comparator.comparing(o -> o.data.getName()); // Default sorter by name
    private final Set<GeneralTreeNode<FileSystem>> clipboard = new HashSet<>(); // Clipboard for cut-and-paste operations

    /**
     * Constructs a DirectoryTree with the specified root.
     * 
     * @param root the root file system node to initialize the tree.
     */
    DirectoryTree(FileSystem root) {
        this.root = this.directoryTree.addRoot(root);
        this.wd = this.root; // Set current working directory to root
    }

    /**
     * Sets the sorter for tree nodes by name.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortByName(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getName());
        if (!ascending) sorter = sorter.reversed(); // Reverse order if not ascending
    }

    /**
     * Sets the sorter for tree nodes by size.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortBySize(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getSize());
        if (!ascending) sorter = sorter.reversed(); // Reverse order if not ascending
    }

    /**
     * Sets the sorter for tree nodes by modified date.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortByModifiedDate(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getDateModified());
        if (!ascending) sorter = sorter.reversed(); // Reverse order if not ascending
    }

    /**
     * Sets the sorter for tree nodes by created date.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortByCreatedDate(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getDateCreated());
        if (!ascending) sorter = sorter.reversed(); // Reverse order if not ascending
    }

    /**
     * Default constructor creating a root directory "C:".
     */
    DirectoryTree() {
        this.root = this.directoryTree.addRoot(new Directory("C:")); // Create root directory "C:"
        this.wd = this.root; // Set current working directory to root
    }

    /**
     * Checks if a file/directory with the given name already exists in the current directory.
     * 
     * @param name the name of the file or directory to check.
     * @return true if the file/directory exists, false otherwise.
     */
    private boolean existsInCurrentDirectory(String name) {
        return this.wd.children.stream().anyMatch(node -> node.data.getName().equals(name)); // Check if name exists among children
    }

    /**
     * Cuts the specified items (files or directories) and adds them to the clipboard.
     * 
     * @param items the list of items to cut.
     */
    public void cut(ArrayList<GeneralTreeNode<FileSystem>> items) {
        clipboard.addAll(items); // Add items to clipboard
        for (var item : items) {
            ((GeneralTreeNode<FileSystem>) item.parent).children.remove(item); // Remove item from parent
            item.parent = item; // Set parent of item to itself (detached state)
        }

        // Update the modified date of the current working directory
        var folder = wd.data;
        if (folder instanceof Directory) {
            folder.setDateModified(LocalDateTime.now());
        }
    }

    /**
     * Pastes the items from the clipboard to the specified indices in the current working directory.
     * 
     * @param indices the indices where the items should be pasted.
     */
    public void paste(ArrayList<Integer> indices) {
        int i = 0;
        for (var index : indices) {
            var item = clipboard.iterator().next(); // Get an item from the clipboard
            if (index == i) {
                clipboard.remove(item); // Remove item from clipboard after pasting
                this.wd.children.add(item); // Add item to current working directory
            }
            i++;
        }

        // Update the modified date of the current working directory
        var folder = wd.data;
        if (folder instanceof Directory) {
            folder.setDateModified(LocalDateTime.now());
        }
    }

    /**
     * Searches for a directory with the specified name using a binary search tree.
     * 
     * @param name the name of the directory to search for.
     * @return the search result as a string.
     */
    public String search(String name) {
        BinarySearchTree<FileSystem> bst = new BinarySearchTree<>(wd);
        return bst.searchResult(new Directory(name), wd); // Search for the directory
    }

    /**
     * Creates a new file or directory in the current working directory.
     * 
     * @param dir the file or directory to create.
     * @return the node representing the newly created file or directory.
     * @throws IllegalArgumentException if a file/directory with the same name already exists.
     */
    public GeneralTreeNode<FileSystem> create(FileSystem dir) {
        if (existsInCurrentDirectory(dir.getName())) {
            throw new IllegalArgumentException("Directory already exists"); // Check for existing name
        }
        return this.directoryTree.addChild(this.wd, dir); // Add new file or directory to tree
    }

    /**
     * Removes a file or directory from the tree.
     * 
     * @param dir the node representing the file or directory to remove.
     */
    public void remove(GeneralTreeNode<FileSystem> dir) {
        this.directoryTree.remove(dir); // Remove node from tree
    }

    /**
     * Renames a file or directory.
     * 
     * @param dir the node representing the file or directory to rename.
     * @param newName the new name for the file or directory.
     * @return the new name of the file or directory.
     * @throws IllegalArgumentException if a file/directory with the new name already exists.
     */
    public String rename(GeneralTreeNode<FileSystem> dir, String newName) {
        if (existsInCurrentDirectory(newName)) {
            throw new IllegalArgumentException("Directory already exists"); // Check for existing name
        }
        return dir.data.rename(newName); // Rename the file or directory
    }

    /**
     * Getter for the current working directory.
     * 
     * @return the current working directory node.
     */
    public GeneralTreeNode<FileSystem> getWd() {
        return this.wd;
    }

    /**
     * Changes the current working directory.
     * 
     * @param dir the new current working directory.
     */
    public void cd(GeneralTreeNode<FileSystem> dir) {
        this.wd = dir; // Set the new working directory
    }

    /**
     * Checks if a given node is the root of the tree.
     * 
     * @param dir the node to check.
     * @return true if the node is the root, false otherwise.
     */
    public boolean isRoot(GeneralTreeNode<FileSystem> dir) {
        return this.directoryTree.isRoot(dir); // Check if node is root
    }

    /**
     * Getter for the directory tree.
     * 
     * @return the directory tree.
     */
    public LinkedGeneralTree<FileSystem> getDirectoryTree() {
        return this.directoryTree;
    }

    /**
     * Getter for the clipboard.
     * 
     * @return the clipboard set containing cut items.
     */
    public Set<GeneralTreeNode<FileSystem>> getClipboard() {
        return clipboard;
    }

    /**
     * Recursive method to generate a string representation of the tree structure.
     * 
     * @param node the current node to process.
     * @param sb the StringBuilder to append the string representation.
     * @param prefix the prefix to use for the current node.
     * @param childrenPrefix the prefix to use for child nodes.
     */
    public void generateTreeDisplay(GeneralTreeNode<FileSystem> node, StringBuilder sb, String prefix,
                                    String childrenPrefix) {
        sb.append(prefix);
        sb.append(node.data);
        sb.append('\n');

        // Sort children based on the current sorter
        var kinder = node.children.stream().sorted(sorter).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < kinder.size(); i++) {
            GeneralTreeNode<FileSystem> child = (GeneralTreeNode<FileSystem>) kinder.get(i);
            if (i < kinder.size() - 1) {
                // More children to process
                generateTreeDisplay(child, sb, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                // Last child to process
                generateTreeDisplay(child, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

    /**
     * Generates a string representation of the entire tree.
     * 
     * @return the string representation of the tree.
     */
    public String generateTreeDisplay() {
        StringBuilder sb = new StringBuilder();
        generateTreeDisplay(root, sb, "", ""); // Start from the root
        return sb.toString();
    }

    /**
     * Provides a string representation of the directory tree.
     * 
     * @return the string representation of the directory tree.
     */
    @Override
    public String toString() {
        return generateTreeDisplay(); // Use generateTreeDisplay to get the tree representation
    }
}
