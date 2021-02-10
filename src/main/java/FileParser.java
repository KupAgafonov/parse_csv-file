import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** this is ".csv file parser" */

public class FileParser {

    private static final int INDEX_ACCOUNT_TYPE = 0;
    private static final int INDEX_ACCOUNT_NUMBER = 1;
    private static final int INDEX_CURRENCY = 2;
    private static final int INDEX_OPERATION_DATE = 3;
    private static final int INDEX_REFERENCE = 4;
    private static final int INDEX_OPERATION_DESCRIPTION = 5;
    private static final int INDEX_INCOME = 6;
    private static final int INDEX_COST = 7;

    public static List<Operation> loadDataFromFile(File file) {
        List<Operation> operations = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
            boolean firstLine = true;
            for (String line : lines) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] fragments = line.split(",", 8);
                if (fragments.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                operations.add(new Operation(
                        fragments[INDEX_ACCOUNT_TYPE],
                        fragments[INDEX_ACCOUNT_NUMBER],
                        fragments[INDEX_CURRENCY],
                        LocalDate.parse(fragments[INDEX_OPERATION_DATE], DateTimeFormatter.ofPattern("dd.MM.yy")),
                        fragments[INDEX_REFERENCE],
                        fragments[INDEX_OPERATION_DESCRIPTION],
                        Double.parseDouble(fragments[INDEX_INCOME]
                                .replaceAll("\"", "")
                                .replaceAll(",", ".")),
                        Double.parseDouble(fragments[INDEX_COST]
                                .replaceAll("\"", "")
                                .replaceAll(",", "."))));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return operations;
    }
}

