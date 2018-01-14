package com.belajar.latihan5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.belajar.latihan5.helper.dbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private dbHelper helper;
    public EditText namaTxt;
    public ListView dataHasil;
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaTxt = (EditText) findViewById(R.id.namaTxt);
        dataHasil = (ListView) findViewById(R.id.listView1);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        helper = new dbHelper(this);
        ArrayList array_list = helper.getAllNama();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        dataHasil.setAdapter(adapter);

        dataHasil.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Anda Yakin Ingin Hapus Data ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String item = ((TextView)arg1).getText().toString();
                                helper.deleteNama(item);
                                reloadData();
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Hapus Data");
                d.show();

            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               helper.insertNama(namaTxt.getText().toString());
                reloadData();
               Toast.makeText(MainActivity.this,"Data Saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void reloadData(){
        ArrayList array_list2 = helper.getAllNama();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list2);
        dataHasil.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
