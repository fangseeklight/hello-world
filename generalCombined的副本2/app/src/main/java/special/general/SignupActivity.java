package special.general;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText InputUN;
    EditText InputPW1;
    EditText InputPW2;
    EditText Email;

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        //view层的控件和业务层的控件，靠id关联和映射  给btn1赋值，即设置布局文件中的Button按钮id进行关联
        Button btn2;
        Button btnsign;
        btnsign = (Button) findViewById(R.id.join);
//        btn2 = (Button) findViewById(R.id.back1);

        InputUN = findViewById(R.id.inputUsername);
        InputPW1 = (EditText) findViewById(R.id.inputPassword1);
        InputPW2 = (EditText) findViewById(R.id.inputPassword2);
        Email = (EditText) findViewById(R.id.inputEmail);
/*
        //给btn2绑定监听事件
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 给bnt1添加点击响应事件
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                //启动
                startActivity(intent);
            }
        });
        */
        btnsign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checksign();

            }
        });
    }

    private void checksign(){
        String userName = InputUN.getText().toString().trim();
        String passWord1 = InputPW1.getText().toString().trim();
        String passWord2 = InputPW2.getText().toString().trim();
        String email = Email.getText().toString().trim();
        if (userName.isEmpty()){
            Toast.makeText(SignupActivity.this,"Please Input User Name", Toast.LENGTH_SHORT).show();
        }else if (passWord1.isEmpty()){
            Toast.makeText(SignupActivity.this,"Please Input User Password", Toast.LENGTH_SHORT).show();
        }else if (passWord2.isEmpty()){
            Toast.makeText(SignupActivity.this,"Please Input User Password", Toast.LENGTH_SHORT).show();
        }else if(passWord1.equals(passWord2)){
            signinSuccess();
        }else {
            Toast.makeText(SignupActivity.this,"Please Input Same Password", Toast.LENGTH_SHORT).show();
        }
    }
    private void signinSuccess(){
        String id = databaseUsers.push().getKey();
        String userName = InputUN.getText().toString().trim();
        String passWord1 = InputPW1.getText().toString().trim();
        String email = Email.getText().toString().trim();
        int score = 0;
        int energy = 100;

        Users user = new Users(id, userName, passWord1 , email, score, energy);

        databaseUsers.child(id).setValue(user);
        Toast.makeText(this,"Successfully Registered", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}


