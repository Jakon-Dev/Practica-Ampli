import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Task2 {
    protected BigDecimal cost;

    protected Task2(BigDecimal cost) {
        setCost(cost);
    }

    public final BigDecimal costInEuros() {
        return cost;
    }

    protected void setCost(BigDecimal newCost) {
        newCost = newCost.setScale(2, RoundingMode.HALF_UP);
        if (newCost.signum() <= 0)
            throw new IllegalArgumentException("cost must be positive");
        this.cost = newCost;
    }
}
