package com.example.uploadingfiles.db;
 
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.uploadingfiles.FileDetail;
 
/**
 * This program demonstrates how to establish database connection to Microsoft
 * SQL Server.
 * @author www.codejava.net
 *
 */
public class JdbcSQLServerConnection {
 
	public static  Connection connection = null;
    public static void main(String[] args) {
 
       
 
        try {
 
            String dbURL = "jdbc:sqlserver://193.64.16.38\\StatisticsAPP1";
            String user = "Nf_srv_stream";
            String pass = "Lanttu-12";
            
            String connectionUrl =
                    "jdbc:sqlserver://193.64.16.38:1433;"
                            + "database=StatisticsAPP1;"
                            + "user=Nf_srv_stream;"
                            + "password=Lanttu-12;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true;"
                            + "loginTimeout=30;";	
             connection=null;

            try {
            	connection = DriverManager.getConnection(connectionUrl);
                      		
            	
                // Code here.
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
            //connection = DriverManager.getConnection(dbURL, user, pass);
            if (connection != null) {
                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                	connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void uploadToDB(String CustFileName,
    		String formatedCustFileName,
    		String MsgFileName,
    		String formatedMsgFileName,
    		String application,
    		String  description,
    		String incident,
    		String submitter,
    		String status)
    {
    	
       if(JdbcSQLServerConnection.connection==null)
       {
    	   connection=createConnection();
       }
       String pattern = "dd.MM.YYYY hh:mm:ss";
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
       String date = simpleDateFormat.format(new Date());
       System.out.println(date);
     
    
    	String insertQuery="INSERT INTO StatisticsAPP1.dbo.FyiMsgupdateTracker"
    			+ "(ORIGNINAL_CUSTFILE_NAME , EXSTREAM_CUSTFILE_NAME , ORIGNINAL_MSGFILE_NAME,"
    			+ "	EXSTREAM_MSGFILE_NAME"
    			+ ",APPLICATION ,DESCRIPTION ,INCIDENT_ID,SUBMITTER ,STATUS,SUBMITTED_TIME, "
    			+ "LAST_UPDATE_TIME,COMPLETION_TIME ) "+ 
                             "VALUES ('"+CustFileName+"','"+
                            		 formatedCustFileName+"','"+
                            		 MsgFileName+"','"+
                            		 formatedMsgFileName+"','"+
                            		 application+"','"+
                            		 description+"','"+
                            		 incident+"','"+
                            		 submitter+"','"+
                            		 status+"','"+
                            		 date+"','"+
                            		 date+"','"+
                            		 " "+"');";
    	System.out.println("insertQuery +" +insertQuery );
    	
    	try {
			Statement queryStatement=connection.createStatement();
			queryStatement.execute(insertQuery);
			queryStatement.closeOnCompletion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    public static List<FileDetail> loadFromDB()
    {
    	 List<FileDetail> fileDetails=new ArrayList();
       if(JdbcSQLServerConnection.connection==null)
       {
    	   connection=createConnection();
       }
    
    	String selectQuery="select origninal_custfile_name , exstream_custfile_name ,"
    			+ " origninal_msgfile_name,exstream_msgfile_name,APPLICATION ,"
    			+ "DESCRIPTION ,INCIDENT_ID,"
    			+ "SUBMITTER ,STATUS,SUBMITTED_TIME,LAST_UPDATE_TIME,COMPLETION_TIME from "
    			+ " StatisticsAPP1.dbo.FyiMsgupdateTracker";
    	System.out.println("insertQuery +" +selectQuery );
    	
    	try {
			Statement queryStatement=connection.createStatement();
			queryStatement.execute(selectQuery);
			ResultSet reResultSet=queryStatement.getResultSet();
			while(reResultSet.next()){
				FileDetail file=new FileDetail();
			String CustFileName =reResultSet.getString(1);
			String formatedCustFileName=reResultSet.getString(2);
			String MsgFileName =reResultSet.getString(3);
			String formatedMsgFileName=reResultSet.getString(4);
			String application =reResultSet.getString(5);
			String description =reResultSet.getString(6);
			String incident=reResultSet.getString(7);
			String submitter=reResultSet.getString(8);
			String status=reResultSet.getString(9);
			String submittedDate=reResultSet.getString(10);
			String statusDate=reResultSet.getString(11);
			String completionDate=reResultSet.getString(12);
			
			file.setCustFileName("http://193.64.16.36:5003/files/"+CustFileName);
			file.setFormatedCustFileName(formatedCustFileName);
			file.setMsgFileName("http://193.64.16.36:5003/files/"+MsgFileName);
			file.setFormatedMsgFileName(formatedMsgFileName);
			file.setApplication(application);
			file.setDescription(description);
			file.setIncident(incident);
			file.setSubmitter(submitter);
			file.setStatus(status);
			file.setSubmitedDate(submittedDate);
			file.setLatestUpdateDate(statusDate);
			file.setCompletionDate(completionDate);
			fileDetails.add(file);
			}
			
			
			queryStatement.closeOnCompletion();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return fileDetails;
    	
    }
    public static  Connection  createConnection()
    {
    	try {
    		 
            String dbURL = "jdbc:sqlserver://193.64.16.38\\StatisticsAPP1";
            String user = "Nf_srv_stream";
            String pass = "Lanttu-12";
            
            String connectionUrl =
                    "jdbc:sqlserver://193.64.16.38:1433;"
                            + "database=StatisticsAPP1;"
                            + "user=Nf_srv_stream;"
                            + "password=Lanttu-12;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true;"
                            + "loginTimeout=30;";	
             connection=null;

            try {
            	connection = DriverManager.getConnection(connectionUrl);
                      		
            	
                // Code here.
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
            //connection = DriverManager.getConnection(dbURL, user, pass);
            if (connection != null) {
                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
           
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    	
    	 return connection;
    }
    
}