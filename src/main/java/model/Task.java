package model;

/**
 * Abstract base class representing a task in the Dog application.
 * Provides common functionality for all task types.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the specified description.
     *
     * @param description the task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a task with the specified description and completion status.
     *
     * @param description the task description.
     * @param isDone true if the task is completed, false otherwise.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns a string representation of the task for saving to file.
     *
     * @return the task in save format.
     */
    public abstract String toSaveFormat();

    /**
     * Returns the status icon for the task.
     *
     * @return "X" if completed, " " if not completed.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Checks if the task description contains the given keyword.
     *
     * @param keyword the keyword to search for (case-insensitive).
     * @return true if the description contains the keyword, false otherwise.
     */
    public boolean containsKeyword(String keyword) {
        return this.description.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Returns a string representation of the task for display.
     *
     * @return the task in display format.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}