package com.profillah.crud;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class edit_mahasiswa extends AppCompatActivity {

    com.rengwuxian.materialedittext.MaterialEditText ETNim,ETName, ETAddress,ETHobby;
    String nim,name,address,hobby;
    Button BTNUpdate;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Intent, get data from main
        nim     = getIntent().getStringExtra("nim");
        //Toast.makeText(edit_mahasiswa.this,nim,Toast.LENGTH_LONG).show();


        ETNim       = findViewById(R.id.ETNim);
        ETName      = findViewById(R.id.ETName);
        ETAddress   = findViewById(R.id.ETAddress);
        ETHobby     = findViewById(R.id.ETHobby);
        BTNUpdate   = findViewById(R.id.BTNUpdate);

        progressDialog = new ProgressDialog(this);

        BTNUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_delete){
            new AlertDialog.Builder(edit_mahasiswa.this)
                    .setMessage("Do you want to delete this NIM: "+nim+" ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.setMessage("Deleting...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            AndroidNetworking.post("http://sentimenku.com/mobile/api/deleteMahasiswa.php")
                                    .addBodyParameter("nim",""+nim)
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            progressDialog.dismiss();
                                            try {
                                                Boolean status = response.getBoolean("status");
                                                Log.d("status",""+status);
                                                String result = response.getString("result");
                                                if(status){
                                                    new AlertDialog.Builder(edit_mahasiswa.this)
                                                            .setMessage("data deleted successfully !")
                                                            .setCancelable(false)
                                                            .setPositiveButton("back", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent i = new Intent(edit_mahasiswa.this, MainActivity.class);
                                                                    startActivity(i);
                                                                    edit_mahasiswa.this.finish();
                                                                }
                                                            })
                                                            .show();

                                                }else{
                                                    Toast.makeText(edit_mahasiswa.this, ""+result, Toast.LENGTH_SHORT).show();
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            anError.printStackTrace();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();


        }
        return super.onOptionsItemSelected(item);
    }




}

