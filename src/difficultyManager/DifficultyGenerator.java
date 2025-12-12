//package difficultyManager;
//
//import java.util.List;
//import model.DifficultyLevel;
//import javaapplication19.SudokuBoard;
//
//public class DifficultyGenerator {
//    private final RandomPairs randomPairs = new RandomPairs();
//    public SudokuBoard removeCells(SudokuBoard solved,DifficultyLevel df)
//    {
//       SudokuBoard sb = solved.newBoard();//new ref
//       List<int[]>pairs = randomPairs.generateDistinctPairs(df.getCellsToRemove());
//       for(int[]pair:pairs)
//       {
//           int x = pair[0];
//           int y = pair[1];
//           sb.setCell(x, y, 0);//set new element in board
//       }
//       return sb;
//    }
//    public  SudokuBoard generate(SudokuBoard solved,DifficultyLevel df)
//    {
//        return removeCells(solved,df);
//    }
//}
