--ALTER TABLE recibo_pago_cliente ADD COLUMN monto_recibo_efectivo NUMERIC(14,0) DEFAULT 0;
drop table dato_banco;
CREATE TABLE "dato_banco" (
	"iddato_banco" INTEGER NOT NULL ,
	"titular" TEXT NOT NULL ,
	"documento" TEXT NOT NULL ,
	"nro_cuenta" TEXT NOT NULL ,
	"activo" BOOLEAN NOT NULL ,
	"fk_idbanco" INTEGER NOT NULL ,
	PRIMARY KEY("iddato_banco")
);
drop table transaccion_banco;
CREATE TABLE "transaccion_banco" (
	"idtransaccion_banco" INTEGER NOT NULL ,
	"fecha_creado" TIMESTAMP NOT NULL ,
	"nro_transaccion" TEXT NOT NULL ,
	"monto" NUMERIC(14,0) NOT NULL ,
	"observacion" TEXT NOT NULL ,
	"concepto" TEXT NOT NULL ,
	"fk_iddato_banco" INTEGER NOT NULL ,
	"fk_iddato_banco_cliente" INTEGER NOT NULL ,
	"fk_idrecibo_pago_cliente" INTEGER NOT NULL ,
	PRIMARY KEY("idtransaccion_banco")
);
drop table recibo_pago_cliente;
CREATE TABLE "recibo_pago_cliente" (
	"idrecibo_pago_cliente" INTEGER NOT NULL ,
	"fecha_emision" TIMESTAMP NOT NULL ,
	"descripcion" TEXT NOT NULL ,
	"monto_recibo_pago" NUMERIC(14,0) NOT NULL ,
	"monto_letra" TEXT NOT NULL ,
	"estado" TEXT NOT NULL ,
	"forma_pago" TEXT NOT NULL ,
	"monto_recibo_efectivo" NUMERIC(14,0) NOT NULL ,
	"monto_recibo_tarjeta" NUMERIC(14,0) NOT NULL ,
	"monto_recibo_transferencia" NUMERIC(14,0) NOT NULL ,
	"fk_idcliente" INTEGER NOT NULL ,
	"fk_idusuario" INTEGER NOT NULL ,
	PRIMARY KEY("idrecibo_pago_cliente")
);
drop table caja_detalle_alquilado;
CREATE TABLE "caja_detalle_alquilado" (
	"idcaja_detalle_alquilado" INTEGER NOT NULL ,
	"fecha_emision" TIMESTAMP NOT NULL ,
	"descripcion" TEXT NOT NULL ,
	"tabla_origen" TEXT NOT NULL ,
	"estado" TEXT NOT NULL ,
	"cierre" CHAR(2) NOT NULL ,
	"monto_alquilado_efectivo" NUMERIC(14,0) NOT NULL ,
	"monto_alquilado_tarjeta" NUMERIC(14,0) NOT NULL ,
	"monto_alquilado_transferencia" NUMERIC(14,0) NOT NULL ,
	"monto_alquilado_credio" NUMERIC(14,0) NOT NULL ,
	"monto_recibo_pago" NUMERIC(14,0) NOT NULL ,
	"monto_delivery" NUMERIC(14,0) NOT NULL ,
	"monto_gasto" NUMERIC(14,0) NOT NULL ,
	"monto_vale" NUMERIC(14,0) NOT NULL ,
	"monto_compra_contado" NUMERIC(14,0) NOT NULL ,
	"monto_compra_credito" NUMERIC(14,0) NOT NULL ,
	"monto_apertura_caja" NUMERIC(14,0) NOT NULL ,
	"monto_cierre_caja" NUMERIC(14,0) NOT NULL ,
	"monto_recibo_efectivo" NUMERIC(14,0) NOT NULL ,
	"monto_recibo_tarjeta" NUMERIC(14,0) NOT NULL ,
	"monto_recibo_transferencia" NUMERIC(14,0) NOT NULL ,
	"fk_idgasto" INTEGER NOT NULL ,
	"fk_idcompra" INTEGER NOT NULL ,
	"fk_idventa_alquiler" INTEGER NOT NULL ,
	"fk_idvale" INTEGER NOT NULL ,
	"fk_idrecibo_pago_cliente" INTEGER NOT NULL ,
	"fk_idusuario" INTEGER NOT NULL ,
	PRIMARY KEY("idcaja_detalle_alquilado")
);

CREATE TABLE "dato_banco_cliente" (
	"iddato_banco_cliente" INTEGER NOT NULL ,
	"titular" TEXT NOT NULL ,
	"documento" TEXT NOT NULL ,
	"nro_cuenta" TEXT NOT NULL ,
	"activo" BOOLEAN NOT NULL ,
	"fk_idbanco" INTEGER NOT NULL ,
	PRIMARY KEY("iddato_banco_cliente")
);
CREATE TABLE "item_banco_cliente" (
	"iditem_banco_cliente" INTEGER NOT NULL ,
	"fk_iddato_banco_cliente" INTEGER NOT NULL ,
	"fk_idcliente" INTEGER NOT NULL ,
	PRIMARY KEY("iditem_banco_cliente")
);
--2022-11-06
ALTER TABLE venta_alquiler ADD COLUMN fk_idtipo_evento INTEGER DEFAULT 0;
ALTER TABLE venta_alquiler ADD COLUMN monto_sena NUMERIC(14,0) DEFAULT 0;
ALTER TABLE venta_alquiler ADD COLUMN monto_letra TEXT DEFAULT 'cero';
CREATE TABLE "tipo_evento" (
	"idtipo_evento" INTEGER NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"activar" BOOLEAN NOT NULL ,
	PRIMARY KEY("idtipo_evento")
);
--1