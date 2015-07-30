-- Create table
create table ACL_ENTRY
(
  id                  VARCHAR2(32) default SYS_GUID() not null,
  acl_object_identity VARCHAR2(32),
  ace_order           NUMBER(10),
  sid                 VARCHAR2(32),
  mask                NUMBER(19),
  granting            NUMBER(1),
  audit_success       NUMBER(5),
  audit_failure       NUMBER(10)
);
-- Add comments to the table 
comment on table ACL_ENTRY
  is 'ACL授权表';
-- Add comments to the columns 
comment on column ACL_ENTRY.id
  is '主键ID';
comment on column ACL_ENTRY.acl_object_identity
  is '受保护域对象的实例ID(外键关联ACL_OBJECT_IDENTITY)';
comment on column ACL_ENTRY.ace_order
  is '用于ACE集合排序';
comment on column ACL_ENTRY.sid
  is '操作主体(外键关联ACL_SID.ID)';
comment on column ACL_ENTRY.mask
  is '表示一个整数位掩码，表示操作权限的掩码';
comment on column ACL_ENTRY.granting
  is '0-权限无效,1-权限按MASK';
comment on column ACL_ENTRY.audit_success
  is '审计预留字段';
comment on column ACL_ENTRY.audit_failure
  is '审计预留字段';


-- Create table
create table ACL_SID
(
  id        VARCHAR2(32) default SYS_GUID() not null,
  principal NUMBER(1),
  sid       VARCHAR2(32)
);
-- Add comments to the table 
comment on table ACL_SID
  is 'ACL授权角色表';
-- Add comments to the columns 
comment on column ACL_SID.id
  is '主键ID';
comment on column ACL_SID.principal
  is '授权的类型（0-代表按角色授权;1-代表按用户授权）';
comment on column ACL_SID.sid
  is '表示一个被认证对象。记录一个用户名(USERBASE.NAME)或角色名(ROLES.NAME) 。如果按角色认证，SID就是对应的角色，如果按用户名认证，SID就是对应的用户名。用来表示是否使用文本显示引用的主体名或一个GrantedAuthority';

-- Create/Recreate indexes 
create unique index IDX_ACL_SID_U1 on ACL_SID (SID, PRINCIPAL);



-- Create table
create table ACL_OBJECT_IDENTITY
(
  id                 VARCHAR2(32) default SYS_GUID() not null,
  object_id_identity VARCHAR2(32),
  object_id_class    VARCHAR2(32),
  parent_object      VARCHAR2(32),
  owner_sid          VARCHAR2(32),
  entries_inheriting NUMBER(1)
);
-- Add comments to the table 
comment on table ACL_OBJECT_IDENTITY
  is 'ACL标识领域对象表(为系统中每个唯一的领域对象实例保存信息)';
-- Add comments to the columns 
comment on column ACL_OBJECT_IDENTITY.id
  is '主键ID';
comment on column ACL_OBJECT_IDENTITY.object_id_identity
  is '受保护域对象的实例ID';
comment on column ACL_OBJECT_IDENTITY.object_id_class
  is '受保护的域对象类名ID(外键关联ACL_CLASS.ID)';
comment on column ACL_OBJECT_IDENTITY.parent_object
  is '当前ACL的父ACL';
comment on column ACL_OBJECT_IDENTITY.owner_sid
  is '域对象实例的拥有者(外键关联ACL_SID.ID)';
comment on column ACL_OBJECT_IDENTITY.entries_inheriting
  is '当前ACL是否继承父ACL,0-不继承,1-继承';
-- Create/Recreate indexes 
create unique index IDX_ACL_OBJECT_IDE_U1 on ACL_OBJECT_IDENTITY (OBJECT_ID_CLASS, OBJECT_ID_IDENTITY);

-- Create table
create table ACL_CLASS
(
  id        VARCHAR2(32) default SYS_GUID() not null,
  class     VARCHAR2(200)
);
-- Add comments to the table 
comment on table ACL_CLASS
  is 'ACL领域对象表';
-- Add comments to the columns 
comment on column ACL_CLASS.id
  is '主键ID';
comment on column ACL_CLASS.class
  is '所要操作的域对象类型，它是域对象类型的全限定名。其中，class是所要操作的域对象类型，它是域对象类型的全限定名。只有在此表中的CLASS，才被准许进行ACL授权及验证';
-- Create/Recreate indexes 
create unique index IDX_ACL_CLASS_U1 on ACL_CLASS (CLASS);
