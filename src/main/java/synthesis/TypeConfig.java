package synthesis;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 *
 * @author Damian Arellanes
 */
@ConfigurationProperties(prefix = "type")
@Service
public class TypeConfig {

  private Map<Integer, Class<?>> colour = new HashMap<>();

    public Map<Integer, Class<?>> getColour() {
      return colour;
    }

    public void setColours(Map<Integer, Class<?>> colour) {
      this.colour = colour;
    }
}
