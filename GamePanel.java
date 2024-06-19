import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    int screenWidth = 600;
    int screenHeight = 600;
    int unitSize = 30;
    int gameUnits = (screenWidth*screenHeight)/unitSize;
    int gameSpeed = 100;
    int X[]= new int[gameUnits];
    int Y[]=new int[gameUnits];
    int snakeLength = 6;
    int apples = 0;
    int appleX = 0;
    int appleY = 0;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame () {
        newApple();
        running=true;
        timer = new Timer(gameSpeed,this);
        timer.start();
    }
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            //draw the grid line on the screen
            for(int i = 0;i<screenHeight/unitSize;i++){
                g.drawLine(i*unitSize,0,i*unitSize,screenHeight);
                g.drawLine(0,i*unitSize,screenWidth,i*unitSize);
            }
            //draw the food on the screen on a random place
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,unitSize,unitSize);
            //draw the head and the body of the snake
            for(int i =0;i<snakeLength;i++){
                if(i==0){
                    g.setColor(Color.green);             //for the head
                }else {
                    g.setColor(new Color(45,180,0)); //for the body

                }
                g.fillRect(X[i],Y[i],unitSize,unitSize);
                //score
                g.setColor(Color.blue);
                g.setFont(new Font("Sans Serif",Font.BOLD,30));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: "+apples, 300 - metrics.stringWidth("Score: "+apples)/2,g.getFont().getSize());
            }
        }else {
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int) (screenWidth/unitSize))*unitSize;
        appleY = random.nextInt((int) (screenHeight/unitSize))*unitSize;
    }
    public void move(){
        for(int i = snakeLength;i>0;i--){
            X[i] = X[i-1];
            Y[i]=Y[i-1];

        }
        switch (direction){
            case 'U':
                Y[0]=Y[0]-unitSize;
                break;
            case 'D':
                Y[0]=Y[0]+unitSize;
                break;
            case 'L':
                X[0]=X[0]-unitSize;
                break;
            case 'R':
                X[0]=X[0]+unitSize;
                break;
        }
    }
    public void checkApple(){
        if((X[0] == appleX) && (Y[0] == appleY)){
            snakeLength+=1;
            apples++;
            newApple();
        }
    }
    public void checkCollision(){
        //checks if head collide the body
        for(int i = snakeLength;i>0;i--){
            if((X[0]== X[i]) && (Y[0]== Y[i])){
                running = false;
            }
        }
        //check if head touches left border
        if(X[0] < 0){
            running = false;
        }
        //checks if head touches right border
        if(X[0] >screenWidth){
            running = false;
        }
        //checks if head touched top border
        if(Y[0] < 0){
            running = false;
        }
        //checks if head touched bottom border
        if(Y[0] > screenHeight){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //score
        g.setColor(Color.white);
        g.setFont( new Font("Sans Serif",Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+apples, (600 - metrics1.stringWidth("Score: "+apples))/2, g.getFont().getSize());
        //Game over text
        g.setColor(Color.white);
        g.setFont(new Font("Sans Serif",Font.BOLD,30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(600 - metrics.stringWidth("Game Over"))/2,screenHeight/2);
        g.drawString("Thanks for playing!",(500 - metrics.stringWidth("Game Over"))/2,(screenHeight/2)+40);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_A:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
} 
    
