/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
package algonquin.cst2335.finalproject.SongData;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {SongData.class, History.class}, version = 1, exportSchema = false)
    public abstract class SongsDatabase extends RoomDatabase {
        public abstract SongDao songDao();
        public abstract SearchHistoryDao searchHistoryDao();


        private static volatile SongsDatabase INSTANCE;

        public static SongsDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (SongsDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        SongsDatabase.class, "song_database")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }

