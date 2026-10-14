package category.finset;

import java.util.List;

/**
 *
 * @author Damian Arellanes
 */
public interface FBuilder {
  
  public boolean isTotal();
  public boolean hasValueAt(int x);
  public List<Integer> fiberAt(int x);
  public boolean isFinalised();
  public int at(int x);
  public void set(int x, int y);
  public void finalise();  
  public Function get();
}
