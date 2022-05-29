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

/**
 * @author Никита
 * Этот класс является вторым окном приложения,
 * которое позволяет увидеть изображение в
 * увеличенном формате
 */

public class BigImageActivity extends AppCompatActivity {

    /**
     * Переменная хранащая значения
     * из intent
     */
    Bundle arguments;

    /**
     * Массив из имён всех файлов
     */
    String[] allFilesNamesM;
    /**
     * Переменная хранащая путь к папке с изображениями
     */
    String path;

    /**
     * Переменные текстовых полей
     */
    TextView groupName;
    TextView nameOfCover;

    /**
     * Переменные изображений
     */
    ImageView imageView ;
    ImageView imageBackGround;

    /**
     * Переменная хранащая номер
     * имени изображения в массиве
     */
    int number = 0;

    /**
     * Первый метод, с которого начинается выполнение Activity
     * @param savedInstanceState - сохраняет состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        /**
         * определяем текстовые поля
        */
        groupName = findViewById(R.id.groupName);
        nameOfCover = findViewById(R.id.nameOfCover);

        /**
         * определяем изобращения
         */
        imageView = findViewById(R.id.imageView);
        imageBackGround = findViewById(R.id.imageBackGround);

        /**
         * Получаем значения из intent
         */
        arguments = getIntent().getExtras();

        /**
         * Получаем имя выбранного файла
         */
        String coverName = arguments.getString("coverName");
        /**
         * Получаем именна всех файлов
         */
        ArrayList<String> allFilesNames = arguments.getStringArrayList("allFilesNames");

        /**
         * Получаем путь к папке с изображениями
         */
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Covers/";

        /**
         * Создаём массив с размером
         * равным количеству всех файлов
         */
        allFilesNamesM = new String[allFilesNames.size()];

        /**
         * В переменную хранащую номер
         * имени изображения в массиве
         * заносим номер имнени изображения,
         * которого мы выбрали и переносим
         * имена файлов в массив
         */
        int count = 0;
        for (String name: allFilesNames) {
            allFilesNamesM[count] = name;
            if (coverName.equals(name)){
                number = count;
            }
            count++;
        }

        /**
         * Выводим изображение и текст
         * на экран
         */
        init();
    }

    /**
     * Метод для переключения на
     * следущее изображение
     */
    public void onClickForward(View view){

        /**
         * если текущее изображение
         * является последним, то
         * переход к первому изображению,
         * иначе переход к следующему
         */
        if(number + 1 == allFilesNamesM.length){
            number = 0;

        }
        else {
            number++;
        }
        /**
         * Выводим изображение и текст
         * на экран
         */
        init();
    }

    public void onClickBack(View view){

        /**
         * если текущее изображение
         * является первым, то
         * переход к последнему изображению,
         * иначе переход к предыдущему
         */
        if(number - 1 < 0){
            number = allFilesNamesM.length-1;
        }
        else {
            number --;
        }
        /**
         * Выводим изображение и текст
         * на экран
         */
        init();
    }

    /**
     * Метод для получения названия группы или названия трека из имени файла
     * @param coverName - имя файла
     * @param isGroup - true если нужно получить название группы и false если нужно название трека
     * @return название группы или название трека
     */
    private String coverNaveCut(String coverName, boolean isGroup){

        /**
         * Создаём регуляроное выражение
         * чтобы убрать из имени файла расширение
         */
        Pattern pattern = Pattern.compile("\\..*");
        Matcher matcher = pattern.matcher(coverName);

        /**
         * Находим в имени файла расширение
         */
        matcher.find();
        /**
         * Делим имя файла на название группы и название трека
         * предварительно убрав расширение
         */
        String[] groupAndName = coverName.replace(matcher.group(), "").split(" - ");

        /**
         * если true вернуть название группы
         * если false вернуть название трека
         */
        if (isGroup){
            return groupAndName[0];
        }
        else {
            return groupAndName[1];
        }
    }

    /**
     * Метод для вывода текста и изображения на экран
     */
    private void init(){
        /**
         * Получаем путь к файлу
         */
        Uri uri = Uri.parse(path+allFilesNamesM[number]);

        /**
         * Выводим текст
         */
        groupName.setText(coverNaveCut(allFilesNamesM[number], true));
        nameOfCover.setText(coverNaveCut(allFilesNamesM[number], false));

        /**
         * Выводим иображение
         */
        imageView.setImageURI(uri);
        imageBackGround.setImageURI(uri);
    }
}