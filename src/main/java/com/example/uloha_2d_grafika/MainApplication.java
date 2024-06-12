package com.example.uloha_2d_grafika;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;


public class MainApplication extends Application {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    GraphicsContext graphicsContext;

    Balloon balloon;
    Random random = new Random();
    Direction direction;
    double clickedX;
    double clickedY;
    public boolean stop = false;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            clickedX = event.getX();
            clickedY = event.getY();
            System.out.println(clickedX + ", " + clickedY);
        });
        randomDirection();
        stage.setScene(scene);
        stage.setTitle("Klasifikovaná úloha");
        stage.show();

        balloon = new Balloon(550-SCREEN_WIDTH, SCREEN_HEIGHT-350);

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastTick = 0;

            @Override
            public void handle(long now) {
                if (now - lastTick > 1000000l) {
                    lastTick = now;
                    tick();
                }
            }
        };
        animationTimer.start();

        AnimationTimer textureAnimation = new AnimationTimer() {
            long lastTick = 0;

            @Override
            public void handle(long now) {
                if (now - lastTick > 45000000l) {
                    lastTick = now;
                    animationTick();
                }
            }
        };
        textureAnimation.start();
    }
    boolean popped = false;
    private void detectCollisions(Balloon balloon) {
            if ((clickedX >= balloon.getX()+330 && clickedX <= balloon.getX()+550) && (clickedY >= balloon.getY()+50 && clickedY <= balloon.getY() + 350)){
                popped = true;
                System.out.println("pop");
                clickedX = 0;
                clickedY = 0;
            }
    }

    private void tick(){
        if (popped) {
            return;
        }
        switch (direction){
            case UL:
                if (balloon.getX()+330 < 0 || balloon.getY()+50 < 0){
                    if ((balloon.getX()+330 < 0) && !(balloon.getY()+50 < 0)){
                        direction = Direction.UR;
                    } else if (!(balloon.getX()+330 < 0) && (balloon.getY()+50 < 0)){
                        direction = Direction.DL;
                    } else {
                        direction = Direction.DR;
                    }
                }
                balloon.decrementY(); balloon.decrementX();
                break;
            case UR:
                if (balloon.getX()+550 > SCREEN_WIDTH || balloon.getY()+50 < 0){
                    if ((balloon.getX()+550 > SCREEN_WIDTH) && !(balloon.getY()+50 < 0)){
                        direction = Direction.UL;
                    } else if (!(balloon.getX()+550 > SCREEN_WIDTH) && (balloon.getY()+50 < 0)){
                        direction = Direction.DR;
                    } else {
                        direction = Direction.DL;
                    }
                }
                balloon.decrementY(); balloon.incrementX();
                break;
            case DL:
                if (balloon.getX()+330 < 0 || balloon.getY()+350 > SCREEN_HEIGHT){
                    if ((balloon.getX()+330 < 0) && !(balloon.getY()+350 > SCREEN_HEIGHT)){
                        direction = Direction.DR;
                    } else if (!(balloon.getX()+330 < 0) && (balloon.getY()+350 > SCREEN_HEIGHT)){
                        direction = Direction.UL;
                    } else {
                        direction = Direction.UR;
                    }
                }
                balloon.incrementY(); balloon.decrementX();
                break;
            case DR:
                if (balloon.getX()+550 > SCREEN_WIDTH || balloon.getY()+350 > SCREEN_HEIGHT){
                    if ((balloon.getX()+550 > SCREEN_WIDTH) && !(balloon.getY()+350 > SCREEN_HEIGHT)){
                        direction = Direction.DL;
                    } else if (!(balloon.getX()+550 > SCREEN_WIDTH) && (balloon.getY()+350 > SCREEN_HEIGHT)){
                        direction = Direction.UR;
                    } else {
                        direction = Direction.UL;
                    }
                }
                balloon.incrementY(); balloon.incrementX();
                break;
        }
        detectCollisions(balloon);
    }
    int animationFrame = 0;
    boolean reverse = false;
    boolean done = false;
    Image currentBalloonTexture;
    private void animationTick(){
        clearScreen();
        if (done){
            return;
        }
        if (popped){
            if (animationFrame != 0 && currentBalloonTexture == balloon.getImagesNormal()[animationFrame]){
                animationFrame = 0;
            }
            if (animationFrame < balloon.getImagesPop().length-1){
                animationFrame++;
                currentBalloonTexture = balloon.getImagesPop()[animationFrame];
            } else {
                currentBalloonTexture = null;
                respawn();
            }
        }
        if (!popped){
            if (animationFrame < balloon.imagesNormal.length-1 && reverse == false){
                animationFrame++;
                currentBalloonTexture = balloon.getImagesNormal()[animationFrame];
            } else{
                reverse = true;
            }
            if (animationFrame > 0 && reverse == true){
                animationFrame--;
                currentBalloonTexture = balloon.getImagesNormal()[animationFrame];
            } else{
                reverse = false;
            }
        }
        graphicsContext.drawImage(currentBalloonTexture, balloon.x, balloon.y, balloon.height, balloon.height);
    }
    private void respawn(){
        balloon.setX(random.nextInt(SCREEN_WIDTH-550));
        balloon.setY(random.nextInt(SCREEN_HEIGHT-350));
        animationFrame = 0;
        currentBalloonTexture = balloon.getImagesNormal()[animationFrame];
        randomDirection();
        popped = false;
        done = false;
    }
    private void randomDirection(){
        switch (random.nextInt(3)+1) {
            case 1:
                direction = Direction.UL;
                break;
            case 2:
                direction = Direction.UR;
                break;
            case 3:
                direction = Direction.DL;
                break;
            case 4:
                direction = Direction.DR;
                break;
        }
    }
    private void clearScreen() {
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect( 0, 0, SCREEN_WIDTH, SCREEN_WIDTH);
    }
    public static void main(String[] args) {
        launch();
    }
}