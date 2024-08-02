import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DirectoryTree {
    //The tree structure representing the file system
    private final LinkedGeneralTree<FileSystem> directoryTree = new LinkedGeneralTree<>();
    private final GeneralTreeNode<FileSystem> root;
    private GeneralTreeNode<FileSystem> wd;
    //Default sorter for tree nodes
    private Comparator<? super TreeNode<FileSystem>> sorter = Comparator.comparing(o -> o.data.getName());

     //Constructor with a specified root
    DirectoryTree(FileSystem root) {
        this.root = this.directoryTree.addRoot(root);
        this.wd = this.root;
    }


    //Sorting methods
    public void sortByName(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getName());
        if (!ascending) sorter = sorter.reversed();
    }

    public void sortBySize(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getSize());
        if (!ascending) sorter = sorter.reversed();
    }

    public void sortByModifiedDate(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getDateModified());
        if (!ascending) sorter = sorter.reversed();
    }

    public void sortByCreatedDate(boolean ascending) {
        sorter = Comparator.comparing(n -> n.data.getDateCreated());
        if (!ascending) sorter = sorter.reversed();
    }

    //Default constructor creating a root directory "C:"
    DirectoryTree() {
        this.root = this.directoryTree.addRoot(new Directory("C:"));
        this.wd = this.root;
    }

    //Checks if a file/directory with the given name already exists in the current directory
    private boolean existsInCurrentDirectory(String name) {
        return this.wd.children.stream().anyMatch(node -> node.data.getName().equals(name));
    }

    //Creates a new file or directory in the current working directory
    public GeneralTreeNode<FileSystem> create(FileSystem dir) {
        if (existsInCurrentDirectory(dir.getName())) {
            throw new IllegalArgumentException("Directory already exists");
        }
        return this.directoryTree.addChild(this.wd, dir);
    }

    // Removes a file or directory
    public void remove(GeneralTreeNode<FileSystem> dir) {
        this.directoryTree.remove(dir);
    }

    //Renames a file or directory
    public String rename(GeneralTreeNode<FileSystem> dir, String newName) {
        if (existsInCurrentDirectory(newName)) {
            throw new IllegalArgumentException("Directory already exists");
        }
        return dir.data.rename(newName);
    }

    //Getter for current working directory
    public GeneralTreeNode<FileSystem> getWd() {
        return this.wd;
    }

    // Changes the current working directory
    public void cd(GeneralTreeNode<FileSystem> dir) {
        this.wd = dir;
    }

    //Checks if a given node is the root
    public boolean isRoot(GeneralTreeNode<FileSystem> dir) {
        return this.directoryTree.isRoot(dir);
    }

    //Getter for the directory tree
    public LinkedGeneralTree<FileSystem> getDirectoryTree() {
        return this.directoryTree;
    }

    //Recursive method to generate a string representation of the tree structure
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

    //Generates a string representation of the entire tree
    public String generateTreeDisplay() {
        StringBuilder sb = new StringBuilder();
        generateTreeDisplay(root, sb, "", "");
        return sb.toString();
    }

    //String representation of the directory tree
    public String toString() {
        return generateTreeDisplay();
    }
}
