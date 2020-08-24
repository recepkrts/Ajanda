package com.example.ajanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    LinearLayout icerik;
    int secilenId;
    List<Liste> gelecekListemiz;
    Veritabani veritabani;
    ImageView ekle;
    String tarih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icerik = findViewById(R.id.icerik);
        ekle = findViewById(R.id.ekle);
        gelecekListemiz = new ArrayList<>();
        veritabani = new Veritabani(context);
        veritabani();
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                @SuppressLint("InflateParams") final View alertPenceresi = layoutInflater.inflate(R.layout.alertdialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Yeni Oluştur")
                        .setView(alertPenceresi)
                        .setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editTextAlert = alertPenceresi.findViewById(R.id.editTextAlert);
                                DatePicker datePicker = alertPenceresi.findViewById(R.id.dataPicker);
                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth();
                                int year = datePicker.getYear();
                                tarih = day+"/"+month+"/"+year;
                                veritabani.ekle(editTextAlert.getText().toString());
                                if (!veritabani.liste().isEmpty()){
                                    Toast.makeText(context,"Başarıyla Eklendi!",Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    Toast.makeText(context,"Hata!",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("İptal", null)
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        for (int i=0; i<gelecekListemiz.size(); i++){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.liste,null,false);
            icerik.addView(linearLayout);

            CardView cardViewListe = linearLayout.findViewById(R.id.cardViewListe);
            cardViewListe.setId(gelecekListemiz.get(i).getId());
            cardViewListe.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) context);
            cardViewListe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openContextMenu(v);
                }
            });

            final TextView textView = linearLayout.findViewById(R.id.textView);
            textView.setId(gelecekListemiz.get(i).getId());
            textView.setText(gelecekListemiz.get(i).getListeNotu());
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
        secilenId = v.getId();
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sil:
                if(veritabani.sil(secilenId) > 0){
                    Toast.makeText(context,"Silindi!",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void veritabani() {
        gelecekListemiz = veritabani.liste();
    }
}
