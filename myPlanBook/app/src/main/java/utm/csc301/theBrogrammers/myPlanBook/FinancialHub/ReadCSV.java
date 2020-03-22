package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCSV {

    private static final int[] indexMap = {0, 1, 2, 3, 4}; // default map



    public static TransactionCollection parse(BufferedReader reader) throws Exception {
        String line;
        String delim = ",";
        TransactionCollection collection = new TransactionCollection(indexMap);
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(delim);
            // if debit column is null

            data = parseData(data);

            collection.importSingleFileTransaction(data);
        }

        reader.close();
        return collection;
    }

    private static String[] parseData(String[] data) throws Exception{

        String[] list = new String[5];
        list[3] = data[data.length-1];
        list[0] = data[0];
        if (data[data.length-2].equals("")) {
            list[2] = data[data.length - 3];
            list[4] = "true";
        }
        else {
            list[2] = data[data.length-2];
            list[4] = "false";
        }

        Log.i("[Transaction]", list[0]+ ", "+
                list[1] + ", " + list[2] + ", " +
        list[3] + ", " + list[4]);

        int index = 1; String inst = "";
        while (index < data.length-3){
            inst+= data[index];
            index++;
        } list[1] = inst;
        return list;
    }

    public static void print(String[] data){
        int i = 0;
        Log.i("len" + Integer.toString(data.length),  "--");
        for(String s: data){
            Log.i("" + Integer.toString(i),  s);
            i++;
        }

    }



}
