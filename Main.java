//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

//This file should be called Reporting.java I think

public class Main {
    public static void main(String[] args) {
        String username = "pdubois";
        String password = "PDUBOIS";

        //Username and password check. Then see if there are additional arguments
        if (args[0] == username & args[1] == password) {
            // Do things here

            // If only username and password passed
            if (args.length == 2) {
                System.out.printf("1- Report Patients Basic Information\n");
                System.out.printf("2- Report Doctors Basic Information\n");
                System.out.printf("3- Report Admissions Information\n");
                System.out.printf("4- Update Admissions Payment\n");
                //Terminate program
                System.exit(0);
            }
            // If program is executed with a number as the third argument
            else if (isNumeric(args[2])) {
                int val =  Integer.parseInt(args[2]);

                // If a 1 is the third argument
                if (val == 1) {
                    //Asks for user to input the SSN they want to look up
                    System.out.printf("Enter Patient SSN: ");
                    Scanner sc = new Scanner(System.in);
                    // Gets entered SSN
                    int SSN = sc.nextInt();

                    //Queries database for patient SSN and prints linked values
                    //...

                    //Terminate program
                    System.exit(0);
                }
                // If a 2 is the third argument
                else if (val == 2) {
                    //Asks for user to input the ID for the Doctor they want to look up
                    System.out.printf("Enter Doctor ID: ");
                    Scanner sc = new Scanner(System.in);
                    // Gets entered ID
                    int empID = sc.nextInt();

                    //Queries database for Doctor empID and prints linked values
                    //...

                    //Terminate program
                    System.exit(0);
                }
                // If a 3 is the third argument
                else if (val == 3) {
                    //Asks for user to input the admissionNumber they want to look up
                    System.out.printf("Enter Admission Number: ");
                    Scanner sc = new Scanner(System.in);
                    // Gets entered ID
                    int adNum = sc.nextInt();

                    //Queries database for admissionNumber and prints linked values
                    //...

                    //Terminate program
                    System.exit(0);
                }
                // If a 4 is the third argument
                else if (val == 4) {
                    //Asks for user to input the admissionNumber they want to look up
                    System.out.printf("Enter Admission Number: ");
                    Scanner sc = new Scanner(System.in);
                    // Gets entered ID
                    int adNum = sc.nextInt();

                    //Asks for user to input the new total payment
                    System.out.printf("Enter the new total payment: ");
                    Scanner sc2 = new Scanner(System.in);
                    // Gets entered total payment
                    float totPay = sc2.nextFloat();

                    //Updates database with new total payment
                    //...

                    //Terminate program
                    System.exit(0);
                }
                // Add rest of things for entering 2, 3, or 4 as the third argument

                // Will needs some form of error handling
            }

            // Will needs some form of error handling
        }

        // Will needs some form of error handling
    }

    // Code for checking if string is numeric pulled from https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}


