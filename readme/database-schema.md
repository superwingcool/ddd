### codecheck数据库
#####C_PROJECTS  任务表
| Column | DataType    |Description | 
| ------------- |:-------------:| -----:|
|ID       | INT(11) | 主键自增id ||
| PROJECT_NAME      | VARCHAR(1024)      |   任务名字 |
| CURRENT_SNAPSHOT_ID | INT(11)      | snapshot id |
|  PROJECT_UUID|VARCHAR(50)|任务对应的UUID|
|CREATED_AT|DATETIME|任务创建时间|
|PROJECT_NAME_KEY|VARCHAR(50)|？ 待确认|
|SCM_ADDR|VARCHAR(2000)|代码库地址|
|BRANCH|VARCHAR(2000)|代码库分支|
|LANGUAGE|VARCHAR(2000)|语言|
|FILEPATH|VARCHAR(2000)|文件路径 ？|
|REMARK1|VARCHAR(255)|-|
|REMARK2|VARCHAR(255)|-|

#####C_ISSUES  问题表
| Column | DataType    |Description | 
| ------------- |:-------------:| -----:|
|ID       | BIGINT(20) | 主键自增id ||
| RULE_ID      | INT(11)      |   对应的规则id |
| SEVERITY | TINYINT(1)      |  |
|  ISSUE_KEY|VARCHAR(50)|？|
|MESSAGE|VARCHAR(4000)|?|
|MESSAGE_HASH|VARCHAR(50)|？|
|LINE|INT(11)|?|
|LINE_HASH|VARCHAR(50)|?|
|STATUS|TINYINT(1)|0:未解决 1：已解决|
|MANUAL_STATUS|TINYINT(1)|0：未忽略 2：手动忽略|
|TAGS|VARCHAR(4000)|？|
|ISSUE_UUID|VARCHAR(50)|issue 的uuid|
|FILE_UUID|VARCHAR(50)|？|
|SNAPSHOT_ID|INT(11)|对应的snapshot的id|
|PROJECT_UUID|VARCHAR(50)|对应的project的uuid|
|CREATED_AT|DATETIME|创建时间|
|FIXED_AT|DATETIME|修改时间|
|REMARK1|VARCHAR(255)|-|
|REMARK2|VARCHAR(255)|-|

#####C_RULES  规则表
| Column | DataType    |Description | 
| ------------- |:-------------:| -----:|
|ID       | INT(11) | 主键自增id ||
| RULE_ID      | INT(11)      |   对应的规则id |
| PLUGIN_NAME     | VARCHAR(255)      |   插件名字|
| PLUGIN_RULE_KEY | VARCHAR(200)      | ？ |
| NAME|VARCHAR(200)|规则名字|
| NAME_CHINESE|VARCHAR(200)|规则中文名字|
|DESCRIPTION|LONGTEXT|规则藐视|
|DESCRIPTION_CHINESE|LONGTEXT|规则中文描述|
|PRIORITY|INT(11)|规则级别|
|SYSTEM_TAGS|VARCHAR(4000)|规则标签|
|LANGUAGE|VARCHAR(20)|?规则语言|
|STATUS|TINYINT(1)|0:未解决 1：已解决|
|MANUAL_STATUS|TINYINT(1)|0：未忽略 2：手动忽略|
|TAGS|VARCHAR(4000)|？|
|ISSUE_UUID|VARCHAR(50)|issue 的uuid|
|FILE_UUID|VARCHAR(50)|？|
|SNAPSHOT_ID|INT(11)|对应的snapshot的id|
|PROJECT_UUID|VARCHAR(50)|对应的project的uuid|
|CREATED_AT|DATETIME|创建时间|
|UPDATED_AT|DATETIME|修改时间|
|STATUS|VARCHAR(40)|规则所处的状态ON／OFF|
|CATEGORY_BIG|VARCHAR(255)|规则分类|
|CATEGORY_SMALL|VARCHAR(255)|规则分类|
|RULE_SET|VARCHAR(255)|规则集|
|JENKINS_PLUGIN_NAME|VARCHAR(255)|jenkins插件名字|
|REMARK1|VARCHAR(255)|-|
|REMARK2|VARCHAR(255)|-|

#####C_SNAPSHOTS  
| Column | DataType    |Description | 
| ------------- |:-------------:| -----:|
|ID       | INT(11) | 主键自增id ||
| PROJECT_ID      | INT(11)      |   对应的project id |
| COMMIT_START_TIME     | DATETIME      |  任务检查开始时间|
| COMMIT_END_TIME     | DATETIME      |  任务检查结束时间|
| PROGRESS | VARCHAR(64)      | ？ |
| SCM_ADDR|VARCHAR(2000)|代码库地址|
| SCM_BRANCH|VARCHAR(200)|代码库分支|
|SCM_REVISION|VARCHAR(50)|代码库commit的revision号|
|SCM_KEY|VARCHAR(50)|？|
|REMARK1|VARCHAR(255)|-|
|REMARK2|VARCHAR(255)|-|

#####T_JENKINS_JOB_INFO  
| Column | DataType    |Description | 
| ------------- |:-------------:| -----:|
|ID       | INT(11) | 主键自增id ||
| PROJECT_NAME      | VARCHAR(200)      |   对应的project id |
| GIT_URL     | VARCHAR(200)      |  任务检查开始时间|
| PROJECT_ID     | VARCHAR(200)    |  任务检查结束时间|
| JOB_NAME | VARCHAR(64)      | ？ |
| VIEW_NAME | VARCHAR(128)      | ？ |
| JOB_TYPE | VARCHAR(64)      | ？ |
| BRANCH_NAME | VARCHAR(64)      | ？ |
| JOB_TRIGGER_TIME | DATETIME      | ？ |
| TASK_MISSION | VARCHAR(200)      | ？ |
|REMARK1|VARCHAR(255)|-|
|REMARK2|VARCHAR(255)|-|
|SUB_DIR|VARCHAR(255)|-|
|CREDENTIAL_TITLE|VARCHAR(255)|-|
|BUILD_LANCHER|VARCHAR(255)|-|
|TEMPLATE_ID|VARCHAR(120)|-|
|RULE_INFO|LONGTEXT|-|
|JOB_BUILD_NODE|VARCHAR(200)|-|
