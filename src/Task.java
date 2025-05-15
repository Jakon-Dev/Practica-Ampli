import java.math.BigDecimal;
import java.math.RoundingMode;


public class Task {
    protected BigDecimal cost;

    protected Task(BigDecimal cost) {
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        if (this.cost.signum() <= 0)
            throw new IllegalArgumentException("cost must be positive");
    }

    public final BigDecimal costInEuros() {
        return this.cost;
    }
}



