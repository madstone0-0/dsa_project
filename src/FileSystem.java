import java.time.LocalDateTime;

/**
*Abstract base class representing file system objects (files or directories).
**/
public abstract class FileSystem implements Comparable<FileSystem> {
    protected String name;
    protected long size;
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateModified;

    /**
     * Constructor for FileSystem objects.
     * @param name The name of the file or directory
     */
    public FileSystem(String name) {
        this.name = name;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.size = 0;
    }

    /**
     * Gets the name of the file or directory.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Renames the file or directory.
     * @param newName The new name
     * @return The old name
     */
    public String rename(String newName) {
        var temp = this.name;
        this.name = newName;
        this.dateModified = LocalDateTime.now();
        return temp;
    }

    /**
     * Gets the size of the file or directory.
     * @return The size in bytes
     */
    public long getSize() {
        return this.size;
    }

    /**
     * Gets the creation date and time.
     * @return The creation date and time
     */
    public LocalDateTime getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Gets the last modification date and time.
     * @return The last modification date and time
     */

    public LocalDateTime getDateModified() {
        return this.dateModified;
    }

    /**
     * Checks if this FileSystem object is equal to another object.
     * Two FileSystem objects are considered equal if they have the same name.
     * @param other The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof FileSystem otherFileSystem)) return false;
        return this.name.equals(otherFileSystem.name);
    }

    /**
     * Compares this FileSystem object with another FileSystem object.
     * The comparison is based on the names of the objects.
     * @param o The FileSystem object to compare with
     * @return A negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(FileSystem o) {
        return this.name.compareTo(o.name);
    }

    /**
     * Abstract method to determine if this is a directory.
     * @return true if this is a directory, false otherwise
     */
    public abstract boolean isDirectory();
}
