package com.ntg.testtest.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "asset_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "category_id",
                        childColumns = "category_id")
        })
public class AssetModel {

    @PrimaryKey()
    @ColumnInfo(name = "barcode")
    @NonNull
    private String barcode;

    @ColumnInfo(name = "category_id")
    private long categoryId;

    @ColumnInfo(name = "asset_description")
    private String assetDescription;


    public AssetModel(String barcode, String assetDescription, long categoryId) {
        this.assetDescription = assetDescription;
        this.barcode = barcode;
        this.categoryId = categoryId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public String toString() {
        return "AssetCategoryModel{" +
                "barcode='" + barcode + '\'' +
                ", assetDescription='" + assetDescription + '\'' +
                ", assetCategory='" + categoryId + '\'' +
                '}';
    }
}
