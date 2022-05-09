import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

public class main{
	public static void main  (String[]args){
		new Window();
	}		
}

class Window implements ActionListener{
	Panel panel;
	JFrame window;
	JButton sortButton;
	JButton resetButton;
	JComboBox list;
	public Window(){

		window = new JFrame("Sorting Visualizer");
		window.setSize(700,490);
		window.setLayout(null);

		panel = new Panel();
		panel.setBounds(10,10,680,400);
		window.add(panel);

		resetButton = new JButton("reset");
		resetButton.addActionListener(this);
		resetButton.setBounds(10,430,80,30); 
		window.add(resetButton);
		
		sortButton = new JButton("start");
		sortButton.addActionListener(this);
		sortButton.setBounds(100,430,80,30);
		window.add(sortButton);

		String sorting_algorithms[] = {
			"bubble sort",
			"quick sort",
		};

		list = new JComboBox(sorting_algorithms);
		list.setBounds(190,430,150,30);
		window.add(list);

		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.fillArray();
	}	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == resetButton){
			panel.fillArray();
		}
		else if(e.getSource() == sortButton){
			SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
				protected Void doInBackground() throws Exception{
					String type_of_sort = list.getSelectedItem().toString();
					System.out.println(type_of_sort);
					panel.sort(type_of_sort);
					return null;
				}
			};
			worker.execute();
		}
	}

}

class Panel extends JPanel implements ActionListener{
	int arr[] = new int [33]; 
	Timer timer = new Timer(100,this);
	int updated_bar  = -1;
	public void fillArray(){
		timer.start();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(100, 400 + 1);
		}
		repaint();
	}
	public Panel(){
		//this.setBackground(Color.white);
		//this.setPreferredSize(new Dimension(400,400));
	}
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		drawBars(g2d);
	}

	public void drawBars(Graphics g){
		int bar_pos = 10;
		int WIDTH=20;
		for(int i = 0 ; i < arr.length ; i++){
			g.setColor(Color.black);
			if(i==updated_bar){
				g.setColor(Color.red);
			}
			g.fillRect(bar_pos,490-arr[i],WIDTH,arr[i]);
			bar_pos+=20;
		}
	}
	
	public int parition(int arr[],int starting_index , int ending_index){
		int pivot = arr[ending_index];
		int i=starting_index-1;
		int j;
		for(j=starting_index;j<=ending_index-1;j++){
			if(arr[j] < pivot){
				i++;
				int temp =  arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
				updated_bar = i;
				repaint();
				try{
							Thread.sleep(100);
				}catch(Exception ex){ 
							ex.printStackTrace() ;
				}
				
			} 
		}
		i++;
		int temp =  arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
		updated_bar = i;
		repaint();
		try{
				Thread.sleep(100);
		}catch(Exception ex){ 
				ex.printStackTrace() ;
		}
		return i;
	}	
	public void quicksort(int arr[],int starting_index , int ending_index){
		
		if(starting_index >= ending_index){
			updated_bar = -1;
			repaint();
			return ;
		}

		int pivot_index = parition(arr,starting_index,ending_index);
		quicksort(arr,starting_index,pivot_index-1);
		quicksort(arr,pivot_index+1,ending_index);
	}
	public void bubblesort(){
		for(int i = 0 ; i < arr.length-1;i++){
			for(int j = 0 ; j < arr.length-1 - i ; j++){
				if(arr[j]>arr[j+1]){
					int temp = arr[j];
					arr[j]=arr[j+1];
					arr[j+1] = temp;
					updated_bar = j+1;
					repaint();
					try{
							Thread.sleep(100);
					}catch(Exception ex){ 
							ex.printStackTrace() ;
					}
					
				}
			}
		}		
		updated_bar = -1;
		repaint();
		System.out.println("done");
	}

	public void sort(String typeofSort){
		if(typeofSort == "bubble sort"){
			bubblesort();
		}
		if(typeofSort == "quick sort"){
			quicksort(arr,0,arr.length-1);
		}
	}
	public void actionPerformed(ActionEvent e){
		repaint();
	}
}

