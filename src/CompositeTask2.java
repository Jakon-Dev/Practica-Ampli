import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CompositeTask2 extends Task implements PropertyChangeListener {
    private final List<Task> subtasks;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CompositeTask2(List<Task> subtasks) {
        super(BigDecimal.ZERO);
        this.subtasks = List.copyOf(subtasks);
        for (Task t : subtasks) {
            if (t instanceof SimpleTask2 s)
                s.addPropertyChangeListener(this);
            else if (t instanceof CompositeTask2 c)
                c.addPropertyChangeListener(this);
        }
        recalculateCost();
    }

    private void recalculateCost() {
        BigDecimal newCost = subtasks.stream()
                .map(Task::costInEuros)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        if (!this.cost.equals(newCost)) {
            BigDecimal old = this.cost;
            this.cost = newCost;
            pcs.firePropertyChange("cost", old, newCost);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("cost".equals(evt.getPropertyName()))
            recalculateCost();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
}