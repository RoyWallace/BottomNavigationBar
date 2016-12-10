# BottomNavigationBar
Material design 更新的BottomNavigationbar的开源实现

目前版本最新版本是v0.3，还在努力完善中，有兴趣的朋友可以关注。如果发现问题请写在issue，我会定期查看和修复。

###version

v0.1 基础版本

v0.2 支持修改tab selected 情况下的width scale 属性

v0.3 支持设置tab text 在unselected情况下是否隐藏

###Demo修改
有童鞋反应说要如何实现像新规范里面NavigationBar透明的样式，所以这里把项目的Demo修改一下，实现Navigationbar透明并且覆盖在BottomNavigationBar上面。（目前这种实现方式还不够完善，会导致statusBar也不占位的问题。如果有网友有更好的实现方式，不妨提出来，大家一起探讨和分享。）
ps:最近碰上公司项目比较赶，所以暂停了BottomNavigationBar的开源进度。

具体实现：
demo基础上

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
去掉所有的xml中的所有android:fitsSystemWindows="true"

    <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:theme="@style/AppTheme.AppBarOverlay">

        <View
            android:layout_width="match_parent"
            android:layout_height="24dp"/>

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="#33000000"
            app:layout_scrollFlags="scroll"
            app:popupTheme="@style/AppTheme.PopupOverlay"/>

    </android.support.design.widget.AppBarLayout>

#####补个图

 ![image](https://github.com/RoyWallace/BottomNavigationBar/blob/master/gif/v0.3.1.gif?raw=true)


###TODO

 支持 bar background 不随tab选中而变化
 支持 tab textColor selector


[bottom navigation bars的规范在这里](https://www.google.com/design/spec/components/bottom-navigation.html)

###Show
![image](https://github.com/RoyWallace/BottomNavigationBar/blob/master/gif/v0.2.gif?raw=true)
![image](https://github.com/RoyWallace/BottomNavigationBar/blob/master/gif/v0.3.gif?raw=true)

####How to use
[![](https://jitpack.io/v/RoyWallace/BottomNavigationBar.svg)](https://jitpack.io/#RoyWallace/BottomNavigationBar)

**Step 1**. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
**Step 2.** Add the dependency

    dependencies {
            compile 'com.github.RoyWallace:BottomNavigationBar:v0.1'
    }

**Step 3.**  use it in your layout xml

    <etong.bottomnavigation.lib.BottomNavigationBar
        android:id="@+id/bottomLayout"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:layout_gravity="bottom"/>
**Step 4.** add tab and tabSelected listener

    bottomLayout = (BottomNavigationBar) findViewById(R.id.bottomLayout);
    //params: int icon,String text,int color
    bottomLayout.addTab(R.mipmap.ic_local_movies_white_48dp, "Movies & Tv", 0xff4a5965);
    bottomLayout.setOnTabListener(new BottomNavigationBar.TabListener() {
        @Override
        public void onSelected(int position) {
            //...
        }
    });
and you can also use the **BottomNavigationBehavior** with **CoordinatorLayout** 

    <etong.bottomnavigation.lib.BottomNavigationBar
        android:id="@+id/bottomLayout"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:layout_gravity="bottom"
        app:layout_behavior="etong.bottomnavigation.lib.BottomNavigationBehavior"/>

####Set tab selected width( bottomLayout.setTabWidthSelectedScale(1.5f);
####Set tab text default visible
    bottomLayout.setTextDefaultVisible(true);
    
 

#License
BottomNavigationBar for Android
The MIT License (MIT)

Copyright (c) 2016 恶童历险记(https://github.com/RoyWallace)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


