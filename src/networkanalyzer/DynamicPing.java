/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkanalyzer;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import static networkanalyzer.Pinger.dateDown;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
/**
 *
 * @author Kaeny
 */
public class DynamicPing
{
static int ping;

    //time series data
    public static TimeSeries series;
    
    //most recent value added
    private int lastValue = 0;
    
   
    
    //chartPanel
    private final ChartPanel chartPanel;
    //construct new dynamic chart application
    
    private JFreeChart chart;
    
    public DynamicPing()
    {
        this.series = new TimeSeries("Ping", Second.class);
        
        final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        chart = createChart(dataset);
        
        
        
        //background color chart
        chart.setBackgroundPaint(Color.WHITE);
        
        chartPanel = new ChartPanel(chart);        
        chartPanel.setSize(504,346);    
       
        
    }
    ChartPanel getChartPanel()
        {       
    return chartPanel;
        }
   
  public JFreeChart createChart(final XYDataset dataset)
  {
      final JFreeChart result = ChartFactory.createTimeSeriesChart(
              "Ping Chart", //title
              "Time", //x-axis
              "Ping", //y-axis
              dataset,
              false,
              false,
              false
      );
      
      final XYPlot plot = result.getXYPlot();
      
      plot.setBackgroundPaint(Color.WHITE);
      plot.setDomainGridlinesVisible(true);
      plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
      plot.setRangeGridlinesVisible(true);
      plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
      
      ValueAxis xaxis = plot.getDomainAxis();
      xaxis.setAutoRange(true);
      
      xaxis.setFixedAutoRange(60000.0);
      xaxis.setVerticalTickLabels(true);
      
      ValueAxis yaxis = plot.getRangeAxis();
      yaxis.setAutoRange(true);
      
      return result;
      
      
  }
  JFreeChart getChart()
       {       
    return chart;
        }
    public static void pingData(int ping) throws IOException{
          
          DynamicPing.series.addOrUpdate(new Second(), ping);
          
          if(NetworkAnalyzer.saveCheck.isSelected() && !NetworkAnalyzer.saveLoc.getText().isEmpty())
          {
              long timeNowMsec = System.currentTimeMillis();
              Date timeNow = new Date(timeNowMsec);
              SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" );
              String time = sdf.format( timeNow );
              try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(NetworkAnalyzer.saveLoc.getText() + ".txt", true))))
             {
             out.println( time + " " + ping );
             } 
          }
        
      }
    
    
    
    
   

   
    
}
