import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class Operation {

    private final String accountType;
    private final String accountNumber;
    private final String currency;
    private final LocalDate operationDate;
    private final String reference;
    private final String operationDescription;
    private final Double income;
    private final Double cost;

    public static double getSum(List<Operation> operations, Function<Operation, Double> function) {
        return operations.stream()
                .map(function)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static TreeMap<String, Double> getOperationsMap(List<Operation> operations,
                                                           Function<Operation, Double> function) {
        return operations
                .stream()
                .collect(Collectors
                        .toMap(Operation::getReadableDescription,
                                function,
                                Double::sum,
                                TreeMap::new));
    }

    public String getReadableDescription() {
        return operationDescription
                .replaceAll("^(.+/|^.+\\\\)(.+\\s)(\\s+\\d{2}.\\d{2}.\\d{2}\\s+\\d{2}.\\d{2}.\\d{2}+.+)",
                        "$2")
                .trim();
    }
}
