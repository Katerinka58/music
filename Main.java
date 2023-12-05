import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileDownloader {
    public static void main(String[] args) {
        String filePath = "путь_к_текстовому_файлу"; // Укажите путь к текстовому файлу

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String imageLink = reader.readLine();
            String mp3Link = reader.readLine();

            Thread imageThread = new Thread(() -> downloadFile(imageLink, "путь_к_сохранению_изображения")); // Укажите путь для сохранения изображения
            Thread mp3Thread = new Thread(() -> {
                try {
                    downloadFile(mp3Link, "путь_к_сохранению_mp3"); // Укажите путь для сохранения mp3-файла
                    playAudio("путь_к_сохранению_mp3"); // Укажите путь к сохраненному mp3-файлу
                } catch (IOException e) {
                    System.out.println("Ошибка при воспроизведении аудио: " + e.getMessage());
                }
            });

            imageThread.start();
            mp3Thread.start();

            imageThread.join();
            mp3Thread.join();

            System.out.println("Скачивание и воспроизведение завершены");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении текстового файла: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Ошибка при ожидании завершения потоков: " + e.getMessage());
        }
    }

    private static void downloadFile(String fileLink, String savePath) {
        try {
            URL url = new URL(fileLink);
            InputStream in = url.openStream();
            Files.copy(in, Path.of(savePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл " + fileLink + " успешно скачан");
        } catch (IOException e) {
            System.out.println("Ошибка при скачивании файла " + fileLink + ": " + e.getMessage());
        }
    }

    private static void playAudio(String filePath) throws IOException {
        // Код для воспроизведения аудио
    }
}