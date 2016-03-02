package uilquestionaire;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class FileHandler {

    File topics;
    RandomAccessFile questions;
    int questionNumber;
    Scanner tFile;
    public final File RAF = new File(System.getProperty("user.dir")+"/src/uilquestionaire/RandomAccesFile.dat");

    /**
     * Precondition: N/A
     * Postcondition: Creates FileHandler object with the specified parameters.
     * @param top Sequential file containing topics.
     * @param ques Random Access file containing questions.
     */
    public FileHandler(File top, RandomAccessFile ques){
        topics = top;
        questions = ques;


    }

    /**
     * Precondition: File != null, in !=null.
     * Postcondition: in is written to the Sequential file of topics.
     * Usage: Scans through the file to find any duplicates between it and in, then writes in if there are none.
     * @param in String to be written.
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void writeToFile(String in)throws IOException, FileNotFoundException{
        Scanner fileS = new Scanner(topics);
        ArrayList<String> before= new ArrayList();
        while(fileS.hasNext()){
            before.add(fileS.nextLine());
        }
        fileS.close();
        if(!before.contains(in))
            before.add(in);
        BufferedWriter fileW = new BufferedWriter(new FileWriter(topics));
        for(int i =0; i<before.size(); i ++){
            fileW.write(before.get(i));
            fileW.newLine();
        }
        fileW.close();
    }
    
    public void writeToFile(String in, String original)throws IOException, FileNotFoundException{
        Scanner fileS = new Scanner(topics);
        ArrayList<String> before= new ArrayList();
        while(fileS.hasNext()){
            String lineIn = fileS.nextLine();
            if(!lineIn.equals(original))
                before.add(lineIn);
        }
        fileS.close();
        if(!before.contains(in))
            before.add(in);
        BufferedWriter fileW = new BufferedWriter(new FileWriter(topics));
        for(int i =0; i<before.size(); i ++){
            fileW.write(before.get(i));
            fileW.newLine();
        }
        fileW.close();
    }


    /**
     * Precondition: File !=null
     * Postcondition: Return topics.
     * @return Returns a LinkList containing all topics in File
     * @throws IOException
     * @throws FileNotFoundException
     */
    public LinkList getTopics()throws IOException, FileNotFoundException{
        Scanner fileS=new Scanner(topics);
        LinkList out = new LinkList();
        while(fileS.hasNext()){
            out.add(fileS.nextLine());
        }
        return out;
    }

    /**
     * Precondition: RandomAccessFile != null
     * Postcondition: Returns questions based on questionYear and topic
     * @param questionYear Year by which to compare.
     * @param topic Topic by which to compare.
     * @return Returns LinkList containing all Questions in RandomAccesFile that have either the year or topic as requested.
     * @throws IOException
     * @throws FileNotFoundException
     */
    public LinkList findQuestions(String questionYear, String topic) throws IOException, FileNotFoundException{
        LinkList questionOut = new LinkList();
        long recordNumber=0;
        questions.seek(0);
        while(questions.length()>questions.getFilePointer()){
            String[] topicR = getField(Question.TOPIC,questions.getFilePointer()).split("~"); //Separates the field from the exit character ~ inserted to keep fields a certain size.
            String[] yearR = getField(Question.TESTYEAR, questions.getFilePointer()).split("~");
            if(questionYear.equalsIgnoreCase(yearR[0]) || topic.equalsIgnoreCase(topicR[0])){
                String[] questionR= getField(Question.QUESTION,questions.getFilePointer()).split("~");
                String answersR = getField(Question.ANSWERS, questions.getFilePointer());
                String[] correctR = getField(Question.CORRECTANSWER, questions.getFilePointer()).split("~");
                String[] answersA = new String[5];
                for(int i =0; i<answersA.length; i++){
                    String[] temp = (answersR.substring(i*100, (i*100)+99)).split("~");
                    answersA[i] = temp[0];
                }
            questionOut.add(new PlacementWrapper((int)recordNumber,new Question(questionR[0],answersA,topicR[0], yearR[0],correctR[0])));
            }
            questions.seek(++recordNumber*Question.RECORD);
        }
        return questionOut;
    }

    /**
     * Precondition: RandomAccesFile !=null
     * Postcondition: returns LinkList containing the number of questions as specified by number and by topic.
     * @param topic topic by which to match questions in RandomAccessFile
     * @param number number of questions to insert into LinkList
     * @return LinkList containing Questions.
     * @throws IOException
     */
    public LinkList getTopicQuestion(String topic, int number) throws IOException{
        LinkList questionOut = new LinkList();
        long recordNumber=0;
        questions = new RandomAccessFile(RAF, "rw");
        int counter = 0;
            questions.seek(recordNumber*Question.RECORD);
        while(questions.length()>questions.getFilePointer()&& number > counter){
            
            String topicR[] = getField(Question.TOPIC,questions.getFilePointer()).split("~");
            if(topic.equalsIgnoreCase(topicR[0])){
                
                String yearR[] = getField(Question.TESTYEAR, questions.getFilePointer()).split("~");
                String questionR[] = getField(Question.QUESTION,questions.getFilePointer()).split("~");
                String answersR = getField(Question.ANSWERS, questions.getFilePointer());
                String correctR[] = getField(Question.CORRECTANSWER, questions.getFilePointer()).split("~");
                String[] answersA = new String[5];
                for(int i =0; i<answersA.length; i++){
                    String temp[] = answersR.substring(i*100, (i*100)+100).split("~");
                    answersA[i] = temp[0];
                }
               
            questionOut.add(new Question(questionR[0],answersA,topicR[0], yearR[0], correctR[0]));
            counter++;
            }
            questions.seek(++recordNumber*Question.RECORD);
        }
        if(questionOut.size()<number){
            System.out.println("Sorry, but I was only able to find " + questionOut.size()+ " question{s} that fit your criteria.");
        }
        return questionOut;
    }

    public void writeQuestion(Question in) throws IOException{
        questions.seek(questions.length());
        questions.writeBytes(in.toFile());
    }

    /**
     * Precondition: RandomAccessFile!=null, position>=0, in != null.
     * Postcondition: in is written to RandomAccessFile.
     * Writes a Question to RandomAccessFile.
     * @param in Question to be written.
     * @param position Index of Question to write over in RandomAccessFile.
     * @throws IOException
     */
    public void writeQuestion(Question in, int position) throws IOException{
        questions.seek(position * Question.RECORD);
        questions.writeBytes(in.toFile());
    }

    /**
     * Precondition: RandomAccessFile!=null, number>0.
     * @param year Year of the questions to be looked for.
     * @param number Number of questions in the LinkList.
     * @return LinkList containing number Questions that have year as their Year attribute.
     * @throws IOException
     */
     public LinkList getYearQuestion(String year, int number) throws IOException{
        LinkList questionOut = new LinkList();
        long recordNumber=0;
        int counter=0;
        questions.seek(recordNumber*Question.RECORD);
        while(questions.length()>questions.getFilePointer()&& number > counter){
            
            String[] yearR = getField(Question.TESTYEAR,questions.getFilePointer()+Question.TOPIC).split("~");

            if(year.equalsIgnoreCase(yearR[0])){
                questions.seek(questions.getFilePointer()-Question.TOPIC-Question.TESTYEAR);
                String[] topicR = getField(Question.TOPIC, questions.getFilePointer()).split("~");
                String[] questionR= getField(Question.QUESTION,questions.getFilePointer()).split("~");
                String answersR = getField(Question.ANSWERS, questions.getFilePointer());
                String[] correctR = getField(Question.CORRECTANSWER, questions.getFilePointer()).split("~");
                String[] answersA = new String[5];
                for(int i =0; i<answersA.length; i++){
                    String[] temp = answersR.substring(i*100, (i*100)+100).split("~");
                    answersA[i] =temp[0];
                }
            questionOut.add(new Question(questionR[0],answersA,topicR[0], yearR[0], correctR[0]));
            counter++;
            }
            questions.seek(++recordNumber*Question.RECORD);
        }
        return questionOut;

    }

     /**
      * Precondition: RandomAccessFile != null.
      * @param number Number of questions in LinkList.
      * @return LinkList containing random questions from RandomAccessFile.
      * @throws IOException
      */
    public LinkList getRandom(int number) throws IOException{
        Random rand = new Random();
        LinkList out = new LinkList();
        long[] list = new long[number];
        for(int i =0; i<number; i++){
            list[i] = rand.nextInt(((int)questions.length()/Question.RECORD));
        }
        if(number< (questions.length()/Question.RECORD)){
            for(int k =0; k<number; k++){
                for(int i =0; i<number; i++){
                    if(list[i] == list[k]){
                        list[k] = rand.nextInt(((int)questions.length()/Question.RECORD));
                        
                    }
                    
                }
            }
        }
        for(int i = 0; i<number; i++){
            out.add(new Question(getField(Question.RECORD,(long)list[i]*Question.RECORD)));
        }

        return out;
    }

    /**
     * Usage: Finds a set of data and returns it as byte[].
     * @param fieldLength Number of characters to be read.
     * @param pointerLoc Location of the data to be returned.
     * @return byte[] of data starting at pointerLoc, ending at pointerLoc+fieldLength.
     * @throws IOException
     */
    private String getField(int fieldLength, long pointerLoc) throws IOException{
        String out = "";
        questions.seek(pointerLoc);
        for(int i =0; i<fieldLength; i++){
            out+= (char)questions.readByte();
        }
        return out;
    }

}
