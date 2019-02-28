package table;

import java.util.List;

public class tableModel {
    // Variable
    private String tableName;
    private List<String> tableCol;


    // Constructor
    public tableModel() {
    }

    public tableModel(String tableName, List<String> tableCol) {
        this.tableName = tableName;
        this.tableCol = tableCol;
    }

    // Getter
    public String getTableName() {
        return tableName;
    }

    public List<String> getTableCol() {
        return tableCol;
    }

    // Setter
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableCol(List<String> tableCol) {
        this.tableCol = tableCol;
    }

    // method & function
    public void printTable (String tname,List<tableModel> t) {
        for (tableModel tm : t) {
            if (tm.getTableName().equalsIgnoreCase(tname)) {
                printTable(tm);
            }
        }
    }

    public void printTableAll (List<tableModel> t) {
        for (tableModel tm : t) {
                printTable(tm);
        }
    }


    public void printTable (tableModel l) {
        printLine("-");
        System.out.println("T.Name :\t"+l.getTableName());
        System.out.print("Col.Name :\t");
        for (int i = 0; i < l.getTableCol().size(); i++) {
            if (i == l.getTableCol().size()-1) {
                System.out.print(l.getTableCol().get(i) + "");
            } else {
                System.out.print(l.getTableCol().get(i)+" ,");
            }
        }
        printLine("-");
    }


    public void printLine (String line) {
        System.out.println();
        for (int i = 0 ; i <= 29; i++) {
            System.out.print(line);
        }
        System.out.print("\n");
    }

    public void printTableWithColumn(String[] colName, String tableName, List<tableModel> table_list) {
        //System.out.println("printTableWithColumn init");
        tableModel theOne = null;
        for (tableModel tm : table_list) {
            if (tm.getTableName().equalsIgnoreCase(tableName)) {
                theOne = tm;
                break;
            }
        }
        if (theOne != null) {
            printLine("-");
            System.out.println("T.Name :\t"+theOne.getTableName());
            System.out.print("Col.Name :\t");
            for (String columnNameReq : colName) {
                for (String columnInTable : theOne.getTableCol()) {
                    if (columnNameReq.equalsIgnoreCase(columnInTable)) {
                        if (columnNameReq.equalsIgnoreCase(colName[colName.length-1])) {
                            System.out.print(columnInTable);
                        } else {
                            System.out.print(columnInTable+", ");
                        }
                    }
                }
            }
            printLine("-");
        }
    }
}
