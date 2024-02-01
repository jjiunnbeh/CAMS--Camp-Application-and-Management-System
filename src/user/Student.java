package user;

import java.io.IOException;
import java.util.List;


/**
 * Represent a Student in NTU
 * The Student class inherited from User Class
 * contains the attributes of the student. 
 * contains the getter and setter for student class 
 * 1 student can only be a committee member for 1 camp
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class Student extends User {
    private String commitCamp;
    private int points;
    private boolean isCommittee;

    
    /**
 * Consructor class to create the Student object
 * @param ID of the student, retrieved from their email before the @ symbol
 * @param name of the Student
 * @param faculty the Faculty of the Student
 * @param password of the Student
 * @param commitCamp name of the camp the student registered as committee for
 * @param points, the points from replying to queries
 */
    public Student(String ID, String name, String faculty, String password, String commitCamp, int points) {
        super(ID, name, faculty, password);
        this.commitCamp = commitCamp;
        this.points = points;
    }

    
    /**
    * set the isCommittee attribute as true or false
    * true being a committee member, false being not a committee member of any camp
    * @param isCommittee, parameter to indicate if the student is a committee or not
    */
    public void setIsCommittee(boolean isCommittee) {
        this.isCommittee = isCommittee;
    }

    
    /**
    * Retrieve the camp name of the student registered as committee for
    * @return the name of the camp
    */
    public String getCommitCamp() {
        return commitCamp;
    }

    
    /**
    * Check if the user is registered as a committee member. 1 student can only be committee member for 1 camp
    * @return if the student is an committee member or not
    */
    public boolean getIsCommittee() {
        return this.isCommittee;
    }

    
    /**
    * Set the student as a committee member and the corresponding camp's name
    * @param campName, the name of the camp the student register as committee for
    */
    public void setCommitCamp(String campName) 
    {
    	this.commitCamp = campName;
        this.isCommittee = true;
    }

    
    /**
    * Retrieve the points the student have collected for replying queries
    * @return the points of the student
    */
    public int getPoints() {
        return points;
    }


    
    /**
    * Set the number of points the student have attained
    * @param points, the points the student have attained
    */
    public void setPoints(int points) 
    {
        this.points = points;
    }
}
