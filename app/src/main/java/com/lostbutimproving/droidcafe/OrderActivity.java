package com.lostbutimproving.droidcafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class OrderActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        RadioButton radioButton = findViewById(R.id.nextday);
        radioButton.setChecked(true);
        Intent intent = getIntent();
        String message = "Order: " +
                intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.order_textview);
        textView.setText(message);
        Spinner spinner = findViewById(R.id.label_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.sameday:
                if (checked)
                    displayToast(getString(R.string.same_day_messenger_service));
                break;
            case R.id.nextday:
                if (checked)
                    displayToast(getString(R.string.next_day_ground_delivery));
                break;
            case R.id.pickup:
                if (checked)
                    displayToast(getString(R.string.pick_up));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
        displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void processDatePickerResult(int year, int month, int dayOfMonth) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string + "/" + year_string);

        displayToast(getString(R.string.date) + dateMessage);

    }

    public void processTimePickerResult(int hourOfDay, int minute) {
        String hour_string = Integer.toString(hourOfDay);
        String minute_string = Integer.toString(minute);
        String timeMessage = (hour_string +
                ":" + minute_string);

        displayToast(getString(R.string.time) + timeMessage);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.date_picker));
    }

    public void showTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.time_picker));
    }
}