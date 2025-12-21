package View;

import exceptions.InvalidGame;
import model.Entry;
import model.SudokuBoard;
import storageManager.FileManager;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import model.Game;
import solver.SudokuSolver;
import undo.UndoLogEntry;
import undo.UndoLogManager;
import static undo.UndoLogManager.clear;

public class SudokuGUI {

    JFrame frame;
    private JTextField[][] cells;
    private boolean[][] isGiven;
    private SudokuBoard puzzle;
    private boolean undoing = false;
    // Folder for incomplete games
    private static final String INCOMPLETE_FOLDER = "incomplete";
    private SudokuView view;
    private Game currentGame;
    public SudokuGUI(SudokuView view,Game game,boolean isContinue) {
        this.view=view;
        this.currentGame=game;
        this.puzzle=game.getBoard();
        cells = new JTextField[9][9];
        isGiven = new boolean[9][9];
        if(isContinue)
        {
            isGiven=loadGivenForPreviousGame();
        }else
        {
            for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                isGiven[i][j]=puzzle.getGrid(i, j)!=0;
            }
        }
        }
        /*for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                isGiven[i][j]=puzzle.getGrid(i, j)!=0;
            }
        }*/
        /*puzzle = new SudokuBoard(new int[9][9]);*/
        /*File givenFile=new File(INCOMPLETE_FOLDER+"/givens.txt");
        if(!givenFile.exists())
        {
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    isGiven[i][j]=puzzle.getGrid(i, j)!=0;
                }
            }
            saveGiven();
        }
        else{
            loadGiven();
        }*/
        initializeGUI();
        updateGridUI();
        /*resetPuzzle();*/
}
    private boolean[][] loadGivenForPreviousGame()
    {
        boolean[][] given=new boolean[9][9];
        for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    given[i][j]=puzzle.getGrid(i, j)!=0;
                }
            }
        File logFile=new File("incomplete/undo.log");
        if(!logFile.exists())return given;
        try(BufferedReader r=new BufferedReader(new FileReader(logFile))){
            String line;
            while((line=r.readLine())!=null)
            {
                UndoLogEntry u=UndoLogEntry.fromLine(line);
                given[u.row][u.col]=false;
            }
        }catch(IOException e){}
        return given;
    }
    
   /* private void saveGiven()
    {
        try(BufferedWriter w=new BufferedWriter(new FileWriter(INCOMPLETE_FOLDER+"/givens.txt"))){
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++){
                    w.write(isGiven[i][j]?"1 ":"0 ");
                } 
                w.newLine();
            }
        }catch(IOException e)
        {
            System.err.println("failed to save givens");
        }
    }
    private void loadGiven()
    {
        File file=new File(INCOMPLETE_FOLDER+"/givens.txt");
        if(!file.exists())return;
        try(BufferedReader r=new BufferedReader(new FileReader(file)))
        {
             for(int i=0;i<9;i++)
            {   String[] part=r.readLine().trim().split("\\s+");
                for(int j=0;j<9;j++){
                    isGiven[i][j]=part[j].equals("1");
                }
            }
        }catch(IOException e)
        {
            System.err.println("failed to load givens");
        }
    }*/

    private void initializeGUI() {
        frame = new JFrame("Sudoku Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel gridPanel = createGridPanel();
        frame.add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                JPanel block = new JPanel(new GridLayout(3, 3, 5, 5));
                block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

                for (int subRow = 0; subRow < 3; subRow++) {
                    for (int subCol = 0; subCol < 3; subCol++) {
                        int row = blockRow * 3 + subRow;
                        int col = blockCol * 3 + subCol;

                        cells[row][col] = new JTextField();
                        cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                        cells[row][col].setFont(new Font("Arial", Font.BOLD, 28));

                        // Allow only single digit 1-9
                        cells[row][col].addKeyListener(new java.awt.event.KeyAdapter() {
                            public void keyTyped(java.awt.event.KeyEvent e) {
                                char c = e.getKeyChar();
                                if (!Character.isDigit(c) || c == '0') {
                                    e.consume();
                                } else if (!cells[row][col].getText().isEmpty()) {
                                    e.consume(); // Replace mode
                                }
                            }
                        });
                        cells[row][col].getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
   private void updatePuzzle() {
    if (isGiven[row][col] || undoing) return;

    String text = cells[row][col].getText().trim();
    int prev = puzzle.getGrid(row, col);

    if (text.isEmpty()) {
        puzzle.setDigit(row, col, 0);
    } else {
        try {
            int val = Integer.parseInt(text);
            puzzle.setDigit(row, col, val);
        } catch (NumberFormatException ex) {
            puzzle.setDigit(row, col, 0); // safety
        }
    }

    // ==== LOGGING ====
    // Only log if value changed
    try {
        UndoLogManager.logMove("incomplete",
                new UndoLogEntry(row, col,
                        puzzle.getGrid(row, col), prev));
    } catch (IOException ex) {
        System.err.println("Undo log failed");
    }

    saveGameFile();
}


                                                                              public void insertUpdate(javax.swing.event.DocumentEvent e) {
                                                                                  updatePuzzle();
                                                                              }

                                                                              public void removeUpdate(javax.swing.event.DocumentEvent e) {
                                                                                  updatePuzzle();
                                                                              }

                                                                              public void changedUpdate(javax.swing.event.DocumentEvent e) {

                                                                              }
                                                                          });

                        block.add(cells[row][col]);
                    }
                }
                gridPanel.add(block);
            }
        }
        return gridPanel;
    }


    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        /*JButton loadBtn = new JButton("Load from CSV");
        loadBtn.addActionListener(e -> loadPuzzleFromFile());*/

        JButton undoBtn = new JButton("Undo");
        undoBtn.addActionListener(e -> undo());

        JButton solveBtn = new JButton("Solve");
        solveBtn.addActionListener(e -> solve());
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> exit());

        JButton checkBtn = new JButton("Verify");
        checkBtn.addActionListener(e -> verify());

        JButton clearBtn = new JButton("Clear User Entries");
        clearBtn.addActionListener(e -> clearUserEntries());

        panel.add(undoBtn);
        /*panel.add(loadBtn);*/
        panel.add(exitBtn);
        panel.add(solveBtn);
        panel.add(checkBtn);
        panel.add(clearBtn);

        return panel;
    }

    private void resetPuzzle() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(puzzle.getArray()[i], 0);
            Arrays.fill(isGiven[i], false);
        }
        if (cells != null) { // Safe if called after cells initialized
            updateGridUI();
        }
    }
private void updateGridUI() {
    undoing = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = cells[i][j];
                if (isGiven[i][j]) {
                    cell.setText(String.valueOf(puzzle.getGrid(i,j)));
                    cell.setEditable(false);
                    cell.setBackground(new Color(230, 240, 255));
                    cell.setForeground(Color.BLACK);
                } else {
                    //cell.setText("");
                 int value = puzzle.getGrid(i, j); // read from board
                cell.setText(value == 0 ? "" : String.valueOf(value));
                    cell.setEditable(true);
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLUE);
                }
            }
        }
            undoing = false;
    }
   /*  private void loadPuzzleFromFile() {
       JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();

        try {
            // READ CSV MANUALLY 
            int[][] board = new int[9][9];
            BufferedReader br = new BufferedReader(new FileReader(file));

            for (int i = 0; i < 9; i++) {
                String[] values = br.readLine().split(",");
                for (int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(values[j].trim());
                }
            }
            br.close();

            // CREATE BOARD 
            puzzle = new SudokuBoard(board);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    isGiven[i][j] = (puzzle.getGrid(i, j) != 0);
                }
            }

            // ====== RESET INCOMPLETE FOLDER 
            UndoLogManager.clear("incomplete");      // clear undo.log
            FileManager.saveBoard("incomplete", puzzle); // save game.txt

            updateGridUI();

            JOptionPane.showMessageDialog(frame,
                    "Puzzle loaded successfully from CSV:\n" + file.getName());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Failed to load puzzle:\n" + ex.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}*/
        
     
    /////////////////////////////////////
    public void undo(){

         /*  try {
    UndoLogEntry e = UndoLogManager.popLast("incomplete");
    if (e != null) {
        undoing = true;
        puzzle.setDigit(e.row, e.col, e.prev);
        cells[e.row][e.col].setText(
            e.prev == 0 ? "" : String.valueOf(e.prev)
        );
        undoing = false;
    }
} catch (IOException ex) {
    System.err.println("Undo failed");
}*/
    try{
        undoing = true;
        view.undo(currentGame);
        updateGridUI();
        undoing = false;
    }catch(IOException e){
        System.err.println("Undo failed");
    }

 }
///////// SAVE BOARD TO GAME FILE 
    private void saveGameFile() {
        try {
            FileManager.saveBoard(INCOMPLETE_FOLDER, puzzle); 
        } catch (IOException ex) {
            System.err.println("Error saving game file");
        }
    }
  
   private void solve() {
    /*SudokuSolver solver = new SudokuSolver(puzzle);
    boolean solved = solver.solve();
    if (solved) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int value = puzzle.getGrid(r, c);
                cells[r][c].setText(String.valueOf(value));
                cells[r][c].setForeground(Color.RED); // solved numbers in red
            }
        }
        JOptionPane.showMessageDialog(frame, "Puzzle solved!");
    } else {
        JOptionPane.showMessageDialog(frame, "No solution exists!");
    }*/
    int emptyCells =puzzle.getEmptyCells().size();
    if(emptyCells!=5)
    {
       JOptionPane.showMessageDialog(frame,"Solve only works when exactly 5 cells left");
       return;
    }
    try{
        int[] solve=view.solveGame(currentGame);
        int index=0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                int value=solve[index++];
                puzzle.setDigit(i, j, value);
                cells[i][j].setText(String.valueOf(value));
                cells[i][j].setForeground(Color.red);
            }
        }
        JOptionPane.showMessageDialog(frame,"Puzzle solved!");
        UndoLogManager.clear("incomplete");
        FileManager.delete("incomplete");
        
    }catch(InvalidGame e)
    {
        JOptionPane.showMessageDialog(frame,"No valid solution exit for this board.");
    }
}

    private void verify() {
        if(puzzle.getEmptyCells().size()>0)
        {
            JOptionPane.showMessageDialog(frame,"Incomplete board.Please fill all cells first.");
            return;
        }
        String verify=view.verifyGame(currentGame);
        if("VALID".equals(verify))
        {
            JOptionPane.showMessageDialog(frame,"Valid solution.");
        }else{
           JOptionPane.showMessageDialog(frame,"Invalid solution."); 
        }
        //UndoLogManager.clear("incomplete");
        //FileManager.delete("incomplete");
    }

    private int[] getColumn(int[][] grid, int col) {
        int[] column = new int[9];
        for (int i = 0; i < 9; i++) column[i] = grid[i][col];
        return column;
    }

    private int[] getBox(int[][] grid, int boxIndex) {
        int[] box = new int[9];
        int r = (boxIndex / 3) * 3, c = (boxIndex % 3) * 3;
        int idx = 0;
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) box[idx++] = grid[r + i][c + j];
        return box;
    }

    private void clearUserEntries() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!isGiven[i][j]) {
                    cells[i][j].setText("");
                    cells[i][j].setForeground(Color.BLUE);
                }
            }
        }
        saveGameFile(); 
        clear("incomplete");
    }

    private int[][] readGrid() {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText().trim();
                grid[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return grid;
    }
    
    public static void main(String[] args) {
       /* SwingUtilities.invokeLater(SudokuGUI::new);*/
    }

    private void exit() {
        if(puzzle.getEmptyCells().size()>0)
        {
            frame.dispose();
            System.exit(0);
            return;
        }
        String verify=view.verifyGame(currentGame);
        if("VALID".equals(verify))
        {
            JOptionPane.showMessageDialog(frame,"Congrats!!! Game completed and solution is valid.");
            UndoLogManager.clear("incomplete");
            FileManager.delete("incomplete");
        }else{
           JOptionPane.showMessageDialog(frame,"Game completed but solution is invalid."); 
        }
        
       //call first frame
       SwingUtilities.invokeLater(()->{
           new SudokuMainPage().setVisible(true);
       });
       frame.dispose();
    }
}
