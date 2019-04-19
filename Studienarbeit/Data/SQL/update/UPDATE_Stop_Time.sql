BEGIN;
UPDATE Stop SET realtime = DATE_ADD(realtime, INTERVAL 1 HOUR);
UPDATE Stop SET timeTabledTime = DATE_ADD(timeTabledTime, INTERVAL 1 HOUR);
COMMIT;