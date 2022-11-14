package com.assign7.a3922.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText studentID, studentName;
    Button load,find,insert,update,delete;
    myDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentID = findViewById(R.id.studentID);
        studentName = findViewById(R.id.studentName);
        insert = (Button)findViewById(R.id.AddBtn);
        load = (Button)findViewById(R.id.loadDataBtn);
        delete = (Button)findViewById(R.id.deleteBtn);
        update= (Button)findViewById(R.id.updateBtn);
        db=new myDBHelper(this);

        insert.setOnClickListener(v -> {
            String s_id= studentID.getText().toString();
            String s_name=studentName.getText().toString();

            Boolean checkInsertedData=db.insertData(s_id, s_name);
            if(checkInsertedData){
                Toast.makeText(MainActivity.this,"New Entry Inserted", Toast.LENGTH_SHORT).show();
                studentID.setText("");
                studentName.setText("");
            }
            else{
                Toast.makeText(MainActivity.this,"New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(v -> {
            String stu_id= studentID.getText().toString();
            String stu_name=studentName.getText().toString();

            Boolean checkUpdatedData=db.updateData(stu_id, stu_name);
            if(checkUpdatedData){
                Toast.makeText(MainActivity.this,"Student Record Updated", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Student Record Not updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(v -> {
            String stu_id= studentID.getText().toString();

            Boolean checkDataDeleted=db.deleteData(stu_id);
            if(checkDataDeleted){
                Toast.makeText(MainActivity.this,"Deleted Successfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Delete Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });

        load.setOnClickListener(v -> {
            Cursor res = db.getData();
            if(res.getCount()==0){
                Toast.makeText(MainActivity.this,"Empty Database :(", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder buffer=new StringBuilder();
            while(res.moveToNext()){
                buffer.append("Student Id: ").append(res.getString(0)).append("\n");
                buffer.append("Student Name: ").append(res.getString(1)).append("\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle(" ~~~~ Student Database ~~~~ ");
            builder.setMessage(buffer.toString());
            builder.show();
            db.close();
        });

    }

}
