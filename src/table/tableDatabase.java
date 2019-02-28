package table;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tableDatabase {
    // Variables
    private String fileName = "table.txt";
    private String databaseLoc = Paths.get(".").toAbsolutePath().normalize().toString()+"/res/"+fileName;
    private BufferedReader tableBR;

    // Constructor
    public tableDatabase() {
        init();
    }

    private void init() {
        try {
            this.tableBR = new BufferedReader(new FileReader(this.databaseLoc));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            try {
                if(new File(this.databaseLoc).createNewFile()) {
                    System.out.println("Table Database .... OK");
                    try {
                        this.tableBR = new BufferedReader(new FileReader(this.databaseLoc));
                    } catch (FileNotFoundException ex) {
                        System.out.println("failed to load database");
                        System.exit(1);
                    }
                }
            } catch (IOException e1) {
                //e1.printStackTrace();
                System.out.println("Table Database .... NOT OK");
                System.exit(1);
            }
            System.out.println("failed to load database");
            System.exit(1);
        }
    }

    public List<tableModel> readTable () {
        if (tableBR == null) {
            init();
        }
        List<tableModel> temp_table = new ArrayList<>();
        String temp_buffer;

        try {
            while ((temp_buffer = this.tableBR.readLine()) != null) {
                String[] dataBfrd = temp_buffer.split(",");
                //for (String s : dataBfrd) {
                //    System.out.println("this one : "+s);
                //}

                //for (String s : Arrays.asList(dataBfrd).subList(1, dataBfrd.length)) {
                //    System.out.println("something : "+s);
                //}

                temp_table.add(new tableModel(dataBfrd[0], Arrays.asList(dataBfrd).subList(1, dataBfrd.length)));
            }
            return temp_table;
        } catch (Exception exc) {
            System.out.println("Something wrong!!!");
            System.exit(1);
            return null;
        }
    }


    public static void main(String[] args) {
        //System.out.println("check1");
        //tableDatabase x = new tableDatabase();
        //List<tableModel> sumShit = x.readTable();
        //System.out.println("check1.5");
        //new tableModel().printTableAll(new tableDatabase().readTable());
        //new tableModel().printTable("customer",new tableDatabase().readTable());
        //System.out.println(sumShit.get(0).getTableName());
        //System.out.println("check2");
    }
}
