package com.example.loginexercise;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final String condition1 = "put your device in a dark place";
    private final String condition2 = "Shake your device";
    private final String condition3 = "Connect your device to the charger";
    private final String condition4 = "Place the device in airplane mode";
    private final String condition5 = "";

    //for lightSensor:
    private static final float DARK_THRESHOLD = 10.0f;
    //for accelerometerSensor:
    private static final int SHAKE_THRESHOLD = 12; // Adjust this value to suit your needs
    private static final int SHAKE_TIME_INTERVAL = 200; // Time interval to detect multiple shakes (in ms)
    private long lastShakeTime = 0; // To store the last time a shake was detected
    private float lastX = 0, lastY = 0, lastZ = 0; // Previous acceleration values

    private ShapeableImageView condition1_BTN;
    private ShapeableImageView condition2_BTN;
    private ShapeableImageView condition3_BTN;
    private ShapeableImageView condition4_BTN;
    private ShapeableImageView condition5_BTN;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelerometerSensor;

    private BroadcastReceiver deviceStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findviews();
        condition1_BTN.setOnClickListener(v -> showConditionDialog(1));
        condition2_BTN.setOnClickListener(v -> showConditionDialog(2));
        condition3_BTN.setOnClickListener(v -> showConditionDialog(3));
        condition4_BTN.setOnClickListener(v -> showConditionDialog(4));
        condition5_BTN.setOnClickListener(v -> showConditionDialog(5));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setUpSensors();

        deviceStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action){
                    case Intent.ACTION_POWER_CONNECTED:
                        condition3_BTN.setImageResource(R.drawable.done);
                        break;
                    case Intent.ACTION_POWER_DISCONNECTED:
                        condition3_BTN.setImageResource(R.drawable.undone);
                        break;
                    case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                        boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
                        if (isAirplaneModeOn) {
                            condition4_BTN.setImageResource(R.drawable.done);
                        } else {
                            condition4_BTN.setImageResource(R.drawable.undone);                         }
                        break;
                    default:
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensors();
        registerBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensor listeners
        sensorManager.unregisterListener(this);
        // Unregister the receiver
        unregisterReceiver(deviceStateReceiver);
    }

    private void findviews() {
        condition1_BTN = findViewById(R.id.condition1);
        condition2_BTN = findViewById(R.id.condition2);
        condition3_BTN = findViewById(R.id.condition3);
        condition4_BTN = findViewById(R.id.condition4);
        condition5_BTN = findViewById(R.id.condition5);

    }

    private void setUpSensors() {
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerSensors(){
        if(lightSensor != null){
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(accelerometerSensor != null){
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void registerBroadcastReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(deviceStateReceiver, filter);
    }

    private void showConditionDialog(int condition) {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_card, null);

        // Find views in the custom layout
        TextView dialogTitle = customView.findViewById(R.id.dialogTitle);
        TextView dialogMessage = customView.findViewById(R.id.dialogMessage);
        ImageView dialogIcon = customView.findViewById(R.id.dialogIcon);
        //LinearLayout dialogButtonContainer = customView.findViewById(R.id.dialogButtonContainer);

        // Set the dialog titles dynamically
        dialogTitle.setText("Instructions");
        switch (condition){
            case 1:
                dialogMessage.setText(condition1);
                break;
            case 2:
                dialogMessage.setText(condition2);
                break;
            case 3:
                dialogMessage.setText(condition3);
                break;
            case 4:
                dialogMessage.setText(condition4);
                break;
            case 5:
                dialogMessage.setText(condition5);
                break;
            default:
        }

//        // Optional: Add a button dynamically if required
//        if (addExtraButton) {
//            Button extraButton = new Button(this);
//            extraButton.setText("Extra Action");
//            extraButton.setOnClickListener(v -> {
//                // Handle extra button click
//            });
//
//            // Add the extra button to the container
//            dialogButtonContainer.addView(extraButton);
//        }

        // Build and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customView);

        // Create the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handleLightSensorData(SensorEvent event) {
        float lux = event.values[0];
        if (lux <= DARK_THRESHOLD)
            condition1_BTN.setImageResource(R.drawable.done);
    }

    private void handleAccelerometerData(SensorEvent event) {
        // Get the current accelerometer values
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Calculate the change in acceleration
        float deltaX = Math.abs(x - lastX);
        float deltaY = Math.abs(y - lastY);
        float deltaZ = Math.abs(z - lastZ);

        // Check if the change in acceleration exceeds the threshold
        if ((deltaX > SHAKE_THRESHOLD || deltaY > SHAKE_THRESHOLD || deltaZ > SHAKE_THRESHOLD)) {
            long currentTime = System.currentTimeMillis();
            // Only trigger a shake if enough time has passed since the last one
            if (currentTime - lastShakeTime > SHAKE_TIME_INTERVAL) {
                condition2_BTN.setImageResource(R.drawable.done);
                lastShakeTime = currentTime; // Update the last shake time
            }

            // Store the current acceleration values for the next comparison
            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    private void handleActionPowerConnected() {
        condition3_BTN.setImageResource(R.drawable.done);
    }

        @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                handleLightSensorData(event);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                handleAccelerometerData(event);
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}