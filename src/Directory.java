import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystem {
    private List<FileSystem> contents;

    public Directory(String name, String type) {
        super(name, type);
        this.contents = new ArrayList<>();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addItem(FileSystem item) {
        contents.add(item);
        updateSize(item.getSize());
    }

    public void removeItem(FileSystem item) {
        if (contents.remove(item)) {
            updateSize(-item.getSize());
        }
    }

    private void updateSize(long change) {
        size += change;
        dateModified = LocalDateTime.now();
    }

    public List<FileSystemItem> getContents() {
        return new ArrayList<>(contents);
    }
}
