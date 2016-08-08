DROP SCHEMA IF EXISTS `sda-jdbc`;
CREATE SCHEMA IF NOT EXISTS `sda-jdbc` DEFAULT CHARACTER SET utf8 ;
USE `sda-jdbc` ;

CREATE TABLE IF NOT EXISTS `sda-jdbc`.`uzytkownik` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `imie` VARCHAR(45) NOT NULL,
  `nazwisko` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB,DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `sda-jdbc`.`ksiazka` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tytul` VARCHAR(45) NOT NULL,
  `autor` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB,DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `sda-jdbc`.`wypozyczenie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_wypozyczenia` DATETIME NOT NULL,
  `data_zwrotu` DATETIME NULL,
  `zwrocone` INT NOT NULL,
  `uzytkownik_id` INT NOT NULL,
  `ksiazka_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_wypozyczenie_uzytkownik_idx` (`uzytkownik_id` ASC),
  INDEX `fk_wypozyczenie_ksiazka1_idx` (`ksiazka_id` ASC),
  CONSTRAINT `fk_wypozyczenie_uzytkownik`
  FOREIGN KEY (`uzytkownik_id`)
  REFERENCES `sda-jdbc`.`uzytkownik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wypozyczenie_ksiazka1`
  FOREIGN KEY (`ksiazka_id`)
  REFERENCES `sda-jdbc`.`ksiazka` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB,DEFAULT CHARACTER SET utf8;
