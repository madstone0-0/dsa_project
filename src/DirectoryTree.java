//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class DirectoryTree {
    private final LinkedGeneralTree<String> directoryTree = new LinkedGeneralTree<>();
    private final GeneralTreeNode<String> root;
    private GeneralTreeNode<String> wd;

    DirectoryTree(String root) {
        this.root = this.directoryTree.addRoot(root);
        this.wd = this.root;
    }

    DirectoryTree() {
        this.root = this.directoryTree.addRoot("C:\\");
        this.wd = this.root;
    }

    public GeneralTreeNode<String> createDirectory(String dir) {
        return this.directoryTree.addChild(this.wd, dir);
    }

    public void removeDirectory(GeneralTreeNode<String> dir) {
        this.directoryTree.remove(dir);
    }

    public String renameDirectory(GeneralTreeNode<String> dir, String newDir) {
        return this.directoryTree.set(dir, newDir);
    }

    public GeneralTreeNode<String> getWd() {
        return this.wd;
    }

    public void cd(GeneralTreeNode<String> dir) {
        this.wd = dir;
    }

    public boolean isRoot(GeneralTreeNode<String> dir) {
        return this.directoryTree.isRoot(dir);
    }

    public LinkedGeneralTree<String> getDirectoryTree() {
        return this.directoryTree;
    }

    public String toString() {
        return this.directoryTree.toString();
    }
}
