/*
    Name: Manav Mittal
    Date: Dec 08, 2021
    Assignment: CSC-221 Final

    File Descripton:
    - Player Class inheriting from the abstract Actor class. 
    - Contains 2 attributes: score and noLives
    - contains necessary constructors, accessor and mutator methods, class's own version of info() and display(). 
    - This is a supporting file to GameManager.java
*/

public class Player extends Actor
{
    private int score;
    private int noLives;

    // Deafult constructor
    Player(String aName)
    {
        super(aName);
        score = 0;
        noLives = 10;
    } 
    // constructor with name and number of lives
    Player(String aName, int lives)
    {
        super(aName);
        score = 0; 
        noLives = lives;
    }
    // sets all values for reading a file to resume a game.
    Player(String aName, int aHealth, int aScore, int lives)
    {
        super(aName);
        int health = getHealth();
        health = aHealth;
        score = aScore;
        noLives = lives;
    }
    
    // get methods
    public int getScore()
    {
        return score; 
    }
    public int getLives()
    {
        return noLives;
    }

    // set methods
    public void setScore(int aScore)
    {
        score = aScore; 
    }
    public void setLives(int lives)
    {
        noLives = lives;
    }

    // method to write info in a manner that it can be used to output player info to a file.
    public String info()
    {
        String writeInfo;
        writeInfo = getScore() + "," + getLives();
        return writeInfo;
    }

    // method to check whether the donor player has more than 1 lives and if the receieving player has less than 10 lives.
    public boolean giveLife(Player aPlayer)
    {
        boolean canGiveLife;
        if (this.getLives() <= 1)
        {
            canGiveLife = false;
        }
        else if (aPlayer.getLives() >= 10)
        {
            canGiveLife = false;
        }
        else
        {
            canGiveLife = true;
        }
        return canGiveLife;
    }

    // write formatted output to the screen to display the Player information. 
    public void display()
    {
        super.display();
        System.out.println("Score: " + getScore());
        System.out.println("Number of Lives: " + getLives());
    }
}
