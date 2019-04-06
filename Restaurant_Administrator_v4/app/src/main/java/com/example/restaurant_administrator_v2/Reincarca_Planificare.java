package com.example.restaurant_administrator_v2;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Reincarca_Planificare extends AppCompatActivity {

    DatabaseHelper myDb;

    private Button reincarca_planificarea;
    private RelativeLayout planificare_restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reincarca__planificare);

        getSupportActionBar().hide();

        myDb = new DatabaseHelper(this);
        reincarca_planificarea = (Button) findViewById(R.id.button_reincarca_planificarea);
        planificare_restaurant = (RelativeLayout) findViewById(R.id.planificare_restaurant);

        ReincarcaPlanificarea();

    }

    public String ObtineNumarLinii(String s) {
        String[] split = s.split("_");
        return split[1];
    }

    public String ObtineNumarColoane(String s) {
        String[] split = s.split("_");
        return split[2];
    }

    public void ReincarcaPlanificarea() {
        reincarca_planificarea.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                        int latime_celula = (int) (50 * scale + 0.5f);

                        Cursor res = myDb.getAllData();

                        if (res.getCount() == 0) {
                            Toast.makeText(Reincarca_Planificare.this, "Nothing found.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String nume_imagine;

                        String s = "";
                        Cursor res1 = myDb.getAllData();
                        res1.moveToLast();
                        s = res1.getString(res1.getColumnIndex("CELULA_ID"));

                        int numar_linii = Integer.parseInt(ObtineNumarLinii(s));
                        int numar_coloane = Integer.parseInt(ObtineNumarColoane(s));
                        //Toast.makeText(Reincarca_Planificare.this,String.valueOf(numar_coloane),Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < numar_linii + 1; i++) {
                            for (int j = 0; j < numar_coloane + 1; j++) {

                                LinearLayout celula = new LinearLayout(Reincarca_Planificare.this);
                                celula.setOrientation(LinearLayout.VERTICAL);
                                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(latime_celula, latime_celula);
                                lp1.setMargins(j * latime_celula, i * latime_celula, 0, 0);
                                celula.setLayoutParams(lp1);
                                celula.setBackgroundColor(Color.parseColor("#13775000"));

                                res.moveToNext();
                                nume_imagine = res.getString(3);
                                //Toast.makeText(Reincarca_Planificare.this,nume_imagine,Toast.LENGTH_SHORT).show();

                                if (nume_imagine != null) {
                                    ImageView imageView = new ImageView(Reincarca_Planificare.this);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(latime_celula, latime_celula);
                                    imageView.setLayoutParams(layoutParams);

                                    switch (nume_imagine) {
                                        case "adaugare_masa":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_masa);
                                            imageView.setScaleX((float) 0.75);
                                            imageView.setScaleY((float) 0.75);
                                            break;
                                        case "adaugare_bar":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_bar);
                                            break;
                                        case "adaugare_perete_3":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_perete_3);
                                            break;
                                        case "adaugare_bucatarie":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_bucatarie);
                                            break;
                                        case "adaugare_perete":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_perete);
                                            imageView.setScaleY((float) 2);
                                            break;
                                        case "adaugare_usa":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_usa);
                                            imageView.setScaleX((float) 2);
                                            break;
                                        case "adaugare_toaleta":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_toaleta);
                                            break;
                                        case "adaugare_perete_2":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_perete_2);
                                            imageView.setScaleX((float) 2);
                                            break;
                                        case "adaugare_usa_2":
                                            imageView.setBackgroundResource(R.mipmap.ic_launcher_foreground_usa_2);
                                            imageView.setScaleY((float) 2);
                                            break;
                                    }

                                    celula.addView(imageView);
                                }

                                planificare_restaurant.addView(celula);
                            }
                        }
                    }
                }
        );
    }
}
