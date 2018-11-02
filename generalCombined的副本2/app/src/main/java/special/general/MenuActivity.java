package special.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MenuActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        TextView Energyshow = (TextView) findViewById(R.id.showEnergy);
        TextView Scoreshow = (TextView) findViewById(R.id.showScore);
        final int energyS = LoginActivity.currentEnergy;
        String scoreS = LoginActivity.currentName;
        final String currentId = LoginActivity.currentID;
        final Users currentUser = LoginActivity.currentUser;
        final String Ss = String.valueOf(scoreS);
//        Energyshow.setText(Se);
        Scoreshow.setText(Ss);

       // Button enterEn = (Button) findViewById(R.id.enterBluetooth);
        Button enterEn2 = (Button) findViewById(R.id.enterBluetooth2);

        Button enterAcc = (Button) findViewById(R.id.enterAccountActivity);
        Button rkList = (Button) findViewById(R.id.rankingList);
        Button GoogleM = (Button) findViewById(R.id.ggmap);
        ImageButton enterGame = (ImageButton) findViewById(R.id.imageButton1);
    //
        /*
           enterEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(MenuActivity.this,GetEnergyActivity.class);
                //启动
                startActivity(intent);
            }
        });
        */
        enterEn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(MenuActivity.this,Get2Activity.class);
                //启动
                startActivity(intent);
            }
        });
        enterAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(MenuActivity.this,AccountActivity.class);
                //启动
                startActivity(intent);
            }
        });
        rkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(MenuActivity.this,RankingActivity.class);
                //启动
                startActivity(intent);
            }
        });

        GoogleM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        enterGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(energyS >= 20) {

                    Map userUpdate = new HashMap();//(currentId,currentUser.userName,currentUser.userPassword,currentUser.email,currentUser.userScore,currentUser.userEnergy-20);
                    userUpdate.put("email",currentUser.email);
                    userUpdate.put("userEnergy",currentUser.userEnergy - 20);
                    userUpdate.put("userId",currentUser.userId);
                    userUpdate.put("userName",currentUser.userName);
                    userUpdate.put("userPassword",currentUser.userPassword);
                    userUpdate.put("userScore",currentUser.userScore);
                    Map childUpdates =new HashMap();
                    childUpdates.put("/users/"+ currentId,userUpdate);
                    mDatabase.updateChildren(childUpdates);
                    // 给bnt1添加点击响应事件
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    //启动
                    startActivity(intent);
                }else {
                    Toast.makeText(MenuActivity.this, "You do not have enough energy value", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_signup, menu);
        return true;
    }
}

