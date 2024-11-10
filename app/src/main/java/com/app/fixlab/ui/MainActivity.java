package com.app.fixlab.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.fixlab.R;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Person> clients;
    private List<Person> technicians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * LOAD CLIENTS AND DEVICES:
     */
    private void loadClientsAndDevices() {

    }

    /**
     * LOAD TECHNICIANS:
     */
    private void loadTechnicians() {

    }


}