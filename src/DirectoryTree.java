import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DirectoryTree {
    private final LinkedGeneralTree<FileSystem> directoryTree = new LinkedGeneralTree<>();
    private final GeneralTreeNode<FileSystem> root;
    private GeneralTreeNode<FileSystem> wd;
    private Comparator<? super TreeNode<FileSystem>> sorter = Comparator.comparing(o -> o.data.getName());

    DirectoryTree(FileSystem root) {
        this.root = this.directoryTree.addRoot(root);
        this.wd = this.root;
    }


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

    DirectoryTree() {
        this.root = this.directoryTree.addRoot(new Directory("C:"));
        this.wd = this.root;
    }

    private boolean existsInCurrentDirectory(String name) {
        return this.wd.children.stream().anyMatch(node -> node.data.getName().equals(name));
    }

    public GeneralTreeNode<FileSystem> create(FileSystem dir) {
        if (existsInCurrentDirectory(dir.getName())) {
            throw new IllegalArgumentException("Directory already exists");
        }
        return this.directoryTree.addChild(this.wd, dir);
    }

    public void remove(GeneralTreeNode<FileSystem> dir) {
        this.directoryTree.remove(dir);
    }

    public String rename(GeneralTreeNode<FileSystem> dir, String newName) {
        if (existsInCurrentDirectory(newName)) {
            throw new IllegalArgumentException("Directory already exists");
        }
        return dir.data.rename(newName);
    }

    public GeneralTreeNode<FileSystem> getWd() {
        return this.wd;
    }

    public void cd(GeneralTreeNode<FileSystem> dir) {
        this.wd = dir;
    }

    public boolean isRoot(GeneralTreeNode<FileSystem> dir) {
        return this.directoryTree.isRoot(dir);
    }

    public LinkedGeneralTree<FileSystem> getDirectoryTree() {
        return this.directoryTree;
    }

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

    public String generateTreeDisplay() {
        StringBuilder sb = new StringBuilder();
        generateTreeDisplay(root, sb, "", "");
        return sb.toString();
    }

    public String toString() {
        return generateTreeDisplay();
    }
}
