package de.hse.swt.timemanagement;

public class User {
    private int id;
    private String firstName;
    private String lastname;
    private String eMail;
    private String hierarchy;
    private int groupId;
    private int vacDays;

    public User(int id, String firstName, String lastName, String eMail, String hierarchy, int groupId, int vacDays) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastName;
        this.eMail = eMail;
        this.hierarchy = hierarchy;
        this.groupId = groupId;
        this.vacDays = vacDays;
    }

    final public int getID() {
        return this.id;
    }

    final public String getFirstName() {
        return this.firstName;
    }

    final public String getLastName() {
        return this.lastname;
    }

    final public String getEMail() {
        return this.eMail;
    }

    final public String getHierarchy() {
        return this.hierarchy;
    }

    final public int getGroupID() {
        return this.groupId;
    }

    final public int getVacDays() {
        return this.vacDays;
    }

    final public void clearData() {
        this.id = -1;
        this.firstName = null;
        this.lastname = null;
        this.eMail = null;
        this.hierarchy = null;
        this.groupId = -1;
        this.vacDays = -1;
    }
}
