package State;

import table.tableDatabase;
import table.tableModel;

import java.util.ArrayList;
import java.util.List;

public class executingCommandState {



    // method & function
    public void execCommand(String command) {
        System.out.println("Output : ");
        // check is command empty
        if (command.isEmpty()) {
            System.out.println("SQL command is empty");
            return ;
        }
        // clean "space_char" from the back of command
        while (command.charAt(command.length()-1) == ' ') {
            command = removeXLastChar(command,' ');
        }
        // clean "space_char" from the front of command
        while (command.charAt(0) == ' ') {
            command = removeXFirstChar(command,' ');
        }



        // check if last char === ;
        if (command.charAt(command.length()-1) == ';') {
            command = removeXLastChar(command,';');
            String[] parseCommand = command.split(" ");
            // if command == "select"
            if (parseCommand[0].equalsIgnoreCase("select")) {
                // SELECT m.nim,nama,kode,nilai FROM mahasiswa m JOIN registrasi r ON (m.nim=r.nim);
                boolean isJoinExist = false;
                for (String s : parseCommand) {
                    if (s.equalsIgnoreCase("join")) {
                        isJoinExist = true;
                    }
                }
                // normal select
                if (!isJoinExist) {
                    // SELECT *;
                    // SELECT _tname_;
                    //int i = 0;
                    if (parseCommand[parseCommand.length -2].equalsIgnoreCase("from")) {
                      //  System.out.println(i);
                        //i++;
                        execSelectCommandFromTable(parseCommand[1],parseCommand[3]);
                    }// else {
                     //   execSelectCommand(parseCommand[1]);
                    //}

                } else {
                        int idxFrom = 0, idxJoin = 0, idxOn = 0;
                        for (int i = 0; i < parseCommand.length; i++) {
                            if (parseCommand[i].equalsIgnoreCase("from")) {
                                idxFrom = i;
                            }
                            if (parseCommand[i].equalsIgnoreCase("join")) {
                                idxJoin = i;
                            }
                            if (parseCommand[i].equalsIgnoreCase("on")) {
                                idxOn = i;
                            }
                        }
                        if (idxFrom != 0 && idxJoin != 0 && idxFrom != 0) {
                            execJoinCommand(parseCommand[1],parseCommand[idxFrom+1],parseCommand[idxJoin+1],parseCommand[idxOn+1]);
                        } else {
                            System.out.println("SQL error");
                        }
                    }
            } else if (parseCommand[0].equalsIgnoreCase("exit")){
                // if it exit
                System.out.println("exit ....");
                System.exit(1);
            } else {
                System.out.println("SQL command not found");
            }
        } else {
            System.out.println("SQL invalid , reason ';' is missing");
        }
    }


    private void execSelectCommandFromTable(String colName, String tableName) {
        //System.out.println(colName+"---+"+colName.equalsIgnoreCase("*"));
        if (colName.equalsIgnoreCase("*")) {
          //  System.out.println("using normal print");
            execSelectCommand(tableName);
        } else {
            String[] parsedColName = colName.split(",");
            //System.out.println("this is parsed colname");
            //for (String s : parsedColName) {
            //    System.out.println(s);
            //}
            //System.out.println("this is end of parsed colname");
            execSelectColumnCommand(parsedColName,tableName);
        }
        //System.out.println("execSelectCommandFromTable is finished");
    }

    private void execSelectColumnCommand(String[] parsedColName, String tableName) {
        new tableModel().printTableWithColumn(parsedColName,tableName,new tableDatabase().readTable());
       // List<tableModel> tableList = new tableDatabase().readTable();
    }

    private void execJoinCommand(String colNameReq, String tableFrom, String tableJoin, String field_to_join) {
        // will start to clean colNameReq
        String[] parsedColNameReq = colNameReq.split(",");
        // init col list
        List<String> colInTableFrom = new ArrayList<>(),colInTableJoin = new ArrayList<>();
        List<String> cleanCol = new ArrayList<>();
//        System.out.println("customer".split(".")[0]);
        // this one is to clean colNameReq
        for (String col : parsedColNameReq) {
            // clean col from _tname.colname_
            if (col.contains(".")) {
                int pointIdx = -1;
                for (int i = 0; i < col.length(); i++){
                    if (col.charAt(i) == '.') {
                        pointIdx = i;
                    }
                }
                cleanCol.add(removeCharAfter(col,pointIdx));
            } else {
                cleanCol.add(col);
            }
            // col should be clean now
            //---------------------
               // for (String su : cleanCol) {
                //    System.out.println("inCLean col : " + su);
               // }
            //--------------------
        }

        for (String colName : cleanCol) {
            // adding to valid col name from actual table
            //System.out.println("this one is will added to something : "+colName);
            if (new tableModel().isColInColList(colName,new tableDatabase().getTableModel(tableFrom))) {
            //    System.out.println("this one is will added to colFrom : "+colName);
                colInTableFrom.add(colName);
            }
            if (new tableModel().isColInColList(colName,new tableDatabase().getTableModel(tableJoin))) {
          //      System.out.println("this one is will added to colJoin : "+colName);
                colInTableJoin.add(colName);
            }
        }
        //----- will print cleanCol
        //System.out.println(cleanCol.size());
        //for (String x : cleanCol) {
        //    System.out.println("inCLean col : " + x);
        //}
        //------------------------

        /*
        * Output :
        * Tabel (1) : Mahasiswa
        * List Kolom : nim, nama
        * Tabel (2) : registrasi
        * List Kolom : nim, kode, nilai
        **/

        execSelectColumnCommand(arrayListtoString(colInTableFrom).split(","),tableFrom);
        execSelectColumnCommand(arrayListtoString(colInTableJoin).split(","),tableJoin);

    }

    // will print all column of selected table
    private void execSelectCommand(String s) {
        new tableModel().printTable(s,new tableDatabase().readTable());
    }

    private static String removeCharAfter (String str, int idx) {
        return str.substring(idx+1,str.length());
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    private static String removeXLastChar(String str,char char_to_remove) {
        if (str.charAt(str.length()-1) == char_to_remove) {
            return removeLastChar(str);
        } else {
            return str;
        }
    }

    private static String removeFirstChar(String str) {
        return str.substring(1, str.length());
    }

    private static String removeXFirstChar(String str, char char_to_remove) {
        if (str.charAt(0) == char_to_remove) {
            return removeFirstChar(str);
        } else {
            return str;
        }
    }

    public String arrayListtoString (List<String> arrList) {
        if (arrList == null) {
            return null;
        } else {
            String temp = "";
            for (String s : arrList) {
                if (s.equalsIgnoreCase(arrList.get(arrList.size()-1))) {
                    temp = temp+s;
                } else {
                    temp = temp + s + ",";
                }
            }
            return temp;
        }
    }

    public static void main(String[] args) {
    //System.out.println(removeFirstChar("command"));
        //System.out.println("m.cust".split(".")[0]);
       // System.out.println("m.cust".contains("."));
        new executingCommandState().execCommand("select c.id_customer,nama,id_barang from customer c join membeli m on (c.id_customer = m.id_customer);");
        //new executingCommandState().execCommand("select c.id_customer,nama,id_barang from customer c join membeli m on (c.id_customer = m.id_customer);");
    }

}
