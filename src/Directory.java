import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystem {

    public Directory(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    //Updates the size of the directory and modifies the last modified date
    private void updateSize(long change) {
        size += change;
        dateModified = LocalDateTime.now();
    }

    //String representation of the directory
    @Override
    public String toString() {
        return String.format("%s/", name);
    }

}
