package dataClasses;

import java.util.HashMap;

public class Volunteer {

    private String nivol;
    private String name;
    private String forename;

    private HashMap<String, Integer> activities = new HashMap<>();

    public Volunteer(String id, String name, String forename) {
        this.nivol = id;
        this.name = name;
        this.forename = forename;
    }

    public String getNivol() {
        return nivol;
    }

    public String getFullName() {
        return forename + " " + name;
    }

    public HashMap<String, Integer> getActivities() {
        return activities;
    }

    public void addActivity(String activityId, Integer roleId) {
        activities.put(activityId, roleId);
    }

    public int getActivityRole(String activityId) {
        return activities.get(activityId);
    }

}
