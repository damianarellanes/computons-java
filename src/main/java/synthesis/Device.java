package synthesis;

/**
 * @author Damian Arellanes
 * @param <T> is the inputs wrapper type
 * @param <R> is the return type
 */
public interface Device <T extends Parameters, R> {   
  
  public R exec (T inputs);
  public Class<T> getParametersClass ();
  public Class<R> getReturnType ();
}
