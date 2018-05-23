import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.net.ServerSocket;
import java.util.StringTokenizer;
 
public class C5Project{
   public static void main(String[] args) throws IOException{
      try{
         Scanner scan = new Scanner(System.in);
         Boolean run = true;
         InetAddress ip = InetAddress.getLocalHost();
         int port = 4444;
         int[][] map = new int[40][60];
         int locX = 55;
         int locY = 3;
         boolean print = true;

         //Open the socket connection.
         Socket s = new Socket(ip, port);
         //time out at 30 seconds
         s.setSoTimeout(30000);

         System.out.println("Connected to server...");

         // set up data streams
         DataInputStream dis = new DataInputStream(s.getInputStream());
         DataOutputStream dos = new DataOutputStream(s.getOutputStream());
         
         System.out.println("WASD keys to move");
         System.out.println("type done to finish");
         
         
         //gets map
         int maxX1 = Integer.parseInt(dis.readUTF());
         int maxY1 = Integer.parseInt(dis.readUTF());
         locX = Integer.parseInt(dis.readUTF());
         locY = Integer.parseInt(dis.readUTF());
         for (int x = 0; x < maxX1; x++){
            for (int y = 0; y < maxY1; y++){
               map[x][y] = Integer.parseInt(dis.readUTF());
            }
         }
         
         //just a section
         for (int x = -1; x < 21; x++){
            System.out.print("|");
            for (int y = 0; y < 20; y++){
            if (x == -1 || x == 20){
               System.out.print("-");
            }
            else{
               int placeX = x+(locY/20)*20;
               int placeY = y+(locX/20)*20;
                  if (locY < 11){
                     placeX = x;
                  }
                  if (locY > (map.length-11)){
                     placeX = x+map.length-20;
                  }
                  if (locX < 11){
                     placeY = y;
                  }
                  if (locX > (map[0].length-11)){
                     placeY = y+map[0].length-20;
                  }
                  
                  if(map[placeX][placeY] == 1){
                     System.out.print("X");
                  }
                  else if(map[placeX][placeY] == 0){
                     System.out.print(" ");
                  }
                  else if(map[placeX][placeY] == 9){
                     System.out.print("O");
                  }
               }
            }
            System.out.print("|");
            System.out.print("\n");
         }
         //end just a section

         while (run){
            //while running
            
            //gets movement and sends data
            String oper = scan.next();
            if (oper.equals("done")){
               run = false;
               dos.writeUTF(oper);
            }
            else{
               dos.writeUTF(oper);
               dos.writeUTF(Integer.toString(locX));
               dos.writeUTF(Integer.toString(locY));
               oper = null;
               
               //gets map
               int maxX = Integer.parseInt(dis.readUTF());
               int maxY = Integer.parseInt(dis.readUTF());
               locX = Integer.parseInt(dis.readUTF());
               locY = Integer.parseInt(dis.readUTF());
               for (int x = 0; x < maxX; x++){
                  for (int y = 0; y < maxY; y++){
                     map[x][y] = Integer.parseInt(dis.readUTF());
                  }
               }
               
               //print the table
               if(print){
                  //just a section
                  System.out.print("\n");
                  for (int x = -1; x < 21; x++){
                     System.out.print("|");
                     for (int y = 0; y < 20; y++){
                        if (x == -1 || x == 20){
                           System.out.print("-");
                        }
                        else{
                           int placeX = x+(locY/20)*20;
                           int placeY = y+(locX/20)*20;
                           if (locY < 11){
                              placeX = x;
                           }
                           if (locY > (map.length-11)){
                              placeX = x+map.length-20;
                           }
                           if (locX < 11){
                              placeY = y;
                           }
                           if (locX > (map[0].length-11)){
                              placeY = y+map[0].length-20;
                           }
                           
                           if(map[placeX][placeY] == 1){
                              System.out.print("X");
                           }
                           else if(map[placeX][placeY] == 0){
                              System.out.print(" ");
                           }
                           else if(map[placeX][placeY] == 9){
                              System.out.print("O");
                           }
                        }
                     }
                     System.out.print("|");
                     System.out.print("\n");
                  }
                  //end just a section
                  
                  System.out.println("WASD keys to move");
                  System.out.println("type done to finish");
               }
            }
         }
         
         //end of program
         //dos.writeUTF("");
         //String cleanUp = dis.readUTF();
         System.out.println("wait 15 seconds before reconnecting");
         System.out.println("exiting");
      }
      catch (IOException e) {
         System.out.println(e);
      }
   }
}