import java.util.List;

public interface Sequence {
    int getSeriesRandomDigit();

    boolean isMergePossible(int lastElement, int currentElement);

    int mergeElement(int currentElement, int lastElement);

    List<Integer> getSequence();
}
