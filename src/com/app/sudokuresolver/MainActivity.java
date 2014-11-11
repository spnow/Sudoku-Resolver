package com.app.sudokuresolver;

import com.app.sudokuwned.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final Button reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Reset", Toast.LENGTH_SHORT)
						.show();
				reset();
			}
		});

		final Button solve = (Button) findViewById(R.id.solve);
		solve.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Loading ...",
						Toast.LENGTH_SHORT).show();

				final Sudoku s = new Sudoku(getGrid());
				if (!s.validator()) {
					Toast.makeText(getBaseContext(), "Grille invalide",
							Toast.LENGTH_SHORT).show();
				} else {
					// solve grid
					s.solve();
					if (s.countSolution() != 0) {
						setGrid(s.getSolve(0));
					} else {
						Toast.makeText(getBaseContext(), "Aucune solution",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	private String getGrid() {
		String grid = new String();
		for (int i = 1; i <= Sudoku.sizeGrid; i++) {
			String sid = "editText" + i;
			int id = getResources().getIdentifier(sid, "id", getPackageName());
			EditText tmpEditText = (EditText) findViewById(id);
			if (tmpEditText.getText().toString().trim().length() != 0) {
				grid += tmpEditText.getText().toString();
			} else {
				grid += '0';
			}
		}
		return grid;
	}

	protected void setGrid(String grid) {
		for (int i = 1; i <= Sudoku.sizeGrid; i++) {
			String sid = "editText" + i;
			int id = getResources().getIdentifier(sid, "id", getPackageName());
			EditText tmpEditText = (EditText) findViewById(id);
			tmpEditText.setText(String.valueOf(grid.charAt(i - 1)));
		}
	}

	private void reset() {
		for (int i = 1; i <= Sudoku.sizeGrid; i++) {
			String sid = "editText" + i;
			int id = getResources().getIdentifier(sid, "id", getPackageName());
			EditText tmpEditText = (EditText) findViewById(id);
			tmpEditText.setText("");
		}
	}
}
