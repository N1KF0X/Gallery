package ru.example.mygallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Матвей
 * Класс отвечает за связь элементов java кода с View-компонентами.
 * Т.е. получая набор java объектов, мы должны подать его на вход в адаптер, который преобразует его уже в набор View-компонентов, которые и использует в дальнейшем RecyclerView.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> allFilesNames;
    private ArrayList<Cell> galleryList;
    private Context context;

    /**
     * Конструктор, который создает объект с заданием всех полей
     * @param context - доступ к функциям ОС Android
     * @param galleryList - список файлов
     * @param allFilesNames - все имена файлов
     */
    public MyAdapter(Activity context, ArrayList<Cell> galleryList, ArrayList<String> allFilesNames) {
        this.context = context;
        this.galleryList = galleryList;
        this.allFilesNames = allFilesNames;
    }

    /**
     * Метод вызывается для создания объекта ViewHolder, в конструктор которого необходимо передать созданный View-компонент, с которым в дальнейшем будут связываться java объекты
     * @param parent -  это родительское представление, которое будет содержать нашу ячейку, которую мы собираемся создать
     * @param viewType - необходим для разных типов ячеек
     * @return объект View
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Метод отвечает за связь java объекта и View - компонентов
     * @param viewHolder объект, для обращения к View - комопнентам
     * @param position - позиция объекта
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageFromPath(galleryList.get(position).getPath(), viewHolder.img);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * По нажатию на изображение, осуществляется переход на следующую Activity
             */
            public void onClick(View v) {
                Intent intent = new Intent(context, BigImageActivity.class);
                intent.putExtra("coverName", galleryList.get(position).getTitle());
                intent.putStringArrayListExtra("allFilesNames", allFilesNames);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Предоставляет прямую ссылку на каждый View-компонент
     * Используется для кэширования View-компонентов и последующего быстрого доступа к ним
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        /**
         * Конструктор, который принимает на вход View и ищет все дочерние компоненты
         * @param view - объект компонентов
         */
        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
        }
    }

    /**
     * Метод сообщает количество элементов в списке
     * @return количество элементов
     */
    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    /**
     * Определяем путь и сам объект изображения
     * Если файл существует, то отображаем его
     * @param path - путь
     * @param image - изображение
     */
    private void setImageFromPath(String path, ImageView image) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = ImageHelper.decodeSampleBitmapFromPath(imgFile.getAbsolutePath(), 200, 200);
            image.setImageBitmap(myBitmap);
        }
    }
}
