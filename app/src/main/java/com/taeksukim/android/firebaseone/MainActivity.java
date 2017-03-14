package com.taeksukim.android.firebaseone;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSign;

    List<Bbs> datas = new ArrayList<>();
    ListAdapter adapter;

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText) findViewById(R.id.editContent);
        btnSign = (Button) findViewById(R.id.btnSign);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                // 1. bbs 레퍼런스(테이블)에 키를 생성.
                String key = bbsRef.push().getKey();

                // 2. 입력될 레코드(키,값,세트)를 생성
                Map<String, String> postValues = new HashMap<>();
                postValues.put("title" , title);
                postValues.put("content" , content);

                // 3. 데이터 데이터를 데이터베이스에 입력
                DatabaseReference keyRef = bbsRef.child(key);
                keyRef.setValue(postValues);


//
//                Map<String,Objects> keyMap = new HashMap<>();
//                keyMap.put(key, postValues);
//                bbsRef.updateChildren(keyMap);


            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ListAdapter(datas, this);
        listView.setAdapter(adapter);

        bbsRef.addValueEventListener(postListener);
    }

    ValueEventListener postListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //Post post = dataSnapshot.getValue(Post.class);
            // ...
            Log.w("mainActivity", "data count = " +dataSnapshot.getChildrenCount());

            datas.clear();

            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                String key = snapshot.getKey();
                Bbs bbs = snapshot.getValue(Bbs.class);
                Bbs.key = key;

                datas.add(bbs);

            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("mainActivity", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
}
