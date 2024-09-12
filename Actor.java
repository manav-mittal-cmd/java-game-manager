/*
    Name: Manav Mittal
    Date: Dec 08, 2022

    File Descripton:
    - Abstract class with info() method as the abstract method. Contains the attributes name and health. 
    - This is a supporting file to GameManager.java
*/

public abstract class Actor
{
    private String name;
    private int health;

    //Constructors
    // takes a string that is the name of this actor and sets health to a default of 100.
    Actor(String aName)
    {
        name = aName;
        health = 100;
    }

    // get methods
    public String getName()
    {
        return name;
    }
    public int getHealth()
    {
        return health;
    }

    // set methods
    public void setName(String aName)
    {
        name = aName;
    }
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }

    // abstract method to make the class abstract
    public abstract String info();

    // display() is meant to write formatted output to the screen to display the Actor information. 
    public void display()
    {
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
    }
}
