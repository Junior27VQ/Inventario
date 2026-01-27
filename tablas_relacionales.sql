--crear tablas en orden de la dependencia del pk (fk)
--borrar la tabla de categorias si existe
drop table if exists detalle_pedido;--tiene fk(2)
drop table if exists detalle_venta;--tiene fk(2)
drop table if exists cabecera_pedido;--tiene fk(2)
drop table if exists historial_stock;--tiene fk(1)
drop table if exists proveedores;--tiene fk(1)
drop table if exists productos;--si tiene fk(2)
drop table if exists unidades_medidas;--si tiene fk(1)
drop table if exists categoria_unidad_medida;--no tiene fk
drop table if exists categorias;--tiene fk en la misma tabla
drop table if exists estado_pedido;--tiene fk(0)
drop table if exists tipo_documento;--tiene fk(0)
drop table if exists cabecera_ventas;--tiene fk(0)

--*********************************************************
--crear tablas en orden del pk y luego las fk
--drop table if exists categorias;
create table categorias(
	codigo_cat serial not null primary key,
	nombre varchar(100) not null,
	categoria_padre int references categorias(codigo_cat) 
);
insert into categorias(nombre,categoria_padre)
values('Materia Prima',null),
		('Proteina',1),
		('Salsa',1),
		('Punto de venta',null),
		('Bebidas',4),
		('Con alcohol',5),
		('Sin alcohol',5);
select * from categorias;

--drop table if exists categoria_unidad_medida;
create table categoria_unidad_medida(
	codigo_cudm char(1) not null primary key,
	nombre varchar(100) not null
);
insert into categoria_unidad_medida(codigo_cudm,nombre)
values('U','Unidades'),
	  ('V','Volumen'),
	  ('P','Peso'),
	  ('L','Longitud');
select * from categoria_unidad_medida;

--drop table if exists unidades_medidas;
create table unidades_medidas(
	codigo_udm char(2) not null primary key,
	descripcion varchar(100) not null,
	categoria_udm char(1) not null references categoria_unidad_medida(codigo_cudm)
);
insert into unidades_medidas(codigo_udm,descripcion,categoria_udm)
values('ml','milimetro','V'),
	  ('l','litros','V'),
	  ('u','unidad','U'),
	  ('d','decena','U'),
	  ('g','gramos','P'),
	  ('kg','kilogramos','P'),
	  ('lb','libras','P');
select * from unidades_medidas;

--drop table if exists productos;
create table productos(
	codigo_pro serial not null primary key,
	nombre varchar(100) not null,
	udm char(2) not null references unidades_medidas(codigo_udm),
	precio_venta money not null,
	tiene_iva boolean not null,
	costo money not null,
	categoria int not null references categorias(codigo_cat),
	stock int not null
);
insert into productos(nombre,udm,precio_venta,tiene_iva,costo,categoria,stock)
values('CocaCola','u',0.5804,true,0.3729,7,105),
	  ('Salsa de tomate','kg',0.95,true,0.8736,3,0),
	  ('Mostaza','kg',0.95,true,0.89,3,0),
	  ('Fuze tea','u',0.8,true,0.7,7,49);
select * from productos;

--parte 2
--drop table if exists tipo_documento;
create table tipo_documento(
	codigo_dcm char(1) not null primary key,
	descripcion varchar(10) not null
);
insert into tipo_documento(codigo_dcm,descripcion)
values('C','CEDULA'),
	  ('R','RUC');
select * from tipo_documento;

--drop table if exists estado_pedido;
create table estado_pedido(
	codigo_std char(1) not null primary key,
	descripcion varchar(100) not null
);
insert into estado_pedido(codigo_std,descripcion)
values('S','Solicitado'),
	  ('R','Recibido');
select * from estado_pedido;

--drop table if exists proveedores;
create table proveedores(
	identificador char(13) not null primary key,
	tipo_documento char(1) not null references tipo_documento(codigo_dcm),
	nombre varchar(100) not null,
	telefono char(10) not null,
	correo varchar(100) not null,
	direccion varchar(100) not null
);
insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion)
values('1792285747','C','SANTIAGO MOSQUERA','0992920306','zantycb89@gmail.com','Cumbayork'),
	  ('1792285747001','R','SNACKS SA','0992920398','sanacks@gmail.com','Loja');
select * from proveedores;

--drop table if exists cabecera_pedido;
create table cabecera_pedido(
	numero serial not null primary key,
	proveedor varchar(13) not null references proveedores(identificador),
	fecha date not null,
	estado char(1) not null references estado_pedido(codigo_std)
);
insert into cabecera_pedido(proveedor,fecha,estado)
values('1792285747','30/11/2023','R'),
	  ('1792285747001','01/12/2024','R');
select * from cabecera_pedido;

--parte3
--drop table if exists cabecera_ventas;
create table cabecera_ventas(
	codigo_cv serial not null primary key,
	fecha timestamp without time zone not null,
	costo money not null,
	iva money not null,
	total money not null
);
insert into cabecera_ventas(fecha,costo,iva,total)
values('20/11/2023 19:59',3.26,0.39,3.65);
select * from cabecera_ventas;

--drop table if exists historial_stock;
create table historial_stock(
	codigo_hst serial not null primary key,
	fecha timestamp without time zone not null,
	referencia varchar(100) not null,
	producto int not null references productos(codigo_pro),
	cantidad int not null
);
insert into historial_stock(fecha,referencia,producto,cantidad)
values('20/11/2023 19:59','PEDIDO1',1,100),
	  ('20/11/2023 19:59','PEDIDO1',4,50),
	  ('20/11/2023 20:00','PEDIDO1',1,10),
	  ('20/11/2023 20:00','VENTA1',1,-5),
	  ('20/11/2023 20:00','VENTA',4,1);
select * from historial_stock;

--drop table if exists detalle_venta;
create table detalle_venta(
	codigo_vnt serial not null primary key,
	cabecera_vnt int not null references cabecera_ventas(codigo_cv),
	producto int not null references productos(codigo_pro),
	cantidad int not null,
	precio money not null,
	subtotal money not null,
	subtotal_iva money not null
);
insert into detalle_venta(cabecera_vnt,producto,cantidad,precio,subtotal,subtotal_iva)
values(1,1,5,0.58,2.9,3.25),
	  (1,4,1,0.36,0.36,0.4);
select * from detalle_venta;
--drop table if exists detalle_pedido;
create table detalle_pedido(
	codigo_dp serial not null primary key,
	cabecera_pdd int not null references cabecera_pedido(numero),
	producto int not null references productos(codigo_pro),
	cantidad_solicitada int not null,
	subtotal money not null,
	cantidad_recibida int not null
);
insert into detalle_pedido(cabecera_pdd,producto,cantidad_solicitada,subtotal,cantidad_recibida)
values(1,1,100,37.29,100),
	  (1,4,50,11.8,50),
	  (2,1,10,3.73,10);
select * from detalle_pedido;