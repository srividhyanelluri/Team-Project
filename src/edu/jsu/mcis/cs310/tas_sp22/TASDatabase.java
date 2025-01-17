package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.*;


public class TASDatabase {
    private final Connection connection;
    
    public TASDatabase(String username, String password, String address){
        this.connection = openConnection(username, password, address);
    }
    
    public boolean isConnected(){
        boolean connected = false;
        try {
            if( !(connection == null))
                connected = !(connection.isClosed());
        }
        catch (SQLException e) {e.printStackTrace();}
        return connected;
    }
    
    private Connection openConnection(String u, String p, String a){
        Connection conn = null;
        
        if(a.equals("") || u.equals("") || p.equals(""))
            System.err.println("ERROR: PLEASE SPECIFY THE ADDRESS/USERNAME/PASSWORD BEFORE OPENING THE DATABASE CONNECTION ");
        else { 
            try {
                String url = "jdbc:mysql://" + a + "/tas_sp22_v1?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=EXCEPTION&serverTimezone=America/Chicago";
               // System.err.println("Connecting to " + url + "...");
                conn = DriverManager.getConnection(url,u,p);
            }
            catch (Exception e){e.printStackTrace();}
        }
        return conn;
    }
    
    public Badge getBadge(String id) {
        
        Badge b = null;
        
        try {  
            
            PreparedStatement pstUpdate = null, pstSelect = null;
            ResultSet resultset = null;
            
            String query = "SELECT * FROM badge WHERE id = ?";
            pstSelect = connection.prepareStatement(query);
            pstSelect.setString(1, id);
            
            
            pstSelect.execute();
    
            resultset = pstSelect.getResultSet();
                
            if(resultset.next()) {
                String desc = resultset.getString("description");
                b = new Badge(id, desc);
                  
            }
           
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return b;
    }


    
    
    
}
