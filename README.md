# Android中MVP模式易学详解

## MVC模式
MVC模式的结构分为三部分，实体层的Model，视图层的View，以及控制层的Controller

![](https://github.com/ModelPresenter/MVPDemo/blob/master/screenshot/AndroidMVCpattern.png)

例如，View层接受用户的输入，然后通过Controller修改对应的Model实例；同时，当Model实例的数据发生变化的时候，需要修改UI界面，
可以通过Controller更新界面。（View层也可以直接更新Model实例的数据，而不用每次都通过Controller，这样对于一些简单的数据更新工作会变得方便许多。）

在Android中，承担View的是XML，Activity和Fragment，逻辑代码Controller也在Activity和Fragment中并没有单独提取出来，
其实这样的MVC我们也可以看成是MV模式，如右图可见

## MVP模式
在Android项目中，Activity和Fragment占据了大部分的开发工作。如果有一种设计模式（或者说代码结构）
专门是为优化Activity和Fragment的代码而产生的，你说这种模式重要不？这就是MVP设计模式。

按照MVC的分层，Activity和Fragment（后面只说Activity）应该属于View层，用于展示UI界面，以及接收用户的输入，
此外还要承担一些生命周期的工作。Activity是在Android开发中充当非常重要的角色，特别是TA的生命周期的功能，
所以开发的时候我们经常把一些业务逻辑直接写在Activity里面，这非常直观方便，代价就是Activity会越来越臃肿，
超过1000行代码是常有的事，而且如果是一些可以通用的业务逻辑（比如用户登录），写在具体的Activity里就意味着这个逻辑不能复用了。
如果有进行代码重构经验的人，看到1000+行的类肯定会有所顾虑。因此，Activity不仅承担了View的角色，还承担了一部分的Controller角色，
这样一来V和C就耦合在一起了，虽然这样写方便，但是如果业务调整的话，要维护起来就难了，而且在一个臃肿的Activity类查找业务逻辑的代码也会非常蛋疼，
所以看起来有必要在Activity中，把View和Controller抽离开来，而这就是MVP模式的工作。

![](https://github.com/ModelPresenter/MVPDemo/blob/master/screenshot/AndroidMVPpattern.png)

###### MVP模式的核心思想：
MVP把Activity中的UI逻辑抽象成View接口，把业务逻辑抽象成Presenter接口，Model类还是原来的Model

这就是MVP模式，现在这样的话，Activity的工作的简单了，只用来响应生命周期，其他工作都丢到Presenter中去完成。从上图可以看出，
Presenter是Model和View之间的桥梁，为了让结构变得更加简单，View并不能直接对Model进行操作，这也是MVP与MVC最大的不同之处。

## MVP模式的作用
MVP模式有什么好处？
- 分离了视图逻辑和业务逻辑，降低了耦合
- Activity只处理生命周期的任务，代码变得更加简洁
- 视图逻辑和业务逻辑分别抽象到了View和Presenter的接口中去，提高代码的可阅读性
- Presenter被抽象成接口，可以有多种具体的实现，所以方便进行单元测试
- 把业务逻辑抽到Presenter中去，避免后台线程引用着Activity导致Activity的资源无法被系统回收从而引起内存泄露和OOM

其中最重要的有三点：

#### Activity 代码变得更加简洁
使用MVP之后，Activity就能瘦身许多了，基本上只有FindView、SetListener以及Init的代码。
其他的就是对Presenter的调用，还有对View接口的实现。这种情形下阅读代码就容易多了，而且你只要看Presenter的接口，
就能明白这个模块都有哪些业务，很快就能定位到具体代码。Activity变得容易看懂，容易维护，以后要调整业务、删减功能也就变得简单许多。
#### 方便进行单元测试
一般单元测试都是用来测试某些新加的业务逻辑有没有问题，如果采用传统的代码风格，我们可能要先在Activity里写一段测试代码，测试完了再把测试代码删掉换成正式代码，
这时如果发现业务有问题又得换回测试代码，咦，测试代码已经删掉了！好吧重新写吧……

MVP中，由于业务逻辑都在Presenter里，我们完全可以写一个PresenterTest的实现类继承Presenter的接口，
现在只要在Activity里把Presenter的创建换成PresenterTest，就能进行单元测试了，测试完再换回来即可。
万一发现还得进行测试，那就再换成PresenterTest吧。

#### 避免 Activity 的内存泄露
Android APP 发生OOM的最大原因就是出现内存泄露造成APP的内存不够用，而造成内存泄露的两大原因之一就是Activity泄露（Activity Leak）（另一个原因是Bitmap泄露（Bitmap Leak））。
- Java一个强大的功能就是其虚拟机的内存回收机制，这个功能使得Java用户在设计代码的时候，不用像C++用户那样考虑对象的回收问题。然而，Java用户总是喜欢随便写一大堆对象，然后幻想着虚拟机能帮他们处理好内存的回收工作。
可是虚拟机在回收内存的时候，只会回收那些没有被引用的对象，被引用着的对象因为还可能会被调用，所以不能回收。

采用传统的MV模式，一大堆异步任务和对UI的操作都放在Activity里面，比如你可能从网络下载一张图片，
在下载成功的回调里把图片加载到 Activity 的 ImageView 里面，所以异步任务保留着对Activity的引用。这样一来，即使Activity已经被切换到后台（onDestroy已经执行），这些异步任务仍然保留着对Activity实例的引用，所以系统就无法回收这个Activity实例了，结果就是Activity Leak。
Android的组件中，Activity对象往往是在堆（Java Heap）里占最多内存的，所以系统会优先回收Activity对象，如果有Activity Leak，APP很容易因为内存不够而OOM。

采用MVP模式，只要在当前的Activity的onDestroy里，分离异步任务对Activity的引用，就能避免 Activity Leak。

使用MVP，至少需要经历以下步骤：
- 创建Presenter接口，把所有业务逻辑的接口都放在这里，并创建它的实现PresenterImpl（在这里可以方便地查看业务功能，由于接口可以有多种实现所以也方便写单元测试）
- 创建View接口，把所有视图逻辑的接口都放在这里，其实现类是当前的Activity/Fragment
- 由图可以看出，Activity里包含了一个Presenter，而PresenterImpl里又包含了一个View并且依赖了Model。Activity里只保留对Presenter的调用，其它工作全部留到PresenterImpl中实现
- Model并不是必须有的，但是一定会有View和Presenter

通过上面的介绍，MVP的主要特点就是把Activity里的许多逻辑都抽离到View和Presenter接口中去，并由具体的实现类来完成。这种写法多了许多IView和IPresenter的接口，在某种程度上加大了开发的工作量，刚开始使用MVP的小伙伴可能会觉得这种写法比较别扭，而且难以记住。其实一开始想太多也没有什么卵用，只要在具体项目中多写几次，就能熟悉MVP模式的写法，理解TA的意图，以及享受其带来的好处。

项目是一个简单的登录界面，点击LOGIN则进行账号密码验证，点击CLEAR则重置输入。用MVP模式实现

![](https://github.com/ModelPresenter/MVPDemo/blob/master/screenshot/Screenshot.png)

## License
Thanks：
学习参考 Kaede 的MVP模式 简单易懂的介绍
