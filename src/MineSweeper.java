import java.util.Scanner;
public class MineSweeper {
    String[][] mineMap;
    String[][] map;
    int row;
    int col;
    Scanner scanner = new Scanner(System.in);

    public MineSweeper (int row, int col){
        this.row = row;
        this.col = col;
        this.mineMap = new String[row][col];
        this.map = new String[row][col];

        for(int i = 0; i < row; ++i)
            for (int k = 0; k < col; ++k){
                mineMap[i][k] = " - ";
                map[i][k] = " - ";
            }

        placeMines();
    }

    public void run (){
        printMineMap(true);
        System.out.println("-------------------------------");

        while (true){
            printMineMap(false);

            int row, col;
            row = getRow();
            col = getCol();
            boolean flag = false;

            while ( !isSelected(row, col) ) {
                if (isLose(row, col)){
                    flag = true;
                    break;
                }
                editMap(row, col);
                printMineMap(false);
                //printMineMap(true);  // This one was to control the game for programmer
                if(isWin()){
                    System.out.println("Congrat! You win!");
                    flag = true;
                    break;
                }
                row = getRow();
                col = getCol();
            }
            if (flag){
                printMineMap(true);
                break;
            }
            if( isSelected(row, col) ){
                System.out.println("You have chosen a coordinate that you chose coordinate before. Enter new coordinates.");
                continue;
            }
        }
    }

    private boolean isWin (){
        int totalCells = this.col * this.row;
        int mineCount = totalCells / 4;
        int uncoveredCells = 0;

        for(int i = 0; i < this.row; ++i)
            for(int k = 0; k < this.col; ++k)
                if( !this.map[i][k].equals(" - ") )
                    uncoveredCells++;

        return uncoveredCells == (totalCells - mineCount);
    }

    private void editMap(int row, int col){
        int mineCount = 0;
        for(int i = -1; i <= 1; ++i){
            for (int k = -1; k <= 1; ++k){
                if(i == 0 && k == 0)
                    continue;

                int newRow = row + i;
                int newCol = col + k;

                if (newRow >= 0 && newRow < this.row && newCol >= 0 && newCol < this.col)
                    if(this.mineMap[newRow][newCol].equals(" * "))
                        mineCount++;
            }
        }
        this.map[row][col] = " " + mineCount + " ";
    }

    private void editMap1 (int row, int col) {
        int mineCount = 0;
        for(int i = 0; i < this.row; ++i)
            for(int k = 0; k < this.col; ++k)
                if( ((i + k) >= (row + col - 2)) && ((i + k) <= (row + col + 2)) )
                    if (this.mineMap[i][k].equals(" * "))
                        mineCount++;

        String strMineCount = " " + String.valueOf(mineCount) + " ";
        this.map[row][col] = strMineCount;
    }



    private boolean isSelected (int row, int col) {
        return !this.map[row][col].equals(" - ");
    }

    private boolean isLose (int row, int col) {
        if(this.mineMap[row][col].equals(" * ")){
            System.out.println("You have chosen a mined coordinate... You lost the game.");
            return true;
        }
        else
            return false;
    }

    private void placeMines(){
        int minesToPlace = (this.row * this.col) / 4;
        int placedMines = 0;

        while (placedMines < minesToPlace){
            int randomRow = (int) (Math.random() * row);
            int randomCol = (int) (Math.random() * col);

            if(!this.mineMap[randomRow][randomCol].equals(" * ")){
                this.mineMap[randomRow][randomCol] = " * ";
                placedMines++;
            }
        }
    }

    private int getRow() {
        int row;
        while (true){
            System.out.print("Row: ");
            row = this.scanner.nextInt();
            if(row >= 0  && row <= this.row)
                return row;
            else
                System.out.println("You entered a row coordinate greater than the array's row boundary! Enter again: ");
        }
    }
    private int getCol() {
        int col;
        while (true){
            System.out.print("Col: ");
            col = this.scanner.nextInt();
            if(col >= 0  && col <= this.col)
                return col;
            else
                System.out.println("You entered a col coordinate greater than the array's col boundary! Enter again: ");
        }
    }
    public void printMineMap(boolean isMineMap){
        if(!isMineMap){
            System.out.println("Map: ");
            for(int i = 0; i < this.map.length; ++i){
                for (int k = 0; k < this.map[i].length; ++k)
                    System.out.print(this.map[i][k]);
                System.out.println();
            }
        }
        else{
            System.out.println("Mine Map: ");
            for(int i = 0; i < this.mineMap.length; ++i){
                for (int k = 0; k < this.mineMap[i].length; ++k)
                    System.out.print(this.mineMap[i][k]);
                System.out.println();
            }
        }
    }

}