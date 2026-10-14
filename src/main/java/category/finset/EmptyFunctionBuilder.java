package category.finset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Damian Arellanes
 */
public class EmptyFunctionBuilder implements FBuilder {
  
  private final Finset codomain;
  
  public EmptyFunctionBuilder(Finset codomain) {    
    this.codomain = codomain;
  }

  @Override
  public boolean isTotal() {
    return true;
  }

  @Override
  public boolean hasValueAt(int x) {
    return false;
  }

  @Override
  public List<Integer> fiberAt(int x) {
    return new ArrayList<>();
  }

  @Override
  public boolean isFinalised() {
    return true;
  }

  @Override
  public int at(int x) {
    throw new UnsupportedOperationException(
      "Empty functions have no behaviour"
    );
  }

  @Override
  public void set(int x, int y) {
    throw new UnsupportedOperationException(
      "Cannot assign a mapping to an empty function"
    ); 
  }

  @Override
  public void finalise() {}

  @Override
  public Function get() {
    return new Function(new int[0], codomain.size, new HashMap<>());
  }  
}
