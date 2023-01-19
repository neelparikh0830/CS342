import javafx.scene.paint.Color;

public class checkWinner {

    //private final int maxRow = 5, maxCol = 6;

    private static boolean checkDiagonalDR(GameButton[][] matrix, int colX, int rowY, int x, int p, Color winnerColor) 
    {
        if (x == 3){
            changeToWinner(matrix[rowY][colX], winnerColor);
            return true;
        }
        else if ((colX >= 6) || (rowY >= 5)){
            return false;
        }
        else if (matrix[rowY][colX].getPlayerOccupies() == p &&  matrix[rowY+1][colX+1].getPlayerOccupies() == p){
            // return checkDiagonalDR(matrix, colX+1, rowY+1, x+1, p, winnerColor);
            if (checkDiagonalDR(matrix, colX+1, rowY+1, x+1, p, winnerColor)) {
                changeToWinner(matrix[rowY][colX], winnerColor);
                return true;
            }
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalDL(GameButton[][] matrix, int colX, int rowY, int x, int p, Color winnerColor) 
    {
        if (x == 3){
            changeToWinner(matrix[rowY][colX], winnerColor);
            return true;
        }
        else if ((colX <= 0) || (rowY >= 5)){
            return false;
        }
        else if (matrix[rowY][colX].getPlayerOccupies() == p &&  matrix[rowY+1][colX-1].getPlayerOccupies() == p){
            // return checkDiagonalDL(matrix, colX-1, rowY+1, x+1, p, winnerColor);
            if (checkDiagonalDL(matrix, colX-1, rowY+1, x+1, p, winnerColor)) {
                changeToWinner(matrix[rowY][colX], winnerColor);
                return true;
            }
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalUL(GameButton[][] matrix, int colX, int rowY, int x, int p, Color winnerColor) 
    {
        if (x == 3){
            changeToWinner(matrix[rowY][colX], winnerColor);
            return true;
        }
        else if ((colX <= 0) || (rowY <= 0)){
            return false;
        }
        else if (matrix[rowY][colX].getPlayerOccupies() == p &&  matrix[rowY-1][colX-1].getPlayerOccupies() == p){
            if (checkDiagonalUL(matrix, colX-1, rowY-1, x+1, p, winnerColor)) {
                changeToWinner(matrix[rowY][colX], winnerColor);
                return true;
            }
            // return checkDiagonalUL(matrix, colX-1, rowY-1, x+1, p, winnerColor);
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalUR(GameButton[][] matrix, int colX, int rowY, int x, int p, Color winnerColor) 
    {
        if (x == 3){
            changeToWinner(matrix[rowY][colX], winnerColor);
            return true;
        }
        else if ((colX >= 6) || (rowY <= 0)){
            return false;
        }
        else if (matrix[rowY][colX].getPlayerOccupies() == p &&  matrix[rowY-1][colX+1].getPlayerOccupies() == p){
            // return checkDiagonalUR(matrix, colX+1, rowY-1, x+1, p, winnerColor);
            if (checkDiagonalUR(matrix, colX+1, rowY-1, x+1, p, winnerColor)) {
                changeToWinner(matrix[rowY][colX], winnerColor);
                return true;
            }
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalTwoLTest(GameButton[][] matrix, int colX, int rowY, int x, int p, Color winnerColor)
    {
        // if (rowY == 4 && colX > 0 && colX < 5)
        // {
        //     if(matrix[rowY+1][colX-1].getPlayerOccupies() == p && matrix[rowY][colX].getPlayerOccupies() == p 
        //         && matrix[rowY-1][colX+1].getPlayerOccupies() == p && matrix[rowY-2][colX+2].getPlayerOccupies() == p)
        //     {
        //         changeToWinner(matrix[rowY+1][colX-1], winnerColor);
        //         changeToWinner(matrix[rowY][colX], winnerColor);
        //         changeToWinner(matrix[rowY-1][colX+1], winnerColor);
        //         changeToWinner(matrix[rowY-2][colX+2], winnerColor);
        //         return true;
        //     }
            
        // }
        // else if (rowY == 1 && colX > 1 && colX < 6)
        // {
        //     if(matrix[rowY+2][colX-2].getPlayerOccupies() == p && matrix[rowY+1][colX-1].getPlayerOccupies() == p 
        //         && matrix[rowY][colX].getPlayerOccupies() == p && matrix[rowY-1][colX+1].getPlayerOccupies() == p)
        //     {
        //         changeToWinner(matrix[rowY+2][colX-2], winnerColor);
        //         changeToWinner(matrix[rowY+1][colX-1], winnerColor);
        //         changeToWinner(matrix[rowY][colX], winnerColor);
        //         changeToWinner(matrix[rowY-1][colX+1], winnerColor);
        //         return true;
        //     }
            
        // }
        if (rowY <= 3 && rowY > 0 && colX >= 2  && colX < 6)
        {
            if(matrix[rowY+2][colX-2].getPlayerOccupies() == p && matrix[rowY+1][colX-1].getPlayerOccupies() == p 
                && matrix[rowY][colX].getPlayerOccupies() == p && matrix[rowY-1][colX+1].getPlayerOccupies() == p)
            {
                changeToWinner(matrix[rowY+2][colX-2], winnerColor);
                changeToWinner(matrix[rowY+1][colX-1], winnerColor);
                changeToWinner(matrix[rowY][colX], winnerColor);
                changeToWinner(matrix[rowY-1][colX+1], winnerColor);
                return true;
            }
            
        }
        if (rowY <= 4 && rowY > 1 && colX >= 1  && colX < 5)
        {
            if(matrix[rowY+1][colX-1].getPlayerOccupies() == p && matrix[rowY][colX].getPlayerOccupies() == p 
                && matrix[rowY-1][colX+1].getPlayerOccupies() == p && matrix[rowY-2][colX+2].getPlayerOccupies() == p)
            {
                changeToWinner(matrix[rowY+1][colX-1], winnerColor);
                changeToWinner(matrix[rowY][colX], winnerColor);
                changeToWinner(matrix[rowY-1][colX+1], winnerColor);
                changeToWinner(matrix[rowY-2][colX+2], winnerColor);
                return true;
            }
            
        }
        return false;
    }

    private static boolean checkDiagonalTwoRTest(GameButton[][] matrix, int colX, int rowY, int x, int p, Color winnerColor)
    {
        // if (rowY == 4 && colX > 0 && colX < 5)
        // {
        //     if(matrix[rowY+1][colX-1].getPlayerOccupies() == p && matrix[rowY][colX].getPlayerOccupies() == p 
        //         && matrix[rowY-1][colX+1].getPlayerOccupies() == p && matrix[rowY-2][colX+2].getPlayerOccupies() == p)
        //     {
        //         changeToWinner(matrix[rowY+1][colX-1], winnerColor);
        //         changeToWinner(matrix[rowY][colX], winnerColor);
        //         changeToWinner(matrix[rowY-1][colX+1], winnerColor);
        //         changeToWinner(matrix[rowY-2][colX+2], winnerColor);
        //         return true;
        //     }
            
        // }
        // else if (rowY == 1 && colX > 1 && colX < 6)
        // {
        //     if(matrix[rowY+2][colX-2].getPlayerOccupies() == p && matrix[rowY+1][colX-1].getPlayerOccupies() == p 
        //         && matrix[rowY][colX].getPlayerOccupies() == p && matrix[rowY-1][colX+1].getPlayerOccupies() == p)
        //     {
        //         changeToWinner(matrix[rowY+2][colX-2], winnerColor);
        //         changeToWinner(matrix[rowY+1][colX-1], winnerColor);
        //         changeToWinner(matrix[rowY][colX], winnerColor);
        //         changeToWinner(matrix[rowY-1][colX+1], winnerColor);
        //         return true;
        //     }
            
        // }
        if (rowY <= 3 && rowY > 0 && colX > 0  && colX <= 4)
        {
            if(matrix[rowY+2][colX+2].getPlayerOccupies() == p && matrix[rowY+1][colX+1].getPlayerOccupies() == p 
                && matrix[rowY][colX].getPlayerOccupies() == p && matrix[rowY-1][colX-1].getPlayerOccupies() == p)
            {
                changeToWinner(matrix[rowY+2][colX+2], winnerColor);
                changeToWinner(matrix[rowY+1][colX+1], winnerColor);
                changeToWinner(matrix[rowY][colX], winnerColor);
                changeToWinner(matrix[rowY-1][colX-1], winnerColor);
                return true;
            }
            
        }
        if (rowY <= 4 && rowY > 1 && colX > 1  && colX <= 5)
        {
            if(matrix[rowY+1][colX+1].getPlayerOccupies() == p && matrix[rowY][colX].getPlayerOccupies() == p 
                && matrix[rowY-1][colX-1].getPlayerOccupies() == p && matrix[rowY-2][colX-2].getPlayerOccupies() == p)
            {
                changeToWinner(matrix[rowY+1][colX+1], winnerColor);
                changeToWinner(matrix[rowY][colX], winnerColor);
                changeToWinner(matrix[rowY-1][colX-1], winnerColor);
                changeToWinner(matrix[rowY-2][colX-2], winnerColor);
                return true;
            }
            
        }
        return false;
    }

    public static boolean checkWinner(GameButton[][] matrix, int colX, int rowY, Color winnerColor) {


        int p = matrix[rowY][colX].getPlayerOccupies();

        if (p == 0) return false;

        for (int i = 0; i < 4; i++){
            if (matrix[rowY][i].getPlayerOccupies() == p && matrix[rowY][i+1].getPlayerOccupies() == p
                && matrix[rowY][i+2].getPlayerOccupies() == p && matrix[rowY][i+3].getPlayerOccupies() == p){
                changeToWinner(matrix[rowY][i], winnerColor);
                changeToWinner(matrix[rowY][i+1], winnerColor);
                changeToWinner(matrix[rowY][i+2], winnerColor);
                changeToWinner(matrix[rowY][i+3], winnerColor);
                
                return true;
            }
        }

        for (int i = 5; i > 2; i--){
            if (matrix[i][colX].getPlayerOccupies() == p && matrix[i-1][colX].getPlayerOccupies() == p
                && matrix[i-2][colX].getPlayerOccupies() == p && matrix[i-3][colX].getPlayerOccupies() == p){
                changeToWinner(matrix[i][colX], winnerColor);
                changeToWinner(matrix[i-1][colX], winnerColor);
                changeToWinner(matrix[i-2][colX], winnerColor);
                changeToWinner(matrix[i-3][colX], winnerColor);
                return true;
            }
        }

        if(checkDiagonalDL(matrix,colX,rowY, 0,p, winnerColor) || checkDiagonalDR(matrix,colX,rowY, 0,p, winnerColor)
            || checkDiagonalUL(matrix,colX,rowY, 0,p, winnerColor) || checkDiagonalUR(matrix,colX,rowY, 0,p, winnerColor)
            || checkDiagonalTwoLTest(matrix,colX,rowY, 0,p, winnerColor) || checkDiagonalTwoRTest(matrix,colX,rowY, 0,p, winnerColor))
        {
            return true;
        }

        return false;

    }

    private static String colorHexToCSS(String c) {
		return ("#" + c.substring(2));
	}
    
    private static void changeToWinner(GameButton b, Color c) {
        b.setStyle(
            "-fx-focus-color: transparent; " +
            "-fx-background-color: " +
            colorHexToCSS(c.toString())
        );
    }

    /**
     * =========================================================================
     * Only used for testing
     * =========================================================================
     */

    private static boolean checkDiagonalDRTest(Integer[][] matrix, int colX, int rowY, int x, int p) 
    {
        if (x == 3){
            return true;
        }
        else if ((colX >= 6) || (rowY >= 5)){
            return false;
        }
        else if (matrix[rowY][colX] == p &&  matrix[rowY+1][colX+1] == p){
            return checkDiagonalDRTest(matrix, colX+1, rowY+1, x+1, p);
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalDLTest(Integer[][] matrix, int colX, int rowY, int x, int p) 
    {
        if (x == 3){
            return true;
        }
        else if ((colX <= 0) || (rowY >= 5)){
            return false;
        }
        else if (matrix[rowY][colX] == p &&  matrix[rowY+1][colX-1] == p){
            return checkDiagonalDLTest(matrix, colX-1, rowY+1, x+1, p);
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalULTest(Integer[][] matrix, int colX, int rowY, int x, int p) 
    {
        if (x == 3){
            return true;
        }
        else if ((colX <= 0) || (rowY <= 0)){
            return false;
        }
        else if (matrix[rowY][colX] == p &&  matrix[rowY-1][colX-1] == p){
            return checkDiagonalULTest(matrix, colX-1, rowY-1, x+1, p);
        }
        return false;
        //return true;
    }

    private static boolean checkDiagonalURTest(Integer[][] matrix, int colX, int rowY, int x, int p) 
    {
        if (x == 3){
            return true;
        }
        else if ((colX >= 6) || (rowY <= 0)){
            return false;
        }
        else if (matrix[rowY][colX] == p &&  matrix[rowY-1][colX+1] == p){
            return checkDiagonalURTest(matrix, colX+1, rowY-1, x+1, p);
        }
        return false;
        //return true;
    }

    public static boolean checkWinnerTest(Integer[][] matrix, int colX, int rowY) {

        int p = matrix[rowY][colX];

        if (p == 0) return false;

        for (int i = 0; i < 4; i++){
            if (matrix[rowY][i] == p && matrix[rowY][i+1] == p
                && matrix[rowY][i+2] == p && matrix[rowY][i+3] == p){
                return true;
            }
        }

        for (int i = 5; i > 2; i--){
            if (matrix[i][colX] == p && matrix[i-1][colX] == p
                && matrix[i-2][colX] == p && matrix[i-3][colX] == p){
                return true;
            }
        }

        if(checkDiagonalDLTest(matrix,colX,rowY, 0,p) || checkDiagonalDRTest(matrix,colX,rowY, 0,p)
            || checkDiagonalULTest(matrix,colX,rowY, 0,p) || checkDiagonalURTest(matrix,colX,rowY, 0,p))
        {
            return true;
        }

        return false;

    }
}