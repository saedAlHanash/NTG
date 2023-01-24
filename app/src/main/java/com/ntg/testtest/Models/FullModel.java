package com.ntg.testtest.Models;

import androidx.room.ColumnInfo;

public class FullModel {
    @ColumnInfo(name = "barcode")
    public String barcode;
    @ColumnInfo(name = "asset_description")
    public String description;
    @ColumnInfo(name = "category_name")
    public String categoryName;
}
