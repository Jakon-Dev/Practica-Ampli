import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CompositeTask extends Task implements CostChangeListener {
    private final List<Task> subtasks;

    public CompositeTask(List<Task> subtasks) {
        super(BigDecimal.ZERO); // S'actualitza desprÃ©s
        this.subtasks = List.copyOf(subtasks);
        for (Task task : subtasks) {
            if (task instanceof SimpleTask st)
                st.addObserver(this);
            else if (task instanceof CompositeTask ct)
                ct.addObserver(this);
        }
        recalculateCost();
    }

    private void recalculateCost() {
        BigDecimal newCost = subtasks.stream()
                .map(Task::costInEuros)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        if (!this.cost.equals(newCost)) {
            this.cost = newCost;
            notifyObservers(new CostChanged(null, newCost));
        }
    }

    @Override
    public void costChanged(SimpleTask source, CostChanged event) {
        recalculateCost();
    }

    private final List<CostChangeListener> observers = new ArrayList<>();

    public void addObserver(CostChangeListener listener) {
        observers.add(listener);
    }

    private void notifyObservers(CostChanged event) {
        for (CostChangeListener listener : observers) {
            listener.costChanged(null, event);
        }
    }
}
