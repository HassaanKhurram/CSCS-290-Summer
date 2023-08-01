package Java;
import java.util.Scanner;

public class MazeGame {
    public static int r;
    public static int c;
    public static int score = 0;
    public static int highscore = 12;

    public static void main(String[] args) {
        System.out.println("WELCOME TO MAZE GAME");
        boolean menuCheck = true;

        while (menuCheck) {
            System.out.println("MENU");
            System.out.println("Press 1 to Play Game ");
            System.out.println("Press 2 to Instructions ");
            System.out.println("Press 3 to Credit ");
            System.out.println("Press 4 to High Score ");
            System.out.println("Press 5 to Exit ");
            Scanner scan = new Scanner(System.in);
            int input = scan.nextInt();

            if (input == 1) {
                // Start the game
                long startTime = System.currentTimeMillis();
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        char board[][] = new char[7][7];
                        board = initializeMaze();
                        printMaze(board);
                        playGame(board);
                        board = initializeMaze();
                        long endTime = System.currentTimeMillis();
                        long timeTaken = (endTime - startTime) / 6000;
                    }
                });
                thread.start();
                try {
                    thread.join(60000); // Wait for up to 60 seconds for the thread to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (thread.isAlive()) {
                    thread.interrupt();
                    System.out.println("You did not enter a string within the time limit.");
                }
            } else if (input == 2) {
                // Display game instructions
                System.out.println("The maze will have walls (#) that are impassable obstacles, open paths (.) that you can move through, your starting position (P), and the exit point (E).");
                System.out.println("The objective of the maze game is to reach the exit point (marked with 'E') in the shortest number of moves possible.");
                System.out.println("As the player navigates through the maze, the game will keep track of the number of paths the player takes to reach the exit.");
                System.out.println("W: Move up.\r\n" + "A: Move left.\r\n" + "S: Move down.\r\n" + "D: Move right.");
                System.out.println("The player's score is determined by the total number of moves (paths) taken to complete the maze.");
                System.out.println("The lower the number of moves taken to reach the exit, the higher the player's score.");
                System.out.println("After each game session, the game will display the player's score as well as the current high score achieved during that play session.");
                System.out.println("The high score will be updated if the player manages to complete the maze in fewer moves than the previous high score");
            } else if (input == 3) {
                // Display credits
                System.out.println("The game is developed by Muhammad Hassan bin khurram");
                System.out.println("His rollno is 251684003");
            } else if (input == 4) {
                // Display high score
                highScore();
            } else if (input == 5) {
                // Exit the game
                menuCheck = false;
                System.out.println("Thank you for playing the game ");
            } else {
                System.out.println("Wrong Input");
            }
        }
    }

    // Method to update the high score
    public static void updateScore() {
        highscore = score;
    }

    // Empty block (not doing anything?)
    {
        System.out.println("");
    }

    // Method to display the high score
    public static void highScore() {
        System.out.println("High score of the game is " + highscore);
    }

    // Main game logic
    public static void playGame(char board[][]) {
        r = 1;
        c = 1;
        board[r][c] = 'H';
        score = 0;
        printMaze(board);
        boolean playgamecheck = true;
        while (playgamecheck) {
            playgamecheck = movePlayer(board);
        }
        if (score < highscore) {
            updateScore();
            System.out.println("You scored the highest marks " + score);
        } else {
            System.out.println("The score of the player is " + score);
            System.out.println("Highest score of the game is " + highscore);
        }
    }

    // Method to handle player movement
    public static boolean movePlayer(char board[][]) {
        boolean move = true;
        boolean userinput = true;
        char moveInput = '0';
        while (move) {
            while (userinput) {
                System.out.println("Please enter player to move ");
                System.out.println("W/w: Move up.\r\n" + "A/a: Move left.\r\n" + "S/s: Move down.\r\n" + "D/d: Move right.");
                Scanner scan = new Scanner(System.in);
                moveInput = scan.next().charAt(0);
                score++;
                if (moveInput == 'W' || moveInput == 'w' || moveInput == 'A' || moveInput == 'a' || moveInput == 'S' || moveInput == 's'
                        || moveInput == 'D' || moveInput == 'd') {
                    userinput = false;
                } else {
                    System.out.println("Wrong input");
                }
            }
            int oldr = r;
            int oldc = c;
            if (moveInput == 'W' || moveInput == 'w') {
                r--;
            } else if (moveInput == 'A' || moveInput == 'a') {
                c--;
            } else if (moveInput == 'S' || moveInput == 's') {
                r++;
            } else if (moveInput == 'D' || moveInput == 'd') {
                c++;
            }
            move = isValidMove(board, moveInput);
            if (move == false) {
                board = initializeMaze();
                board[r][c] = 'H';
                System.out.println("\n".repeat(20));
                printMaze(board);
                System.out.println("User score = " + score);
            } else {
                r = oldr;
                c = oldc;
                board = initializeMaze();
                board[r][c] = 'H';
                printMaze(board);
                System.out.println("User score = " + score);
                userinput = true;
            }
        }
        return hasPlayerwon();
    }

    // Method to check if the player has won the game
    public static boolean hasPlayerwon() {
        if (r == 5 && c == 5) {
            System.out.println("Player has Won ");
            return false;
        } else return true;
    }

    // Method to check if the move is valid
    public static boolean isValidMove(char board[][], char i) {
        if (board[r][c] != '#')
            return false;
        else {
            System.out.println("\n".repeat(20));
            System.out.println("Invalid move, there's a Wall. Try again.");
            return true;
        }
    }

    // Method to print the maze
    public static void printMaze(char board[][]) {
        System.out.println("                    MAZE GAME");
        for (int i = 0; i < 7; i++) {
            System.out.print("              ");
            for (int j = 0; j < 7; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to initialize the maze with the starting layout
    public static char[][] initializeMaze() {
        char board[][] = {
                {'#', '#', '#', '#', '#', '#', '#'},
                {'#', 'P', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '.', '#', '#'},
                {'#', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '.', '#', '#', '#'},
                {'#', '.', '.', '.', '.', 'E', '#'},
                {'#', '#', '#', '#', '#', '#', '#'}
        };
        return board;
    }
}
