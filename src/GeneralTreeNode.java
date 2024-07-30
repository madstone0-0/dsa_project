import java.util.ArrayList;

public class GeneralTreeNode<T extends Comparable<T>> extends TreeNode<T> {
    ArrayList<TreeNode<T>> children = new ArrayList<>();

    // A set of children would ensure no duplicates and would be more efficient in checking existence but would
    // require a reinitialization everytime we wanted to change what attributes we were sorting by. Will look at again
    // Set<TreeNode<T>> children = new TreeSet<>();

    GeneralTreeNode(T data) {
        super(data);
    }

    GeneralTreeNode(T data, TreeNode<T> parent) {
        super(data, parent);
    }

    public void addChild(GeneralTreeNode<T> child) {
        child.parent = this;
        children.add(child);
    }
}
