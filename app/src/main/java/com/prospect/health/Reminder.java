package com.prospect.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reminder extends AppCompatActivity {
   /* Button btDate, btTime, buttonMas;
    EditText edDate, edTime, editName;
    private int day, month, year, time, minutes;*/

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Button mButtonL;
    private Button mButtonM;
    private Button mButtonX;
    private Button mButtonJ;
    private Button mButtonV;
    private Button mButtonS;
    private Button mButtonD;

    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;
    public static String date1;

    Long timeAlarm;
    String finalHour;
    String finalMinute;
    int selectedMinute1;
    int selectedHour1;

    private EditText mEditMmedicamento;
    private String medicamento;


    boolean id;
    boolean id1;
    boolean id2;
    boolean id3;
    boolean id4;
    boolean id5;
    boolean id6;
    boolean idglobal=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        mEditMmedicamento = (EditText) findViewById(R.id.editMedicamento);
        medicamento = (mEditMmedicamento.getText() != null)? mEditMmedicamento.getText().toString(): "hola";
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Usuario/"+id+"/Alarma/"+medicamento);

        mButtonL = (Button) findViewById(R.id.buttonL);
        ButonDias(mButtonL, id);
        Log.d("myTag", "hola1"+id+"j");
        mButtonM = (Button) findViewById(R.id.buttonM);
        ButonDias(mButtonM, id1);
        Log.d("myTag", "hola2"+id1+"j");
        mButtonX = (Button) findViewById(R.id.buttonX);
        ButonDias(mButtonX, id2);
        mButtonJ = (Button) findViewById(R.id.buttonJ);
        ButonDias(mButtonJ, id3);
        mButtonV = (Button) findViewById(R.id.buttonV);
        ButonDias(mButtonV, id4);
        mButtonS = (Button) findViewById(R.id.buttonS);
        ButonDias(mButtonS, id5);
        mButtonD = (Button) findViewById(R.id.buttonD);
        ButonDias(mButtonD, id6);


        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");

        notificationsTime = (TextView) findViewById(R.id.notifications_time);
        Calendar date = new GregorianCalendar();
        int hour1 = date.get(Calendar.HOUR_OF_DAY);
        int minute1 = date.get(Calendar.MINUTE);
        date1 = hour+"-"+minute1;
        notificationsTime.setText(date1);

        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
        }
        findViewById(R.id.notifications_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
            }
        });

        findViewById(R.id.notifications_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       // String finalHour, finalMinute;
                        selectedHour1=selectedHour;
                        selectedMinute1=selectedMinute;
                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                       if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        /*Calendar today = Calendar.getInstance();

                        today.set(Calendar.DAY_OF_WEEK, 0);
                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);*/

                        /*SharedPreferences.Editor edit = settings.edit();
                        edit.putString("day", "Monday");
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());
                        timeAlarm = today.getTimeInMillis();

                        edit.commit();*/

                        //.makeText(Reminder.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Reminder.this);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecione la hora");
                mTimePicker.show();

            }
        });

       /* btDate=(Button)findViewById(R.id.buttonDate);
        btTime=(Button)findViewById(R.id.buttonTime);
        buttonMas=(Button)findViewById(R.id.buttonMas);
        edDate=(EditText)findViewById(R.id.editDate);
        edTime=(EditText)findViewById(R.id.editTime);
        editName=(EditText)findViewById(R.id.editNameMedicine);
        btDate.setOnClickListener(this);
        btTime.setOnClickListener(this);
        buttonMas.setOnClickListener(this);*/
    }

    public void MasAlarm(View view) {
        List<Integer> day = new ArrayList<Integer>();

        day.add(000);
        day.add(111);
        day.add(0011);

        Log.d("myTag", "hola"+id+id1+id3+"j");
        /*if (id) {
            day.add(0);
        }
        if (id1) {
            day.add(1);
        }
        if (id2) {
            day.add(2);
        }
        if (id3) {
            day.add(3);
        }
        if (id4) {
            day.add(4);
        }
        if (id5) {
            day.add(5);
        }
        if (id6) {
            day.add(6);
        }*/

         Toast.makeText(Reminder.this, "Holaa mundo", Toast.LENGTH_LONG).show();
        /*if (selectedHour1 < 10) finalHour = "0" + selectedHour1;
        if (selectedMinute1 < 10) finalMinute = "0" + selectedMinute1;
        notificationsTime.setText(finalHour + ":" + finalMinute);*/
        Map<String, Object> map = new HashMap<>();
        Log.d("myTag", "Afuera");
        String data = "hola "+ day.size() +" hola";
        Log.d("myTag", data);
        for (int i = 0; i < day.size(); i++){
            Log.d("myTag", "Inicio uno");
            Toast.makeText(Reminder.this, "hola"+i, Toast.LENGTH_LONG).show();
            Calendar today = Calendar.getInstance();

            map.put( "Day", day.get(i));
            map.put("Hour", selectedHour1);
            map.put( "Minute ", selectedMinute1);

        today.set(Calendar.DAY_OF_WEEK, day.get(i));
        today.set(Calendar.HOUR_OF_DAY, selectedHour1);
        today.set(Calendar.MINUTE, selectedMinute1);
        today.set(Calendar.SECOND, 0);


        SharedPreferences.Editor edit = settings.edit();
        edit.putString("day", day.get(i).toString());
        edit.putString("hour", finalHour);
        edit.putString("minute", finalMinute);

        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
        edit.putInt("alarmID", alarmID);
        edit.putLong("alarmTime", today.getTimeInMillis());
        timeAlarm = today.getTimeInMillis();

        edit.commit();

        Toast.makeText(Reminder.this, finalMinute+" "+selectedHour1 , Toast.LENGTH_LONG).show();

        Utils.setAlarm(alarmID, today.getTimeInMillis(), Reminder.this);


    }
        saveData(map); 
        /*id=false; id1=false; id2=false; id3=false; id4=false; id5=false; id6=false;
        ButonDias(mButtonL, id);ButonDias(mButtonM, id1);ButonDias(mButtonX, id2);ButonDias(mButtonJ, id3);
        ButonDias(mButtonV, id4);ButonDias(mButtonS, id5);ButonDias(mButtonD, id6);*/
        mEditMmedicamento.setText("");
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v==btDate){
            final Calendar calendar= Calendar.getInstance();
            day=calendar.get(Calendar.DAY_OF_MONTH);
            month=calendar.get(Calendar.MONTH);
            year=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edDate.setText(dayOfMonth+"/"+ (month+1)+"/"+year);
                }
            }, day,month,year);
            datePickerDialog.show();
        }
        if(v==btTime){
            final Calendar calendar = Calendar.getInstance();
            time=calendar.get(Calendar.HOUR_OF_DAY);
            minutes=calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    edTime.setText(hourOfDay+":"+minute);
                }
            }, time, minutes, false);
            timePickerDialog.show();
        }
        if(v==buttonMas){
            AlertDialog.Builder builder = new AlertDialog.Builder(Reminder.this);
            builder.setIcon(R.mipmap.ic_launcher).
                    setTitle("Alarm Activated").
                    setMessage("Name of the Medicine: "+editName.getText().toString()+"\nDate: " + edDate.getText().toString()+ "\nTime: " + edTime.getText().toString());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            editName.setText("");
            edDate.setText("");
            edTime.setText("");
        }
    }*/

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                startActivity(new Intent(Reminder.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(Reminder.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ButonDias(final Button Dia, final boolean id111){
        Dia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                hola(Dia, id111);
            }});
        Log.d( "mytag", " hola global "+id111 );
    }

    public void hola(Button Dia, boolean id111){
        if(!id111){
            Dia.setBackgroundColor(Color.parseColor("#7BC85D"));
            id111=true;
            Log.d( "mytag", " hola globalIF "+id111 );
        }else if(id111){
            Dia.setBackgroundColor(Color.parseColor("#FFFFFF"));
            id111=false;
            Log.d( "mytag", " hola globalELSE "+id111 );
        }
    }
    //Almacenar datos
    private void saveData(Map map){

        mDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    // Log.d("myTag", "Su registro ha sido exitoso");
                    Toast.makeText( Reminder.this, "Alarma guardada!", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }
        public void return0(View v){
            Intent return0 = new Intent(this, MainActivity.class);
            startActivity(return0);
        }

   /* //debe redireccional al pantallazo principal que sera el layout
    //con estadistica de progreso de el usuario e informacion basica de el
    public void continuee(View v){
        Intent returnn = new Intent(this, MainActivity.class);
        startActivity(returnn);
    }

    public static void setAlarm(int i, long timestamp, Context ctx){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }
    */
}