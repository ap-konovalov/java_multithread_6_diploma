import org.junit.jupiter.api.Test;
import ru.netology.utils.PropertyLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyLoaderTests {

    @Test
    void shouldLoadServerPort() {
        String actualServerPort = PropertyLoader.getProperty("server.port");
        assertEquals("8089", actualServerPort);
    }
}
