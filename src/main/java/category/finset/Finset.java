package category.finset;

/**
 *
 * @author Damian Arellanes
 */
public class Finset {  
    
  public final int size;
  
  public Finset(int n) {    
    
    if (n < 0) {
      throw new IllegalArgumentException("Cardinality cannot be negative");
    }
    this.size = n;
  }
  
  public boolean isEmpty() {
    return this.size == 0;
  }
  
  @Override
  public String toString() {
    return String.valueOf(size);
  }
}
