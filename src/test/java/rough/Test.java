package rough;

import base.TestBase;

import java.util.Hashtable;

public class Test extends TestBase {
    public static void main(String[] args) {
        int rows = excel.getRowCount("addCustomerTest");
        int cols = excel.getColumnCount("addCustomerTest");

        Object[][] data = new Object[rows - 1][1];

        Hashtable<String,String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2
            table = new Hashtable<>();
            for (int colNum = 0; colNum < cols; colNum++) {
                // data[0][0]
                table.put(excel.getCellData("addCustomerTest", colNum, 1), excel.getCellData("addCustomerTest", colNum, rowNum));
                data[rowNum - 2][0] = table;
            }
        }

        System.out.println(rows + " - " + cols);
    }
}
