package uilquestionaire;
/**
 * This Class is used in conjunction with LinkList and Question to enable the storage of a question's index in a RandomAccessFile. This enables one to rewrite over a question if changes are needed.
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class PlacementWrapper{

    private int placement;
    private Question question;

    /**
     * Creates a PlacementWrapper with pl as position in RandomAccessFile and q as the Question there.
     * @param pl Position in RandomAccessFile
     * @param q Question at pl.
     */
    public PlacementWrapper(int pl, Question q){
        placement = pl;
        question = q;
    }

    /**
     * Returns position in RandomAccessFile
     * @return placement.
     */
    public int getPlace(){
        return placement;
    }

    /**
     * Returns Question at placement.
     * @return Question.
     */
    public Question getQuestion(){
        return question;
    }

}