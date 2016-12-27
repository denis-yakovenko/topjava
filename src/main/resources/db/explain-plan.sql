EXPLAIN SELECT * FROM meals WHERE id=100002 and userid = 100000;
EXPLAIN SELECT * FROM meals WHERE userid = 100000 and datetime BETWEEN '2015-05-30 10:00:00.000000' and '2015-05-31 23:00:00.000000' ORDER BY datetime desc;
EXPLAIN SELECT id, datetime, description, calories FROM meals WHERE id=100003 AND userid=100000;
EXPLAIN SELECT id, datetime, description, calories FROM meals where userid=100001 ORDER BY datetime desc;
EXPLAIN UPDATE meals SET datetime='2015-05-30 10:00:00.000000', description='aaaaaaa', calories=111 WHERE id=100002 and userid = 100000;
EXPLAIN DELETE FROM meals WHERE id=100002 and userid = 100000;