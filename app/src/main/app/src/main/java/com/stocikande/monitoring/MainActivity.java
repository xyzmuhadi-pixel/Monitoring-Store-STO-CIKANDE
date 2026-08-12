package com.stocikande.monitoring;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    LinearLayout layout;
    SharedPreferences pref;

    EditText tanggal, psmTarget, psmActual, pwpTarget, pwpActual;
    EditText gratisTarget, gratisActual, suegTarget, suegActual;
    EditText telur, newMember, stdMember, catatan;
    Spinner shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("monitoring", MODE_PRIVATE);

        ScrollView scroll = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 25, 30, 30);

        scroll.addView(layout);

        TextView title = new TextView(this);
        title.setText("MONITORING PENJUALAN\nSTO CIKANDE");
        title.setTextSize(24);
        title.setTextColor(Color.WHITE);
        title.setGravity(17);
        title.setPadding(10, 25, 10, 25);
        title.setBackgroundColor(Color.rgb(220, 0, 40));
        layout.addView(title);

        tanggal = field("Tanggal");
        tanggal.setText(new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault()).format(new Date()));

        layout.addView(tanggal);

        TextView shiftLabel = label("Shift");
        layout.addView(shiftLabel);

        shift = new Spinner(this);
        String[] shiftItems = {"Pilih Shift", "Pagi", "Siang", "Malam"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                shiftItems
        );
        shift.setAdapter(adapter);
        layout.addView(shift);

        section("PSM");
        psmTarget = field("PSM Target");
        psmActual = field("PSM Actual");

        section("PWP");
        pwpTarget = field("PWP Target");
        pwpActual = field("PWP Actual");

        section("SERBA GRATIS");
        gratisTarget = field("Serba Gratis Target");
        gratisActual = field("Serba Gratis Actual");

        section("SUEEGERRR");
        suegTarget = field("Sueegeerrr Target");
        suegActual = field("Sueegeerrr Actual");

        section("DATA PENJUALAN");
        telur = field("Telur");
        newMember = field("New Member");
        stdMember = field("STD Member");

        section("CATATAN");
        catatan = field("Catatan");
        catatan.setMinLines(4);
        catatan.setGravity(48);

        Button simpan = new Button(this);
        simpan.setText("SIMPAN DATA");
        simpan.setTextSize(16);
        simpan.setTextColor(Color.WHITE);
        simpan.setBackgroundColor(Color.rgb(0, 150, 70));
        layout.addView(simpan);

        Button riwayat = new Button(this);
        riwayat.setText("LIHAT RIWAYAT");
        riwayat.setTextSize(16);
        layout.addView(riwayat);

        simpan.setOnClickListener(v -> simpanData());

        riwayat.setOnClickListener(v -> tampilkanRiwayat());

        setContentView(scroll);
    }

    EditText field(String hint) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setTextSize(16);
        e.setSingleLine(false);
        e.setPadding(20, 15, 20, 15);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        params.setMargins(0, 5, 0, 5);
        e.setLayoutParams(params);

        layout.addView(e);
        return e;
    }

    TextView label(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(16);
        t.setPadding(5, 15, 5, 5);
        return t;
    }

    void section(String text) {
        TextView s = new TextView(this);
        s.setText(text);
        s.setTextSize(19);
        s.setTextColor(Color.rgb(220, 0, 40));
        s.setPadding(5, 20, 5, 8);
        layout.addView(s);
    }

    void simpanData() {

        String data =
                "==============================\n" +
                "MONITORING STO CIKANDE\n" +
                "Tanggal : " + tanggal.getText() + "\n" +
                "Shift : " + shift.getSelectedItem() + "\n\n" +

                "PSM : " + psmTarget.getText() +
                " / " + psmActual.getText() + "\n" +

                "PWP : " + pwpTarget.getText() +
                " / " + pwpActual.getText() + "\n" +

                "Serba Gratis : " + gratisTarget.getText() +
                " / " + gratisActual.getText() + "\n" +

                "Sueegeerrr : " + suegTarget.getText() +
                " / " + suegActual.getText() + "\n\n" +

                "Telur : " + telur.getText() + "\n" +
                "New Member : " + newMember.getText() + "\n" +
                "STD Member : " + stdMember.getText() + "\n\n" +

                "Catatan : " + catatan.getText() + "\n" +
                "==============================\n";

        String lama = pref.getString("riwayat", "");

        pref.edit()
                .putString("riwayat", data + lama)
                .apply();

        Toast.makeText(
                this,
                "Data berhasil disimpan",
                Toast.LENGTH_LONG
        ).show();

        bersihkan();
    }

    void bersihkan() {
        psmTarget.setText("");
        psmActual.setText("");
        pwpTarget.setText("");
        pwpActual.setText("");
        gratisTarget.setText("");
        gratisActual.setText("");
        suegTarget.setText("");
        suegActual.setText("");
        telur.setText("");
        newMember.setText("");
        stdMember.setText("");
        catatan.setText("");
        shift.setSelection(0);
    }

    void tampilkanRiwayat() {

        String riwayat = pref.getString(
                "riwayat",
                "Belum ada data monitoring."
        );

        new android.app.AlertDialog.Builder(this)
                .setTitle("Riwayat Monitoring")
                .setMessage(riwayat)
                .setPositiveButton("Tutup", null)
                .show();
    }
          }
