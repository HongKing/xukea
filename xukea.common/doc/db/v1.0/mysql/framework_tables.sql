/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/10/22 11:59:03                          */
/*==============================================================*/

/*==============================================================*/
/* Table: MSG_EMAIL_SEND                                        */
/*==============================================================*/
create table MSG_EMAIL_SEND
(
   ID                   INTEGER(10) not null comment 'ID',
   SUBJECT              VARCHAR(100) not null comment '邮件标题',
   CONTENT              VARCHAR(1000) not null comment '邮件内容',
   TOADDR               VARCHAR(50) not null comment '收件人',
   ATTACHMENT_IDS       VARCHAR(50) comment '附件IDS',
   SEND_TIME            DATETIME not null comment '发送时间',
   primary key (ID)
);

alter table MSG_EMAIL_SEND comment '已发邮件';

/*==============================================================*/
/* Table: MSG_EMAIL_WAIT                                        */
/*==============================================================*/
create table MSG_EMAIL_WAIT
(
   ID                   INTEGER(10) not null comment 'ID',
   SUBJECT              VARCHAR(100) not null comment '邮件标题',
   CONTENT              VARCHAR(1000) not null comment '邮件内容',
   TOADDR               VARCHAR(50) not null comment '收件人',
   ATTACHMENT_IDS       VARCHAR(50) comment '附件IDS',
   primary key (ID)
);

alter table MSG_EMAIL_WAIT comment '待发邮件';

/*==============================================================*/
/* Table: SYS_ATTACHMENT                                        */
/*==============================================================*/
create table SYS_ATTACHMENT
(
   ID                   INTEGER(10) not null comment 'ID',
   FILE_NAME            VARCHAR(50) not null comment '文件名称',
   FILE_URL             VARCHAR(200) not null comment '存放地址',
   FILE_SIZE            FLOAT(10,2) not null comment '文件大小',
   FILE_TYPE            VARCHAR(5) comment '文件类型',
   REMARK               VARCHAR(500) comment '备注',
   UPLOAD_USER          INTEGER(10) comment '上传人ID',
   UPLOAD_DATE          DATETIME comment '上传日期',
   primary key (ID)
);

alter table SYS_ATTACHMENT comment '附件信息表';

/*==============================================================*/
/* Table: SYS_ERROR_LOG                                         */
/*==============================================================*/
create table SYS_ERROR_LOG
(
   ERR_TIME             DATETIME not null comment '错误日期',
   ERR_LEVEL            VARCHAR(10) comment '错误级别',
   ERR_FROM             VARCHAR(100) comment '错误来源',
   ERR_VALUE            VARCHAR(500) comment '对象值(obj.toString())',
   MSG_SHORT            VARCHAR(500) comment '消息简介',
   MSG_ALL              VARCHAR(1000) comment '消息详情'
);

alter table SYS_ERROR_LOG comment '系统错误日志';

/*==============================================================*/
/* Index: IDX_SYS_ERROR_LOG                                     */
/*==============================================================*/
create index IDX_SYS_ERROR_LOG on SYS_ERROR_LOG
(
   ERR_TIME,
   ERR_FROM
);

/*==============================================================*/
/* Table: SYS_SEQUENCE                                          */
/*==============================================================*/
create table SYS_SEQUENCE
(
   TAB_NAME             VARCHAR(50) not null comment '表名',
   NEXT_ID              INTEGER(10) comment '下一个值',
   primary key (TAB_NAME)
);

alter table SYS_SEQUENCE comment '系统序列';

/*==============================================================*/
/* Table: SYS_SETTINGS                                          */
/*==============================================================*/
create table SYS_SETTINGS
(
   NAME                 VARCHAR(30) not null comment '键',
   VALUE                VARCHAR(30) comment '值',
   REMARK               VARCHAR(100) comment '备注',
   CAN_EDIT             TINYINT(1) default 1 comment '可编辑',
   CAN_DELETE           TINYINT(1) default 1 comment '可删除',
   EDIT_DATE            DATETIME comment '更新时间',
   primary key (NAME)
);
 
alter table SYS_SETTINGS comment '系统设置';

