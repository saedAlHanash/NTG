package com.ntg.testtest.DB.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ntg.testtest.Models.Category;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CategoryDAO {
    @Insert
    Completable insertCategory(Category model);

    @Query("SELECT * FROM category_table")
    Single<List<Category>> getAllCategories();
}
