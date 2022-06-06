//*Created by Sofia Söderström 2019-04-13*//

package Server.controller;
import Server.socketNetwork.Connect;
import Server.model.HttpServer;

import java.net.*;

public class Controller{
  public void Controller(Socket socket, Connect server){
    HttpServer hs = new HttpServer(socket, server);
    hs.start();
}}
