//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

//This file should be called Reporting.java I think

public class Main {
    public static void main(String[] args) throws SQLException{
        //Connect to Oracle
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver? Did you follow the execution steps. ");
            System.out.println("");
            System.out.println("*****Open the file and read the comments in the beginning of the file****");
            System.out.println("");
            e.printStackTrace();
            return;
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl",
                    args[0], //Username --> Does this need to connect to one of our accounts with our table?
                    args[1]);//Password
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if(conn == null){
            System.out.println("Failed to make connection!");
            //Terminate program
            conn.close();
            System.exit(0);
        }

        if (args.length == 2) {
        System.out.printf("1- Report Patients Basic Information\n");
        System.out.printf("2- Report Doctors Basic Information\n");
        System.out.printf("3- Report Admissions Information\n");
        System.out.printf("4- Update Admissions Payment\n");
        //Terminate program
        conn.close();
        System.exit(0);
        }
        // If program is executed with a number as the third argument
        else if (isNumeric(args[2])) {
            int val = Integer.parseInt(args[2]);

            // If a 1 is the third argument
            if (val == 1) {
                //Asks for user to input the SSN they want to look up
                System.out.printf("Enter Patient SSN: ");
                Scanner sc = new Scanner(System.in);
                // Gets entered SSN
                int SSN = sc.nextInt();
                String strSSN = String.valueOf(SSN);

                //Queries database for patient SSN and prints linked values
                //Need to make conn Connection object --> use OracleTest.java code
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SSN, firstName, lastName, address FROM patient WHERE SSN = " + strSSN);

                //Extract values from query
                String pSSN = String.valueOf(rs.getInt("SSN"));
                String pfname = rs.getString("firstName");
                String plname = rs.getString("lastName");
                String paddress = rs.getString("address");

                //Print values from query
                System.out.printf("Patient SSN: " + pSSN + "\n");
                System.out.printf("Patient First Name: " + pfname + "\n");
                System.out.printf("Patient Last Name: " + plname + "\n");
                System.out.printf("Patient Address: " + paddress + "\n");

                //Terminate program
                conn.close();
                System.exit(0);
            }
            // If a 2 is the third argument
            else if (val == 2) {
                //Asks for user to input the ID for the Doctor they want to look up
                System.out.printf("Enter Doctor ID: ");
                Scanner sc = new Scanner(System.in);
                // Gets entered ID
                String empID = String.valueOf(sc.nextInt());

                //Queries database for Doctor empID and prints linked values
                //Need to make conn Connection object --> use OracleTest.java code
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID, firstName, lastName, gender, medicalSchool, specialty FROM employee, doctor WHERE employee.ID = " + empID + " AND doctor.empID = " + empID);

                //Extract values from query
                String dID = String.valueOf(rs.getInt("ID"));
                String dfName = rs.getString("firstName");
                String dlName = rs.getString("lastName");
                String dGender = rs.getString("gender");
                String dMedSchool = rs.getString("medicalSchool");
                String dSpecialty = rs.getString("specialty");

                //Print values from query
                System.out.printf("Doctor ID: " + dID + "\n");
                System.out.printf("Doctor First Name: " + dfName + "\n");
                System.out.printf("Doctor Last Name: " + dlName + "\n");
                System.out.printf("Doctor Gender: " + dGender + "\n");
                System.out.printf("Doctor Graduated From: " + dMedSchool + "\n");
                System.out.printf("Doctor Specialty: " + dSpecialty + "\n");

                //Terminate program
                conn.close();
                System.exit(0);
            }
            // If a 3 is the third argument
            else if (val == 3) {
                //Asks for user to input the admissionNumber they want to look up
                System.out.printf("Enter Admission Number: ");
                Scanner sc = new Scanner(System.in);
                // Gets entered ID
                String adNum = String.valueOf(sc.nextInt());

                //Queries database for admissionNumber and prints linked values
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery("SELECT admissionNum, SSN, admissionTime, totalPayment FROM hospitalAdmission WHERE admissionNum = " + adNum);

                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //Extract values from query
                String haNum = String.valueOf(rs1.getInt("admissionNum"));
                String haSSN = String.valueOf(rs1.getInt("SSN"));
                String haDate = formatter.format(rs1.getDate("admissionTime")); //Not sure this will work properly
                String haPayment = String.valueOf(rs1.getInt("totalPayment"));

                //Print values from first query
                System.out.printf("Admission Number: " + haNum + "\n");
                System.out.printf("Patient SSN: " + haSSN + "\n");
                System.out.printf("Admission date (start date): " + haDate + "\n");
                System.out.printf("Total Payment: " + haPayment + "\n");

                //Performs second query for rooms stayed in
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT roomNum, stayStart, stayEnd FROM stayedIn WHERE admissionNum = " + adNum);

                System.out.printf("Rooms:\n");

                //While loop makes sure all relevant values are printed
                while (rs2.next()) {
                    //Extract values from query
                    String rNum = String.valueOf(rs2.getInt("roomNum"));
                    String rSS = formatter.format(rs2.getDate("stayStart")); //Not sure this will work properly
                    String rSE = formatter.format(rs2.getDate("stayEnd")); //Not sure this will work properly

                    //Print values from second query
                    System.out.printf("\tRoomNum: " + rNum);
                    System.out.printf("\tFromDate: " + rSS);
                    System.out.printf("\tToDate: " + rSE + "\n");
                }

                //Performs third query for Doctors seen
                Statement stmt3 = conn.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT empID FROM examinedBy WHERE admissionNum = " + adNum);

                System.out.printf("Doctors that examined the patient in this admission:\n");

                //While loop makes sure all relevant values are printed
                while (rs3.next()) {
                    //Extract values from query
                    String dID = String.valueOf(rs3.getInt("empID"));

                    //Print values from third query
                    System.out.printf("\tDoctor ID: " + dID + "\n");
                }

                //Terminate program
                conn.close();
                System.exit(0);
            }
            // If a 4 is the third argument
            else if (val == 4) {
                //Asks for user to input the admissionNumber they want to look up
                System.out.printf("Enter Admission Number: ");
                Scanner sc = new Scanner(System.in);
                // Gets entered ID
                String adNum = String.valueOf(sc.nextInt());

                //Asks for user to input the new total payment
                System.out.printf("Enter the new total payment: ");
                Scanner sc2 = new Scanner(System.in);
                // Gets entered total payment
                String totPay = String.valueOf(sc2.nextFloat());

                //Updates database with new total payment
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE hospitalAdmission SET totalPayment = " + totPay + " WHERE admissionNum = " + adNum);

                //Terminate program
                conn.close();
                System.exit(0);
            }

            // Add rest of things for entering 2, 3, or 4 as the third argument

            // Will needs some form of error handling
        }
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


