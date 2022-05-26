package ru.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigImageActivity extends AppCompatActivity {
    Bundle arguments;

    String[] allFilesNamesM;
    String path;

    TextView groupName;
    TextView nameOfCover;

    ImageView imageView ;
    ImageView imageBackGround;

    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        groupName = findViewById(R.id.groupName);
        nameOfCover = findViewById(R.id.nameOfCover);

        imageView = findViewById(R.id.imageView);
        imageBackGround = findViewById(R.id.imageBackGround);

        arguments = getIntent().getExtras();

        String coverName = arguments.getString("coverName");
        ArrayList<String> allFilesNames = arguments.getStringArrayList("allFilesNames");

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Covers/";

        allFilesNamesM = new String[allFilesNames.size()];

        int count = 0;
        for (String name: allFilesNames) {
            allFilesNamesM[count] = name;
            if (coverName.equals(name)){
                number = count;
            }
            count++;
        }

        init();
    }

    public void onClickForward(View view){

        if(number + 1 == allFilesNamesM.length){
            number = 0;

        }
        else {
            number++;
        }
        init();
    }

    public void onClickBack(View view){

        if(number - 1 < 0){
            number = allFilesNamesM.length-1;
        }
        else {
            number --;
        }
        init();
    }

    private String coverNaveCut(String coverName, boolean isGroup){
        Pattern pattern = Pattern.compile("\\..*");
        Matcher matcher = pattern.matcher(coverName);

        matcher.find();
        String[] groupAndName = coverName.replace(matcher.group(), "").split(" - ");

        if (isGroup){
            return groupAndName[0];
        }
        else {
            return groupAndName[1];
        }
    }

    private void init(){
        Uri uri = Uri.parse(path+allFilesNamesM[number]);

        groupName.setText(coverNaveCut(allFilesNamesM[number], true));
        nameOfCover.setText(coverNaveCut(allFilesNamesM[number], false));

        imageView.setImageURI(uri);
        imageBackGround.setImageURI(uri);
    }
}