package deco2800.arcade.statistics;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;*/


public class MainWindow  extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainWindow(){
		this.setSize(1280, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);
		
		/*
		 * Left side panel
		 */
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(20, 20,250, 650);
		leftPanel.setLayout(null);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		/*
		 * Top panel
		 */
		ImagePanel topPanel = new ImagePanel(new ImageIcon("header.png").getImage());
		topPanel.setBounds(290, 20,950, 170);
		topPanel.setLayout(null);
		topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		/*
		 *  mid main panel
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(290, 220,950, 450);
		mainPanel.setLayout(null);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		
		
		
		/*
		 * Game Logo
		 */
		JLabel gameLogo = new JLabel();
		ImageIcon gameIcon  = new ImageIcon("game_icon.png");
		gameLogo.setIcon(gameIcon);
		topPanel.add(gameLogo);
		gameLogo.setBounds(70, 50, 100, 100);
		gameLogo.setOpaque(true);
		
		/*
		 * Game Name
		 */
		JLabel gameName = new JLabel("Call Of Duty");
		gameName.setFont(new Font("Arial", 0, 100));
		gameName.setForeground(Color.white);
		topPanel.add(gameName);
		gameName.setBounds(200, 50, 700, 100);
		gameName.setOpaque(false);
		
		/*
		 * user line chart panel
		 */
	/*	CategoryDataset lineDataset = createLineDataset();
		JFreeChart lineChart = createLineChart(lineDataset, "Previous & Current Player","Date","Number of Player");
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		lineChartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.add(lineChartPanel);
		lineChartPanel.setBounds(500, 20,420, 200);
		lineChartPanel.setBackground(Color.red);*/
		
		/*
		 * News feed panel
		 */
		JPanel newsPanel = new JPanel();
		mainPanel.add(newsPanel);
		newsPanel.setBounds(20,20,420, 420);
		newsPanel.setLayout(new GridLayout(0,1,0,5));
		newsPanel.add(new JButton("News 1"));
		newsPanel.add(new JButton("News 2"));
		newsPanel.add(new JButton("News 3"));
		newsPanel.add(new JButton("News 4"));
        newsPanel.add(new JButton("News 5"));
		newsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		/*
		 * Achievements track panel
		 */
		JPanel achievementsPanel = new JPanel();
		mainPanel.add(achievementsPanel);
		achievementsPanel.setBounds(500,240,420, 200);
		achievementsPanel.setLayout(new GridLayout(0,1,2,5));
		achievementsPanel.add(new JButton("Achievement 1"));
		achievementsPanel.add(new JButton("Achievement 2"));
		achievementsPanel.add(new JButton("Achievement 3"));
		achievementsPanel.setBackground(Color.black);
		//achievementsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		achievementsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		
		
		/*
		 * Adding all components to frame
		 */
		this.add(leftPanel);
		this.add(topPanel);
		this.add(mainPanel);
	}
	
/*	private CategoryDataset createLineDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		result.setValue(80, "Number of users", "1");
		result.setValue(80, "Number of users", "2");
		result.setValue(100, "Number of users", "3");
		result.setValue(70, "Number of users", "4");
		result.setValue(80, "Number of users", "5");
		result.setValue(150, "Number of users", "6");
		result.setValue(200, "Number of users", "7");
		result.setValue(175, "Number of users", "8");
		result.setValue(149, "Number of users", "9");
		result.setValue(130, "Number of users", "10");
		result.setValue(50, "Number of users", "11");
		result.setValue(100, "Number of users", "12");
		return result;
	}
	 private JFreeChart createLineChart(CategoryDataset lineDataset, String title,String xName,String yName) {
		    JFreeChart lineChart = ChartFactory.createLineChart(title,          // chart title
		            xName,
		            yName,
		    		lineDataset,                // data
		    		PlotOrientation.VERTICAL,
		            false,                   // include legend
		            true,
		            false);
		    lineChart.setBackgroundPaint(Color.white);
		    lineChart.getTitle().setPaint(Color.pink);
		    CategoryPlot plot = lineChart.getCategoryPlot();
		    plot.setRangeGridlinePaint(Color.blue);
		    return lineChart;
	    }*/
}
