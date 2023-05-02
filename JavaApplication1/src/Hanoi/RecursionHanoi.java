/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hanoi;

/**
 *
 * @author wmend
 */
public class RecursionHanoi {

    public static String Hanoi(int nDisks, int fromPeg, int toPeg) {
        if (nDisks == 1) {
            //System.out.println("IF Disk ("+nDisks+") : " + fromPeg + " -> " + toPeg + ";");
            return fromPeg + " -> " + toPeg + ";";
        } else {
            //System.out.println("ELSE Disk ("+nDisks+") : " + fromPeg + " -> " + toPeg + ";");
            String sol1, sol2, sol3;
            int helpPeg = 6 - fromPeg - toPeg;
            sol1 = Hanoi(nDisks - 1, fromPeg, helpPeg);
            //System.out.println("Sol1 : " + sol1);
            sol2 = fromPeg + " -> " + toPeg + ";";
            //System.out.println("Sol2 : " + sol2);
            sol3 = Hanoi(nDisks - 1, helpPeg, toPeg);
            //System.out.println("Sol3 : " + sol3);
            return sol1 + sol2 + sol3;
        }
    }

    public static void main(String[] args) {
        int nDisks = 3;
        String stepsToSolution = Hanoi(nDisks, 1, 3);
        for (String step : stepsToSolution.split(";")) {
            System.out.println(step);
        }
    }
}
