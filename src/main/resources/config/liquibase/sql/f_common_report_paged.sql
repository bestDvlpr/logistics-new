CREATE OR REPLACE FUNCTION f_common_report_paged(start_date VARCHAR(10) = NULL, end_date VARCHAR(10) = NULL,
                                                 company_id VARCHAR(255) = NULL, region_id VARCHAR(255) = NULL,
                                                 limitVal   VARCHAR(255) = NULL, offsetVal VARCHAR(255) = NULL)
    RETURNS TABLE(
        companyName      CHARACTER VARYING,
        date             CHARACTER VARYING,
        id               BIGINT,
        docType          CHARACTER VARYING,
        productName      CHARACTER VARYING,
        productQty       BIGINT,
        docDate          CHARACTER VARYING,
        clientFirstName  CHARACTER VARYING,
        clientLastName   CHARACTER VARYING,
        phoneNumber      CHARACTER VARYING,
        districtName     CHARACTER VARYING,
        address          CHARACTER VARYING,
        carModel         CHARACTER VARYING,
        carNumber        CHARACTER VARYING,
        driverFirstName  CHARACTER VARYING,
        driverLastName   CHARACTER VARYING,
        sentToDCTime     CHARACTER VARYING,
        deliveredTime    CHARACTER VARYING,
        deliveryTookTime CHARACTER VARYING,
        companyId        BIGINT)
AS $BODY$
DECLARE
BEGIN
    RETURN QUERY SELECT
                     coalesce(c.name, '')     AS companyName,
                     coalesce(TO_CHAR(TO_TIMESTAMP(r.doc_date / 1000), 'DD-MM-YYYY') :: CHARACTER VARYING,
                              '')             AS date,
                     coalesce(r.id, 0)        AS id,
                     coalesce(r.doc_type, '') AS docType,
                     coalesce(p.name, '')     AS productName,
                     coalesce(pe.qty :: BIGINT,
                              0)              AS productQty,
                     coalesce(TO_CHAR(TO_TIMESTAMP(r.doc_date / 1000), 'DD-MM-YYYY HH24:MM:HH') :: CHARACTER VARYING,
                              '')             AS docDate,
                     coalesce(cl.first_name,
                              '')             AS clientFirstName,
                     coalesce(cl.last_name,
                              '')             AS clientLastName,
                     coalesce(pn.number,
                              '')             AS phoneNumber,
                     coalesce(l.name,
                              '')             AS districtName,
                     coalesce(a.street_address,
                              '')             AS address,
                     coalesce(cm.name,
                              '')             AS carModel,
                     coalesce(cr.number,
                              '')             AS carNumber,
                     coalesce(dr.first_name,
                              '')             AS driverFirstName,
                     coalesce(dr.last_name,
                              '')             AS driverLastName,
                     coalesce(TO_CHAR(r.sent_to_dc_time, 'DD-MM-YYYY HH24:MM:SS') :: CHARACTER VARYING,
                              '')             AS sentToDCTime,
                     coalesce(TO_CHAR(r.delivered_time, 'DD-MM-YYYY HH24:MM:SS') :: CHARACTER VARYING,
                              '')             AS deliveredTime,
                     coalesce(
                         to_char(r.delivered_time - to_timestamp(r.doc_date / 1000), 'HH24:MM:SS') :: CHARACTER VARYING,
                         '')                  AS deliveryTookTime,
                     coalesce(r.company_id,
                              0)              AS companyId
                 FROM receipt r
                     LEFT JOIN address adr ON r.address_id = adr.id
                     LEFT JOIN company c ON r.company_id = c.id
                     LEFT JOIN product_entry pe ON pe.receipt_id = r.id
                     LEFT JOIN product p ON pe.product_id = p.id
                     LEFT JOIN client cl ON r.client_id = cl.id
                     LEFT JOIN (
                                   SELECT DISTINCT ON (client_id) *
                                   FROM address
                               ) a ON a.client_id = cl.id
                     LEFT JOIN location l ON a.district_id = l.id
                     LEFT JOIN car cr ON pe.attached_car_id = cr.id
                     LEFT JOIN car_model cm ON cr.car_model_id = cm.id
                     LEFT JOIN (
                                   SELECT DISTINCT ON (dc.cars_id)
                                       driver.*,
                                       car.id AS carId
                                   FROM driver_cars dc
                                       INNER JOIN driver driver ON dc.drivers_id = driver.id
                                       INNER JOIN car ON dc.cars_id = car.id
                               ) dr ON dr.carId = cr.id
                     LEFT JOIN (
                                   SELECT DISTINCT ON (phone_number.client_id) *
                                   FROM phone_number
                               ) pn ON pn.client_id = cl.id
                 WHERE r.status = 'DELIVERED' AND
                       (cast(TO_TIMESTAMP(r.doc_date / 1000) AS DATE)
                        BETWEEN to_date($1, 'yyyy-mm-dd') AND to_date($2, 'yyyy-mm-dd') OR $1 IS NULL)
                       AND (c.name = $3 OR $3 IS NULL)
                       AND (l.name = $4 OR $4 IS NULL)
                 LIMIT (limitVal :: BIGINT)
                 OFFSET (offsetVal :: BIGINT);
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;
ALTER FUNCTION f_common_report_paged( CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING )
OWNER TO logistics;
