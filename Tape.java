import java.util.ArrayList;
import java.util.Collections;

public class Tape {
    private ArrayList<String> left;
    private ArrayList<String> right;

    public Tape() {
        left = new ArrayList<String>();
        right = new ArrayList<String>();
    }

    // Returns the right side of the tape
    public ArrayList<String> getRight() {
        System.out.println(right);
        return right;
    }

    // Returns the left side of the tape
    public ArrayList<String> getLeft() {
        ArrayList<String> reversedLeft = new ArrayList<>(left);
        Collections.reverse(reversedLeft);
        System.out.print(reversedLeft);
        return reversedLeft;
    }

    // Initializes the tape for the start
    public void initializeTape() {
        for (int i = 0; i < 15; i++) {
            right.add("_");
        }
        right.add("_");
        left.add("_");
    }

    // Initializes the tape with the input
    public void initializeTape(String content) {
        char[] contentArr = content.toCharArray();

        for (int i = 0; i < contentArr.length; i++) {
            right.add(String.valueOf(contentArr[i]));
        }

        right.add("_");
        left.add("_");

    }

    // Returns the content of the tape through a string
    public String getTapeContent() {
        String leftString = "";
        String rightString = "";

        for (int i = 0; i < left.size(); i++) {
            leftString += left.get(i);
        }

        for (int i = 0; i < right.size(); i++) {
            rightString += right.get(i);
        }

        StringBuffer rLeftString = new StringBuffer(leftString).reverse();
        return rLeftString + rightString;
    }

    // Prints the content of the tape
    public void printTapeContent() {
        getLeft();
        getRight();
    }

    // Changes where the read/write head is supposed to be pointing
    public void step(String symbol) {
        if (symbol.equals("L")) {
            String temp = left.get(0);
            right.add(0, temp);
            left.remove(0);
            left.add("_");
        } else if (symbol.equals("R")) {
            String temp = right.get(0);
            left.add(0, temp);
            right.remove(0);
            right.add("_");
        }
    }

    // Reads the symbol of the head: right array, index 0
    public String readSymbol() {
        return right.get(0);
    }


    // Writes the symbol where the head
    public void writeSymbol(String symbol) {
        right.set(0, symbol);
    }

    public static void main(String[] args) {
        Tape tape1 = new Tape();

        tape1.initializeTape("WOWZER");

        tape1.printTapeContent();
        tape1.step("L");
        tape1.printTapeContent();
        tape1.writeSymbol("O");
        tape1.step("L");
        tape1.writeSymbol("W");
        tape1.step("L");
        tape1.writeSymbol("Z");
        tape1.step("L");
        tape1.writeSymbol("E");
        tape1.step("L");
        tape1.writeSymbol("R");
        tape1.step("R");
        tape1.step("R");
        tape1.step("R");
        tape1.step("R");
        tape1.step("R");

        tape1.printTapeContent();
    }

}
