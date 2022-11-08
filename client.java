import java.io.*;
import java.net.*;
import java.util.Scanner;


class client
{
   static int port1 = 0;
   static int id =-1;
   public static void connection1(String message)
   {
      try
      {

         BufferedReader inFromUser =
            new BufferedReader(new InputStreamReader(System.in));
         DatagramSocket clientSocket = new DatagramSocket();
         InetAddress IPAddress = InetAddress.getByName("localhost");
         byte[] sendData = new byte[1024];
         byte[] receiveData = new byte[1024];
         String sentence = message;
         sendData = sentence.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port1);
         clientSocket.send(sendPacket);
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         clientSocket.receive(receivePacket);
         String modifiedSentence = new String(receivePacket.getData());
         System.out.println("FROM SERVER:" + modifiedSentence);
         clientSocket.close();
      }
      catch(IOException e)
      {
         System.out.println("Error");
      }
   }
   public static Boolean cs_enter()
   {
      System.out.println("Sending...");
      connection1("start");
      System.out.println("Sent");
      new check().writeToCheckFile(id,1);
      return true;
   }

   public static Boolean cs_leave()
   {
      System.out.println("Sending...");
      new check().writeToCheckFile(id,2);
      new check().isMultipleCS();
      connection1("stop");
      System.out.println("Sent");
      return true;
   }
   public static void main(String args[]) throws Exception
   {

      File f = new File("testconfig.txt");
        System.out.println(f.exists());
        id = Integer.parseInt(args[0]);
        System.out.println(id);
        temp main = new temp(id);
        try {
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line + "!");
                if (!line.startsWith("#")) {
                    int numberOfNodes = Integer.parseInt(line);
                    break;
                }
            }
            int cntr = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line + "?");
                if (!line.startsWith(" ") && !line.startsWith("#")
                        && !line.equals("")) {
                    String[] split = line.split("\t+| +");
                    String port = split[1];
                    if (id == cntr) {
                     port1 = Integer.parseInt(split[1]);
                 }
                 cntr++;
              }}}
                 catch(IOException e)
                 {

                 }
                 
                        

      for(int i=0;i<100;i++)
       { 
         cs_enter();
	 Thread.sleep(1000);
         cs_leave();
      }
   }
}
