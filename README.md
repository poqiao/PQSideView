# PQSideView
![image](https://github.com/poqiao/PQSideView/blob/master/app/src/main/assets/tup.gif)
### 导入以下依赖
```Java
 implementation 'com.github.poqiao:PQSideView:1.0.1"
 ```
 ### 可设置背景颜色，字体颜色，提示view的颜色，文字大小根据高度来获取，
 ```Java
   <com.mxq.pqsideview.PQSideBar
        android:layout_width="120dp"
        android:layout_alignParentRight="true"
        app:hint_bg_color="#ccc"
        app:hint_text_color="#fff"
        app:bg_select_color="#ff0000"
        app:text_select_color="#fff"
        app:text_normal_color="#000"
        app:bg_color="#00ffffff"
        android:layout_height="match_parent" />
```
```Java
<declare-styleable name="PQSideBar">
        未选择状态下的文字颜色
        <attr name="text_normal_color" format="color|reference" />
        <!--选中的文字颜色-->
        <attr name="text_select_color" format="color|reference" />
       <!-- 背景颜色-->
        <attr name="bg_color" format="color|reference" />
        <!--选中的背景颜色-->
        <attr name="bg_select_color" format="color|reference" />
       <!-- 提示的字体颜色-->
        <attr name="hint_text_color" format="color|reference" />
       <!-- 提示的背景颜色-->
        <attr name="hint_bg_color" format="color|reference" />
        <!--提示的类型-->
        <attr name="hint_type" format="enum">
            <enum name="circle" value="1"/>
            <enum name="circle_right_angle" value="2"/>
        </attr>
    </declare-styleable>
```
