import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SimpleTask extends Task {
    private final List<CostChangeListener> observers = new ArrayList<>();

    public SimpleTask(BigDecimal cost) {
        super(cost);
    }

    public void changeCost(BigDecimal newCost) {
        newCost = newCost.setScale(2, RoundingMode.HALF_UP);
        if (newCost.signum() <= 0)
            throw new IllegalArgumentException("cost must be positive");

        if (!this.cost.equals(newCost)) {
            BigDecimal oldCost = this.cost;
            this.cost = newCost;
            notifyObservers(new CostChanged(oldCost, newCost));
        }
    }

    public void addObserver(CostChangeListener listener) {
        observers.add(listener);
    }

    private void notifyObservers(CostChanged event) {
        for (CostChangeListener listener : observers) {
            listener.costChanged(this, event);
        }
    }
}
