package ru.example.mygallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Матвей
 * Главный класс - MainActivity, c которого начинается работа приложения
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Объявляем поле списка, для дальнейшего использование в коде программы
     */
    List<Cell> allFilesPaths;
    ArrayList<String> allFilesNames = new ArrayList<>();

    /**
     * Первый метод, с которого начинается выполнение Activity
     * @param savedInstanceState - сохраняет состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Позволяет отобразить макет нашего первого Activity
         */
        setContentView(R.layout.activity_main);
        /**
         * Условная конструкция if/else
         * Проверяем текущий статус разрешения на чтение файлов. Если его нет, то запрашиваем разрешение с помощью диалогового окна.
         * Иначе, пользователь уже подтвердил разрешение на чтение файлов, и приложение отображает изображения.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            showImages();
        }
    }

    /**
     * Метод показа изображений пользователя
     */
    private void showImages() {
        /**
         * Указываем папку со всеми изображениями
         */
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Covers/";
        allFilesPaths = new ArrayList<>();
        allFilesPaths = listAllFiles(path);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        /**
         * Cписок в 3 колонки
         */
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        /**
         * Оптимизация отображения
         */
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        ArrayList<Cell> cells = prepareDate();
        MyAdapter adapter = new MyAdapter(MainActivity.this, cells, allFilesNames);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Подготовка изображений для списка
     * @return список
     */
    private ArrayList<Cell> prepareDate() {
        ArrayList<Cell> allImages = new ArrayList<>();
        for (Cell c : allFilesPaths) {
            Cell cell = new Cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return allImages;
    }
    /**
     * Загружает список файлов из папки
     * @param pathName - имя папки
     * @return список
     */
    private List<Cell> listAllFiles(String pathName) {
        List<Cell> allFiles = new ArrayList<>();
        File file = new File(pathName);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                Cell cell = new Cell();
                cell.setTitle(f.getName());
                allFilesNames.add(f.getName());
                cell.setPath(f.getAbsolutePath());
                allFiles.add(cell);
            }
        }
        return allFiles;
    }

    /**
     * Решения пользователя на чтение файлов
     * Если разрешение на отображение есть, то показываем изображения
     * Иначе, выводится сообщение о том, что разрешение на чтение нет
     * @param requestCode - должен быть таким же, как мы указывали в requestPermissions
     * @param permissions - название разрешений, которые запрашивали
     * @param grantResults - ответы пользователя на запросы разрешений
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImages();
            } else {
                Toast.makeText(this, "Разрешения на чтение нет", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
