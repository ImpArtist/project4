# **项目报告**

​				 	   **项目名:学院数据中台	组长:文俊熙	组员:兰永褀**

## **总体项目介绍**

### 项目背景和目标

随着学院信息化建设的不断深入，学院内部生成了大量的教学、科研和行政管理数据。然而，这些宝贵的数据分散在不同的系统和部门之间，形成了数据孤岛和碎片化现象，阻碍了数据资源的整合和有效利用。例如，教学部门可能有学生的课程表和成绩数据，科研团队则可能保留着实验室数据和科研成果，行政管理部门则负责学生和员工的档案和考勤数据。这些分散的数据不仅增加了数据管理的复杂性，还限制了数据在跨部门和跨系统之间的流通与共享，影响了学院决策的科学性和效率。

为了解决这些问题，我们计划启动学院数据中台建设项目。该项目旨在建立一个统一的数据管理平台，通过集成各部门和系统的数据，实现数据的统一标准化、高效管理和安全共享。这将极大地提升数据的可访问性和可用性，帮助学院更快速、更精确地进行教学质量评估、科研成果分析以及行政决策支持。

同时，通过数据的集成与分析，学院能够挖掘出更多潜在的数据价值，为未来的信息化发展奠定坚实的基础。通过先进的可视化工具和技术，将复杂的数据转化为直观、易于理解的图表和图像。此外，项目通过数据可视化帮助行政管理部门监控学院资源的使用效率，简化复杂数据的理解过程，使得决策者能够快速做出基于数据的决策，降低不同数据的使用观测的难度以及决策的科学性和效率。

数据中台不仅能够优化学院内部数据的管理流程，还能为师生提供更优质的服务和支持，推动学术研究和行政运营的现代化进程。



### 项目范围

\- 数据中台平台架构设计与搭建

\- 数据接入与集成

\- 数据存储与管理

\- 数据治理与安全

\- 数据服务与应用支持



### 技术架构和设计

#### 整体架构

如图为大致框架图，二层服务对应项目中的各种微服务。

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps1.png)

#### 后端技术架构

数据库：

使用 MySQL 数据库作为主要存储后端系统中的结构化数据，例如学生信息、课程表、成绩记录等。

Spring Boot：作为后端微服务的基础框架，提供了快速开发和部署微服务的能力。

Spring Cloud：用于构建分布式系统的工具集，包括服务发现、配置管理、负载均衡等功能。

Nacos：作为服务注册与发现中心，管理和发现微服务实例，支持动态配置管理和服务路由。

MyBatis Plus：通过将 Java 对象映射到 SQL 语句，简化了与 SQL 数据库的集成。



#### 前端技术架构

使用 Vue.js 作为前端开发的主要框架，提供了响应式和组件化的开发能力，使得界面开发更加高效和灵活。

数据可视化：

使用 ECharts 图表库进行数据可视化，通过图表展示学生学业能力、API使用情况、各个数据表内容等信息，帮助用户快速理解和分析数据。



### 关键功能和特性

#### 用户登录

用户通过提供用户名和密码进行登录，服务端验证后生成并返回一个 Token。客户端会将这个 Token 存储在本地，每次请求需要验证身份的资源时，将 Token 发送至服务端。服务端接收到 Token 后验证其有效性，包括检查签名和过期时间等。验证通过后，服务端根据 Token 中的信息授权用户访问资源。在 Token 过期前，客户端可请求更新，服务端根据定时策略更新或失效 Token。用户注销时，客户端清除 Token，或者服务端提供注销接口使其失效。



#### 数据查询

实现对不同表的各种查询，包括范围、精准、模糊、分组和关联查询。

|   类型   |                           功能描述                           |
| :------: | :----------------------------------------------------------: |
| 模糊查询 | 允许用户进行模糊匹配的数据查询，以便找到符合特定模式的数据。提供简单易用的界面，让用户输入关键词或模式进行查询。支持在单个字段或多个字段中进行模糊匹配。支持对大数据量进行高效查询，确保查询速度和效率。 |
| 精准查询 | 允许用户进行精准匹配的数据查询，以便找到完全符合条件的数据。提供多种查询条件的选择和组合，满足用户不同的查询需求 。支持对各种数据类型的精准匹配，如数字、日期、字符串等。提供查询结果的排序和分页功能，方便用户浏览大量查询结果 |
| 关联查询 | 允许用户在两个数据表或数据集之间进行连接查询，以便获取相关联的数据提供可视化的连接关系图，帮助用户理解不同数据之间的关联关系。 |
| 范围查询 | 允许用户按照指定字段的数值或日期范围进行查询，以获取符合条件的数据提供直观的界面，让用户轻松指定数值或日期范围。支持多种比较运算符，如大于、小于、等于、介于等。能够处理不同数据类型的范围查询，如数字范围、日期范围等。提供查询结果的排序和分页功能，以便用户查看大量数据 |
| 分组查询 | 允许用户根据指定字段对数据进行分组，并对每个分组进行聚合操作，以便进行统计分析或汇总计算。提供简单易用的分组配置界面，让用户选择分组字段和聚合操作。支持多种聚合操作，如计数、求和、平均值、最大最小值等。 |
| 数据筛选 | 允许用户根据指定条件对数据进行筛选，以便快速定位符合要求的数据子集。提供多种筛选条件的选择和组合，满足用户不同的筛选需求能够处理各种数据类型的筛选条件，如文本匹配、数值比较、日期范围等。 |



#### 数据分析

用户将在数据查询的基础上，对查询结果再进行一次聚合查询，前端会将上面数据查询的几个参数和下面数据分析的几个参数同时传给后端，验证并路由后，后端将再构造上面数据查询的SQL语句进行查询,再将得到的数据查询结果做数据分析查询，最后将结果以及根据类型做的一些统计传递给前端，前段做相应的可视化操作。



####  API管理

中台管理人员可以根据业务系统所要求的方案，在不同的业务系统基础上，为业务系统专门创建或修改API。

中台管理员可以实时监控各个API的流量情况。通过流量监控，管理员可以及时了解API的使用情况，发现潜在的性能问题或异常情况。

中台管理员可以查看API流量的统计报表，包括请求量、响应时间、错误率等指标的统计数据。这些报表可以帮助管理员了解API的使用情况和性能表现，为业务决策提供数据支持。



#### 可视化图表订阅

用户可以订阅生成的可视化图表，随时查看最新的数据分析结果。

动态数据交互：在可视化图表中加入互动功能，允许用户通过点击或悬停查看特定数据点的详细信息。提供更深入的数据洞察，使用户能够更直观地理解数据趋势和关键指标的变化。



#### 数据库内容操作

允许授权用户对数据库内容进行增删改查操作。通过可视化组件为管理人员能方便创建新的表以及其新的字段，也可直接进行删除。支持管理员对统一表的记录或字段进行修改。



#### 学生能力预测

基于历史数据和统计模型，预测学生的学术成绩或特定能力的发展趋势。

系统能够根据用户选择的多维度数据，自动生成雷达图，用以直观比较不同年级学生在各学科或能力维度上的表现差异。这种视觉化比较有助于识别学生的强项和需改进的领域，为个性化教育提供了数据支持。

此外，系统还能生成基于年份的柱状图，展示学生在学术能力上的预测趋势和与不同学历平均水平的对比。这种趋势分析不仅有助于预测学生未来的学术成就，还能帮助学校制定长期发展策略和资源分配计划。通过这些工具，教育机构可以更精确地了解和应对学生群体的需求，提升教育质量和学生的学习体验。



## 小组成员的工作内容(均为本人填写)

### 组员：兰永祺

#### 整体工作介绍

**前端页面开发**

设计并创建数据中台系统的前端页面，确保页面布局合理，易于用户理解和操作。

使用HTML、CSS和JavaScript技术，确保页面在不同设备上显示良好，并能够根据用户的操作做出相应变化。



**页面功能实现**

开发页面上的各种功能，比如用户点击按钮、填写表单等操作的响应。

通过与后端的数据交互，展示和处理用户需要的数据，确保页面信息的实时更新和准确显示



**团队协作**

与后端开发人员密切合作，确保前后端功能的协调和一致性。

与设计师协作，根据设计稿实现页面布局和样式，确保页面视觉上的一致性和用户友好性。



**测试和调试**

编写和执行测试用例，确保页面功能的正确性和稳定性。

调试并修复页面的bug和问题，保证用户体验的连续性和良好性。



####  具体工作内容

**1.登录页面设计与实现**

设计并开发登录页面，实现用户输入验证逻辑，例如检查用户名密码的正确性，提供友好的错误提示。

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps2.jpg)



**2. 数据搜索与过滤**

开发搜索功能，允许用户根据关键词快速查找数据。

设置多种搜索功能满足用户对不同搜索方法的需求，同时每种搜索方法配备对应的搜索输入框，并对用户输入的内容进行判别，比如是否输入了内容，是否选择了数据库等

对于不同搜索方式，前端将不同的消息封装发送给后端，并接收来自后端的数据以表格的形式展现在下方，其中表格可以进行分页查看，同时可以对不同的表头名进行排序，包括升序降序从而实现数据过滤和排序功能，让用户能够按需筛选和展示数据。

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps3.jpg)

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps4.jpg)



**3. 数据可视化图像生成（Echarts）**

使用Echarts将数据搜索得到的经过筛选后的数据发送给后端再次进行整理并重新或许数据呈现为图表和图形。

开发交互式图表，支持用户在图表中进行数据筛选和视图切换。同时支持对生成的图表进行订阅，在特定页面再次展示

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps5.jpg)



**4. 数据库内容操作**

通过后端API接口，实现对数据库内容的增删改查操作。包括对于数据表整体的修改以及对表中内容的修改操作

编写前端逻辑，处理用户操作（如表单提交）并与后端进行数据交互，确保数据的同步和一致性。

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps6.jpg)



**5. API功能管理**

创建API：对用户输入的API相关数据进行判断，通过后端返回的数据对API合法性进行验证，对于不同的输入在页面显示不同的反馈，正确引导用户进行合适的输入，若创建成功会弹出创建成功的弹窗并给出URL，失败则给出相应提示

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps7.jpg)

API详细信息查看：对已经创建的API进行查看管理操作，对API详细信息以表的内容进行展示，可供用户灵活搜索查看具体功能，对于用户选择的API，将该API的一段时间内的流量信息进行展示，并进行数据可视化方便用户查看



**6. 学生学业能力预测**

为用户设置学生的多个学业维度能力进行选择，将选择的维度项发送给后端并提供不同学历层次以及班级的能力平均值作为参照一同生成学历雷达图并进行比较判别

![img](file:///C:\Users\C_M_t\AppData\Local\Temp\ksohtml16664\wps8.jpg)

对不同的维度进行选择发送给后端，从后端接受数据将学生今年的学业能力进度与不同学历层次平均值共同以柱状图的形式可视化呈现作为学业能力趋势的判断依据，方便用户进行分析



###  组员:文俊熙

#### 整体工作介绍

**全后端开发**

工作包括不限于项目架构搭建、所有微服务部署(统一网关、登录、搜索、API管理、学生学业能力维度分析、统一库管理、报表查阅)、微服务注册与发现中心的管理、所有微服务的实现。

**接口设计**

后端代码先行，在ApiPost上创建所有接口并提供相应的测试用例以供前端对接。大概实现了70个接口，代码量达到7000+。

**数据库设计**

根据系统特性构建数据库表结构，使其能与后端逻辑兼容，并创建符合实际情况的记录，以供功能测试。

#### 具体工作内容

**1.统一网关**

实现了一个基本的Spring Cloud Gateway网关应用所需的核心功能和配置。通过合理的配置，该网关能够有效地管理和转发请求到后端的各个微服务实例，同时确保了系统的安全性和可扩展性。配置包括:

- 配置了网关服务的端口号，确保网关应用在特定端口上监听并处理请求。
- 配置了日志记录的级别和格式，以便记录网关处理请求的详细信息。
- 指定了网关应用的名称，以便在分布式系统中唯一标识该网关服务。
- 指定了Nacos注册中心的地址，确保网关能够从注册中心动态获取微服务的实例信息。
- 启用了Spring Cloud提供的负载均衡功能，以确保请求能够均衡分布到不同的微服务实例上。
- 配置了多个路由规则，定义了请求如何根据路径信息被路由到对应的微服务实例上。
- 配置了全局的跨域请求处理，确保网关可以安全地处理来自不同源的跨域请求。

**2.登录**

大致工作流程与逻辑如下

- 前端通过web将自己的用户名和密码发送到后端。
- 后端核对用户名和密码后，将用户ID及其他信息作为JWT payload，生成一个token返回给前端。
- 前端保存token，将结果保存在sessionStorage中，退出登录时删除token。
- 前端在此后的每次请求中的header中放入token。
- 后端检查token的有效性，包括签名是否正确，token是否过期等等。
- 后端验证token通过后，进行业务处理，返回相应结果。

这样做的好处是不需要在服务端保存会话信息，特别**适用于分布式微服务**。验证token是否有效时，会根据签名利用算法生成一份新的token，将新的token和传过来的token进行比对，比对结果决定token的验证是否通过。

**3.数据搜索与分析**

数据搜索方面，根据前端传来的搜索内容和相关参数比如是否是全字、选择展示的属性、升序或降序的属性等，在后端运用MyBatis+动态构建SQL语句，以达到符合搜索要求的目的，包括各种搜索类型(常规，范围，关联，分组)和各种搜索选项与参数。同时对数据搜索安全性提供了保障，通过MP里的各种标注符防止SQL注入等问题

数据分析方面，在数据查询的结果上，提供再一次的聚合分析，用户输入聚合的属性和分组的属性，后端手动对数据查询的结果进行分组，聚合，将分析的结果构建成Echarts中的Option，最后将Option传递回前端以生成报表，这里Option的处理分为对数值、字符串类型和日期类型三种情况，分别进行处理，**数值类型**统计每组平均、求和、最大值、最小值、标准差、方差、众数、中位数和分组、聚合属性的出现次数；**字符串类型**统计每组最多(少)出现次数的字符串以及他们的值和所在分组，并统计总体分组和聚合属性的出现次数；**日期类型**统计每组最大(小)的日期以及他们的值和所在分组，并统计总体分组和聚合属性的出现次数。最后根据聚合属性的类型，提供一段文字，显示分析的一些重要参数比如分布情况，集中趋势等，方便用户看到一些分析出的特性。最后用户可以订阅该报表，方便后面直接查看实时的数据分析。(数据搜索与分析部分是整个项目中最繁杂的，实现代码量超2000)

**4.报表查阅**

当用户选择订阅报表的时候，系统将此次数据查询以及分析的查询内容全部记录下来，与这个报表名和用户ID绑定在一起，当用户在报表查阅页面选择这个报表时，会调用这些查询信息，然后再次进行一次查询和分析，将结果也就是Option再次返回给前端进行报表构建，从而提供可视化界面，展示实时的分析结果。

**5.API管理**

- **API创建：**用户输入一个名称时，后端先通过正则表达式对名称合法性进行确认(跟变量标识符一样的规则)通过后则自动进行DB查询，判断是否已存在，两者都通过则该名称合法。当用户输入一个SQL语句时后端先通过正则表达式判断是否是一条查询语句，如果是则放入到DB中执行，若无报错则认为这个SQL语句合法。当用户正确填写API信息后，若创建成功则根据后端构建URL的规则自动构建一个URL并返回前端通过提示窗口展示，API自动设置为运行状态，只提供给目标业务用户进行服务。
- **API使用情况：**用户选择一个API名称，先返回这个API的其他信息如名称、URL等，然后调用DB中的API记录表，根据用户所选择的时间范围(最近一天，一个星期，一个月)筛选出符合条件的记录，将记录放入程序中开始进行分组统计(也就是时间切片分组分别为1h、1day、1day)，分别统计每个分组上API的访问次数，并统计总体访问的IP分布，构建Option返回前前端为用户提供可视化报表。(这是整个项目中最复杂，难度最大的业务，因为时间的动态性导致一切都要动态处理)

![image-20240711172844464](C:\Users\C_M_t\AppData\Roaming\Typora\typora-user-images\image-20240711172844464.png)

- **API表：**返回给前端API的列表，方便用户查看API的详细信息以及删除API，提供查询服务，方便用户快速定位。

**6.统一库管理**

用户选择数据库后可以看到库中所有表的真实信息，同时可以在库中创建表，创建时DB会在后端建两个表(删除同理删两个)，一个存储真实的数据信息，一个存储该表的字段信息，如中文名称，数据类型和展示顺序优先级(该表表结构时这个表也会同步进行更新)，通过这样能够实现数据查询与分析，API管理等服务的中文展示，增强了可读性。

选择并进入一个表后可以看到真实的数据信息以及表结构，用户可以直接在表中进行数据操作，前端会把改变的数据信息传给后端，后端进行更新或插入或删除，然后将修改的表数据再次发送给前端，若修改的数据不合法会返回修改失败的提示，并执行回滚等操作，保证修改的原子性。

**7.学生学业能力预测**

类似于数据查询与分析，同样是根据前端传来的查询信息如比较维度，比较目标以及学生本人信息进行数据聚合分析，将得到的结果构建出Option返回给前端进行报表生成。这里的一个解决方案就是设计了一个多级选择器可以对各种培养层次/年级/班级进行选择。![image-20240711174115368](C:\Users\C_M_t\AppData\Roaming\Typora\typora-user-images\image-20240711174115368.png)

## 项目可拓展性

拓展点其实有很多，但根据最实用也是最实际的我们挑出以下两点：

**1.数据库/表的拓展**

在统一库管理中，我们目前虽然只是在一个库中进行数据操作，但已提前提供样板，为能创建更多的库，存储更多的数据提供便利，也可能能接入其他数据库或表使其与系统兼容。

**2.性能提升与数据服务的增强**

由于我们采用的是微服务架构，容易将该系统进行不同维度的拓展，具体包括如下：

- **模块化和独立部署**：
  - 微服务架构将整个应用拆分为多个小型服务，每个服务专注于特定的业务功能。这种模块化使得新增、升级或替换特定服务的数据存储或处理组件变得更加简单和可控。
- **技术选型灵活性**：
  - 每个微服务可以根据其需求选择最适合的数据存储和处理工具，而不受整个应用的限制。例如，某些服务可能更适合使用Redis作为缓存或会话存储，而另一些可能需要Elasticsearch来支持复杂的搜索和分析功能。
- **水平扩展和弹性**：
  - 微服务架构使得每个服务可以独立进行水平扩展，根据负载需求增加或减少实例数量。这对于处理大量数据或高并发请求尤为重要，例如通过增加Kafka的分区或增加Doris的节点来提升处理能力。
- **服务自治性和解耦**：
  - 每个微服务在架构上是自治的，它们通过定义良好的API进行通信，而不是直接依赖于共享的中心化数据存储。这种解耦使得服务可以独立进行演进和扩展，而不会影响到整个系统的其他部分。
- **技术异构性的支持**：
  - 微服务架构支持技术异构性，即可以使用不同技术栈和工具来满足不同服务的需求。例如，可以在同一个系统中同时使用Redis、ES、Kafka和Doris等工具，每个服务选择最适合自己需求的工具和技术。
- **故障隔离和容错性**：
  - 单个微服务的故障不会影响整个系统的稳定性，相邻服务之间通过清晰的边界和API进行通信，因此可以更容易地实现故障隔离和部分服务降级。