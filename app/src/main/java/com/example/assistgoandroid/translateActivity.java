package com.example.assistgoandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Le Jie Bennett
 * Notes:
 * Need to add the translation feature that actually translates the inputted text
 * Also need to update the lists in Strings.xml depending on the translation API
 * https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes for language codes
 */
public class translateActivity extends AppCompatActivity {
    TextInputEditText translatePhraseInput;
    TextView translatedOutput;
    Button inputLanguageBtn, outputLanguageBtn;
    LinearLayout translateButtonsLayout;

    String[] codedTranslatedLanguageList;
    String[] translatedLanguageList;
    HashMap<String,String> languageToCoded = new HashMap<>();
    String inputLanguageCode = "DetectedLanguage";
    String outputLanguageCode = "English";

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData().getBooleanExtra("isInputLanguage", true) == true) {
                            inputLanguageBtn.setText(result.getData().getStringExtra("language"));

                            if(result.getData().getStringExtra("language")=="Detected Language")
                                ///Use API to figure out language code
                                Log.d("translateActivity", "onActivityResult: detected language");
                            else{
                                inputLanguageCode = languageToCoded.get(result.getData().getStringExtra("language"));
                            }
                        } else {
                            outputLanguageBtn.setText(result.getData().getStringExtra("language"));
                            outputLanguageCode = languageToCoded.get(result.getData().getStringExtra("language"));

                        }
                    }
                }
            });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_page);

        translateButtonsLayout = findViewById(R.id.translateButtonsLayout);
        translatePhraseInput = findViewById(R.id.translatePhraseInput);
        translatedOutput = findViewById(R.id.translatedOutput);
        outputLanguageBtn = findViewById(R.id.outputLanguageBtn);

        //Used to convert to language code
        createLanguageHashmap();

        translatePhraseInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Updates the translation ouput text field
                //Will need to add the actual translation function here
                translatedOutput.setText(editable.toString());

            }
        });


        outputLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(translateActivity.this, chooseLanguageActivity.class);
                intent.putExtra("isInputLanguage", false);
                activityResultLaunch.launch(intent);
            }
        });
        inputLanguageBtn = findViewById(R.id.inputLanguageBtn);
        inputLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(translateActivity.this, chooseLanguageActivity.class);
                intent.putExtra("isInputLanguage", true);
                activityResultLaunch.launch(intent);
            }
        });
    }

    private void createLanguageHashmap(){
        translatedLanguageList =  getResources().getStringArray(R.array.translated_language);
        codedTranslatedLanguageList = getResources().getStringArray(R.array.coded_translated_language);
        for (int i = 0; i < translatedLanguageList.length; i++) {
            languageToCoded.put(translatedLanguageList[i], codedTranslatedLanguageList[i]);
        }

    }


}


