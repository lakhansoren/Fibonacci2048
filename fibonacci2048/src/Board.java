import java.util.ArrayList;
import java.util.Random;

public class Board {
    private static int board[][];
    private static Sequence sequence;
    public Board(int n, int m, Sequence fiboSeries) {
        board = new int[n][m];
        sequence = fiboSeries;
    }

    public void fillRandom() {
        int randomDigit = sequence.getSeriesRandomDigit();
        class Pair {
            int row , col;
        }
        ArrayList<Pair> zeroPositions = new ArrayList<>();
        for(int i = 0 ; i < board.length ; i++) {
            for(int j = 0 ; j < board[i].length ; j++) {
                if(this.board[i][j] == 0) {
                    Pair pair = new Pair();
                    pair.row = i;
                    pair.col = j;
                    zeroPositions.add(pair);
                }
            }
        }
        int randomPosition = Math.abs(new Random().nextInt()) % zeroPositions.size();
        this.board[zeroPositions.get(randomPosition).row][zeroPositions.get(randomPosition).col]
                = randomDigit;
    }

    public boolean makeMove(String gameInput) {
        boolean moveSuccessful = false;
        switch(gameInput) {
            case "left" :
                moveSuccessful = moveLeft();
                break;
            case "right" :
                moveSuccessful = moveRight();
                break;
            case "up" :
                moveSuccessful = moveUp();
                break;
            case "down":
                moveSuccessful = moveDown();
                break;
            default:
                moveSuccessful = false;
        }
        return moveSuccessful;
    }

    private boolean moveRight() {
        int duplicateBoard[][] = saveState();
        for(int i = 0 ; i < this.board.length ; i++) {
            ArrayList<Integer> boardMembers = new ArrayList<>();
            for(int j = this.board[i].length - 1 ; j >= 0 ; j--) {
                boardMembers.add(this.board[i][j]);
            }
            BoardStack boardStack = new BoardStack(sequence);
            ArrayList<Integer> newBoardMembersState = boardStack.getNewState(boardMembers);
            int index = this.board[i].length - 1;
            for(int member : newBoardMembersState) {
                this.board[i][index--] = member;
            }
            while(index >= 0) {
                this.board[i][index--] = 0;
            }
        }
        return boardChanged(duplicateBoard);
    }

    private boolean moveLeft() {
        int duplicateBoard[][] = saveState();
        for(int i = 0 ; i < this.board.length ; i++) {
            ArrayList<Integer> boardMembers = new ArrayList<>();
            for(int j = 0 ; j < this.board[i].length ; j++) {
                boardMembers.add(this.board[i][j]);
            }
            BoardStack boardStack = new BoardStack(sequence);
            ArrayList<Integer> newBoardMembersState = boardStack.getNewState(boardMembers);
            int index = 0;
            for(int member : newBoardMembersState) {
                this.board[i][index++] = member;
            }
            for(int j = index ; j < this.board[i].length ; j++) {
                this.board[i][j] = 0;
            }
        }
        return boardChanged(duplicateBoard);
    }

    private boolean boardChanged(int[][] duplicateBoard) {
        for(int i = 0 ; i < board.length ; i++) {
            for(int j = 0 ; j < board[i].length ; j++) {
                if(board[i][j] != duplicateBoard[i][j])
                    return true;
            }
        }
        return false;
    }

    private int[][] saveState() {
        int newBoard[][] = new int[this.board.length][this.board[0].length];
        for(int i = 0 ; i < this.board.length ; i++) {
            for(int j = 0 ; j < this.board[i].length ; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    private boolean moveUp() {
        int duplicateBoard[][] = saveState();
        for(int j = 0 ; j < this.board[0].length ; j++) {
            ArrayList<Integer> boardMembers = new ArrayList<>();
            for(int i = 0 ; i < this.board.length ; i++) {
                boardMembers.add(this.board[i][j]);
            }
            BoardStack boardStack = new BoardStack(sequence);
            ArrayList<Integer> newBoardMembersPosition = boardStack.getNewState(boardMembers);
            int index = 0;
            for(int member : newBoardMembersPosition) {
                this.board[index++][j] = member;
            }
            while(index < this.board.length) {
                this.board[index++][j] = 0;
            }
        }
        return boardChanged(duplicateBoard);
    }

    private boolean moveDown() {
        int duplicateBoard[][] = saveState();
        for(int j = 0 ; j < this.board[0].length ; j++) {
            ArrayList<Integer> boardMembers = new ArrayList<>();
            for(int i = this.board.length - 1 ; i >= 0 ; i--) {
                boardMembers.add(this.board[i][j]);
            }
            BoardStack boardStack = new BoardStack(sequence);
            ArrayList<Integer> newBoardMembersPosition = boardStack.getNewState(boardMembers);
            int index = this.board.length - 1;
            for(int member : newBoardMembersPosition) {
                this.board[index--][j] = member;
            }
            while(index >= 0) {
                this.board[index--][j] = 0;
            }
        }
        return boardChanged(duplicateBoard);
    }

    public boolean hasWon() {
        int winNum = sequence.getSequence().get(this.board.length * this.board[0].length - 1);
        for(int i = 0 ; i < this.board.length ; i++) {
            for(int j = 0 ; j < this.board[i].length ; j++) {
                if(this.board[i][j] == winNum)
                    return true;
            }
        }
        return false;
    }

    public boolean nextMovePossible() {
        for(int i = 0 ; i < this.board.length ; i++) {
            for(int j = 0 ; j < this.board[i].length ; j++) {
                if(this.board[i][j] == 0) {
                    return true;
                }
            }
        }
        return isLeftMovePossible() || isUpMovePossible();
    }

    private boolean isUpMovePossible() {
        for(int j = 0 ; j < this.board[0].length ; j++) {
            for(int i = 1 ; i < this.board.length ; i++) {
                if(sequence.isMergePossible(this.board[i][j] , this.board[i - 1][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLeftMovePossible() {
        for(int i = 0 ; i < this.board.length ; i++) {
            for(int j = 1 ; j < this.board[i].length ; j++) {
                if(sequence.isMergePossible(this.board[i][j] , this.board[i][j - 1])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void outputState() {
        for(int i = 0 ; i < this.board.length ; i++) {
            for(int j = 0 ; j < this.board[i].length ; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
