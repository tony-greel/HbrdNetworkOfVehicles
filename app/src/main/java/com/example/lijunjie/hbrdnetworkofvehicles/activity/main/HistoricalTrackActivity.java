package com.example.lijunjie.hbrdnetworkofvehicles.activity.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.Car;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CustomWindowMyCar;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.HistoricalCustomWindow;
import com.example.lijunjie.hbrdnetworkofvehicles.util.Date;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

/**
 * Created by lijunjie on 2018/6/1.
 */

public class HistoricalTrackActivity extends BaseActivity implements View.OnClickListener{

    private MapView historical_mapView;
    public AMap historical_aMap = null;
    public UiSettings historical_UiSettings;
    private Polyline mPolyline;


    private HistoricalCustomWindow historicalCustomWindow;

    private ImageView historical_security_img_back , historical_img_start;

    private TextView historical_tv_start;

    //模拟经纬度
    double m = 39.6077319721;
    double b = 116.9188057458;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_track);
        initialization();
        mapInitialization();
        binding();
        historical_mapView.onCreate(savedInstanceState);
    }

    private void initialization() {
        //地图控件绑定
        historical_mapView = findViewById(R.id.historical_map);
        historical_aMap = historical_mapView.getMap();

        historical_security_img_back = findViewById(R.id.historical_security_img_back);

        historical_img_start = findViewById(R.id.historical_img_start);

        historical_tv_start = findViewById(R.id.historical_tv_start);

        historicalCustomWindow = new HistoricalCustomWindow(this);
        List<CustomWindowMyCar> itemList = new ArrayList<>();
        List<Date> dates=Date.generate();
        for(Date d:dates){
            itemList.add(new CustomWindowMyCar(getResources().getDrawable(R.drawable.img_circular_two), d.getDay()+" "+d.getWeek()));
        }
        historicalCustomWindow.setItemList(itemList);
        historicalCustomWindow.setOnItemSelectListener(new HistoricalCustomWindow.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position) {
                switch (position){
                    case 0:
                        Toast.makeText(HistoricalTrackActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(HistoricalTrackActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(HistoricalTrackActivity.this, "3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(HistoricalTrackActivity.this, "4", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(HistoricalTrackActivity.this, "5", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(HistoricalTrackActivity.this, "6", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(HistoricalTrackActivity.this, "7", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        historical_mapView.onResume();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        historical_mapView.onPause();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        historical_mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        historical_mapView.onDestroy();

    }

    private void binding() {
        historical_security_img_back.setOnClickListener(this);
        historical_img_start.setOnClickListener(this);

        historical_tv_start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.historical_security_img_back:

                break;

            case R.id.historical_img_start:
                historicalCustomWindow.showMenu();
                break;

            case R.id.historical_tv_start:
                historicalCustomWindow.showMenu();
                break;

        }
    }

    /**
     * 地图初始化
     */
    private void mapInitialization() {
        historical_UiSettings = historical_aMap.getUiSettings();
        historical_UiSettings.setZoomControlsEnabled(false);
        historical_UiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        if (getLocationPre() && getReadExternalMemory()){
            Log.e("TAT","今日");
            initMapStyleFile(this,"style.data");
        }

        if (historical_aMap != null) {
            MyLocationStyle locationStyle = new MyLocationStyle();
            locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.vehicle));
            historical_aMap.setMyLocationStyle(locationStyle);
            historical_aMap.getUiSettings().setMyLocationButtonEnabled(true);
            historical_aMap.setMyLocationEnabled(true);

            List<Car> cars = new ArrayList<>();
            for (int i = 0; i <= 10; i++) {
                LatLng latLng = new LatLng(m + i, b + i);
                Car car = new Car(latLng, "车牌号码" + i, "车辆信息" + i);
                Log.e("TAG", latLng.latitude + "  " + latLng.longitude);
                cars.add(car);
            }
            load(cars);
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
            Marker e = historical_aMap.addMarker(markerOption);
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
    public void initMapStyleFile(Context context, String fileName){
        File file = new File("/sdcard/"+fileName);
        if(file.exists()){
            historical_aMap.setCustomMapStylePath("/sdcard/style.data");
            historical_aMap.setMapCustomEnable(true);
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
            historical_aMap.setCustomMapStylePath("/sdcard/style.data");
            historical_aMap.setMapCustomEnable(true);
        } catch (IOException e) {
            Log.e("TAT",e.getMessage()+"ljj");
        }
    }

    @Override
    public void onGrantPermission() {
        super.onGrantPermission();
        initMapStyleFile(HistoricalTrackActivity.this, "style.data");
    }


    /**
     * 添加轨迹线
     */
    private void addPolylineInPlayGround() {
        List<LatLng> list = readLatLngs();
        List<Integer> colorList = new ArrayList<Integer>();
        List<BitmapDescriptor> bitmapDescriptors = new ArrayList<BitmapDescriptor>();

        int[] colors = new int[]{Color.argb(255, 0, 255, 0),Color.argb(255, 255, 255, 0),Color.argb(255, 255, 0, 0)};

        //用一个数组来存放纹理
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        textureList.add(BitmapDescriptorFactory.fromResource(R.drawable.vehicle));

        List<Integer> texIndexList = new ArrayList<Integer>();
        texIndexList.add(0);//对应上面的第0个纹理
        texIndexList.add(1);
        texIndexList.add(2);

        Random random = new Random();
        for (int i = 0; i < list.size(); i++) {
            colorList.add(colors[random.nextInt(3)]);
            bitmapDescriptors.add(textureList.get(0));

        }

        mPolyline = historical_aMap.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.vehicle))
                .addAll(list)
                .useGradient(true)
                .width(18));

        LatLngBounds bounds = new LatLngBounds(list.get(0), list.get(list.size() - 2));
        historical_aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    /**
     * 读取坐标点
     * @return
     */
    private List<LatLng> readLatLngs() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < coords.length; i += 2) {
            points.add(new LatLng(coords[i+1], coords[i]));
        }
        return points;
    }

    /**
     * 坐标点数组数据
     */
    private double[] coords = { 116.3499049793749, 39.97617053371078,
            116.34978804908442, 39.97619854213431, 116.349674596623,
            39.97623045687959, 116.34955525200917, 39.97626931100656,
            116.34943728748914, 39.976285626595036, 116.34930864705592,
            39.97628129172198, 116.34918981582413, 39.976260803938594,
            116.34906721558868, 39.97623535890678, 116.34895185151584,
            39.976214717128855, 116.34886935936889, 39.976280148755315,
            116.34873954611332, 39.97628182112874, 116.34860763527448,
            39.97626038855863, 116.3484658907622, 39.976306080391836,
            116.34834585430347, 39.976358252119745, 116.34831166130878,
            39.97645709321835, 116.34827643560175, 39.97655231226543,
            116.34824186261169, 39.976658372925556, 116.34825080406188,
            39.9767570732376, 116.34825631960626, 39.976869087779995,
            116.34822111635201, 39.97698451764595, 116.34822901510276,
            39.977079745909876, 116.34822234337618, 39.97718701787645,
            116.34821627457707, 39.97730766147824, 116.34820593515043,
            39.977417746816776, 116.34821013897107, 39.97753930933358,
            116.34821304891533, 39.977652209132174, 116.34820923399242,
            39.977764016531076, 116.3482045955917, 39.97786190186833,
            116.34822159449203, 39.977958856930286, 116.3482256370537,
            39.97807288885813, 116.3482098441266, 39.978170063673524,
            116.34819564465377, 39.978266951404066, 116.34820541974412,
            39.978380693859116, 116.34819672351216, 39.97848741209275,
            116.34816588867105, 39.978593409607825, 116.34818489339459,
            39.97870216883567, 116.34818473446943, 39.978797222300166,
            116.34817728972234, 39.978893492422685, 116.34816491505472,
            39.978997133775266, 116.34815408537773, 39.97911413849568,
            116.34812908154862, 39.97920553614499, 116.34809495907906,
            39.979308267469264, 116.34805113358091, 39.97939658036473,
            116.3480310509613, 39.979491697188685, 116.3480082124968,
            39.979588529006875, 116.34799530586834, 39.979685789111635,
            116.34798818413954, 39.979801430587926, 116.3479996420353,
            39.97990758587515, 116.34798697544538, 39.980000796262615,
            116.3479912988137, 39.980116318796085, 116.34799204219203,
            39.98021407403913, 116.34798535084123, 39.980325006125696,
            116.34797702460183, 39.98042511477518, 116.34796288754136,
            39.98054129336908, 116.34797509821901, 39.980656820423505,
            116.34793922017285, 39.98074576792626, 116.34792586413015,
            39.98085620772756, 116.3478962642899, 39.98098214824056,
            116.34782449883967, 39.98108306010269, 116.34774758827285,
            39.98115277119176, 116.34761476652932, 39.98115430642997,
            116.34749135408349, 39.98114590845294, 116.34734772765582,
            39.98114337322547, 116.34722082902628, 39.98115066909245,
            116.34708205250223, 39.98114532232906, 116.346963237696,
            39.98112245161927, 116.34681500222743, 39.981136637759604,
            116.34669622104072, 39.981146248090866, 116.34658043260109,
            39.98112495260716, 116.34643721418927, 39.9811107163792,
            116.34631638374302, 39.981085081075676, 116.34614782996252,
            39.98108046779486, 116.3460256053666, 39.981049089345206,
            116.34588814050122, 39.98104839362087, 116.34575119741586,
            39.9810544889668, 116.34562885420186, 39.981040940565734,
            116.34549232235582, 39.98105271658809, 116.34537348820508,
            39.981052294975264, 116.3453513775533, 39.980956549928244
    };
}
