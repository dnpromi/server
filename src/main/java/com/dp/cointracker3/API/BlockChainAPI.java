package com.dp.cointracker3.API;

import com.dp.cointracker3.model.AddressList;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BlockChainAPI {

    private static final String BLOCKCHAIN_INFO_API_URL = "https://blockchain.info/multiaddr";

    // Create an HttpClient
    private HttpClient httpClient = HttpClients.createDefault();
    public static int MAX_TXNS = 5;

    public BlockChainAPI(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public BlockChainAPI() {
        httpClient = HttpClients.createDefault();
    }

    /**
     * Returns AddressList of upto 5 txn by default
     * @param addresses list of addresses
     * @return AddressDetails of all the proided addresses. If something is missing, it will not be present in the lsit
     * @throws IOException
     */
    public AddressList getAddressList(List<String> addresses) throws IOException {
        return getAddressList(addresses, MAX_TXNS);
    }

    /**
     *
     * @param addresses
     * @param maxTxns - max supported is 10
     * @return
     * @throws IOException
     */
    public AddressList getAddressList(List<String> addresses, int maxTxns) {

        if (maxTxns > MAX_TXNS ) {
            throw new IllegalArgumentException("maxTxns cannnot be more than 10");
        }
        // Join multiple addresses separated by "|"
        String joinedAddresses = String.join("|", addresses);
        joinedAddresses = URLEncoder.encode(joinedAddresses, UTF_8);
        // Build the API URL with the addresses
        String apiUrl = BLOCKCHAIN_INFO_API_URL + "?active=" + joinedAddresses + "&n=" + maxTxns;


        // Create an HTTP GET request
        HttpGet request = new HttpGet(apiUrl);

        try {

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(request);

            // Check if the response status is OK (200)
            if (response.getStatusLine().getStatusCode() == 200) {
                // Read the response content
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder responseContent = new StringBuilder();
                String line;

                while (true) {
                    if (!((line = reader.readLine()) != null)) break;
                    responseContent.append(line);
                }

                //System.out.println(responseContent.toString());
                // Parse the JSON response using Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                JsonNode jsonNode = objectMapper.readTree(responseContent.toString());

                // Create an AddressList object and populate it with addresses and transactions
                AddressList addressList = new AddressList();

                // Assuming you have a Jackson ObjectMapper configured
                // to deserialize the JSON response to your AddressList class
                addressList = objectMapper.treeToValue(jsonNode, AddressList.class);

                return addressList;
            } else {
                throw new RuntimeException("Failed to retrieve data from the API. HTTP status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
