/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/1/11 10:06:08                           */
/*==============================================================*/

drop index UNQ_USR_MDL_GROUP on USR_MDL_GROUP;
drop index UNQ_USR_MDL_MENU  on USR_MDL_MENU;
drop index UNQ_USR_ROLE      on USR_ROLE;
drop index IDX_USR_USER_NAME on USR_USER;

drop table if exists PLUGIN_INFO;
drop table if exists PLUGIN_RELATION;

drop table if exists USR_USER_ROLE;
drop table if exists USR_MDL_GROUP_MENU;
drop table if exists USR_MDL_GROUP_ROLE;
drop table if exists USR_MDL_GROUP;
drop table if exists USR_MDL_MENU;
drop table if exists USR_ROLE;
drop table if exists USR_USER;

/*==============================================================*/
/* Table: PLUGIN_INFO                                           */
/*==============================================================*/
create table PLUGIN_INFO
(
   CODE                 VARCHAR(30) not null comment '插件编码',
   NAME                 VARCHAR(100) not null comment '插件名称',
   VERSION              VARCHAR(10) not null comment '版本号',
   STATUS               CHAR(2) default '0' comment '状态',
   LEVEL                CHAR(2) default '1' comment '插件级别',
   SETUP_DATE           DATETIME not null comment '安装时间',
   UPLOAD_DATE          DATETIME comment '升级时间',
   primary key (CODE)
);

alter table PLUGIN_INFO comment '插件管理';

/*==============================================================*/
/* Table: PLUGIN_RELATION                                       */
/*==============================================================*/
create table PLUGIN_RELATION
(
   MAIN_PLUGIN_CODE     VARCHAR(30) comment '主插件CODE',
   RLTN_PLUGIN_CODE     VARCHAR(30) not null comment '被依赖的插件CODE',
   RLTN_PLUGIN_NAME     VARCHAR(100) not null comment '被依赖的插件名称',
   RLTN_PLUGIN_VERSION  VARCHAR(10) not null comment '被依赖的版本号'
);

alter table PLUGIN_RELATION comment '插件依赖关系';

/*==============================================================*/
/* Table: USR_MDL_GROUP                                         */
/*==============================================================*/
create table USR_MDL_GROUP
(
   CODE                 VARCHAR(30) not null comment '群组编码',
   NAME                 VARCHAR(100) not null comment '群组名称',
   SHORT_WORD           VARCHAR(30) not null comment '群组简称',
   REMARK               VARCHAR(500) comment '备注',
   PLUGIN_CODE          VARCHAR(30) not null comment '插件编码',
   ORDER_IDX            INTEGER(3) default 99 comment '排序',
   primary key (CODE)
);

alter table USR_MDL_GROUP comment '模块群组';

/*==============================================================*/
/* Index: UNQ_USR_MDL_GROUP                                     */
/*==============================================================*/
create unique index UNQ_USR_MDL_GROUP on USR_MDL_GROUP
(
   SHORT_WORD
);

/*==============================================================*/
/* Table: USR_MDL_GROUP_MENU                                    */
/*==============================================================*/
create table USR_MDL_GROUP_MENU
(
   MDLG_CODE            VARCHAR(30) comment '群组编码',
   MENU_CODE            VARCHAR(30) comment '菜单编码'
);

alter table USR_MDL_GROUP_MENU comment '模块群组与菜单对应关系';

/*==============================================================*/
/* Table: USR_MDL_GROUP_ROLE                                    */
/*==============================================================*/
create table USR_MDL_GROUP_ROLE
(
   MDLG_CODE            VARCHAR(30) comment '模块群组编码',
   ROLE_CODE            VARCHAR(30) comment '角色编码'
);

alter table USR_MDL_GROUP_ROLE comment '模块群组与角色对应关系';

/*==============================================================*/
/* Table: USR_MDL_MENU                                          */
/*==============================================================*/
create table USR_MDL_MENU
(
   CODE                 VARCHAR(30) not null comment '菜单编码',
   NAME                 VARCHAR(100) not null comment '菜单名称',
   SHORT_WORD           VARCHAR(30) not null comment '菜单简称',
   URL                  VARCHAR(200) comment '菜单URL',
   ICON_URL             VARCHAR(50) comment '菜单图标',
   PLUGIN_CODE          VARCHAR(30) not null comment '插件编码',
   ORDER_IDX            INTEGER(2) default 99 comment '排序',
   REMARK               VARCHAR(500) comment '备注',
   primary key (CODE)
);

alter table USR_MDL_MENU comment '模块菜单';

/*==============================================================*/
/* Index: UNQ_USR_MDL_MENU                                      */
/*==============================================================*/
create unique index UNQ_USR_MDL_MENU on USR_MDL_MENU
(
   SHORT_WORD
);

/*==============================================================*/
/* Table: USR_ROLE                                              */
/*==============================================================*/
create table USR_ROLE
(
   CODE                 VARCHAR(30) not null comment '角色编码',
   NAME                 VARCHAR(100) not null comment '角色名称',
   SHORT_WORD           VARCHAR(30) not null comment '角色简称',
   REMARK               VARCHAR(500) comment '备注',
   CAN_EDIT             TINYINT(1) default 1 comment '是否可编辑',
   CAN_DELET            TINYINT(1) default 1 comment '是否可删除',
   ORDER_IDX            INTEGER(3) default 99 comment '排序',
   primary key (CODE)
);

alter table USR_ROLE comment '角色表';

/*==============================================================*/
/* Index: UNQ_USR_ROLE                                          */
/*==============================================================*/
create unique index UNQ_USR_ROLE on USR_ROLE
(
   SHORT_WORD
);

/*==============================================================*/
/* Table: USR_USER                                              */
/*==============================================================*/
create table USR_USER
(
   ID                   INTEGER(10) not null comment 'ID',
   USER_NAME            VARCHAR(100) not null comment '用户名',
   REAL_NAME            VARCHAR(100) not null comment '真实姓名',
   PASSWORD             VARCHAR(100) not null comment '密码',
   SEX                  CHAR(2) comment '性别',
   MOBILE               VARCHAR(20) not null comment '手机',
   EMAIL                VARCHAR(100) not null comment '电邮',
   TELEPHONE            VARCHAR(20) comment '办公电话',
   ADDRESS              VARCHAR(200) comment '联系地址',
   POSTCODE             CHAR(10) comment '邮政编码',
   STATUS               CHAR(2) default '0' comment '状态',
   ADD_DATE             DATETIME not null comment '添加时间',
   EDIT_DATE            DATETIME  comment '修改时间',
   primary key (ID)
);

alter table USR_USER comment '用户基础信息';

/*==============================================================*/
/* Index: IDX_USR_USER_NAME                                     */
/*==============================================================*/
create index IDX_USR_USER_NAME on USR_USER
(
   USER_NAME
);

/*==============================================================*/
/* Table: USR_USER_ROLE                                         */
/*==============================================================*/
create table USR_USER_ROLE
(
   USER_ID              INTEGER(10) comment '用户ID',
   ROLE_CODE            VARCHAR(30) comment '角色编码'
);

alter table USR_USER_ROLE comment '用户与角色对应关系';

alter table PLUGIN_RELATION add constraint FK_FK_PLUGIN_RELATION foreign key (MAIN_PLUGIN_CODE)
      references PLUGIN_INFO (CODE) on delete cascade;

alter table USR_MDL_GROUP add constraint FK_PLUGIN_MDL_GROUP foreign key (PLUGIN_CODE)
      references PLUGIN_INFO (CODE) on delete cascade;

alter table USR_MDL_GROUP_MENU add constraint FK_USR_MDL_GROUP_MENU foreign key (MDLG_CODE)
      references USR_MDL_GROUP (CODE) on delete cascade;

alter table USR_MDL_GROUP_MENU add constraint FK_USR_MDL_MENU_GROUP foreign key (MENU_CODE)
      references USR_MDL_MENU (CODE) on delete cascade;

alter table USR_MDL_GROUP_ROLE add constraint FK_USR_MDL_GROUP_ROLE foreign key (MDLG_CODE)
      references USR_MDL_GROUP (CODE) on delete cascade;

alter table USR_MDL_GROUP_ROLE add constraint FK_USR_ROLE_MDL_GROUP foreign key (ROLE_CODE)
      references USR_ROLE (CODE) on delete cascade;

alter table USR_MDL_MENU add constraint FK_PLUGIN_MDL_MENU foreign key (PLUGIN_CODE)
      references PLUGIN_INFO (CODE) on delete cascade;

alter table USR_USER_ROLE add constraint FK_USR_ROLE_USER foreign key (ROLE_CODE)
      references USR_ROLE (CODE) on delete cascade;

alter table USR_USER_ROLE add constraint FK_USR_USER_ROLE foreign key (USER_ID)
      references USR_USER (ID) on delete cascade;

