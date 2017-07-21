
/*==============================================================*/
/* Table: CODIGOS                                               */
/*==============================================================*/
create table CODIGOS (
   ID                   SERIAL               not null,
   MAESTRO              INT4                 null,
   NOMBRE               TEXT                 null,
   CODIGO               TEXT                 null,
   DESCRIPCION          TEXT                 null,
   PARAMETROS           TEXT                 null,
   ESTADO               BOOL                 null,
   constraint PK_CODIGOS primary key (ID)
);

/*==============================================================*/
/* Index: CODIGOS_PK                                            */
/*==============================================================*/
create unique index CODIGOS_PK on CODIGOS (
ID
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_3_FK on CODIGOS (
MAESTRO
);

/*==============================================================*/
/* Table: DETALLEFACTURA                                        */
/*==============================================================*/
create table DETALLEFACTURA (
   ID                   SERIAL               not null,
   FACTURA              INT4                 null,
   PRODUCTO             INT4                 null,
   CANTIDAD             INT4                 null,
   constraint PK_DETALLEFACTURA primary key (ID)
);

/*==============================================================*/
/* Index: DETALLEFACTURA_PK                                     */
/*==============================================================*/
create unique index DETALLEFACTURA_PK on DETALLEFACTURA (
ID
);

/*==============================================================*/
/* Index: RELATIONSHIP_5_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_5_FK on DETALLEFACTURA (
FACTURA
);

/*==============================================================*/
/* Index: RELATIONSHIP_7_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_7_FK on DETALLEFACTURA (
PRODUCTO
);

/*==============================================================*/
/* Table: ENTIDAD                                               */
/*==============================================================*/
create table ENTIDAD (
   ID                   SERIAL               not null,
   RAZON_SOCIAL         TEXT                 null,
   REPRESENTANTE        TEXT                 null,
   CORREO               TEXT                 null,
   PIN                  TEXT                 null,
   ESTADO               BOOL                 null,
   TIPO                 CHAR(2)              null,
   constraint PK_ENTIDAD primary key (ID)
);

/*==============================================================*/
/* Index: ENTIDAD_PK                                            */
/*==============================================================*/
create unique index ENTIDAD_PK on ENTIDAD (
ID
);

/*==============================================================*/
/* Table: FACTURA                                               */
/*==============================================================*/
create table FACTURA (
   IDF                  SERIAL               not null,
   CLIENTE              INT4                 null,
   NUMERO               TEXT                 null,
   SUBTOTAL             DECIMAL(10,6)        null,
   IVA                  DECIMAL(2,2)         null,
   VALOR_IVA            DECIMAL(6,4)         null,
   TOTAL                DECIMAL(6,4)         null,
   FECHA                DATE                 null,
   USUARIO              TEXT                 null,
   constraint PK_FACTURA primary key (IDF)
);

/*==============================================================*/
/* Index: FACTURA_PK                                            */
/*==============================================================*/
create unique index FACTURA_PK on FACTURA (
IDF
);

/*==============================================================*/
/* Index: RELATIONSHIP_6_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_6_FK on FACTURA (
CLIENTE
);

/*==============================================================*/
/* Table: GRUPOUSUARIO                                          */
/*==============================================================*/
create table GRUPOUSUARIO (
   ID                   SERIAL               not null,
   USUARIO              TEXT                 null,
   TIPO                 CHAR(2)              null,
   ESTADO               BOOL                 null,
   constraint PK_GRUPOUSUARIO primary key (ID)
);

/*==============================================================*/
/* Index: GRUPOUSUARIO_PK                                       */
/*==============================================================*/
create unique index GRUPOUSUARIO_PK on GRUPOUSUARIO (
ID
);

/*==============================================================*/
/* Table: MAESTRO                                               */
/*==============================================================*/
create table MAESTRO (
   ID                   SERIAL               not null,
   CODIGO               TEXT                 null,
   NOMBRE               TEXT                 null,
   constraint PK_MAESTRO primary key (ID)
);

/*==============================================================*/
/* Index: MAESTRO_PK                                            */
/*==============================================================*/
create unique index MAESTRO_PK on MAESTRO (
ID
);

/*==============================================================*/
/* Table: TRACKING                                              */
/*==============================================================*/
create table TRACKING (
   ID                   SERIAL               not null,
   PROVEEDOR            INT4                 null,
   PRODUCTO             INT4                 null,
   USUARIO              TEXT                 null,
   CANTIDAD             INT4                 null,
   SALDO                INT4                 null,
   TIPO                 CHAR(2)              null,
   FECHA                DATE                 null,
   SERIAL               TEXT                 null,
   VALOR                DECIMAL(6,4)         null,
   constraint PK_TRACKING primary key (ID)
);

/*==============================================================*/
/* Index: TRACKING_PK                                           */
/*==============================================================*/
create unique index TRACKING_PK on TRACKING (
ID
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on TRACKING (
PROVEEDOR
);

/*==============================================================*/
/* Index: RELATIONSHIP_4_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_4_FK on TRACKING (
PRODUCTO
);

alter table CODIGOS
   add constraint FK_CODIGOS_RELATIONS_MAESTRO foreign key (MAESTRO)
      references MAESTRO (ID)
      on delete restrict on update restrict;

alter table DETALLEFACTURA
   add constraint FK_DETALLEF_RELATIONS_FACTURA foreign key (FACTURA)
      references FACTURA (IDF)
      on delete restrict on update restrict;

alter table DETALLEFACTURA
   add constraint FK_DETALLEF_RELATIONS_TRACKING foreign key (PRODUCTO)
      references TRACKING (ID)
      on delete restrict on update restrict;

alter table FACTURA
   add constraint FK_FACTURA_RELATIONS_ENTIDAD foreign key (CLIENTE)
      references ENTIDAD (ID)
      on delete restrict on update restrict;

alter table TRACKING
   add constraint FK_TRACKING_RELATIONS_ENTIDAD foreign key (PROVEEDOR)
      references ENTIDAD (ID)
      on delete restrict on update restrict;

alter table TRACKING
   add constraint FK_TRACKING_RELATIONS_CODIGOS foreign key (PRODUCTO)
      references CODIGOS (ID)
      on delete restrict on update restrict;
