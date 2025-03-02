package dataClasses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;

public class Activity {

    // Main infos
    private String id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;

    // Roles needed
    private HashMap<Integer, Integer> rolesNeeded; // roleId -> number of volunteers needed

    // Current registered volunteers 
    private List<Volunteer> registeredVolunteers = null;

    public Activity(String id, String name, LocalDateTime start, LocalDateTime end, HashMap<Integer, Integer> rolesNeeded) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.rolesNeeded = rolesNeeded;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public HashMap<Integer, Integer> getRolesNeeded() {
        return rolesNeeded;
    }

    public int getRoleVolunteersNeeded(Integer roleId) {
        return rolesNeeded.get(roleId);
    }

    public List<Volunteer> getRegisteredVolunteers() {
        return registeredVolunteers;
    }

    public void setRegisteredVolunteers(List<Volunteer> registeredVolunteers) {
        this.registeredVolunteers = registeredVolunteers;
    }

    public boolean isActivityComplete() {
        //Checks for every role needded if the number of registered volunteers is enough
        for (Integer roleId : rolesNeeded.keySet()) {
            if (registeredVolunteers.stream().filter(volunteer -> volunteer.getActivityRole(this.id) == roleId).count() < rolesNeeded.get(roleId)) {
                return false;
            }
        }
        return true;
    }
}
