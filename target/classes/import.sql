create database db_pais;
create table pais (
    id serial not null,
    nombre varchar(255) not null,
    sigla varchar(2) not null,
    moneda varchar(50) not null,
    primary key (id)
);

create table departamento (
    id serial not null,
    nombre varchar(255) not null,
    id_pais int not null,
    poblacion BIGINT not null,
    primary key (id),
    foreign key (id_pais) references pais(id)
);

create table ciudad (
    id serial not null,
    nombre varchar(255) not null,
    id_departamento int not null,
    capital boolean not null,
    primary key (id),
    foreign key (id_departamento) references departamento(id)
);

create table rol (
                     id serial not null,
                     nombre varchar(255) not null,
                     habilitado boolean not null,
                     primary key (id)
);

create table usuario (
    id serial not null,
    nombreusuario varchar(20) not null,
    nombre varchar(255) not null,
    apellido varchar(255) not null,
    email varchar(255) not null,
    clave varchar(255) not null,
    habilitado boolean not null,
    rol_id int not null,
    primary key (id),
    foreign key (rol_id) references rol(id)
);

insert into pais (nombre, sigla, moneda) values ('Colombia', 'CO', 'Peso');
insert into departamento (nombre, id_pais, poblacion) values ('Cundinamarca', 1, 1000000);
insert into ciudad (nombre, id_departamento) values ('Bogota', 1);
insert into rol (nombre, habilitado) values ('Administrador', true);
insert into usuario (nombre, apellido, email, clave, habilitado, rol_id) values ('Diana', 'Plata', 'dianacplata@gmail.com', '123456', true, 1);


