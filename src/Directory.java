import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem {
    private String name;
    private FileSystem parent;
    private List<FileSystem> contents;

    public Directory(String name) {
        this.name = name;
        this.contents = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public FileSystem getParent() {
        return parent;
    }

    @Override
    public void setParent(FileSystem parent) {
        this.parent = parent;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addItem(FileSystem item) {
        contents.add(item);
        item.setParent(this);
    }

    public void removeItem(FileSystem item) {
        contents.remove(item);
        item.setParent(null);
    }

    public List<FileSystem> getContents() {
        return new ArrayList<>(contents);
    }
}
