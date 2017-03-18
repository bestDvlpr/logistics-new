"use strict";
var PayMaster = (function () {
    function PayMaster(id, paymasterID, payMasterName) {
        this.id = id;
        this.paymasterID = paymasterID;
        this.payMasterName = payMasterName;
    }
    return PayMaster;
}());
exports.PayMaster = PayMaster;
