import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A JFrame for Conway's Game of Life.
 */
public class GameOfLifeFrame extends TextFrame {

	private static final long serialVersionUID = 1L;
	private String[][] matrice;

	/**
	 * Constructs a frame for Conway's Game of Life.
	 */
	public GameOfLifeFrame() {
		super("Conway's Game of Life");
		addMenuBarGame();
	}

	/**
	 * Visualizes the game frame.
	 */
	public void start() {
		setTextDimension(20, 40);
		setTextEditable(false);
		setResizable(false);
		setVisible(true);
	}

	protected JMenuItem menuGame;

	/**
	 * Adds the game menu bar to the frame.
	 */
	protected void addMenuBarGame() {
		ActionListener menuGameListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play();
			}
		};

		menuGame = new JMenuItem("Play");
		menuGame.setMnemonic(KeyEvent.VK_P);
		menuGame.addActionListener(menuGameListener);
		menuBar.add(menuGame);
	}

	/**
	 * Loads the content from a text file to the game board.
	 * 
	 * @param file the file
	 */
	public void loadTextFile(File file) {
		setTextEditable(true);

		showMessageDialog("The game is loaded from " + file.getName(), "TODO");

		// take the coordinates of the file and put it in a list
		CoordinateIO coor = new CoordinateIO();
		coor.setFile(file);
		ArrayList<Coordinate> listeCoordinate = coor.read();
		if (listeCoordinate.size() != 0) {

			Coordinate dimension = listeCoordinate.get(0);
			int dimX = dimension.getX();
			int dimY = dimension.getY();

			setTextDimension(dimX, dimY);
			setResizable(false);

			// Create a matrix with length and width of the first coordinate of the file
			// fill this matrix with " "
			matrice = new String[dimX][dimY];
			for (int i = 0; i < dimX; i++) {
				Arrays.fill(matrice[i], " ");
			}

			// fill the matrix with "0" for the coordinates of the file
			for (int i = 1; i < listeCoordinate.size(); i++) {
				int x = listeCoordinate.get(i).getX();
				int y = listeCoordinate.get(i).getY();
				matrice[x][y] = "0";
			}

			// print the matrix in the frame
			String loadfile = matriceToString(matrice);
			setText(loadfile);
		}
	}

	// change a matrix into a string
	private static String matriceToString(String[][] matrice) {
		String texte = "";

		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[i].length; j++) {
				texte += matrice[i][j];
			}
			texte += "\n";
		}

		return texte;
	}

	/**
	 * Saves the content from the game board to a text file.
	 * 
	 * @param file the file
	 */
	public void saveTextFile(File file) {
		if (matrice == null) {
			showErrorDialog("You can't save the file : you need to have some cells");
			return;
		}
		try {
			file.createNewFile();
			if (file.canWrite()) {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				writer.print(matriceToFileString(matrice));
				writer.close();
			}
		} catch (IOException exc) {
			showErrorDialog("Failed to save text to file.");
		}
	}

	// convert a matrix in a text to save in a file

	private String matriceToFileString(String[][] matrice) {
		String texte = "";

		int rows = matrice.length;
		int cols = matrice[0].length;

		// dimensions for the first line
		texte += rows + " " + cols + "\n";

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if ("0".equals(matrice[i][j])) {
					texte += i + " " + j + "\n";
				}
			}
		}

		return texte;
	}

	/**
	 * Clears the game board.
	 */
	public void clearText() {
		// clear the matrix
		if (matrice != null) {
			for (int i = 0; i < matrice.length; i++) {
				Arrays.fill(matrice[i], " ");
			}
		}
		textArea.setText("");
	}

	/**
	 * Creates and visualizes the next generation of the cell population.
	 */
	public void play() {
	    if (matrice == null) {
	        showErrorDialog("You can't play : you need to initialize cells");
	        return;
	    }
		// showMessageDialog("The next cell generation is visualized", "TODO");
		// create a new matrix
		int lengthMatrice = matrice.length;
		int widthMatrice = matrice[0].length;
		String[][] nextMatrice = new String[lengthMatrice][widthMatrice];

		for (int i = 0; i < lengthMatrice; i++) {
			for (int j = 0; j < widthMatrice; j++) {
				int neighbors = countNeighbors(matrice, i, j);
				// live cell:
				if (matrice[i][j].equals("0")) {
					if (neighbors == 2 || neighbors == 3)
						nextMatrice[i][j] = "0";
					else
						nextMatrice[i][j] = " ";
				}
				// dead cell:
				else {
					if (neighbors == 3)
						nextMatrice[i][j] = "0";
					else
						nextMatrice[i][j] = " ";
				}
			}
		}
		// the new matrix became the next generation
		matrice = nextMatrice;
		setText(matriceToString(matrice));
	}

	// count Neighbors of a given coordinate
	private int countNeighbors(String[][] m, int x, int y) {
		int count = 0;
		// run through the 8 neighbors of a cell

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				// we don't want to count the cell herself
				if (i != 0 || j != 0) {
					int coorx = x + i;
					int coory = y + j;
					// check if the coordinates are in the matrix
					if (coorx >= 0 && coorx < m.length && coory >= 0 && coory < m[0].length) {
						// count neighbors
						if (m[coorx][coory].equals("0")) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	/**
	 * A small test of the GameOfLifeFrame class.
	 */
	public static void main(String[] args) {
		GameOfLifeFrame frame = new GameOfLifeFrame();
		frame.start();
	}

}
