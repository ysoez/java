package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.HashMap;

import static dsa.Algorithm.Complexity.Value.LINEAR;
import static dsa.array.Arrays.isEmpty;

class TournamentWinner {

    private static final int HOME_TEAM_WON = 1;
    private static final int WIN_POINTS = 3;

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    String getLeaderTeam(String[][] competitions, int[] results) {
        if (isEmpty(competitions) || isEmpty(results) || competitions.length != results.length)
            throw new IllegalArgumentException();
        var teamScores = new HashMap<String, Integer>();
        var leaderTeam = "";
        for (int i = 0; i < competitions.length; i++) {
            var matchTeams = competitions[i];
            var homeTeam = matchTeams[0];
            var awayTeam = matchTeams[1];
            var matchResult = results[i];
            if (matchResult != 0 && matchResult != 1)
                throw new IllegalArgumentException();
            var winningTeam = matchResult == HOME_TEAM_WON ? homeTeam : awayTeam;
            var winningTeamScore = teamScores.getOrDefault(winningTeam, 0) + WIN_POINTS;
            teamScores.put(winningTeam, winningTeamScore);
            var currentLeaderTeamScore = teamScores.computeIfAbsent(leaderTeam, _ -> 0);
            //
            // ~ last wins if equal
            //
            if (winningTeamScore >= currentLeaderTeamScore)
                leaderTeam = winningTeam;
        }
        return leaderTeam;
    }

}
