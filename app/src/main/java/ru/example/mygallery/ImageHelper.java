package ru.example.mygallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Матвей
 * Класс, в котором мы изменяем размеры изображений для корректного отображения
 */
public class ImageHelper {
    /**
     * Метод уменьшает размеры изображения для лучшего представления
     * @return нужный размер
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            while ((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * Метод, используемый для декодирования изображений
     * @param pathname - имя папки
     * @param reqWidth - требуемая ширина
     * @param reqHeight - требуемая высота
     * @return декодированные файлы
     */
    public static Bitmap decodeSampleBitmapFromPath(String pathname, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathname, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathname, options);
    }
}
