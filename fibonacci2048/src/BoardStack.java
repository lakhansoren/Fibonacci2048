import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class BoardStack {
    private Stack<Integer> stack;
    private boolean isMerged;
    private Sequence sequence;

    public BoardStack(Sequence sequence) {
        this.stack = new Stack<>();
        isMerged = false;
        this.sequence = sequence;
    }

    public ArrayList<Integer> getNewState(ArrayList<Integer> boardMembers) {
        ArrayList<Integer> boardMembersNewState = new ArrayList<>();
        for(int currentElement : boardMembers) {
            if(currentElement == 0) {
                continue;
            }
            if(stack.size() == 0) {
                stack.push(currentElement);
                continue;
            }
            if(isMerged) {
                stack.push(currentElement);
                isMerged = false;
                continue;
            }
            int lastElement = stack.peek();
            if(sequence.isMergePossible(lastElement, currentElement)) {
                int newElement = sequence.mergeElement(currentElement, lastElement);
                stack.pop();
                stack.push(newElement);
                isMerged = true;
            }
        }
        while(stack.size() != 0) {
            boardMembersNewState.add(stack.pop());
        }
        Collections.reverse(boardMembersNewState);
        return boardMembersNewState;
    }
}
