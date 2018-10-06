createdb agenda_mvc;

psql agenda_mvc;

CREATE TABLE contactos( 
    id_contacto serial NOT NULL PRIMARY KEY,
    nombre varchar(50) NOT NULL,
    email varchar(50)  NOT NULL
);


INSERT INTO contactos (nombre, email) VALUES 
('Dejah Thoris','dejah@barson.ma'), 
('Jhon Carter','jhon@barson.ma'),
('Carthoris Carter','carthoris@barson.ma');

ALTER TABLE contactos
ADD COLUMN teléfono varchar(13);

UPDATE contactos set teléfono = '775-117-33-38' WHERE id_contacto = 1;
UPDATE contactos set teléfono = '775-120-98-77' WHERE id_contacto = 2;
UPDATE contactos set teléfono = '775-221-17-35' WHERE id_contacto = 3;
UPDATE contactos set teléfono = '442-105-54-55' WHERE id_contacto = 12;

SELECT * FROM contactos;