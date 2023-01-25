package com.ntg.testtest.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ntg.testtest.DB.DAO.AssetsDAO;
import com.ntg.testtest.DB.DAO.CategoryDAO;
import com.ntg.testtest.Helper.ImportHelper;
import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.Models.Category;

import java.io.IOException;

import io.reactivex.rxjava3.schedulers.Schedulers;

@androidx.room.Database(entities = {AssetModel.class, Category.class},
        version = 6, exportSchema = false)
public abstract class Database extends RoomDatabase {

    /**
     * singleton instance
     */
    private static Database instance;
    /**
     * name database
     */
    public static String DB_name = "assets_database1";

    public abstract AssetsDAO assetsDAO();

    public abstract CategoryDAO categoryDAO();

    public static synchronized Database getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, Database.class, DB_name)
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                            super.onDestructiveMigration(db);
                            try {
                                new ImportHelper().readFromExl(context.getAssets()
                                        .open("barcods.xla"), instance);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .build();

            //fpr call onDestructiveMigration to import exl file
            instance.assetsDAO().emptyRequest().subscribeOn(Schedulers.computation()).subscribe();
        }

        return instance;
    }
}
