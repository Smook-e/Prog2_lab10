package model;
public enum DifficultyLevel {
    EASY("easy", 10),
    MEDIUM("medium", 15),
    HARD("hard", 20);
    
    private String folderName;
    private int cellsToRemove;
    
    DifficultyLevel(String folderName, int cellsToRemove) {
        this.folderName = folderName;
        this.cellsToRemove = cellsToRemove;
    }
    public String getfolderName() { return folderName; }
    public int getCellsToRemove() { return cellsToRemove; }
}
