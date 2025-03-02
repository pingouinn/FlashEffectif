package dataClasses;

import java.util.HashMap;

public class CachedInstances {
    
    private static HashMap<String, Activity> activities = new HashMap<>();
    private static HashMap<String, Volunteer> volunteers = new HashMap<>();
    private static HashMap<Integer, ActivityList> activityLists = new HashMap<>();

    public static HashMap<String, Activity> getActivities() {
        return activities;
    }

    public static HashMap<String, Volunteer> getVolunteers() {
        return volunteers;
    }

    public static HashMap<Integer, ActivityList> getActivityLists() {
        return activityLists;
    }

    public static void addActivity(String id, Activity activity) {
        activities.put(id, activity);
    }

    public static void addVolunteer(String id, Volunteer volunteer) {
        volunteers.put(id, volunteer);
    }

    public static void addActivityList(Integer id, ActivityList activityList) {
        activityLists.put(id, activityList);
    }

    public static Activity getActivity(String id) {
        return activities.get(id);
    }

    public static Volunteer getVolunteer(String id) {
        return volunteers.get(id);
    }

    public static ActivityList getActivityList(Integer id) {
        return activityLists.get(id);
    }

    public static Integer getNewActLstId() {
        return activityLists.size();
    }

    public static ActivityList getLastList() {
        return activityLists.get(activityLists.size()-1);
    }
}
