/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author seane
 */
import java.net.*; 
import java.io.*; 
import java.util.ArrayList;
import java.util.Scanner;

public class Server { 
    public static void main(String[] args) throws IOException {
    BufferedReader br = null;
    String[] parts;
    String part1;
    String reply = null;
    String part2;
    ServerSocket serverSocket = null; 
    String input = null;
    ArrayList <String> ips = new ArrayList<String>();
        ArrayList <String> urls = new ArrayList <String>();
        Scanner fromStr;
        String word;
    int port = 0;
    String filename = null;
    if(args.length > 0){
        input = args[0];
        port = Integer.parseInt(input);
        filename = args[1];
    }

    try { 
        serverSocket = new ServerSocket(port); 
    } 
    catch (IOException e) { 
        System.err.println("Could not listen on port: "+ port); 
        System.exit(1); 
    } 

    Socket clientSocket = null; 
    System.out.println ("Waiting for connection ...");

    try { 
        clientSocket = serverSocket.accept(); 
        } 
    catch (IOException e) { 
        System.err.println("Accept failed."); 
        System.exit(1); 
    } 

    System.out.println ("Connection successful");
    System.out.println ("Waiting for input ...");
    
    //open IO streams
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
    BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 

    String inputLine; 
    try{
                br= new BufferedReader (new FileReader  (filename));    
                String temp;
                while((temp = br.readLine())  != null){
                    fromStr = new Scanner(temp);
                    for(int i=0; i<2; i++){
                        word = fromStr.next();
                        if(i == 0){
                            urls.add(word);
                        }
                        if(i == 1){
                            ips.add(word);
                        }
                    }
                     
                
            }
            }catch(IOException e){
                e.printStackTrace();
            }

    //wait and read input from client. Exit when input from socket is Bye
    while ((inputLine = in.readLine()) != null) 
        { 
            String temp= inputLine;
            if(inputLine.contains("get-ip")){
                parts = temp.split(" ");
                for(int i=0; i<ips.size(); i++){
                    if((urls.get(i).contains(parts[1]))){
                        out.println(ips.get(i));
                    }
                }
            }
            if(inputLine.contains("get-hostname")){
                parts = temp.split(" ");
                for(int i=0; i<urls.size(); i++){
                    if((ips.get(i).contains(parts[1]))){
                        out.println(urls.get(i));
                    }
                }
            }
            if(!(inputLine.contains("get-ip")|| inputLine.contains("get-hostname"))){
                out.println("You entered an unsupported command");
            }
            if(inputLine.equals(0)){
                System.out.println("Connection closed");
                break;
            }
            
        
         
        //reply back to client

        if (inputLine.trim().toLowerCase().equals("bye")) 
            break; 
        } 

    //close IO streams and at the end socket
    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
   } 
}

