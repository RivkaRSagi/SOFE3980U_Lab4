package com.ontariotechu.sofe3980U;


import java.io.FileReader; 
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import com.opencsv.*;

/**
 * Evaluate Single Variable Continuous Regression
 *
 */
public class App 
{

	static Double calculateCE(List<Integer> dataTrue, ArrayList<float[]> dataEstimate){
		double CE = 0.0;
		double sum = 0.0;
		double num = dataTrue.size();

		//for every data point
		for(int i=0; i<num; i++){
			//use switch statement to increment the sum according to the true y value
			switch (dataTrue.get(i)){//if true y value is 1
				case 1:
				sum += Math.log(dataEstimate.get(i)[0]); //add the log of predicted_1
				break;

				case 2://if true value is 2
				sum += Math.log(dataEstimate.get(i)[1]); //add the log of predicted_2
				break;

				case 3:
				sum += Math.log(dataEstimate.get(i)[2]);
				break;

				case 4:
				sum += Math.log(dataEstimate.get(i)[3]);
				break;

				case 5:
				sum += Math.log(dataEstimate.get(i)[4]);
				break;

			}
		}

		//after summing logs for every data point, multiply the sum by -1/n
		CE = sum*(1/num)*(-1);

		return CE;
	}

	static int[][] confusionMatrix(List<Integer> dataTrue, ArrayList<float[]> dataEstimate){
		
		//save the total number of the data points
		int num = dataTrue.size();
		int[][] matrix= {{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}};
		
		for(int i=0; i<num; i++){
			//find the max from the predicted y values to find the estimate y value
			int max = 0;
			for(int j=0; j<5; j++){
				if((dataEstimate.get(i)[j])>(dataEstimate.get(i)[max]))	max=j;
			}

			switch (dataTrue.get(i)){
				case 1://if true value is 1
				matrix[max][0]++; //increment the first column and the row at 'max' index
				break;

				case 2://if true value is 2
				matrix[max][1]++;
				break;

				case 3:
				matrix[max][2]++;
				break;

				case 4:
				matrix[max][3]++;
				break;

				case 5:
				matrix[max][4]++;
				break;

			}
		}

		return matrix;
	}



    public static void main( String[] args )
    {
		String filePath="model.csv";
		FileReader filereader;
		List<String[]> allData;
		try{
			filereader = new FileReader(filePath); 
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build(); 
			allData = csvReader.readAll();
		}
		catch(Exception e){
			System.out.println( "Error reading the CSV file" );
			return;
		}
		
		//collect data from model.csv and save in arrays/lists
		List<Integer> data_true = new ArrayList<Integer>();
		ArrayList<float[]> data_predicted = new ArrayList<float[]>();

		for (String[] row : allData) { 
			int y_true=Integer.parseInt(row[0]);
			data_true.add(y_true);
			float[] y_predicted=new float[5];
			for(int i=0;i<5;i++){
				y_predicted[i]=Float.parseFloat(row[i+1]);
			}
			data_predicted.add(y_predicted);
		} 

		//calculate CE and confusion matrix
		double ce = calculateCE(data_true, data_predicted);
		int[][] conMatrix = confusionMatrix(data_true, data_predicted);

		//print output
		System.out.println("CE = " + ce);
		System.out.println("Confusion matrix");

		System.out.println("\t\ty=1\ty=2\ty=3\ty=4\ty=5");
		for(int i=0; i<5; i++){
			System.out.print("\ty^=" + (i+1) + "\t");
			for(int j=0; j<5; j++){
				System.out.print(conMatrix[i][j] + "\t");
			}
			System.out.println();
		}




	}
	
}
