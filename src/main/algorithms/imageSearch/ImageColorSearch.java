package main.algorithms.imageSearch;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Comparator;

public class ImageColorSearch {

    public ArrayList<BufferedImage> start(ArrayList<BufferedImage> images , BufferedImage target){
        ArrayList<Double> targetColor = getRatio(target);
        ArrayList<ArrayList<Double>> imagesColor = new ArrayList<>();
        for (int i = 0 ; i < images.size() ; i++) {
            ArrayList<Double> templist = getRatio(images.get(i));
            templist.add(new Double(i));
            imagesColor.add(templist);
        }

        imagesColor.sort(
                new Comparator<ArrayList<Double>>() {
                    @Override
                    public int compare(ArrayList<Double> o1, ArrayList<Double> o2) {
                        double total = targetColor.get(0) + targetColor.get(1) + targetColor.get(2);
                        double redRatioTarget = targetColor.get(0) / total;
                        double greenRatioTarget = targetColor.get(1) / total;
                        double blueRatioTarget = targetColor.get(2) / total;

                        total = o1.get(0) + o1.get(1) + o1.get(2);
                        double redRatioO1 = o1.get(0) / total;
                        double greenRatioO1 = o1.get(1) / total;
                        double blueRatioO1 = o1.get(2) / total;

                        total = o2.get(0) + o2.get(1) + o2.get(2);
                        double redRatioO2 = o2.get(0) / total;
                        double greenRatioO2 = o2.get(1) / total;
                        double blueRatioO2 = o2.get(2) / total;

                        double redDifferenceO1 = Math.abs(redRatioTarget - redRatioO1);
                        double greenDifferenceO1 = Math.abs(greenRatioTarget - greenRatioO1);
                        double blueDifferenceO1 = Math.abs(blueRatioTarget - blueRatioO1);

                        double totalDifferenceO1 = redDifferenceO1 + blueDifferenceO1 + greenDifferenceO1;

                        double redDifferenceO2 = Math.abs(redRatioTarget - redRatioO2);
                        double greenDifferenceO2 = Math.abs(greenRatioTarget - greenRatioO2);
                        double blueDifferenceO2 = Math.abs(blueRatioTarget - blueRatioO2);

                        double totalDifferenceO2 = redDifferenceO2 + blueDifferenceO2 + greenDifferenceO2;

                        return Double.compare(totalDifferenceO1 , totalDifferenceO2);
                    }
                }
        );

        ArrayList<BufferedImage> result = new ArrayList<>();
        for (ArrayList<Double> item : imagesColor){
            result.add(images.get( (int) Math.round(item.get(3)) ));
        }

        return result;
    }


    public ArrayList<Double> getRatio(BufferedImage image){
        ArrayList<Double> list = new ArrayList<>();
        double totalRed = 0 , totalBlue = 0 , totalGreen = 0;
        for (int i = 0 ; i < image.getWidth() ; i++){
            for (int j = 0; j < image.getHeight() ; j++){
                int rgb = image.getRGB(i , j);
                Color color = new Color(rgb , true);
                totalRed+=color.getRed();
                totalGreen+=color.getGreen();
                totalBlue+=color.getBlue();
            }
        }
        list.add(totalRed);
        list.add(totalGreen);
        list.add(totalBlue);
        return list;
    }
}