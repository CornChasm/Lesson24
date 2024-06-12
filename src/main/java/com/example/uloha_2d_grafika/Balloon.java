package com.example.uloha_2d_grafika;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.List;

public class Balloon extends Rectangle {

    public static final List<String> STANDARD_TEXTURES = List.of("pohyb1.png", "pohyb2.png", "pohyb3.png", "pohyb4.png", "pohyb5.png", "pohyb6.png", "pohyb7.png", "pohyb8.png", "pohyb9.png", "pohyb10.png", "pohyb11.png", "pohyb12.png", "pohyb13.png", "pohyb14.png", "pohyb15.png", "pohyb16.png", "pohyb17.png", "pohyb18.png", "pohyb19.png", "pohyb20.png");
    public static final List<String> POP_TEXTURES = List.of("pop1.png", "pop2.png", "pop3.png", "pop4.png", "pop5.png", "pop6.png", "pop7.png", "pop8.png", "pop9.png", "pop10.png", "pop11.png");
    Image[] imagesNormal = new Image[STANDARD_TEXTURES.size()];
    Image[] imagesPop = new Image[POP_TEXTURES.size()];

    public Balloon(int x, int y) {
        super(x, y);
        loadTextures();
    }

    public Balloon(int x, int y, int height, int width) {
        super(x, y, height, width);
        loadTextures();
    }

    private void loadTextures() {
        for (int i = 0; i < STANDARD_TEXTURES.size(); i++) {
            URL imagesNormalURL = getClass().getResource(("/images/") + STANDARD_TEXTURES.get(i));
            imagesNormal[i] = new Image(imagesNormalURL.toString());
        }
        for (int i = 0; i < POP_TEXTURES.size(); i++) {
            URL imagesPopURL = getClass().getResource(("/images/") + POP_TEXTURES.get(i));
            imagesPop[i] = new Image(imagesPopURL.toString());
        }
    }

    public Image[] getImagesNormal() {
        return imagesNormal;
    }

    public Image[] getImagesPop() {
        return imagesPop;
    }
}
