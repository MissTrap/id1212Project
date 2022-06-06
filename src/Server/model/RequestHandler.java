//*Created by Sofia Söderström 2019-04-13*//

package Server.model;

import java.io.*;
import java.util.*;

public class RequestHandler extends Thread{

  //Variables init
  String toClient = "";
  String out = ""; String out2 = ""; String theWord = ""; String theState = ""; String guess = "";
  String aWord = ""; String temp = ""; String endOfString = ""; String stateword = "";
  String bWord = "";
  int attempts = 0; int score = 0; int i = 0; int randomNumber = 0;
  int max = 51528; int min = 1; int ran = 0;
  Random rand;

  //called when new client.
  public String RequestHandler(){return state1();}

  //Called when client has given a input, will return a String of next state.
  public String next(String input){return state(input);}

  //generates a new word to the variable theWord.
  public void newWord(){
    try{
      FileReader newW = new FileReader(new File("words.txt"));
      BufferedReader newWord = new BufferedReader(newW);
      randomNumber = rando();
      for(i = 1; i < randomNumber; i++){
        aWord = newWord.readLine();
      }
      theWord = newWord.readLine();
      newWord.close();
      newW.close();
    }
    catch(IOException e){e.printStackTrace();}
    System.out.println("The word is now: " + theWord);
  }

  //Runs only when new client. Score = 0.
  public String state1(){
    newWord();
    out = "_";
    for(i = 1; i < theWord.length(); i++){
      out = out + " _";
    }
    attempts = theWord.length();
    score = 0;
    out2 = out + " | Attempts left: " + attempts + " | Score: " + score;
    theState = out2;
    toClient = score + " " + attempts + " " + 0 + " " + 0 + " " + out + " " + theWord;
    return toClient;
  }

  //Runs when player is done with the last word, right or wrong. Score may be different from 0.
  public void state2(){
    newWord();
    out = "_";
    for(i = 1; i < theWord.length(); i++){
      out = out + " _";
    }
    attempts = theWord.length()+1;
    out2 = out + " | Attempts left: " + attempts + " | Score: " + score;
    theState = out2;
  }

  //Runs when the client has made a guess of either a letter or the entire word. Gives back a string of the next state.
  public String state(String guess){
    String[] sub = theState.split("\\s+");
    //if(checkIfCorrect(guess)){ //Does not work despite multiple tries to fix this.
    //  out = "You guessed correct! The word was " + guess + " | Score: ";
    //  score = Integer.parseInt(sub[sub.length-1]) + 1;
    //  out = out + score;
    //  theState = out;
    //  return out;
    //}
    if(partOfWord(guess)){
      for(i = 0; i < theWord.length(); i++){
        if(Character.toLowerCase(theWord.charAt(i)) == Character.toLowerCase(guess.charAt(0))){
          sub[i] = guess;
      }}
      stateword = "";
      for(i = 0; i < theWord.length(); i++){
        stateword = stateword + sub[i];
      }
      if(stateword.equalsIgnoreCase(theWord)){
        score++;
        out = "You guessed correct! The word was " + theWord + " | Score: " + score;
        bWord = theWord;
        theState = out;
        state2();
        sub = theState.split("\\s+");
        temp = "_";
        for(i = 1; i < theWord.length(); i++){
          temp = temp + " _";
        }
        toClient = score + " " + attempts + " " + 0 + " " + 1 + " " + temp + " " + bWord;
        return toClient;
      }
      else{
        out = sub[0];
        for(i = 1; i < sub.length; i++){
          out = out + " " + sub[i];
      }}
      theState = out;
      sub = theState.split("\\s+");
      temp = sub[0];
      for(i = 1; i < theWord.length(); i++){
        temp = temp + " " + sub[i];
      }
      toClient = score + " " + attempts + " " + 0 + " " + 0 + " " + temp + " " + theWord;
      return toClient;
    }
    else{
      endOfString = " | Score: ";
      attempts--;
      if(attempts == 0){
        score--;
        out = "You have no attempts left, so you lost! Score: " + score;
        theState = out;
        state2();
        sub = theState.split("\\s+");
        temp = "_";
        for(i = 1; i < theWord.length(); i++){
          temp = temp + " _";
        }
        toClient = score + " " + attempts + " " + 1 + " " + 0 + " " + temp + " " + theWord;
        return toClient;
      }
      else{
        for(i = 0; i < (sub.length - 4); i++){
          out = out + sub[i] + " ";
        }
        out = out + attempts;
        out = out + endOfString + score;
        theState = out;
        sub = theState.split("\\s+");
        temp = sub[0];
        for(i = 1; i < theWord.length(); i++){
          temp = temp + " " + sub[i];
        }
        toClient = score + " " + attempts + " " + 0 + " " + 0 + " " + temp + " " + theWord;
        return toClient;
  }}}

  //Function used when generating a new word, this only makes a random number.
  public int rando(){
    rand = new Random();
    ran = rand.nextInt((max - min) +1) + min;
    return ran;
  }

  //Boolean function, finds out if the guess is a correct letter of theWord.
  public boolean partOfWord(String guess){
    if(guess.length() == 1){
      for(i = 0; i < theWord.length(); i++){
        if(Character.toLowerCase(guess.charAt(0)) == Character.toLowerCase(theWord.charAt(i))){
          return true;
    }}}
    return false;
  }

  //Boolean function, finds out if the guess is in fact theWord. This one never seem to work!
  //public boolean checkIfCorrect(String guess){
  //  for(i = 0; i < theWord.length(); i++){
  //    if(!(Character.toLowerCase(guess.charAt(i)) == Character.toLowerCase(theWord.charAt(i)))){
  //      return false;
  //  }}
  //  return true;
  //}
}
