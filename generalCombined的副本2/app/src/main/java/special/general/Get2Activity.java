package special.general;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Get2Activity extends AppCompatActivity {

    int scanState = 1;
    int scanTimes = 0;

    private ArrayList<BluetoothDevice> mLeDevices;
    private ArrayList<Integer> mRSSIs;//新加
    private ArrayList<byte[]> mRecords;//新加
    private LayoutInflater mInflator;

    private TextView textView11;
    private ImageButton check2;
    private String bluetoothAddress1 = "CC:E7:43:A5:F9:C6";
    private String bluetoothAddress2 = "E8:C0:7C:86:5C:DA";
//    private String bluetoothAddress3 = "04:1B:6D:84:D0:51"; //LG test
    private String bluetoothAddress3 = "90:97:F3:F3:E7:09";
    private String bluetoothAddress4 = "A4:07:B6:3D:73:81";


    private List<BluetoothDevice> mBlueList2 = new ArrayList<>();
    final String currentId = LoginActivity.currentID;
    final Users currentUser = LoginActivity.currentUser;

    BluetoothAdapter mBluetoothAdapter;
    // Device scan callback.
    BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addDevice(device);
                    if(mBlueList2.size()!=0){
                        System.out.println("=======================in for loop once=========================scanState:" + scanState);
                        scanTimes++;
                        if(scanTimes>300){
                            if(scanState == 1){
                                Toast.makeText(Get2Activity.this, "Not in the scanning area", Toast.LENGTH_SHORT).show();
                                textView11.setText("Not in the scanning area");
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                scanState = 0;
                            }
                        }
                        //Toast.makeText(Get2Activity.this, "in for loop once", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < mBlueList2.size(); i++){
                            if (scanState == 0){
                                break;
                            }
                            if ((mBlueList2.get(i).getAddress().equals(bluetoothAddress1)) && (!mBlueList2.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))) {
                                LoginActivity.currentBluetoothAddress = mBlueList2.get(i).getAddress();
                                Toast.makeText(Get2Activity.this, "Successfully Checked", Toast.LENGTH_SHORT).show();
                                pushToDatabase();
                                i = mBlueList2.size();
                                break;
                                //   return;
                            } else if ((mBlueList2.get(i).getAddress().equals(bluetoothAddress2)) && (!mBlueList2.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))) {
                                LoginActivity.currentBluetoothAddress = mBlueList2.get(i).getAddress();
                                Toast.makeText(Get2Activity.this, "Successfully Checked2", Toast.LENGTH_SHORT).show();
                                pushToDatabase();
                                i = mBlueList2.size();
                                break;
                                //   return;
                            } else if ((mBlueList2.get(i).getAddress().equals(bluetoothAddress3)) && (!mBlueList2.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))) {
                                LoginActivity.currentBluetoothAddress = mBlueList2.get(i).getAddress();
                                Toast.makeText(Get2Activity.this, "Successfully Checked3", Toast.LENGTH_SHORT).show();
                                pushToDatabase();
                                i = mBlueList2.size();
                                break;
                                //   return;
                            } else if ((mBlueList2.get(i).getAddress().equals(bluetoothAddress4)) && (!mBlueList2.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress))) {
                                LoginActivity.currentBluetoothAddress = mBlueList2.get(i).getAddress();
                                Toast.makeText(Get2Activity.this, "Successfully Checked4", Toast.LENGTH_SHORT).show();
                                pushToDatabase();
                                i = mBlueList2.size();
                                break;
                                //   return;
                            } else if (mBlueList2.get(i).getAddress().equals(LoginActivity.currentBluetoothAddress)) {
                                Toast.makeText(Get2Activity.this, "Try a new area", Toast.LENGTH_SHORT).show();
                                textView11.setText("Please go to another place!");
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                scanState = 0;
                                //     return;
                            } else {
                                //Toast.makeText(Get2Activity.this, "in che", Toast.LENGTH_SHORT).show();
                                System.out.print("-1-");
                            }
                        }
                    }else {
                        System.out.println("size = 0");
                        Toast.makeText(Get2Activity.this, "No bluetooth signal", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    };

//    private ProgressDialog progressDialog2 = new ProgressDialog(this);

    DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get2);

        mDatabase = FirebaseDatabase.getInstance().getReference();

//        lisetView = (ListView) findViewById(R.id.list_view);
        textView11 = (TextView) findViewById(R.id.textView11);
        check2 = (ImageButton)findViewById(R.id.CheckAddEnergy2);
        textView11.setText("Get started now !");

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        isGpsEnable(this);
        check2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                textView11.setText("Start checking... Please wait");
                init2();
                mBlueList2.clear();
                scanState = 1;
                scanTimes = 0;
                /*
                if (progressDialog2 == null) {
                }
                progressDialog2.setMessage("in searching please wait!");
                progressDialog2.show();
                textView11.setText("Start checking");
                init2();
                progressDialog2.dismiss();
                */
            }
        });
    }


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


    public void addDevice(BluetoothDevice device) {
        if(!mBlueList2.contains(device)) {
            mBlueList2.add(device);
        }
    }

    public void pushToDatabase(){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<successful once>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        scanState = 0;
        textView11.setText("Successfully Checked");
    }



    public void init2(){

        System.out.println("----------get in inti----------");

        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }


}
