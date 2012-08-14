
CREATE TABLE userreg (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(128) NOT NULL,
  phone varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  password blob NOT NULL,
  galleryId integer DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
);

CREATE TABLE gallery (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(128) DEFAULT NULL,
  logo varchar(128) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE artist (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  description varchar(1024) DEFAULT NULL,
  galleryid integer NOT NULL,
  photo varchar(128) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE art (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  description varchar(1024) DEFAULT NULL,
  thumbnail varchar(256) NOT NULL,
  galleryId integer NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE art_artist (
  artId integer NOT NULL,
  artistId integer NOT NULL,
  PRIMARY KEY (artistId,artId),
  KEY fk1_idx (artId),
  KEY fk2_idx (artistId),
  CONSTRAINT fk_artId FOREIGN KEY (artId) REFERENCES art (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_artistId FOREIGN KEY (artistId) REFERENCES artist (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE artmedia (
  artId integer NOT NULL,
  url varchar(256) NOT NULL,
  type char(1) NOT NULL,
  label varchar(128) DEFAULT NULL,
  id integer NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id),
  KEY fk1_idx (artId),
  CONSTRAINT fk1 FOREIGN KEY (artId) REFERENCES art (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);



