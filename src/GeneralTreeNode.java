public class GeneralTreeNode<T> extends TreeNode<T> {
    GeneralTreeNode(T data) {
        super(data);
    }

    GeneralTreeNode(T data, TreeNode<T> parent) {
        super(data, parent);
    }

    public void addChild(GeneralTreeNode<T> child) {
        super.addChild(child);
    }
}
