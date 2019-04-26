package com.bestkitchen.entity;

public class Client {

    private Long id;
    private String clientName;
    private String clientLastName;
    private String clientSecondLastName;
    private String clientObservations;

    public Long getId() {
        return id;
    }

    @Override
    public String toString(){
        return this.getClientName() + " " + this.getClientLastName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientSecondLastName() {
        return clientSecondLastName;
    }

    public void setClientSecondLastName(String clientSecondLastName) {
        this.clientSecondLastName = clientSecondLastName;
    }

    public String getClientObservations() {
        return clientObservations;
    }

    public void setClientObservations(String clientObservations) {
        this.clientObservations = clientObservations;
    }
}
