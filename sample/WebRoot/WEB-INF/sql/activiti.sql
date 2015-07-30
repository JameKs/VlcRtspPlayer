/*
 * 自定义activiti用户表
 * */
DROP TABLE ACT_ID_MEMBERSHIP
CREATE VIEW ACT_ID_MEMBERSHIP AS
select USER_ID AS USER_ID_ ,GW_ID AS GROUP_ID_ from T_JXJY_GW_USER;

DROP TABLE ACT_ID_USER
CREATE VIEW ACT_ID_USER AS
SELECT ID AS ID_,
       '' AS REV_,
       U.USER_NAME AS first_,
       U.USER_NAME AS LAST_,
       EMAIL AS email_,
       PASSWORD AS pwd_,
       '' AS picture_id_
  FROM T_JXJY_USER U;
  
DROP TABLE ACT_ID_GROUP;
CREATE VIEW ACT_ID_GROUP AS
SELECT ID AS ID_,
       '' AS REV_,
       GW_MC AS NAME_,
       '' AS TYPE_
from T_JXJY_GW;
/**
 * 查看我的待办
 */
select count(distinct RES.ID_) 
from ACT_RU_TASK RES 
left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ 
WHERE 
(
RES.ASSIGNEE_ = ? 
or 
(
	RES.ASSIGNEE_ is null 
		and (
			I.USER_ID_ = ? 
			or 
			I.GROUP_ID_ IN (select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = ? )
		) 
) 
) 
--Parameters: 3(String), 3(String), 3(String)