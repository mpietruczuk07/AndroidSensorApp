package pl.edu.pb.sensorappp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorLight;
    private Sensor sensorPressure;
    private TextView sensorNameTextView;
    private TextView sensorValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        sensorNameTextView = findViewById(R.id.second_sensor_name);
        sensorValueTextView = findViewById(R.id.second_sensor_details);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensorLight == null){
            System.out.println("LIGHT IS NULL");
            //sensorLightNameTextView.setText(sensorLight.getName());
            sensorValueTextView.setText(R.string.missing_sensor);
        }

        if(sensorPressure == null){
            sensorValueTextView.setText(R.string.missing_sensor);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_FASTEST);


//        if(sensorLight != null){
//            sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//
//        if(sensorPressure != null){
//            sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
//        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            System.out.println("LIGHT");
            System.out.println(sensorType);
        }

//        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//            System.out.println("PRESSURE");
//            System.out.println(sensorType);
//        }


//        float currentValue = sensorEvent.values[0];
//
//        switch(sensorType){
//            case Sensor.TYPE_LIGHT:
//                //DO POPRAWY!
//                //String temp = String.format(getResources().getString(R.string.light_sensor_label, currentValue));
//                sensorNameTextView.setText("TYPE_LIGHT");
//                sensorValueTextView.setText(getResources().getString(R.string.sensor_label_value, currentValue));
//                break;
//
//            case Sensor.TYPE_PRESSURE:
//                sensorValueTextView.setText(getResources().getString(R.string.sensor_label_value, currentValue));
//                break;
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
