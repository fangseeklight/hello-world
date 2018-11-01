package special.general;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//-

public class GetEnergyActivity extends AppCompatActivity {

    private final static int SEARCH_CODE = 0x123;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final String TAG = "GetEnergyActivity";

    private List<BluetoothDevice> mBlueList = new ArrayList<>();
//    private ListView lisetView;
    private TextView textView1;
    private ImageButton check1;
    private String bluetoothAddress1 = "CC:E7:43:A5:F9:C6";
    private String bluetoothAddress2 = "E8:C0:7C:86:5C:DA";
    private String bluetoothAddress3 = "04:1b:6d:84:d0:51";
    private String bluetoothAddress4 = "A4:07:B6:3D:73:81";


//    final int energyS = LoginActivity.currentEnergy;
//    String scoreS = LoginActivity.currentName;
    final String currentId = LoginActivity.currentID;
    final Users currentUser = LoginActivity.currentUser;
//    final String Ss = String.valueOf(scoreS);

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_energy);

        mDatabase = FirebaseDatabase.getInstance().getReference();

//        lisetView = (ListView) findViewById(R.id.list_view);
        textView1 = (TextView) findViewById(R.id.textView1);
        check1 = (ImageButton)findViewById(R.id.CheckAddEnergy);
        textView1.setText("hi, there!");
        Log.e(TAG, "onCreate: GPS working?：" + isGpsEnable(this));
        check1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                init();
                textView1.setText("Start checking");
            }
        });
    }
    //gps是否可用(有些设备可能需要定位)
    public static final boolean isGpsEnable(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 判断蓝牙是否开启
     */
    private void init() {
        // 判断手机是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Your Device Does Not Support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }
        // 判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,SEARCH_CODE);
        } else {
            // 不做提示，强行打开
            mBluetoothAdapter.enable();
        }
        startDiscovery();
        Log.e(TAG, "startDiscovery: turn on bluetooth");
    }

    /**
     * 注册异步搜索蓝牙设备的广播
     */
    private void startDiscovery() {
        // 找到设备的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 注册广播
        registerReceiver(receiver, filter);
        // 搜索完成的广播
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播
        registerReceiver(receiver, filter1);
        Log.e(TAG, "startDiscovery: broadcast registered");
        startScanBluth();
    }

    /**
     * 广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("------------------onReceive once-------------------");
            Toast.makeText(GetEnergyActivity.this, "onReceive once", Toast.LENGTH_SHORT).show();
            // 收到的广播类型
            String action = intent.getAction();
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 没否配对
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Toast.makeText(GetEnergyActivity.this, "wrong stage3", Toast.LENGTH_SHORT).show();
                    if (!mBlueList.contains(device)) {
                        mBlueList.add(device);
                    }
                    textView1.setText("nearby equipments：" + mBlueList.size() + "\u3000\u3000the address of this equipment：" + getBluetoothAddress());
/*
                    MyAdapter adapter = new MyAdapter(MainActivity.this, mBlueList);
                    lisetView.setAdapter(adapter);

                    Log.e(TAG, "onReceive: " + mBlueList.size());
                    Log.e(TAG, "onReceive: " + (device.getName() + ":" + device.getAddress() + " ：" + "m" + "\n"));
*/
                }else {
                    Toast.makeText(GetEnergyActivity.this, "wrong stage2", Toast.LENGTH_SHORT).show();
                    textView1.setText("wrong stage 2");
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 关闭进度条
                progressDialog.dismiss();
                Log.e(TAG, "onReceive: searching complete");
                for(int i = 0; i < mBlueList.size(); i++){
                    if((mBlueList.get(i).getAddress() .equals( bluetoothAddress1))&&(!mBlueList.get(i).getAddress().equals( LoginActivity.currentBluetoothAddress))){
                        LoginActivity.currentBluetoothAddress = mBlueList.get(i).getAddress();
                        pushToDatabase();
                        Toast.makeText(GetEnergyActivity.this, "Check successful", Toast.LENGTH_SHORT).show();
                    }else if((mBlueList.get(i).getAddress() .equals( bluetoothAddress2))&&(mBlueList.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))){
                        LoginActivity.currentBluetoothAddress = mBlueList.get(i).getAddress();
                        pushToDatabase();
                        Toast.makeText(GetEnergyActivity.this, "Check successful", Toast.LENGTH_SHORT).show();
                    }else if((mBlueList.get(i).getAddress() .equals( bluetoothAddress3))&&(mBlueList.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))){
                        LoginActivity.currentBluetoothAddress = mBlueList.get(i).getAddress();
                        pushToDatabase();
                        Toast.makeText(GetEnergyActivity.this, "Check successful", Toast.LENGTH_SHORT).show();
                    }else if((mBlueList.get(i).getAddress() .equals( bluetoothAddress4))&&(mBlueList.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))){
                        LoginActivity.currentBluetoothAddress = mBlueList.get(i).getAddress();
                        pushToDatabase();
                        Toast.makeText(GetEnergyActivity.this, "Check successful", Toast.LENGTH_SHORT).show();
                    }else if(mBlueList.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress)){
                        Toast.makeText(GetEnergyActivity.this, "Your need go to a new area", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(GetEnergyActivity.this, "Your are not inside a bluetooth range", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(GetEnergyActivity.this, "wrong stage1", Toast.LENGTH_SHORT).show();
                textView1.setText("wrong stage 1");
            }
        }
    };
    public void pushToDatabase(){
        Map userUpdate = new HashMap();//(currentId,currentUser.userName,currentUser.userPassword,currentUser.email,currentUser.userScore,currentUser.userEnergy-20);
        userUpdate.put("email",currentUser.email);
        userUpdate.put("userEnergy",currentUser.userEnergy+40);
        userUpdate.put("userId",currentUser.userId);
        userUpdate.put("userName",currentUser.userName);
        userUpdate.put("userPassword",currentUser.userPassword);
        userUpdate.put("userScore",currentUser.userScore);
        Map childUpdates =new HashMap();
        childUpdates.put("/users/"+ currentId,userUpdate);
        mDatabase.updateChildren(childUpdates);
    }

    private ProgressDialog progressDialog;

    /**
     * 搜索蓝牙的方法
     */
    private void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("in searching please wait!");
        progressDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            //取消注册,防止内存泄露（onDestroy被回调代不代表Activity被回收？：具体回收看系统，由GC回收，同时广播会注册到系统
            //管理的ams中，即使activity被回收，reciver也不会被回收，所以一定要取消注册），
            unregisterReceiver(receiver);
        }else {
            Toast.makeText(GetEnergyActivity.this, "nothing found", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 获取本机蓝牙地址
     */
    private String getBluetoothAddress() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object address = method.invoke(bluetoothManagerService);
            if (address != null && address instanceof String) {
                return (String) address;
            } else {
                return null;
            }
            //抛一个总异常省的一堆代码...
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPause() {
        mBlueList.clear();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SEARCH_CODE){
            startDiscovery();
        }
        Log.e(TAG, "onActivityResult: "+requestCode );
        Log.e(TAG, "onActivityResult: "+resultCode );
        Log.e(TAG, "onActivityResult: "+requestCode );
    }

}
