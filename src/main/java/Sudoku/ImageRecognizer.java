package Sudoku;

import java.io.File;
import net.sourceforge.tess4j.*;

public class ImageRecognizer {

    public static void main(String[] args) {
        File imageFile = new File("C:/Users/hp/Documents/sudoku.png");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("C:/Program Files/Tesseract");
        instance.setTessVariable("tessedit_char_whitelist", "1");
        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}