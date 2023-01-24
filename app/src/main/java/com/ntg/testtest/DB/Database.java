package com.ntg.testtest.DB;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ntg.testtest.DB.DAO.AssetsDAO;
import com.ntg.testtest.DB.DAO.CategoryDAO;
import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.Models.Category;

@androidx.room.Database(entities = {AssetModel.class, Category.class}, version = 7, exportSchema = false)
public abstract class Database extends RoomDatabase {

    /**
     * singleton instance
     */
    private static Database instance;

    public abstract AssetsDAO assetsDAO();
    public abstract CategoryDAO categoryDAO();

    /**
     * name database
     */
    public static String DB_name = "assets_database";


    public static synchronized Database getInstance(Context context) {

        if (instance == null)
            instance = Room.databaseBuilder(context, Database.class, DB_name)
                    .fallbackToDestructiveMigration()
                    .build();

        return instance;
    }
}
