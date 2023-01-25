package com.ntg.testtest.ViewModel;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntg.testtest.DB.Database;
import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.Models.Category;
import com.ntg.testtest.Models.FullModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AssetViewModel extends ViewModel {
    private static final String TAG = "AssetViewModel";

    public MutableLiveData<Pair<Boolean, String>> successLiveData = new MutableLiveData<>();
    public MutableLiveData<Pair<List<Category>, String>> categoryLiveData = new MutableLiveData<>();
    public MutableLiveData<Pair<FullModel, String>> assetLiveData;
    public MutableLiveData<Pair<List<AssetModel>, String>> allAssetLiveData = new MutableLiveData<>();

    /**
     * add new category into data base
     *
     * @param db       Data base instance
     * @param category new category model
     * @return live data object to observe process
     */
    public LiveData<Pair<Boolean, String>> insertCategory(Database db, Category category) {
        db.categoryDAO()
                .insertCategory(category)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        successLiveData.postValue(new Pair<>(true, null));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        successLiveData.postValue(new Pair<>(false, e.getMessage()));
                    }
                });
        return successLiveData;
    }

    /**
     * get all categories from DB
     *
     * @param db Data base instance
     * @return live data object to observe process
     */
    public LiveData<Pair<List<Category>, String>> getCategories(Database db) {
        db.categoryDAO()
                .getAllCategories()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        Log.d(TAG, "onSubscribe: " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Category> categories) {
                        Log.d(TAG, "onComplete: ");
                        categoryLiveData.postValue(new Pair<>(categories, null));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        categoryLiveData.postValue(new Pair<>(null, e.getMessage()));
                    }
                });
        return categoryLiveData;
    }

    /**
     * add new Asset into data base
     *
     * @param db    Data base instance
     * @param model new category model
     * @return live data object to observe process
     */
    public LiveData<Pair<Boolean, String>> insertAsset(Database db, AssetModel model) {
        db.assetsDAO()
                .insertAsset(model)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        successLiveData.postValue(new Pair<>(true, null));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        successLiveData.postValue(new Pair<>(false, e.getMessage()));
                    }
                });
        return successLiveData;
    }

    /**
     * get an asset from DB with barcode
     *
     * @param db Data base instance
     * @return live data object to observe process
     */
    public LiveData<Pair<FullModel, String>> getAsset(Database db, String barcode) {
        assetLiveData = new MutableLiveData<>();
        db.assetsDAO()
                .getAsset(barcode)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull FullModel categories) {
                        assetLiveData.postValue(new Pair<>(categories, null));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        assetLiveData.postValue(new Pair<>(null, e.getMessage()));
                    }
                });

        return assetLiveData;
    }


    public LiveData<Pair<List<AssetModel>, String>> getAllAssets(Database db) {
        allAssetLiveData = new MutableLiveData<>();
        db.assetsDAO()
                .getAllAsset()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<AssetModel> categories) {
                        allAssetLiveData.postValue(new Pair<>(categories, null));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        assetLiveData.postValue(new Pair<>(null, e.getMessage()));
                    }
                });

        return allAssetLiveData;

    }
}
