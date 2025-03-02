package requests;

//Import request libs and json
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.json.JSONArray;

import dataClasses.CachedInstances;
import dataClasses.Activity;
import dataClasses.Volunteer;
import dataClasses.ActivityList;    

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import utils.dateParser;
import utils.roles;

public class queries {

    private requestManager request;

    public queries(String user, String pass, Set<Cookie> cookies) {
        this.request = new requestManager(user, pass);
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
                if (!roles.isRoleNeeded(code)) {continue;}
                rolesNeeded.put(code, crewRole.optInt("effectif"));
            }

            JSONObject actJson = rawData.getJSONObject("activite");
            activity = new Activity(
                rawData.optString("id"),
                actJson.optString("libelle"),
                dateParser.stringToDateTime(rawData.optString("debut")),
                dateParser.stringToDateTime(rawData.optString("fin")),
                rolesNeeded
            );

            CachedInstances.addActivity(activityId, activity);
        }

        return activity;
    }

    public ActivityList APIgetActivitiesList(Integer structId, LocalDate startDate, LocalDate endDate) {
        String res = null;
        try {
            res = request.performRequest("activite?debut=" + dateParser.dateToString(startDate) + "&fin=" + dateParser.dateToString(endDate) + "&structure=" + structId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //Get the JSON object
        JSONArray rawData = res != null ? new JSONArray(res) : new JSONArray();
        if (rawData.isEmpty()) {return null;}

        ActivityList activityList = CachedInstances.getActivityList(structId);
        if (activityList == null) {
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

            activityList = new ActivityList(
                structId,
                startDate,
                endDate,
                activities
            );

            CachedInstances.addActivityList(structId, activityList);
        }

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
        JSONObject rawData = res != null ? new JSONObject(res) : new JSONObject();
        if (rawData.isEmpty()) {return null;}

        JSONArray registeredVolunteersJson = new JSONArray(rawData);
        List<Volunteer> registeredVolunteers = activity.getRegisteredVolunteers();
        for (int i = 0; i < registeredVolunteersJson.length(); i++) {
            JSONObject registeredVolunteerJson = registeredVolunteersJson.getJSONObject(i);
            Integer roleId = registeredVolunteerJson.optInt("role");
            if (!roles.isRoleNeeded(roleId)) {continue;}

            JSONObject userJson = rawData.getJSONObject("utilisateur");
            Volunteer volunteer = APIgetVolunteer(userJson.optString("id"));
            registeredVolunteers.add(volunteer);
        }

        activity.setRegisteredVolunteers(registeredVolunteers);
        return activity;
    }
}
