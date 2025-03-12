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
	//static function to calculate MSE given true y and estimated y
	static Float calculateMSE(List<Float> dataTrue, List<Float> dataEstimate)
	{
		Float MSE = 0.0f, sum = 0.0f;
		int num = dataTrue.size();
	   
		for(int i=0; i<num; i++){
			sum += (dataTrue.get(i) - dataEstimate.get(i))*(dataTrue.get(i) - dataEstimate.get(i));
		}
		MSE = sum/num;
		
		return MSE;
	}

	//static function to calculate MAE given true y and estimated y
	static Float calculateMAE(List<Float> dataTrue, List<Float> dataEstimate)
	{
		Float MAE = 0.0f, sum = 0.0f;
		int num = dataTrue.size();

		for(int i=0; i<num; i++){
			sum += abs(dataTrue.get(i) - dataEstimate.get(i));
		}
		MAE = sum/num;

		return MAE;
	}

	//static funciton to calculate MARE given true y and estimated y
	static Float calculateMARE(List<Float> dataTrue, List<Float> dataEstimate)
	{
		Float MARE = 0.0f, sum = 0.0f, temp = 0.0f;
		int num = dataTrue.size();
	   
		for(int i=0; i<num; i++){
			//temp = ( abs(y-y^) ) / ( abs(y) + Math.ulp(1.0f) )
			//sum += temp * 100
			temp = (abs(dataTrue.get(i) - dataEstimate.get(i))) / (abs(dataTrue.get(i)) + Math.ulp(1.0f));
			sum += temp*100;
		}
		MARE = sum/num;
		
		return MARE;
	
	}

	//bestModel() should take in the error metrics of the three models, take the average error for all three, and return the min() error
	static int bestModel(Float model1[], Float model2[], Float model3[]){
    	Float ave1 = 0f;
        Float ave2 = 0f;
        Float ave3 = 0f;
        int n = model1.length;
        
        for(int i=0; i<n; i++){
    		ave1 += model1[i];
            ave2 += model2[i];
            ave3 += model3[i];
    	}
        ave1 = ave1/n;
        ave2 = ave2/n;
        ave3 = ave3/n;
        
        int smallest = 1;
        if(ave1>ave2 && ave3>ave2) smallest = 2;
        if(ave2>ave3 && ave1>ave3) smallest = 3;
        
        return smallest;
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

		//save contents of model1.csv into arrays/lists
		List<Float> data_true = new ArrayList<Float>();
    	List<Float> data_predicted = new ArrayList<Float>();
		for (String[] row : allData) { 
			float y_true=Float.parseFloat(row[0]);
			data_true.add(y_true);
			float y_predicted=Float.parseFloat(row[1]);
			data_predicted.add(y_predicted);
		} 

		System.out.println("Model 1:");
		//calculate MSE for first model
		Float MSE1 = calculateMSE(data_true, data_predicted);
		System.out.println("MSE = " + MSE1);

		//calculate MAE for first model
		Float MAE1 = calculateMAE(data_true, data_predicted);
		System.out.println("MAE = " + MAE1);

		//calculate MARE for first model
		Float MARE1 = calculateMARE(data_true, data_predicted);
		System.out.println("MARE = " + MARE1);

		//group error metrics in an array
		Float Model1[] = new Float[3];
		Model1[0] = MSE1;
		Model1[1] = MAE1;
		Model1[2] = MARE1;

		

		//second model.csv file
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

		List<Float> data_true2 = new ArrayList<Float>();
    	List<Float> data_predicted2 = new ArrayList<Float>();
		for (String[] row : allData2) { 
			float y_true=Float.parseFloat(row[0]);
			data_true2.add(y_true);
			float y_predicted=Float.parseFloat(row[1]);
			data_predicted2.add(y_predicted);
		} 

		System.out.println("\nModel 2: ");
		//calculate MSE for second model
		Float MSE2 = calculateMSE(data_true2, data_predicted2);
		System.out.println("MSE = " + MSE2);
		
		//calculate MAE for second model
		Float MAE2 = calculateMAE(data_true2, data_predicted2);
		System.out.println("MAE = " + MAE2);
		
		//calculate MARE for second model
		Float MARE2 = calculateMARE(data_true2, data_predicted2);
		System.out.println("MARE = " + MARE2);

		//group error metrics in an array
		Float Model2[] = new Float[3];
		Model2[0] = MSE2;
		Model2[1] = MAE2;
		Model2[2] = MARE2;




		//third model.csv file
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
		
		List<Float> data_true3 = new ArrayList<Float>();
		List<Float> data_predicted3 = new ArrayList<Float>();
		for (String[] row : allData3) { 
			float y_true=Float.parseFloat(row[0]);
			data_true3.add(y_true);
			float y_predicted=Float.parseFloat(row[1]);
			data_predicted3.add(y_predicted);
		} 

		System.out.println("\nModel 3: ");
		//calculate MSE for third model
		Float MSE3 = calculateMSE(data_true3, data_predicted3);
		System.out.println("MSE = " + MSE3);
				
		//calculate MAE for third model
		Float MAE3 = calculateMAE(data_true3, data_predicted3);
		System.out.println("MAE = " + MAE3);
				
		//calculate MARE for third model
		Float MARE3 = calculateMARE(data_true3, data_predicted3);
		System.out.println("MARE = " + MARE3);

		//group error metrics in an array
		Float Model3[] = new Float[3];
		Model3[0] = MSE3;
		Model3[1] = MAE3;
		Model3[2] = MARE3;
		

		System.out.println("\nModel " + bestModel(Model1, Model2, Model3) + " has the lowest error overall and is therefore recommended.");
    }
}
