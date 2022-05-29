package ru.example.mygallery;

/**
 * @author Матвей
 * Класс необходим для того, чтобы файлы с устройства можно было использовать как объекты класса, для дальнейшего взаимодействия
 */

public class Cell {
    /** Поле имени файла */
    private String title;
    /** Поле пути файла */
    private String path;

    /**
     * Функция получения значения поля имени
     * @return возвращает имя
     */
    public String getTitle() {
        return title;
    }
    /**
     * Процедура определения имени
     * @param title - имя
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Функция получения значения поля пути
     * @return возвращает путь
     */
    public String getPath() {
        return path;
    }
    /**
     * Процедура определения пути
     * @param path - путь
     */
    public void setPath(String path) {
        this.path = path;
    }
}
