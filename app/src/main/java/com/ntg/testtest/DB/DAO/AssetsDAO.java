package com.ntg.testtest.DB.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.Models.FullModel;

import java.util.List;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AssetsDAO {
    @Insert
    Completable insertAsset(AssetModel model);

    @Query("SELECT * FROM asset_table " +
            "WHERE barcode = -1")
    Single<List<AssetModel>> emptyRequest();

    @Query("SELECT barcode,asset_description,category_name " +
            "FROM asset_table " +
            "INNER JOIN category_table ON asset_table.category_id = category_table.category_id " +
            "WHERE barcode = :barcode")
    Single<FullModel> getAsset(String  barcode);

    @Query("SELECT * FROM asset_table " +
            "WHERE barcode = :barcode")
    AssetModel getAssetModel(String  barcode);

    @Query("SELECT * FROM asset_table ")
    Single<List<AssetModel>> getAllAsset();
}
