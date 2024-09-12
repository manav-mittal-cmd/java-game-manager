/*
    Name: Manav Mittal
    Date: Dec 08, 2021
    Assignment: CSC-221 Final

    File Descripton:
    - Monster Class inheriting from the abstract Actor class. 
    - Contains 1 attribute: type
    - contains necessary constructors, accessor and mutator methods, class's own version of info() and display(). 
    - This is a supporting file to GameManager.java
*/

public class Monster extends Actor
{
    private String type;
    
    // constructors
    Monster(String aName)
    {
        super(aName);
        type = "generic";
    }
    Monster(String aName, String aType)
    {
        super(aName);
        type = aType;
    }
    Monster(String aName, int aHealth, String aType)
    {
        super(aName);
        int health = getHealth();
        health = aHealth;
        type = aType;
    }

    // get method
    public String getType()
    {
        return type;
    }

    // set method
    public void setType(String aType)
    {
        type = aType;
    }

    // save monster information to a String for writing to a file.
    public String info()
    {
        String writeInfo;
        writeInfo = getName() + "," + getHealth() + "," + getType();
        return writeInfo;
    }

    // write formatted output to the screen to display the Monster information. 
    public void display()
    {
        super.display();
        System.out.println("Type: " + getType());
    }
}
