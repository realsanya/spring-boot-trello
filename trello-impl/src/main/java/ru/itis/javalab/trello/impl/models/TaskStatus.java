package ru.itis.javalab.trello.impl.models;

public enum TaskStatus {
    TODO("TODO"),
    PAUSE("PAUSE"),
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    ARCHIVED("ARCHIVED");


    private final String taskStatus;

    TaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() {
        return taskStatus;
    }
}
