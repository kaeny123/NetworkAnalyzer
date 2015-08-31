/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkanalyzer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;


/**
 *
 * @author Kaeny
 */
public class Pinger {
static int count=0;
static int max = 0;
static int min = 0;
static int avg = 0;
static int ping;
static java.util.Date dateDown = new java.util.Date();
static java.util.Date dateUp = new java.util.Date();
static java.util.Date dateStart = new java.util.Date();


   
    public static int pingCheck(String host) throws IOException{
    String time = "";
    //command to execute
     String pingCmd = "ping " + host + " -t " + "-n 1"; 
    
     //gets runtime to execute command
     Runtime runtime = Runtime.getRuntime(); 
     try {
         Process process = runtime.exec(pingCmd);
         
         //gets inputstream to read the output of the cocmmand
         BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
         
         //read outputs
         String inputLine = in.readLine();
         while((inputLine != null)) {
             if (inputLine.length() > 0 && inputLine.contains("time")){
                 time = inputLine.substring(inputLine.indexOf("time"));
                 break;
             }
             inputLine = in.readLine();
         } 
             time = time.replaceAll("[^0-9]+", " ");
             time = time.substring(0, Math.min(time.length(), 3));
             time = time.replaceAll(" ", "");
             //System.out.println("ping:" + time);
             

            //ping calcs
             
             
             count++;
             ping = Integer.parseInt(time);
             
             dateUp = new java.util.Date();
             
             if(dateStart.compareTo(dateDown) < 0 )
             {
                try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("disconnectLog.txt", true))))
             {
             out.println("You have been disconnected from " + dateDown + " to " + dateUp );
             } 
             }
             dateStart = dateUp;
                      
                 
             
             
     } catch(IOException | NumberFormatException ex)
     { 
             dateDown = new java.util.Date();
     }
     
     
    return ping;
    }
    
}
