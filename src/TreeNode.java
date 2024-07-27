import java.util.ArrayList;

public abstract class TreeNode<T> {
    T data;
    TreeNode<T> parent;
    ArrayList<TreeNode<T>> children = new ArrayList<>();

    TreeNode(T data) {
        this.data = data;
    }

    TreeNode(T data, TreeNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public void addChild(TreeNode<T> child) {
        child.parent = this;
        children.add(child);
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", this.getClass().getSimpleName(), data.toString());
    }
}
