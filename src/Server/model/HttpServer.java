//*Created by Sofia Söderström 2019-04-13*//

package Server.model;
import Server.socketNetwork.Connect;

import java.io.*;
import java.net.*;

public class HttpServer extends Thread{
  RequestHandler rh = new RequestHandler();
  //init
  Socket socket;
  Connect server;
  DataInputStream in;
  DataOutputStream output;
  String text = "";
  String inputFromServer = "";

  public HttpServer(Socket socket, Connect server){
    System.out.println("Server has init");
    this.server = server;
    this.socket = socket;
  }

  public void run(){
    theGame();
  }

//Always running waiting for new input from player/sends new states.
  public void theGame(){
    try{
      in = new DataInputStream(socket.getInputStream());
      output =  new DataOutputStream(socket.getOutputStream());
      System.out.println("Server start new game");
      inputFromServer = rh.RequestHandler();
      output.writeUTF(inputFromServer);
      output.flush();
      while (true){
        while(in.available() == 0){
            try{Thread.sleep(1);}
            catch(InterruptedException e){e.printStackTrace();}
        }
        text = in.readUTF();
        inputFromServer = rh.next(text);
        output.writeUTF(inputFromServer);
        output.flush();
    }}
    catch(IOException e){e.printStackTrace();}
}}
