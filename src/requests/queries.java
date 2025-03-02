package requests;

//Import request libs and json
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.json.JSONArray;

import dataClasses.CachedInstances;
import dataClasses.Activity;
import dataClasses.Volunteer;
import dataClasses.ActivityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import utils.DateParser;
import utils.Roles;

public class Queries {

    private RequestManager request;

    public Queries(String user, String pass, Set<Cookie> cookies) {
        this.request = new RequestManager(user, pass);
        this.request.setCookies(cookies);
    }

    public Activity APIgetActivity(String activityId) {
        String res = null;
        try {
            res = request.performRequest("seance/"+activityId+"/");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown activity");
            return null;
        }

        //Get the JSON object
        JSONObject rawData = res != null ? new JSONObject(res) : new JSONObject();
        if (rawData.isEmpty()) {return null;}

        Activity activity = CachedInstances.getActivity(activityId);
        if (activity == null) {        
            
            //Build needed crew roles
            HashMap<Integer, Integer> rolesNeeded = new HashMap<>();
            JSONArray rolesNeededJson = rawData.getJSONArray("roleConfigList");
            for (int i = 0; i < rolesNeededJson.length(); i++) {
                JSONObject crewRole = rolesNeededJson.getJSONObject(i);
                Integer code = crewRole.optInt("code");
                if (!Roles.isRoleNeeded(code)) {continue;}
                rolesNeeded.put(code, crewRole.optInt("effectif"));
            }

            JSONObject actJson = rawData.getJSONObject("activite");
            activity = new Activity(
                rawData.optString("id"),
                actJson.optString("libelle"),
                DateParser.stringToDateTime(rawData.optString("debut")),
                DateParser.stringToDateTime(rawData.optString("fin")),
                rolesNeeded
            );

            CachedInstances.addActivity(activityId, activity);
        }

        // Get registered volunteers for this activity asynchronously
        this.APIgetActivityRegisteredVolunteers(activity);

        return activity;
    }

    public ActivityList APIgetActivitiesList(Integer structId, LocalDate startDate, LocalDate endDate) {
        String res = null;
        try {
            res = request.performRequest("activite?debut=" + DateParser.dateToString(startDate) + "&fin=" + DateParser.dateToString(endDate) + "&structure=" + structId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //Get the JSON object
        JSONArray rawData = res != null ? new JSONArray(res) : new JSONArray();
        if (rawData.isEmpty()) {return null;}

  
        HashMap<String, Activity> activities = new HashMap<>();
        for (int i = 0; i < rawData.length(); i++) {
            JSONObject activityJson = rawData.getJSONObject(i);
            JSONArray activitySeance = activityJson.getJSONArray("seanceList");
            activitySeance.forEach(seance -> {
                JSONObject seanceJson = (JSONObject) seance;
                Activity activity = APIgetActivity(seanceJson.optString("id"));
                activities.put(activity.getId(), activity);
            });
        }

        ActivityList activityList = new ActivityList(
            structId,
            startDate,
            endDate,
            activities
        );

        CachedInstances.addActivityList(CachedInstances.getNewActLstId(), activityList);
    
        return activityList;
    }


    public Volunteer APIgetVolunteer(String nivol) {
        String res = null;
        try {
            res = request.performRequest("utilisateur/"+nivol+"/");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //Get the JSON object
        JSONObject rawData = res != null ? new JSONObject(res) : new JSONObject();
        if (rawData.isEmpty()) {return null;}

        Volunteer volunteer = CachedInstances.getVolunteer(nivol);
        if (volunteer == null) {
            volunteer = new Volunteer(
                rawData.optString("id"),
                rawData.optString("nom"),
                rawData.optString("prenom")
            );

            CachedInstances.addVolunteer(nivol, volunteer);
        }

        return volunteer;
    }

    public Activity APIgetActivityRegisteredVolunteers(Activity activity) {
        String res = null;
        try {
            res = request.performRequest("seance/"+activity.getId()+"/inscription/");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //Get the JSON object
        JSONArray rawData = res != null ? new JSONArray(res) : new JSONArray();
        if (rawData.isEmpty()) {return null;}

        List<Volunteer> registeredVolunteers = new ArrayList<>();
        for (int i = 0; i < rawData.length(); i++) {
            JSONObject registeredVolunteerJson = rawData.getJSONObject(i);
            Integer roleId = registeredVolunteerJson.optInt("role");
            if (!Roles.isRoleNeeded(roleId)) {continue;}

            JSONObject userJson = registeredVolunteerJson.getJSONObject("utilisateur");
            Volunteer volunteer = APIgetVolunteer(userJson.optString("id"));
            registeredVolunteers.add(volunteer);
            volunteer.addActivity(activity.getId(), roleId);
        }

        activity.setRegisteredVolunteers(registeredVolunteers);
        return activity;
    }
}
