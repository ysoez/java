package security;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputSanitization {

    private static final String USERNAME_REGEX = "\\w+";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    public static void main(String[] args) {
        System.out.print("Enter your username (alphanumeric and underscores only): ");
        try (var scanner = new Scanner(System.in)) {
            String username = scanner.nextLine();
            if (isNotValidUsername(username)) {
                return;
            }
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            if (isNotValidEmail(email)) {
                return;
            }
            String sanitizedUsername = sanitize(username);
            String sanitizedEmail = sanitize(email);
            System.out.println("Sanitized Username: " + sanitizedUsername);
            System.out.println("Sanitized Email: " + sanitizedEmail);
        }
    }

    private static boolean isNotValidUsername(String username) {
        if (username.matches(USERNAME_REGEX)) {
            return false;
        }
        System.out.println("Invalid username! It should only contain alphanumeric characters and underscores.");
        return true;
    }

    private static boolean isNotValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            System.out.println("Invalid email address!");
            return true;
        }
        return false;
    }

    private static String sanitize(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

}
