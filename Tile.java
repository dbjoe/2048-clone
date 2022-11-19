package project3;

public class Tile {
    int value;

    public Tile(){
        value = 2;
    }
    
    public Tile(int value){
        if (!power2(value)){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }
    
    public void setValue(int value){
        if (!power2(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
    
    public boolean power2(double value){
        if (Math.abs(value - 2.0) < 0.0000001 ){//TODO: add more precision?
            return true;
        }
        else if (value < 2.0) {
        	return false;
        }
        else {
            return power2(value / 2.0);
        }
    }
    
    @Override
    public String toString(){//TODO: remove dead code?
    	if(this == null) {
    		return " ";
    	}
    	else {
            return "" + value;
    	}
    }
}
