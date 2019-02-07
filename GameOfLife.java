import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * THE GAME OF LIFE
 * 
 * Game Description: There is 2D infinite grid(in the current version of the
 * game user decides how big is the grid - all cells out of the user's grid are
 * considered always dead ). User populates the grid with cells and can see the
 * result after, one iteration at a time.
 * 
 * Game Rules: The cell survives if: it has 2 or 3 neighbors/ The cell dies if:
 * it has less than 2 or more than 3 neighbors/ A new cell is created if: an
 * empty position has exactly 3 neighbors
 **/

public class GameOfLife {

//******************************<MAIN BEGIN>******************************//

	public static void main(String[] args) {

		int gridSize;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to The Game of Life!\n");

		// decide the grid(matrix) size
		System.out.println("Enter grid size(whole number): ");
		while (true) {
			try {
				gridSize = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Wrong entry(try again): ");
				sc.nextLine();
			}
		}

		// create grid(matrix)
		int[][] matrix = new int[gridSize][gridSize];
		printMatrix(matrix);

		// user populates grid(matrix) with cells
		while (true) {
			System.out.println(
					"Populate the grid with cells(Enter row and column numbers accordingly), or\nType anything else to finish:");
			try {
				int gridRow = sc.nextInt();
				sc.nextLine();
				int gridColumn = sc.nextInt();
				matrix[gridRow - 1][gridColumn - 1] = 1;

				// print the grid(matrix) with the new cell in
				printMatrix(matrix);
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Are you sure you want to finish(y/n)?");
				String nextEntry = sc.nextLine();
				if (nextEntry.equals("y") || nextEntry.equals("Y")) {
					break;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Wrong entry(try again): ");
			}
		}

		// grid(matrix) iterations
		String input;
		int iterationNumber = 1;
		while (true) {
			if (isThereLiveCells(matrix) == false) {
				System.out.println("All cells are dead. Game Over!");
				input = "q";
			} else {
				System.out.println("For next iteration: press 'n'\n" + "To end the game: press 'q'");
				input = sc.nextLine();
			}
			if (input.equals("n")) {
				System.out.println("-----------------------------------------\nIteration " + iterationNumber);
				iterationNumber++;
				printMatrix(matrixAfterOneIteration(matrix));
				System.out.println("-----------------------------------------\n");

				matrix = matrixAfterOneIteration(matrix);
				// quit
			} else if (input.equals("q")) {
				sc.close();
				System.exit(0);//GAME END
			}
		}
	}

//******************************<MAIN END>******************************//

	private static int[][] matrixAfterOneIteration(int[][] matrix) {

		int[][] returnMatrix = new int[matrix.length][matrix.length];

		// creating biggerMatrix same as matrix but surrounded by layer of zeros
		int[][] biggerMatrix = new int[matrix.length + 2][matrix.length + 2];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				biggerMatrix[i + 1][j + 1] = matrix[i][j];
			}
		}

//		System.out.println("biggerMatrix\n");
//		printMatrix(biggerMatrix);
//		System.out.println("\n");

		// sweeping through the grid(matrix)
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {

//---------------Applying the rules of the game - cell lives, dies, or is created---------------//

				// if live cell found
				if (matrix[i][j] == 1) {
					// then if cell has less than 2 neighbors or more than 3 it dies
					if (!isCellAlive(i + 1, j + 1, biggerMatrix)) {
						returnMatrix[i][j] = 0;
						// else cell stays alive if cell has exactly 2 or 3 neighbors
					} else {
						returnMatrix[i][j] = 1;
					}
					// if dead cell found
				} else {
					// if dead cell has exactly 3 neighbors then new cell is born
					if (createNewCell(i + 1, j + 1, biggerMatrix)) {
						returnMatrix[i][j] = 1;
						// else cell stays dead
					} else {
						returnMatrix[i][j] = 0;
					}
				}
			}
		}
		// return the new updated matrix

		return returnMatrix;
	}
	
	//create new cell condition check
	private static boolean createNewCell(int i, int j, int[][] biggerMatrix) {

		int neighbors = 0;
		// counting the neighbors of the cell
		for (int k = i - 1; k < i + 2; k++) {
			for (int n = j - 1; n < j + 2; n++) {
				if (biggerMatrix[k][n] == 1) {
					neighbors++;
				}
			}
		}
		if (neighbors == 3) {
			return true;
		} else {
			return false;
		}
	}

	//cell stays alive condition check
	private static boolean isCellAlive(int i, int j, int[][] biggerMatrix) {
//		System.out.println("live cell is found: " + i + j);
		int neighbors = -1;// -1 to compensate that the cell count itself
		// counting the neighbors of the cell
		for (int k = i - 1; k < i + 2; k++) {
			for (int n = j - 1; n < j + 2; n++) {
				if (biggerMatrix[k][n] == 1) {
					neighbors++;
//							System.out.println("neighbors" + "[" + k + "]" + "[" + n + "]");
				}
			}
		}
//		System.out.println("neighbors: " + neighbors);

		if (neighbors == 2 || neighbors == 3) {
//			System.out.println("cell lives\n");
			return true;
		} else {
//			System.out.println("cell dies\n");
			return false;
		}
	}
	
	//display grid with the live cells in it
	private static void printMatrix(int[][] matrix) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 1) {
					System.out.print("o|");
				} else {
					System.out.print(" |");
				}
			}
			System.out.println();
		}
	}
	
	//check if there are live cells left at all
	private static boolean isThereLiveCells(int[][] matrix) {
		int sumMatrix = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 1) {
					sumMatrix++;
				}
			}
		}
		if (sumMatrix < 1) {
			return false;
		} else {
			return true;
		}
	}
}