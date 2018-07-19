CREATE OR REPLACE FUNCTION f_common_report_paged_count(start_date VARCHAR(10) = NULL, end_date VARCHAR(10) = NULL,
                                                       company_id VARCHAR(255) = NULL, region_id VARCHAR(255) = NULL)
    RETURNS BIGINT

LANGUAGE plpgsql
AS $BODY$
DECLARE result BIGINT;
BEGIN
    result:=0;

    SELECT count(r.*) :: BIGINT
    INTO result
    FROM receipt r
        LEFT JOIN logistics.public.company c ON r.company_id = c.id
        LEFT JOIN client cl ON r.client_id = cl.id
        LEFT JOIN (
                      SELECT DISTINCT ON (client_id) *
                      FROM address
                  ) a ON a.client_id = cl.id
        LEFT JOIN location l ON a.district_id = l.id
    WHERE
--         r.status = 'DELIVERED' AND
          (cast(to_timestamp(r.doc_date / 1000) AS DATE) BETWEEN to_date($1, 'yyyy-mm-dd')
           AND to_date($2, 'yyyy-mm-dd') OR $1 IS NULL)
          AND (c.name = $3 OR $3 IS NULL)
          AND (l.name = $4 OR $4 IS NULL);
    RETURN result;
END;
$BODY$
