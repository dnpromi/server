package com.dp.cointracker3;

import com.dp.cointracker3.DB.UserAddressDAL;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.ws.rs.*;

@Path("/add-address")
public class AddAddress {

    // Ideally singleton instance is injected
    private UserAddressDAL userAddressDAL = new UserAddressDAL();

    @POST
    @Produces("text/plain")
    public String addAddress(@QueryParam("user") String user, @QueryParam("address") String address, @QueryParam("nickname") String nickname) {
        if(user == null || user.isBlank()) {
            throw new InvalidRequestStateException("Invalid request : user needs to be valid.");
        }
        if(address == null || address.isBlank()) {
            throw new InvalidRequestStateException("Invalid request : address needs to be valid.");
        }
        String nicknameNew = nickname;
        if(nicknameNew == null || nickname.isBlank()) {
            nicknameNew = "";
        }

        return userAddressDAL.addAddress(user, nicknameNew, address);
    }

    public void setUserAddressDAL(UserAddressDAL userAddressDAL) {
        this.userAddressDAL = userAddressDAL;
    }
}
