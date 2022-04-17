import java.util.*;
import java.util.regex.*;
import java.security.*;
import java.lang.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
/*import java.io.InputStream;
import java.io.File;
import org.apache.poi.ss.usermodel.FormulaEvaluator; 
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;   
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import org.apache.poi.xssf.usermodel.XSSFSheet; */
<dependency><groupId>org.apache.poi</groupId><artifactId>poi-ooxml</artifactId><version>3.11</version></dependency>
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class HotelProject {
    private void centry() throws IOException {
        Scanner in = new Scanner(System.in);
        String name = "";
        System.out.print("Press '1' if customer enters \nElse Press '0': ");
        while (true) {
            int cust = in.nextInt();
            if (cust == 1)
                break;
            else if (cust == 0) {
                System.out.print('\f');
                System.out.println("End...");
                System.exit(0);
            } else {
                System.out.println("Please choose the correct option i.e; '1 or 0'\nInput again:");
                continue;
            }
        }
        System.out.println();
        System.out.print("Enter your name: ");
        name = in.nextLine();
        name += in.nextLine();
        String phno = "";
        System.out.print("Enter your Contact Number: ");
        while (true) {
            phno = in.next();
            Pattern r = Pattern.compile("[0-9]");
            Matcher m = r.matcher(phno);
            boolean b = m.matches();
            if (phno.length() == 10 && !b) {
                break;
            }
            if (b) {
                System.out.println("Input should be in numbers");
            }
            System.out.println("Please enter 10 digit contact number:");
        }
        System.out.println();
        System.out.print('\f');
        HotelProject a = new HotelProject();
        a.order(name, phno);
    }

    private void order(String name, String phno) throws IOException {
        Scanner in = new Scanner(System.in);
        int[] item = new int[5];
        System.out.println(
                "Enter the .xlsx file location, in the format\ndrive:\\folder(s)or(file)\n Please note do not add spaces\nExample: C:\\\\Menu\\\\myhotel.xlsx");
        String p = in.nextLine();
        FileInputStream fis = new FileInputStream(p);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet s = wb.getSheetAt(0);
        int rows = s.getLastRowNum();
        int cols = 3;
        String[] opt = new String[rows];
        double[][] cost = new double[2][rows];
        try {
            for (int r = 1; r <= rows; r++) {
                XSSFRow row = s.getRow(r);
                for (int c = 0; c <= cols; c++) {
                    XSSFCell cell = row.getCell(c);
                    if (c == 0)
                        cost[0][r - 1] = cell.getNumericCellValue();
                    else if (c == 1)
                        opt[r - 1] = cell.getStringCellValue();
                    else if (c == 2)
                        cost[1][r - 1] = cell.getNumericCellValue();
                }
            }
        } catch (Error e) {
            e.printStackTrace();
        }
        System.out.println("Hello " + name + " hope you are fine\n\n");
        System.out.println(
                "Please place your order \nPlease note you can order only 5 items at a time \nPress 0 to stop choosing \n Thank You");
        for (int i = 0; i < rows; i++)
            System.out.println((int) cost[0][i] + ". " + opt[i] + "-> " + cost[1][i] + "/-");
        System.out.println("\n\nEnter the item: ");
        for (int i = 0; i < 5; i++) {
            item[i] = in.nextInt();
            if (item[i] == 0)
                break;
        }
        System.out.print('\f');
        System.out.println("Items that you have listed are:");
        System.out.println("**************************");
        for (int i = 0; i < 5; i++) {
            if (item[i] == 0 || item[i] > rows)
                continue;
            System.out.println("  " + opt[(item[i] - 1)] + " which costs Rs." + cost[1][(item[i] - 1)]); // product
                                                                                                         // value is
                                                                                                         // used; number
                                                                                                         // of *items*
                                                                                                         // listed by
                                                                                                         // user
        }
        System.out.println("**************************");
        System.out.println(
                "Please Confirm your order\n\nPress '1' to PAY and PURCHASE\nPress '0' to EXIT or CANCEL your order");
        while (true) {
            int confirm = in.nextInt();
            if (confirm == 1) {
                HotelProject a = new HotelProject();
                a.bill(name, opt, cost, item, phno, rows);
            } else if (confirm == 0) {
                System.out.print('\f');
                System.out.println("Your order is CANCELLED\n\n\nTHANK YOU " + name + "\n\nPlease visit again");
                System.exit(0);
            } else {
                System.out.println("Please choose the correct option i.e; '1 or 0'\nInput again:");
                continue;
            }
        }
    }

    private void bill(String name, String[] opt, double[][] cost, int[] item, String phno, int rows)
            throws IOException {
        System.out.print('\f');
        float total = 0;
        System.out.println("**************************");
        System.out.println("Your bill:");
        for (int i = 0; i < 5; i++) {
            if (item[i] == 0 || item[i] > rows)
                continue;
            System.out.println("  " + opt[(item[i] - 1)] + " which costs Rs." + cost[1][(item[i] - 1)]);
            total = (float) (total + cost[1][(item[i] - 1)]);
        }
        System.out.println("**************************");
        System.out.println("Total Amount To be Paid is :Rs. " + total);
        System.out.println(
                "\n\nPlease wait\n You will be called to Contact Number " + phno + " after your order is ready");
        System.out.println("\n\n\nTHANK YOU " + name + "\n\nPlease visit again");
        System.exit(0);
    }

    public static void main(String args[]) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print('\f');
        String name = "";
        HotelProject a = new HotelProject();
        a.centry();
    }
}