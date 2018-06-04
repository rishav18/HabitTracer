package com.example.android.habittracer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.habittracer.data.HabitContract.HabitEntry;
import com.example.android.habittracer.data.HabitDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        mDbHelper = new HabitDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addData:
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDatabaseInfo() {

        Cursor cursor= readAllActivites();
        TextView displayView = (TextView) findViewById(R.id.text_view_habit);
        try {
            displayView.setText("The Habit Activites table contains " + cursor.getCount() + " activities.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_ACTIVITY_DESCRIPTION + " - " +
                    HabitEntry.COLUMN_ACTIVITY_MOMENT + " - " +
                    HabitEntry.COLUMN_ACTIVITY_DURATION + " - " + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int descriptionColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_DESCRIPTION);
            int momentColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_MOMENT);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_DURATION);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                int currentMoment = cursor.getInt(momentColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentDescription + " - " +
                        currentMoment + " - " +
                        currentDuration + " - "));
            }
        } finally {
            cursor.close();
        }
    }
    public Cursor readAllActivites()
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_ACTIVITY_DESCRIPTION,
                HabitEntry.COLUMN_ACTIVITY_MOMENT,
                HabitEntry.COLUMN_ACTIVITY_DURATION,
        };
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

}
