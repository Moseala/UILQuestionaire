package uilquestionaire;

/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class Node {

   private Node head;
   private Node tail;
   private Object item;

   /**
    * Creates a node containing the parameter.
    * @param obj Object to be stored.
    */
    public Node(Object obj){
    item = obj;
   }

    /**
     * Creates a full node, having a head, object and tail.
     * @param h Precieding node.
     * @param obj Object to be stored.
     * @param t Tailing node.
     */
   public Node(Node h, Object obj, Node t){
       head = h;
       item = obj;
       tail = t;
   }

   /**
    * Creates a solid copy of another node.
    * @param other Node to be compied.
    */
   public Node(Node other){
       this.head = other.head;
       this.tail = other.tail;
       this.item = other.item;
   }

   /**
    * Returns tailing node.
    * @return Tailing node.
    */
   public Node getTail(){
       return tail;
   }

   /**
    * Returns precieding node.
    * @return Precieding node.
    */
   public Node getHead(){
       return head;
   }

   /**
    * Returns item, DOES NOT PRINT OR CALL TO STRING.
    * @return item.
    */
   public Object getItem(){
       return item;
   }

   /**
    * Returns item at position requested. DOES NOT CALL toString METHOD.
    * @param position Index of item to be returned.
    * @return Object item at position.
    */
   public Object getItem(int position){
       if(position < 0)
           return -1;
       else
            if(position == 0){
                return this.getItem();
            }
            else
                if(tail==null){
                    return -1;
                }
                else
                    return tail.getItem(position - 1);
   }

   /**
    * Returns this node's tail, if there is none, then it returns the first node in the series.
    * @return Tailing node.
    */
   public Node next(){
       if(tail!= null){
           return this.tail;
       }
       return getFirst();
   }

   /**
    * Sets head to parameter.
    * @param other Node to be the new head.
    */
   public void setHead(Node other){
       head = other;
   }

   /**
    * Sets tail to parameter.
    * @param other
    */
   public void setTail(Node other){
       tail = other;
   }

   /**
    * Sets item to parameter.
    * @param other Item to become the new item.
    */
   public void setItem(Object other){
       item = other;
   }

   /**
    * Sets item at position to other.
    * @param other New item to be changed.
    * @param position Position of item to be changed.
    */
   public void setItem(Object other, int position){
       if(position--!=0){
           tail.setItem(other, position);
       }
       else
           this.setItem(other);
   }

   /**
    * Adds parameter to the end of the series of nodes.
    * @param other Object to become the last node item in the series.
    */
   public void add(Object other){
       if(getItem() == null && getHead() == null && getTail() == null){
           setItem(other);

       }
       else
        if(getTail() == null){
               setTail(new Node(this, other, null));
               if(this == null){
               }
            }
        else
              tail.add(other);
   }

   /**
    * Adds other to position parameter in the series of nodes.
    * @param other Object to be inserted.
    * @param position Position to insert object.
    */
   public void add(Object other, int position){
       if(this!= null)
            this.adda(other, position);
       else{
           this.item = other;
       }

   }

   /**
    * Private, recursive method to be used by add to go through the series and find position to insert object.
    * @param other Object to be inserted.
    * @param position Position for object to be inserted at.
    */
   private void adda(Object other, int position){
       if(position == 0){
           Node replace = new Node(this.head,other,this);

           if(this.head != null){
            head.tail = replace;
           }
           else
               this.head = replace;
       }
       else
           if(tail != null){
               tail.adda(other,position-1);
           }
           else
               System.out.print("Could not add, because position.");
   }

   /**
    * Returns the position of the object requested, using the object's .equals().
    * @param in Object to be found.
    * @return int position of in, if found. -1 otherwise.
    */
   public int getPosition(Object in){
       int i = 0;
       return getPosition(i, new Node(in));
   }

   /**
    * recursive method used by getPosition to find position of the object requested.
    * @param i position storage.
    * @param req Node to be compared.
    * @return Position of req.
    */
   private int getPosition(int i,Node req){
       if(isEmpty()){
           return -1;
       }
       if(req.equals(this)){
           return i;
       }
       if(tail == null){
           return -1;
       }
       return tail.getPosition(++i, req);
   }

   /**
    * Returns true if this node's item is equal to the other node's item. False otherwise.
    * @param other Object to be compared to.
    * @return True if items are equal by their .equals() method, false otherwise.
    */
   private boolean equals(Node other){
       if(this.isEmpty()){
           return false;
       }
       else
            return (this.item).equals((other.item));
   }

   /**
    * Returns size of node series.
    * @param i Counter.
    * @return int; size of series.
    */
   public int sizeCount(int i){
        if(tail == null){
            return i;
        }
        else
            return tail.sizeCount(i+1);
   }

   /**
    * Recursive method that finds parameter in node series, then deletes it.
    * @param other node to be deleted.
    * @return First object in the series.
    */
   public Node delete(Node other){
       if(this.equals(other)){
           /*if the list only has one variable*/
           if(this.head ==null && this.tail == null ){
               this.item = null;
               return new Node(null, null, null);
           }
           else
               /*if you're deleting the first object*/
           if(this.head == null){
               this.tail.head = null;
               return this.tail;
           }
           else{
               /*if you're deleting the last object*/
           if(this.tail == null){
               this.head.tail = null;
               return getFirst();}
           else{
               /*if you're deleting from the middle of the list*/
                this.head.tail = this.tail;
                this.tail.head = this.head;
                return getFirst();
                }
           }
       }
       else
        if(tail == null){
          System.out.println("Item not found, therefore it cannot be deleted.");
          return this.getFirst();
        }
        else
             return tail.delete(other);
   }

   /**
    * Returns first node in the series.
    * @return First node.
    */
   public Node getFirst(){
       if(this.getHead() == null){
           return this;
       }
       else
           return head.getFirst();
   }

   /**
    * Returns item at current position.
    * @return Object in this node.
    */
   public Object getThis(){
       return this.item;
   }

   /**
    * Prints all items in list, recursive method.
    * @return All items in list. (uses their .toString() method)
    */
   public String toPrint(){
       if(item == null){
           return "N/A & not empty";
       }
       if(this.tail!=null)
                return item.toString()+", "+ tail.toPrint();
            else
                return item.toString();
   }

   /**
    * Returns if the list is empty.
    * @return True if list is empty, false if not.
    */
   public boolean isEmpty(){
       return ((this.item == null)&& (this.tail== null) &&(this.head==null));
   }

   /**
    * Prints a more formatted interpretation of the node series.
    * @return String containing all items in the series.
    */
   @Override
   public String toString(){
        if(!isEmpty()){
        return "[ " +
                this.toPrint()+ " ]";
        }
        else
            return "[ The list is empty ]";
    }


}
