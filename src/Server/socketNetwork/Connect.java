//*Created by Sofia Söderström 2019-04-13*//

package Server.socketNetwork;
import Server.controller.Controller;

import java.net.*;
import java.io.*;
import java.util.*;

public class Connect{
  Controller controller;
  ServerSocket socket;
  ArrayList<Controller> clientConnections = new ArrayList<Controller>();

  public Connect(){
    try{
      socket = new ServerSocket(8888);
      while(true){
        controller = new Controller();
        Socket sock = socket.accept();
        controller.Controller(sock, this);
        clientConnections.add(controller);
    }}
    catch(IOException e){e.printStackTrace();}
  }

  public static void main(String[] args) {
    new Connect();
}}
