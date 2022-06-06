//*Created by Sofia Söderström 2019-04-13*//

package Client.view;
import Client.controller.Controller;

public class View extends Thread{
  Controller controller;

  public View(){
    views();
  }

  public void views(){
    controller = new Controller();
    controller.start();
    controller.inputUser();
  }

  public static void main(String[] args) {
    new View();
  }
}
