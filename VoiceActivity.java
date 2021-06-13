package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceActivity extends Fragment
{
    int voiceCode = 1;
    DataBaseHelper db;
    EditText VoiceTXT;
    ListView productsListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View myview = inflater.inflate(R.layout.activity_voice, container, false);
        VoiceTXT = (EditText)myview.findViewById(R.id.VoiceTXT);
        Button VoiceIconBTN = (Button)myview.findViewById(R.id.VoiceIconBTN);
        productsListView = (ListView)myview.findViewById(R.id.productsListView);
        db = new DataBaseHelper(getContext());

        VoiceIconBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent , voiceCode);
            }
        });
        return myview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == voiceCode && resultCode == getActivity().RESULT_OK)
        {
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            VoiceTXT.setText(text.get(0));
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1);
            final ArrayList<Integer> cat_id = new ArrayList<>();
            final ArrayList<Integer>ID = new ArrayList<>();
            productsListView.setAdapter(adapter);

            Cursor cursor = db.FindProduct_ByName(VoiceTXT.getText().toString());
            if (!cursor.isAfterLast())
            {
                while (!cursor.isAfterLast())
                {
                    ID.add(cursor.getInt(0));
                    adapter.add(cursor.getString(1));
                    cat_id.add(cursor.getInt(4));
                    cursor.moveToNext();
                }
            }

            productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

        }
    }
}