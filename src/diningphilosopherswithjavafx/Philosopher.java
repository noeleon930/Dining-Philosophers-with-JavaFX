/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosopherswithjavafx;

import static diningphilosopherswithjavafx.DiningTableController.running;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

/**
 *
 * @author Noel
 */
public class Philosopher implements Runnable
{

    private Lock leftFork;
    private Lock rightFork;
    private String name;
    private boolean hungry;

    private ImageView headView;
    private Image thinkingImg;
    private Image hungryImg;
    private Image eatingImg;

    private ImageView leftForkView;
    private ImageView rightForkView;

    public Philosopher(Lock _leftFork, Lock _rightFork, String _name, ImageView _headView, Image _thinkingImg, Image _hungryImg, Image _eatingImg, ImageView _leftForkView, ImageView _rightForkView)
    {
        this.leftFork = _leftFork;
        this.rightFork = _rightFork;
        this.name = _name;
        this.hungry = false;

        this.headView = _headView;
        this.thinkingImg = _thinkingImg;
        this.hungryImg = _hungryImg;
        this.eatingImg = _eatingImg;

        this.leftForkView = _leftForkView;
        this.rightForkView = _rightForkView;
    }

    public void think()
    {
        //If they are hungry, they do not think!!! hahaha
        if (hungry == false)
        {
            try
            {
                System.out.println(name + " is thiking...");
                Platform.runLater(() ->
                {
                    headView.setImage(thinkingImg);
                });

                Thread.sleep((long) (Math.random() * 5000));
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (hungry == false)
        {
            hungry = true;
        }

    }

    public void eat()
    {
        //They are hungry
        System.out.println(name + " is hungry...");
        if (hungry == true)
        {
            Platform.runLater(() ->
            {
                headView.setImage(hungryImg);
            });
        }
        try
        {
            Thread.sleep((long) (Math.random() * 3000));
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (leftFork.tryLock())
        {
            try
            {
                if (rightFork.tryLock())
                {
                    try
                    {
                        System.out.println(name + " is eating...");
                        Platform.runLater(() ->
                        {
                            headView.setImage(eatingImg);
                            leftForkView.setVisible(false);
                            rightForkView.setVisible(false);
                        });
                        hungry = false;

                        try
                        {
                            Thread.sleep((long) (Math.random() * 8000));
                        }
                        catch (InterruptedException ex)
                        {
                            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Platform.runLater(() ->
                        {
                            leftForkView.setVisible(true);
                            rightForkView.setVisible(true);
                        });
                    }
                    finally
                    {
                        rightFork.unlock();
                    }
                }
            }
            finally
            {
                leftFork.unlock();
            }
        }
    }

    @Override
    public void run()
    {
        while (running)
        {
            think();
            eat();
            if (hungry == true)
            {
                Platform.runLater(() ->
                {
                    headView.setImage(hungryImg);
                });
            }
        }
        System.out.println(name + " stopped...");
    }

}
