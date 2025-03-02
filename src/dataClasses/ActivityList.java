package dataClasses;

import java.util.HashMap;
import java.time.LocalDate;

public class ActivityList {

    private int structId;
    private LocalDate start; 
    private LocalDate end;
    
    // Cache 
    private HashMap<String, Activity> activities = new HashMap<>();

    public ActivityList(int structId, LocalDate start, LocalDate end, HashMap<String, Activity> activities) {
        this.structId = structId;
        this.start = start;
        this.end = end;
        this.activities = activities;
    }

    public int getStructId() {
        return structId;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public HashMap<String, Activity> getActivities() {
        return activities;
    }

    public Activity getActivity(String activityId) {
        return activities.get(activityId);
    }
}
