/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wmend
 */
public class DataAccess {

    static String _url = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=hanoi;"
            + "encrypt=true;trustServerCertificate=true";
    static String _user = "wmendozak";
    static String _password = "WMSql2022";

    public static void main(String[] args) throws ClassNotFoundException {
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        try {
            Connection cn = DriverManager.getConnection(_url, _user, _password);
            Statement st = cn.createStatement();
            String query = "select * from users";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.printf("%s %s %s%n", rs.getString(1), rs.getString(2), rs.getString(3));
            }

            //System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Error connection");
            e.printStackTrace();
        }
    }

    public static String Login(String user, String pwd) {
        String userName = "";
        CallableStatement cstmt = null;
        //ResultSet rs = null;
        try {
            Connection cn = DriverManager.getConnection(_url, _user, _password);

            cstmt = cn.prepareCall(
                    "{call usp_Users_Login(?,?,?)}");
            cstmt.setString("pId", user);
            cstmt.setString("pPwd", pwd);
            cstmt.registerOutParameter("pName", java.sql.Types.NVARCHAR);

            cstmt.execute();

            userName = cstmt.getString("pName");
        } catch (SQLException e) {
            System.out.println("Error connection");
            e.printStackTrace();
        }
        return userName;
    }

    public static String RegisterUser(String user, String pwd, String userName) {
        String output = "";
        CallableStatement cstmt = null;
        //ResultSet rs = null;
        try {
            Connection cn = DriverManager.getConnection(_url, _user, _password);

            cstmt = cn.prepareCall(
                    "{call usp_Users_Insert(?,?,?,?)}");
            cstmt.setString("pId", user);
            cstmt.setString("pPwd", pwd);
            cstmt.setString("pName", userName);
            cstmt.registerOutParameter("pOutput", java.sql.Types.NVARCHAR);

            cstmt.execute();

            output = cstmt.getString("pOutput");
        } catch (SQLException e) {
            System.out.println("Error connection");
            e.printStackTrace();
        }
        return output;
    }
    
    public static List<String> listScore(String userId) {
        List<String> list = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            Connection cn = DriverManager.getConnection(_url, _user, _password);

            cstmt = cn.prepareCall(
                    "{call usp_Score_Select(?)}");
            cstmt.setString("pUserId", userId);
            cstmt.execute();
            rs = cstmt.getResultSet();
            list = new ArrayList<>();
            while(rs.next())
            {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error connection");
            e.printStackTrace();
        }
        return list;
    }
    
        public static String registerScore(String userId, int disks, int pegs, int steps, int time) {
        String output = "";
        CallableStatement cstmt = null;
        //ResultSet rs = null;
        try {
            Connection cn = DriverManager.getConnection(_url, _user, _password);

            cstmt = cn.prepareCall(
                    "{call usp_Score_Insert(?,?,?,?,?)}");
            cstmt.setString("pUserId", userId);
            cstmt.setInt("pDisks", disks);
            cstmt.setInt("pPegs", pegs);
            cstmt.setInt("pSteps", steps);
            cstmt.setInt("pSeconds", time);

            cstmt.execute();

        } catch (SQLException e) {
            System.out.println("Error connection");
            e.printStackTrace();
        }
        return output;
    }
}
