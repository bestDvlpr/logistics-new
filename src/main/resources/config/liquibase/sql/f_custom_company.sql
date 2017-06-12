CREATE OR REPLACE FUNCTION f_custom_company(stat       VARCHAR(50) = NULL,
                                            start_date VARCHAR(10) = NULL,
                                            end_date   VARCHAR(10) = NULL)
    RETURNS TABLE(
        id       BIGINT,
        idNumber CHARACTER VARYING,
        name     CHARACTER VARYING)
AS $BODY$
DECLARE
BEGIN
    RETURN QUERY SELECT
                     coalesce(cm.id, 0)         AS id,
                     coalesce(cm.id_number, '') AS idNumber,
                     coalesce(cm.name, '')      AS name
                 FROM
                     company cm
                 WHERE cm.id IN (SELECT DISTINCT r.company_id
                                 FROM receipt r
                                 WHERE (r.status = stat OR stat IS NULL) AND
                                       (r.delivered_time BETWEEN to_date(start_date, 'yyyy-mm-dd')
                                        AND to_date(end_date, 'yyyy-mm-dd')
                                        OR end_date IS NULL));
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;
ALTER FUNCTION f_custom_company( CHARACTER VARYING, CHARACTER VARYING, CHARACTER VARYING )
OWNER TO logistics;
