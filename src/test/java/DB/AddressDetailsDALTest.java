package DB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AddressDetailsDALTest {

    com.dp.cointracker3.DB.AddressDetailsDAL dal;
    @BeforeEach
    public void setup() {
        dal = new com.dp.cointracker3.DB.AddressDetailsDAL();
    }

    @Test
    public void addAddress_valid_success_thenDelete() {
        String address = "exampleaddress" + new Random().nextInt();
        String detail = "exampledetails";
        //upsert
        dal.upsertAddressDetail(address, detail);

        //get
        String responseDetail = dal.getAddressDetail(address);
        assertEquals(detail, responseDetail);
        //delete
        assertDoesNotThrow(() -> dal.deleteAddressDetail(address));
    }

}
