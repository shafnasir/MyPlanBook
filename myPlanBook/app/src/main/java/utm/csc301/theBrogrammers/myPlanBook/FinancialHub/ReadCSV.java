package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadCSV {



    public static void parse(BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

        }

        reader.close();
    }



}
