import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    private final JButton[][] buttons = new JButton[3][3];
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private boolean computerMode = false;

    public TicTacToe() {
        setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(3, 3, 4, 4));
        grid.setBackground(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = createButton(i, j);
                grid.add(buttons[i][j]);
            }
        }

        JPanel controls = new JPanel();
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(_ -> resetGame());
        controls.add(restartButton);

        JRadioButton computerModeButton = new JRadioButton("VS computer", computerMode);
        computerModeButton.addActionListener(_ -> {computerMode = computerModeButton.isSelected();resetGame();});
        controls.add(computerModeButton);

        add(grid, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }

    private JButton createButton(int i, int j) {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.PLAIN, 70));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.addActionListener(_ -> handleMove(i, j));
        return button;
    }

    private void handleMove(int i, int j) {
        if (gameOver || board[i][j] != '\0') return;

        board[i][j] = currentPlayer;
        buttons[i][j].setText(String.valueOf(currentPlayer));
        if (checkWinForPlayer(currentPlayer)) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "The player " + currentPlayer + " won   !");
        } else if (isBoardFull()) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "Draw!\nYou can try again!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            if (computerMode && currentPlayer == 'O') {
                makeComputerMove();
            }
        }
    }

    private void makeComputerMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    board[i][j] = 'O';
                    int score = miniMax(false);
                    board[i][j] = '\0';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        handleMove(bestMove[0], bestMove[1]);
    }

    private int miniMax(boolean isMaximizing) {
        if (checkWinForPlayer('O')) {return 1;}
        if (checkWinForPlayer('X')) {return -1;}
        if (isBoardFull()) {return 0;}

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    board[i][j] = isMaximizing ? 'O' : 'X';
                    int score = miniMax(!isMaximizing);
                    board[i][j] = '\0';

                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);

                }
            }
        }
        return bestScore;
    }

    private boolean checkWinForPlayer(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        return board[0][2] == player && board[1][1] == player && board[2][0] == player;
    }

    private boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\0') return false;
            }
        }
        return true;
    }

    private void resetGame() {
        gameOver = false;
        currentPlayer = 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
    }
}
