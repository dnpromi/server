package API;

import com.dp.cointracker3.API.BlockChainAPI;
import com.dp.cointracker3.model.AddressList;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class BlockChainAPITest {

    BlockChainAPI api;

    @BeforeEach
    public void setup() {
        api = new BlockChainAPI(HttpClients.createDefault());
    }

    @Test
    public void getAddressList_valid_success() throws IOException {
        AddressList list = api.getAddressList(List.of("3E8ociqZa9mZUSwGdSmAEMAoAxBK3FNDcd", "bc1q0sg9rdst255gtldsmcf8rk0764avqy2h2ksqs5"));

        Assertions.assertEquals(2, list.getAddresses().size());
    }
}
