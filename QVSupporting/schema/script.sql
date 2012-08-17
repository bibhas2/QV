use test;

drop table ArtMedia;                                                            
drop table art_artist;                                                          
drop table Art;                                                                 
drop table Artist;                                                              
drop table userreg; 
drop table Gallery;                                                             

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

CREATE TABLE Gallery (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(128) DEFAULT NULL,
  logo varchar(128) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Artist (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  description varchar(1024) DEFAULT NULL,
  galleryid integer NOT NULL,
  photo varchar(128) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT Artistfk1 FOREIGN KEY (galleryId) REFERENCES Gallery (id)
);

CREATE TABLE Art (
  id integer NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  description varchar(1024) DEFAULT NULL,
  thumbnail varchar(256) NOT NULL,
  galleryId integer NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT Artfk1 FOREIGN KEY (galleryId) REFERENCES Gallery (id)
);

CREATE TABLE art_artist (
  artId integer NOT NULL,
  artistId integer NOT NULL,
  PRIMARY KEY (artistId,artId),
  KEY fk1_idx (artId),
  KEY fk2_idx (artistId),
  CONSTRAINT fk_artId FOREIGN KEY (artId) REFERENCES Art (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_artistId FOREIGN KEY (artistId) REFERENCES Artist (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE ArtMedia (
  artId integer NOT NULL,
  url varchar(256) NOT NULL,
  type char(1) NOT NULL,
  label varchar(128) DEFAULT NULL,
  id integer NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id),
  KEY fk1_idx (artId),
  CONSTRAINT fk1 FOREIGN KEY (artId) REFERENCES Art (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

create table Media (
	id integer NOT NULL AUTO_INCREMENT,
  	label varchar(1024) NOT NULL,
  	fileName varchar(256) NOT NULL,
  	mimeType varchar(256) NOT NULL,
  	galleryId integer not null,
  	
  	PRIMARY KEY (id),
  	CONSTRAINT Mediafk1 FOREIGN KEY (galleryId) REFERENCES Gallery (id)
);
  	
create table SysParam (
	id integer NOT NULL AUTO_INCREMENT,
  	name varchar(256) NOT NULL,
  	label varchar(256) NOT NULL,
  	intValue integer,
  	stringValue varchar(1024),
  	
	PRIMARY KEY (id),
	UNIQUE KEY email (name)
);

insert into Gallery (name) values ('MOMA NYC');
insert into Gallery (name) values ('Miami Art Museum');

insert into SysParam (name, label, stringValue) values ('host_name', 'Host name', '');
insert into SysParam (name, label, stringValue) values ('media_dir', 'Media storage directory', '');
insert into SysParam (name, label, stringValue) values ('media_url_prefix', 'Media URL prefix', '');

