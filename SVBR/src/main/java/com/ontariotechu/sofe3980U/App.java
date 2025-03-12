package com.ontariotechu.sofe3980U;


import java.io.FileReader; 
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.*;
import com.opencsv.*;

/**
 * Evaluate Single Variable Continuous Regression
 *
 */
public class App 
{
	static Double calculateBCE(List<Integer> dataTrue, List<Float> dataEstimate){
		double BCE = 0.0;
		double sum = 0.0;
		//save the total number of data points
		double num = dataTrue.size();

		for(int i=0; i<num; i++){
			//sum += y(log(y^)) + (1-y)(log(1-y^))
			sum += (dataTrue.get(i)*(Math.log(dataEstimate.get(i))) + (1-dataTrue.get(i))*(Math.log(1-dataEstimate.get(i))));
		}

		BCE = sum*(1/num)*(-1);

		return BCE;
	}

	static double[] countConfusionMatrix(List<Integer> dataTrue, List<Float> dataEstimate){
		//matrix = {TP, FP, TN, FN}
		double[] matrix = {0,0,0,0};
		int num = dataTrue.size();

		for(int i=0; i<num; i++){
			if(dataEstimate.get(i)>=0.5){//if estimate is greater than 0.5, we predict positive
				if(dataTrue.get(i) == 1){//if true value is 1, this is a true positive
					matrix[0] += 1;
				}else{//true value is 0, this is a false positive
					matrix[1] += 1;
				}
			}else{//estimate is less than 0.5, we predict negative
				if(dataTrue.get(i) == 0){//if true value is 0, this is a true negative
					matrix[2] += 1;
				}else{//true value is 1, this is a false negative
					matrix[3] += 1;
				}
			}
		}

		return matrix;
	}

	static double calculateAccuracy(double[] matrix){
		//matrix = {0=TP, 1=FP, 2=TN, 3=FN}
		return (matrix[0] + matrix[2])/(matrix[0] + matrix[2] + matrix[3] + matrix[1]);
	}

	static double calculatePrecision(double[] matrix){
		//matrix = {0=TP, 1=FP, 2=TN, 3=FN}
		return (matrix[0])/(matrix[0] + matrix[1]);
	}

	static double calculateRecall(double[] matrix){
		//matrix = {0=TP, 1=FP, 2=TN, 3=FN}
		return (matrix[0])/(matrix[0] + matrix[3]);
	}

	static double calculateF1(double precision, double recall){
		return 2*((precision*recall)/(precision+recall));
	}

	static double calculateAUC(List<Integer> dataTrue, List<Float> dataEstimate){

		double[] y = new double[100];
		double[] x = new double[100];
		int num = dataTrue.size();
		double numPositive = 0, numNegative = 0;

		//count n +ve and n -ve
		for(int i=0; i<num; i++){
			if(dataTrue.get(i) == 1) numPositive++;
			else if(dataTrue.get(i)==0) numNegative++;
		}

		//set x and y values for ROC
		for(int i=0; i<100; i++){
			double th = 0.0, TP = 0.0, FP = 0.0;
			//get threshold
			th = i/100.00;
			//using new threshold count true positives and false positives
			for(int j=0; j<num; j++){
				if(dataEstimate.get(j)>= th){
					if(dataTrue.get(j) == 1) TP++;
					else if(dataTrue.get(j)==0) FP++;
				}
			}
			y[i] = TP/numPositive;
			x[i] = FP/numNegative;
		}

		//AUC
		double auc = 0;
		for(int i=1; i<100; i++){
			auc += (y[i-1]+y[i])*abs(x[i-1]-x[i])/2.0;
		}

		return auc;
	}


    public static void main( String[] args )
    {
		String filePath="model_1.csv";
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
		
		//save model1 true and predicted data in separate lists
		List<Integer> data_true = new ArrayList<Integer>();
		List<Float> data_predicted = new ArrayList<Float>();
		for (String[] row : allData) { 
			int y_true=Integer.parseInt(row[0]);
			data_true.add(y_true);
			float y_predicted=Float.parseFloat(row[1]);
			data_predicted.add(y_predicted);
		} 

		//calculate metrics for model 1
		Double BCE1 = calculateBCE(data_true, data_predicted);
		double[] conMatrix = countConfusionMatrix(data_true, data_predicted);
		Double accuracy1 = calculateAccuracy(conMatrix);
		Double precision1 = calculatePrecision(conMatrix);
		Double recall1 = calculateRecall(conMatrix);
		Double F1score1 = calculateF1(precision1, recall1);
		Double AUC1 = calculateAUC(data_true, data_predicted);
		
		System.out.println("\nFOR MODEL 1: \n");
		System.out.println("BCE: " + BCE1);
		System.out.println("Confusion Matrix");
		System.out.println("\t\ty=1\ty=0");
		System.out.println("\ty^=1\t" + conMatrix[0] + "\t" + conMatrix[1]);
		System.out.println("\ty^=0\t" + conMatrix[2] + "\t" + conMatrix[3]);
		System.out.println("Accuracy = " + accuracy1);
		System.out.println("Precision = " + precision1);
		System.out.println("Recall = " + recall1);
		System.out.println("f1 score = " + F1score1);
		System.out.println("auc roc = " + AUC1);
		



		//access model2.csv
		String filePath2="model_2.csv";
		FileReader filereader2;
		List<String[]> allData2;
		try{
			filereader2 = new FileReader(filePath2); 
			CSVReader csvReader2 = new CSVReaderBuilder(filereader2).withSkipLines(1).build(); 
			allData2 = csvReader2.readAll();
		}
		catch(Exception e){
			System.out.println( "Error reading the CSV file" );
			return;
		}

		//save model2 true and predicted data in separate lists
		List<Integer> data_true2 = new ArrayList<Integer>();
		List<Float> data_predicted2 = new ArrayList<Float>();
		for (String[] row : allData2) { 
			int y_true=Integer.parseInt(row[0]);
			data_true2.add(y_true);
			float y_predicted=Float.parseFloat(row[1]);
			data_predicted2.add(y_predicted);
		} 
		
		//calculate metrics for model 2
		Double BCE2 = calculateBCE(data_true2, data_predicted2);
		double[] conMatrix2 = countConfusionMatrix(data_true2, data_predicted2);
		Double accuracy2 = calculateAccuracy(conMatrix2);
		Double precision2 = calculatePrecision(conMatrix2);
		Double recall2 = calculateRecall(conMatrix2);
		Double F1score2 = calculateF1(precision2, recall2);
		Double AUC2 = calculateAUC(data_true2, data_predicted2);
				
		System.out.println("\nFOR MODEL 2: \n");
		System.out.println("BCE: " + BCE2);
		System.out.println("Confusion Matrix");
		System.out.println("\t\ty=1\ty=0");
		System.out.println("\ty^=1\t" + conMatrix2[0] + "\t" + conMatrix2[1]);
		System.out.println("\ty^=0\t" + conMatrix2[2] + "\t" + conMatrix2[3]);
		System.out.println("Accuracy = " + accuracy2);
		System.out.println("Precision = " + precision2);
		System.out.println("Recall = " + recall2);
		System.out.println("f1 score = " + F1score2);
		System.out.println("auc roc = " + AUC2);




		//access model3.csv 
		String filePath3="model_3.csv";
		FileReader filereader3;
		List<String[]> allData3;
		try{
			filereader3 = new FileReader(filePath3); 
			CSVReader csvReader3 = new CSVReaderBuilder(filereader3).withSkipLines(1).build(); 
			allData3 = csvReader3.readAll();
		}
		catch(Exception e){
			System.out.println( "Error reading the CSV file" );
			return;
		}

		//save model3 true and predicted data in separate lists
		List<Integer> data_true3 = new ArrayList<Integer>();
		List<Float> data_predicted3 = new ArrayList<Float>();
		for (String[] row : allData3) { 
			int y_true=Integer.parseInt(row[0]);
			data_true3.add(y_true);
			float y_predicted=Float.parseFloat(row[1]);
			data_predicted3.add(y_predicted);
		} 

		//calculate metrics for model 1
		Double BCE3 = calculateBCE(data_true3, data_predicted3);
		double[] conMatrix3 = countConfusionMatrix(data_true3, data_predicted3);
		Double accuracy3 = calculateAccuracy(conMatrix3);
		Double precision3 = calculatePrecision(conMatrix3);
		Double recall3 = calculateRecall(conMatrix3);
		Double F1score3 = calculateF1(precision3, recall3);
		Double AUC3 = calculateAUC(data_true3, data_predicted3);
		
		System.out.println("\nFOR MODEL 3: \n");
		System.out.println("BCE: " + BCE3);
		System.out.println("Confusion Matrix");
		System.out.println("\t\ty=1\ty=0");
		System.out.println("\ty^=1\t" + conMatrix3[0] + "\t" + conMatrix3[1]);
		System.out.println("\ty^=0\t" + conMatrix3[2] + "\t" + conMatrix3[3]);
		System.out.println("Accuracy = " + accuracy3);
		System.out.println("Precision = " + precision3);
		System.out.println("Recall = " + recall3);
		System.out.println("f1 score = " + F1score3);
		System.out.println("auc roc = " + AUC3);

	}
	
}
