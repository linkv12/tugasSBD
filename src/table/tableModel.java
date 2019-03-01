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
        System.out.println("");
        for (int i = 0 ; i <= 28; i++) {
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
            System.out.println("T.Name \t\t: "+theOne.getTableName());
            if (checkColNameValid(colName,theOne.getTableCol())) {
                System.out.print("Col.Name \t: ");
                for (String nameCol : colName) {
                    if (nameCol.equalsIgnoreCase(colName[colName.length -1])) {
                        //System.out.println(nameCol);
                        System.out.println(nameCol+"\n");
                    } else {
                        System.out.print(nameCol+" ,");
                    }
                }
            } else {
                System.out.println("SQL error column not exist");
            }
            printLine("-");
        } else {
            System.out.println("SQL error tables not exist");
        }
    }



    private boolean checkColNameValid(String[] colName, List<String> tableCol) {
        boolean isAllLegal = true;
        for (String sinArray : colName) {
            isAllLegal = isColInColList(sinArray,tableCol);
        }
        return isAllLegal;
    }

    private boolean isColInColList(String str, List<String> list) {
        for (String listContent: list) {
            if (str.equalsIgnoreCase(listContent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isColInColList(String str, tableModel tModel) {
        return isColInColList(str,tModel.getTableCol());
    }
}
