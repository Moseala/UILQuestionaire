package uilquestionaire;
/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class LinkList {


   private Node first;

   /**
    * Creates a new LinkList with empty node.
    */
   public LinkList(){
       first = new Node(null, null, null);
   }

   /**
    * Creates a LinkList with other being its first item.
    * @param other First item.
    */
   public LinkList(Object other){
       first = new Node(null, other, null);
   }

   /**
    * Adds other to the end of LinkList.
    * @param other Object to be added.
    */
   public void add(Object other){
       first.add(other);
       first = first.getFirst();
   }

   /**
    * Adds other to the position stated in the LinkList.
    * @param other Object to be added.
    * @param position Position for other to be added at.
    */
   public void add(Object other, int position){
       first.add(other, position);
       first = first.getFirst();
   }

   /**
    * Deletes other from LinkList.
    * @param other Object to be deleted.
    */
   public void delete(Object other){
       first = first.delete(new Node(other));
   }

   /**
    * Returns item at position in LinkList.
    * @param position Position of item to be returned.
    * @return Object at position position in LinkList.
    */
   public Object getItem(int position){
       if(position > this.size())
           throw new ArrayIndexOutOfBoundsException();
       return first.getItem(position);
   }

   /**
    * Returns first object in LinkList.
    * @return First object.
    */
   public Object getFirst(){
       return first.getItem();
   }

   /**
    * Returns size of LinkList.
    * @return Size of LinkList.
    */
   public int size(){
       if(first.isEmpty()){
           return 0;
       }
       else
            return first.sizeCount(1);
   }

   /**
    * Sets item at position to other.
    * @param other New object to be set.
    * @param position Position to set this item.
    */
   public void setItem(Object other, int position){
       first.setItem(other, position);
   }


   /**
    * Returns string interpretation of all objects in LinkList.
    * @return All objects in LinkList.
    */
   @Override
   public String toString(){
        first = first.getFirst();
        return first.toString();
   }
   

}
