package com.example.lijunjie.hbrdnetworkofvehicles.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.loginand.LoginActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.Car;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CustomWindowMyCar;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.CustomWindow;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements LocationSource,AMapLocationListener,View.OnClickListener{

    private MapView mapView;
    public AMap aMap = null;

    private CustomWindow customWindow;

    //定位功能
    public LocationSource.OnLocationChangedListener mListener;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;

    public UiSettings mUiSettings ;

    //模拟经纬度
    double m = 39.6077319721;
    double b = 116.9188057458;

    public RelativeLayout main_rl , main_rl_whole;

    private DrawerLayout side_pull_drawer;

    private Button main_login_but;

    private ImageView main_img_personal_information ;

    private ImageView main_img_vehicle;

    private FloatingActionsMenu main_fab_menu;
    private FloatingActionButton main_fab_1 , main_fab_2 , main_fab_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();

        setContentView(R.layout.activity_main);
        initialization();
        binding();
        mapView.onCreate(savedInstanceState);
        mapInitialization();
    }


    /**
     * 设置状态栏透明
     */
    private void statusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 26) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //弹框数据
        customWindow = new CustomWindow(this);
        List<CustomWindowMyCar> itemList = new ArrayList<>();
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "All"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "CheckIn"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "Cancel"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "Chat"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "Complete"));
        customWindow.setItemList(itemList);
        customWindow.setOnItemSelectListener(new CustomWindow.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 控件初始化
     */
    private void initialization() {
        //地图控件绑定
        mapView = findViewById(R.id.main_map);
        aMap = mapView.getMap();

        //设置布局颜色透明度
        main_rl_whole = findViewById(R.id.main_rl_whole);
        main_rl = findViewById(R.id.main_rl);
        main_rl.getBackground().setAlpha(190);

        main_login_but = findViewById(R.id.main_login_but);

        side_pull_drawer = findViewById(R.id.side_pull_drawer);

        main_img_personal_information = findViewById(R.id.main_img_personal_information);
        main_img_vehicle = findViewById(R.id.main_img_vehicle);

        main_fab_menu = findViewById(R.id.main_fab_menu);

        main_fab_1 = findViewById(R.id.main_fab_1);
        main_fab_2 = findViewById(R.id.main_fab_2);
        main_fab_3 = findViewById(R.id.main_fab_3);

    }

    /**
     * main调用singleTask后跳转的数据必须重写此方法并且在该方法中完成数据对接
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        if (name != null){
            main_rl.setVisibility(View.GONE);
        }
    }

    /**
     * 监听初始化
     */
    private void binding() {
        main_login_but.setOnClickListener(this);
        side_pull_drawer.setOnClickListener(this);
        main_img_personal_information.setOnClickListener(this);
        main_img_vehicle.setOnClickListener(this);
        aMap.setLocationSource(this);// 设置定位监听

        main_fab_menu.setOnClickListener(this);
        main_fab_1.setOnClickListener(this);
        main_fab_2.setOnClickListener(this);
        main_fab_3.setOnClickListener(this);

        slidingOperation();
    }

    /**
     * 控件事件监听
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_login_but:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                break;

            case R.id.side_pull_drawer:
                break;

            case R.id.main_img_personal_information:
                side_pull_drawer.openDrawer(Gravity.LEFT);
                break;

            case R.id.main_img_vehicle:
                SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                String name = sp.getString("name", null);
                if (name != null){
                    customWindow.showMenu();
                }else {
                    Toast.makeText(this, "请登录后查看车辆", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.main_fab_menu:
                break;

            case R.id.main_fab_1:
                Intent main_fab_1_intent = new Intent(this, HistoricalTrackActivity.class);
                startActivity(main_fab_1_intent);
                break;

            case R.id.main_fab_2:
                break;

            case R.id.main_fab_3:
                break;
        }
    }


    /**
     * 侧拉框事件处理
     */
    private void slidingOperation() {
        side_pull_drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                main_rl_whole.setX(slideOffset * drawerView.getWidth());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }


    /**
     * 初始化地图
     */
    private void mapInitialization() {
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);

            aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
            mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

            if (getLocationPre() && getReadExternalMemory()) {
                Log.e("TAT", "今日");
                initMapStyleFile(this, "style.data");
            }
            if (aMap != null && name != null) {
                MyLocationStyle locationStyle = new MyLocationStyle();
                locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.vehicle));
                aMap.setMyLocationStyle(locationStyle);
                aMap.setLocationSource(this);
                aMap.getUiSettings().setMyLocationButtonEnabled(true);
                aMap.setMyLocationEnabled(true);

                List<Car> cars = new ArrayList<>();
                for (int i = 0; i <= 10; i++) {
                    LatLng latLng = new LatLng(m + i, b + i);
                    Car car = new Car(latLng, "车牌号码" + i, "车辆信息" + i);
                    Log.e("TAG", latLng.latitude + "  " + latLng.longitude);
                    cars.add(car);
                }
                load(cars);
        }else {
                Toast.makeText(this, "请登录或者注册绑定一下车辆,这样我们将更好的为您服务！！", Toast.LENGTH_SHORT).show();
            }
    }


    /**
     * 通过集合批量传入车辆
     * @param cars
     */
    private void load(List<Car> cars) {
        for(Car car:cars){
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.draggable(false);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.drawable.vehicle)));
            markerOption.position(car.getLatLng());
            Log.e("TAG", car.getLatLng().latitude+" "+car.getLatLng().longitude);
            Marker e = aMap.addMarker(markerOption);
            //可以给定位点绑定一个信息对象
            e.setObject(car);
            //允许定位点显示信息窗口
            e.setTitle(car.getId());
            e.setSnippet(car.getInfo());
        }
    }


    /**
     * 初始化地图样式文件
     * @param context 初始化所需上下文
     * @param fileName 文件名
     * @return 是否初始化成功
     */
    public void initMapStyleFile(Context context,String fileName){
        File file = new File("/sdcard/"+fileName);
        if(file.exists()){
            aMap.setCustomMapStylePath("/sdcard/style.data");
            aMap.setMapCustomEnable(true);
            return;
        }

        AssetManager manager = context.getAssets();
        try {
            InputStream is = manager.open(fileName);
            FileOutputStream fo = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count;
            while((count=is.read(buffer))!=-1)
                fo.write(buffer,0,count);
            fo.flush();
            is.close();
            fo.close();

            Log.e("TAT","执行");
            aMap.setCustomMapStylePath("/sdcard/style.data");
            aMap.setMapCustomEnable(true);
        } catch (IOException e) {
            Log.e("TAT",e.getMessage()+"ljj");
        }
    }

    @Override
    public void onGrantPermission() {
        super.onGrantPermission();
        initMapStyleFile(MainActivity.this, "style.data");
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && mListener != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
            }
            else {
                String errText = "定位失败！！" + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("报TM错",errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }
}
