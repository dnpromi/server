import static org.mockito.Mockito.*;

import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.GetAddress;
import com.dp.cointracker3.model.UserAddress;
import com.sun.jdi.request.InvalidRequestStateException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetAddressTest {

    private GetAddress getAddress;
    private UserAddressDAL userAddressDAL;

    @BeforeEach
    public void setUp() {
        userAddressDAL = mock(UserAddressDAL.class);
        getAddress = new GetAddress();
        getAddress.setUserAddressDAL(userAddressDAL);
    }

    @Test
    public void getAddresses_ValidAddressId() {
        // Arrange
        String addressId = "valid-address-id";
        UserAddress mockAddress = new UserAddress();
        mockAddress.setAddress("mocked-address");

        // Mock behavior of UserAddressDAL
        when(userAddressDAL.getAddress(addressId)).thenReturn(mockAddress);

        // Act
        String result = getAddress.getAddresses(addressId);

        // Assert
        Assertions.assertEquals("mocked-address", result);
    }

    @Test
    public void getAddresses_InvalidAddressId() {
        String addressId = "invalid-address-id";

        // Mock behavior of UserAddressDAL
        when(userAddressDAL.getAddress(addressId)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class,() -> getAddress.getAddresses(addressId));

    }

    @Test
    public void getAddresses_NullAddressId() {
        String addressId = null;

        Assertions.assertThrows(IllegalArgumentException.class,() -> getAddress.getAddresses(addressId));

    }

    @Test
    public void getAddresses_BlankAddressId() {
        // Arrange
        String addressId = "";

        Assertions.assertThrows(IllegalArgumentException.class,() -> getAddress.getAddresses(addressId));

    }
}
