package dsa.challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TournamentWinnerTest {

    private final TournamentWinner tw = new TournamentWinner();

    @Test
    void testSingleMatchHomeWins() {
        String[][] competitions = {{"TeamA", "TeamB"}};
        int[] results = {1};
        assertEquals("TeamA", tw.getLeaderTeam(competitions, results));
    }

    @Test
    void testSingleMatchAwayWins() {
        String[][] competitions = {{"TeamA", "TeamB"}};
        int[] results = {0};
        assertEquals("TeamB", tw.getLeaderTeam(competitions, results));
    }

    @Test
    void testMultipleMatches() {
        String[][] competitions = {
                {"TeamA", "TeamB"},
                {"TeamB", "TeamC"},
                {"TeamC", "TeamA"}
        };
        int[] results = {0, 0, 1};
        assertEquals("TeamC", tw.getLeaderTeam(competitions, results));
    }

    @Test
    void testMultipleTeamsTieByPointsLastWinnerWins() {
        String[][] competitions = {
                {"A", "B"},
                {"B", "C"},
                {"C", "A"}
        };
        int[] results = {1, 1, 1};
        assertEquals("C", tw.getLeaderTeam(competitions, results));
    }

    @Test
    void testNoMatches() {
        String[][] competitions = {};
        int[] results = {};
        assertThrows(IllegalArgumentException.class, () -> tw.getLeaderTeam(competitions, results));
    }

    @Test
    void testMismatchedInputLengths() {
        String[][] competitions = {{"A", "B"}, {"B", "C"}};
        int[] results = {1};
        assertThrows(IllegalArgumentException.class, () -> tw.getLeaderTeam(competitions, results));
    }

    @Test
    void testNegativeOrInvalidResultsThrows() {
        String[][] competitions = {{"A", "B"}};
        int[] results = {2};
        assertThrows(IllegalArgumentException.class, () -> tw.getLeaderTeam(competitions, results));
    }

}