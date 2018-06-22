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
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.loginand.LoginActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.adapter.VehiclesAdapter;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.Car;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CarInformation;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CustomWindowMyCar;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.CustomWindow;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;


public class MainActivity extends BaseActivity implements LocationSource,AMapLocationListener,View.OnClickListener {

    private TextureMapView mapView;
    public AMap aMap = null;

    private CustomWindow customWindow;

    //定位功能
    public LocationSource.OnLocationChangedListener mListener;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;

    public UiSettings mUiSettings;

    //模拟经纬度
    double m = 39.6077319721;
    double b = 116.9188057458;

    public RelativeLayout main_rl, main_rl_whole;

    private DrawerLayout side_pull_drawer;

    private Button main_login_but;

    private ImageView main_img_personal_information;

    private ImageView main_img_vehicle;

    private FloatingActionsMenu main_multiple_actions;
    private FloatingActionButton main_function_management, main_real_time_monitoring, main_historical_track, main_data_statistics;

    private long firstPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        projectileFrame();
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
    }

    /**
     * 弹框数据
     */
    private void projectileFrame() {
        customWindow = new CustomWindow(this);
        List<CustomWindowMyCar> itemList = new ArrayList<>();
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "湘A123FG"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "湘B452DF"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "湘C598VC"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "湘D678AC"));
        itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_side_pull_frame_my_car_two), "湘F534CD"));
        customWindow.setItemList(itemList);
        customWindow.setOnItemSelectListener(position -> Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show());
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

        main_multiple_actions = findViewById(R.id.main_multiple_actions);

        main_function_management = findViewById(R.id.main_function_management);
        main_real_time_monitoring = findViewById(R.id.main_real_time_monitoring);
        main_historical_track = findViewById(R.id.main_historical_track);
        main_data_statistics = findViewById(R.id.main_data_statistics);


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

        main_multiple_actions.setOnClickListener(this);

        main_function_management.setOnClickListener(this);
        main_real_time_monitoring.setOnClickListener(this);
        main_historical_track.setOnClickListener(this);
        main_data_statistics.setOnClickListener(this);

        slidingOperation();
    }

    /**
     * 控件事件监听
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_login_but:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;

            case R.id.side_pull_drawer:
                break;

            case R.id.main_img_personal_information:
                side_pull_drawer.openDrawer(Gravity.LEFT);
                break;

            case R.id.main_img_vehicle:
                SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                String name = sp.getString("name", null);
                if (name != null) {
                    customWindow.showMenu();
                } else {
                    Toast.makeText(this, "请登录，绑定后查看车辆", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.main_multiple_actions:
                break;

            case R.id.main_function_management:
                Toast.makeText(this, "此功能暂未开放，敬请期待！！", Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_real_time_monitoring:
                SharedPreferences s = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                String name_2 = s.getString("name", null);
                if (name_2 != null) {
                    Intent main_fab_2_intent = new Intent(this, VehicleControlActivity.class);
                    startActivity(main_fab_2_intent);
                } else {
                    Toast.makeText(this, "请登录，绑定车辆后查看车辆历史轨迹", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.main_historical_track:
                SharedPreferences s_1 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                String name_3 = s_1.getString("name", null);
                if (name_3 != null) {
                    Intent main_fab_1_intent = new Intent(this, HistoricalTrackActivity.class);
                    startActivity(main_fab_1_intent);
                } else {
                    Toast.makeText(this, "请登录，绑定车辆后查看车辆", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.main_data_statistics:
                Toast.makeText(this, "此功能暂未开放，敬请期待！！", Toast.LENGTH_SHORT).show();
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
     * 如果在AndroidManifest中调用singleTask启动模式，此方法必须重写
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mapInitialization();
    }

    /**
     * 点击两次退出程序
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }

    /**
     * 初始化地图
     */
    private void mapInitialization() {
        // 获取登录或注册界面的缓存
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        //判断是否被缓存
        if (name != null) {
            main_rl.setVisibility(View.GONE); // 如果有用户缓存则消失隐藏布局

            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);

            aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
            mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

            if (getLocationPre() && getReadExternalMemory()) {
                Log.e("TAT", "今日");
                initMapStyleFile(this, "style.data");
            }
            if (aMap != null) {
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
            }
        } else {
            Toast.makeText(this, "请登录或者注册绑定一下车辆,这样我们将更好的为您服务！！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过集合批量传入车辆
     *
     * @param cars
     */
    private void load(List<Car> cars) {
        for (Car car : cars) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.draggable(false);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.vehicle)));
            markerOption.position(car.getLatLng());
            Log.e("TAG", car.getLatLng().latitude + " " + car.getLatLng().longitude);
            Marker e = aMap.addMarker(markerOption);
            //可以给定位点绑定一个信息对象
            e.setObject(car);
            //允许定位点显示信息窗口
            e.setTitle(car.getId());
            e.setSnippet(car.getInfo());
//            requestVehicleInformation();
        }
    }

    /**
     * 请求车辆信息
     *
     * @param
     */
    private void requestVehicleInformation() {
        String url = "http://1517a91z44.iask.in:35052/SelectLastPlace";

        SharedPreferences sp = this.getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        showProgressDialog();
        FormBody formBody = new FormBody
                .Builder()
                .add("UserSerial", name)
                .build();
        NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
            @Override
            public void onError(Request request, IOException e) {
                Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d("TAG", "lss" + result);
                jsonAnalytic(result);
                dismiss();
            }
        });
    }

    /**
     * 解析用户ID里面的所有车辆
     * @param result
     * @return
     */
    private List<Car>jsonAnalytic(String result) {
        List<Car> list = new ArrayList<>();

        Log.d("SF", result);
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray children = jsonObject.getJSONArray("place");
            for (int j = 0; j < children.length(); j++) {
                jsonObject = children.getJSONObject(j);

                Car car = new Car("","","");

                car.setLongitude(jsonObject.getString("positionY"));
                car.setLatitude(jsonObject.getString("positionX"));

                list.add(car);
                for(int i = 0; i<list.size(); i++){
                    Log.d("GH", String.valueOf(list.get(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
