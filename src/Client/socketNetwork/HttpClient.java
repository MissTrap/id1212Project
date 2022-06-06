//*Created by Sofia Söderström 2019-04-13*//

package Client.socketNetwork;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpClient extends Thread{
  //init
  private Socket socket;
  private DataInputStream input;
  private DataOutputStream output;
  String out = ""; String inp = "";
  int rated; int len;
  String rFromS = ""; String that = ""; String that1 = "";

  //körs första gången
  public void HttpClient(){
    try{
      this.socket = new Socket("127.0.0.1", 8888); //localhost ipaddress and port 8888.
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
      System.out.println("Welcome to the Hangman game");
      System.out.println("You may at any time type 'quit' to end the game. \n You may guess on a single letter at a time.");
      rFromS = input.readUTF();
      String[] sub = rFromS.split("\\s+");
      len = sub.length;
      that = "";
      for(int i = 4; i < (len - 1); i++){
        that = that + " " + sub[i];
      }
      System.out.println(that + " | Attempts left: " + sub[1] + " | Score: " + sub[0]);
      start();
      Update();
    }
    catch(UnknownHostException e){e.printStackTrace();}
    catch(IOException e){e.printStackTrace();}
  }

  public void run(){
    inputFromUser();
  }

//Always waiting for new stuff from server for the view to print.
  public void Update(){
    try{
      input = new DataInputStream(socket.getInputStream());
      while(true){
        while(input.available() == 0){
          try{Thread.sleep(1);}
          catch(InterruptedException e){e.printStackTrace();}
        }
        rFromS = input.readUTF();
        String[] sub = rFromS.split("\\s+");
        len = sub.length;
        that = "";
        for(int i = 4; i < (len - 1); i++){
          that = that + " " + sub[i];
        }
        if(Integer.parseInt(sub[2]) == 1){
          System.out.println("You have no attempts left, so you lost! Score: " + sub[0]);
          System.out.println(that + " | Attempts left: " + sub[1] + " | Score: " + sub[0]);
        }
        else if(Integer.parseInt(sub[3]) == 1){
          System.out.println("You guessed correct! The word was " + sub[len-1] + " | Score: " + sub[0]);
          System.out.println(that + " | Attempts left: " + sub[1] + " | Score: " + sub[0]);
        }
        else{
          System.out.println(that + " | Attempts left: " + sub[1] + " | Score: " + sub[0]);
        }
      }
    }
    catch(IOException e){e.printStackTrace();}
  }

//Gets input from user. Always running.
  public void inputFromUser(){
    Scanner in = new Scanner(System.in);
    while(true){
      while(!in.hasNextLine()){
        try{Thread.sleep(1);}
        catch(InterruptedException e){e.printStackTrace();}
      }
      inp = in.nextLine();
      if(inp.equalsIgnoreCase("quit")){
        try{
          input.close();
          output.close();
          socket.close();
        }
        catch(IOException e){e.printStackTrace();}
        break;
      }
      rated = controll(inp);
      if(rated != 1){
        System.out.println("1 Unvalid input, did you try to hack us?! You may try to make a valid input.");
      }
      else{
        try{
          output.writeUTF(inp);
          output.flush();
        }
        catch(IOException e){e.printStackTrace();}
      }
    }
  }

  public int controll(String input){
    rated = 1;
    while(input.length() != 0){
      inp = input.substring(0, 1);
      input = input.substring(1);
      if(inp == ";" || inp == "." || inp == "(" || inp == ")"){
        rated = 0;
      }
    }
    return rated;
  }
}
