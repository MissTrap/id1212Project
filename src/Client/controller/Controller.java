//*Created by Sofia Söderström 2019-04-13*//

package Client.controller;
import Client.socketNetwork.HttpClient;

public class Controller extends Thread{
  private final HttpClient hc = new HttpClient();

  public void connect(){
    hc.HttpClient();
  }
  public void inputUser(){
    hc.inputFromUser();
  }
  public void run(){
    connect();
  }
}
