
/*==============================================================*/
/* Table: LOG                                                   */
/*==============================================================*/
create table LOG (
   ID                   SERIAL               not null,
   ENTIDAD              TEXT                 null,
   FECHA                DATE                 null,
   VALOR                TEXT                 null,
   USUARIO              VARCHAR(10)          null,
   constraint PK_LOG primary key (ID)
);

/*==============================================================*/
/* Index: LOG_PK                                                */
/*==============================================================*/
create unique index LOG_PK on LOG (
ID
);

/*==============================================================*/
/* Table: PRODUCTO                                              */
/*==============================================================*/
create table PRODUCTO (
   ID                   SERIAL               not null,
   TIPO                 INT4                 null,
   DESCRIPCION          TEXT                 null,
   CODIGO               TEXT                 null,
   ESTADO               BOOL                 null,
   constraint PK_PRODUCTO primary key (ID)
);

/*==============================================================*/
/* Index: PRODUCTO_PK                                           */
/*==============================================================*/
create unique index PRODUCTO_PK on PRODUCTO (
ID
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on PRODUCTO (
TIPO
);

/*==============================================================*/
/* Table: TIPO                                                  */
/*==============================================================*/
create table TIPO (
   ID                   SERIAL               not null,
   NOMBRE               TEXT                 null,
   DESCRIPCION          TEXT                 null,
   PADRE                INT4                 null,
   constraint PK_TIPO primary key (ID)
);

/*==============================================================*/
/* Index: TIPO_PK                                               */
/*==============================================================*/
create unique index TIPO_PK on TIPO (
ID
);

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_1_FK on TIPO (
PADRE
);

/*==============================================================*/
/* Table: TRACKING                                              */
/*==============================================================*/
create table TRACKING (
   ID                   SERIAL               not null,
   PRODUCTO             INT4                 null,
   FECHA                DATE                 null,
   TIPO                 CHAR(2)              null,
   USUARIO              CHAR(10)             null,
   VALOR                INT4                 null,
   SALDO                INT4                 null,
   constraint PK_TRACKING primary key (ID)
);

/*==============================================================*/
/* Index: TRACKING_PK                                           */
/*==============================================================*/
create unique index TRACKING_PK on TRACKING (
ID
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   ID                   SERIAL               not null,
   NOMBRE               TEXT                 null,
   APELLIDOS            TEXT                 null,
   USUARIO              TEXT                 null,
   PWD                  TEXT                 null,
   constraint PK_USUARIO primary key (ID)
);

/*==============================================================*/
/* Index: USUARIO_PK                                            */
/*==============================================================*/
create unique index USUARIO_PK on USUARIO (
ID
);

alter table PRODUCTO
   add constraint FK_PRODUCTO_RELATIONS_TIPO foreign key (TIPO)
      references TIPO (ID)
      on delete restrict on update restrict;

alter table TIPO
   add constraint FK_TIPO_RELATIONS_TIPO foreign key (PADRE)
      references TIPO (ID)
      on delete restrict on update restrict;

alter table TRACKING
   add constraint FK_TRACKING_RELATIONS_PRODUCTO foreign key (PRODUCTO)
      references PRODUCTO (ID)
      on delete restrict on update restrict;
