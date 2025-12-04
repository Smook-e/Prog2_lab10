package model;
public enum DifficultyLevel {
    EASY("easy", 10),
    MEDIUM("medium", 15),
    HARD("hard", 20);
    
    private String fileName;
    private int cellsToRemove;
    
    DifficultyLevel(String fileName, int cellsToRemove) {
        this.fileName = fileName;
        this.cellsToRemove = cellsToRemove;
    }
    public String getfileName() { return fileName; }
    public int getcellsToRemove() { return cellsToRemove; }
}
