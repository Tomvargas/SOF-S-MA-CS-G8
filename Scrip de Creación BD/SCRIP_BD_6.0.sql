-- -----------------------------------------------------
-- ESQUEMA Gestion_Arbitros
-- -----------------------------------------------------
DROP DATABASE IF EXISTS Gestion_Arbitros;

CREATE SCHEMA
IF NOT EXISTS Gestion_Arbitros;
USE Gestion_Arbitros;

-- -----------------------------------------------------
-- CREAR TABLAS
-- -----------------------------------------------------
-- TABLA ROL
-- -----------------------------------------------------
-- CREAR 4 ROLES (ARBITRO - SECRETARIA - PRESIDENTE - ADMINSTRADOR)
-- EMPIEZA CON VALORES PREDETERMINADOS EN 1
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS rol
(
  id_rol INT NOT NULL AUTO_INCREMENT,
  nombre_rol VARCHAR
(45) NOT NULL,
  create_at DATE NULL,
  delete_at DATE NULL,
  updated_at DATE NULL,
  estado VARCHAR
(2) NULL,
  PRIMARY KEY
(id_rol)
);

-- -----------------------------------------------------
-- TABLA USUARIO
-- CREAR 1 USUARIO CON CADA ROL
-- VALORES DE ID PREDETERMINADOS EN 200
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS usuario
(
  id_usuario INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR
(45) NOT NULL,
  apellido VARCHAR
(45) NOT NULL,
  nombre_usuario VARCHAR
(45) NOT NULL,
  email VARCHAR
(45) NOT NULL,
  contrasenia VARCHAR
(45) NOT NULL,
  create_at DATE NULL,
  delete_at DATE NULL,
  updated_at DATE NULL,
  estado VARCHAR
(2) NULL,
  rol_id_rol INT NOT NULL,
  PRIMARY KEY
(id_usuario)
);

ALTER TABLE usuario AUTO_INCREMENT = 200;

ALTER TABLE usuario ADD CONSTRAINT fk_usuario_rol1
    FOREIGN KEY (rol_id_rol)    
    REFERENCES rol (id_rol);

-- -----------------------------------------------------
-- TABLA ARBITRO
-- VALORES DE ID PREDETERMINADOS EN 300
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS arbitro
(
  id_arbitro INT NOT NULL AUTO_INCREMENT,
  categoria VARCHAR
(45) NOT NULL,
  nombre VARCHAR
(45) NOT NULL,
  apellido VARCHAR
(45) NOT NULL,
  email VARCHAR
(45) NOT NULL,
  nombre_usuario VARCHAR
(45) NOT NULL,
  contrasenia VARCHAR
(45) NOT NULL,
  edad INT NOT NULL,
  nacionalidad VARCHAR
(45) NOT NULL,
  cantidad_partidos INT NOT NULL,
  create_at DATE NULL,
  delete_at DATE NULL,
  updated_at DATE NULL,
  estado VARCHAR
(2) NULL,
  PRIMARY KEY
(id_arbitro)
);

ALTER TABLE arbitro AUTO_INCREMENT = 300;

ALTER TABLE ARBITRO ADD COLUMN ROL_ID_ROL INT;

SET SQL_SAFE_UPDATES = 0;
update arbitro set rol_id_rol = 1 where rol_id_rol is null;
SET SQL_SAFE_UPDATES = 1;
-- -----------------------------------------------------
-- TABLA CLUB
-- VALORES DE ID PREDETERMINADOS EN 400
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS club
(
  id_club INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR
(45) NOT NULL,
  director VARCHAR
(45) NOT NULL,
  create_at DATE NULL,
  delete_at DATE NULL,
  updated_at DATE NULL,
  estado VARCHAR
(2) NULL,
  PRIMARY KEY
(id_club)
);

ALTER TABLE club AUTO_INCREMENT = 400;

-- -----------------------------------------------------
-- TABLA PARTIDO
-- VALORES DE ID PREDETERMINADOS EN 500
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS partido
(
  id_partido INT NOT NULL AUTO_INCREMENT,
  club_id_local INT NOT NULL,
  club_id_rival INT NOT NULL,
  estado varchar
(2) NULL,
  PRIMARY KEY
(id_partido)
);

ALTER TABLE partido AUTO_INCREMENT = 500;

ALTER TABLE partido ADD CONSTRAINT fk_partido_club1
    FOREIGN KEY (club_id_local)    
    REFERENCES club (id_club);

ALTER TABLE partido ADD CONSTRAINT fk_partido_club2
    FOREIGN KEY (club_id_rival)
    REFERENCES club (id_club);
ALTER TABLE PARTIDO ADD COLUMN PARTIDO_DESCRIPCION VARCHAR(100);
-- -----------------------------------------------------
-- TABLA SORTEO
-- VALORES DE ID PREDETERMINADOS EN 600
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS sorteo
(
  id_sorteo INT NOT NULL AUTO_INCREMENT,
  fecha_sorteo DATE NOT NULL,
  create_at DATE NULL,
  delete_at DATE NULL,
  update_at DATE NULL,
  estado VARCHAR
(2) NULL,
  arbitro_id_arbitro INT NOT NULL,
  partido_id_partido INT NOT NULL,
  arbitro_id_sustituto INT NOT NULL,
  PRIMARY KEY
(`id_sorteo`)
);

ALTER TABLE sorteo AUTO_INCREMENT = 600;

ALTER TABLE sorteo ADD CONSTRAINT fk_sorteo_arbitro1
    FOREIGN KEY (arbitro_id_arbitro)
    REFERENCES arbitro (id_arbitro);

ALTER TABLE sorteo ADD CONSTRAINT fk_sorteo_partido1
    FOREIGN KEY (partido_id_partido)
    REFERENCES partido (id_partido);

ALTER TABLE sorteo ADD CONSTRAINT fk_sorteo_arbitro2
    FOREIGN KEY (arbitro_id_sustituto)
    REFERENCES arbitro (id_arbitro);

-- -----------------------------------------------------
-- TABLE AGENDA
-- VALORES DE ID PREDETERMINADOS EN 700
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS agenda
(
  id_agenda INT NOT NULL AUTO_INCREMENT,
  fecha_partido DATE NOT NULL,
  lugar_partido VARCHAR
(45) NOT NULL,
  hora_partido TIME NOT NULL COMMENT 'Valores de time (hora: minuto: segundo)',
  create_at DATE NULL,
  delete_at DATE NULL,
  updated_at DATE NULL,
  estado VARCHAR
(2) NULL,
  partido_id_partido INT NOT NULL,
  PRIMARY KEY
(id_agenda)
);

ALTER TABLE agenda AUTO_INCREMENT = 700;

ALTER TABLE agenda ADD CONSTRAINT fk_agenda_partido1
    FOREIGN KEY (partido_id_partido)
    REFERENCES partido (id_partido);

ALTER TABLE AGENDA ADD COLUMN SORTEADO VARCHAR(2);

-- -----------------------------------------------------
-- TABLA ACTA_PARTIDO
-- VALORES DE ID PREDETERMINADOS EN 800
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS acta_partido
(
  id_acta_partido INT NOT NULL AUTO_INCREMENT,
  codigo_acta INT NOT NULL,
  fecha_emision_acta DATE NOT NULL,
  hora_inicio_partido TIME NOT NULL,
  hora_fin_partido TIME NOT NULL,
  equipo_local VARCHAR
(45)  NULL,   -- tal vez deberia omitirse ¿?
  equipo_rival VARCHAR
(45)  NULL,   -- tal vez deberia omitirse ¿?
  duracion_partido TIME NOT NULL,
  num_gol_equipo_local INT NOT NULL,
  num_gol_equipo_rival INT NOT NULL,
  equipo_ganador VARCHAR
(45) NOT NULL,
  create_at DATE NULL,
  delete_at DATE NULL,
  update_at DATE NULL,
  estado VARCHAR
(2) NULL,
  partido_id_partido INT NOT NULL,
  PRIMARY KEY
(id_acta_partido)
);

ALTER TABLE acta_partido AUTO_INCREMENT = 800;

ALTER TABLE acta_partido ADD CONSTRAINT fk_acta_partido_partido1
    FOREIGN KEY (partido_id_partido)
    REFERENCES partido (id_partido);

-- -----------------------------------------------------
-- TABLA ASISTENCIA
-- VALORES DE ID PREDETERMINADOS EN 900
-- -----------------------------------------------------
CREATE TABLE
IF NOT EXISTS asistencia
(
  id_asistencia INT NOT NULL AUTO_INCREMENT,
  partido VARCHAR
(45) NOT NULL,
  lugar VARCHAR
(45) NOT NULL,
  fecha_encuentro DATE NOT NULL,
  asistencia TINYINT NOT NULL COMMENT 'Tipo de dato BOOLEAN',
  estado VARCHAR
(2) NULL,
  arbitro_id_arbitro INT NOT NULL,
  PRIMARY KEY
(id_asistencia)
);

ALTER TABLE asistencia AUTO_INCREMENT = 900;

ALTER TABLE asistencia ADD CONSTRAINT fk_asistencia_arbitro1
    FOREIGN KEY (arbitro_id_arbitro)
    REFERENCES arbitro (id_arbitro);

-- ------------------------ FIN DEL ESQUEMA


-- ------------------------ PROCEDIMIENTOS ALAMCENADOS ------------------------
-- -----------------------------------------------------
-- PROCEDURE_CRUD_ROL
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_rol;
DELIMITER $$
CREATE PROCEDURE PR_insertar_rol(
    IN xNombre_rol VARCHAR
(45),  
    IN xEstado VARCHAR
(2)
    )
-- MODIFICADO SE SETEAN LOS VALORES DE AUDITORIA CREATEAT DESDE LA BASE
BEGIN
    INSERT INTO rol
        (nombre_rol, create_at, delete_at, updated_at, estado)
    VALUES
        (xNombre_rol, curdate(), NULL, NULL, xEstado);
END
$$
DELIMITER ;
CALL PR_insertar_rol
('arbitro', 'A');        -- -> 01
CALL PR_insertar_rol
('secretaria', 'A');     -- -> 02
CALL PR_insertar_rol
('presidente', 'A');     -- -> 03
CALL PR_insertar_rol
('administrador', 'A');
-- -> 04
-- CALL PR_insertar_rol(?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_rol;
DELIMITER $$
CREATE PROCEDURE PR_consultar_rol(IN xId_rol INT)
BEGIN
    SELECT *
    FROM rol
    WHERE id_rol = xId_rol;
END
$$
DELIMITER ;
CALL PR_consultar_rol
(1);
-- CALL PR_consultar_rol(?);           -> llamada en Java
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_rol_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_rol_()
BEGIN
    SELECT *
    FROM rol
    WHERE ESTADO <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_rol;
DELIMITER $$
CREATE PROCEDURE PR_modificar_rol(
    IN xId_rol INT,
    IN xNombre_rol VARCHAR
(45)
    )
BEGIN
    UPDATE rol SET nombre_rol = xNombre_rol, updated_at = curdate() WHERE id_rol = xId_rol;
END
$$
DELIMITER ;
-- CALL PR_modificar_rol(1, 'arbitro', '2023-01-28', NULL, NULL, 'A');
-- CALL PR_modificar_rol(?, ?, ?, ?, ?, ?)      -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_rol;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_rol(IN xId_rol INT)
BEGIN
    UPDATE ROL SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_rol = xId_rol;
END
$$
DELIMITER ;
-- CALL PR_eliminar_rol(1);
-- CALL PR_eliminar_rol(?);     -> llamada en Java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_USUARIO
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_usuario;
DELIMITER $$
CREATE PROCEDURE PR_insertar_usuario(
    IN xNombre VARCHAR
(45), 
    IN xApellido VARCHAR
(45), 
    IN xNombre_usuario VARCHAR
(45), 
    IN xEmail VARCHAR
(45), 
    IN xContrasenia VARCHAR
(45), 
    IN xEstado VARCHAR
(2),
    IN xRol_id_rol INT
    )
BEGIN
    INSERT INTO usuario
        (nombre, apellido, nombre_usuario, email, contrasenia, create_at, delete_at, updated_at, estado, rol_id_rol)
    VALUES
        (xNombre, xApellido, xNombre_usuario, xEmail, xContrasenia, curdate() , NULL, NULL, xEstado, xRol_id_rol);
END
$$
DELIMITER ;
CALL PR_insertar_usuario
('Juan', 'Perez', 'juanperez', 'juanperez@example.com', '123', 'A', 1);          -- 200
CALL PR_insertar_usuario
('Jose', 'Perez', 'joseperez', 'joseperez@example.com', '123', 'A', 2);          -- 201
CALL PR_insertar_usuario
('Julio', 'Perez', 'julioperez', 'julioperez@example.com', '123', 'A', 3);       -- 202
CALL PR_insertar_usuario
('Juanito', 'Perez', 'juanitoperez', 'juanitoperez@example.com', '123', 'A', 4);
-- 203
-- CALL PR_insertar_usuario(?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_usuario;
DELIMITER $$
CREATE PROCEDURE PR_consultar_usuario(IN xId_usuario INT)
BEGIN
    SELECT *
    FROM usuario
    WHERE id_usuario = xId_usuario;
END
$$
DELIMITER ;
CALL PR_consultar_usuario
(200);
-- CALL PR_consultar_rol(?);           -> llamada en Java
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_usuario_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_usuario_()
BEGIN
    select 
    u.id_usuario as id,
    u.nombre,
    u.apellido,
    u.nombre_usuario,
    u.email,
    u.contrasenia,
    u.rol_id_rol as id_rol,
    r.nombre_rol 
    from 
    usuario u 
    inner join rol r on r.id_rol = u.rol_id_rol
    where u.estado <> 'E';
END
$$
DELIMITER ;YO

DROP PROCEDURE IF EXISTS PR_modificar_usuario;
DELIMITER $$
CREATE PROCEDURE PR_modificar_usuario(
    IN xId_usuario INT,
    IN xNombre VARCHAR
(45), 
    IN xApellido VARCHAR
(45), 
    IN xNombre_usuario VARCHAR
(45), 
    IN xEmail VARCHAR
(45), 
    IN xContrasenia VARCHAR
(45),
    IN xRol_id_rol INT
    )
BEGIN
    UPDATE usuario SET nombre = xNombre, apellido = xApellido, nombre_usuario = xNombre_usuario, email = xEmail, contrasenia = xContrasenia, 
    updated_at = curdate(), rol_id_rol = xRol_id_rol 
    WHERE id_usuario = xId_usuario;
END
$$
DELIMITER ;
CALL PR_modificar_usuario
(200, 'Juan', 'Perez', 'juanperez', 'juanperez@example.com', 'password123', 1);
-- CALL PR_modificar_usuario(?, ?, ?, ?, ?, ?, ?);         -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_usuario;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_usuario(IN xId_usuario INT)
BEGIN
    UPDATE USUARIO SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_usuario = xId_usuario;
END
$$
DELIMITER ;
CALL PR_eliminar_usuario
(200);
-- CALL PR_eliminar_usuario(?);           -> llamada en Java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_ARBITRO
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_arbitro;
DELIMITER $$
CREATE PROCEDURE PR_insertar_arbitro(
    IN xCategoria VARCHAR
(45), 
    IN xNombre VARCHAR
(45), 
    IN xApellido VARCHAR
(45), 
    IN xEmail VARCHAR
(45), 
    IN xNombre_usuario VARCHAR
(45), 
    IN xContrasenia VARCHAR
(45), 
    IN xEdad INT, 
    IN xNacionalidad VARCHAR
(45), 
    IN xCantidad_partidos INT, 
    IN xEstado VARCHAR
(2)
    )
BEGIN
    INSERT INTO arbitro
        (categoria, nombre, apellido, email, nombre_usuario, contrasenia, edad, nacionalidad, cantidad_partidos,
        create_at, delete_at, updated_at, estado, rol_id_rol)
    VALUES
        (xCategoria, xNombre, xApellido, xEmail, xNombre_usuario, xContrasenia, xEdad, xNacionalidad, xCantidad_partidos,
            curdate(), NULL, NULL, xEstado, 1);
END
$$
DELIMITER ;
CALL PR_insertar_arbitro
('Profesional', 'Pedro', 'Santamaria', 'pedroSantamaria@example.com', 'PedroSantamaria', '123', 25, 'Venezolano', 
30, 'A');    -- 300
CALL PR_insertar_arbitro
('Profesional', 'Miquel', 'Villalba', 'miquelVillalba@example.com', 'MiquelVillalba', '123', 25, 'Ecuatoriano', 
30, 'A');
-- 301
-- CALL PR_insertar_arbitro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_arbitro;
DELIMITER $$
CREATE PROCEDURE PR_consultar_arbitro(IN xId_arbitro INT)
BEGIN
    SELECT *
    FROM arbitro
    WHERE id_arbitro = xId_arbitro;
END
$$
DELIMITER ;
CALL PR_consultar_arbitro
(300);
-- CALL PR_consultar_arbitro(?);           -> llamada en Java
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_arbitro_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_arbitro_()
BEGIN
    SELECT *
    FROM arbitro
    WHERE ESTADO <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_arbitro;
DELIMITER $$
CREATE PROCEDURE PR_modificar_arbitro(
    IN xId_arbitro INT,
    IN xCategoria VARCHAR
(45), 
    IN xNombre VARCHAR
(45), 
    IN xApellido VARCHAR
(45), 
    IN xEmail VARCHAR
(45), 
    IN xNombre_usuario VARCHAR
(45), 
    IN xContrasenia VARCHAR
(45), 
    IN xEdad INT, 
    IN xNacionalidad VARCHAR
(45), 
    IN xCantidad_partidos INT
    )
BEGIN
    UPDATE arbitro SET categoria = xCategoria, nombre = xNombre, apellido = xApellido, email = xEmail, nombre_usuario = xNombre_usuario, 
    contrasenia = xContrasenia, edad = xEdad, nacionalidad = xNacionalidad, cantidad_partidos = xCantidad_partidos, updated_at = curdate()
    WHERE id_arbitro = xId_arbitro;
END
$$
DELIMITER ;
CALL PR_modificar_arbitro
(300, 'Profesional', 'Pedro', 'Santamaria', 'pedroSantamaria@example.com', 'PedroSantamaria', '123', 25, 'Venezolano', 30);
-- CALL PR_modificar_arbitro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);               -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_arbitro;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_arbitro(IN xId_arbitro INT)
BEGIN
    UPDATE ARBITRO SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_arbitro = xId_arbitro;
END
$$
DELIMITER ;
-- CALL PR_eliminar_arbitro(300);
-- CALL PR_eliminar_arbitro(?);           -> llamada en Java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_CLUB
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_club;
DELIMITER $$
CREATE PROCEDURE PR_insertar_club(
    IN xNombre VARCHAR
(45),
    IN xDirector VARCHAR
(45),
    IN xEstado VARCHAR
(2)
    )
BEGIN
    INSERT INTO club
        (nombre, director, create_at, delete_at, updated_at, estado)
    VALUES
        (xNombre, xDirector, curdate(), null, NULL, xEstado);
END
$$
DELIMITER ;
CALL PR_insertar_club
('club_Ecuador', 'Dir Juan', 'A');   -- 400
CALL PR_insertar_club
('club_Alemania', 'Dir Diego', 'A');  -- 401
CALL PR_insertar_club
('club_Argentina', 'Dir Kevin', 'A'); -- 402
CALL PR_insertar_club
('club_Venezuela', 'Dir Carlos', 'A');
-- 403
-- CALL PR_insertar_club(?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_club;
DELIMITER $$
CREATE PROCEDURE PR_consultar_club(IN xId_club INT)
BEGIN
    SELECT *
    FROM club
    WHERE id_club = xId_club;
END
$$
DELIMITER ;
CALL PR_consultar_club
(400);
-- CALL PR_consultar_club(?);           -> llamada en Java
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_club_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_club_()
BEGIN
    SELECT *
    FROM club
    WHERE ESTADO <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_club;
DELIMITER $$
CREATE PROCEDURE PR_modificar_club(
    IN xId_club INT,
    IN xNombre VARCHAR
(45),
    IN xDirector VARCHAR
(45)
    )
BEGIN
    UPDATE club SET nombre = xNombre, director = xDirector, updated_at = curdate()
    WHERE id_club = xId_club;
END
$$
DELIMITER ;
CALL PR_modificar_club
(400, 'club_Ecuador', 'Dir Juan');
-- 400
-- CALL PR_modificar_club(?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_club;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_club(IN xId_club INT)
BEGIN
    UPDATE CLUB SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_club = xId_club;
END
$$
DELIMITER ;
-- CALL PR_eliminar_club(400);
-- CALL PR_eliminar_club(?);           -> llamada en Java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_PARTIDO
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_partido;
DELIMITER $$
CREATE PROCEDURE PR_insertar_partido(
    IN xClub_id_local INT,
    IN xClub_id_rival INT,
    IN xESTADO VARCHAR
(2)
    )
BEGIN
## Modificado por Flavio
## Se agrega selec para retorno de id
    declare nombre_local varchar(50);
    declare nombre_rival varchar(50);
    set nombre_local := (select nombre from club where id_club = xClub_id_local and estado <> 'E');
    set nombre_rival := (select nombre from club where id_club = xClub_id_rival and estado <> 'E');
    
    INSERT INTO partido (club_id_local, club_id_rival, ESTADO, partido_descripcion)
    VALUES (xClub_id_local, xClub_id_rival, xESTADO , concat(nombre_local , ' VS ' , nombre_rival));
    
    select max(id_partido) as id_partido from partido;
END
$$
DELIMITER ;
CALL PR_insertar_partido
(400, 403, 'A');
-- 500
-- CALL PR_insertar_partido(?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_partido;
DELIMITER $$
CREATE PROCEDURE PR_consultar_partido(IN xId_partido INT)
BEGIN
    SELECT *
    FROM partido
    WHERE id_partido = xId_partido;
END
$$
DELIMITER ;
CALL PR_consultar_partido
(500);
-- CALL PR_consultar_partido(?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_partido_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_partido_()
BEGIN
    SELECT *
    FROM partido
    WHERE ESTADO <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_partido;
DELIMITER $$
CREATE PROCEDURE PR_modificar_partido(
    IN xId_partido INT,
    IN xClub_id_local INT,
    IN xClub_id_rival INT
    )
BEGIN
    UPDATE partido SET club_id_local = xClub_id_local, club_id_rival = xClub_id_rival 
    WHERE id_partido = xId_partido;
END
$$
DELIMITER ;
CALL PR_modificar_partido
(500, 400, 403);
-- CALL PR_modificar_partido(?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_partido;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_partido(IN xId_partido INT)
BEGIN
    UPDATE PARTIDO SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_partido = xId_partido;
END
$$
DELIMITER ;
-- CALL PR_eliminar_partido(500);
-- CALL PR_eliminar_partido(?);           -> llamada en Java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_SORTEO
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_sorteo;
DELIMITER $$
CREATE PROCEDURE PR_insertar_sorteo(
    IN xFecha_sorteo DATE,
    IN xPartido_id_partido INT,
    IN xArbitro_id_arbitro INT,
    IN xArbitro_id_sustituto INT,
    IN xEstado VARCHAR(2)
    )
BEGIN
    INSERT INTO sorteo
        (fecha_sorteo, create_at, delete_at, update_at, estado, arbitro_id_arbitro, partido_id_partido, arbitro_id_sustituto)
    VALUES
        (xFecha_sorteo, curdate(), NULL, NULL, xEstado, xArbitro_id_arbitro, xPartido_id_partido, xArbitro_id_sustituto);

    UPDATE AGENDA SET SORTEADO ='S' WHERE PARTIDO_ID_PARTIDO = xPartido_id_partido;
END
$$
DELIMITER ;

CALL PR_insertar_sorteo
('2023-01-10', 500, 300, 301, 'A');
-- 600
-- CALL PR_insertar_sorteo(?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_sorteo;
DELIMITER $$
CREATE PROCEDURE PR_consultar_sorteo(IN xId_sorteo INT)
BEGIN
    SELECT *
    FROM sorteo
    WHERE id_sorteo = xId_sorteo;
END
$$
DELIMITER ;
CALL PR_consultar_sorteo
(600);
-- CALL PR_consultar_sorteo(?);
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_sorteo_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_sorteo_()
BEGIN
    SELECT *
    FROM sorteo
    WHERE ESTADO <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_sorteo;
DELIMITER $$
CREATE PROCEDURE PR_modificar_sorteo(
    IN xId_sorteo INT,
    IN xFecha_sorteo DATE,
    IN xPartido_id_partido INT,
    IN xArbitro_id_arbitro INT,
    IN xArbitro_id_sustituto INT
    )
BEGIN
    UPDATE sorteo SET fecha_sorteo = xFecha_sorteo, update_at = curdate(), arbitro_id_arbitro = xArbitro_id_arbitro, 
    partido_id_partido = xPartido_id_partido, arbitro_id_sustituto = xArbitro_id_sustituto
    WHERE id_sorteo = xId_sorteo;
END
$$
DELIMITER ;
CALL PR_modificar_sorteo
(600, curdate(), 500,300, 301);
-- CALL PR_modificar_sorteo(?, ?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_sorteo;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_sorteo(IN xId_sorteo INT)
BEGIN
    UPDATE SORTEO SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_sorteo = xId_sorteo;
END
$$
DELIMITER ;
-- CALL PR_eliminar_sorteo(600);
-- CALL PR_eliminar_sorteo(?);       -> llamada en java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_AGENDA
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_agenda;
DELIMITER $$
CREATE PROCEDURE PR_insertar_agenda(
    IN xFecha_partido DATE,
    IN xLugar_partido VARCHAR
(45),
    IN xHora_partido TIME,
    IN xEstado VARCHAR
(2),
    IN xPartido_id_partido INT
    )
BEGIN
    INSERT INTO agenda
        (fecha_partido, lugar_partido, hora_partido, create_at, delete_at, updated_at, estado, partido_id_partido)
    VALUES
        (xFecha_partido, xLugar_partido, xHora_partido, curdate(), NULL, NULL, xEstado, xPartido_id_partido);
END
$$
DELIMITER ;
CALL PR_insertar_agenda('2023-01-11', 'BRASIL', CURTIME(), 'A', 500);
-- 700
-- CALL PR_insertar_agenda(?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_agenda;
DELIMITER $$
CREATE PROCEDURE PR_consultar_agenda(IN xId_agenda INT)
BEGIN
    SELECT *
    FROM agenda
    WHERE id_agenda = xId_agenda;
END
$$
DELIMITER ;
CALL PR_consultar_agenda
(700);
-- CALL PR_consultar_agenda(?);                   -> llamada en Java
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_agenda_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_agenda_()
BEGIN
    ## Modificado por Flavio
    ## para realizacion de consultas con tabla partido 
    ##
    SELECT a.id_agenda, a.partido_id_partido , a.fecha_partido, a.lugar_partido,
    a.hora_partido, p.club_id_local, l.nombre as nombre_local, p.club_id_rival,
    r.nombre as nombre_rival, a.estado
    FROM agenda a
    inner join partido p on a.partido_id_partido = p.id_partido
    inner join club l on p.club_id_local = l.id_club
    inner join club r on p.club_id_rival = r.id_club
    WHERE a.ESTADO <> 'E';
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_agenda;
DELIMITER $$
CREATE PROCEDURE PR_modificar_agenda(
    IN xId_agenda INT,
    IN xFecha_partido DATE,
    IN xLugar_partido VARCHAR
(45),
    IN xHora_partido TIME,
    IN xPartido_id_partido INT
    )
BEGIN
    UPDATE agenda SET fecha_partido = xFecha_partido, lugar_partido = xLugar_partido, hora_partido = xHora_partido,
    updated_at = curdate(), partido_id_partido = xPartido_id_partido
    WHERE id_agenda = xId_agenda;
END
$$
DELIMITER ;
CALL PR_modificar_agenda
(700, '2023-01-11', 'BRASIL', CURTIME(),500);
-- CALL PR_modificar_agenda(?, ?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_agenda;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_agenda(IN xId_agenda INT)
BEGIN
    UPDATE AGENDA SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_agenda = xId_agenda;
END
$$
DELIMITER ;
-- CALL PR_eliminar_agenda(700);
-- CALL PR_eliminar_agenda(?);                   -> llamada en Java


-- -----------------------------------------------------
-- PROCEDURE_CRUD_ACTA_PARTIDO
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_acta_partido;
DELIMITER $$
CREATE PROCEDURE PR_insertar_acta_partido(
    IN xFecha_emision_acta DATE,
    IN xHora_inicio_partido TIME,
    IN xHora_fin_partido TIME,
    IN xEquipo_local VARCHAR
(45),       -- tal vez deberia omitirse ¿?
    IN xEquipo_rival VARCHAR
(45),       -- tal vez deberia omitirse ¿?
    IN xDuracion_partido TIME,
    IN xNum_gol_equipo_local INT,
    IN xNum_gol_equipo_rival INT,
    IN xEquipo_ganador VARCHAR
(45),
    IN xEstado VARCHAR
(2),
    IN xPartido_id_partido INT
    )
BEGIN
	declare xCodigo_acta int;
    set xCodigo_acta := (select codigo_acta from acta_partido);
    if xCodigo_acta is null or xCodigo_acta = 0 
    then
		set xCodigo_acta := 1;
    else
		set xCodigo_acta := (select (max(codigo_acta)+1) from acta_partido);
    end if;
    INSERT INTO acta_partido
        (codigo_acta, fecha_emision_acta, hora_inicio_partido, hora_fin_partido, equipo_local, equipo_rival, duracion_partido,
        num_gol_equipo_local, num_gol_equipo_rival, equipo_ganador, create_at, delete_at, update_at, estado, partido_id_partido)

    VALUES
        (xCodigo_acta, xFecha_emision_acta, xHora_inicio_partido, xHora_fin_partido, xEquipo_local, xEquipo_rival, xDuracion_partido,
            xNum_gol_equipo_local, xNum_gol_equipo_rival, xEquipo_ganador, curdate(), NULL, NULL, xEstado, xPartido_id_partido);
END
$$
DELIMITER ;
CALL PR_insertar_acta_partido('2023-01-11', '12:22:00', '15:44:55', 'Ecuador', 'Venzuela', '01:33:33', 2, 4, 'Venezuela', 'A', 500);
-- 800
-- CALL PR_insertar_acta_partido(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_acta_partido;
DELIMITER $$
CREATE PROCEDURE PR_consultar_acta_partido(IN xId_acta_partido INT)
BEGIN
    SELECT *
    FROM acta_partido
    WHERE id_acta_partido = xId_acta_partido;
END
$$
DELIMITER ;
CALL PR_consultar_acta_partido
(800);
-- CALL PR_consultar_acta_partido(?);                   -> llamada en Java
-- GRID
DROP PROCEDURE IF EXISTS PR_consultar_acta_partido_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_acta_partido_()
BEGIN
    SELECT ac.id_acta_partido, ac.fecha_emision_acta, ac.hora_inicio_partido,
    ac.hora_fin_partido, ac.duracion_partido, ac.num_gol_equipo_local, ac.num_gol_equipo_rival,
    ac.partido_id_partido, l.id_club as id_club_l, l.nombre as nombre_local, r.id_club as id_club_r, r.nombre as nombre_rival,
    ac.equipo_ganador FROM ACTA_PARTIDO ac inner join partido p on ac.partido_id_partido = p.id_partido
    inner join club l on l.id_club = p.club_id_local
    inner join club r on r.id_club = p.club_id_rival where ac.estado <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_acta_partido;
DELIMITER $$
CREATE PROCEDURE PR_modificar_acta_partido(
    IN xId_acta_partido INT,
    IN xFecha_emision_acta DATE,
    IN xHora_inicio_partido TIME,
    IN xHora_fin_partido TIME,
    IN xEquipo_local VARCHAR
(45),       -- tal vez deberia omitirse ¿?
    IN xEquipo_rival VARCHAR
(45),       -- tal vez deberia omitirse ¿?
    IN xDuracion_partido TIME,
    IN xNum_gol_equipo_local INT,
    IN xNum_gol_equipo_rival INT,
    IN xEquipo_ganador VARCHAR
(45)
    )
BEGIN
    UPDATE acta_partido SET id_acta_partido = xId_acta_partido, fecha_emision_acta = xFecha_emision_acta, hora_inicio_partido = xHora_inicio_partido, 
    hora_fin_partido = xHora_fin_partido, equipo_local = xEquipo_local, equipo_rival = xEquipo_rival, duracion_partido = xDuracion_partido, 
    num_gol_equipo_local = xNum_gol_equipo_local, num_gol_equipo_rival = xNum_gol_equipo_rival, equipo_ganador = xEquipo_ganador, 
    update_at = curdate()
    WHERE id_acta_partido = xId_acta_partido;
END
$$
DELIMITER ;
CALL PR_modificar_acta_partido
(800,'2023-01-11', '12:22:00', '15:44:55', 'Ecuador', 'Venzuela', '01:33:33', 2, 4, 'Venezuela');
-- CALL PR_modificar_acta_partido(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_acta_partido;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_acta_partido(IN xId_acta_partido INT)
BEGIN
    UPDATE ACTA_PARTIDO SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_acta_partido = xId_acta_partido;
END
$$
DELIMITER ;
-- CALL PR_eliminar_acta_partido(800);
-- CALL PR_eliminar_acta_partido(?);                   -> llamada en Java

-- -----------------------------------------------------
-- PROCEDURE_CRUD_ASISTENCIA
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS PR_insertar_asistencia;
DELIMITER $$
CREATE PROCEDURE PR_insertar_asistencia(
    IN xPartido VARCHAR
(45),
    IN xLugar VARCHAR
(45),
    IN xFecha_encuentro DATE,
    IN xAsistencia TINYINT,
    IN xEstado VARCHAR
(2),
    IN xArbitro_id_arbitro INT
    )
BEGIN
    INSERT INTO asistencia
        (partido, lugar, fecha_encuentro, asistencia, estado, arbitro_id_arbitro)
    VALUES
        (xPartido, xLugar, xFecha_encuentro, xAsistencia, xEstado, xArbitro_id_arbitro);
END
$$
DELIMITER ;
CALL PR_insertar_asistencia
('Ecuadir VS Venezuela', 'BRASIL', '2023-01-25', 1, 'A', 300);
-- 900
-- CALL PR_insertar_asistencia(?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_asistencia;
DELIMITER $$
CREATE PROCEDURE PR_consultar_asistencia(IN xId_asistencia INT)
BEGIN
    SELECT *
    FROM asistencia
    WHERE id_asistencia = xId_asistencia;
END
$$
DELIMITER ;
CALL PR_consultar_asistencia
(900);
-- CALL PR_consultar_asistencia(?);                   -> llamada en Java

DROP PROCEDURE IF EXISTS PR_consultar_asistencia_;
DELIMITER $$
CREATE PROCEDURE PR_consultar_asistencia_()
BEGIN
    SELECT *
    FROM asistencia
    WHERE ESTADO <> 'E';
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS PR_modificar_asistencia;
DELIMITER $$
CREATE PROCEDURE PR_modificar_asistencia(
    IN xId_asistencia INT,
    IN xPartido VARCHAR
(45),
    IN xLugar VARCHAR
(45),
    IN xFecha_encuentro DATE,
    IN xAsistencia TINYINT,
    IN xArbitro_id_arbitro INT
    )
BEGIN
    UPDATE asistencia SET partido = xPartido, lugar = xLugar, fecha_encuentro = xFecha_encuentro, asistencia = xAsistencia, arbitro_id_arbitro = xArbitro_id_arbitro
    WHERE id_asistencia = xId_asistencia;
END
$$
DELIMITER ;
CALL PR_modificar_asistencia
(900, 'Ecuadir VS Venezuela', 'BRASIL', '2023-01-25', 1, 300);
-- CALL PR_modificar_asistencia(?, ?, ?, ?, ?, ?, ?);           -> llamada en Java

DROP PROCEDURE IF EXISTS PR_eliminar_asistencia;
DELIMITER $$
CREATE PROCEDURE PR_eliminar_asistencia(IN xId_asistencia INT)
BEGIN
    UPDATE ASISTENCIA SET ESTADO = 'E', DELETE_AT = curdate() WHERE id_asistencia = xId_asistencia;
END
$$
DELIMITER ;
-- CALL PR_eliminar_asistencia(900);
-- CALL PR_eliminar_asistencia(?);                   -> llamada en Java

-- sp combos 2023-02-01
DROP PROCEDURE IF EXISTS pr_combo_partidos;
DELIMITER $$
CREATE PROCEDURE pr_combo_partidos()
BEGIN
            select 0 id_partido, 'Seleccione' equipos_partido
    union
        SELECT p.id_partido, concat(l.nombre, ' VS ' , r.nombre) as equipos_partido
        FROM partido p inner join club l on p.club_id_local = l.id_club
            inner join club r on p.club_id_rival = r.id_club;
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS pr_combo_partidos_acta;
DELIMITER $$
CREATE PROCEDURE pr_combo_partidos_acta()
BEGIN
## Modificado por Flavio
## Se cambia seleccion para que ni incluya partidos que ya tienen acta 
   SELECT 
    p.id_partido,
    p.club_id_local,
    p.club_id_rival,
    l.nombre AS equipo_local,
    r.nombre AS equipo_rival
FROM
    partido p
        INNER JOIN
    club l ON p.club_id_local = l.id_club
        INNER JOIN
    club r ON p.club_id_rival = r.id_club
		INNER JOIN
	SORTEO S ON S.partido_id_partido = P.ID_PARTIDO
		left join 
	acta_partido ac on ac.partido_id_partido = p.id_partido 
    where ac.id_acta_partido is null and p.estado <> 'E' AND S.PARTIDO_ID_PARTIDO <> P.ID_PARTIDO;
END
$$
DELIMITER ;


DROP PROCEDURE IF EXISTS pr_combo_partidos_sorteo;
DELIMITER $$
CREATE PROCEDURE pr_combo_partidos_sorteo()
BEGIN
##
## Enlista los partidos que no tienen sorteo N = no tienen , S = si tienen
    SELECT 
    a.partido_id_partido,
    p.club_id_local,
    p.club_id_rival,
    l.nombre AS equipo_local,
    r.nombre AS equipo_rival
FROM
	PARTIDO P
		INNER JOIN 
    agenda a 
		ON
	A.partido_id_partido = P.ID_PARTIDO
        INNER JOIN
    club l ON p.club_id_local = l.id_club
        INNER JOIN
    club r ON p.club_id_rival = r.id_club
    where A.sorteado <> 'S' or a.sorteado is null AND A.ESTADO <> 'E';
END
$$
DELIMITER ;


-- consulta los partidos a los que pertenece un arbitro
DROP PROCEDURE IF EXISTS pr_consultar_arbitros_partidos;
DELIMITER $$
CREATE PROCEDURE pr_consultar_arbitros_partidos(
IN xId_arbitro INT
)
BEGIN
	SELECT
	A.PARTIDO_ID_PARTIDO as id_partido,
    A.LUGAR_PARTIDO,
    A.FECHA_PARTIDO,
    A.HORA_PARTIDO,
    p.partido_descripcion AS PARTIDO,
    S.id_sorteo,
    S.ARBITRO_ID_ARBITRO AS ID_ARBITRO,
    S.ARBITRO_ID_SUSTITUTO AS ID_ARBITRO_SUSTITUTO
FROM
	PARTIDO P
		INNER JOIN
	SORTEO S ON
    S.PARTIDO_ID_PARTIDO = P.ID_PARTIDO
		INNER JOIN 
	AGENDA A ON
	A.PARTIDO_ID_PARTIDO = s.PARTIDO_ID_PARTIDO
    WHERE A.SORTEADO = 'S' AND  (S.ARBITRO_ID_ARBITRO = xId_arbitro) AND A.ESTADO <> 'E';    
    
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS pr_consultar_asistencia_arbitro;
DELIMITER $$
CREATE PROCEDURE pr_consultar_asistencia_arbitro(
IN xId_arbitro INT
)
BEGIN
	SELECT
    A.ID_ASISTENCIA,
    A.PARTIDO,
    A.LUGAR,
    A.FECHA_ENCUENTRO AS FECHA,
    A.ASISTENCIA
	FROM
	ASISTENCIA A
    WHERE A.ARBITRO_ID_ARBITRO = xId_arbitro;
END
$$
DELIMITER ;

-- pr login
DROP PROCEDURE IF EXISTS pr_login;
DELIMITER $$
CREATE PROCEDURE pr_login(
in xUsuario varchar(50),
in xContrasenia varchar(50)
)
BEGIN
    
    if not exists (select * from usuario where nombre_usuario = xUsuario and contrasenia = xContrasenia)
    then
		select a.id_arbitro as id, a.nombre_usuario, a.email, a.rol_id_rol as id_rol, r.nombre_rol from arbitro a inner join rol r on r.id_rol = a.rol_id_rol where nombre_usuario = xUsuario and contrasenia = xContrasenia;
    else
		select u.id_usuario as id, u.nombre_usuario, u.email, u.rol_id_rol as id_rol, r.nombre_rol from usuario u inner join rol r on r.id_rol = u.rol_id_rol where nombre_usuario = xUsuario and contrasenia = xContrasenia;
    end if;
    
END
$$
DELIMITER ;



