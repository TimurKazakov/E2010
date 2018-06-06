import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Runnable {
    private ArrayList<Integer> inputNumber;
// отрисовка графиков
    public Graph(FileDataToInt fileDataToInt) {
        this.inputNumber = fileDataToInt.integers;
    }

    private void ShowGrap() {

        List<XYChart> charts = new ArrayList<XYChart>();
        XYChart chart = new XYChartBuilder().xAxisTitle("количество элементов").yAxisTitle("Вольтаж").width(800).height(600).build();
//        chart.getStyler().setYAxisMin(Double.valueOf(bigggerNumber()));
//        chart.getStyler().setYAxisMax(Double.valueOf(lesserNumber()));


        double[] d3 = new double[inputNumber.size()];
        for (int i = 0; i <d3.length ; i++) {
            d3[i]=inputNumber.get(i);
        }
try {


    XYSeries series = chart.addSeries("Data", null, d3);
    series.setMarker(SeriesMarkers.NONE);
    charts.add(chart);
}
catch (Exception e){
    JOptionPane.showMessageDialog(null, "Неверный Формат файла");
}
        new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }




    private static double[] getRandomWalk(int numPoints) {

        double[] y = new double[numPoints];
        y[0] = 0;
        for (int i = 1; i < y.length; i++) {
            y[i] = y[i - 1] + Math.random() - .5;
        }
        return y;
    }

    public static void main(String[] args) {

        List<XYChart> charts = new ArrayList<XYChart>();
        XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
        chart.getStyler().setYAxisMin(-10.0);
        chart.getStyler().setYAxisMax(10.0);
        XYSeries series = chart.addSeries("" + 1, null, getRandomWalk(200));
        series.setMarker(SeriesMarkers.NONE);
        charts.add(chart);

        new SwingWrapper<XYChart>(charts).displayChartMatrix();



    }

    @Override
    public void run() {
        ShowGrap();
    }
}


