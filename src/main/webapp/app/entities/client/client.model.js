"use strict";
var Client = (function () {
    function Client(id, firstName, lastName, address, cityId, regionId, streetId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.cityId = cityId;
        this.regionId = regionId;
        this.streetId = streetId;
    }
    return Client;
}());
exports.Client = Client;
