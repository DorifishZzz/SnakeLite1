

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;


import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.program.ProgramMenuBar;

public class MainClass extends GraphicsProgram implements ActionListener
{

    public GOval food;

    private ArrayList<GRect> snakeBody;

    private int snakeX, snakeY, snakeWidth, snakeHeight;

    private String direction;
    public Timer timer = new Timer(100, this);
    // every 200ms, action performed will be called

    private boolean isPlaying = false, isGameOver;
    private int score, previousScore;
    private GLabel scoreLabel;

    private GLabel gameOverLabel;
    private GLabel instructions;
    private int panelWidth, panelHeight;

    private Music music;

    private Ranking ranking;



    public void run()
    {
        setUpCanvas();
        setUpInfo();

        addKeyListeners();
        timer.start();

        music  = new Music();
        music.playBgm();

        ranking = new Ranking();

        setupMenu();

    }

    private void setupMenu(){
        ProgramMenuBar menuBar = getMenuBar();
        JMenu menu = menuBar.getMenu(0);
        menu.setText("Rank System");
        menu.removeAll();

        JMenuItem rankItem = new JMenuItem("Ranking list");
        rankItem.addActionListener(e -> { //lamda
            ranking.start();
        });
        menu.add(rankItem);
    }

    public void reset(){
        setUpSnake();
        setUpBall();
        newScores(0);
        music.playBgm();
    }

    public void setUpCanvas(){
        setTitle("Snake Lite");

        panelWidth = 800;
        panelHeight = 800;
        setWindowSize(panelWidth, panelHeight);

        setCenterLocation(getScreenWidth() / 2, getScreenHeight() / 2);
        setResizable(false);
        setBackground(Color.PINK);
    }

    public void setUpInfo(){
        instructions = new Instructions("Click anywhere to start Game", 200, 300);
        add(instructions);

        scoreLabel = new Scoreboard("your score is:" + score, 520, 20);
        add(scoreLabel);

        gameOverLabel = new Instructions("GameOver!", 300, 450);
        add(gameOverLabel);

        showGameOver(false);

    }

    private void newScores(int x){
        score = x;
        scoreLabel.setLabel("your score is:" + score);
    }

    private void showInstruction(boolean show) { //instructions show or not
        instructions.setVisible(show);
    }

    private void showGameOver(boolean show){ //gameover show or not
        gameOverLabel.setVisible(show);
    }

    private void setUpSnake(){
        // to remove the snake from the interface
        removeSnake();

        snakeBody = new ArrayList<>();
        //snake head location
        snakeX = 175;
        snakeY = 275;
        snakeWidth = snakeHeight = 25;

        //head of the snake
        SnakePart head = new SnakePart(snakeX, snakeY, snakeWidth, snakeHeight);
        head.setFillColor(Color.BLUE);
        snakeBody.add(head); //the 0 value of the arrayList will always be the position of the head

        for(int i = 0; i < 2; i++){
            SnakePart body = new SnakePart(snakeX - snakeWidth * (i + 1), snakeY, snakeWidth, snakeHeight);
            snakeBody.add(body);
        }
        direction = "R";
        drawSnake();
    }


    private void removeSnake(){
        if (snakeBody != null){
            for(int i = 0; i < snakeBody.size(); i++){
                remove(snakeBody.get(i));
            }
        }
    }

    public void drawSnake()
    {
        for(int i = 0; i < snakeBody.size(); i++){
            add(snakeBody.get(i));
        }

    }

    public void setUpBall(){
        removeFood();
        food = new Ball(50, 50, snakeWidth, snakeHeight);
        randomFood();
        add(food);
    }

    private void removeFood(){
        if (food != null){
            remove(food);
        }
    }

    public void randomFood()
    {
        // range of 25 to 700, multiple of 25, cannot be at location of snake's head
        // 25 * [1, 28]

        GRect head = snakeBody.get(0); //get the snake's head location

        while(true){
            int x = (int)(Math.random() * 28 + 1) * snakeWidth;
            int y = (int)(Math.random() * 28 + 1) * snakeHeight;

            if(x != head.getX() || y != head.getY()) // make sure the ball is not at the snake's head
            {
                food.setLocation(x, y);
                return;
            }
        }
    }

    public void mouseClicked(MouseEvent e){
        startPauseGame();
    } //when mouse is clicked, this method will run

    private void startPauseGame(){
        // instructions disappear, snake and food appear
        showInstruction(false);
        reset();
        isPlaying = true;
        showGameOver(false);
    }

    public void keyPressed(KeyEvent keyPressed) // when any key is pressed, this method will run
    {
        switch (keyPressed.getKeyCode())
        {
            case KeyEvent.VK_UP:{
                direction = "U";
                break;
            }

            case KeyEvent.VK_DOWN:{
                direction = "D";
                break;
            }


            case KeyEvent.VK_LEFT:{
                direction = "L";
                break;
            }


            case KeyEvent.VK_RIGHT:{
                direction = "R";
                break;
            }
        }
    }


    private void redrawSnake() //moving the body of the snake
    {
        for(int i = snakeBody.size() - 1; i > 0; i --){ //i cannot be 0
            GRect current = snakeBody.get(i);
            GRect before = snakeBody.get(i - 1);
            current.setLocation(before.getLocation());
        }
    }

    private void growSnake()
    {
        GRect snakePart = new SnakePart(0, 0, snakeWidth, snakeHeight);
        //the position will change right after, so initial x y position doesn't matter
        snakeBody.add(snakePart);
        add(snakePart);
    }

    private void moveUp()
    {
        GRect head = snakeBody.get(0);
        head.setLocation(head.getX(), head.getY() - snakeHeight);
    }

    private void moveDown()
    {
        GRect head = snakeBody.get(0);
        head.setLocation(head.getX(), head.getY() + snakeHeight);
    }

    private void moveLeft()
    {
        GRect head = snakeBody.get(0);
        head.setLocation(head.getX() - snakeWidth, head.getY());
    }

    private void moveRight()
    {
        GRect head = snakeBody.get(0);
        head.setLocation(head.getX() + snakeWidth, head.getY());
    }

    private boolean bumpSelf(){
        GRect head = snakeBody.get(0);
        for(int i = 1; i < snakeBody.size(); i++){
            GRect current = snakeBody.get(i);
            if(head.getX() == current.getX() && head.getY() == current.getY()){
                return true;
            }
        }
        return false;
    }

    private boolean bumpEdge(){
        GRect head = snakeBody.get(0);
        double x = head.getCenterX();
        double y = head.getCenterY();

        if(direction.equals("U")){
            if (y - snakeHeight <= 0){  //if in the screen, y has to be at least 0
                return true;
            }
        }

        else if(direction.equals("D")){
            if (y + 2 * snakeHeight >= panelHeight){
                return true;
            }
        }

        else if(direction.equals("L")){
            if (x - snakeWidth <= 0){
                return true;
            }
        }

        else {
            if(x + snakeWidth >= panelWidth){
                return true;
            }
        }

        return false;

    }

    private void gameOver(){
        isPlaying = false;
        showGameOver(true);
        showInstruction(true);
        music.playDead();
        ranking.scoreValues(score);
    }

    private boolean eatFood(){
        GRect head = snakeBody.get(0);
        if(head.getX() == food.getX() && head.getY() == food.getY()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        if(isPlaying == false){
            return;
        }

        // see if the snake bumps itself - Dead
        if (bumpSelf() || bumpEdge() == true){
            gameOver();
            return;
        }

        // see if the snake eats the food
        if (eatFood() == true){
            newScores(score + 1);
            growSnake();
            randomFood();
            music.playEat();
        }

        redrawSnake();
        if(direction.equals("U")){
            moveUp();
        }

        else if(direction.equals("D")){
            moveDown();
        }

        else if(direction.equals("L")){
            moveLeft();
        }

        else {
            moveRight();
        }
    }

    public static void main(String[] args)
    {
        new MainClass().start();
    }
}