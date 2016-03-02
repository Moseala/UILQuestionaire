package uilquestionaire;
import java.io.*;
import java.util.*;

/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class UI {


    private File topics = new File(System.getProperty("user.dir")+"/src/uilquestionaire/Topics.dat");
    public final File RAF = new File(System.getProperty("user.dir")+"/src/uilquestionaire/RandomAccesFile.dat");
    private static Scanner in = new Scanner(System.in);

    final int bytesTestYear = 8;
    final int bytesCorrectAnswer = 2;
    final int bytesQuestion = 1000;
    final int bytesAnswers = 500;
    final int bytesTopic = 50;



    public UI(){

    }
    /**
     * Displays the main menu, and accepts user choice for the next method.
     * @throws IOException
     */
    public void displayMenu() throws IOException{
        System.out.println("**Main Menu**\n");
        System.out.println("1.\tCreate Question");
        System.out.println("2.\tEdit Question");
        System.out.println("3.\tTake a Test");
        System.out.println("4.\tExit");
        switch(Integer.parseInt(getRequest())){
            case 1: createQuestion(); displayMenu(); break;
            case 2: editQuestion(); displayMenu(); break;
            case 3: createTest().runTest(); displayMenu(); break;
            case 4: System.exit(0); break;
            default: System.out.println("Invalid argument."); displayMenu();

        }

    }

    /**
     * Requests user input, then returns it.
     * @return Returns the user's keyboard input.
     */
    public static String getRequest(){
        System.out.print("Please enter selection:  ");
        String out = in.nextLine();
        return out;
    }

    /**
     * Prompts user for all fields required by Question, then writes it to RandomAccessFile.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void createQuestion() throws FileNotFoundException, IOException{
        System.out.print("\nEnter Question: ");
        String question = in.nextLine();

        System.out.print("\nEnter Topic: ");
        String topic = in.nextLine();

        System.out.print("\nEnter Test Year: ");
        String testYear = in.nextLine();
        

        String[] ans = new String[5];
        for(int i=0; i<ans.length; i++){
            System.out.print("\nEnter Answer "+ i +": ");
            ans[i] = in.nextLine();
        }

        System.out.print("\nEnter number of the correct answer: ");
        String correctAns = in.nextLine();
        
        Question out = new Question(question, ans, topic, testYear, correctAns);
        RandomAccessFile raf= new RandomAccessFile(RAF,"rw");
        FileHandler handle = new FileHandler(topics, raf);
        handle.writeQuestion(out);
        handle.writeToFile(topic);
        
    }

    /**
     * Prompts user for which Question he would like to edit, asks for field input, then writes the question to RandomAccessFile.
     * @throws FileNotFoundException
     * @throws IOException
     */
public void editQuestion() throws FileNotFoundException, IOException{
    System.out.print("Please enter the topic of the question you are looking for: ");
    String topicS = in.nextLine();
    System.out.print("Please enter the year of the question you are looking for: ");
    String yearS = in.nextLine();

    RandomAccessFile raf= new RandomAccessFile(RAF,"rw");
    FileHandler handle = new FileHandler(topics, raf);

    LinkList results = handle.findQuestions(yearS, topicS);
    System.out.println("Search complete.");
    if(results.size()!=0){
        for(int i=0; i<results.size(); i++){
            PlacementWrapper temp = (PlacementWrapper)results.getItem(i);
            System.out.println(i+ ". "+temp.getQuestion().getQuestion());
        }
        System.out.print("Enter the number of the question you would like to modify: ");
        int req = in.nextInt();
        String dummy = in.nextLine();

        PlacementWrapper inP = (PlacementWrapper)results.getItem(req);
        Question inQ = inP.getQuestion();
        System.out.println("Please enter the new field when prompted, if you do not want to change the field, simply press the enter key.");
        System.out.println(inQ.getQuestion());
        System.out.print("Enter new question: ");
        String questionOut = in.nextLine();
        if(questionOut.equals("")){
            questionOut = inQ.getQuestion();
        }

        System.out.println(inQ.getTopic());
        String original = inQ.getTopic();
        System.out.print("Enter new topic: ");
        String topicOut = in.nextLine();
        if(topicOut.equals("")){
            topicOut = inQ.getTopic();
        }

        System.out.println(inQ.getYear());
        System.out.print("Enter new year: ");
        String yearOut = in.nextLine();
        if(yearOut.equals("")){
            yearOut = inQ.getYear();
        }

        String[] arrayP = inQ.getAnswers();
        for(int i =0; i < arrayP.length; i++){
            System.out.println(i+". "+arrayP[i].split("~")[0]);
            System.out.print("Enter #" +i+ " answer: ");
            String replace = in.nextLine();
            if(!replace.equals(""))
                arrayP[i]=replace;
        }

        System.out.println(inQ.getCorrectAnswer());
        System.out.print("Enter new correct answer: ");
        String correctOut = in.nextLine();
        if(correctOut.equals("")){
            correctOut = String.valueOf(inQ.getCorrectAnswer());
        }


        inQ = new Question(questionOut, arrayP, topicOut, yearOut, correctOut);
        if((results.size()==1) && !original.equals(topicOut))
            handle.writeToFile(topicOut,original);
        handle.writeQuestion(inQ,inP.getPlace());
    }
    else
        System.out.println("Sorry, there are no matches.");
    
}
/**
 * Prompts user for which kind of test he would like to take, and the number of questions, returns the result.
 * @return LinkList test containing questions that match the user's request.
 * @throws IOException
 */
public Test createTest() throws IOException{
    System.out.println("Please select the type of test you would like to take: \n1. By Category\n2. By Year\n3. Random");
    Test out = new Test();
    RandomAccessFile raf =  new RandomAccessFile(RAF,"rw");
    FileHandler manager = new FileHandler(topics,raf);
    switch(Integer.parseInt(in.nextLine())){
        case 1:
            System.out.println("Which category would you like to review?");
            LinkList list = manager.getTopics();
            for(int i = 0; i<list.size(); i++){
                System.out.println( i+ ". " + list.getItem(i));
            }
            int select = in.nextInt();
            String dummy = in.nextLine();
            System.out.print("How many questions would you like? ");
            int num = in.nextInt();
            dummy = in.nextLine();
            try{
            out = new Test((String)list.getItem(select),num); 
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.out.println("You have requested a Topic that is not listed. Restarting method...");
                createTest();
            }
            break;
        case 2:
            System.out.print("Which year would you like to review? ");
            int year = in.nextInt();
            dummy = in.nextLine();
            System.out.print("How many questions would you like? ");
            int num2 = in.nextInt();
            dummy = in.nextLine();
            out = new Test(year,num2); break;
        case 3: System.out.print("How many random questions would you like to review? ");
            int num3 = in.nextInt();
            dummy = in.nextLine();
            out = new Test(num3); break;
        default: System.out.println("Not a valid selection."); createTest();break;
    }
    return out;
}
/**
 * Prints the user's score.
 * @param correct Number of correct answers.
 * @param incorrect Number of incorrect answers.
 */
public static void calcScores(int correct, int incorrect){
    System.out.println("Questions correct: " + correct + "\nQuestions total: " + (correct + incorrect)+ "\nScore: "+correct+ "/"+ (correct+incorrect) + "; " + ((double)correct/(correct+incorrect))*100 + "%\n"
            +((-4*incorrect) + (6*correct)) + " out of a possible " + (incorrect+correct)*6 + " points.");
    System.out.println("Press enter to continue to the Main Menu.");
    String dummy = in.nextLine();
}

}
