package com.example.android.habittracer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracer.data.HabitContract.HabitEntry;
import com.example.android.habittracer.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity {
    private EditText mDescriptionEditText;
    private Spinner mCategorySpinner;
    private Spinner mMomentSpinner;
    private EditText mDurationEditText;
    private EditText mPlaceEditText;
    private int mCategory = 0;
    private int mMoment = 0;
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mDescriptionEditText = (EditText) findViewById(R.id.edit_activity_description);
        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mMomentSpinner = (Spinner) findViewById(R.id.spinner_moment);
        mDurationEditText = (EditText) findViewById(R.id.edit_activity_duration);
        mPlaceEditText = (EditText) findViewById(R.id.edit_activity_place);

        setupCategorySpinner();
        setupMomentSpinner();

        mDbHelper = new HabitDbHelper(this);
    }

    private void setupCategorySpinner() {
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(categorySpinnerAdapter);
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.category_health))) {
                        mCategory = HabitEntry.CATEGORY_HEALTH;
                    } else if (selection.equals(getString(R.string.category_spiritual))) {
                        mCategory = HabitEntry.CATEGORY_SPIRITUAL;
                    } else if (selection.equals(getString(R.string.category_intellectual))) {
                        mCategory = HabitEntry.CATEGORY_INTELLECTUAL;
                    } else if (selection.equals(getString(R.string.category_manual))) {
                        mCategory = HabitEntry.CATEGORY_MANUAL;
                    } else {
                        mCategory = HabitEntry.CATEGORY_SOCIAL;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = 0;
            }
        });
    }

    private void setupMomentSpinner() {
        ArrayAdapter momentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_moment_options, android.R.layout.simple_spinner_item);
        momentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mMomentSpinner.setAdapter(momentSpinnerAdapter);
        mMomentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.moment_dawn))) {
                        mCategory = HabitEntry.MOMENT_DAWN; // Dawn
                    } else if (selection.equals(getString(R.string.moment_morning))) {
                        mCategory = HabitEntry.MOMENT_MORNING; // Morning
                    } else if (selection.equals(getString(R.string.moment_afternoon))) {
                        mCategory = HabitEntry.MOMENT_AFTERNOON; // Afternoon
                    } else if (selection.equals(getString(R.string.moment_evening))) {
                        mCategory = HabitEntry.MOMENT_EVENING; // Evening
                    } else {
                        mCategory = HabitEntry.MOMENT_NIGHT; // Night
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMoment = 0; // Dawn
            }
        });
    }

    private void insertActivity() {
        String descriptionString = mDescriptionEditText.getText().toString().trim();
        int durationInt = Integer.parseInt(mDurationEditText.getText().toString().trim());
        String placeString = mPlaceEditText.getText().toString().trim();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_ACTIVITY_DESCRIPTION, descriptionString);
        values.put(HabitEntry.COLUMN_ACTIVITY_CATEGORY, mCategory);
        values.put(HabitEntry.COLUMN_ACTIVITY_MOMENT, mMoment);
        values.put(HabitEntry.COLUMN_ACTIVITY_DURATION, durationInt);
        values.put(HabitEntry.COLUMN_ACTIVITY_PLACE, placeString);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        if (newRowId != -1) {
            text = "Activity saved with Id: " + newRowId;
        } else {
            text = "Error with saving activity";
        }
        Toast.makeText(context, text, duration).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertActivity();
                finish();

                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}