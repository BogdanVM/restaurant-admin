package com.gr232.restaurantadmin.activities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.gr232.restaurantadmin.R;
import com.gr232.restaurantadmin.helpers.DatabaseHelper;
import com.gr232.restaurantadmin.helpers.VerificaConexiune;

public class ViewHallActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    DatabaseHelper myDb;

    private Button reincarca_planificarea;
    private RelativeLayout planificare_restaurant;
    private int id_masa = 1;

    FirebaseFirestore dbRef;
    VerificaConexiune vc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hall);

        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseApp.initializeApp(this);
        dbRef = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        dbRef.setFirestoreSettings(settings);

        myDb = new DatabaseHelper(this);
        reincarca_planificarea = (Button) findViewById(R.id.button_reincarca_planificarea);
        reincarca_planificarea.setTextColor(getApplication().getResources().getColor(R.color.whiteColor));
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adauga_comanda:
                Toast.makeText(ViewHallActivity.this, "Comanda adaugata.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.anuleaza_comanda:
                Toast.makeText(ViewHallActivity.this, "Comanda anulata.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.schimba_masa:
                Toast.makeText(ViewHallActivity.this, "Comanda mutata.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    public void ReincarcaPlanificarea() {
        reincarca_planificarea.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                        final int latime_celula = (int) (50 * scale + 0.5f);

                        Cursor res = myDb.getAllData();

                        if (res.getCount() == 0) {
                            Toast.makeText(ViewHallActivity.this, "Nothing found.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String nume_imagine;

                        String s = "";
                        Cursor res1 = myDb.getAllData();
                        res1.moveToLast();
                        s = res1.getString(res1.getColumnIndex("CELULA_ID"));

                        final int numar_linii = Integer.parseInt(ObtineNumarLinii(s));
                        final int numar_coloane = Integer.parseInt(ObtineNumarColoane(s));
                        //Toast.makeText(ViewHallActivity.this,String.valueOf(numar_coloane),Toast.LENGTH_SHORT).show();

                        vc = new VerificaConexiune(ViewHallActivity.this);

                        if (!vc.isConnected()) {

                            for (int i = 0; i < numar_linii + 1; i++) {
                                for (int j = 0; j < numar_coloane + 1; j++) {

                                    LinearLayout celula = new LinearLayout(ViewHallActivity.this);
                                    celula.setOrientation(LinearLayout.VERTICAL);
                                    RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(latime_celula, latime_celula);
                                    lp1.setMargins(j * latime_celula, i * latime_celula, 0, 0);
                                    celula.setLayoutParams(lp1);
                                    celula.setBackgroundColor(Color.parseColor("#13775000"));

                                    res.moveToNext();
                                    nume_imagine = res.getString(3);

                                    //Toast.makeText(ViewHallActivity.this,nume_imagine,Toast.LENGTH_SHORT).show();

                                    if (nume_imagine != null) {
                                        ImageView imageView = new ImageView(ViewHallActivity.this);
                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(latime_celula, latime_celula);
                                        imageView.setLayoutParams(layoutParams);

                                        switch (nume_imagine) {
                                            case "adaugare_masa":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_masa);
                                                imageView.setScaleX((float) 0.75);
                                                imageView.setScaleY((float) 0.75);
                                                break;
                                            case "adaugare_bar":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_bar);
                                                break;
                                            case "adaugare_perete_3":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_3);
                                                break;
                                            case "adaugare_bucatarie":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_bucatarie);
                                                imageView.setScaleX((float) 1.3);
                                                imageView.setScaleY((float) 1.3);
                                                break;
                                            case "adaugare_perete":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete);
                                                break;
                                            case "adaugare_perete_5":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_5);
                                                break;
                                            case "adaugare_toaleta":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_toaleta);
                                                break;
                                            case "adaugare_perete_2":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_2);
                                                break;
                                            case "adaugare_perete_4":
                                                imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_4);
                                                break;
                                        }

                                        celula.addView(imageView);
                                    }

                                    planificare_restaurant.addView(celula);
                                }
                            }
                        } else {

                            int i, j;
                            for (i = 0; i < numar_linii + 1; i++) {
                                for (j = 0; j < numar_coloane + 1; j++) {

                                    final LinearLayout celula = new LinearLayout(ViewHallActivity.this);
                                    celula.setOrientation(LinearLayout.VERTICAL);
                                    RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(latime_celula, latime_celula);
                                    lp1.setMargins(j * latime_celula, i * latime_celula, 0, 0);
                                    celula.setLayoutParams(lp1);
                                    celula.setBackgroundColor(Color.parseColor("#13775000"));

                                    DocumentReference dr = dbRef.collection("celule")
                                            .document("celula_" + String.valueOf(i) + "_" + String.valueOf(j));

                                    dr.get().addOnCompleteListener(
                                            new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    String nume_imagine;

                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();


                                                        if (documentSnapshot.exists()) {
                                                            nume_imagine = String.valueOf(documentSnapshot.get("Continut"));

                                                            ImageView imageView = new ImageView(ViewHallActivity.this);
                                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(latime_celula, latime_celula);
                                                            imageView.setLayoutParams(layoutParams);

                                                            switch (nume_imagine) {
                                                                case "adaugare_masa":

                                                                    final String numar_masa = String.valueOf(documentSnapshot.get("Numar"));
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_masa);
                                                                    imageView.setScaleX((float) 0.75);
                                                                    imageView.setScaleY((float) 0.75);
                                                                    imageView.setId(id_masa);
                                                                    id_masa++;
                                                                    imageView.setOnClickListener(
                                                                            new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    Context wrapper = new ContextThemeWrapper(ViewHallActivity.this, R.style.popupMenuStyle);
                                                                                    PopupMenu popupMenu = new PopupMenu(wrapper, v);
                                                                                    popupMenu.setOnMenuItemClickListener(ViewHallActivity.this);
                                                                                    popupMenu.inflate(R.menu.popup_table);
                                                                                    popupMenu.getMenu().getItem(0).setTitle("  Masa " + numar_masa);
                                                                                    popupMenu.show();
                                                                                    Toast.makeText(ViewHallActivity.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();

                                                                                }

                                                                            }
                                                                    );
                                                                    break;
                                                                case "adaugare_bar":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_bar);
                                                                    break;
                                                                case "adaugare_perete_3":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_3);
                                                                    break;
                                                                case "adaugare_bucatarie":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_bucatarie);
                                                                    imageView.setScaleX((float) 1.3);
                                                                    imageView.setScaleY((float) 1.3);
                                                                    break;
                                                                case "adaugare_perete":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete);
                                                                    break;
                                                                case "adaugare_perete_5":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_5);
                                                                    break;
                                                                case "adaugare_toaleta":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_toaleta);
                                                                    break;
                                                                case "adaugare_perete_2":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_2);
                                                                    break;
                                                                case "adaugare_perete_4":
                                                                    imageView.setBackgroundResource(R.drawable.ic_launcher_foreground_perete_4);
                                                                    break;
                                                            }

                                                            celula.addView(imageView);

                                                        } else {
                                                            //
                                                        }
                                                    } else {
                                                        //
                                                    }
                                                }
                                            }
                                    );
                                    planificare_restaurant.addView(celula);

                                }

                            }
                        }
                    }
                }
        );
    }
}
