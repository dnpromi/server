package com.dp.cointracker3;

import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.model.UserAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.List;
@Path("/get-address")

public class GetAddress {

    // Ideally singleton instance is injected
    private UserAddressDAL userAddressDAL = new UserAddressDAL();
    ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces("text/plain")
    public String getAddresses(@QueryParam("address-id") String addressId) {
        // validation
        if(addressId == null || addressId.isBlank()) {
            throw new IllegalArgumentException("Invalid request : user needs to be valid.");
        }

        UserAddress address = userAddressDAL.getAddress(addressId);
        if(address == null) {
            throw new IllegalArgumentException("Invalid address-id :" + addressId);
        }
        return address.getAddress();
    }

    public void setUserAddressDAL(UserAddressDAL userAddressDAL) {
        this.userAddressDAL = userAddressDAL;
    }
}
