package ru.itis.javalab.trello.impl.models;

public enum Role {
    OWNER("OWNER"),
    DEVELOPER("DEVELOPER"),
    DESIGNER("DESIGNER"),
    QA("QA");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
