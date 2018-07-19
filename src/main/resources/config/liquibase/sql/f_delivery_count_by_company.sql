CREATE OR REPLACE FUNCTION f_delivery_count_by_company(start_date VARCHAR(10) = NULL, end_date VARCHAR(10) = NULL,
                                                       company_id VARCHAR(255) = NULL, region_id VARCHAR(255) = NULL)
    RETURNS TABLE(
        companyName CHARACTER VARYING,
        date        CHARACTER VARYING,
        count       BIGINT,
        companyId   BIGINT)
AS $BODY$
DECLARE
BEGIN
    RETURN QUERY SELECT
                     coalesce(cm.name, '')                                                                     AS companyName,
                     coalesce(TO_CHAR(TO_TIMESTAMP(r.doc_date / 1000), 'YYYY-MM-DD') :: CHARACTER VARYING, '') AS date,
                     coalesce(count(r.*), 0)                                                                   AS count,
                     coalesce(r.company_id,
                              0)                                                                               AS companyId
                 FROM receipt r
                     LEFT JOIN company cm ON r.company_id = cm.id
                     LEFT JOIN client cl ON r.client_id = cl.id
                     LEFT JOIN (
                                   SELECT DISTINCT ON (client_id) *
                                   FROM address
                               ) a ON a.client_id = cl.id
                     LEFT JOIN location l ON a.district_id = l.id
                 WHERE 
--                  r.status = 'DELIVERED' AND
                       (cast(TO_TIMESTAMP(r.doc_date / 1000) AS DATE)
                        BETWEEN to_date($1, 'YYYY-MM-DD') AND to_date($2, 'YYYY-MM-DD') OR $1 IS NULL)
                       AND (cm.name = $3 OR $3 IS NULL)
                       AND (l.name = $4 OR $4 IS NULL)
                 GROUP BY cm.name, TO_CHAR(TO_TIMESTAMP(r.doc_date / 1000), 'YYYY-MM-DD'), r.company_id
                 ORDER BY TO_CHAR(TO_TIMESTAMP(r.doc_date / 1000), 'YYYY-MM-DD') ASC;
END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
ALTER FUNCTION f_delivery_count_by_company( CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING )
OWNER TO logistics;
