/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 8.0.27 : Database - study_blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`study_blog` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `study_blog`;

/*Table structure for table `dm_article` */

DROP TABLE IF EXISTS `dm_article`;

CREATE TABLE `dm_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '文章内容',
  `summary` varchar(1024) DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint DEFAULT '0' COMMENT '访问量',
  `is_comment` char(1) DEFAULT '1' COMMENT '是否允许评论 1是，0否',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';

/*Data for the table `dm_article` */

insert  into `dm_article`(`id`,`title`,`content`,`summary`,`category_id`,`thumbnail`,`is_top`,`status`,`view_count`,`is_comment`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`) values (1,'SpringSecurity从入门到精通','## 课程介绍\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 简介1\n\n​	**Spring Security** 是 Spring 家族中的一个安全管理框架。相比与另外一个安全框架**Shiro**，它提供了更丰富的功能，社区资源也比Shiro丰富。\n\n​	一般来说中大型的项目都是使用**SpringSecurity** 来做安全框架。小项目有Shiro的比较多，因为相比与SpringSecurity，Shiro的上手更加的简单。\n\n​	 一般Web应用的需要进行**认证**和**授权**。\n\n​		**认证：验证当前访问系统的是不是本系统的用户，并且要确认具体是哪个用户**\n\n​		**授权：经过认证后判断当前用户是否有权限进行某个操作**\n\n​	而认证和授权也是SpringSecurity作为安全框架的核心功能。\n\n\n\n## 1. 快速入门\n\n### 1.1 准备工作\n\n​	我们先要搭建一个简单的SpringBoot工程\n\n① 设置父工程 添加依赖\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n② 创建启动类\n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n③ 创建Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 引入SpringSecurity\n\n​	在SpringBoot项目中使用SpringSecurity我们只需要引入依赖即可实现入门案例。\n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n​	引入依赖后我们在尝试去访问之前的接口就会自动跳转到一个SpringSecurity的默认登陆页面，默认用户名是user,密码会输出在控制台。\n\n​	必须登陆之后才能对接口进行访问。\n\n\n\n## 2. 认证\n\n### 2.1 登陆校验流程\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)','SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权',1,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png','1','0',107,'0',NULL,'2022-01-23 23:20:11',NULL,NULL,0),(2,'Swagger2','## 简介\n\n- `OpenAPI`规范(以前称为 Swagger规范)是针对 REST API 的 API 描述格式，OpenAPI 文件允许你描述你的整个 API；\n- `Swagger`是围绕 OpenAPI 规范构建的一组开源工具，可以帮助设计、构建、记录和使用 REST API；\n\n## 使用\n\n- pom依赖\n\n```XML\n<dependency>\n    <groupId>io.springfox</groupId>\n    <artifactId>springfox-swagger2</artifactId>\n</dependency>\n<dependency>\n    <groupId>io.springfox</groupId>\n    <artifactId>springfox-swagger-ui</artifactId>\n</dependency>\n```\n- 在启动类上或者配置类加 `@EnableSwagger2`注解激活；\n- 访问地址：`http://ip:port/swagger-ui.html`；\n- Controller配置\n    - `@Api`\n        - `tags`设置标签\n        - `description`设置描述信息\n- 接口配置\n    - `@ApiOperation`\n- 接口普通参数配置\n    - `@ApiImplicitParams`多个参数\n    - `@ApiImplicitParam`单个参数\n- 实体类配置\n    - `@ApiModel`实体类\n    - `@ApiModelProperty`实体类属性\n- 文档信息配置\n\n```Java\n@Configuration\npublic class SwaggerConfig {\n    @Bean\n    public Docket customDocket() {\n        return new Docket(DocumentationType.SWAGGER_2)\n                .apiInfo(apiInfo())\n                .select()\n                .apis(RequestHandlerSelectors.basePackage(\"com.sangeng.controller\"))\n                .build();\n    }\n\n    private ApiInfo apiInfo() {\n        Contact contact = new Contact(\"团队名\", \"http://www.my.com\", \"my@my.com\");\n        return new ApiInfoBuilder()\n                .title(\"文档标题\")\n                .description(\"文档描述\")\n                .contact(contact)   // 联系方式\n                .version(\"1.1.0\")  // 版本\n                .build();\n    }\n}\n```\n\n','Swagger2入门使用！好用的接口管理工具',1,'http://rjbnc5vri.hn-bkt.clouddn.com/2022/10/15/62f21a2699f54fc782da5d1cfd60adff.png','0','0',0,'0',1,'2022-10-14 14:48:53',1,'2022-10-15 02:57:01',0),(3,'Docker','## 介绍\n\n![image.png](http://rjbnc5vri.hn-bkt.clouddn.com/2022/10/15/133dfc264e294754b07e4bfa88e00018.png)\n\n- `Docker`是基于Go语言实现的云开源项目，解决了运行环境和配置问题的软件容器，方便做持续集成并有助于整体发布的容器虚拟化技术，能够实现系统平滑移植；\n- `Docker`使用客户机-服务器架构；`Docker 客户机`与 `Docker 守护进程`进行对话，后者负责构建、运行和分发 Docker 容器的繁重工作，Docker 客户机和守护进程可以在同一个系统上运行，或者可以将 Docker 客户端连接到远程 Docker 守护进程；Docker 客户机和守护进程使用 REST API、 UNIX 套接字或网络接口进行通信；另一个 Docker 客户端是 `Docker Compose`，它允许您处理由一组容器组成的应用程序；\n- 容器与传统虚拟机技术比较\n    - 传统虚拟机是带环境安装的一种解决方案，需要模拟出一套完整的计算机软件以及硬件环境，缺点是资源占用多、冗余步骤多、启动慢；\n    - 容器是与系统其他部分隔离开的一系列进程，不是模拟一个完整的操作系统而是对进程进行隔离，从另一个镜像运行，并由该镜像提供支持进程所需的全部文件；容器不需要捆绑一整套操作系统，只需要软件工作所需的库资源和设置，复用本地主机的操作系统，启动速度块、占用体积小；\n\n- `Docker`比`VM`虚拟机快的原因\n  - Docker有着比虚拟机更少的抽象层\n      - 由于Docker不需要Hypervisor(虚拟机)实现硬件资源虚拟化，运行在Docker容器上的程序直接使用的都是实际物理机的硬件资源，因此在CPU、内存利用率上Docker将会在效率上有明显优势；\n  - docker利用的是宿主机的内核,而不需要加载操作系统OS内核\n      - 当新建一个容器时，Docker不需要和虚拟机一样重新加载一个操作系统内核，进而避免引寻、加载操作系统内核返回等比较费时费资源的过程，当新建一个虚拟机时，虚拟机软件需要加载OS，返回新建过程是分钟级别的，而Docker由于直接利用宿主机的操作系统，则省略了返回过程，因此新建一个Docker容器只需要几秒钟；\n\n- `Docker`基本组成\n    - 镜像\n        - 镜像是一个只读的模板，可以用来创建 Docker 容器；\n    - 容器\n        - 容器是镜像运行时的实体，为镜像提供了一个标准的和隔离的运行环境，它可以被启动、开始、停止、删除；每个容器都是相互隔离的、保证安全的平台，可以把容器看做是一个简易版的 Linux 环境（包括root用户权限、进程空间、用户空间和网络空间等）和运行在其中的应用程序；\n    - 仓库\n        - 仓库（`Repository`）是集中存放镜像文件的地方；\n\n## 安装\n  - Docker依赖于已存在并运行的Linux内核环境，实质上是在已经运行的Linux下制造了一个隔离的文件环境；\n  - 阿里云镜像仓库地址：`https://download.docker.com/linux/centos/docker-ce.repo`；\n  - 根据官网进行安装Docker Engine；\n  - 可以使用阿里云镜像服务进行镜像加速；\n\n## 相关指令\n\n  #### 帮助启动类命令\n\n|☞令|作用|\n|-|-|\n|systemctl start/restart docker|启动/重启docker|\n|systemctl stop docker|停止docker|\n|systemctl status docker|查看docker状态|\n|docker version|查看版本信息|\n|docker info|查看docker概要信息|\n|docker [具体命令] --help|查看docker帮助文档|\n\n\n  #### 镜像命令\n\n|☞令|作用|OPTIONS||\n|-|-|-|-|\n|docker images|列出本地主机上的镜像|-a：列出本地所有镜像|-q：只显示镜像ID|\n|docker search|查看镜像|--limit：只列出n个镜像||\n|docker pull 镜像名字[:TAG]|下载镜像|||\n|docker system df\n|查看镜像/容器/数据卷所占空间|||\n|docker rmi 镜像ID|删除镜像|-f：强制删除|$(docker images -qa)：所有镜像|\n\n\n  - 虚悬镜像：仓库名、标签都为<none>；\n\n  #### 容器命令\n\n|☞令|作用|OPTIONS||||||\n|-|-|-|-|-|-|-|-|\n|docker run [options] image [command] [arg...]|启动容器|--name|-d：后台运行容器并返回容器ID|-i：以交互模式运行容器|-t：为容器重新分配一个伪输入终端|-P/p: 随机/指定端口映射|/bin/bash |\n|docker ps [options]|列出当前所有正在运行的容器|-l：显示最近创建的容器|-a：列出当前所有正在运行的容器+历史上运行过的|-n：显示最近n个创建的容器|-q :静默模式，只显示容器编号|||\n|docker start|启动容器|||||||\n|docker restart|重启容器|||||||\n|docker stop|停止容器|||||||\n|docker kill|强制停止容器|||||||\n|docker rm|删除容器|||||||\n|docker logs|查看容器ID|||||||\n|docker top|查看容器内运行的进程|||||||\n|docker inspect|查看容器内部细节|||||||\n|docker exec -it 容器ID  /bin/bash|进入正在运行的容器的新的终端并以命令行交互|||||||\n|docker attach 容器ID|直接进入容器启动命令的终端|||||||\n|docker cp  容器ID:容器内路径 目的主机路径|从容器内拷贝文件到主机上|||||||\n|cat 文件名.tar | docker import - 镜像用户/镜像名:镜像版本号 |导入容器|||||||\n|docker export 容器ID > 文件名.tar|导出容器|||||||\n\n\n## 镜像\n  - 一种轻量级、可执行的独立软件包，它包含运行某个软件所需的所有内容，我们把应用程序和配置依赖打包好形成一个可交付的运行环境(包括代码、运行时需要的库、环境变量和配置文件等)，这个打包好的运行环境就是`image`镜像文件；\n  - `UnionFS`（联合文件系统）：UnionFS是一种分层、轻量级并且高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下，UnionFS是 Docker 镜像的基础，镜像可以通过分层来进行继承，基于基础镜像可以制作各种具体的应用镜像；\n  - Docker镜像加载原理\n      - Docker的镜像实际上由一层一层的文件系统组成，这种层级的文件系统UnionFS；`bootfs`(boot file system)主要包含`bootloader`和`kernel`, `bootloader`主要是引导加载`kernel`, Linux刚启动时会加载`bootfs`文件系统，在Docker镜像的最底层是引导文件系统`bootfs`；这一层与我们典型的Linux/Unix系统是一样的，包含boot加载器和内核，当boot加载完成之后整个内核就都在内存中了，此时内存的使用权已由`bootfs`转交给内核，此时系统也会卸载`bootfs`；\n  - 镜像分层优势\n      - 共享资源，方便复制迁移，能够很好地复用；\n  - 提交容器副本形成镜像：`docker commit -m=\"提交的描述信息\" -a=\"作者\" 容器ID 要创建的目标镜像名:[标签]`\n\n## 本地镜像推送入库\n  - 阿里云：`容器镜像服务 → 实例列表 → 个人实例`\n  - 私有库\n      - `Docker Registry`是官方提供的工具，可以用于构建私有镜像仓库；\n      - 下载镜像`Docker Registry`；\n      - 默认情况，仓库被创建在容器的`/var/lib/registry`目录下，建议自行用容器卷映射，方便于宿主机联调；\n      - 运行：`docker run -d -p 5000:5000  -v /tmp/myregistry/:/tmp/registry --privileged=true registry`\n      - 查看私服库上的镜像：`curl -XGET http://localhost:5000/v2/_catalog`；\n      - 上传格式：`docker tag 镜像:Tag Host:Port/Repository:Tag`\n      - 配置私服库配置文件支持http\n          - `vim /etc/docker/daemon.json`\n\n```JSON\n{\n  \"registry-mirrors\": [\"https://aa25jngu.mirror.aliyuncs.com\"],\n  \"insecure-registries\": [\"192.168.111.162:5000\"]\n}\n```\n      - 镜像拉取：`docker pull ``Host:Port/Repository:Tag`\n\n## Docker容器数据卷\n  - 容器数据卷就是目录或文件，存在于一个或多个容器中，由Docker挂载到容器，但不属于联合文件系统，因此能够绕过`UnionFS`提供一些用于持续存储或共享数据的特性；\n  - 卷的设计目的就是数据的持久化，完全独立于容器的生存周期，因此Docker不会在容器删除时删除其挂载的数据卷；\n  - 特点：\n      - 数据卷可在容器之间共享或重用数据；\n      - 卷中的更改可以直接实时生效；\n      - 数据卷中的更改不会包含在镜像的更新中；\n      - 数据卷的生命周期一直持续到没有容器使用它为止；\n      - 容器和宿主机之间数据共享；\n  - 扩大容器的权限：`--privileged=true`\n  - 使用：`docker run -it --privileged=true -v /宿主机绝对路径:/容器内目录 镜像名`\n  - 数据卷读写规则映射\n      - `读写（rw）`（默认）\n          - `docker run -it --privileged=true -v /宿主机绝对路径目录:/容器内目录:rw 镜像名`\n      - `只读（ro）`\n          - 容器内只能读取不能写入文件，宿主机可读可写\n  - 数据卷的继承和共享\n      - `docker run -it --privileged=true --volumes-from 父类 镜像名`\n\n## Docker安装软件注意\n  - 总体步骤\n      - 搜索镜像\n      - 拉取镜像\n      - 查看镜像\n      - 启动镜像\n      - 停止容器\n      - 移除容器\n  - `Tomcat10`需要将`webapps.dist`文件夹改为`webapps`；\n  - `MySQL`安装\n\n```text\ndocker run -d -p 3306:3306 --name some-mysql --privileged=true \n-v /my/own/log:/var/log/mysql \n-v /my/own/datadir:/var/lib/mysql \n-v /my/own/conf:/etc/mysql/conf.d \n-e MYSQL_ROOT_PASSWORD=my-secret-pw  mysql:tag\n```\n      - 在conf中新建`my.cnf`\n\n```text\n[client]\ndefault_character_set=utf8\n[mysqld]\ncollation_server = utf8_general_ci\ncharacter_set_server = utf8\n```\n  - `Redis`需要修改配置`redis.conf`文件；\n\n  ','Docker初级够用版',1,'http://rjbnc5vri.hn-bkt.clouddn.com/2022/10/15/d5e0c6bcf3bb4a3e9a2a72da1dcbf51c.png','1','0',0,'0',1,'2022-10-15 03:06:51',1,'2022-10-15 03:09:29',0),(4,'weq','adadaeqe','adad',2,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png','1','0',22,'0',NULL,'2022-01-21 14:58:30',NULL,NULL,1);

/*Table structure for table `dm_article_tag` */

DROP TABLE IF EXISTS `dm_article_tag`;

CREATE TABLE `dm_article_tag` (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `tag_id` bigint NOT NULL DEFAULT '0' COMMENT '标签id',
  PRIMARY KEY (`article_id`,`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签关联表';

/*Data for the table `dm_article_tag` */

insert  into `dm_article_tag`(`article_id`,`tag_id`) values (1,4),(2,1),(2,4),(3,4),(3,5),(8,6),(11,6),(12,6),(13,1),(13,5);

/*Table structure for table `dm_category` */

DROP TABLE IF EXISTS `dm_category`;

CREATE TABLE `dm_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '分类名',
  `pid` bigint DEFAULT '-1' COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';

/*Data for the table `dm_category` */

insert  into `dm_category`(`id`,`name`,`pid`,`description`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`) values (1,'JAVA',-1,'JAVA相关资讯','0',NULL,NULL,NULL,NULL,0),(2,'MYSQL',-1,'数据库相关资讯','0',NULL,NULL,NULL,NULL,0),(15,'Spring',-1,'Spring相关资讯','0',NULL,NULL,NULL,NULL,0);

/*Table structure for table `dm_comment` */

DROP TABLE IF EXISTS `dm_comment`;

CREATE TABLE `dm_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  `root_id` bigint DEFAULT '-1' COMMENT '根评论id',
  `content` varchar(512) DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint DEFAULT '-1' COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint DEFAULT '-1' COMMENT '回复目标评论id',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

/*Data for the table `dm_comment` */

insert  into `dm_comment`(`id`,`type`,`article_id`,`root_id`,`content`,`to_comment_user_id`,`to_comment_id`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`) values (1,'0',1,-1,'asS',-1,-1,1,'2022-01-29 07:59:22',1,'2022-01-29 07:59:22',0),(2,'0',1,-1,'[哈哈]SDAS',-1,-1,1,'2022-01-29 08:01:24',1,'2022-01-29 08:01:24',0),(3,'0',1,-1,'是大多数',-1,-1,1,'2022-01-29 16:07:24',1,'2022-01-29 16:07:24',0),(4,'0',1,-1,'撒大声地',-1,-1,1,'2022-01-29 16:12:09',1,'2022-01-29 16:12:09',0),(5,'0',1,-1,'你再说什么',-1,-1,1,'2022-01-29 18:19:56',1,'2022-01-29 18:19:56',0),(6,'0',1,-1,'hffd',-1,-1,1,'2022-01-29 22:13:52',1,'2022-01-29 22:13:52',0),(9,'0',1,2,'你说什么',1,2,1,'2022-01-29 22:18:40',1,'2022-01-29 22:18:40',0),(10,'0',1,2,'哈哈哈哈[哈哈]',1,9,1,'2022-01-29 22:29:15',1,'2022-01-29 22:29:15',0),(11,'0',1,2,'we全文',1,10,3,'2022-01-29 22:29:55',1,'2022-01-29 22:29:55',0),(12,'0',1,-1,'王企鹅',-1,-1,1,'2022-01-29 22:30:20',1,'2022-01-29 22:30:20',0),(13,'0',1,-1,'什么阿是',-1,-1,1,'2022-01-29 22:30:56',1,'2022-01-29 22:30:56',0),(14,'0',1,-1,'新平顶山',-1,-1,1,'2022-01-29 22:32:51',1,'2022-01-29 22:32:51',0),(15,'0',1,-1,'2222',-1,-1,1,'2022-01-29 22:34:38',1,'2022-01-29 22:34:38',0),(16,'0',1,2,'3333',1,11,1,'2022-01-29 22:34:47',1,'2022-01-29 22:34:47',0),(17,'0',1,2,'回复weqedadsd',3,11,1,'2022-01-29 22:38:00',1,'2022-01-29 22:38:00',0),(18,'0',1,-1,'sdasd',-1,-1,1,'2022-01-29 23:18:19',1,'2022-01-29 23:18:19',0),(19,'0',1,-1,'111',-1,-1,1,'2022-01-29 23:22:23',1,'2022-01-29 23:22:23',0),(20,'0',1,1,'你说啥？',1,1,1,'2022-01-30 10:06:21',1,'2022-01-30 10:06:21',0),(21,'0',1,-1,'友链添加个呗',-1,-1,1,'2022-01-30 10:06:50',1,'2022-01-30 10:06:50',0),(22,'1',1,-1,'友链评论2',-1,-1,1,'2022-01-30 10:08:28',1,'2022-01-30 10:08:28',0),(23,'1',1,22,'回复友链评论3',1,22,1,'2022-01-30 10:08:50',1,'2022-01-30 10:08:50',0),(24,'1',1,-1,'友链评论4444',-1,-1,1,'2022-01-30 10:09:03',1,'2022-01-30 10:09:03',0),(25,'1',1,22,'收到的',1,22,1,'2022-01-30 10:13:28',1,'2022-01-30 10:13:28',0),(26,'0',1,-1,'sda',-1,-1,1,'2022-01-30 10:39:05',1,'2022-01-30 10:39:05',0),(27,'0',1,1,'说你咋地',1,20,14787164048662,'2022-01-30 17:19:30',14787164048662,'2022-01-30 17:19:30',0),(28,'0',1,1,'sdad',1,1,14787164048662,'2022-01-31 11:11:20',14787164048662,'2022-01-31 11:11:20',0),(29,'0',1,-1,'你说是的ad',-1,-1,14787164048662,'2022-01-31 14:10:11',14787164048662,'2022-01-31 14:10:11',0),(30,'0',1,1,'撒大声地',1,1,14787164048662,'2022-01-31 20:19:18',14787164048662,'2022-01-31 20:19:18',0),(33,'0',1,-1,'[互粉][给力][发红包]',-1,-1,2,'2022-10-12 11:20:58',2,'2022-10-12 11:20:58',0),(34,'0',5,-1,'哇哦~~~',-1,-1,2,'2022-10-12 11:22:23',2,'2022-10-12 11:22:23',0);

/*Table structure for table `dm_link` */

DROP TABLE IF EXISTS `dm_link`;

CREATE TABLE `dm_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `logo` varchar(256) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL COMMENT '网站地址',
  `status` char(1) DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='友链';

/*Data for the table `dm_link` */

insert  into `dm_link`(`id`,`name`,`logo`,`description`,`address`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`) values (1,'百度','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','sda','https://www.baidu.com','1',NULL,'2022-01-13 08:25:47',NULL,'2022-01-13 08:36:14',0),(2,'QQ','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','dada','https://www.qq.com','1',NULL,'2022-01-13 09:06:10',NULL,'2022-01-13 09:07:09',0),(3,'淘宝','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','da','https://www.taobao.com','1',NULL,'2022-01-13 09:23:01',NULL,'2022-01-13 09:23:01',0);

/*Table structure for table `dm_tag` */

DROP TABLE IF EXISTS `dm_tag`;

CREATE TABLE `dm_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '标签名',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签';

/*Data for the table `dm_tag` */

insert  into `dm_tag`(`id`,`name`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`remark`) values (1,'JAVA',1,NULL,1,'2022-10-15 03:04:05',0,''),(2,'c',1,'2022-01-11 09:20:55',1,'2022-10-15 03:04:08',0,''),(3,'c++',1,'2022-01-11 09:21:07',1,'2022-10-15 03:04:10',0,''),(4,'mysql',1,'2022-01-13 15:22:43',1,'2022-10-15 03:04:01',0,''),(5,'默认',1,'2022-01-13 15:22:47',1,'2022-10-15 03:03:58',0,''),(6,'spring',1,'2022-10-15 03:03:50',1,'2022-10-15 03:03:50',0,NULL);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2034 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`menu_type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`,`del_flag`) values (1,'系统管理',0,1,'system',NULL,1,'M','0','0','','system',0,'2021-11-12 10:46:19',0,NULL,'系统管理目录','0'),(100,'用户管理',1,1,'user','system/user/index',1,'C','0','0','system:user:list','user',0,'2021-11-12 10:46:19',1,'2022-07-31 15:47:58','用户管理菜单','0'),(101,'角色管理',1,2,'role','system/role/index',1,'C','0','0','system:role:list','peoples',0,'2021-11-12 10:46:19',0,NULL,'角色管理菜单','0'),(102,'菜单管理',1,3,'menu','system/menu/index',1,'C','0','0','system:menu:list','tree-table',0,'2021-11-12 10:46:19',0,NULL,'菜单管理菜单','0'),(1001,'用户查询',100,1,'','',1,'F','0','0','system:user:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1002,'用户新增',100,2,'','',1,'F','0','0','system:user:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1003,'用户修改',100,3,'','',1,'F','0','0','system:user:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1004,'用户删除',100,4,'','',1,'F','0','0','system:user:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1005,'用户导出',100,5,'','',1,'F','0','0','system:user:export','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1006,'用户导入',100,6,'','',1,'F','0','0','system:user:import','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1007,'重置密码',100,7,'','',1,'F','0','0','system:user:resetPwd','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1008,'角色查询',101,1,'','',1,'F','0','0','system:role:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1009,'角色新增',101,2,'','',1,'F','0','0','system:role:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1010,'角色修改',101,3,'','',1,'F','0','0','system:role:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1011,'角色删除',101,4,'','',1,'F','0','0','system:role:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1012,'角色导出',101,5,'','',1,'F','0','0','system:role:export','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1013,'菜单查询',102,1,'','',1,'F','0','0','system:menu:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1014,'菜单新增',102,2,'','',1,'F','0','0','system:menu:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1015,'菜单修改',102,3,'','',1,'F','0','0','system:menu:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1016,'菜单删除',102,4,'','',1,'F','0','0','system:menu:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(2017,'内容管理',0,4,'content',NULL,1,'M','0','0','','table',NULL,'2022-01-08 02:44:38',1,'2022-07-31 12:34:23','','0'),(2018,'分类管理',2017,1,'category','content/category/index',1,'C','0','0','content:category:list','example',NULL,'2022-01-08 02:51:45',NULL,'2022-01-08 02:51:45','','0'),(2019,'文章管理',2017,0,'article','content/article/index',1,'C','0','0','content:article:list','build',NULL,'2022-01-08 02:53:10',NULL,'2022-01-08 02:53:10','','0'),(2021,'标签管理',2017,6,'tag','content/tag/index',1,'C','0','0','content:tag:index','button',NULL,'2022-01-08 02:55:37',NULL,'2022-01-08 02:55:50','','0'),(2022,'友链管理',2017,4,'link','content/link/index',1,'C','0','0','content:link:list','404',NULL,'2022-01-08 02:56:50',NULL,'2022-01-08 02:56:50','','0'),(2023,'写博文',0,0,'write','content/article/write/index',1,'C','0','0','content:article:writer','build',NULL,'2022-01-08 03:39:58',1,'2022-07-31 22:07:05','','0'),(2024,'友链新增',2022,0,'',NULL,1,'F','0','0','content:link:add','#',NULL,'2022-01-16 07:59:17',NULL,'2022-01-16 07:59:17','','0'),(2025,'友链修改',2022,1,'',NULL,1,'F','0','0','content:link:edit','#',NULL,'2022-01-16 07:59:44',NULL,'2022-01-16 07:59:44','','0'),(2026,'友链删除',2022,1,'',NULL,1,'F','0','0','content:link:remove','#',NULL,'2022-01-16 08:00:05',NULL,'2022-01-16 08:00:05','','0'),(2027,'友链查询',2022,2,'',NULL,1,'F','0','0','content:link:query','#',NULL,'2022-01-16 08:04:09',NULL,'2022-01-16 08:04:09','','0'),(2028,'导出分类',2018,1,'',NULL,1,'F','0','0','content:category:export','#',NULL,'2022-01-21 07:06:59',NULL,'2022-01-21 07:06:59','','0');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COMMENT='角色信息表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`role_key`,`role_sort`,`status`,`del_flag`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,'超级管理员','admin',1,'0','0',0,'2021-11-12 10:46:19',0,NULL,'超级管理员'),(2,'普通角色','common',2,'0','0',0,'2021-11-12 10:46:19',0,'2022-01-01 22:32:58','普通角色'),(3,'嘎嘎嘎','aggag',5,'0','0',NULL,'2022-01-06 14:07:40',NULL,'2022-01-07 03:48:48','嘎嘎嘎'),(12,'友链审核员','link',1,'0','0',NULL,'2022-01-16 06:49:30',NULL,'2022-01-16 08:05:09',NULL);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色和菜单关联表';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (0,0),(2,1),(2,102),(2,1013),(2,1014),(2,1015),(2,1016),(2,2000),(3,100),(3,101),(3,103),(3,104),(3,105),(3,106),(3,107),(3,108),(3,109),(3,110),(3,111),(3,112),(3,113),(3,114),(3,115),(3,116),(3,500),(3,501),(3,1001),(3,1002),(3,1003),(3,1004),(3,1005),(3,1006),(3,1007),(3,1008),(3,1009),(3,1010),(3,1011),(3,1012),(3,1017),(3,1018),(3,1019),(3,1020),(3,1021),(3,1022),(3,1023),(3,1024),(3,1025),(3,1026),(3,1027),(3,1028),(3,1029),(3,1030),(3,1031),(3,1032),(3,1033),(3,1034),(3,1035),(3,1036),(3,1037),(3,1038),(3,1039),(3,1040),(3,1041),(3,1042),(3,1043),(3,1044),(3,1045),(3,1046),(3,1047),(3,1048),(3,1049),(3,1050),(3,1051),(3,1052),(3,1053),(3,1054),(3,1055),(3,1056),(3,1057),(3,1058),(3,1059),(3,1060),(3,2000),(11,1),(11,100),(11,101),(11,102),(11,103),(11,104),(11,105),(11,106),(11,107),(11,108),(11,500),(11,501),(11,1001),(11,1002),(11,1003),(11,1004),(11,1005),(11,1006),(11,1007),(11,1008),(11,1009),(11,1010),(11,1011),(11,1012),(11,1013),(11,1014),(11,1015),(11,1016),(11,1017),(11,1018),(11,1019),(11,1020),(11,1021),(11,1022),(11,1023),(11,1024),(11,1025),(11,1026),(11,1027),(11,1028),(11,1029),(11,1030),(11,1031),(11,1032),(11,1033),(11,1034),(11,1035),(11,1036),(11,1037),(11,1038),(11,1039),(11,1040),(11,1041),(11,1042),(11,1043),(11,1044),(11,1045),(11,2000),(11,2003),(11,2004),(11,2005),(11,2006),(11,2007),(11,2008),(11,2009),(11,2010),(11,2011),(11,2012),(11,2013),(11,2014),(12,2017),(12,2022),(12,2024),(12,2025),(12,2026),(12,2027);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `create_by` bigint DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14787164048664 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`user_name`,`nick_name`,`password`,`type`,`status`,`email`,`phonenumber`,`sex`,`avatar`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`) values (1,'dm','dengming','$2a$10$SVMVBOpGvrfqj3InTbl8t.qSH4j1/trxJcrGGzrb/EllyfO2cioMu','1','0','23412332@qq.com','18888888888','1','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi',NULL,'2022-01-05 09:01:56',1,'2022-01-30 15:37:03',0),(2,'ysl','舒玲宝宝','$2a$10$SVMVBOpGvrfqj3InTbl8t.qSH4j1/trxJcrGGzrb/EllyfO2cioMu','0','0','11111@11.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(3,'sg3','weqe','$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O','1','0',NULL,NULL,'0',NULL,NULL,'2022-01-05 13:28:43',NULL,'2022-01-05 13:28:43',0),(4,'sg2','dsadd','$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he','1','0','23412332@qq.com','19098790742','0',NULL,NULL,NULL,NULL,NULL,0),(5,'sg2233','tteqe','','1','0',NULL,'18246845873','1',NULL,NULL,'2022-01-06 03:51:13',NULL,'2022-01-06 07:00:50',0),(6,'sangeng','sangeng','$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy','1','0','2312321','17777777777','0',NULL,NULL,'2022-01-16 06:54:26',NULL,'2022-01-16 07:06:34',0),(14787164048662,'weixin','weixin','$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC','0','0','weixin@qq.com',NULL,NULL,NULL,-1,'2022-01-30 17:18:44',-1,'2022-01-30 17:18:44',0);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户和角色关联表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1),(2,2),(5,2),(6,12);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
