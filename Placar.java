import java.util.ArrayList;
import java.util.List;



public class Placar {
    private List<PlayerScore> scores;

    public Placar() {
        scores = new ArrayList<>();
    }

    public void addScore(String playerName, int score) {
        PlayerScore existingScore = findPlayerScore(playerName);

        if (existingScore != null) {
            existingScore.setScore(existingScore.getScore() + score);
        } else {
            PlayerScore playerScore = new PlayerScore(playerName, score);
            scores.add(playerScore);
        }
    }

    public List<PlayerScore> getScores() {
        return new ArrayList<>(scores);
    }

    private PlayerScore findPlayerScore(String playerName) {
        for (PlayerScore score : scores) {
            if (score.getPlayerName().equals(playerName)) {
                return score;
            }
        }
        return null;
    }




    public static class PlayerScore {
        private String playerName;
        private int score;

        public PlayerScore(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return playerName + ": " + score;
        }
        
        
    }
    
    
    
    
}
