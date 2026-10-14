package operation;

import java.util.Map;
import java.util.Random;

/**
 *
 * @author Damian Arellanes
 */
public class Config {
    
  public final Map<Integer, Class<?>> types;
  public final Map<Integer, String> devices;
  public final Random random;

  public Config (Map<Integer, Class<?>> types, Map<Integer, String> devices) {
    
    this.types = types;
    this.devices = devices;
    random = new Random();
  }    
}
