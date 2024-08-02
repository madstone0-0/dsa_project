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
    private final GeneralTreeNode<FileSystem> root;
    private GeneralTreeNode<FileSystem> wd; // Current working directory
    // Default sorter for tree nodes
    private Comparator<? super TreeNode<FileSystem>> sorter = Comparator.comparing(o -> o.data.getName());
    private final Set<GeneralTreeNode<FileSystem>> clipboard = new HashSet<>();

    /**
     * Constructor with a specified root.
     * 
     * @param root the root file system node to initialize the tree.
     */
    DirectoryTree(FileSystem root) {
        this.root = this.directoryTree.addRoot(root);
        this.wd = this.root;
    }

    /**
     * Sets the sorter for tree nodes by name.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortByName(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getName());
        if (!ascending) sorter = sorter.reversed();
    }

    /**
     * Sets the sorter for tree nodes by size.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortBySize(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getSize());
        if (!ascending) sorter = sorter.reversed();
    }

    /**
     * Sets the sorter for tree nodes by modified date.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortByModifiedDate(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getDateModified());
        if (!ascending) sorter = sorter.reversed();
    }

    /**
     * Sets the sorter for tree nodes by created date.
     * 
     * @param ascending true for ascending order, false for descending order.
     */
    public void sortByCreatedDate(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getDateCreated());
        if (!ascending) sorter = sorter.reversed();
    }

    /**
     * Default constructor creating a root directory "C:".
     */
    DirectoryTree() {
        this.root = this.directoryTree.addRoot(new Directory("C:"));
        this.wd = this.root;
    }

    /**
     * Checks if a file/directory with the given name already exists in the current directory.
     * 
     * @param name the name of the file or directory to check.
     * @return true if the file/directory exists, false otherwise.
     */
    private boolean existsInCurrentDirectory(String name) {
        return this.wd.children.stream().anyMatch(node -> node.data.getName().equals(name));
    }

    public void cut(ArrayList<GeneralTreeNode<FileSystem>> items) {

        clipboard.addAll(items);
        for (var item : items) {
            ((GeneralTreeNode<FileSystem>) item.parent).children.remove(item);
            item.parent = item;
        }

        var folder = wd.data;
        if (folder instanceof Directory) {
            folder.setDateModified(LocalDateTime.now());
        }
    }

    public void paste(ArrayList<Integer> indices) {
        int i = 0;
        for (var index : indices) {
            var item = clipboard.iterator().next();
            if (index == i) {
                clipboard.remove(item);
                this.wd.children.add(item);
            }
            i++;
        }

        var folder = wd.data;
        if (folder instanceof Directory) {
            folder.setDateModified(LocalDateTime.now());
        }
    }

    public String search(String name) {
        BinarySearchTree<FileSystem> bst = new BinarySearchTree<>(wd);
        return bst.searchResult(new Directory(name), wd);
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
            throw new IllegalArgumentException("Directory already exists");
        }
        return this.directoryTree.addChild(this.wd, dir);
    }

    /**
     * Removes a file or directory from the tree.
     * 
     * @param dir the node representing the file or directory to remove.
     */
    public void remove(GeneralTreeNode<FileSystem> dir) {
        this.directoryTree.remove(dir);
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
            throw new IllegalArgumentException("Directory already exists");
        }
        return dir.data.rename(newName);
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
        this.wd = dir;
    }

    /**
     * Checks if a given node is the root of the tree.
     * 
     * @param dir the node to check.
     * @return true if the node is the root, false otherwise.
     */
    public boolean isRoot(GeneralTreeNode<FileSystem> dir) {
        return this.directoryTree.isRoot(dir);
    }

    /**
     * Getter for the directory tree.
     * 
     * @return the directory tree.
     */
    public LinkedGeneralTree<FileSystem> getDirectoryTree() {
        return this.directoryTree;
    }

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

        var kinder = node.children.stream().sorted(sorter).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < kinder.size(); i++) {
            GeneralTreeNode<FileSystem> child = (GeneralTreeNode<FileSystem>) kinder.get(i);
            if (i < kinder.size() - 1) {
                generateTreeDisplay(child, sb, childrenPrefix + "├── ", childrenPrefix + "│   ");

            } else {
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
        generateTreeDisplay(root, sb, "", "");
        return sb.toString();
    }

    /**
     * Provides a string representation of the directory tree.
     * 
     * @return the string representation of the directory tree.
     */
    @Override
    public String toString() {
        return generateTreeDisplay();
    }
}
