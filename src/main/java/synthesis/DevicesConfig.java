package synthesis;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author Damian Arellanes
 */
@ConfigurationProperties(prefix = "device")
public class DevicesConfig {

  private Map<Integer, String> id = new HashMap<>();
  private Map<Integer, String> endpoint = new HashMap<>();

    public Map<Integer, String> getId() {
      return id;
    }

    public void setId(Map<Integer, String> id) {
      this.id = id;
    }
    
    public Map<Integer, String> getEndpoint() {
      return endpoint;
    }

    public void setEndpoint(Map<Integer, String> endpoint) {
      this.endpoint = endpoint;
    }
}
