package me.combimagnetron.passport.util.file;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public interface File<T> {

    void save(Path path);

    String name();

    T content();

    void content(T content);

    class ImageFile implements File<BufferedImage> {
        private final ImageSource source;
        private final String name;
        private BufferedImage image;
        static File<BufferedImage> image(String name, ImageSource imageSource) {
            return new ImageFile(name, imageSource);
        }

        ImageFile(String name, ImageSource imageSource) {
            this.name = name;
            this.source = imageSource;
            this.image = imageSource.image();
        }

        @Override
        public void save(Path path) {

        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public BufferedImage content() {
            return image;
        }

        @Override
        public void content(BufferedImage content) {
            this.image = content;
        }

        abstract static class ImageSource {
            abstract BufferedImage image();
        }

    }



}
