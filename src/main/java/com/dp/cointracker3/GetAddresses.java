package com.dp.cointracker3;

import com.dp.cointracker3.DB.UserAddressDAL;
import com.dp.cointracker3.model.UserAddress;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.io.IOException;
import java.util.List;

@Path("/get-addresses")
public class GetAddresses {

    // Ideally singleton instance is injected
    private UserAddressDAL userAddressDAL = new UserAddressDAL();
    ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces("text/plain")
    public String getAddresses(@QueryParam("user") String user) {
        // validation
        if(user == null || user.isBlank()) {
            throw new InvalidRequestStateException("Invalid request : user needs to be valid.");
        }

        List<UserAddress> addresses = userAddressDAL.getAllAddresses(user);
        try {
            return objectMapper.writeValueAsString(addresses);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
