import java.time.LocalDateTime;

//Abstract base class representing file system objects (files or directories).
public abstract class FileSystem implements Comparable<FileSystem> {
    protected String name;
    protected long size;
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateModified;

    //Constructor for FileSystem objects.
    public FileSystem(String name) {
        this.name = name;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.size = 0;
    }

    //Gets the name of the file or directory.
    public String getName() {
        return name;
    }

    //Renames the file or directory.
    public String rename(String newName) {
        var temp = this.name;
        this.name = newName;
        this.dateModified = LocalDateTime.now();
        return temp;
    }

    //Gets the size of the file or directory.
    public long getSize() {
        return this.size;
    }

    //Gets the creation date and time.
    public LocalDateTime getDateCreated() {
        return this.dateCreated;
    }

    //Gets the last modification date and time.
    public LocalDateTime getDateModified() {
        return this.dateModified;
    }

    //Checks if this FileSystem object is equal to another object.
    //Two FileSystem objects are considered equal if they have the same name.
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof FileSystem otherFileSystem)) return false;
        return this.name.equals(otherFileSystem.name);
    }

    //Compares this FileSystem object with another FileSystem object.
    //The comparison is based on the names of the objects.
    @Override
    public int compareTo(FileSystem o) {
        return this.name.compareTo(o.name);
    }

    //Abstract method to determine if this is a directory.
    public abstract boolean isDirectory();
}
