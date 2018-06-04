package com.example.android.habittracer.data;
import android.provider.BaseColumns;

public final class HabitContract {

    private HabitContract() {}

    public static abstract class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habit";
        public static final String COLUMN_ACTIVITY_DESCRIPTION = "description";
        public static final String COLUMN_ACTIVITY_CATEGORY = "category";
        public static final String COLUMN_ACTIVITY_MOMENT = "moment";
        public static final String COLUMN_ACTIVITY_DURATION = "duration";
        public static final String COLUMN_ACTIVITY_PLACE = "place";
        public static final int CATEGORY_HEALTH = 0;
        public static final int CATEGORY_SPIRITUAL = 1;
        public static final int CATEGORY_INTELLECTUAL = 2;
        public static final int CATEGORY_MANUAL = 3;
        public static final int CATEGORY_SOCIAL = 4;
       public static final int MOMENT_DAWN = 0;
        public static final int MOMENT_MORNING = 1;
        public static final int MOMENT_AFTERNOON = 2;
        public static final int MOMENT_EVENING = 3;
        public static final int MOMENT_NIGHT = 4;
    }
}
