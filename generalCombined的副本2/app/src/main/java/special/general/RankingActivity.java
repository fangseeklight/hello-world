package special.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    DatabaseReference databaseUsers;
    DatabaseReference databaseUserName;
    DatabaseReference databaseUserScore;
    //    private List<String> list;
    static int i = 0;
    static int[] listOrder = new int[200];
    static String[] listName = new String[200];
    static int[] listScore = new int[200];
    private SimpleAdapter rklist;
    private ListView RankingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUserName = FirebaseDatabase.getInstance().getReference().child("userName");
        databaseUserScore = FirebaseDatabase.getInstance().getReference().child("userScore");

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();


        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int o = 0;
                String[] name = new String[200];
                int[] score = new int[200];
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Users user = userSnapshot.getValue(Users.class);

                    name[o] = user.userName;
                    score[o] = user.userScore;
                    o++;

                }
                i = o;
                for (int j = 0; j < o; j++) {
                    for (int k = 0; k < (o - j); k++) {
                        if (score[k] < score[k + 1]) {
                            int a = score[k];
                            score[k] = score[k + 1];
                            score[k + 1] = a;
                            String b = name[k];
                            name[k] = name[k + 1];
                            name[k + 1] = b;
                        }
                    }
                }
                for (int p = 0; p < o; p++) {
                    listName[p] = name[p];
                    listScore[p] = score[p];
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for (int m = 0; m < i; m++) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("listName", "No. " + (m + 1) + " " + listName[m]);
            map.put("listScore", listScore[m]);
            listItem.add(map);
        }
        String[] from = {"listName","listScore"};
        int[] to = {R.id.listname,R.id.listscore};
        RankingList = (ListView) findViewById(R.id.RankingList);
        rklist = new SimpleAdapter(this, listItem, R.layout.simple_adapter_item,from,to);
        RankingList.setAdapter(rklist);
/*        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.item,
                new String[] {"listOrder","listName", "listScore"},
                new int[] {R.id.listOrder,R.id.ItemTitle,R.id.ItemText}
            );

        RankingList = (ListView) findViewById(R.id.rklist);
        RankingList = new ListView(this);

        SimpleAdapter adapter = new SimpleAdapter(this, getDate(), R.layout.activity_ranking, new String[]{"rankName", "rankScore"}, new int[]{R.id.rankName, R.id.rankScore});
        RankingList.setAdapter(adapter);

        setContentView(RankingList);

    }

    private ArrayList<Map<String, Object>> getDate() {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
       // map.put("title", "G1");
       // map.put("info", "google1");
        for (int m = 0; m < i; m++) {

            map.put("NO.", listName[m]);
            map.put("Score: ",listScore[m]);
            list.add(map);
        }

        return list;
    }

*/
    }
}
