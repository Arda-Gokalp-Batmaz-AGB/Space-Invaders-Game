import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Database class which works like a bridge between the game and
 * game data. It holds player's records in the game like highest score
 * killing count, surviving time.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */

public class DataBase {

	// Data fields which are holding the records
	// of the player.
	public static int personalBestScore;
	public static int defaultEnemyKillCount;
	public static int eliteEnemyKillCount;
	public static int bossEnemyKillCount;
	public static int personalBestSurvivingTime;

	/**
	 * Default constructor
	 */
	public DataBase()
	{}

	/**
	 * Reads the records.txt file and initiliazes data fields
	 * with related values.
	 */
	public static void getDataBase()
	{
		// Reads the file
		java.io.File file = new java.io.File("src/data/records.txt");

		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Initializes data fields to use in tables.
		personalBestScore=Integer.parseInt(input.next());
		defaultEnemyKillCount=Integer.parseInt(input.next());
		eliteEnemyKillCount=Integer.parseInt(input.next());
		bossEnemyKillCount=Integer.parseInt(input.next());
		personalBestSurvivingTime=Integer.parseInt(input.next());
		input.close(); // closses scanner

	}
	/**
	 * Writes new datas to records.txt file so this
	 * method updates the records.
	 * @param score Last score of the player.
	 * @param time Last surviving time of the player.
	 */
	public static void setDataBase(int score,int time)
	{
		// If new score is better than personal best.
		// It becomes new personal best.
		if(score>personalBestScore)
		{
			personalBestScore=score;
		}

		// If new surviving time is better than personal best.
		// It becomes new personal best.
		if(time>personalBestSurvivingTime)
		{
			personalBestSurvivingTime=time;
		}

		// Reads the records.txt file
		File file = new File("src/data/records.txt"); 
		if (!file.exists()) { 
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} 

		// Creates a new filewriter to update the data 
		// in the file
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		// Adds new datas to records.txt file
		BufferedWriter bWriter = new BufferedWriter(fileWriter); 
		try {
			bWriter.write(String.valueOf(personalBestScore));
			bWriter.newLine();
			bWriter.write(String.valueOf(defaultEnemyKillCount));
			bWriter.newLine();
			bWriter.write(String.valueOf(eliteEnemyKillCount));
			bWriter.newLine();
			bWriter.write(String.valueOf(bossEnemyKillCount));
			bWriter.newLine();
			bWriter.write(String.valueOf(personalBestSurvivingTime));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try 
		{
			bWriter.close(); // closses the writer
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
