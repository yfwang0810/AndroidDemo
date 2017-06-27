package yfwang.androiddemo.activity.MapDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.map.AddressListAdapter;
import yfwang.androiddemo.bean.NearAddressBean;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/22 18:39
 */
public class MapActivity extends Activity implements View.OnClickListener, PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, AddressListAdapter.OnItemClickListener {


    private MapView mMapView;
    private LinearLayout mLocationList;
    private ImageView mLocation;
    private RecyclerView mRecyclerView;
    private AMap mAMap;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private int currentPage = 0;// 当前页面，从0开始计数
    private List<PoiItem> poiItems;// poi数据
    private PoiResult poiResult; // poi返回的结果
    private LatLng latLng;
    private LatLng target;
    private AddressListAdapter addressListAdapter;
    private List<NearAddressBean> mList;

    private int PERMISSIONREQUESTCODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化定位
        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        mLocationList = (LinearLayout) findViewById(R.id.ll_locationlist);
        mLocation = (ImageView) findViewById(R.id.iv_location);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);


        mLocation.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        if (addressListAdapter == null) {
            addressListAdapter = new AddressListAdapter(MapActivity.this);
        }
        UiSettings uiSettings = mMapView.getMap().getUiSettings();
        uiSettings.setLogoBottomMargin(-50);//隐藏logo
        uiSettings.setScaleControlsEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        addressListAdapter.setOnItemclickListener(this);

        initLocation();
        startLocation();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            mAMap.setOnCameraChangeListener(this);
            addMarkerInScreenCenter();
        }
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 30));
    }


    Marker screenMarker = null;

    /**
     * 在屏幕中心添加一个Marker
     */
    private void addMarkerInScreenCenter() {
        screenMarker = mAMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_site_2)));
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);

    }

    /**
     * 开始进行poi搜索
     *
     * @param target
     * @param addressName
     */
    protected void doSearchQuery(LatLng target, String addressName) {
        query = new PoiSearch.Query(addressName, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (latLng != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            LatLonPoint latLonPoint = new LatLonPoint(target.latitude, target.longitude);
            poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 1000, true));//
//             设置搜索区域为以lp点为圆心，其周围1000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    /**
     * 开始定位
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                if (location.getErrorCode() == 0) {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    init();
                } else {
                    Toast.makeText(MapActivity.this,"定位失败，loc is null",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MapActivity.this,"定位失败，loc is null",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_location) {
            startLocation();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        mList = new ArrayList<>();
                        for (int i = 0; i < poiItems.size(); i++) {
                            NearAddressBean nearAddressBean = new NearAddressBean();
                            if (i == 0) {
                                nearAddressBean.setSelect(true);
                            } else {
                                nearAddressBean.setSelect(false);
                            }
                            nearAddressBean.setName(poiItems.get(i).getTitle());
                            nearAddressBean.setAddress(poiItems.get(i).getSnippet());

                            mList.add(nearAddressBean);
                        }

                        addressListAdapter.setData(mList);
                        mRecyclerView.setAdapter(addressListAdapter);

                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        Toast.makeText(this, R.string.no_result,Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, R.string.no_result,Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,rcode,Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                   + cities.get(i).getAdCode() + "\n";
        }
        Toast.makeText(this,infomation,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        //这个target 就是地图移动过后中心点的经纬度
        target = cameraPosition.target;
        Log.i("yfwang", target.latitude + "");
        //这个方法是逆地理编码解析出详细位置
        Geo(target);
    }

    private void Geo(LatLng target) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);//和上面一样
// 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(target.latitude, target.longitude), 1000, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        //如果逆地理编码成功，就获取到中心点的详细位置，并且在TextView中进行显示，就如同一开始的那张图片所示。
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                doSearchQuery(target, addressName);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void itemClick(NearAddressBean nearAddressBean, int position) {
        if (mList != null && mList.size() != 0) {
            for (int i = 0; i < mList.size(); i++) {
                if (i == position) {
                    mList.get(i).setSelect(true);
                } else {
                    mList.get(i).setSelect(false);
                }
            }
            addressListAdapter.notifyDataSetChanged();
        }

        //Item 点击回调
        Toast.makeText(MapActivity.this,nearAddressBean.getName() + nearAddressBean.getAddress(),Toast.LENGTH_SHORT).show();
    }



}
