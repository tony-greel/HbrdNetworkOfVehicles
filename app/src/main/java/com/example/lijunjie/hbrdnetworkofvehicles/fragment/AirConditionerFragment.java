package com.example.lijunjie.hbrdnetworkofvehicles.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.EllipseSeekBar;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.WaveView;

/**
 * Created by lijunjie on 2018/5/31.
 */

public class AirConditionerFragment extends Fragment implements View.OnClickListener{

    private EllipseSeekBar ellipseSeekBar;
    private WaveView waveView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airconditioner, container, false);
        waveView = view.findViewById(R.id.wave_view);
        ellipseSeekBar = view.findViewById(R.id.circle_seekbar);
        binding();
        return view;
    }

    private void binding() {
        waveView.setOnClickListener(this);
        ellipseSeekBar.setListener(degree -> {
            Log.d("LSS", String.valueOf(degree));
            waveView.setBaseLine(degree);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_seekbar:

                break;
        }
    }


}
