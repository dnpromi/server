import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.dp.cointracker3.AddAddress;
import com.dp.cointracker3.DB.UserAddressDAL;
import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddAddressTest {

    private AddAddress addAddress;
    private UserAddressDAL userAddressDAL;

    @BeforeEach
    void setUp() {
        userAddressDAL = mock(UserAddressDAL.class);
        addAddress = new AddAddress();
        addAddress.setUserAddressDAL(userAddressDAL);
    }

    @Test
    void testAddAddress_Success() {
        String user = "john";
        String address = "address123";
        String nickname = "John's Address";

        when(userAddressDAL.addAddress(user, nickname, address)).thenReturn("Address added successfully.");

        String response = addAddress.addAddress(user, address, nickname);

        assertEquals("Address added successfully.", response);
    }

    @Test
    void testAddAddress_MissingUser() {
        String user = null;
        String address = "address123";
        String nickname = "John's Address";

        assertThrows(InvalidRequestStateException.class, () -> {
            addAddress.addAddress(user, address, nickname);
        });
    }

    @Test
    void testAddAddress_MissingAddress() {
        String user = "john";
        String address = null;
        String nickname = "John's Address";

        assertThrows(InvalidRequestStateException.class, () -> {
            addAddress.addAddress(user, address, nickname);
        });
    }

    @Test
    void testAddAddress_EmptyNickname() {
        String user = "john";
        String address = "address123";
        String nickname = "";

        when(userAddressDAL.addAddress(user, "", address)).thenReturn("Address added successfully.");

        String response = addAddress.addAddress(user, address, nickname);

        assertEquals("Address added successfully.", response);
    }
}
