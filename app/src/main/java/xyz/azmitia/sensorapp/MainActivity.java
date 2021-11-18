package xyz.azmitia.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textTemperatura;
    private TextView falloText;
    private SensorManager mSensorManager;
    private Sensor mTemperatura;
    private Boolean sensorDisponible;
    private int temperatura = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textTemperatura = findViewById(R.id.temperatura);
        falloText = findViewById(R.id.fallo);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Revisa si existe un sensor en el dispositivo
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            mTemperatura = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorDisponible = true;
            falloText.setVisibility(View.GONE);
        }else{
            falloText.setText("El dispositivo no cuenta con un sensor de temperatura");
            textTemperatura.setVisibility(View.GONE);
            sensorDisponible = false;
        }

        if (sensorDisponible){
            mSensorManager.registerListener(this, mTemperatura, mSensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        temperatura = (int) sensorEvent.values[0];
        textTemperatura.setText(temperatura + " °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}