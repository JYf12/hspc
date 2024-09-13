# hspc
huashengpc record

:earth_africa::修改分支名

>**git branch -m main master** --->将分支名main修改为master

:earth_africa::本地仓库的修改没有基于远程仓库的最新版本 push会报错

>tip1:使用git pull先拉取远程仓库中的最新版本代码  在修改本地代码前先拉取远程最新代码 
>
>tip2:本地修改代码后，每次从本地仓库push到远程仓库之前都要先进行git pull操作，保证push到远程仓库时没有版本冲突。
>
>**git pull 远程仓库名 远程分支名 --rebase**

:earth_africa::将本地分支跟踪到远程分支

>`git branch --set-upstream master origin/next` —>指定master分支追踪到origin/next

:palm_tree::

![](F:\temp\huashengpc\git1.jpeg)

![](F:\temp\huashengpc\git2.jpeg)

![](F:\temp\huashengpc\git3.jpeg)

![](F:\temp\huashengpc\gitstar.jpeg)

update righthere at Sep 13th 16:22
