import static org.mockito.Mockito.*;

import com.dp.cointracker3.DB.AddressDetailsDAL;
import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.GetAllAddressDetails;
import com.dp.cointracker3.model.UserAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetAllAddressDetailsTest {

    private GetAllAddressDetails getAllAddressDetails;
    private UserAddressDAL userAddressDAL;
    private AddressDetailsDAL addressDetailsDAL;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userAddressDAL = mock(UserAddressDAL.class);
        addressDetailsDAL = mock(AddressDetailsDAL.class);
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        getAllAddressDetails = new GetAllAddressDetails();
        getAllAddressDetails.setUserAddressDAL(userAddressDAL);
        getAllAddressDetails.setAddressDetailsDAL(addressDetailsDAL);
        getAllAddressDetails.setObjectMapper(objectMapper);
    }

    @Test
    public void testGetAllAddressDetails_ValidUser() throws Exception {
        // Arrange
        String user = "valid-user";
        int tnx = 50;

        // Mock behavior of UserAddressDAL
        List<UserAddress> mockAddresses = new ArrayList<>();
        mockAddresses.add(new UserAddress(){{setAddress("address1");}});
        mockAddresses.add(new UserAddress(){{setAddress("address2");}});
        when(userAddressDAL.getAllAddresses(user)).thenReturn(mockAddresses);

        // Mock behavior of AddressDetailsDAL
        when(addressDetailsDAL.getAddressDetail("address1")).thenReturn("detail1");
        when(addressDetailsDAL.getAddressDetail("address2")).thenReturn("detail2");

        // Mock behavior of ObjectMapper
        String expectedJson = "[\"detail1\",\"detail2\"]";

        // Act
        String result = getAllAddressDetails.getAllAddressDetails(user, tnx);

        // Assert
        Assertions.assertEquals(expectedJson, result);
    }

    @Test
    public void testGetAllAddressDetails_NullUser() {
        // Arrange
        String user = null;
        int tnx = 50;

        Assertions.assertThrows(IllegalArgumentException.class , () -> getAllAddressDetails.getAllAddressDetails(user, tnx));

    }

    @Test
    public void testGetAllAddressDetails_BlankUser() {
        // Arrange
        String user = "";
        int tnx = 50;

        Assertions.assertThrows(IllegalArgumentException.class , () -> getAllAddressDetails.getAllAddressDetails(user, tnx));

    }
}
