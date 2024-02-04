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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.annotations.VisibleForTesting;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.ws.rs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sync")
public class Sync {

    // Ideally singleton instance is injected
    private UserAddressDAL userAddressDAL = new UserAddressDAL();
    private BlockChainAPI blockChainAPI = new BlockChainAPI();
    private AddressDetailsDAL addressDetailsDAL = new AddressDetailsDAL();
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @POST
    @Produces("text/plain")
    public String sync(@QueryParam("user") String user) {
        // validation
        if(user == null || user.isBlank()) {
            throw new IllegalArgumentException("Invalid request : user needs to be valid.");
        }

        // first get all addresses
        List<UserAddress> addresses = userAddressDAL.getAllAddresses(user);
        List<String> addresStringList = addresses.stream().map(UserAddress::getAddress).collect(Collectors.toList());

        //fetch details from API
        AddressList detailsList =  blockChainAPI.getAddressList(addresStringList, 5);

        if (detailsList.getAddresses().isEmpty()) {
            throw new IllegalArgumentException("Addresses provided do not exist");
        }

        List<String> failedAddress = new ArrayList<>();
        for(Address detail : detailsList.getAddresses()) {
            try {
                addressDetailsDAL.upsertAddressDetail(detail.getAddress(), objectMapper.writeValueAsString(detail));
            } catch (Exception e) {
                failedAddress.add(detail.getAddress());
            }

        }
        if (failedAddress.isEmpty()) {
            return "SUCCESS";
        } else {
            return "FAILED : " + "\n" + String.join("\n", failedAddress);
        }
    }

    @VisibleForTesting
    public void setUserAddressDAL(UserAddressDAL userAddressDAL) {
        this.userAddressDAL = userAddressDAL;
    }

    @VisibleForTesting
    public void setBlockChainAPI(BlockChainAPI blockChainAPI) {
        this.blockChainAPI = blockChainAPI;
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
