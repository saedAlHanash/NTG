package com.ntg.testtest.UI.Activities;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ntg.testtest.DB.Database;
import com.ntg.testtest.Helper.Fragments.FC;
import com.ntg.testtest.Helper.Fragments.FN;
import com.ntg.testtest.Helper.Fragments.FTH;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Fragments.AddAssetFragment;
import com.ntg.testtest.UI.Fragments.AddCategoryFragment;
import com.ntg.testtest.UI.Fragments.ScanFragment;
import com.ntg.testtest.UI.Fragments.SearchFragment;
import com.ntg.testtest.ViewModel.AssetViewModel;


public class MainActivity extends AppCompatActivity {

    public AssetViewModel assetViewModel;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Database.getInstance(this);//init database instance

        assetViewModel = new ViewModelProvider(this)
                .get(AssetViewModel.class);//init view model

    }

    //region handler
    /**
     * for run in UI thread looper
     */
    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {

                case FragmentsKeys.ADD_CATEGORY: {
                    FTH.addFadeFragmentUpFragment(FC.MAIN_C, MainActivity.this,
                            AddCategoryFragment.class, null, FN.ADD_CATEGORY_FN);
                    break;
                }
                case FragmentsKeys.ADD_ASSET: {
                    FTH.addFadeFragmentUpFragment(FC.MAIN_C, MainActivity.this,
                            AddAssetFragment.class, null, FN.ADD_ASSET_FN);
                    break;
                }
                case FragmentsKeys.SEARCH_NAME: {
                    FTH.addFadeFragmentUpFragment(FC.MAIN_C, MainActivity.this,
                            SearchFragment.class, null, FN.SEARCH_FN);
                    break;
                }
                case FragmentsKeys.SCAN: {
                    FTH.addFadeFragmentUpFragment(FC.MAIN_C, MainActivity.this,
                            ScanFragment.class, null, FN.SCAN_FN);
                    break;
                }

            }
        }
    };

    public void sendHandlerMessage(@FragmentsKeys int key) {
        handler.sendEmptyMessage(key);
    }

    public void sendHandlerMessage(Message message) {
        handler.sendMessage(message);
    }
    //endregion

    @IntDef({
            FragmentsKeys.ADD_CATEGORY,
            FragmentsKeys.ADD_ASSET,
            FragmentsKeys.SEARCH_NAME,
            FragmentsKeys.SCAN,
    })
    public @interface FragmentsKeys {

        int ADD_CATEGORY = 0;
        int ADD_ASSET = 1;
        int SEARCH_NAME = 2;
        int SCAN = 3;
    }

    private static final String TAG = "MainActivity__";
}
