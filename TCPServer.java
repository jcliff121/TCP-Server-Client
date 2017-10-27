// Name: Jermaine Clifford
// Date: October 16, 2017
// Requirements:  This code requires testsmall.txt (or testlarge.txt) that is being used by the client to be 
//               in the same folder as this java file. This program needs to be run before the client side.  
// Purpose: This code receives a file 100 times from the client side, then reads in a file that it then compares
//          to the received files to see if it received the files correctly. It displays the failure rate and the average 
//          time it takes to receive a file. 

import java.io.*;
import java.net.*;
import java.util.Scanner; 
import java.io.File;
import java.io.InputStream;

public class TCPServer {
  public static void main(String argv[]) throws Exception
  {
      
    String clientSentence; //variable for each sentence in the file from the client. 
    
    Socket connectionSocket; //Socket variable
    
    BufferedReader fromClient; //Buffered Reader variable
    
    long totalTime = 0; //variable for the total time it takes to receive the files
    
    String comparedFile = "Testsmall.txt"; //the file that will be used for this program
    
    String outputFileName = "receivedFile"; //the name of the copies that will be made from the text file
    
    ServerSocket serSock = new ServerSocket(6789); //opens up the server socket so the client can connect
    
    System.out.println("I am waiting for the connection from the Client Side....");
    
    //this loop makes the server listen for 100 files from the client side. 
    for(int i=0; i<100; i++){
        long fileTime = 0; 
        long startTime = System.currentTimeMillis();
        
        //this makes the server wait till the client tries to connect then accepts the connection
        connectionSocket = serSock.accept(); 
        
        System.out.println("I am starting to receive the " + comparedFile + " file for the " + (i+1) + "th time.");
        
        //this creates the stream that will be used to receive the file. 
        fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        
        //creates a file that the received file will be written to
        PrintWriter out = new PrintWriter(outputFileName+(i+1)+ ".txt");
        
        int lineCounter = 0; //variable to keep track of the number of lines.
        
        //this loop checks to see if the client's file has ended and it prints how many lines have been received. 
        while(true){
            clientSentence = fromClient.readLine(); 
            
            if(clientSentence == null) break;
            
            out.println(clientSentence);
            lineCounter++;
            System.out.println("I have received: " + lineCounter + " lines");
        }
        
        //closes the file 
        out.close(); 
        
        //closes the server side of the client connection
        connectionSocket.close();
        
        long endTime = System.currentTimeMillis();

        fileTime = endTime - startTime; 
        
        System.out.println("The file took " + Math.abs(fileTime) + " milliseconds to be received.");
        
        totalTime += fileTime; 
        
        System.out.println("I am finishing receiving the " + comparedFile + " file for the " + (i+1) + "th time.");
        System.out.println("\n-----------------------\n");
    }
    System.out.println("I am done receiving files.");
    
    //closes the server socket. 
    serSock.close(); 
    
    int failCount = 0; 
    
    File serverFile = new File(comparedFile); //the file in the server side folder. 
    
    //this loop reads the server file and the files received from the client to compare them and find any errors
    for(int i=0; i<100; i++){
        File clientFile = new File(outputFileName + (i+1) + ".txt"); 
        Scanner serverFileReader = new Scanner(serverFile);
        Scanner clientFileReader = new Scanner(clientFile); 
        
        boolean passedTheTest; 
        while(serverFileReader.hasNextLine() && clientFileReader.hasNextLine()){
            passedTheTest = serverFileReader.nextLine().equals(clientFileReader.nextLine());
            
            //prints out any error found. 
            if(!(passedTheTest)){
                System.out.println("File " + outputFileName+(i+1)+ ".txt has error");
                failCount++; 
                break;
            }
        }
        serverFileReader.close();
        clientFileReader.close(); 
    }
    System.out.println("The failure rate is " + failCount + "/100"); //the failure rate when comparing the files
    System.out.println("The average time to receive the file: " + (totalTime/100) + " milliseconds"); //the average time it took to receive a file.
    System.out.println("I am done");
  }
}



