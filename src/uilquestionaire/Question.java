package uilquestionaire;

/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class Question {
    
    private String question;
    private String correctAnswer;
    private String testYear;
    private String[] answers = new String[5];
    private String topic;

    public static final int TOPIC = 50;  //Max number of characters for each parameter, including a summation for the entire Question
    public static final int TESTYEAR = 8;
    public static final int QUESTION = 1000;
    public static final int ANSWERS = 500;
    public static final int CORRECTANSWER = 2;
    public static final int RECORD = TOPIC + TESTYEAR + QUESTION+ ANSWERS + CORRECTANSWER;

    /**
     * Creates question with parameters given.
     * @param questionIn Question of the file.
     * @param ans Array of size 5 of answer choices.
     * @param topicIn Topic of the question.
     * @param testYearIn Year of the question.
     * @param correctAnswerIn Index of the correct answer in ans.
     */
    public Question(String questionIn, String[]ans, String topicIn, String testYearIn, String correctAnswerIn){

        while(questionIn.length()<QUESTION){ // adds ~ to the end of all fields to fill their requirement for the record as stated.
            questionIn+="~";
        }

        for(int i=0; i<ans.length; i++){
            if(ans[i].length()>100)
                ans[i] = ans[i].substring(0,100);  //Here I make sure to check the size of the string before adding/attempting to add, this was much easier here than
            while(ans[i].length()<100){
                ans[i]+="~";
            }
        }

        while(topicIn.length()<TOPIC){
            topicIn+="~";
        }

        while(testYearIn.length() <TESTYEAR){
            testYearIn+="~";
        }
        while(correctAnswerIn.length()<CORRECTANSWER){
            correctAnswerIn +="~";
        }
        question = questionIn.substring(0,QUESTION); //checks to make sure that the parameter does not exceed is required length.
        answers = ans;
        topic = topicIn.substring(0,TOPIC);
        testYear = testYearIn.substring(0,TESTYEAR);
        correctAnswer= correctAnswerIn.substring(0,CORRECTANSWER);

    }
    /**
     * Creates a Question by splitting up in by its field lengths in order of: topic, testYear, question, answers, correctAnswer.
     * @param in String of which to be split.
     */
    public Question(String in){
        int startSum=0;
        topic = in.substring(startSum,startSum+TOPIC);
        startSum+=TOPIC;
        testYear= in.substring(startSum,startSum+TESTYEAR);
        startSum+=TESTYEAR;
        question = in.substring(startSum,startSum+QUESTION);
        startSum+=QUESTION;
        String ans = in.substring(startSum,startSum+ANSWERS);
        for(int i =0; i<answers.length; i++){
                answers[i]= ans.substring(i*100,(i*100)+99);
        }
        startSum+=ANSWERS;
        correctAnswer = in.substring(startSum,startSum+CORRECTANSWER);
    }

    /**
     * Sets year to parameter.
     * @param yearIn Year to be set.
     */
    public void setYear(String yearIn){
        this.testYear = yearIn;
    }

    /**
     * Sets Question to parameter.
     * @param questionIn Question to be set.
     */
    public void setQuestion(String questionIn){
        this.question = questionIn;
    }

    /**
     * Sets answers to parameter. This is a shallow copy, only rewriting the location in answers.
     * @param answersIn Answer array to be set to.
     */
    public void setAnswers(String[] answersIn){
        answers = answersIn;

    }

    /**
     * Sets correctAnswer to parameter.
     * @param correctIn New correct index to be set.
     */
    public void setCorrectAnswer(String correctIn){
        correctAnswer = correctIn;
    }

    /**
     * Sets topic to parameter.
     * @param topicIn New topic to be set.
     */
    public void setTopic(String topicIn){
        topic = topicIn;
    }

    /**
     * Returns a filtered Quetion.
     * @return Question without the file spacer ~.
     */
     public String getQuestion(){
        int i=0;
        String questionOut ="";
        while(question.charAt(i)!='~'&&i<question.length()){
            questionOut += question.charAt(i);
            i++;
        }
        return questionOut;
    }

     /**
      * Returns a filtered year.
      * @return Year without the file spacer ~.
      */
     public String getYear(){
         return String.valueOf(testYear.split("~")[0]);
    }

     /**
      * Returns an unfiltered answer array.
      * @return answer Array.
      */
    public String[] getAnswers(){
        return answers;
    }

    /**
     * Returns a filtered correct answer.
     * @return correctAnswer without the file spacer ~.
     */
    public String getCorrectAnswer(){
        return correctAnswer.split("~")[0];
    }

    /**
     * Returns a filtered topic.
     * @return topic without the file spacer ~.
     */
    public String getTopic(){
        return topic.split("~")[0];
    }

    /**
     * Returns all unfiltered fields in string form so that they may be written to the RandomAccessFile
     * @return String containing all unfiltered fields.
     */
    public String toFile(){
        String ansa = "";
        for(int i =0; i<answers.length; i++){
            ansa+= answers[i];
        }
        return topic+testYear+question+ansa+correctAnswer;
    }

    /**
     * Prints filtered topic, question, and answers. Does NOT return the correctAnswer or testYear.
     * @return Filtered fields topic, question and answers in that order.
     */
    @Override
    public String toString(){
        String out =  topic.split("~")[0] + "\n" + question.split("~")[0] + "\n";
        for(int i =0; i<answers.length; i++){
            out += (i+1) + ". " + answers[i].split("~")[0] + "\n";
        }
        return out;
    }

    /**
     * Returns true if all fields of this and other are equal (using string's .equals).
     * @param other Question to compare this to.
     * @return True if all feilds are equal, false otherwise.
     */
    public boolean equals(Question other){
        boolean result = (this.question.equals(other.getQuestion()));
        if(!result){
            return result;
        }
        result = result && (this.topic.equals(other.getTopic()));
        result = result && (this.getCorrectAnswer().equals(other.getCorrectAnswer()));
        result = result && (this.getYear().equals(other.getYear()));
        for(int i =0; i<5; i++){
            result = result && (other.getAnswers()[i].equals(this.getAnswers()[i]));
        }
        return result;
    }


}
