package com.ntg.testtest.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ntg.testtest.Helper.BarcodeHelper;
import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
created on 25/01/2023 - 6:18 pm
to project NTG test
*/
public class AdapterAssets extends RecyclerView.Adapter<AdapterAssets.Holder> {

    Activity activity;
    List<AssetModel> list;

    OnItemClicked listener;

    public AdapterAssets(Activity activity, List<AssetModel> list) {
        this.activity = activity;
        this.list = list;
    }

    /**
     * set new Item List to the adapter and refreshing all item
     *
     * @param list list of new Item {@link #list}
     */
    public void setAndRefresh(List<AssetModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_barcode, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        var item = getItem(position);

        holder.name.setText(item.getBarcode());

        var width = holder.imageView.getWidth();
        var height = holder.imageView.getHeight();

        var bitmap = BarcodeHelper.generateBarcode(item.getBarcode(), height, width);

        holder.imageView.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @SuppressLint("NonConstantResourceId")
    public static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.name)
        TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * set and init listener to take an action when an item is pressed
     *
     * @param listener Object form interface @{@link OnItemClicked}
     */
    public void setOnItemClicked(OnItemClicked listener) {
        this.listener = listener;
    }

    public interface OnItemClicked {
        /**
         * to call it when item clicked
         *
         * @param position index item in adapter and list
         * @param list     items list of AssetModel
         */
        void onClicked(int position, List<AssetModel> list);
    }

    /**
     * get an item form {@link #list}
     *
     * @param i position adapter == (index in list)
     * @return object AssetModel from list {@link #list}
     */
    public AssetModel getItem(int i) {
        // return item in index i
        return this.list.get(i);
    }

    /**
     * delete an item form {@link #list}
     *
     * @param i position adapter == (index in list)
     */
    public void deleteItem(int i) {
        // delete item with index i in the list
        this.list.remove(i);
        this.notifyItemRemoved(i);
    }

    /**
     * editing an item form {@link #list}
     *
     * @param i    @param i position adapter == (index in list)
     * @param item new Item data
     */
    public void editItem(int i, AssetModel item) {
        // replace item in index i with new item
        this.list.set(i, item);
        this.notifyItemChanged(i);
    }

    /**
     * adding an item to begin {@link #list}
     *
     * @param item new Item to add it to the list
     */
    public void insertItem(AssetModel item) {
        // add item in begin of list
        this.list.add(0, item);
        this.notifyItemInserted(0);
    }


}
