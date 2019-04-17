SELECT 
        count(*)
FROM
        Monitor, Stop
WHERE
        Monitor.number > 800
        AND name = 'kvv' AND
        Stop.realtime > DATE_SUB(timestamp, INTERVAL 1 MINUTE) AND Stop.realTime < timestamp;