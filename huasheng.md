## 华盛

:pager::打包文件

前端：

**npm run build:prod**打包-->生成dist文件(打包之前先删除)

后端：

**mvn clean package**打包-->生成jar包文件(注意需要更改的四个地方)

>前端、后端更改哪部分就重新打包哪部分  打包后的文件压缩之后发送

:pager::后端更改

- yml配置文件：

  ![image-20240614173045766](C:\Users\姜逸凡\AppData\Roaming\Typora\typora-user-images\image-20240614173045766.png)

- OrderInfoController:

  - ![image-20240614174215372](C:\Users\姜逸凡\AppData\Roaming\Typora\typora-user-images\image-20240614174215372.png)
  - ![image-20240614174336494](C:\Users\姜逸凡\AppData\Roaming\Typora\typora-user-images\image-20240614174336494.png)

- ProjectInfoServiceImpl:

  ```
  D:\\其他\\刘老师\\项目\\华胜投标招标\\bidding-agent-be\\end\\src\\main\\resources\\static\\
  ```

![image-20240614175255273](C:\Users\姜逸凡\AppData\Roaming\Typora\typora-user-images\image-20240614175255273.png)