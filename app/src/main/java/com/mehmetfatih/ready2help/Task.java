package com.mehmetfatih.ready2help;

public class Task {

    private String taskName;
    private String taskDescription;
    private String taskDate;
    private String taskPhone;
    private String taskAddress;
    private String taskStatus;
    private String taskOwner;

    public Task() {
    }

    public Task(String taskName, String taskDescription, String taskDate, String taskPhone, String taskAddress, String taskStatus, String taskOwner) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskPhone = taskPhone;
        this.taskAddress = taskAddress;
        this.taskStatus = taskStatus;
        this.taskOwner = taskOwner;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskPhone() {
        return taskPhone;
    }

    public void setTaskPhone(String taskPhone) {
        this.taskPhone = taskPhone;
    }

    public String getTaskAddress() {
        return taskAddress;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskDate='" + taskDate + '\'' +
                ", taskPhone='" + taskPhone + '\'' +
                ", taskAddress='" + taskAddress + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskOwner='" + taskOwner + '\'' +
                '}';
    }
}
