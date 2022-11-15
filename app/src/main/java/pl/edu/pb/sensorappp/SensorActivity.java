package pl.edu.pb.sensorappp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SensorActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    private final List<Integer> favouredSensors = Arrays.asList(Sensor.TYPE_LIGHT, Sensor.TYPE_ACCELEROMETER);
    public static final int SENSOR_DETAILS_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : sensorList) {
            Log.d("SENSOR_APP_TAG", "Sensor name: " + sensor.getName());
            Log.d("SENSOR_APP_TAG", "Sensor vendor: " + sensor.getVendor());
            Log.d("SENSOR_APP_TAG", "Sensor max range: " + sensor.getMaximumRange());
        }

        if(adapter == null){
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sensors_count_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_sensors_count:
                String string = getString(R.string.sensors_count, sensorList.size());
                getSupportActionBar().setSubtitle(string);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class SensorHolder extends RecyclerView.ViewHolder {
        private ImageView sensorIconImageView;
        private TextView sensorNameTextView;
        private TextView sensorTypeTextView;
        private Sensor sensor;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));

            sensorIconImageView = itemView.findViewById(R.id.sensor_icon);
            sensorNameTextView = itemView.findViewById(R.id.sensor_name);
        }

        public void bind(Sensor sensor){
            this.sensor = sensor;
            sensorNameTextView.setText(sensor.getName());
            View itemContainer = itemView.findViewById(R.id.list_item_sensor);
            View magneticContainer = itemView.findViewById(R.id.list_item_sensor);

            if(favouredSensors.contains(sensor.getType())){
                itemContainer.setBackgroundColor(getResources().getColor(R.color.favour_item_background));
                itemContainer.setOnClickListener(v->{
                    Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
                    intent.putExtra("SENSOR_TYPE_PARAMETER", sensor.getType());
                    startActivityForResult(intent, SENSOR_DETAILS_ACTIVITY_REQUEST_CODE);
                });
            }

//            if(sensor.getType() == Sensor.TYPE_LIGHT){
//                magneticContainer.setBackgroundColor(getResources().getColor(R.color.favour_item_background));
//                magneticContainer.setOnClickListener(v->{
//                    Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
//                    intent.putExtra("SENSOR_TYPE_PARAMETER", sensor.getType());
//                    startActivityForResult(intent, SENSOR_DETAILS_ACTIVITY_REQUEST_CODE);
//                });
//            }
//
//            if(sensor.getType() == Sensor.TYPE_PRESSURE){
//                magneticContainer.setBackgroundColor(getResources().getColor(R.color.favour_item_background));
//                magneticContainer.setOnClickListener(v->{
//                    Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
//                    intent.putExtra("SENSOR_TYPE_PARAMETER", sensor.getType());
//                    startActivityForResult(intent, SENSOR_DETAILS_ACTIVITY_REQUEST_CODE);
//                });
//            }

            if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magneticContainer.setBackgroundColor(getResources().getColor(R.color.purple_200));
                magneticContainer.setOnClickListener(v->{
                Intent intent = new Intent(SensorActivity.this, LocationActivity.class);
                    intent.putExtra("SENSOR_TYPE_PARAMETER", sensor.getType());
                    startActivityForResult(intent, SENSOR_DETAILS_ACTIVITY_REQUEST_CODE);
                });
            }
        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {
        private List<Sensor> sensorList;

        public SensorAdapter(List<Sensor> sensorList) {
            this.sensorList = sensorList;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            return new SensorHolder(inflate, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            Sensor sensor = sensorList.get(position);
            holder.bind(sensor);
        }

        @Override
        public int getItemCount() {
            return sensorList.size();
        }

    }
}