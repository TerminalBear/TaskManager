import java.time.temporal.ChronoUnit;

public class DecisionTree {
    private double threshold = 0.5;
    private Node root;

    public DecisionTree() {
        // construct the tree
        root = new Node("daysBetween < 1 && descriptionLength > 50");
        root.setTrueBranch(new Node(1));
        root.setFalseBranch(new Node("daysBetween < 3 && descriptionLength > 30"));
        root.getFalseBranch().setTrueBranch(new Node(2));
        root.getFalseBranch().setFalseBranch(new Node("daysBetween < 7"));
        root.getFalseBranch().getFalseBranch().setTrueBranch(new Node(3));
        root.getFalseBranch().getFalseBranch().setFalseBranch(new Node("descriptionLength > 100"));
        root.getFalseBranch().getFalseBranch().getFalseBranch().setTrueBranch(new Node(4));
        root.getFalseBranch().getFalseBranch().getFalseBranch().setFalseBranch(new Node(5));
    }

    public int predictPriority(Task task) {
        // traverse the tree based on the task's data and return the predicted priority
        Node currentNode = root;
        while (currentNode.getPriority() == null) {
            if (evaluateCondition(currentNode.getCondition(), task)) {
                currentNode = currentNode.getTrueBranch();
            } else {
                currentNode = currentNode.getFalseBranch();
            }
        }
        return currentNode.getPriority() != null ? currentNode.getPriority() : -1;
    }

    public void adjust(Task task, int predictedPriority) {
        // This is a simplified example and the actual implementation would be more
        // complex
        if (predictedPriority == 1) {
            // If the predicted priority was too high, lower the threshold
            threshold -= 0.1;
        } else if (predictedPriority == 5) {
            // If the predicted priority was too low, increase the threshold
            threshold += 0.1;
        }
    }

    public double getThreshold() {
        return threshold;
    }

    private boolean evaluateCondition(String condition, Task task) {
        // evaluate the condition based on the task's data
        // this is a simplified example and the actual implementation would be more
        // complex
        if (condition.contains("daysBetween")) {
            long daysBetween = ChronoUnit.DAYS.between(task.getCreationDate(), task.getDueDate());
            if (condition.contains("< 1")) {
                return daysBetween < 1;
            } else if (condition.contains("< 3")) {
                return daysBetween < 3;
            } else if (condition.contains("< 7")) {
                return daysBetween < 7;
            }
        } else if (condition.contains("descriptionLength")) {
            int descriptionLength = task.getDescription().length();
            if (condition.contains("> 50")) {
                return descriptionLength > 50;
            } else if (condition.contains("> 30")) {
                return descriptionLength > 30;
            } else if (condition.contains("> 100")) {
                return descriptionLength > 100;
            }
        }
        return false;
    }
}