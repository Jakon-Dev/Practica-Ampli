import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimpleTask2 extends Task {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public SimpleTask2(BigDecimal cost) {
        super(cost);
    }

    public void changeCost(BigDecimal newCost) {
        newCost = newCost.setScale(2, RoundingMode.HALF_UP);
        if (newCost.signum() <= 0)
            throw new IllegalArgumentException("cost must be positive");

        if (!this.cost.equals(newCost)) {
            BigDecimal oldCost = this.cost;
            this.cost = newCost;
            pcs.firePropertyChange("cost", oldCost, newCost);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
}
