package com.example.vijaya.androidhardware;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class StorageActivity extends AppCompatActivity {
    EditText txt_content;
    EditText contenttoDisplay;
    String FILENAME = "MyAppStorage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txt_content = (EditText) findViewById(R.id.id_txt_mycontent);
        contenttoDisplay = (EditText) findViewById(R.id.id_txt_display);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveTofile(View v) throws IOException {
        // ICP Task4: Write the code to save the text
        File file = new File(getApplicationContext().getFilesDir(), "storage.txt");
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(txt_content.getText());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void retrieveFromFile(View v) throws IOException {
        // ICP Task4: Write the code to display the above saved text
        File file = new File(getApplicationContext().getFilesDir(), "storage.txt");
        try (Scanner in = new Scanner(file)){
            String text = in.next();
            contenttoDisplay.setText(text);
            contenttoDisplay.setVisibility(View.VISIBLE);
        }
    }
}
