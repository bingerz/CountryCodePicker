# CountryCodePicker

    适用于Android项目的国家代码选择器。

    国家代码的数据参考了谷歌、Facebook等一些应用中的数据，逐条验证过信息（国家名称，国旗，国家代码等）的准确性，
    并查漏补缺，最终数据来源于WiKi。

## 使用

### API
```java
/**
 * 跳转到选择器Activity界面
 */
new CountryCodePicker().start(this);

/**
 * 在当前的界面接收选择的国家代码
 */
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
        case CountryCodePicker.REQUEST_CODE_PICKER:
            if (data == null) {
                Toast.makeText(this, "Country is null", Toast.LENGTH_SHORT).show();
                return;
            }
            CountryCode countryCode = data.getParcelableExtra(CountryCodePicker.EXTRA_CODE);
            if (countryCode != null) {
                //添加自己的代码，处理返回的结果
                Toast.makeText(this, countryCode.mCountryCode + "", Toast.LENGTH_SHORT).show();
            }
            break;
    }
}
```

### 添加到工程

```groovy
buildscript {
  repositories {
    jcenter()
  }

  dependencies {
    compile 'com.bingerz.android:countrycodepicker:0.4.0'
  }
}
```

```java
//AndroidManifest.xml 文件中添加此行代码
<activity android:name="com.bingerz.android.countrycodepicker.CountryCodeActivity" />
```
## 详细说明

最初版本加载国家代码的数据是通过json文件，国家代码和国家名称等信息保存在json文件中。在略大的项目中，
读取json文件过程会对IO造成阻塞，导致界面ANR，现在改为string-array的方式。