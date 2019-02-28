package State;

import java.util.Scanner;

public class mainState {
    // variables
    private boolean shouldRun = true;
    Scanner strScanner = new Scanner(System.in);

    // constructor
    public mainState() {
    }

    // getter
    public boolean isShouldRun() {
        return shouldRun;
    }

    // setter
    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

    // method & function
    public void runCLI () {
        while (shouldRun) {
            System.out.print("command>>");
            new executingCommandState().execCommand(strScanner.nextLine());
        }
    }

    public static void main(String[] args) {
        new mainState().runCLI();
    }
}
