package spotilike;

import static javax.sound.sampled.AudioSystem.*;

import java.io.*;
import java.util.*;

import javax.sound.sampled.*;
import javax.swing.JOptionPane;

import org.json.simple.*;
import org.json.simple.parser.*;;;

public class Spotilike

{
  // global variables for the app
  String status;
  static Long position;
  static Clip audioClip;
  String filePath;
  String title;
  String artist;
  String year;
  static String genre;
  static boolean isFavorite;
  static String heartSong;

  // Declarar scanner static para usarlo en todos los metodos

  // READ JSON
  public static JSONArray readJSONArrayFile(String fileName) {

    // Read JSON and iterate on it

    /*
     * 
     * Disponemos del método «parse». Con este método, convertimos una cadena de
     * caracteres,
     * un String, en un dato numérico.
     * Lo podemos convertir en un número entero (int), sin decimales,
     * o en un número con decimales del tipo double o float, u otro.
     */

    JSONParser parser = new JSONParser();
    JSONArray pathList = null;

    // JSONArray songsList = null;

    try (FileReader reader = new FileReader(fileName)) {

      // Read JSON file
      Object obj = parser.parse(reader);
      pathList = (JSONArray) obj;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return pathList;
  }

  // Everything start here because of MAIN
  // Scanner for user input
  public static void main(final String[] args) throws IOException {

    Scanner input = new Scanner(System.in);

    // mantenerlo siempre activo
    String userInput = "";

    // whilse is diferent from "Q"
    while (!userInput.equals("q")) {

      menu();

      // get string input from scanner
      userInput = input.nextLine();
      // accept upper or lower case commands
      userInput = userInput.toLowerCase();

      // do something
      handleMenu(userInput);
    }

    // close the scanner
    input.close();

  }

  // MENU
  public static void menu() {

    // Title
    System.out.println("\n*** SPOTILIKE ***\n");

    // Options
    System.out.println("[H]ome");
    System.out.println("[S]earch by title");
    System.out.println("[L]ibrary");
    System.out.println("[P]lay");
    System.out.println("P[a]use");
    System.out.println("S[t]op");
    System.out.println("R[e]wind (5 seconds)");
    System.out.println("F[o]rwad (5 seconds)");
    System.out.println("[F]avorites");
    System.out.println("[R]eset");
    System.out.println("[Q]uit");

    System.out.println("");

  }

  // handles the user input for the app
  public static void handleMenu(String userInput) throws IOException {

    String jsonPath = "/Users/Edgar/Documents/GitHub/Spotilike1/src/part1/src/main/java/spotilike/Songs.json";

    // jsonData con el contenido del JSON file en Array
    JSONArray jsonData = readJSONArrayFile(jsonPath);
    JSONObject obj;

    switch (userInput) {

      // Recently played***
      case "h":
        System.out.println("   " + " --> Home <--\n");

        // SEARCH BY TITLE
      case "s":

        System.out.println("--> Search by Title <--");
        System.out.println("\nPlease enter a name song you wanna play");

        Scanner scan = new Scanner(System.in);
        String nameInputSong = scan.nextLine();

        String title;
        String artist;
        String year;
        String genre;

        String filePath;

        for (Integer i = 0; i < jsonData.size(); i++) {

          obj = (JSONObject) jsonData.get(i);

          title = (String) obj.get("title");
          artist = (String) obj.get("artist");
          year = (String) obj.get("year");
          genre = (String) obj.get("genre");
          filePath = (String) obj.get("filePath");

          // parse the object and pull out the name and filePath

          if (nameInputSong.equals(title)) {

            System.out.println(
                "\nPlaying: " + title + " by " + artist + " // " + year + " // " + genre + "\n"
                    + filePath + "\n");

            play(filePath);

            do {
              heartSong = JOptionPane.showInputDialog("Do you want to save this song as a favorite? (Y/N)");
            }

            while (heartSong.equalsIgnoreCase("Y") == false && heartSong.equalsIgnoreCase("N") == false);
            if (heartSong.equalsIgnoreCase("Y")) {

              JOptionPane.showMessageDialog(null, "Song saved as favorite");

              // save the song in some place IDK

            } else if (heartSong.equalsIgnoreCase("N")) {

              JOptionPane.showMessageDialog(null, "OK, this song won't be save in your favorite playlist");

            }

          }

        }
        break;

      case "l":

        System.out.println(" --> Library <--\n");
        System.out.println("Select a number to play.\n1.- Ghost Town by Arcando & ThatBehavior (2016)\n" +
            "2.- Tanzen by Checkie Brown (2017)\n3.- Espiral by Electronautae (2013)\n4.- Golden by The Empire (2016)\n"
            +
            "5.- Mujer Karateka by Electronautae (2013)\n"
            + "6.- Danzon by Jimena Contreras (2015)\n7.- Pasillo de Espejos by Electronautae (2013)\n" +
            "8.- Play Dead by NEFFEZ (2014)\n9.- Storybook by Scott Holmes (2019)\n10.-Vaquero Galactico by Electronautae (2013)\n");

        Scanner scann = new Scanner(System.in);
        int optionNU = scann.nextInt();
        Integer i = optionNU - 1;

        obj = (JSONObject) jsonData.get(i);

        title = (String) obj.get("title");
        artist = (String) obj.get("artist");
        year = (String) obj.get("year");
        genre = (String) obj.get("genre");
        ////////////////// isFavorite = (Boolean) obj.get(obj);
        filePath = (String) obj.get("filePath");

        // if (isFavorite == false) { System.out.println("el valor es falso bro");}

        System.out
            .println("\nPlaying: " + title + " by " + artist + " // " + year + " // " +
                genre + "\n" + filePath + "\n");

        // play always starts from the beggining
        play(filePath);

        do {
          heartSong = JOptionPane.showInputDialog("Do you want to save this song as a favorite? (Y/N)");
        }

        while (heartSong.equalsIgnoreCase("Y") == false && heartSong.equalsIgnoreCase("N") == false);
        if (heartSong.equalsIgnoreCase("Y")) {

          JOptionPane.showMessageDialog(null, "Song saved as favorite");

          // save the song in some place IDK

        } else if (heartSong.equalsIgnoreCase("N")) {

          JOptionPane.showMessageDialog(null, "OK, this song won't be save in your favorite playlist");

        }

      case "p":
        audioClip.start();
        break;

      // PAUSE
      case "a":
        audioClip.getMicrosecondPosition();
        audioClip.stop();
        break;

      case "t":
        audioClip.stop();
        audioClip.setMicrosecondPosition(0);
        break;

      case "e":
        position = audioClip.getMicrosecondPosition();
        audioClip.setMicrosecondPosition(position - 5000000);
        break;

      // ADELANTAR
      case "o":
        position = audioClip.getMicrosecondPosition();
        audioClip.setMicrosecondPosition(position + 5000000);
        break;

      case "f":
        String favorite = "";

        FileReader file = new FileReader(
            "/Users/Edgar/Documents/GitHub/Spotilike2/src/version2/src/main/java/spotilike/Songs.json");
        int ch;
        while ((ch = file.read()) != -1)
          favorite += (char) ch;

        System.out.println(favorite);

        break;

      case "r":
        audioClip.setMicrosecondPosition(0);
        break;

      case "q":

        System.out.println("--> Bye Space Cowboy! <--");
        break;

      default:
        System.out.println("\nOption No valid.\n" + "Please, try again.");

        break;
    }
  }

  // plays an audio file
  public static void play(String filePath) {

    final File fileHandle = new File(filePath);

    try {

      // create clip
      audioClip = AudioSystem.getClip();

      // get input stream
      final AudioInputStream in = getAudioInputStream(fileHandle);

      // play the audio
      audioClip.open(in);
      audioClip.setMicrosecondPosition(0);
      audioClip.loop(Clip.LOOP_CONTINUOUSLY);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();

    }

  }

}
