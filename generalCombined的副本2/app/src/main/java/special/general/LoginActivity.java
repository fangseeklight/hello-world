package special.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    static int iCount = 0;

    EditText loginUN;
    EditText loginPW;

    DatabaseReference databaseUsers;
    DatabaseReference databaseUserName;
    DatabaseReference databaseUserPassword;
    DatabaseReference databaseUserScore;
    DatabaseReference databaseUserEnergy;

    static int currentEnergy = 0;
    static int hisScore = 0;
    static String currentName;
    static String currentID;
    public static Users currentUser;
    static String currentBluetoothAddress = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUserName = FirebaseDatabase.getInstance().getReference().child("userName");
        databaseUserPassword =  FirebaseDatabase.getInstance().getReference().child("userPassword");
        databaseUserScore =  FirebaseDatabase.getInstance().getReference().child("userScore");
        databaseUserEnergy =  FirebaseDatabase.getInstance().getReference().child("userEnergy");


        //view层的控件和业务层的控件，靠id关联和映射  给btn1赋值，即设置布局文件中的Button按钮id进行关联
        Button btn1=(Button)findViewById(R.id.signup1);
        Button btn3=(Button)findViewById(R.id.login1);
        loginUN = (EditText) findViewById(R.id.loginUsername);
        loginPW = (EditText) findViewById(R.id.loginPassword);

        //给btn1绑定监听事件
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 给bnt1添加点击响应事件
                Intent intent =new Intent(LoginActivity.this,SignupActivity.class);
                //启动
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkLogIn();

            }
        });

    }
    private void checkLogIn(){
        final String userName = loginUN.getText().toString().trim();
        final String passWord = loginPW.getText().toString().trim();
        if (userName.isEmpty()){
            Toast.makeText(LoginActivity.this,"Please Input User Name", Toast.LENGTH_SHORT).show();
        }else if (passWord.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please Input Password", Toast.LENGTH_SHORT).show();
        }else{
/*            databaseUsers.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  //  for(DataSnapshot currentSnapshot : dataSnapshot.getValue(Users.class) ) {
                        Users currentUser = dataSnapshot.getValue(Users.class);
                        String currentUN = currentUser.userName;
                        String currentPW = currentUser.userPassword;
                        if (currentUN.equals(userName)) {
                            if (currentPW.equals(passWord)) {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                currentEnergy = currentUser.userEnergy;
                                hisScore = currentUser.userScore;

                            }
                        }
                 //  }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
*/
            //addListenerForSingleValueEvent()
            databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                //databaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                        Users user = userSnapshot.getValue(Users.class);

                        if (userName.equals(user.userName)){
                            i++;
                            if(passWord.equals(user.userPassword)){
                                Toast.makeText(LoginActivity.this,"Successfully Login",Toast.LENGTH_SHORT).show();
                                currentEnergy = user.userEnergy;
                                hisScore = user.userScore;
                                currentName = user.userName;
                                currentID = user.userId;
                                currentUser = user;

                                // 给bnt1添加点击响应事件
                                Intent intent =new Intent(LoginActivity.this,MenuActivity.class);
                                //启动
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this,"User Name or Password is Wrong!",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    if(i == 0){
                        Toast.makeText(LoginActivity.this,"You have not signed up!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_signup, menu);
        return true;
    }
}

