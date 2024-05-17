package bank.api.mock;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserResidenceDatabase {

    private static final String USER_RESIDENCE_FILE = "user-residence.txt";
    private final Map<String, String> userCountries;

    public UserResidenceDatabase(){
        this.userCountries = loadUsersResidence();
    }

    public String getUserResidence(String user) {
        if (!userCountries.containsKey(user)) {
            throw new RuntimeException("user " + user + " doesn't exist");
        }
        return userCountries.get(user);
    }

    private Map<String, String> loadUsersResidence() {
        Map<String, String> userToResidence = new HashMap<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(USER_RESIDENCE_FILE);

        if (inputStream == null) {
            return Collections.emptyMap();
        }

        var scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String []userResidencePair = line.split(" ");
            userToResidence.put(userResidencePair[0], userResidencePair[1]);
        }

        return Collections.unmodifiableMap(userToResidence);
    }

}
