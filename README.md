- **后台代码在lanlanserver包内**
- **数据库设计sql并不包含数据**
- **服务器使用的是本地服务器，云服务器未配置，所以要想运行，还需配置本地环境，修改com.example.mrzheng.lanlanapp.DataBaseService.HttpService.IP，并让客户端和服务器端在同一局域网下。**
- **项目为数据库课设，部分逻辑未完成，依赖较多可以优化地方甚多……**

## APP部分截图及功能介绍

### 首次下载该APP时的功能介绍
![1](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/1.png)![2](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/2.png)![3](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/3.png)![4](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/4.png)

### 每次打开APP时的欢迎动画（可替换为广告）
![5](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/5.png)

### 登录界面
1. 登录界面的logo应为app的logo，但是没有时间设计logo，就先用google代替
2. 左上角X号可以点击，点击即结束此APP进程
3. 编写了动画使得输入手机号和密码的时候弹出的键盘不会遮挡输入（输入的时候主要部分会适度上移动）
![6](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/6.png)

### 注册界面
1. 点击登录界面中的“注册新用户”即可跳转到该界面
2. 手机号输入框限制为number
3. 密码和确认密码限制为必须一致（否则有Toast提示），且密码输入框通过字符过滤器限制为不能输入特殊字符（有提示），不能输入空格回车
4. 点击性别会弹出男/女供用户选择
5. 学号限制为数字
6. 学校默认为山东大学威海校区（该APP本意即为先做本学校使用的APP） 
7. 所有输入框不能为空，否则点击OK会Toast提示
![7](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/7.png)

### 联系客服/关于我们
点击登录界面中的关于我们或者联系客服将会跳转到该界面
![8](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/8.png)

### APP主页
1. 可以无限下滑的RecycleView（只要数据库中任务足够）
2. 显示的是当前所有未被接受的任务的缩略信息
3. 右边的飞机图代表代发快递，箱子图代表代收快递
4. 当下滑查看更多任务时，上部分的代发任务|代收任务会向上消失（增大显示界面效果）
5. 点击“+”按钮，弹出两个按钮，一个发布代发任务，一个是发布代收任务
6. 点击左下角主页，会刷新当前界面，更新任务列表
![9](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/9.png)![10](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/10.png)

### 任务信息详细界面
1. 点击主页中的任何一个任务缩略布局即跳转到该任务的详细信息界面
2. 点击发布者手机号右边的“手机图片”会动态申请权限，若用户同意该权限则调用系统拨号发布者（并不呼叫，只是拨号盘会自动输入好发布者的手机号）
3. 点击接受任务，该任务会被标记为已被接受，个人中心界面也会实时更新接受任务数数据
![11](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/11.png)![12](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/12.png)

### 发布任务（以发布代发快递任务为例）
1. 点击主页中“+”按钮中的代发任务，跳转到该界面（截图为已经输入好的，初始界面全部由与用户填写）
2. 物件类型会弹出选择框供用户选择，亦可自己编辑
3. 点击交易时间弹出时间选择器，用户不可自主输入交易时间
![13](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/13.png)![14](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/14.png)

4. 点击next会跳转到下面的这个界面。
5. 寄件人姓名手机号地址默认为当前用户信息
6. 输入所有信息之后即可点击OK发布该代发任务
7. 发布后个人中心会实时更新发布任务数数据
![15](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/15.png)![16](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/16.png)

8.点击主页刷新任务界面，会显示出数据库中新增的任务信息
![17](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/17.png)

### 本用户所有的发布任务
1. 发布代发任务或者代收任务后，会跳转到该界面显示当前用户所有的发任务
2. 点击个人中心的“发布的任务”也会跳转到该界面
3. 每个item可以点击，点击后底部会显示是否已被接受，如果未被接受，还会显示一个取消该任务的按钮
![18](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/18.png)![19](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/19.png)

### 当前用户所有的接受任务
1. 点击个人中心中的“接受的任务”，会跳转到该界面显示该用户所有接受的任务信息。
2. 点击每个item会显示该任务的详细信息。
![20](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/20.png)

### 快递界面
1. 中间如果查询的快递有信息更新的话，会在这个界面显示出来
![21](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/21.png)

### 查询快递
1. 可以输入运单号也可以扫一扫自动填写运单号
2. 输入运单号的过程中，底部会实时查询运单号的快递公司，如果没找到，用户还可以选择快递公司
3. 点击快递公司如果查询到，则会显示该快递的运送信息
4. 第三张图为所有的快递公司，右部索引可以点击快速定位
5. 查找不到快递信息则会显示查找不到该快递信息界面（未截图）
![22](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/22.png)
![23](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/23.png)
![24](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/24.png)

### 历史记录
所有查询过的信息都会显示在该界面中
![25](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/25.png)

### 扫一扫
1. 扫一扫也可以扫相册里的图，左下角第一按钮为灯光。
![26](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/26.png)

### 编辑资料
1. 点击个人中心的编辑资料会跳转到修改信息界面
2. 前面的信息可以修改，后面的信息不能修改
3. 点击修改头像会显示第一站图
4. 选择拍照，则拍照然后截取，并显示到当前头像框中
5. 选择相册，则选取图片然后截取，并显示到当前头像框中
6. 点击保存则把所有信息更改到数据库中并更新个人中心界面。
![27](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/27.png)![28](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/28.png)![29](https://github.com/zhengtianle/campus-express-delivery/raw/master/screenshot/29.png)

### 其他
1. 点击个人中心里面的“退出登录”会登出当前账户，回到登录界面
2. 两秒内连续按下返回键，会退出当前APP，结束其进程
3. 每个数据库操作出现错误时，都会有相关Toast提示，比如手机号已被注册，发布失败等等。