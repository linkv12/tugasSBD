package State;

import table.tableDatabase;
import table.tableModel;

import java.util.ArrayList;
import java.util.List;

public class executingCommandState {



    // method & function
    public void execCommand(String command) {
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


    }

    // will print all column of selected table
    private void execSelectCommand(String s) {
        new tableModel().printTable(s,new tableDatabase().readTable());
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

    public static void main(String[] args) {
    //System.out.println(removeFirstChar("command"));
    }
}
