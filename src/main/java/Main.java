import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        final Logger LOGGER = LogManager.getLogger(Main.class);
        final Marker PRINT_MARKER = MarkerManager.getMarker("PRINT");
        final Marker EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");

        File csvFile = new File("src/main/resources/movementList.csv");
        try {
            List<Operation> operationsList = FileParser.loadDataFromFile(csvFile);

            LOGGER.info(PRINT_MARKER, "\nСумма расходов :" +
                    Operation.getSum(operationsList, Operation::getCost) +
                    "\nСумма доходов :" +
                    Operation.getSum(operationsList, Operation::getIncome) +
                    "\nСуммы расходов по организациям:\n" +
                    String.join("\n",
                            Operation.getOperationsMap(operationsList, Operation::getCost)
                                    .toString()
                                    .replaceAll("[{}]", "")
                                    .split(",\\s")));
        } catch (Exception e) {
            LOGGER.info(EXCEPTION_MARKER, (Object) e);
        }
    }
}