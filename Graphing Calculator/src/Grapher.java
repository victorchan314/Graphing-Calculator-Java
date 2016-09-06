import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
//Victor Chan, Anirudh Makineni, Kashyap Gummaraju

class Graph extends JComponent {
	
	final int SIZE = 4;//the size for the main graph height
	double lowerxbound = -10.0;//default bounds
	double upperxbound = 10.0;
	double lowerybound = -10.0;
	double upperybound = 10.0;
	double xaxisunits = 0.0;
	double yaxisunits = 0.0;
	int afterExtrema = 0;//used to make the color better in the graph
	Color purple = new Color(128,0,128);//because purple isn't a standard color :(
	
	double x = 0.0;//used for x coordinates
	
	public void paint(Graphics g){//the method that draws the graphs
		g.clearRect(0,0,500,500);//resets the canvas to avoid having excess graphs
		xaxisunits = 500 / (upperxbound - lowerxbound);
		yaxisunits = 500 / (upperybound - lowerybound);
		g.fillRect(0,500+(int) (Math.round(lowerybound*yaxisunits)),500,3);//x-axis
		g.fillRect(500-(int) (Math.round(upperxbound*xaxisunits)),0,3,500);//y-axis
		double abscissa = 0.0;//the next few variables are used
		double ordinate = 0.0;//to save the output values for derivatives
		double fd = 0.0;
		double fd1 = 0.0;
		double sd = 0.0;
		int size = SIZE;
		for (x=lowerxbound;x<upperxbound;x+=(upperxbound-lowerxbound)/50000){
			//goes through the x-values within the bounds
			double abscissa1 = abscissa;
			double ordinate1 = ordinate;
			abscissa = x;
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			//Place your equation here
			double y = Math.abs(x)*x;
			//Place your equation here
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			ordinate = y;
			fd1 = fd;
			fd = (ordinate-ordinate1)/(abscissa-abscissa1);//first derivative
			sd = (fd-fd1)/(abscissa-abscissa1);//second derivative
			if (Math.abs(fd)<0.001 || Math.abs(sd)<0.001){//checks for critical points
				g.setColor(purple);
				size = SIZE + 2;
				g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits))-2,500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(ordinate*yaxisunits))-2,size,size);
				size = SIZE;
				afterExtrema = 400;
			}
			else {
			//afterExtrema is used because if the graph doesn't keep coloring
			//purple after it draws the rectangle for the critical point the purple
			//is covered by red so we need to draw with purple for a while
				if (afterExtrema!=0){
					afterExtrema--;
					g.setColor(purple);
					g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits)),500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(ordinate*yaxisunits)),size,size);
					g.setColor(Color.green);
					g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits)),500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(fd*yaxisunits)),size-3,size-3);
					g.setColor(Color.blue);
					g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits)),500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(sd*yaxisunits)),size-3,size-3);
				}
				else {//draws the actual graph points
					g.setColor(Color.red);
					g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits)),500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(ordinate*yaxisunits)),size,size);
					g.setColor(Color.green);
					g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits)),500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(fd*yaxisunits)),size-3,size-3);
					g.setColor(Color.blue);
					g.fillRect((int) (Math.round(abscissa*xaxisunits))+500-(int) (Math.round(upperxbound*xaxisunits)),500+(int) (Math.round(lowerybound*yaxisunits))-(int) (Math.round(sd*yaxisunits)),size-3,size-3);
				}
			}			
		}
	}
}

public class Grapher extends JFrame{//the actual interface
	
	public static void main(String args[]){
		JFrame Graph = new JFrame("Graph");
		Graph.setSize(750,540);
		Graph.setLocationRelativeTo(null);
		Graph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Graph.setVisible(true);
		
		JPanel Controls = new JPanel();//where you set bounds, zoom, and move the graph
		Controls.setLayout(null);
		Graph.add(Controls);
		Font font = new Font("Sans Serif",0,20);
		
		final Graph graphWindow = new Graph();//adds the graph to the window
		graphWindow.setBounds(0,0,500,500);
		Controls.add(graphWindow);
		
		final JFrame ErrorFrame = new JFrame();//used to display errors
		//also, in the beginning shows what different colors mean
		JOptionPane.showMessageDialog(ErrorFrame,"Red: Function Graph"+"\n"+"Green: First Derivative"+"\n"+"Blue: Second Derivative"+"\n"+"Purple: Critical Point"+"\n"+"and just in case,"+"\n"+"Black: Axes","Legend",JOptionPane.INFORMATION_MESSAGE);
	    
		JLabel xMin = new JLabel("xMin");//for setting lower x bound
		xMin.setBounds(515,50,50,30);
		xMin.setFont(font);
		Controls.add(xMin);
		
		final JTextArea lowerxboundset = new JTextArea("-10.0");
		lowerxboundset.setLineWrap(true);
		lowerxboundset.setBounds(570,50,150,30);
		lowerxboundset.setFont(font);
		Controls.add(lowerxboundset);
	    
		JLabel xMax = new JLabel("xMax");//for setting upper x bound
		xMax.setBounds(515,100,50,30);
		xMax.setFont(font);
		Controls.add(xMax);
		
		final JTextArea upperxboundset = new JTextArea("10.0");
		upperxboundset.setLineWrap(true);
		upperxboundset.setBounds(570,100,150,30);
		upperxboundset.setFont(font);
		Controls.add(upperxboundset);
	    
		JLabel yMin = new JLabel("yMin");//for setting lower y bound
		yMin.setBounds(515,150,50,30);
		yMin.setFont(font);
		Controls.add(yMin);
		
		final JTextArea loweryboundset = new JTextArea("-10.0");
		loweryboundset.setLineWrap(true);
		loweryboundset.setBounds(570,150,150,30);
		loweryboundset.setFont(font);
		Controls.add(loweryboundset);
	    
		JLabel yMax = new JLabel("yMax");//for setting upper y bound
		yMax.setBounds(515,200,50,30);
		yMax.setFont(font);
		Controls.add(yMax);
		
		final JTextArea upperyboundset = new JTextArea("10.0");
		upperyboundset.setLineWrap(true);
		upperyboundset.setBounds(570,200,150,30);
		upperyboundset.setFont(font);
		Controls.add(upperyboundset);
		
		JButton GraphButton = new JButton("Graph");//updates the graphs with
		GraphButton.setBounds(525,250,100,50);     //new bounds
		GraphButton.setFont(font);
		Controls.add(GraphButton);
	    
	   GraphButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	        	try {
	        		String lxb0 = lowerxboundset.getText();
	        		String uxb0 = upperxboundset.getText();
	        		String lyb0 = loweryboundset.getText();
	        		String uyb0 = upperyboundset.getText();
	        		if (lxb0.replaceAll("\\s+","").equals("")||lyb0.replaceAll("\\s+","").equals("")||uxb0.replaceAll("\\s+","").equals("")||uyb0.replaceAll("\\s+","").equals("")){
	        			throw new NullPointerException();
	        		}
	        		double lxb = Double.parseDouble(lxb0);
	        		double uxb = Double.parseDouble(uxb0);
	        		double lyb = Double.parseDouble(lyb0);
	        		double uyb = Double.parseDouble(uyb0);
	        		if (lxb < uxb && lyb < uyb){
	        			graphWindow.lowerxbound = lxb;
	        			graphWindow.upperxbound = uxb;
	        			graphWindow.lowerybound = lyb;
	        			graphWindow.upperybound = uyb;
	        			graphWindow.repaint();
	        		}
	        		else throw new IOException();
	        	}
	        	catch (NullPointerException e){//if box is empty or has spaces only
	        		JOptionPane.showMessageDialog(ErrorFrame,"Please fill out all boxes","Error",JOptionPane.ERROR_MESSAGE);
	        	}
	        	catch (IllegalArgumentException e){//if boxes don't have numbers
	        		JOptionPane.showMessageDialog(ErrorFrame,"Please fill boxes with numbers","Error",JOptionPane.ERROR_MESSAGE);
	        	}
	        	catch (IOException e){//if xMin > xMax or yMin > yMax
	        		JOptionPane.showMessageDialog(ErrorFrame,"Please make sure xMin < xMax and yMin < yMax","Error",JOptionPane.ERROR_MESSAGE);
	        	}
        	}
		});
		
		JButton ZoomInButton = new JButton("+");//zooms in by 5 times
		ZoomInButton.setBounds(570,315,50,50);
		ZoomInButton.setFont(font);
		Controls.add(ZoomInButton);
		
		JButton ZoomOutButton = new JButton("-");//zooms out by 5 times
		ZoomOutButton.setBounds(630,315,50,50);
		ZoomOutButton.setFont(font);
		Controls.add(ZoomOutButton);
	    
		ZoomInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
       			graphWindow.lowerxbound /= 5;
       			graphWindow.upperxbound /= 5;
       			graphWindow.lowerybound /= 5;
       			graphWindow.upperybound /= 5;
       			graphWindow.repaint();
        		lowerxboundset.setText(Double.toString(graphWindow.lowerxbound));
        		upperxboundset.setText(Double.toString(graphWindow.upperxbound));
        		loweryboundset.setText(Double.toString(graphWindow.lowerybound));
        		upperyboundset.setText(Double.toString(graphWindow.upperybound));
        	}
		});
	    
		ZoomOutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
       			graphWindow.lowerxbound *= 5;
       			graphWindow.upperxbound *= 5;
       			graphWindow.lowerybound *= 5;
       			graphWindow.upperybound *= 5;
       			graphWindow.repaint();
        		lowerxboundset.setText(Double.toString(graphWindow.lowerxbound));
        		upperxboundset.setText(Double.toString(graphWindow.upperxbound));
        		loweryboundset.setText(Double.toString(graphWindow.lowerybound));
        		upperyboundset.setText(Double.toString(graphWindow.upperybound));
        	}
		});
		
		JButton MoveUpButton = new JButton("^");//moves graph up 1/10 of screen
		MoveUpButton.setBounds(600,380,50,50);
		MoveUpButton.setFont(font);
		Controls.add(MoveUpButton);
		
		JButton MoveLeftButton = new JButton("<");//moves graph left 1/10 of screen
		MoveLeftButton.setBounds(530,415,50,50);
		MoveLeftButton.setFont(font);
		Controls.add(MoveLeftButton);
		
		JButton MoveRightButton = new JButton(">");//moves graph right 1/10 of screen
		MoveRightButton.setBounds(670,415,50,50);
		MoveRightButton.setFont(font);
		Controls.add(MoveRightButton);
		
		JButton MoveDownButton = new JButton("V");//moves graph down 1/10 of screen
		MoveDownButton.setBounds(600,440,50,50);
		MoveDownButton.setFont(font);
		Controls.add(MoveDownButton);
	    
		MoveUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
       			graphWindow.lowerybound += (graphWindow.upperybound-graphWindow.lowerybound)/20;
       			graphWindow.upperybound += (graphWindow.upperybound-graphWindow.lowerybound)/20;
       			graphWindow.repaint();
        		lowerxboundset.setText(Double.toString(graphWindow.lowerxbound));
        		upperxboundset.setText(Double.toString(graphWindow.upperxbound));
        		loweryboundset.setText(Double.toString(graphWindow.lowerybound));
        		upperyboundset.setText(Double.toString(graphWindow.upperybound));
        	}
		});
	    
		MoveLeftButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
       			graphWindow.lowerxbound -= (graphWindow.upperxbound-graphWindow.lowerxbound)/20;
       			graphWindow.upperxbound -= (graphWindow.upperxbound-graphWindow.lowerxbound)/20;
       			graphWindow.repaint();
        		lowerxboundset.setText(Double.toString(graphWindow.lowerxbound));
        		upperxboundset.setText(Double.toString(graphWindow.upperxbound));
        		loweryboundset.setText(Double.toString(graphWindow.lowerybound));
        		upperyboundset.setText(Double.toString(graphWindow.upperybound));
        	}
		});
	    
		MoveRightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
       			graphWindow.lowerxbound += (graphWindow.upperxbound-graphWindow.lowerxbound)/20;
       			graphWindow.upperxbound += (graphWindow.upperxbound-graphWindow.lowerxbound)/20;
       			graphWindow.repaint();
        		lowerxboundset.setText(Double.toString(graphWindow.lowerxbound));
        		upperxboundset.setText(Double.toString(graphWindow.upperxbound));
        		loweryboundset.setText(Double.toString(graphWindow.lowerybound));
        		upperyboundset.setText(Double.toString(graphWindow.upperybound));
        	}
		});
	    
		MoveDownButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
       			graphWindow.lowerybound -= (graphWindow.upperybound-graphWindow.lowerybound)/20;
       			graphWindow.upperybound -= (graphWindow.upperybound-graphWindow.lowerybound)/20;
       			graphWindow.repaint();
        		lowerxboundset.setText(Double.toString(graphWindow.lowerxbound));
        		upperxboundset.setText(Double.toString(graphWindow.upperxbound));
        		loweryboundset.setText(Double.toString(graphWindow.lowerybound));
        		upperyboundset.setText(Double.toString(graphWindow.upperybound));
        	}
		});
	}
}
