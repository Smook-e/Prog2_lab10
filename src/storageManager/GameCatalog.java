package storageManager;

import model.DifficultyLevel;

public class GameCatalog {
    public boolean hasEasy() {
        return FileManager.exists(DifficultyLevel.EASY.getfolderName());
    }
    public boolean hasMedium() {
        return FileManager.exists(DifficultyLevel.MEDIUM.getfolderName());
    }
    public boolean hasHard() {
        return FileManager.exists(DifficultyLevel.HARD.getfolderName());
    }
    public boolean hasIncomplete() {
        return FileManager.exists("current"); 
    }
    public boolean hasAllLevels() {
        return hasEasy() && hasMedium() && hasHard();
    }
}
