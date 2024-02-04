import static org.mockito.Mockito.*;

import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.GetAddresses;
import com.dp.cointracker3.model.UserAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetAddressesTest {

    private GetAddresses getAddresses;
    private UserAddressDAL userAddressDAL;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userAddressDAL = mock(UserAddressDAL.class);
        objectMapper = mock(ObjectMapper.class);
        getAddresses = new GetAddresses();
        getAddresses.setUserAddressDAL(userAddressDAL);
    }

    @Test
    public void testGetAddresses_ValidUser() throws JsonProcessingException {
        // Arrange
        String user = "valid-user";
        List<UserAddress> mockAddresses = new ArrayList<>();
        mockAddresses.add(new UserAddress(){{setAddress("address1");}});
        mockAddresses.add(new UserAddress(){{setAddress("address2");}});

        // Mock behavior of UserAddressDAL
        when(userAddressDAL.getAllAddresses(user)).thenReturn(mockAddresses);

        // Mock behavior of ObjectMapper
        String mockJson = "[{\"address\":\"address1\"},{\"address\":\"address2\"}]";
        when(objectMapper.writeValueAsString(mockAddresses)).thenReturn(mockJson);

        String result = getAddresses.getAddresses(user);

        // Assert
        Assertions.assertEquals(mockJson, result);
    }

    @Test
    public void testGetAddresses_NullUser() {
        // Arrange
        String user = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> getAddresses.getAddresses(user));

    }

    @Test
    public void testGetAddresses_BlankUser() {
        // Arrange
        String user = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> getAddresses.getAddresses(user));

    }
}
