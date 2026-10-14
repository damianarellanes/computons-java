package synthesis;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author Damian Arellanes
 */
@Service
public class DeviceRegistry {

  private final Map<Integer, Device> indexed = new HashMap<>();
  private final Map<Integer, String> deviceEndpoints = new HashMap<>();

  public DeviceRegistry(DevicesConfig config, Map<String, Device> beans) {
    
    config.getId().forEach((deviceIndex, deviceName) -> {

      Device instance = beans.get(deviceName);
      if (instance == null) {
        throw new IllegalArgumentException("No bean for " + deviceName);
      }
      
      indexed.put(deviceIndex, instance);      
      deviceEndpoints.put(deviceIndex, config.getEndpoint().get(deviceIndex));
    });            
  }

  public Device get(int index) {
    return indexed.get(index);
  }
  
  public Map<Integer, String> getEndpoints () {
    return this.deviceEndpoints;
  }
}