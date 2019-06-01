package com.gr232.restaurantadmin.activities;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.gr232.restaurantadmin.R;
import com.gr232.restaurantadmin.helpers.DatabaseHelper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Clasa corespunzatoare activitatii care va permite modificarea planificarii restaurantului.
 */
public class HallActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private LinearLayout layout_elemente_1, layout_elemente_2, layout_elemente_3;
    private int numar_masa_local = 1;
    private int numar_masa = 1;

    private RelativeLayout layout_planificare;
    private ConstraintLayout ecran;
    private ImageView imageView_masa;
    private ImageView imageView_bucatarie;
    private ImageView imageView_toaleta;
    private ImageView imageView_bar;
    private ImageView imageView_perete;
    private ImageView imageView_perete_2;
    private ImageView imageView_perete_3;
    private ImageView imageView_perete_5;
    private ImageView imageView_perete_4;
    private ImageView imageView_dreapta;
    private ImageView imageView_stanga;

    private Button start;
    private Button button_sterge;
    private Button button_next;

    private String id_imagine;
    private int latime_ecran;
    private int inaltime_ecran;

    public Deque<Integer> numarMasaDisponibil = new ArrayDeque<>();

    FirebaseFirestore dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseApp.initializeApp(this);
        dbRef = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        dbRef.setFirestoreSettings(settings);

        layout_planificare = (RelativeLayout) findViewById(R.id.planificare_restaurant);
        layout_elemente_1 = (LinearLayout) findViewById(R.id.layout_elemente);
        layout_elemente_2 = (LinearLayout) findViewById(R.id.layout_elemente_2);
        layout_elemente_3 = (LinearLayout) findViewById(R.id.layout_elemente_3);
        ecran = (ConstraintLayout) findViewById(R.id.ecran);

        imageView_masa = (ImageView) findViewById(R.id.adaugare_masa);
        imageView_masa.setOnTouchListener(new MyTouchListener());

        imageView_bucatarie = (ImageView) findViewById(R.id.adaugare_bucatarie);
        imageView_bucatarie.setOnTouchListener(new MyTouchListener());

        imageView_toaleta = (ImageView) findViewById(R.id.adaugare_toaleta);
        imageView_toaleta.setOnTouchListener(new MyTouchListener());

        imageView_bar = (ImageView) findViewById(R.id.adaugare_bar);
        imageView_bar.setOnTouchListener(new MyTouchListener());

        imageView_perete = (ImageView) findViewById(R.id.adaugare_perete);
        imageView_perete.setOnTouchListener(new MyTouchListener());

        imageView_perete_2 = (ImageView) findViewById(R.id.adaugare_perete_2);
        imageView_perete_2.setOnTouchListener(new MyTouchListener());

        imageView_perete_3 = (ImageView) findViewById(R.id.adaugare_perete_3);
        imageView_perete_3.setOnTouchListener(new MyTouchListener());

        imageView_perete_5 = (ImageView) findViewById(R.id.adaugare_perete_5);
        imageView_perete_5.setOnTouchListener(new MyTouchListener());

        imageView_perete_4 = (ImageView) findViewById(R.id.adaugare_perete_4);
        imageView_perete_4.setOnTouchListener(new MyTouchListener());

        imageView_dreapta = (ImageView) findViewById(R.id.imageView_next);
        imageView_stanga = (ImageView) findViewById(R.id.imageView_stanga);

        myDb = new DatabaseHelper(this);

        button_sterge = (Button) findViewById(R.id.button_sterge_linie);
        button_sterge.setTextColor(getApplication().getResources().getColor(R.color.whiteColor));
        button_sterge.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteData();
                    }
                }
        );

        button_next = (Button) findViewById(R.id.button_finalizeaza);
        button_next.setTextColor(getApplication().getResources().getColor(R.color.whiteColor));
        FinalizeazaPlanificare();

        SchimbaLegendaDreapta();
        SchimbaLegendaStanga();

        start = (Button) findViewById(R.id.Start);
        start.setTextColor(getApplication().getResources().getColor(R.color.whiteColor));
        ConstruiestePlanificarea();
    }

    public int get_numar_celule_inaltime() {
        inaltime_ecran = ecran.getHeight();
        return inaltime_ecran / 100;
    }

    public int get_numar_celula_latime() {
        latime_ecran = ecran.getWidth() - 350;
        return latime_ecran / 100;
    }

    /**
     * La apasarea butonului ”start” se formeaza matricea plansei prin apeluri utile ale metodelor:
     *          ”get_numar_celula_latime()”,  ”get_numar_celule_inaltime()”.
     *
     * Se creeaza celulele care sunt adaugate la RelativeLayout-ul mare, pentru a putea fi modificat
     * ulterior continutul lor.
     */
    public void ConstruiestePlanificarea() {
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int numar_celule_inaltime = get_numar_celule_inaltime();
                        int numar_celula_latime = get_numar_celula_latime();

                        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                        int latime_celula = (int) (50 * scale + 0.5f);

                        //Toast.makeText(MainActivity.this, String.valueOf(numar_celule_inaltime + " " + numar_celula_latime + " " + latime_celula), Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < numar_celule_inaltime; i++) {
                            for (int j = 0; j < numar_celula_latime; j++) {

                                final LinearLayout celula = new LinearLayout(HallActivity.this);
                                celula.setOrientation(LinearLayout.VERTICAL);

                                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(latime_celula, latime_celula);
                                lp1.setMargins(j * latime_celula, i * latime_celula, 0, 0);
                                celula.setLayoutParams(lp1);
                                celula.setOnDragListener(new MyDragListener());
                                celula.setTag("celula_" + i + "_" + j);

                                layout_planificare.addView(celula);

                            }
                        }
                        start.setEnabled(false);

                        Cursor res = myDb.getAllData();

                        if (res.getCount() == 0) {
                            AddData();
                        }
                    }
                }
        );
    }

    /**
     * La apasarea sagetii dreapta, se vor schimba imaginiile care pot fi adaugate RelativeLayout-ului.
     * Se verifica elementele vizibile care sunt transformate in invizibil, si invers.
     */
    public void SchimbaLegendaDreapta() {
        imageView_dreapta.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(HallActivity.this, String.valueOf(layout_planificare.getChildCount()), Toast.LENGTH_SHORT).show();
                        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) layout_elemente_1.getLayoutParams();

                        if (layout_elemente_1.getVisibility() == View.VISIBLE) {
                            layout_elemente_2.setLayoutParams(lp);
                            layout_elemente_1.setVisibility(View.GONE);
                            layout_elemente_2.setVisibility(View.VISIBLE);


                        } else if (layout_elemente_2.getVisibility() == View.VISIBLE) {
                            layout_elemente_3.setLayoutParams(lp);
                            layout_elemente_2.setVisibility(View.GONE);
                            layout_elemente_3.setVisibility(View.VISIBLE);

                        } else {
                            layout_elemente_3.setVisibility(View.GONE);
                            layout_elemente_1.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    /**
     * La apasarea sagetii stanga, se vor schimba imaginiile care pot fi adaugate RelativeLayout-ului.
     * Se verifica elementele vizibile care sunt transformate in invizibil, si invers.
     */
    public void SchimbaLegendaStanga() {
        imageView_stanga.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) layout_elemente_1.getLayoutParams();

                        if (layout_elemente_1.getVisibility() == View.VISIBLE) {
                            layout_elemente_3.setLayoutParams(lp);
                            layout_elemente_1.setVisibility(View.GONE);
                            layout_elemente_3.setVisibility(View.VISIBLE);

                        } else if (layout_elemente_2.getVisibility() == View.VISIBLE) {
                            layout_elemente_2.setVisibility(View.GONE);
                            layout_elemente_1.setVisibility(View.VISIBLE);

                        } else {
                            layout_elemente_2.setLayoutParams(lp);
                            layout_elemente_3.setVisibility(View.GONE);
                            layout_elemente_2.setVisibility(View.VISIBLE);

                        }
                    }
                }
        );

    }

    /**
     * Se insereaza celulele in baza de date locala (SQLite) si externa (Firebase Firestore).
     * Se trateaza cazurile speciale (prima coloana, prima linie, ultima coloana, ultima linie).
     */
    public void AddData() {

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {

            int valid = 1;
            boolean isInserted = true;
            //Toast.makeText(HallActivity.this, String.valueOf(get_numar_celule_inaltime()) + " " + String.valueOf(get_numar_celula_latime()), Toast.LENGTH_SHORT).show();

            Map<String, Object> celula = new HashMap<>();

            for (int i = 0; i < get_numar_celule_inaltime(); i++) {
                for (int j = 0; j < get_numar_celula_latime(); j++) {

                    if (i == 0 && j == 0) {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                null,
                                null,
                                null,
                                0);

                        celula.put("IdCelula", "celula_" + String.valueOf(i) + "_" + String.valueOf(j));
                        celula.put("VecinStanga", null);
                        celula.put("VecinSus", null);
                        celula.put("Continut", null);
                        celula.put("Numar", 0);


                        dbRef.collection("celule").document("celula_" + String.valueOf(i) + "_" + String.valueOf(j)).set(celula).addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(HallActivity.this, "O mers", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );


                    } else if (i == 0 && j > 0) {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                null,
                                "celula_" + String.valueOf(i) + "_" + String.valueOf(j - 1),
                                null,
                                0);


                        celula.put("IdCelula", "celula_" + i + "_" + j);
                        celula.put("VecinStanga", "celula_" + i + "_" + (j - 1));
                        celula.put("VecinSus", null);
                        celula.put("Continut", null);
                        celula.put("Numar", 0);


                        dbRef.collection("celule").document("celula_" + String.valueOf(i) + "_" +
                                j).set(celula).addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(HallActivity.this, "O mers", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );

                    } else if (i > 0 && j == 0) {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                "celula_" + String.valueOf(i - 1) + "_" + String.valueOf(j),
                                null,
                                null, 0);

                        celula.put("IdCelula", "celula_" + i + "_" + j);
                        celula.put("VecinStanga", null);
                        celula.put("VecinSus", "celula_" + (i - 1) + "_" + j);
                        celula.put("Continut", null);
                        celula.put("Numar", 0);


                        dbRef.collection("celule").document("celula_" + String.valueOf(i) + "_" + String.valueOf(j)).set(celula).addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(HallActivity.this, "O mers", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );

                    } else {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                "celula_" + String.valueOf(i - 1) + "_" + String.valueOf(j),
                                "celula_" + String.valueOf(i) + "_" + String.valueOf(j - 1), null, 0);

                        celula.put("IdCelula", "celula_" + String.valueOf(i) + "_" + String.valueOf(j));
                        celula.put("VecinStanga", "celula_" + String.valueOf(i) + "_" + String.valueOf(j - 1));
                        celula.put("VecinSus", "celula_" + String.valueOf(i - 1) + "_" + String.valueOf(j));
                        celula.put("Continut", null);
                        celula.put("Numar", 0);


                        dbRef.collection("celule").document("celula_" + String.valueOf(i) + "_" + String.valueOf(j)).set(celula).addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(HallActivity.this, "O mers", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                    }

                    if (isInserted == false)
                        valid = 0;
                }
            }

            if (valid == 1)
                Toast.makeText(HallActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(HallActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Se updateaza continutul celulei in baza de date locala SQLite.
     * @param celula_id id-ul celulei
     * @param continut continutul care trebuie actualizat
     */
    public void UpdateCellContent(String celula_id, final String continut) {

        boolean isUpdated = myDb.updateCellContent(celula_id, continut);

        if (isUpdated == true)
            Toast.makeText(HallActivity.this, "Data updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(HallActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
    }

    /**
     * Se updateaza numarul tabelului in baza de date locala SQLite.
     * @param celula_id
     * @param numar
     */
    public void UpdateTableNumber(String celula_id, final int numar) {

        boolean isUpdated = myDb.updateTableNumber(celula_id, numar);

        if (isUpdated == true)
            Toast.makeText(HallActivity.this, "Data updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(HallActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
    }

    public void DeleteData() {

        for (int i = 0; i < get_numar_celule_inaltime(); i++) {
            for (int j = 0; j < get_numar_celula_latime(); j++) {

                dbRef.collection("celule").document("celula_" + String.valueOf(i) + "_" + String.valueOf(j)).delete().addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(HallActivity.this, "O mers", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        }


        Cursor res = myDb.getAllData();

        myDb.deleteData();

        if (res.getCount() == 0)
            Toast.makeText(HallActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(HallActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

    }

    public void FinalizeazaPlanificare() {
        button_next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HallActivity.this, ViewHallActivity.class));
                    }
                }
        );
    }

    public void stergeElement(final View view) {
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(HallActivity.this, R.style.AlertDialog);
                        builder.setMessage("Doriti sa stergeti elementul selectat?");
                        //builder.setTitle("Alert");
                        builder.setCancelable(false);

                        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (String.valueOf(view.getTag()).equals("adaugare_masa")) {
                                    numar_masa--;
                                    numar_masa_local--;
                                }

                                LinearLayout celula = (LinearLayout) view.getParent();
                                celula.removeAllViews();

                                myDb.undoImage((String) celula.getTag());

                                numarMasaDisponibil.push(view.getId());
                                UpdateTableNumber((String) celula.getTag(), 0);

                                final CollectionReference ref = dbRef.collection("celule");
                                ref.whereEqualTo("IdCelula", celula.getTag()).get().addOnCompleteListener(
                                        new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                        Map<String, Object> map = new HashMap<>();
                                                        map.put("Continut", null);
                                                        ref.document(documentSnapshot.getId()).set(map, SetOptions.merge());

                                                        Map<String, Integer> map1 = new HashMap<>();
                                                        map1.put("Numar", 0);
                                                        ref.document(documentSnapshot.getId()).set(map1, SetOptions.merge());
                                                    }
                                                }
                                            }
                                        }
                                ).addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                );


                            }
                        });
                        builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
        );
    }

    public String ObtineIdElement(View v) {
        String path = v.getResources().getResourceName(v.getId());
        String[] split = path.split("/");
        String idElement = split[1];

        return idElement;
    }

    /**
     * Aceasta clasa detecteaza ”ridicarea” unei imagini (evenimentul de drag).
     * In momentul in care acest eveniment are loc, este salvat id-ul imaginii selectate.
     */
    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);


                case MotionEvent.ACTION_UP:
                    id_imagine = ObtineIdElement(view);
                    //Toast.makeText(HallActivity.this, id_imagine, Toast.LENGTH_SHORT).show();

                    break;
            }
            return true;
        }
    }

    /**
     * Prin aceasta clasa este implementat evenimentul de drag and drop pentru imagini.
     * Este implementata interfata OnDragListener, iar in cazul evenimentului onDrop, este
     * updatat continutul celulei unde a fost lasata imaginea.
     */
    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    final LinearLayout container = (LinearLayout) v;
                    ImageView oldView = (ImageView) view;
                    final ImageView newView = new ImageView(getApplicationContext());

                    newView.setImageBitmap(((BitmapDrawable) oldView.getDrawable()).getBitmap());

                    String id_celula = (String) v.getTag();

                    if (numarMasaDisponibil.size() > 0) {
                        newView.setId(numarMasaDisponibil.getLast());
                        numarMasaDisponibil.removeLast();
                    } else {
                        newView.setId(numar_masa);
                    }

                    if (id_imagine.equals("adaugare_masa")) {
                        newView.setScaleX((float) 0.75);
                        newView.setScaleY((float) 0.75);

                        UpdateTableNumber(id_celula, numar_masa_local);

                        final CollectionReference ref = dbRef.collection("celule");
                        ref.whereEqualTo("IdCelula", id_celula).get().addOnCompleteListener(
                                new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                Map<String, Integer> map = new HashMap<>();
                                                map.put("Numar", newView.getId());
                                                ref.document(documentSnapshot.getId()).set(map, SetOptions.merge());
                                            }
                                        }
                                    }
                                }
                        ).addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                        numar_masa++;
                        numar_masa_local++;
                    } else if (id_imagine.equals("adaugare_bucatarie")) {
                        newView.setScaleX((float) 1.3);
                        newView.setScaleY((float) 1.3);
                    }

                    stergeElement(newView);
                    newView.setTag(id_imagine);


                    container.addView(newView);

                    view.setVisibility(view.VISIBLE);

                    UpdateCellContent(id_celula, id_imagine);

                    final CollectionReference ref = dbRef.collection("celule");
                    ref.whereEqualTo("IdCelula", id_celula).whereEqualTo("Continut", null).get().addOnCompleteListener(
                            new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                            Map<Object, String> map = new HashMap<>();
                                            map.put("Continut", id_imagine);
                                            ref.document(documentSnapshot.getId()).set(map, SetOptions.merge());
                                        }
                                    }
                                }
                            }
                    ).addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(HallActivity.this, "Nu o mers", Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                    if (numarMasaDisponibil.size() > 0)
                        Toast.makeText(HallActivity.this, String.valueOf(numarMasaDisponibil.getLast()), Toast.LENGTH_LONG).show();

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
