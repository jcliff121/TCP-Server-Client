// Name: Jermaine Clifford
// Date: October 16, 2017
// Requirements:  This code requires testsmall.txt (or testlarge.txt) to be in the same folder as this java file. 
//               This code also requires the TCPServer.java to be running and the IP address 
//               of the computer it is running on to be updated.
// Purpose: This code reads in a file and sends it 100 times using a TCP connection to the server and keeps 
//          up with the time it takes to send the files. It also gets the total time and the average time it takes to
//          send a file.  

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class TCPClient {
  public static void main(String argv[]) throws Exception
  {
   
   String fileName = "Testsmall.txt"; //the file that will be sent. Must be in the same folder as this program. 
   
   System.out.println("Looking for " + fileName + " file....");
   System.out.println("\n-----------------------\n");
   
   File clientFile = new File(fileName); //creating the file variable
   
   long totalTime = 0; //variable for the total time
   
   //this loops sends the file 100 times
   for (int i=0; i<100; i++){
       long fileTime = 0;
       
       long startTime = System.currentTimeMillis(); //variable for the start of the transmission
       
       Scanner clientFileReader = new Scanner(clientFile); //creates a scanner for the file being sent
       
       //creates the client socket to connect to the server socket
       Socket clientSocket = new Socket("168.26.113.206", 6789); //<-------update IP address parameter with desired IP address!
       
       //displays the IP address that is being used 
       System.out.println("I am connecting to the Server side: " + clientSocket.getLocalAddress());
       
       System.out.println("\nSending " + clientFile + " file for the " + (i+1) + "th time");
       
       System.out.println("File transmission start time: " + startTime); //displays the start time of file transmission
       
       //creates output stream that will be used to send data to the server
       DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream()); 
       
       //reads in the file and replaces the "\n" dropped by the nextLine() function. 
       while(clientFileReader.hasNextLine()){
           toServer.writeBytes(clientFileReader.nextLine() + "\n");
        }
       
       clientFileReader.close(); //closes the file 
       
       clientSocket.close(); //closes the client socket
       
       long endTime = System.currentTimeMillis(); //variable for the end of the file transmission
       
       System.out.println("File transmission end time: " + endTime);
       
       fileTime = endTime - startTime; //the time to send the file
       
       System.out.println("The file took " + fileTime + " milliseconds");
       
       totalTime += fileTime; //the total time to send all of the files
       
       System.out.println("The " + clientFile + " file sent " + (i+1) + " times");
       
       System.out.println("\n-----------------------\n");
    }
    System.out.println("The total time to send the file 100 times: " + totalTime + " milliseconds"); //displays the total time
    System.out.println("The average time to send the file: " + totalTime/100 + "." + totalTime%100 + " milliseconds"); //displays the average
    System.out.println("I am done");
}
}
  
