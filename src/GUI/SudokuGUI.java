package GUI;

import model.SudokuBoard;
import storageManager.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class SudokuGUI {

    private JFrame frame;
    private JTextField[][] cells;
    private boolean[][] isGiven;
    private int[][] puzzle;

    public SudokuGUI() {
        cells = new JTextField[9][9];
        isGiven = new boolean[9][9];
        puzzle = new int[9][9];
        initializeGUI();
        resetPuzzle();
    }

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
                                } else if (cells[row][col].getText().length() >= 1) {
                                    e.consume(); // Replace mode
                                }
                            }
                        });
                        cells[row][col].getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                                                                              private void updatePuzzle() {
                                                                                  String text = cells[row][col].getText().trim();
                                                                                  if (text.isEmpty()) {
                                                                                      puzzle[row][col] = 0;
                                                                                  } else {
                                                                                      try {
                                                                                          puzzle[row][col] = Integer.parseInt(text);
                                                                                      } catch (NumberFormatException ex) {
                                                                                          puzzle[row][col] = 0; // safety
                                                                                      }
                                                                                  }
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

        JButton loadBtn = new JButton("Load from CSV");
        loadBtn.addActionListener(e -> loadPuzzleFromFile());

        JButton saveBtn = new JButton("Save to CSV");
        saveBtn.addActionListener(e -> savePuzzleToFile());

        JButton solveBtn = new JButton("Solve");
        solveBtn.addActionListener(e -> solveSudoku());

        JButton checkBtn = new JButton("Check Solution");
        checkBtn.addActionListener(e -> checkSolution());

        JButton clearBtn = new JButton("Clear User Entries");
        clearBtn.addActionListener(e -> clearUserEntries());

        panel.add(loadBtn);
        panel.add(saveBtn);
        panel.add(solveBtn);
        panel.add(checkBtn);
        panel.add(clearBtn);

        return panel;
    }

    private void resetPuzzle() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(puzzle[i], 0);
            Arrays.fill(isGiven[i], false);
        }
        if (cells != null) { // Safe if called after cells initialized
            updateGridUI();
        }
    }

    private void updateGridUI() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = cells[i][j];
                if (isGiven[i][j]) {
                    cell.setText(String.valueOf(puzzle[i][j]));
                    cell.setEditable(false);
                    cell.setBackground(new Color(230, 240, 255));
                    cell.setForeground(Color.BLACK);
                } else {
                    cell.setText("");
                    cell.setEditable(true);
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLUE);
                }
            }
        }
    }

    // === File Operations (delegated to FileManager) ===

    private void loadPuzzleFromFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                puzzle = FileManager.loadBoard(file).getArray();  // This returns int[9][9]
                // Mark given cells
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        isGiven[i][j] = (puzzle[i][j] != 0);
                    }
                }
                updateGridUI();
                JOptionPane.showMessageDialog(frame, "Puzzle loaded successfully from:\n" + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Failed to load puzzle:\n" + ex.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // For debugging in console
            }
        }
    }

    private void savePuzzleToFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }
            try {
                FileManager.saveBoard(file, new SudokuBoard(readGrid()) );
                JOptionPane.showMessageDialog(frame, "Puzzle saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving file:\n" + ex.getMessage(),
                        "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === Solver, Checker, etc. (unchanged logic) ===

    private void solveSudoku() {
        int[][] grid = readGrid();
        if (solveHelper(grid)) {
            writeGrid(grid, Color.RED);
            JOptionPane.showMessageDialog(frame, "Sudoku solved!");
        } else {
            JOptionPane.showMessageDialog(frame, "No solution exists.");
        }
    }

    private boolean solveHelper(int[][] grid) {
        // Standard backtracking implementation (same as before)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solveHelper(grid)) return true;
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < 9; x++) if (grid[row][x] == num || grid[x][col] == num) return false;
        int br = row / 3 * 3, bc = col / 3 * 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[br + i][bc + j] == num) return false;
        return true;
    }

    private void checkSolution() {
        int[][] grid = readGrid();
        if (!isComplete(grid)) {
            JOptionPane.showMessageDialog(frame, "Board is not fully filled.");
            return;
        }
//        if (isValidSolution(grid)) {
//            JOptionPane.showMessageDialog(frame, "Correct solution! 🎉");
//        }
        else {
            JOptionPane.showMessageDialog(frame, "There are errors.");
        }
    }

    private boolean isComplete(int[][] grid) {
        for (int[] row : grid) for (int cell : row) if (cell == 0) return false;
        return true;
    }

//    private boolean isValidSolution(int[][] grid) {
//        // Row, column, and box checks (same as before)
//        for (int i = 0; i < 9; i++) {
//            if (!isUnique(grid[i]) || !isUnique(getColumn(grid, i)) || !isUnique(getBox(grid, i))) {
//                return false;
//            }
//        }
//        return true;
//    }

//    private boolean[] isUnique(int[] arr) {
//        boolean[] seen = new boolean[10];
//        for (int n : arr) {
//            if (n != 0 && seen[n]) return false;
//            seen[n] = true;
//        }
//        return seen;
//    }

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
    private void autoSave() {
        puzzle = readGrid();
        try {
            File autoSaveFile = new File("autosave.csv");
            FileManager.saveBoard(autoSaveFile, new SudokuBoard(puzzle));
            // Optional: show subtle feedback
             System.out.println("Auto-saved at " + java.time.LocalDateTime.now());
        } catch (IOException ex) {
            // Fail silently or show a one-time warning
            System.err.println("Auto-save failed: " + ex.getMessage());
        }
    }

    private void writeGrid(int[][] grid, Color color) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!isGiven[i][j]) {
                    cells[i][j].setText(String.valueOf(grid[i][j]));
                    cells[i][j].setForeground(color);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}