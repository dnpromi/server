package com.dp.cointracker3;

import com.dp.cointracker3.API.BlockChainAPI;
import com.dp.cointracker3.DB.AddressDetailsDAL;
import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.model.Address;
import com.dp.cointracker3.model.AddressList;
import com.dp.cointracker3.model.UserAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.Tuple;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/get-all-details")
public class GetAllAddressDetails {

    // Ideally singleton instance is injected
    private UserAddressDAL userAddressDAL = new UserAddressDAL();
    private AddressDetailsDAL addressDetailsDAL = new AddressDetailsDAL();
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @POST
    @Produces("text/plain")
    public String getAllAddressDetails(@QueryParam("user") String user, @QueryParam("n") int tnx) {
        // validation
        if(user == null || user.isBlank()) {
            throw new IllegalArgumentException("Invalid request : user needs to be valid.");
        }

        // first get all addresses
        List<UserAddress> addresses = userAddressDAL.getAllAddresses(user);
        List<String> addresStringList = addresses.stream().map(UserAddress::getAddress).collect(Collectors.toList());

        List<String> failedAddress = new ArrayList<>();
        List<String> result = new ArrayList<>();
        for(String address : addresStringList) {
            result.add(addressDetailsDAL.getAddressDetail(address));
        }

        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize" + e);
        }
    }

    @VisibleForTesting
    public void setUserAddressDAL(UserAddressDAL userAddressDAL) {
        this.userAddressDAL = userAddressDAL;
    }

    @VisibleForTesting
    public void setAddressDetailsDAL(AddressDetailsDAL addressDetailsDAL) {
        this.addressDetailsDAL = addressDetailsDAL;
    }

    @VisibleForTesting
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
