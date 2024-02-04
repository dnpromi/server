import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.dp.cointracker3.API.BlockChainAPI;
import com.dp.cointracker3.DB.AddressDetailsDAL;
import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.Sync;
import com.dp.cointracker3.model.Address;
import com.dp.cointracker3.model.AddressList;
import com.dp.cointracker3.model.UserAddress;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SyncTest {

    private Sync sync;
    private UserAddressDAL userAddressDAL;
    private BlockChainAPI blockChainAPI;
    private AddressDetailsDAL addressDetailsDAL;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userAddressDAL = mock(UserAddressDAL.class);
        blockChainAPI = mock(BlockChainAPI.class);
        addressDetailsDAL = mock(AddressDetailsDAL.class);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        sync = new Sync();
        sync.setUserAddressDAL(userAddressDAL);
        sync.setBlockChainAPI(blockChainAPI);
        sync.setAddressDetailsDAL(addressDetailsDAL);
        sync.setObjectMapper(objectMapper);
    }

    @Test
    public void testSync_Successful() throws Exception {
        // Arrange
        String user = "valid-user";
        List<UserAddress> mockAddresses = new ArrayList<>();
        mockAddresses.add(new UserAddress(){{setAddress("address1");}});
        mockAddresses.add(new UserAddress(){{setAddress("address2");}});
        when(userAddressDAL.getAllAddresses(user)).thenReturn(mockAddresses);

        AddressList mockAddressList = new AddressList();
        List<Address> mockAddressDetails = new ArrayList<>();
        mockAddressDetails.add(new Address(){{setAddress("address1"); setHash160("detail11");}});
        mockAddressDetails.add(new Address(){{setAddress("address2"); setHash160("detail12");}});
        mockAddressList.setAddresses(mockAddressDetails);
        when(blockChainAPI.getAddressList(any(), anyInt())).thenReturn(mockAddressList);

        // Mock behavior of AddressDetailsDAL
        doNothing().when(addressDetailsDAL).upsertAddressDetail(anyString(), anyString());

        // Act
        String result = sync.sync(user);

        // Assert
        assertEquals("SUCCESS", result);
    }

    @Test
    public void testSync_Failed() throws Exception {
        // Arrange
        String user = "valid-user";
        List<UserAddress> mockAddresses = new ArrayList<>();
        mockAddresses.add(new UserAddress(){{setAddress("address1");}});
        mockAddresses.add(new UserAddress(){{setAddress("address2");}});
        when(userAddressDAL.getAllAddresses(user)).thenReturn(mockAddresses);

        AddressList mockAddressList = new AddressList();
        List<Address> mockAddressDetails = new ArrayList<>();
        mockAddressDetails.add(new Address(){{setAddress("address1"); setHash160("detail11");}});
        mockAddressDetails.add(new Address(){{setAddress("address2"); setHash160("detail12");}});
        mockAddressList.setAddresses(mockAddressDetails);
        when(blockChainAPI.getAddressList(any(), anyInt())).thenReturn(mockAddressList);

        // Mock behavior of AddressDetailsDAL to fail for one address
        doNothing().when(addressDetailsDAL).upsertAddressDetail(eq("address1"), anyString());
        doThrow(new RuntimeException()).when(addressDetailsDAL).upsertAddressDetail(eq("address2"), anyString());

        // Act
        String result = sync.sync(user);

        // Assert
        assertEquals("FAILED : \naddress2", result);
    }

    @Test
    public void testSync_NullUser() {
        // Arrange
        String user = null;

        assertThrows(IllegalArgumentException.class, () -> sync.sync(user));
    }

    @Test
    public void testSync_BlankUser() {
        // Arrange
        String user = "";

        assertThrows(IllegalArgumentException.class, () -> sync.sync(user));
    }
}
