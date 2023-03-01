package com.example.threadpanelfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class MainCont {
    @FXML
    Circle littleCircle;

    @FXML
    Circle bigCircle;

    @FXML
    Polygon Arrow;

    @FXML
    Label hitCount;

    @FXML
    Label arrowCount;

    boolean stop = true;

    boolean piu = false;

    Thread circleMove1,circleMove2;

    public class CircleMove implements Runnable{

        Circle circle;
        int Speed;

        CircleMove(Circle c, int speed){
            Speed = speed;
            circle = c;
        }


        @Override
        public void run() {
            boolean down = false;
            double newY = 0;

            while (!stop) {

                if (down) {
                    newY = circle.getLayoutY() + Speed;
                } else {
                    newY = circle.getLayoutY() - Speed;
                }
                Platform.runLater(new SetCY(newY,circle));

                if (newY  > 500) {
                    down = false;
                }

                if (newY  < 0) {
                    down = true;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }


            }
        }
    }

    public class SetCY implements Runnable{
        double Y = 0;
        Circle circle;

        public SetCY(double y, Circle c){
            Y = y;
            circle = c;
        }

        @Override
        public void run() {
            circle.setLayoutY(Y);
        }
    }

    public class SetPX implements Runnable{
        double X = 0;
        Polygon polygon;

        public SetPX(double x, Polygon p){
            X = x;
            polygon = p;
        }

        @Override
        public void run() {
            polygon.setLayoutX(X);
        }
    }

    @FXML
    protected void runCircle(){
        if(stop) {
            stop = false;
            circleMove1 = new Thread(
                    new CircleMove(littleCircle,2)
            );

            circleMove2 = new Thread(
                    new CircleMove(bigCircle,3)
            );

            circleMove1.start();
            circleMove2.start();


        }

    }

    @FXML
    protected  void stopCircle(){
        stop = true;
        Platform.runLater(new SetCY(250, littleCircle));
        Platform.runLater(new SetCY(250, bigCircle));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hitCount.setText("0");
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                arrowCount.setText("0");
            }
        });
        piu = false;

    }

    @FXML
    protected  void piu(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!piu) {
                    piu = true;
                    int speed = 2;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            arrowCount.setText("" + (Integer.parseInt(arrowCount.getText()) + 1));
                        }
                    });
                    while (!stop) {
                        Platform.runLater(new SetPX(Arrow.getLayoutX() + speed, Arrow));

                        if (Arrow.getLayoutX() > 470) {
                            Platform.runLater(new SetPX(35, Arrow));
                            break;
                        }

                        if (Arrow.getLayoutX() + 30 > 330 && Arrow.getLayoutX() + 30 < 390) {
                            if (littleCircle.getLayoutY() > 250 - 30 && littleCircle.getLayoutY() < 250 + 30) {
                                Platform.runLater(new SetPX(35, Arrow));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        hitCount.setText("" + (Integer.parseInt(hitCount.getText()) + 1));
                                    }
                                });

                                break;
                            }
                        }

                        if (Arrow.getLayoutX() + 30 > 435 && Arrow.getLayoutX() + 30 < 465) {
                            if (bigCircle.getLayoutY() > 250 - 15 && bigCircle.getLayoutY() < 250 + 15) {
                                Platform.runLater(new SetPX(35, Arrow));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        hitCount.setText("" + (Integer.parseInt(hitCount.getText()) + 2));
                                    }
                                });
                                break;
                            }
                        }

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }

                    Platform.runLater(new SetPX(35,Arrow));
                    piu = false;
                }
        }}).start();

    }



}
