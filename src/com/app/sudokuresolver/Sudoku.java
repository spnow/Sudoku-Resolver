package com.app.sudokuresolver;

import java.util.LinkedList;

public class Sudoku {
	public static final char empty = '0';
	public static final int stop = 100;
	public static final int sizeBloc = 3;
	public static final int sizeEdge = 9;
	public static final int sizeGrid = sizeEdge * sizeEdge;
	protected LinkedList<String> solution;
	protected StringBuilder grid;
	
	/* Constructor */
	public Sudoku(String rgrid) {
		solution = new LinkedList<String>();
		grid = new StringBuilder(rgrid);
	}

	/* inherit Object */
	@Override
	public String toString() {
		String buffer = new String();
		for (int i = 0; i < sizeGrid; i++)
			buffer += grid.charAt(i);
		return buffer;
	}

	/* check method */
	private boolean surLigne(int k, int i) {
		for (int j = 0; j < sizeEdge; j++) {
			if (grid.charAt(ctop(i, j)) == k)
				return false;
		}
		return true;
	}

	private boolean surColonne(int k, int j) {
		for (int i = 0; i < sizeEdge; i++)
			if (grid.charAt(ctop(i, j)) == k)
				return false;
		return true;
	}

	private boolean surBloc(int k, int i, int j) {
		int m = i - (i % sizeBloc), n = j - (j % sizeBloc);
		for (i = m; i < m + sizeBloc; i++)
			for (j = n; j < n + sizeBloc; j++)
				if (grid.charAt(ctop(i, j)) == k)
					return false;
		return true;
	}

	private boolean check(int value, int position) {
		if (surLigne(value, ptoc(position)[0])
				&& surColonne(value, ptoc(position)[1])
				&& surBloc(value, ptoc(position)[0], ptoc(position)[1]))
			return true;
		return false;
	}

	/**/

	/* Solve */
	public int solve() {
		resolve(0);
		return countSolution();
	}

	private boolean resolve(int position) {
		if (position == sizeGrid) {
			solution.add(toString());
			if (countSolution() < stop) // stop condition
				return false;
			else
				return true;
		}

		if (grid.charAt(position) != empty)
			return resolve(position + 1);

		for (int k = 49; k < 58; k++) { // 49 == 1 && 57 == 9 in base 10
			if (check(k, position)) {
				grid.setCharAt(position, (char) k);
				if (resolve(position + 1))
					return false;
			}
		}

		grid.setCharAt(position, empty);
		return false;
	}

	public String getSolve(int n) {
		if (countSolution() != 0)
			return solution.get(n % countSolution()).toString();
		else
			return "";
	}

	public int countSolution() {
		return solution.size();
	}

	/**/
	
	public boolean validator() {
		for (int position = 0; position < sizeGrid; position++) {
			if (grid.charAt(position) != '0') {
				char tmp = grid.charAt(position);
				grid.setCharAt(position, '0');
				
				if (!check(tmp, position)) {
					return false;
				} else {
					grid.setCharAt(position, tmp);
				}
			}
		}
		return true;
	}

	/**/
	
	private static int[] ptoc(int position) {
		int[] c = new int[2];
		c[0] = position / 9;
		c[1] = position % 9;
		return c;
	}

	private static int ctop(int i, int j) {
		return (i * 9) + (j);
	}
}
