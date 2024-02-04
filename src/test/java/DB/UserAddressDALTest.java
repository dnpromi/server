package DB;

import com.dp.cointracker3.DB.UserAddressDAL;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserAddressDALTest {

    UserAddressDAL userAddressDAL;

    @BeforeEach
    public void setup() {
        userAddressDAL = new UserAddressDAL();
    }
    @Test
    public void getAddress_valid_Success() {
        assertNotNull(userAddressDAL.getAddress("address101"));
    }

    @Test
    public void addAddress_valid_success_thenDelete() {
        String user1 = "user1";
        String nickname = "add3";
        String address = "address103";
        String createdAddressId = userAddressDAL.addAddress("user1", "add2", "address102");
        assertNotNull(createdAddressId);
        assertDoesNotThrow(() -> userAddressDAL.deleteAddress(user1, createdAddressId));
    }

    @Test
    public void getAddresses_valid_returnsAll() {
        String user1 = "user1";
        int initialCount = userAddressDAL.getAllAddresses(user1).size();

        String nickname = "add4";
        String address = "address104";
        String createdAddressId = userAddressDAL.addAddress(user1, nickname, address);

        int finalCount = userAddressDAL.getAllAddresses(user1).size();
        assertEquals(1, finalCount - initialCount);

        // cleanup
        userAddressDAL.deleteAddress(user1, createdAddressId);

    }
}
