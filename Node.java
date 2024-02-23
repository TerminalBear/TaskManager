public class Node {
    private String condition;
    private Node trueBranch;
    private Node falseBranch;
    private Integer priority;

    public Node(String condition) {
        this.condition = condition;
        this.priority = null;
    }

    public Node(Integer priority) {
        this.condition = null;
        this.priority = priority;
    }

    // getters
    public String getCondition() {
        return condition;
    }

    public Node getTrueBranch() {
        return trueBranch;
    }

    public Node getFalseBranch() {
        return falseBranch;
    }

    public Integer getPriority() {
        return priority;
    }

    // setters
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setTrueBranch(Node trueBranch) {
        this.trueBranch = trueBranch;
    }

    public void setFalseBranch(Node falseBranch) {
        this.falseBranch = falseBranch;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}