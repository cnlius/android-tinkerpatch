package com.jason.tinkerpatch;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jason.tinkerpatch.databinding.ActivityMainBinding;
import com.jason.tinkerpatch.utils.AppUtils;
import com.tinkerpatch.sdk.TinkerPatch;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        initData();
    }

    private void initData() {
        mBinding.tvVersionName.setText("AppVersion："+ AppUtils.getAppVersionName(this));
        Integer patchVersion = TinkerPatch.with().getPatchVersion();
        mBinding.tvPatchName.setText("终极补丁："+patchVersion);
        Toast.makeText(this, "终极补丁:"+patchVersion , Toast.LENGTH_SHORT).show();
    }
}
