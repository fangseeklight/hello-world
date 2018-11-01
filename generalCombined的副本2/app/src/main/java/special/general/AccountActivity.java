package special.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    static String NameAccount ;
    static String ScoreAccount ;
    static String EnergyAccount ;

    DatabaseReference databaseUsers;
    DatabaseReference databaseUserName;
    DatabaseReference databaseUserPassword;
    DatabaseReference databaseUserScore;
    DatabaseReference databaseUserEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        NameAccount = LoginActivity.currentName;

        databaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
        databaseUserScore =  FirebaseDatabase.getInstance().getReference().child("userScore");
        databaseUserEnergy =  FirebaseDatabase.getInstance().getReference().child("userEnergy");

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            //databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Users user = userSnapshot.getValue(Users.class);

                    assert user != null;
                    if (LoginActivity.currentID.equals(user.userId)) {

                        ScoreAccount = String.valueOf(user.userScore);
                        EnergyAccount = String.valueOf(user.userEnergy);

                        TextView accountShowName = (TextView) findViewById(R.id.accountName);
                        TextView accountShowScore = (TextView) findViewById(R.id.accountText1);
                        TextView accountShowEnergy = (TextView) findViewById(R.id.accountText2);

                        accountShowName.setText(NameAccount);
                        accountShowScore.setText(ScoreAccount);
                        accountShowEnergy.setText(EnergyAccount);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}