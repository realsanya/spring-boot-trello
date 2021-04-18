package ru.itis.javalab.trello.impl.models;

public enum ProjectRole {
    OWNER("OWNER"),
    DEVELOPER("DEVELOPER"),
    DESIGNER("DESIGNER"),
    QA("QA");

    private final String role;

    ProjectRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
