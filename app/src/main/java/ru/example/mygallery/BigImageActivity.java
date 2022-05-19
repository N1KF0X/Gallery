package ru.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        Bundle arguments = getIntent().getExtras();

        String coverName = arguments.getString("coverName");

        Pattern pattern = Pattern.compile("\\..*");

        Matcher matcher = pattern.matcher(coverName);

        matcher.find();
        String[] groupAndName = coverName.replace(matcher.group(), "").split(" - ");

        TextView groupName = findViewById(R.id.groupName);
        TextView nameOfCover = findViewById(R.id.nameOfCover);
        groupName.setText(groupAndName[0]);
        nameOfCover.setText(groupAndName[1]);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Covers/" + coverName;

        ImageView imageView = findViewById(R.id.imageView);
        Uri uri = Uri.parse(path);
        imageView.setImageURI(uri);

        ImageView imageBackGround = findViewById(R.id.imageBackGround);
        imageBackGround.setImageURI(uri);
    }
}