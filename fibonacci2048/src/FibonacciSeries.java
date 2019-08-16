import java.util.ArrayList;
import java.util.List;

public class FibonacciSeries implements Sequence{
    private static ArrayList<Integer> fiboSeries = new ArrayList<>();
    private static int randomDigit = 1;
    public FibonacciSeries(int num) {
        fiboSeries.add(1);
        fiboSeries.add(1);
        for(int i = 3 ; i <= num ; i++)
            fiboSeries.add(fiboSeries.get(i - 3) + fiboSeries.get(i - 2));
    }

    @Override
    public int getSeriesRandomDigit() {
        return randomDigit;
    }

    @Override
    public boolean isMergePossible(int lastElement, int currentElement) {
        int newElement = lastElement + currentElement;
        for(int element : fiboSeries) {
            if(element == newElement)
                return true;
        }
        return false;
    }

    @Override
    public int mergeElement(int currentElement, int lastElement) {
        return currentElement + lastElement;
    }

    @Override
    public List<Integer> getSequence() {
        return fiboSeries;
    }
}
