import java.sql.*;

public class Main {

     private static final String url="jdbc:mysql://localhost:3306/mydb";
    private static final String Username="root";
    private static final String password="sfu%gsu46-&#";

    public static void main(String[] args) {
        try{
       Class.forName("com.mydb.cj.jdbc.Driver");
        }
    catch(ClassNotFoundException e) {
        System.out.print(e.getMessage());
    }
        try{
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","sfu%gsu46-&#");
        Statement statement=con.createStatement();
        System.out.println(con);
            String query="select * from event;";
        ResultSet resultset=statement.executeQuery(query);
           while(resultset.next()){
               int id=resultset.getInt("tickets");

               System.out.println("id"+id);
           }
        }
        catch(SQLException e){
      System.out.println(e.getMessage());
    }

    }

    }