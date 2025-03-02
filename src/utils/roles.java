package utils;

import java.util.HashMap;

public class Roles {

    private static final HashMap<Integer, String> neededRoles = new HashMap<>();
    static {
        neededRoles.put(75, "Chef d'Intervention");
        neededRoles.put(167, "PSE 2");
        neededRoles.put(166, "PSE 1");
    }

    public static boolean isRoleNeeded(Integer roleId) {
        return neededRoles.containsKey(roleId);
    }

    public static HashMap<Integer, String> getNeededRoles() {
        return neededRoles;
    }

    public static String getRoleName(Integer roleId) {
        return neededRoles.get(roleId);
    }
}