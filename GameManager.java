/*
    Name: Manav Mittal
    Date: Dec 08, 2021
    Assignment: CSC-221 Final

    File Descripton:
    This program acts as a blueprint to a game. It has several static methods that contain different menus and functions that the user can do. 
*/

// importing different packages for exception handling, file creation, scanning, etc. 
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
class GameManager
{
    // create one scanner to recieve all user inputs. 
    public static final Scanner KB = new Scanner(System.in);

    // create arraylist for players and monsters each to store the players and monsters the user adds. 
    public static ArrayList<Player> players = new ArrayList<Player>();
    public static ArrayList<Monster> monsters = new ArrayList<Monster>();

    // universal variable used to store file name 
    public static String fileName = "";

    // a variable used in some of the while loops within the methods. Defined here in order for it to have scope within multiple methods. 
    public static String tryAgain = "";

    public static void main(String[] args) 
    {
        // display the option to whether start a new game or continue.
        startScreen();

        // display main menu with four options and send user to next menu based on what option is selected.
        // most methods call different menu methods in order to keep the flow of the program. 
        mainMenu();

        // close gracefully in case one of the exit methods fail to do so. 
        KB.close();
        System.exit(0);
    }

    // method to ask user which file they want to use to save their data to. 
    public static void startScreen()
    {
        boolean a = false;
        while(!a)
        {
            // present options to the user 
            System.out.println("\nPlease select the option desired by entering the correct character");
            System.out.println("\tN)EW Game?");
            System.out.println("\tR)ESUME Game?");
            System.out.println("\tE)XIT Game without Saving?");

            // store user's answer into a string variable and then use if statements to determine what the user entered.
            String userInput = KB.nextLine().toLowerCase();

            if (userInput.equals("n"))
            {
                // get file name from user.
                System.out.println("Please enter file name: ");
                fileName = KB.nextLine();

                // set "a" to true in order to break the loop and move onto the main menu.
                a = true;
            }
            else if(userInput.equals("r"))
            {
                // call method that reads the file data, sorts the information into the player and monster ArrayLists. 
                readFile();

                // set "a" to true in order to break the loop and move onto the main menu.
                a = true;
            }
            else if(userInput.equals("e")) 
            {
                // end program if user wants to quit without saving.
                System.exit(0);
            }
            else
            {
                // tell user their answers were invalid and then the while loop with ask them to re-answer.
                System.out.println("invalid input, Please try again!"); 
            }
        }
    }
    
    // method to display the main menu. Will make the "main" method very short. 
    public static void mainMenu()
    {
        // loop to keep the menu going when an invalid answer is given
        boolean a = true;
        while(a)
        {
            // print menu options
            System.out.println("\n\nMAIN MENU - Please select an Option:");
            System.out.println("\tP)layer options - add, edit, give a life, view");
            System.out.println("\tM)onster - add, edit, view");
            System.out.println("\tS)ave game & Exit");
            System.out.println("\tE)xit game without saving");
            System.out.println();

            String userInput = KB.nextLine().toLowerCase();

            // switch statement to send user to the next menu based on their answers
            switch(userInput)
            {
                case "p":
                    playerMenu();
                    break;
                case "m":
                    monsterMenu();
                    break;
                case "s":
                    exitAndSave();
                    break;
                case "e":
                    System.exit(0); // immediately end program if user wants to quit without saving. 
                    break;
                default:
                    tryAgain = "y";
                    System.out.println("Invalid input, Try again!");
            }
            // if statement to check if the program reached the default case or if it broke from the switch statement before that
            // will keep while loop going if invalid input was entered and end the loop if it was valid.
            if(tryAgain.equals("y"))
            {
                tryAgain = "n";
                a = true;
            }
            else
            {
                a = false;
            }
        }
    }
    
    // method to display the player menu
    public static void playerMenu()
    {
        boolean a = true;
        while(a)
        {
            // display options
            System.out.println("\n\nPLAYER MENU - Please select an Option:");   
            System.out.println("\tA)dd Player");
            System.out.println("\tE)dit Player");
            System.out.println("\tG)ive a Life");
            System.out.println("\tD)elete Player");
            System.out.println("\tV)iew all Players");
            System.out.println("\tB)ack to Main");

            String userInput = KB.nextLine().toLowerCase();
            switch(userInput)
            {
                case "a":
                    addPlayer();
                    break;
                case "e": 
                    modifyPlayer();
                    break;
                case "g":
                    giveALife();
                    break;
                case "d":
                    deletePlayer();
                    break;
                case "v":
                    viewAllPlayers();
                    break;
                case "b":
                    mainMenu(); 
                    break;
                default:
                    tryAgain = "y";
                    System.out.println("Invalid Input, Try Again!");
            }
            if(tryAgain.equals("y"))
            {
                tryAgain = "n";
                a = true;
            }
            else
            {
                a = false;
            }
        }
    }
    
    // method to display the monster menu.
    public static void monsterMenu()
    {
        // display options
        System.out.println("\n\nMONSTER MENU - Please select an Option:");
        System.out.println("\tA)dd Monster");
        System.out.println("\tE)dit Monster");
        System.out.println("\tD)elete Monster");
        System.out.println("\tV)iew all Monsters");
        System.out.println("\tB)ack to Main");
        System.out.println();

        boolean a = true;
        while(a)
        {
            String userInput = KB.nextLine().toLowerCase();
            switch(userInput)
            {
                case "a":
                    addMonster();
                    break;
                case "e": 
                    modifyMonster();
                    break;
                case "d":
                    deleteMonster();
                    break;
                case "v":
                    viewAllMonsters();
                case "b":
                    mainMenu();
                    break;
                default:
                    tryAgain = "y";
                    System.out.println("Invalid Input, Try Again!");
            }   
            if(tryAgain.equals("y"))
            {
                tryAgain = "n";
                a = true;
            }
            else
            {
                a = false;
            }
        }
    }

    // method to create a new player based on what name the user gives. Then add the new player to the player ArrayList. 
    public static void addPlayer()
    {
        // declaring variables that will store user input
        String playerName;
        int playerLives;

        // prompt user to enter player name
        System.out.println("Please enter the name of the player to add: ");
        playerName = KB.nextLine();

        boolean done = false;
        while(!done)
        {
            // use try...catch block in order to deal with any exceptions that may occur.
            try
            {
                // prompt user to enter number of player lives between 1 and 10, and check for any other entries. 
                System.out.println("Please enter how many lives this player has, 1-10:");
                playerLives = KB.nextInt();

                if (playerLives < 0 || playerLives > 10)
                    throw new OutOfBoundsException(); // throw custom error if entry is not between 1 and 10.
                
                // create new player based on entered info and add it to the player ArrayList.
                Player player = new Player(playerName, playerLives); 
                players.add(player);
                done = true;
            }
            catch (OutOfBoundsException oobe)
            {
                System.out.println("Entry must be between 1-10!");
                KB.nextLine();
            }
            catch (InputMismatchException ime) // throws this exception when anything other than an integer is entered.
            {
                System.out.println("Entry must be an integer between 1-10 !");
                KB.nextLine();
            }
        }
        KB.nextLine(); // clear for further input
        // call playerMenu since this function is over. 
        playerMenu();
    }

    // method to create a new monster and add to the monster ArrayList. 
    public static void addMonster()
    {
        // ask the name of the monster
        System.out.println("Please enter monster name: ");
        String monsterName = KB.nextLine();

        // ask for what type of monster the user wants to create
        System.out.println("Please enter the 'type' for this monster:");
        String monsterType = KB.nextLine();

        // create new monster and add it to monster ArrayList
        Monster monster = new Monster(monsterName, monsterType);
        monsters.add(monster);

        monsterMenu();
    }
    
    // method to modify a player the user has previously created. 
    public static void modifyPlayer()
    {
        boolean done = false;
        while(!done)
        {
            try
            {
                // check if there are any players in the list. If there are not any players then, send user back to player menu.
                if (players.size() == 0)
                {
                    System.out.println("There are no players to modify. Please go back to the player menu and add more players.");
                    done = true;
                }
                else
                {
                    // loop through the ArrayList and display the current players in the list along with their indices so that it is easier for the user to enter which player they want to modify. 
                    System.out.println("Please enter the number for the Player you wish to modify: ");
                    for(int i = 0; i < players.size(); i++)
                    {
                        System.out.println("\t" + i + " - " + (players.get(i).getName()));
                    }
                    int playerNo = KB.nextInt();

                    if (playerNo+1 > players.size() || playerNo < 0) // check whether input is one of the options from the list
                        throw new OutOfBoundsException();

                    editPlayerMenu(playerNo);
                    done = true;
                }
            }
            catch (OutOfBoundsException oobe)
            {
                System.out.println("Enter a number from within the list.");
            }
            catch (IndexOutOfBoundsException ioobe)
            {
                System.out.println("You selected a player number outside the possible range. Please try again.");
            }
            catch (InputMismatchException ime)
            {
                System.out.println("Please enter an integer!");
                KB.nextLine();
            }
            catch (Exception e) // in case there are any other unhandled exceptions
            {
                System.out.println(e.toString());
            }
        }
        playerMenu();
    }
    
    // method to ask user what feature to edit in the player
    public static void editPlayerMenu(int playerNo)
    {
        KB.nextLine(); // clear input for when this function is called. 
        boolean d = false;
        while(!d)
        {
            System.out.println("\n\nEDIT PLAYER - select what to edit:");
            System.out.print("\tN)ame\n\tH)ealth\n\tS)core\n\tL)ives\n\tR)eturn to Player menu");
            System.out.println();

            String userInput = KB.nextLine().toLowerCase();
            switch(userInput)
            {
                case "n":
                    System.out.println("Please enter the new NAME for this Player: ");
                    players.get(playerNo).setName(KB.nextLine());
                    break;
                case "h":
                    System.out.println("Please enter the new HEALTH for this Player: ");
                    players.get(playerNo).setHealth(KB.nextInt());
                    KB.nextLine();
                    break;
                case "s":
                    System.out.println("Please enter the new SCORE for this Player: ");
                    players.get(playerNo).setScore(KB.nextInt());
                    KB.nextLine();
                    break;
                case "l":
                    System.out.println("Please enter the new number of LIVES for this Player: ");
                    players.get(playerNo).setLives(KB.nextInt());
                    KB.nextLine();
                    break;
                case "r":
                    // break through the loop
                    // program will return to modifyPlayer() from where it will go to main menu
                    d = true;
                    break;
                default:
                    System.out.println("Invalid Input, Try Again!");
            }
        } 
    }

    // method to modify monsters. 
    // same was modifyPlayer() except "player" is changed to "monster"
    public static void modifyMonster()
    {
        boolean done = false;
        while(!done)
        {
            try
            {
                if (monsters.size() == 0)
                {
                    System.out.println("There are no monsters to modify. Please go back to the monster menu and add more monsters.");
                    done = true;
                }
                else
                {
                    System.out.println("Please enter the number for the Monster you wish to modify: ");
                    for(int i = 0; i < monsters.size(); i++)
                    {
                        System.out.println("\t" + i + " - " + (monsters.get(i).getName()));
                    }
                    int monsterNo = KB.nextInt();

                    if (monsterNo+1 > monsters.size() || monsterNo < 0)
                        throw new OutOfBoundsException();

                    editMonsterMenu(monsterNo);
                    done = true;
                }
            }
            catch (OutOfBoundsException oobe)
            {
                System.out.println("Enter a number from within the list.");
            }
            catch (IndexOutOfBoundsException ioobe)
            {
                System.out.println("You selected a monster number outside the possible range. Please try again.");
            }
            catch (InputMismatchException ime)
            {
                System.out.println("Please enter an integer!");
                KB.nextLine();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        monsterMenu();
    }

    // method to display an edit monster menu when the user chooses to edit their monsters.
    // similar method to editPlayerMenu(). All "player" mentions are changed to "monster".
    public static void editMonsterMenu(int monsterNo)
    {
        KB.nextLine(); // clear input for when this function is called. 
        boolean d = false;
        while(!d)
        {
            System.out.println("\n\nEDIT MONSTER - select what to edit:");
            System.out.print("\tN)ame\n\tH)ealth\n\tT)ype\n\tB)ack to Monster menu");
            System.out.println();

            String userInput = KB.nextLine().toLowerCase();
            switch(userInput)
            {
                case "n":
                    System.out.println("Please enter the new NAME for this Monster: ");
                    monsters.get(monsterNo).setName(KB.nextLine());
                    break;
                case "h":
                    System.out.println("Please enter the new HEALTH for this Monster: ");
                    monsters.get(monsterNo).setHealth(KB.nextInt());
                    KB.nextLine();
                    break;
                case "t":
                    System.out.println("Please enter the new TYPE for this Monster: ");
                    monsters.get(monsterNo).setType(KB.nextLine());
                    break;
                case "b":
                    // break through this loop
                    // this method is called in modifyMonster() and therefore the program execution will continue in that method.
                    d = true;
                    break;
                default:
                    System.out.println("Invalid Input, Try Again!");
            }
        } 
    }

    // method to display all players upon user's request. 
    public static void viewAllPlayers()
    {
        System.out.println("\n\nPlayer list: ");
       
        // loop through the player arraylist and print the info for each player using .display from the Player class
        for(int i = 0; i < players.size(); i++)
        {
            System.out.println("\n***** Player " + (i+1) +  " Info ****");
            players.get(i).display();
        }
        System.out.println("\nEnd of Player list");

        // go back to player menu
        playerMenu();
    }

    // method to display all monsters
    public static void viewAllMonsters()
    {
        System.out.println("\n\nMonster list: ");
       
        for(int i = 0; i < monsters.size(); i++)
        {
            System.out.println("\n***** Monster " + (i+1) +  " Info ****");
            monsters.get(i).display();
        }
        monsterMenu();
    }

    // method to ask user to input the name of the player they want to delete. The method then removes the player from the players ArrayList.
    // method is very similar to modify player
    public static void deletePlayer()
    {
        // set loop in place in case of any invalid input
        boolean done = false;
        while(!done)
        {
            try
            {
                // checking if there are any players within the arraylist. If not, then send the user back to the player menu.
                if (players.size() == 0)
                {
                    System.out.println("There are no players to delete.");
                    done = true;
                }
                else
                {
                    // loop through the player arraylist and print it with the indices.
                    System.out.println("Please enter the number for the Player you wish to delete: ");
                    for(int i = 0; i < players.size(); i++)
                    {
                        System.out.println("\t" + i + " - " + (players.get(i).getName()));
                    }
                    int toDelete = KB.nextInt();

                    if (toDelete+1 > players.size() || toDelete < 0)
                        throw new OutOfBoundsException();

                    // remove player from the player arraylst.
                    players.remove(toDelete);
                    KB.nextLine();
                    done = true; // break through the loop.
                }
            }
            catch (OutOfBoundsException oobe)
            {
                System.out.println("Enter a number from within the list.");
            }
            catch (IndexOutOfBoundsException ioobe)
            {
                System.out.println("You selected a player number outside the possible range. Please try again.");
            }
            catch (InputMismatchException ime)
            {
                System.out.println("Please enter an integer!");
                KB.nextLine();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        playerMenu();
    }

    // method to ask user to input the name of the monster they want to delete. The method then removes the monster from the monsters ArrayList.
    // method is same as deletePlayer() except "player" is changed to "monster"
    public static void deleteMonster()
    {
        boolean done = false;
        while(!done)
        {
            try
            {
                // checking if there are any players within the arraylist. If not, then send the user back to the player menu.
                if (monsters.size() == 0)
                {
                    System.out.println("There are no monsters to delete.");
                    done = true;
                }
                else
                {
                    System.out.println("Please enter the number for the Monster you wish to delete: ");
                    for(int i = 0; i < monsters.size(); i++)
                    {
                        System.out.println("\t" + i + " - " + (monsters.get(i).getName()));
                    }
                    int toDelete = KB.nextInt();

                    if (toDelete+1 > monsters.size() || toDelete < 0)
                        throw new OutOfBoundsException();

                    // remove monster from the monster arraylst.
                    monsters.remove(toDelete);
                    KB.nextLine();
                    done = true;
                }
            }
            catch (OutOfBoundsException oobe)
            {
                System.out.println("Enter a number from within the list.");
            }
            catch (IndexOutOfBoundsException ioobe) // this exception occurred occasionally and so is being addressed so that it does not impede the flow of the program.
            {
                System.out.println("You selected a monster number outside the possible range. Please try again.");
            }
            catch (InputMismatchException ime)
            {
                System.out.println("Please enter an integer!");
                KB.nextLine();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        monsterMenu(); // go back to monster menu since this task is over. 
    }

    // method to tranfer a life from one player to another. One player's life is decremented while the other player's life is incremented. 
    public static void giveALife()
    {
        boolean done = false;
        while(!done)
        {
            try
            {
                // checking if there are any players within the arraylist. If not, then send the user back to the player menu.
                if (players.size() == 1)
                {
                    System.out.println("There are less than 2 players. Please add more players from the Player Menu in order to transfer lives.");
                    done = true;
                }
                else
                {
                    // get first player
                    System.out.println("*** Player that GIVES a life!");
                    System.out.println("Please enter the number for the player you want:");
                    for(int i = 0; i < players.size(); i++)
                    {
                        System.out.println("\t" + i + " - " + (players.get(i).getName()));
                    }
                    int donorPlayer = KB.nextInt();

                    if (donorPlayer+1 > players.size() || donorPlayer < 0)
                        throw new OutOfBoundsException();

                    // get second player
                    System.out.println("*** Player that RECIEVES a life!");
                    System.out.println("Please enter the number for the player you want:");
                    for(int i = 0; i < players.size(); i++)
                    {
                        System.out.println("\t" + i + " - " + (players.get(i).getName()));
                    }
                    int recievingPlayer = KB.nextInt();

                    // check if user can use both players to donate a life
                    if(players.get(donorPlayer).giveLife(players.get(recievingPlayer)))
                    {
                        // can give a life, then . . .
                        // remove one life from donor player
                        players.get(donorPlayer).setLives(players.get(donorPlayer).getLives() - 1);

                        // add one life to the other player
                        players.get(recievingPlayer).setLives(players.get(recievingPlayer).getLives() + 1);
                    }
                    else
                    {
                        System.out.println("Either donating player has less than or equal to 1 life or receiving player has more than or equal to 10.\nCannot donate lives. Please choose another player.");
                    }
                    // clear input and break out of loop. 
                    KB.nextLine();
                    done = true;
                }
            }
            catch (OutOfBoundsException oobe)
            {
                System.out.println("Enter a number from within the list.");
            }
            catch (IndexOutOfBoundsException ioobe)
            {
                System.out.println("You selected a player number outside the possible range. Please try again.");
            }
            catch (InputMismatchException ime)
            {
                System.out.println("Please enter an integer!");
                KB.nextLine();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        playerMenu();
    }

    // create method to write data onto the file once the player exits and saves. 
    public static void exitAndSave()
    {
        // create a path with the desired name.
        Path out = Paths.get(fileName);

        try
        {
            // create a variable that acts as a counter for how many names have been accounted for. 
            int noPlayers = 0;

            // create new output stream object.
            // CREATE will create a file if it does not exist
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(out, CREATE));
            
            // create a new actor ArrayList that will contain both players and monsters. 
            ArrayList<Actor> names = new ArrayList<Actor>();

            for (int i = 0; i < players.size(); i++)
            {
                names.add(players.get(i));
            }
            for (int i = 0; i < monsters.size(); i++)
            {
                names.add(monsters.get(i));
            }
            for (int i = 0; i < names.size(); i++)
            {
                String info1;
                String info2;
                String info3;

                // create temporary players in order to compare classes to sort out the Actor arraylist
                Player tempPlayer = new Player("temp");
                Monster tempMonster = new Monster("temp");
                if (names.get(i).getClass().equals(tempPlayer.getClass()))
                {
                    // String variables that contain what to write to the file. 
                    info1 = "p" + ",";
                    info3 = "" + players.get(i).getScore() + "," + players.get(i).getLives() + "\n";
                    noPlayers++;
                }
                else if (names.get(i).getClass().equals(tempMonster.getClass()))
                {
                    info1 = "m" + ",";
                    info3 = "" + monsters.get(i - noPlayers).getType() + "\n";
                }
                else
                {
                    throw new Exception(); // just in case there are any exceptions during execution
                }

                info2 = names.get(i).getName() + "," + names.get(i).getHealth() + "," ;

                String totalInfo = info1 + info2 + info3; // combine all parts 

                byte[] data = totalInfo.getBytes(); // convert to bytes 
                output.write(data); // write the array of bites to file 
            }
            // gracefully close 
            output.flush();
            output.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            System.out.println("Please try again");
            mainMenu();
        }
        System.exit(0); // end program since the user wanted to "save and exit".
    }
    
    // create method that reads data from a file and puts it into an array. 
    public static void readFile()
    {
        // get name from user and create path
        System.out.println("Please enter file name: ");
        fileName = KB.nextLine(); 
        Path in = Paths.get(fileName);

        if (Files.exists(in))
        {
            try
            {
                // create new input stream object.
                InputStream input = Files.newInputStream(in);

                // create a reader object in order to read the stream. 
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String s = null;
                while ((s = reader.readLine()) != null)
                {
                    // copy all data from the file into an array with elements separated by commas. 
                    String actorInfo[] = s.split(",");

                    // check if the first string is a "p" or "m". "p" represents players and "m" represents monsters.
                    if (actorInfo[0].equals("p")) 
                    {
                        try
                        {
                            String playerName = actorInfo[1];
                            int playerHealth = Integer.parseInt(actorInfo[2]);
                            int playerScore = Integer.parseInt(actorInfo[3]);
                            int playerLives = Integer.parseInt(actorInfo[4]);

                            // create new player based on info from file and add it to player ArrayList
                            Player player = new Player(playerName, playerHealth, playerScore, playerLives); 
            
                            players.add(player);
                        }
                        catch (NumberFormatException nfe) // need this due to Integer.parseInt() potentially having a string that cannot be converted to a number.
                        {
                            System.out.println("An error occurred while reading the file provided. Will have to create new file.");
                        }
                    }
                    // repeat above process for monsters.
                    else if (actorInfo[0].equals("m"))
                    {
                        try
                        {
                            String monsterName = actorInfo[1];
                            int monsterHealth = Integer.parseInt(actorInfo[2]);
                            String monsterType = actorInfo[3];
                        
                            Monster monster = new Monster(monsterName, monsterHealth, monsterType);
                
                            monsters.add(monster);
                        }
                        catch(NumberFormatException nfe)
                        {
                            System.out.println("An error occurred while reading the file provided. Will have to create new file.");
                        }
                    }
                }
                reader.close(); // close gracefully 
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        else // in case the file name entered does not exist, prompt user to enter the name of a new file. 
        {
            System.out.println("File does not exist. Please create a new file.");
            System.out.println("Please enter file name: ");
            fileName = KB.nextLine();
        }
    }
    
}

// create a custom child class of Exception in order to catch a specific error that occurs while adding a player. 
// Constructor used in addPlayer() method.
class OutOfBoundsException extends Exception 
{
    // default constructor, throws a message
    public OutOfBoundsException() 
    {
        super("Input must be a number between 1 ans 10!");
    }
    // another contructor which throws whatever message we pass into it.
    public OutOfBoundsException(String strMessage)  
    {
        super(strMessage);
    }
}

