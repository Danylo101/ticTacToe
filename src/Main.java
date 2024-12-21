import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();

        ticTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticTacToe.setTitle("Tic Tac Toe");
        ticTacToe.setSize(400, 400);

        ticTacToe.setVisible(true);
    }
}
