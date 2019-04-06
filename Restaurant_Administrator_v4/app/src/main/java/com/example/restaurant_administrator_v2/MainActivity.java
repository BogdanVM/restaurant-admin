package com.example.restaurant_administrator_v2;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private LinearLayout layout_elemente_1, layout_elemente_2, layout_elemente_3;

    private RelativeLayout layout_planificare;
    private ConstraintLayout ecran;
    private ImageView imageView_masa;
    private ImageView imageView_bucatarie;
    private ImageView imageView_toaleta;
    private ImageView imageView_bar;
    private ImageView imageView_perete;
    private ImageView imageView_perete_2;
    private ImageView imageView_perete_3;
    private ImageView imageView_usa;
    private ImageView imageView_usa_2;
    private ImageView imageView_dreapta;
    private ImageView imageView_stanga;

    private Button start;
    private Button button_sterge;
    private Button button_next;
    private Button button_undo;

    private String id_imagine;
    private int latime_ecran;
    private int inaltime_ecran;

    Deque<String> stiva_celule = new ArrayDeque<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

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

        imageView_usa = (ImageView) findViewById(R.id.adaugare_usa);
        imageView_usa.setOnTouchListener(new MyTouchListener());

        imageView_usa_2 = (ImageView) findViewById(R.id.adaugare_usa_2);
        imageView_usa_2.setOnTouchListener(new MyTouchListener());

        imageView_dreapta = (ImageView) findViewById(R.id.imageView_next);
        imageView_stanga = (ImageView) findViewById(R.id.imageView_stanga);

        myDb = new DatabaseHelper(this);

        button_sterge = (Button) findViewById(R.id.button_sterge_linie);
        button_sterge.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteData();
                    }
                }
        );

        button_next = (Button) findViewById(R.id.button_finalizeaza);
        FinalizeazaPlanificare();

        button_undo = (Button) findViewById(R.id.button_undo);
        Anuleaza_actiune();

        SchimbaLegendaDreapta();
        SchimbaLegendaStanga();

        start = (Button) findViewById(R.id.Start);
        ConstruiestePlanificarea();
    }

    public int get_numar_celule_inaltime() {
        return inaltime_ecran / 100;
    }

    public int get_numar_celula_latime() {
        return latime_ecran / 100;
    }

    public void ConstruiestePlanificarea() {
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        latime_ecran = ecran.getWidth() - 350;
                        inaltime_ecran = ecran.getHeight();

                        int numar_celule_inaltime = get_numar_celule_inaltime();
                        int numar_celula_latime = get_numar_celula_latime();

                        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                        int latime_celula = (int) (50 * scale + 0.5f);

                        //Toast.makeText(MainActivity.this, String.valueOf(numar_celule_inaltime + " " + numar_celula_latime + " " + latime_celula), Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < numar_celule_inaltime; i++) {
                            for (int j = 0; j < numar_celula_latime; j++) {

                                final LinearLayout celula = new LinearLayout(MainActivity.this);
                                celula.setOrientation(LinearLayout.VERTICAL);

                                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(latime_celula, latime_celula);
                                lp1.setMargins(j * latime_celula, i * latime_celula, 0, 0);
                                celula.setLayoutParams(lp1);
                                celula.setBackgroundColor(Color.parseColor("#13775000"));
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

    public void SchimbaLegendaDreapta() {
        imageView_dreapta.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, String.valueOf(layout_planificare.getChildCount()), Toast.LENGTH_SHORT).show();
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

    public void AddData() {

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {

            int valid = 1;
            boolean isInserted = true;
            //Toast.makeText(MainActivity.this, String.valueOf(get_numar_celule_inaltime()) + " " + String.valueOf(get_numar_celula_latime()), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < get_numar_celule_inaltime(); i++) {
                for (int j = 0; j < get_numar_celula_latime(); j++) {

                    if (i == 0 && j == 0) {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                null,
                                null,
                                null);
                    } else if (i == 0 && j > 0) {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                null,
                                "celula_" + String.valueOf(i) + "_" + String.valueOf(j - 1),
                                null);
                    } else if (i > 0 && j == 0) {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                "celula_" + String.valueOf(i - 1) + "_" + String.valueOf(j),
                                null,
                                null);
                    } else {
                        isInserted = myDb.insertData("celula_" + String.valueOf(i) + "_" + String.valueOf(j),
                                "celula_" + String.valueOf(i - 1) + "_" + String.valueOf(j),
                                "celula_" + String.valueOf(i) + "_" + String.valueOf(j - 1), null);
                    }

                    if (isInserted == false)
                        valid = 0;
                }
            }

            if (valid == 1)
                Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();

        }
    }

    public void UpdateData(String celula_id, String continut) {
        boolean isUpdated = myDb.updateData(celula_id, continut);

        if (isUpdated == true)
            Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
    }

    public void DeleteData() {

        Cursor res = myDb.getAllData();

        myDb.deleteData();

        if (res.getCount() == 0)
            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

    }

    public void FinalizeazaPlanificare() {
        button_next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Reincarca_Planificare.class));
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

    public void Anuleaza_actiune() {
        button_undo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(MainActivity.this,String.valueOf(inaltime_ecran/100+" "+latime_ecran/100),Toast.LENGTH_SHORT).show();
                        try {
                            String ultima_celula = stiva_celule.getFirst();

                            View view = null;
                            LinearLayout celula;

                            for (int i = 0; i < layout_planificare.getChildCount(); i++) {
                                view = layout_planificare.getChildAt(i);

                                if (((String) view.getTag()).equals(ultima_celula)) {
                                    myDb.undoImage((String) view.getTag());
                                    celula = (LinearLayout) view;
                                    celula.removeAllViews();
                                    stiva_celule.removeFirst();

                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Stiva de elemente este goala.", Toast.LENGTH_LONG).show();
                        } finally {
                            //Toast.makeText(MainActivity.this, String.valueOf(stiva_celule.size()), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);


                case MotionEvent.ACTION_UP:
                    id_imagine = ObtineIdElement(view);
                    //Toast.makeText(MainActivity.this, id_imagine, Toast.LENGTH_SHORT).show();

                    break;
            }
            return true;
        }
    }

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
                    ImageView newView = new ImageView(getApplicationContext());

                    newView.setImageBitmap(((BitmapDrawable) oldView.getDrawable()).getBitmap());

                    if (id_imagine.equals("adaugare_masa")) {
                        newView.setScaleX((float) 0.75);
                        newView.setScaleY((float) 0.75);

                    } else if (id_imagine.equals("adaugare_perete") || id_imagine.equals("adaugare_usa_2")) {
                        newView.setScaleY((float) 2);
                    } else if (id_imagine.equals("adaugare_perete_2") || id_imagine.equals("adaugare_usa")) {
                        newView.setScaleX((float) 2);
                    }

                    container.addView(newView);

                    String id_celula = (String) v.getTag();
                    stiva_celule.push(id_celula);

                    view.setVisibility(view.VISIBLE);

                    UpdateData(id_celula, id_imagine);

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
