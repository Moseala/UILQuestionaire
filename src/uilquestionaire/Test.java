package uilquestionaire;
import java.io.*;
import java.util.Scanner;
/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class Test {

    private File topics = new File(System.getProperty("user.dir")+"/src/uilquestionaire/Topics.dat");
    public final File RAF = new File(System.getProperty("user.dir")+"/src/uilquestionaire/RandomAccesFile.dat");
    private RandomAccessFile questionList;
    private LinkList questions;
    private int position;
    private Scanner in = new Scanner(System.in);

    /**
     * Default constructor used to prevent null pointer errors.
     */
    public Test(){
    }
    /**
     * Creates a test with num questions matching cat.
     * @param cat Category to match questions to.
     * @param num Number of questions to be found and stored in LinkList questions.
     * @throws IOException
     */
    public Test(String cat, int num) throws IOException{
        questionList = new RandomAccessFile(RAF,"rw");
        FileHandler fileH = new FileHandler(topics,questionList);
        questions = fileH.getTopicQuestion(cat, num);
    }
/**
 * Creates a test with num questions matching year.
 * @param year Year to match questions to.
 * @param num Number of questions to include in LinkList questions.
 * @throws IOException
 */
    public Test(int year, int num) throws IOException{
        questionList = new RandomAccessFile(RAF,"rw");
        FileHandler fileH = new FileHandler(topics,questionList);
        questions = fileH.getYearQuestion(String.valueOf(year), num);
    }

    /**
     * Inserts num of a random assortment of questions
     * @param num Number of questions to include in LinkList questions
     * @throws IOException
     */
    public Test(int num) throws IOException{
        questionList = new RandomAccessFile(RAF,"rw");
        FileHandler fileH = new FileHandler(topics,questionList);
        questions = fileH.getRandom(num);
    }

    /**
     * Returns question at LinkList position i.
     * @param i Position of question to return.
     * @return Question at i.
     */
    public Question getQuestion(int i){
        return (Question) questions.getItem(i);
    }

    /**
     * Prints out test, accumulates answers and prints score from calcScores.
     */
    public void runTest() throws IOException{
        int corrCounter = 0;
        int inCorrCounter = 0;
        try{
        if(questions.size()!=0){
            for(int i = 0; i<questions.size(); i++){
                Question at = (Question)questions.getItem(i);
                System.out.println(at);
                System.out.print("Please enter your anwer: ");
                int selection = in.nextInt();
                String dummy = in.nextLine();
                if(selection-1 == Integer.parseInt(at.getCorrectAnswer().split("~")[0])){
                    corrCounter++;
                }
                else
                    inCorrCounter++;

            }
            UI.calcScores(corrCounter,inCorrCounter);
       }
        else
            System.out.println("Sorry, but we cannot find any questions matching your search criteria.");
        }
        catch(NullPointerException e){
            System.out.println("An error has occured with creating your test, please be sure to request for questions stored.\nReturning to Main Menu.");
            UI ui = new UI();
            ui.displayMenu();
        }
    }
}
