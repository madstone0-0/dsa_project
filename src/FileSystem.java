import java.time.LocalDateTime;

public abstract class FileSystem {
    protected String name;
    protected long size;
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateModified;

    public FileSystem(String name) {
        this.name = name;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.size = 0;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
        this.dateModified = LocalDateTime.now();
    }

    public long getSize() {
        return this.size;
    }

    public LocalDateTime getDateCreated() {
        return this.dateCreated;
    }

    public LocalDateTime getDateModified() {
        return this.dateModified;
    }
    
    public abstract boolean isDirectory();
}
