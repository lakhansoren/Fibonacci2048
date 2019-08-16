import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FibonacciGame {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String args[]) throws IOException {
        int n , m;
        n = readInteger();
        m = readInteger();
        Sequence fiboSeries = new FibonacciSeries(n * m);
        Board board = new Board(n , m , fiboSeries);
        board.fillRandom();
        board.fillRandom();
        board.outputState();
        while(true) {
            if(!board.nextMovePossible()) {
                System.out.println("You Lost :<");
            }
            String gameInput = readString();
            boolean moveWasSuccesfullyMade = board.makeMove(gameInput);
            if(moveWasSuccesfullyMade) {
                board.fillRandom();
                board.outputState();
                if(board.hasWon()) {
                    System.out.println("You Win :)");
                    break;
                }
            } else {
                System.out.println("Move not possible. Choose some other move");
            }
        }
    }

    private static String readString() throws IOException {
        return br.readLine();
    }

    private static int readInteger() throws IOException {
        return Integer.parseInt(br.readLine());
    }
}
