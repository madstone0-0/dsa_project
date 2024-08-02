import java.time.LocalDateTime;

public class Directory extends FileSystem {

    public Directory(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    private void updateSize(long change) {
        size += change;
        dateModified = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("%s/", name);
    }

}
