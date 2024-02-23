import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    public enum Status {
        INCOMPLETE, IN_PROGRESS, COMPLETE
    }

    // Properties of the Task class
    private String name;
    private String description;
    private int predictedPriority;
    private int userSetPriority;
    private LocalDate dueDate;
    private LocalDate creationTime;
    private Status status;

    public Task() {
        this.creationTime = LocalDate.now();
    }

    public Task(String name, String description, LocalDate creationTime, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.creationTime = creationTime;
        this.dueDate = dueDate;
    }

    // Getter and Setters for the properties
    public String getName() {
        return this.name;
    }

    public LocalDate getCreationTime() {
        return this.creationTime;
    }

    public int getUserSetPriority() {
        return this.userSetPriority;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getCreationDate() {
        return this.creationTime;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPredictedPriority(int predictedPriority) {
        this.predictedPriority = predictedPriority;
    }

    public void setUserSetPriority(int userSetPriority) {
        this.userSetPriority = userSetPriority;
    }

    public boolean setDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.dueDate = LocalDate.parse(dueDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", predictedPriority=" + predictedPriority +
                ", userSetPriority=" + userSetPriority +
                ", dueDate='" + dueDate + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // Object instance created, example of how to use the class
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setPredictedPriority(1);
        task.setUserSetPriority(1);
        task.setDueDate("2022-12-31");
        task.setStatus(Status.INCOMPLETE);

        // Print the object using toString method
        System.out.println(task);
    }
}