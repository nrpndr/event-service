-- create table users if it does not exist
CREATE TABLE IF NOT EXISTS `db_events`.`events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `event_date` varchar(10) NOT NULL,
  `event_start_time` varchar(10) NOT NULL,
  `event_duration_in_minutes` int NOT NULL,
  `venue` varchar(100) NOT NULL,
  `description` varchar(5000) NOT NULL,
  `artist_names` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
