package com.prospect.health;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Reminder extends AppCompatActivity {
   /* Button btDate, btTime, buttonMas;
    EditText edDate, edTime, editName;
    private int day, month, year, time, minutes;*/

    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");

        notificationsTime = (TextView) findViewById(R.id.notifications_time);

        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
        }

        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                        Toast.makeText(Reminder.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        Utils.setAlarm(alarmID, today.getTimeInMillis(), Reminder.this);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
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

    public void returnn(View v){
        Intent returnn = new Intent(this, MainActivity.class);
        startActivity(returnn);
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