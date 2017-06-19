CREATE OR REPLACE FUNCTION f_delivery_count_by_company_by_status_by_date(start_date  VARCHAR(10) = NULL,
                                                                         end_date    VARCHAR(10) = NULL,
                                                                         company_id  VARCHAR(255) = NULL,
                                                                         district_id VARCHAR(255) = NULL,
                                                                         status      VARCHAR(255) = NULL)
    RETURNS TABLE(
        districtName  CHARACTER VARYING,
        districtName2 CHARACTER VARYING,
        companyName   CHARACTER VARYING,
        count         BIGINT,
        companyId     BIGINT)
AS $BODY$
DECLARE
BEGIN
    RETURN QUERY SELECT
                     coalesce(l.name, '')      AS districtName,
                     coalesce(l2.name, '')     AS districtName2,
                     coalesce(cm.name, '')     AS companyName,
                     coalesce(count(r.*), 0)   AS count,
                     coalesce(r.company_id, 0) AS companyId
                 FROM receipt r
                     LEFT JOIN company cm ON r.company_id = cm.id
                     LEFT JOIN client cl ON r.client_id = cl.id
                     LEFT JOIN company res ON r.receiver_id = res.id
                     LEFT JOIN (
                                   SELECT DISTINCT ON (client_id) *
                                   FROM address
                               ) a ON a.client_id = cl.id
                     LEFT JOIN address a2 ON res.address_id = a2.id
                     LEFT JOIN location l ON a.district_id = l.id
                     LEFT JOIN location l2 ON a2.district_id = l2.id
                 WHERE (r.status = $5 OR $5 IS NULL) AND
                       (cast(TO_TIMESTAMP(r.doc_date / 1000) AS DATE)
                        BETWEEN to_date($1, 'YYYY-MM-DD') AND to_date($2, 'YYYY-MM-DD') OR $1 IS NULL)
                       AND (cm.name = $3 OR $3 IS NULL)
                       AND (l.name = $4 OR $4 IS NULL)
                       AND r.receiver_id IS NOT NULL OR r.client_id IS NOT NULL
                 GROUP BY cm.name, r.company_id, l.name, l2.name;
END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
ALTER FUNCTION f_delivery_count_by_company_by_status_by_date( CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING )
OWNER TO logistics;
