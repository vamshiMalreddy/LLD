/*
    Snake game
    Snake, egg, 
    no obsatacles, it should come out of other end
    snke length - 5 units
    for food it takes lenght increases 
    Score - increase for every cell it traverses. if it consumes 1 egg - score increases by 2, 2 egg - 3 and soon
    50*50 matrix - 1 cell of the grid
    one egg at a time, snake length increases
    games ends when the snakes touches itself.
*/

// Todo
// Complete
// Test
// Extensions
    // Poison, Obstacle, pass through itself for two times.


/*
 * can we create comments as function names?
 */


public enum Direction{
    LEFT(-1,0),
    RIGHT(1,0),
    UP(0,-1),
    DOWN(0,1);

    private int deltaX;
    private int deltaY;

    public Direction(int deltaX, int deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public static Direction fromString(String str){
        for(Direction dir: Direction.values()){
            if(value.name().equalsIgnoreCase(str)){
                return value
            }
        }

        throw IllegalArgumentException("Illegal argument - doesn't match to an ENUM");
    }

    public int getDeltaX(){
        return deltaX;
    }

    public int getDeltaY(){
        return deltaY;
    }
}

public class Controller{
    private Scanner scanner;

    public Controller(){
        scanner = new Scanner(System.in);
    }

    public Direction getInput(){
        while(true){
            try{
                System.out.println("provide input in (LEFT,RIGHT,UP,DOWN)");
                String input = scanner.nextLine();
                return Direction.fromString(input);
            }
            catch(IllegalArgumentException e){
                System.err.println("Error: " + e.getMessage());
                System.out.println("Invalid input please try again");
            }
        }
    }
}

public class Position{
    int row;
    int col;
    private static Random random = new Random();

    public Position(int row,int col){
        this.row = row;
        this.col = col;
    }

    public static Position getRandomPosition(int maxRows, int maxCols){
        return new Position(random.nextInt(maxRows), random.nextInt(maxCols));
    }

    public boolean equals(Position position){
        return this.row == position.row && this.col == position.col;
    }
}

public class Snake {
    LinkedList<Position> body;
    int length;

    public void Snake(Position headPosition, Position tailPosition){
        body = new LinkedList<Position>();
        body.addFirst(headPosition);
        body.addLast(tailPosition);
    }
    
    public void move(Direction direction, int gridHeight, int gridWidth){
        Position currSnakeHeadPosition = body.getFirst();

        int newRow = (currSnakeHeadPosition.getRow() + direction.getDeltaX())%gridHeight;
        int newCol = (currSnakeHeadPosition.getCol() + direction.getDeltaY())%gridWidth;

        Position newSnakeHeadPosition = getNewSnakePosition(snakeHead, direction);
        
        body.addFirst(newSnakeHeadPosition);
        body.removeLast();
    }

    public void moveSnake(Direction direction, int gridHeight, int gridWidth){

        Position newSnakeHeadPosition = getNewSnakePosition(snakeHead, direction);
        snake.move(newSnakeHeadPosition);
    }

    public Position getNewSnakePosition(Postition currSnakeHeadPosition, Direction direction){
        int newRow = currSnakeHeadPosition.getRow();
        int newCol = currSnakeHeadPosition.getCol();

        // we can get this from ENUM.
        if (direction == LEFT){
            newRow = (newRow - 1) % gridHeight;
        }
        if (direction == RIGHT){
            newRow = (newRow + 1) % gridHeight;
        }
        if (direction == UP){
            newCol = (newCol + 1) % gridWidth;
        }
        if (direction == DOWN){
            newCol = (newCol + 1) % gridWidth;
        }

        return new Position(newRow, newCol);
    }

    public boolean checkSelfBite(){
        Position headPosition = body.getFirst();
        for(Position position: body.subList(1,body.size())){
            if(position.equals(headPosition)){
                return false;
            }
        }
        return true;
    }

    public void increaseLength(int gridHeight, int gridWidth){
        Position tailPosition = body.getLast();
        int newTailRow = (tailPosition.getRow() - 1)%gridHeight;
        Position newTailPosition = new Position(newTailRow,tailPosition.getCol());
    }
}

public class Game{
    Snake snake;
    int gridWidth;
    int gridHeight;
    Position foodPosition;
    Scoreboard scoreboard = new Scoreboard();
    private Controller controller = new Controller();
    Random random = new Random();

    public Game(int gridWidth, int gridHeight){
        Position randomHeadPosition = Position.getRandomPosition(gridHeight, gridWidth);
        Position tailPosition = new Position((randomHeadPosition.getRow()-1)%gridHeight, randomHeadPosition.getCol());
        snake = new Snake(randomHeadPosition, tailPosition);
    }

    public void play(){

        while(true){
            Direction direction = Controller.getInput();
            Postition headPosition = moveSnake(direction);
            if (snake.checkSelfBite()){
                System.out.println("Game Over");
                break;
            }
            scoreboard.increaseScore();
            if (food.getPosition().equals(headPosition)){
                scoreboard.increaseEggsEaten();
                snake.increaseLength();
                createNewEgg();
            }
        }
    }

    public void moveSnake(Direction direction){
        Position currSnakeHeadPosition = snake.getHead().getPosition();
        Position newSnakeHeadPosition = getNewSnakePosition(snakeHead, direction);
        snake.move(newSnakeHeadPosition);
    }

    // this could be in snake?
    public Position getNewSnakePosition(Postition currSnakeHeadPosition, Direction direction){
        int newRow = currSnakeHeadPosition.getRow();
        int newCol = currSnakeHeadPosition.getCol();

        // we can get this from ENUM.
        if (direction == LEFT){
            newRow = (newRow - 1) % gridHeight;
        }
        if (direction == RIGHT){
            newRow = (newRow + 1) % gridHeight;
        }
        if (direction == UP){
            newCol = (newCol + 1) % gridWidth;
        }
        if (direction == DOWN){
            newCol = (newCol + 1) % gridWidth;
        }

        return new Position(newRow, newCol);
    }

    public void createNewEgg(){
        foodPosition = Position.getRandomPosition(gridHeight, gridWidth);
    }
}

public class Scoreboard{
    int score;
    int eggsEaten;

    public Scoreboard(){
        score = 0;
        eggsEaten = 0;
    }

    public void increaseScore(){
        score += eggsEaten - 1;
    }

    public void increaseEggsEaten(){
        eggsEaten += 1;
    }
}

