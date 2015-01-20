/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosopherswithjavafx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Noel
 */
public class DiningTableController implements Initializable
{
    //To run or not to run
    public static boolean running = true;
    
    //Great philosophers
    @FXML
    public ImageView aristotle;
    public Image[] aristotleImg = new Image[3];
    @FXML
    public ImageView buddha;
    public Image[] buddhaImg = new Image[3];
    @FXML
    public ImageView kant;
    public Image[] kantImg = new Image[3];
    @FXML
    public ImageView marx;
    public Image[] marxImg = new Image[3];
    @FXML
    public ImageView russell;
    public Image[] russellImg = new Image[3];

    //Forks
    @FXML
    public ImageView fork1;
    public boolean fork1See = true;
    @FXML
    public ImageView fork2;
    public boolean fork2See = true;
    @FXML
    public ImageView fork3;
    public boolean fork3See = true;
    @FXML
    public ImageView fork4;
    public boolean fork4See = true;
    @FXML
    public ImageView fork5;
    public boolean fork5See = true;

    //Generalize 5 philosophers and forks
    public static Philosopher[] philosophers = new Philosopher[5];
    public static Lock[] forks = new ReentrantLock[5];

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("Dining start!");
        running = true;
        
        //Let philosopher thread run!
        for (Philosopher p : philosophers)
        {
            new Thread(p).start();
        }
    }

    @FXML
    private void handleButtonStop(ActionEvent event)
    {
        running = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Initialize philosophers' head pictures for their status
        aristotleImg[0] = new Image(getClass().getResourceAsStream("img/Aristotle-Thinking.png"));
        aristotleImg[1] = new Image(getClass().getResourceAsStream("img/Aristotle-Hungry.png"));
        aristotleImg[2] = new Image(getClass().getResourceAsStream("img/Aristotle-Eating.png"));

        buddhaImg[0] = new Image(getClass().getResourceAsStream("img/Buddha-Thinking.png"));
        buddhaImg[1] = new Image(getClass().getResourceAsStream("img/Buddha-Hungry.png"));
        buddhaImg[2] = new Image(getClass().getResourceAsStream("img/Buddha-Eating.png"));

        kantImg[0] = new Image(getClass().getResourceAsStream("img/Kant-Thinking.png"));
        kantImg[1] = new Image(getClass().getResourceAsStream("img/Kant-Hungry.png"));
        kantImg[2] = new Image(getClass().getResourceAsStream("img/Kant-Eating.png"));

        marxImg[0] = new Image(getClass().getResourceAsStream("img/Marx-Thinking.png"));
        marxImg[1] = new Image(getClass().getResourceAsStream("img/Marx-Hungry.png"));
        marxImg[2] = new Image(getClass().getResourceAsStream("img/Marx-Eating.png"));

        russellImg[0] = new Image(getClass().getResourceAsStream("img/Russell-Thinking.png"));
        russellImg[1] = new Image(getClass().getResourceAsStream("img/Russell-Hungry.png"));
        russellImg[2] = new Image(getClass().getResourceAsStream("img/Russell-Eating.png"));

        //Initialize philosophers and forks
        for (int i = 0; i < 5; i++)
        {
            forks[i] = new ReentrantLock();
        }

        philosophers[0] = new Philosopher(forks[0], forks[1], "aristotle", aristotle, aristotleImg[0], aristotleImg[1], aristotleImg[2], fork1, fork5);
        philosophers[1] = new Philosopher(forks[1], forks[2], "buddha", buddha, buddhaImg[0], buddhaImg[1], buddhaImg[2], fork2, fork1);
        philosophers[2] = new Philosopher(forks[2], forks[3], "kant", kant, kantImg[0], kantImg[1], kantImg[2], fork3, fork2);
        philosophers[3] = new Philosopher(forks[3], forks[4], "marx", marx, marxImg[0], marxImg[1], marxImg[2], fork4, fork3);
        philosophers[4] = new Philosopher(forks[4], forks[0], "russell", russell, russellImg[0], russellImg[1], russellImg[2], fork5, fork4);
    }

}
