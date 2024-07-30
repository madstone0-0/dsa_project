import java.time.LocalDateTime;

public abstract class FileSystem implements Comparable<FileSystem> {
    protected String name;
    protected long size;
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateModified;

    public FileSystem(String name) {
        this.name = name;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.size = 0;
    }

    public String getName() {
        return name;
    }

    public String rename(String newName) {
        var temp = this.name;
        this.name = newName;
        this.dateModified = LocalDateTime.now();
        return temp;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof FileSystem otherFileSystem)) return false;
        return this.name.equals(otherFileSystem.name);
    }

    @Override
    public int compareTo(FileSystem o) {
        return this.name.compareTo(o.name);
    }

    public abstract boolean isDirectory();
}
