package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadCSV {

    private static final int[] indexMap = {0, 1, 2, 3, 4}; // default map


    public static TransactionCollection parse(BufferedReader reader, int[] indexMap) throws IOException {
        String line;
        String delim = ",";

        TransactionCollection collection = new TransactionCollection(indexMap);
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(delim);
            collection.importSingleTransaction(data);
        }

        reader.close();
        return collection;
    }

    public static TransactionCollection parse(BufferedReader reader) throws IOException {
        String line;
        String delim = ",";
        TransactionCollection collection = new TransactionCollection(indexMap);
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(delim);
            collection.importSingleTransaction(data);
        }

        reader.close();
        return collection;
    }



}
