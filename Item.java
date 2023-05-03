package problema_da_mochila;

public class Item {
	
	 	protected char name;
	    protected int weight;
	    protected int value;
	  
	    public Item(char _name, int _weight, int _value) {
	    	setName(_name);
	        setWeight(_weight);
	        setValue(_value);    
	    }
	    
	    public void setName(char _name) {name = _name;}
	    public void setWeight(int _weight) {weight = _weight;}
	    public void setValue(int _value) {value = _value;}
	    
	    public char getName() {return name;}
	    public int getWeight() {return weight;}
	    public int getValue() {return value;}
	  
}
